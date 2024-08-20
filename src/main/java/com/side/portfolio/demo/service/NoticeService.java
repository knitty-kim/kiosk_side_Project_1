package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Notice;
import com.side.portfolio.demo.repository.NoticeJpaRepository;
import com.side.portfolio.demo.repository.FileNameTableJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeJpaRepository noticeJpaRepository;
    private final FileNameTableJpaRepository fileJpaRepository;
    private final FileService fileService;

    @Transactional
    public void createNotice(Notice notice) {
        noticeJpaRepository.save(notice);
    }

    public List<Notice> findAll() {
        return noticeJpaRepository.findAll();
    }

    public Page<Notice> findAll(Pageable pageable){
        Page<Notice> result = noticeJpaRepository.findAll(pageable);
        return result;
    }

    public Notice findById(Long noticeId) {
        return noticeJpaRepository.findById(noticeId).get();
    }

    @Transactional
    public void deleteById(Long noticeId) {
        Notice notice = findById(noticeId);

        //순회할 필드 이름 목록
        String[] fieldNames = {"Img1", "Img2", "Img3", "Img4", "Img5", "Img6"};

        try {
            //Java Reflection API
            for (String fieldName : fieldNames) {
                //notice.getImgX() 메서드 동적 호출
                Method getter = notice.getClass().getMethod("get" + fieldName);
                String uuidImg = (String) getter.invoke(notice);

                //필드 값이 null 이 아닌 경우 삭제
                if (uuidImg != null) {
                    fileService.deleteFile(uuidImg);  // 실제 파일 삭제
                    fileJpaRepository.deleteByUuidFileName(uuidImg);   // FileNameTable 삭제
                }
            }

            //notice 엔티티 삭제
            noticeJpaRepository.deleteById(noticeId);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //공지 수정
//    @Transactional
//    public void updateNotice(Long id, String name, NoticeStatus status) {
//        //빌더 패턴을 사용중이나,
//        //수정 시에는 기존 객체의 필드를 SET 해야하므로 setUp.. 메서드 사용
//        Notice notice = noticeJpaRepository.findById(id).get();
//        notice.setUpName(name);
//        notice.setUpPrice(price);
//        notice.setUpQty(qty);
//        notice.setUpStatus(status);
//        notice.setUpSeller(seller);
//    }

}
