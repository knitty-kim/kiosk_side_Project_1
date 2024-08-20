package com.side.portfolio.demo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileNameTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uploadFileName;
    private String uuidFileName;

    @Builder
    public FileNameTable(String uploadFileName, String uuidFileName) {
        this.uploadFileName = uploadFileName;
        this.uuidFileName = uuidFileName;
    }
}
