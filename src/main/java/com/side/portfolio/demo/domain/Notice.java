package com.side.portfolio.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private String title;
    private String content;
    private int viewCnt;

    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String img6;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public Notice(String title, String content, int viewCnt,
                  LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.title = title;
        this.content = content;
        this.viewCnt = viewCnt;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
