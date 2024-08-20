package com.side.portfolio.demo.service;

import com.side.portfolio.demo.repository.FileNameTableJpaRepository;
import com.side.portfolio.demo.domain.FileNameTable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileNameTableJpaRepository fileRepository;

    @Value("${file.dir}")
    private String fileDir;

    public String getFullDir(String filename) {
        return fileDir + filename;
    }

    public String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public String createFileNameWithUUID(String fileName) {
        String ext = getExt(fileName);
        return UUID.randomUUID().toString() + "." + ext;
    }

    @Transactional
    public FileNameTable createFile(MultipartFile multipartFile) throws IOException{
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String fileNameWithUUID = createFileNameWithUUID(originalFilename);
        File file = new File(getFullDir(fileNameWithUUID));
        multipartFile.transferTo(file);

        fileRepository.save(new FileNameTable(originalFilename, fileNameWithUUID));
        return fileRepository.findByUuidFileName(fileNameWithUUID);
    }

    @Transactional
    public void deleteFile(String uuid) {
        String fullDir = getFullDir(uuid);
        File file = new File(fullDir);
        if (file.exists()) {
            file.delete();
        }
    }

    public FileNameTable findTableById(Long fileId) {
        return fileRepository.findById(fileId).get();
    }

    public FileNameTable findTableByUuid(String uuid) {
        return fileRepository.findByUuidFileName(uuid);
    }

    @Transactional
    public void deleteTableByUuidFileName(String uuid) {
        fileRepository.deleteByUuidFileName(uuid);
    }
}
