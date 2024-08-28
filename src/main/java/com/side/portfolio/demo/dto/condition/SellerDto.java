package com.side.portfolio.demo.dto.condition;

import com.querydsl.core.annotations.QueryProjection;
import com.side.portfolio.demo.status.SellerStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class SellerDto {

    private Long id;
    private String name;
    private String phNumber;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private SellerStatus status;
    private String remark;

    @QueryProjection
    public SellerDto(Long id, String name, String phNumber,
                     String email, LocalDateTime createdDate,
                     LocalDateTime modifiedDate, SellerStatus status,
                     String remark) {
        this.id = id;
        this.name = name;
        this.phNumber = phNumber;
        this.email = email;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.status = status;
        this.remark = remark;
    }
}
