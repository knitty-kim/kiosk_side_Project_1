package com.side.portfolio.demo.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.portfolio.demo.dto.condition.PartnerDto;
import com.side.portfolio.demo.dto.condition.QPartnerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.side.portfolio.demo.domain.QPartner.partner;
import static com.side.portfolio.demo.domain.QSeller.seller;
import static com.side.portfolio.demo.domain.QTeam.team;

@Repository
@RequiredArgsConstructor
public class SellerCustomRepositoryImpl implements SellerCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PartnerDto> searchPartnerByTeamId(Long teamId) {

        return queryFactory
                .select(new QPartnerDto(seller.id, seller.name, team.id,
                        seller.phNumber, seller.email, seller.status, partner.status,
                        seller.createdDate, partner.createdDate))
                .from(partner)
                .join(partner.team, team)
                .join(partner.seller, seller)
                .where(partner.team.id.eq(teamId))
                .fetch();

    }


}
