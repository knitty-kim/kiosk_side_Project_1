package com.side.portfolio.demo.service;

import com.side.portfolio.demo.domain.Item;
import com.side.portfolio.demo.domain.Notice;
import com.side.portfolio.demo.domain.Seller;
import com.side.portfolio.demo.repository.ItemJpaRepository;
import com.side.portfolio.demo.repository.NoticeJpaRepository;
import com.side.portfolio.demo.status.ItemStatus;
import com.side.portfolio.demo.status.NoticeStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeJpaRepository noticeJpaRepository;

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
