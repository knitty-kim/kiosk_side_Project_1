package com.side.portfolio.demo.repository;

import com.side.portfolio.demo.domain.FileNameTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileNameTableJpaRepository extends JpaRepository<FileNameTable, Long> {

    FileNameTable findByUuidFileName(String uuidFileName);
    void deleteByUuidFileName(String uuidFileName);

}
