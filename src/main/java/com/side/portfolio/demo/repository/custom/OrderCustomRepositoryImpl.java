package com.side.portfolio.demo.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.portfolio.demo.domain.QTeam;
import com.side.portfolio.demo.dto.condition.OrderedItemDto;
import com.side.portfolio.demo.dto.condition.QOrderedItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.side.portfolio.demo.domain.QItem.item;
import static com.side.portfolio.demo.domain.QOrder.order;
import static com.side.portfolio.demo.domain.QOrderItem.orderItem;
import static com.side.portfolio.demo.domain.QSeller.seller;
import static com.side.portfolio.demo.domain.QTeam.*;
import static com.side.portfolio.demo.status.OrderStatus.ORDERED;

@Repository
@RequiredArgsConstructor
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderedItemDto> searchOrderItemBySellerId(Long sellerId) {

        return queryFactory
                .select(new QOrderedItemDto(item.id, item.name,
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
}
