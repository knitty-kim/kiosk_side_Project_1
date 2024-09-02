package com.side.portfolio.demo.dto.condition;

import com.side.portfolio.demo.status.TeamStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeamSearchCond {

    private Long id;
    private String name;
    private String phNumber;
    private Integer tickets;
    private String email;
    private TeamStatus status;
    private String street;
    private String city;
    private String zipcode;

}
