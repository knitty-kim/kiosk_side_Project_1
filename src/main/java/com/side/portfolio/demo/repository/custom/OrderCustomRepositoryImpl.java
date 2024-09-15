package com.side.portfolio.demo.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.portfolio.demo.dto.condition.OrderedItemDto;
import com.side.portfolio.demo.dto.condition.OrderedItemSearchCond;
import com.side.portfolio.demo.dto.condition.QOrderedItemDto;
import com.side.portfolio.demo.status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.side.portfolio.demo.domain.QItem.item;
import static com.side.portfolio.demo.domain.QOrder.order;
import static com.side.portfolio.demo.domain.QOrderItem.orderItem;
import static com.side.portfolio.demo.domain.QSeller.seller;
import static com.side.portfolio.demo.domain.QTeam.team;

@Repository
@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderedItemDto> searchOrderItemBySellerId(Long sellerId) {

        return queryFactory
                .select(new QOrderedItemDto(order.id, item.id, item.name,
                        orderItem.count, order.team.id,
                        order.team.name, order.status,
                        order.createdDate, order.modifiedDate))
                .from(orderItem)
                .join(orderItem.item, item)
                .join(item.seller, seller)
                .join(orderItem.order, order)
                .join(order.team, team)
                .where(seller.id.eq(sellerId))
                .fetch();
    }

    @Override
    public List<OrderedItemDto> searchOrderItemBySellerId_Cond(Long sellerId, OrderedItemSearchCond cond) {

        List<OrderedItemDto> content = queryFactory
                .select(new QOrderedItemDto(order.id, item.id, item.name,
                        orderItem.count, order.team.id,
                        order.team.name, order.status,
                        order.createdDate, order.modifiedDate))
                .from(orderItem)
                .join(orderItem.item, item)
                .join(item.seller, seller)
                .join(orderItem.order, order)
                .join(order.team, team)
                .where(seller.id.eq(sellerId), orderIdEq(cond.getOrderId()),
                        itemIdEq(cond.getItemId()), itemNameEq(cond.getItemName()),
                        teamNameEq(cond.getTeamName()), statusEq(cond.getOrderStatus()),
                        startDateGoe(cond.getStartDate()),
                        endDateLoe(cond.getEndDate()))
                .fetch();

        return content;
    }

    private BooleanExpression orderIdEq(Long id) {
        return id == null ? null : order.id.eq(id);
    }

    private BooleanExpression itemIdEq(Long id) {
        return id == null ? null : item.id.eq(id);
    }

    private BooleanExpression itemNameEq(String name) {
        return StringUtils.hasText(name) ? item.name.eq(name) : null;
    }

    private BooleanExpression teamNameEq(String name) {
        return StringUtils.hasText(name) ? team.name.eq(name) : null;
    }

    private BooleanExpression startDateGoe(LocalDate startDate) {
        return startDate == null ? null : order.createdDate.goe(startDate.atStartOfDay());
    }

    private BooleanExpression endDateLoe(LocalDate endDate) {
        return endDate == null ? null : order.createdDate.loe(endDate.atTime(23,59,59));
    }

    private BooleanExpression statusEq(OrderStatus status) {
        return status == null ? null : order.status.eq(status);
    }
}
