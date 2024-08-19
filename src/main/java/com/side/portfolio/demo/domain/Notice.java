package com.side.portfolio.demo.domain;

import com.side.portfolio.demo.status.NoticeStatus;
import com.side.portfolio.demo.upload.UploadFile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

    //POSTED, DELETED
    @Enumerated(EnumType.STRING)
    private NoticeStatus status;

    @Builder
    public Notice(String title, String content, String img1, String img2,
                  String img3, String img4, String img5, String img6,
                  LocalDateTime createdDate, LocalDateTime modifiedDate,
                  NoticeStatus status) {
        this.title = title;
        this.content = content;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.img4 = img4;
        this.img5 = img5;
        this.img6 = img6;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.status = status;
    }
}
