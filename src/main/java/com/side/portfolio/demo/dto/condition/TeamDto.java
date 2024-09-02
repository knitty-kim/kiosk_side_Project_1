package com.side.portfolio.demo.dto.condition;

import com.querydsl.core.annotations.QueryProjection;
import com.side.portfolio.demo.status.TeamStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class TeamDto {

    private Long id;
    private String name;
    private String phNumber;
    private String email;
    private Integer tickets;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private TeamStatus status;
    private String street;
    private String city;
    private String zipcode;

    @QueryProjection
    public TeamDto(Long id, String name, String phNumber,
                   String email, Integer tickets, LocalDateTime createdDate,
                   LocalDateTime modifiedDate, TeamStatus status,
                   String street, String city, String zipcode) {
        this.id = id;
        this.name = name;
        this.phNumber = phNumber;
        this.email = email;
        this.tickets = tickets;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.status = status;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }
}
