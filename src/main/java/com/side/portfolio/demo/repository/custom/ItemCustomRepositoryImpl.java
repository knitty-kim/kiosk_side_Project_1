package com.side.portfolio.demo.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.portfolio.demo.dto.condition.ItemDto;
import com.side.portfolio.demo.dto.condition.ItemSearchCond;
import com.side.portfolio.demo.dto.condition.QItemDto;
import com.side.portfolio.demo.status.ItemStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

import static com.side.portfolio.demo.domain.QItem.item;
import static com.side.portfolio.demo.domain.QSeller.seller;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ItemDto> searchItem(ItemSearchCond cond, Pageable pageable) {

        List<ItemDto> content = queryFactory
                .select(new QItemDto(item.id, item.name, item.price, item.qty,
                        seller.id, seller.name, item.status))
                .from(item)
                .join(item.seller, seller)
                .where(idEq(cond.getId()), nameEq(cond.getName()),
                        priceGoe(cond.getPrice()), qtyGoe(cond.getQty()),
                        sellerNameEq(cond.getSellerName()), statusEq(cond.getStatus()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(item.count())
                .from(item)
                .join(item.seller, seller)
                .where(idEq(cond.getId()), nameEq(cond.getName()),
                        priceGoe(cond.getPrice()), qtyGoe(cond.getQty()),
                        sellerNameEq(cond.getSellerName()), statusEq(cond.getStatus()));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());

    }

    private BooleanExpression idEq(Long id) {
        return id == null ? null : item.id.eq(id);
    }

    private BooleanExpression nameEq(String name) {
        return StringUtils.hasText(name) ? item.name.eq(name) : null;
    }

    private BooleanExpression priceGoe(BigDecimal price) {
        return price == null ? null : item.price.goe(price);
    }

    private BooleanExpression qtyGoe(Integer qty) {
        return qty == null ? null : item.qty.goe(qty);
    }

    private BooleanExpression sellerNameEq(String sellerName) {
        return StringUtils.hasText(sellerName) ? seller.name.eq(sellerName) : null;
    }

    private BooleanExpression statusEq(ItemStatus status) {
        return status == null ? null : item.status.eq(status);
    }

}
