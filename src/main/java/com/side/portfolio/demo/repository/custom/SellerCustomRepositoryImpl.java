package com.side.portfolio.demo.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.portfolio.demo.domain.Partner;
import com.side.portfolio.demo.dto.condition.*;
import com.side.portfolio.demo.status.SellerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.side.portfolio.demo.domain.QPartner.partner;
import static com.side.portfolio.demo.domain.QSeller.seller;
import static com.side.portfolio.demo.domain.QTeam.team;

@Repository
@RequiredArgsConstructor
public class SellerCustomRepositoryImpl implements SellerCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PartnerDto> searchPartnerByTeamId(Long teamId, PartnerSearchCond cond, Pageable pageable) {

        List<PartnerDto> content = queryFactory
                .select(new QPartnerDto(seller.id, seller.name, team.id,
                        seller.phNumber, seller.email, seller.status, partner.status,
                        seller.createdDate, partner.createdDate))
                .from(partner)
                .join(partner.team, team)
                .join(partner.seller, seller)
                .where(partner.team.id.eq(teamId),
                        idEq(cond.getId()), nameEq(cond.getName()),
                        phNumberEq(cond.getPhNumber()), emailEq(cond.getEmail()),
                        statusEq(cond.getStatus()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(partner.count())
                .from(partner)
                .join(partner.team, team)
                .join(partner.seller, seller)
                .where(partner.team.id.eq(teamId),
                        idEq(cond.getId()), nameEq(cond.getName()),
                        phNumberEq(cond.getPhNumber()), emailEq(cond.getEmail()),
                        statusEq(cond.getStatus()));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());

    }

    @Override
    public Page<SellerDto> searchSeller(SellerSearchCond cond, Pageable pageable) {

        List<SellerDto> content = queryFactory
                .select(new QSellerDto(seller.id, seller.name, seller.phNumber,
                        seller.email, seller.createdDate, seller.modifiedDate,
                        seller.status, seller.remark))
                .from(seller)
                .where(idEq(cond.getId()), nameEq(cond.getName()),
                        phNumberEq(cond.getPhNumber()), emailEq(cond.getEmail()),
                        statusEq(cond.getStatus()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(seller.count())
                .from(seller)
                .where(idEq(cond.getId()), nameEq(cond.getName()),
                        phNumberEq(cond.getPhNumber()), emailEq(cond.getEmail()),
                        statusEq(cond.getStatus()));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());

    }

    private BooleanExpression idEq(Long id) {
        return id == null ? null : seller.id.eq(id);
    }

    private BooleanExpression nameEq(String name) {
        return StringUtils.hasText(name) ? seller.name.eq(name) : null;
    }

    private BooleanExpression emailEq(String email) {
        return StringUtils.hasText(email) ? seller.email.eq(email) : null;
    }

    private BooleanExpression phNumberEq(String phNumber) {
        return StringUtils.hasText(phNumber) ? seller.phNumber.eq(phNumber) : null;
    }

    private BooleanExpression statusEq(SellerStatus status) {
        return status == null ? null : seller.status.eq(status);
    }

}
