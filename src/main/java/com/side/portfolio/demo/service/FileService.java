//package com.side.portfolio.demo.service;
//
//import com.side.portfolio.demo.domain.Notice;
//import com.side.portfolio.demo.repository.SavedFileJpaRepository;
//import com.side.portfolio.demo.repository.UploadFileJpaRepository;
//import com.side.portfolio.demo.upload.UploadFile;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class FileService {
//
//    private final SavedFileJpaRepository fileRepository;
//
//    public void createFile(Notice notice, List<MultipartFile> files) {
//
//    }
//
//    public void deleteFile(Long fileId) {
//        fileRepository.deleteById(fileId);
//    }
//
//}
