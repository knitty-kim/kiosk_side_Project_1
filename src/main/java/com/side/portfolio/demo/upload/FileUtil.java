package com.side.portfolio.demo.upload;

import com.side.portfolio.demo.repository.UploadFileJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUtil {

    private final UploadFileJpaRepository fileRepository;

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

    public UploadFile createFile(MultipartFile multipartFile) throws IOException{
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String fileNameWithUUID = createFileNameWithUUID(originalFilename);
        File file = new File(getFullDir(fileNameWithUUID));
        multipartFile.transferTo(file);

        fileRepository.save(new UploadFile(originalFilename, fileNameWithUUID));
        return fileRepository.findByUuidFileName(fileNameWithUUID);
    }

    public void deleteFile(String fileName) {
        String fullDir = getFullDir(fileName);
        File file = new File(fullDir);
        if (file.exists()) {
            file.delete();
        }
    }

    public UploadFile findById(Long fileId) {
        return fileRepository.findById(fileId).get();
    }
}
