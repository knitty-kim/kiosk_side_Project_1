package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.upload.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileJpaRepository extends JpaRepository<UploadFile, Long> {

    UploadFile findByUuidFileName(String uuidFileName);
}
