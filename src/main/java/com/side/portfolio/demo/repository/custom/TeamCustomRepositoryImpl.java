package com.side.portfolio.demo.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.portfolio.demo.dto.condition.QTeamDto;
import com.side.portfolio.demo.dto.condition.TeamDto;
import com.side.portfolio.demo.dto.condition.TeamSearchCond;
import com.side.portfolio.demo.status.TeamStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.side.portfolio.demo.domain.QTeam.team;

@Repository
@RequiredArgsConstructor
public class TeamCustomRepositoryImpl implements TeamCustomRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<TeamDto> searchTeam(TeamSearchCond cond, Pageable pageable) {

        List<TeamDto> content = queryFactory
                .select(new QTeamDto(team.id, team.name, team.phNumber, team.email, team.tickets,
                        team.createdDate, team.modifiedDate, team.status,
                        team.address.street, team.address.city,
                        team.address.zipcode))
                .from(team)
                .where(idEq(cond.getId()), nameEq(cond.getName()),
                        phNumberEq(cond.getPhNumber()), ticketsGoe(cond.getTickets()),
                        emailEq(cond.getEmail()), statusEq(cond.getStatus()),
                        streetEq(cond.getStreet()), cityEq(cond.getCity()),
                        zipcodeEq(cond.getZipcode()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(team.count())
                .from(team)
                .where(idEq(cond.getId()), nameEq(cond.getName()),
                        phNumberEq(cond.getPhNumber()), ticketsGoe(cond.getTickets()),
                        emailEq(cond.getEmail()), statusEq(cond.getStatus()),
                        streetEq(cond.getStreet()), cityEq(cond.getCity()),
                        zipcodeEq(cond.getZipcode()));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());

    }

    private BooleanExpression idEq(Long id) {
        return id == null ? null : team.id.eq(id);
    }

    private BooleanExpression nameEq(String name) {
        return StringUtils.hasText(name) ? team.name.eq(name) : null;
    }

    private BooleanExpression phNumberEq(String phNumber) {
        return StringUtils.hasText(phNumber) ? team.phNumber.eq(phNumber) : null;
    }

    private BooleanExpression ticketsGoe(Integer tickets) {
        return tickets == null ? null : team.tickets.goe(tickets);
    }

    private BooleanExpression emailEq(String email) {
        return StringUtils.hasText(email) ? team.email.eq(email) : null;
    }

    private BooleanExpression statusEq(TeamStatus status) {
        return status == null ? null : team.status.eq(status);
    }

    private BooleanExpression streetEq(String street) {
        return StringUtils.hasText(street) ? team.address.street.eq(street) : null;
    }

    private BooleanExpression cityEq(String city) {
        return StringUtils.hasText(city) ? team.address.city.eq(city) : null;
    }

    private BooleanExpression zipcodeEq(String zipcode) {
        return StringUtils.hasText(zipcode) ? team.address.zipcode.eq(zipcode) : null;
    }
}
