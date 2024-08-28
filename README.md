<div align=center>
	<img src="https://github.com/user-attachments/assets/a1882efd-4c4e-4de4-9960-73d2423ba5d4" />
</div>




<br>
<br>
<br>
<br>

## 링크
🔗[프로젝트 둘러보기](http://13.209.103.55:8080/)<br>
🔖[블로그](https://develop-log-book.tistory.com/)<br>
💌[메일](mailto:kimkitae1208@gmail.com)<br>

<!--⛓️[노션](https://www.notion.so/ventovox/Ks-Notion-Page-54ec277bf8a944e78b824929a342e984?pvs=4)<br>-->

<br>
<br>

## 기획 의도
미국의 대학들은 워낙 넓은 캠퍼스에 자리 잡고 있어서, 보통 차나 자전거를 타고 다닐 정도예요. <br>
학교 내에 큰 카페테리아도 있긴 하지만, 들어와 있는 식당들이 그리 다양하지는 않죠.<br>
대학에서는 학생들이 다양한 음식을 먹을 수 있도록 식권을 나눠주지만, 급식 퀄리티가 아주 뛰어나지는 않은 편이죠.<br>
같은 메뉴가 자주 나오는 경우도 있고, 식사 시간대가 되면 사람들이 몰려서 식당이 복잡해지기까지 하죠!<br>
학교 근처 외부 레스토랑에서도 식권을 사용할 수 있긴 한데, 넓은 캠퍼스를 벗어나서 나가기가 좀 번거로울 때도 많아요.<br>
배달을 시키고 싶어도, 미국의 배달 문화가 한국만큼 발달해 있지 않은 데다가 팁도 신경써야 해서 쉽지만은 않답니다.

<br>

>1. 미국 대학의 넓은 캠퍼스
>2. 높지 않은 퀄리티의 급식
>3. 반복되는 메뉴
>4. 혼잡한 식당
>5. 부담되는 배달 문화!

<br>
   
이러한 점들을 해결하고자 다음의 아이디어를 떠올리게 됐어요<br><br>

**학교 내부 특정 장소에 "_배달 거점 + 키오스크 + 자판기(이하 키오스크)_"를 설치하자!**

<br>

>1. 키오스크 이용 고객은 학교가 관리하는 사람들(교직원, 학생 등..)을 대상으로 합니다
>2. 학교가 주문을 넣으면 식당에서 확인하고 조리를 시작합니다
>3. 조리된 음식은 식당에서 학교 내부의 여러 키오스크들까지 배달됩니다
>4. 키오스크 이용 고객들은 식권으로 사용하여 가까운 키오스크에서 원하는 음식을 가져갑니다

<br>

**+ 대학뿐만 아니라 회사들이 모여있는 산업센터까지도 활용하고자 해요!**

<br>
<br>
<br>

## 목차
- [개발 경과](#개발-경과)
- [프로젝트 설명](#프로젝트-설명)
- [트러블 슈팅](#트러블-슈팅)
- [기술 스택](#기술-스택)


<br>
<br>


## 개발 경과
<details>
  <summary><b>24년 7월</b></summary>
  
  <div markdown="1">
    <ul>
      <li>
        <details>
          <summary><b>24.07.25 Thu</b></summary>
          <div markdown="1">
            <ol>
              <li>회사소개 페이지 추가</li>
              <li>메인 이미지 변경</li>
              <!-- <img src="./docs/주요_기능/포토스팟_콜렉션/1.gif" width=70%> -->
            </ol>
          </div>
        </details>
      </li>
      <li>
        <details>
          <summary><b>24.07.26 Fri</b></summary>
          <div markdown="1">
            <ol>
              <li>마스터 계정 생성
                <ul>
                  <li>마스터만 팀 목록을 볼 수 있도록 수정</li>
                  <li>마스터만 상품을 수정할 수 있도록 수정</li>
                </ul>
              </li>
              <li>판매자는 판매자 목록을 볼 수 없도록 수정</li>
              <li>상품 목록 페이지 및 페이징 기능 구현중</li>
              <li>팀 목록, 판매자 목록 페이지 및 페이징 기능 구현중</li>
            </ol>
          </div>
        </details>
      </li>
    </ul>
  </div>
</details>


<details>
  <summary><b>24년 8월</b></summary>
  
  <div markdown="1">
    <ul>
      <li>
	<details>
	  <summary><b>24.08.02 Fri</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>상품 목록 페이지 및 페이징 기능 구현</li>
	      <li>개발 DB, 테스트 DB의 분리</li>
	    </ol>
	  </div>
	</details>
      </li>
      <li>
	<details>
	  <summary><b>24.08.03 Sat</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>팀 목록 페이지 및 페이징 기능 구현</li>
	      <li>판매자 목록 페이지 구현</li>
	      <li>판매자 목록 페이징 기능 구현중</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.05 Mon</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>판매자 목록 페이징 기능 구현</li>
	      <li>상품, 판매자, 팀 목록 현재 페이지에 대한 CSS 추가</li>
	      <li>주문 추가, 취소 기능 구현</li>
	      <li>팀 id를 조건으로 하여 장바구니가 조회되도록 구현</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.06 Tue</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>장바구니 기능 구현</li>
	      <li>장바구니 페이지 구현</li>
	      <li>주문 기능 구현중
	      <ul>
		      <li>장바구니 정보 조회</li>
		      <li>장바구니 정보를 이용해 주문상세 데이터 생성</li>
		      <li>장바구니 정보 가공하여 최종 주문 금액, 최종 주문 개수 계산</li>
	      </ul></li>
	      <li>주문 페이지 구현중</li>
		    <li>상품 목록에서 CLOSED 상태의 상품은 수량 및 담기 버튼 비활성화</li>
		    <li>상품 목록에서 담을 수량 변경 및 담기 시, 0보다 작은 수량을 입력하는 경우에 대한 유효성 검사</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.07 Wed</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>장바구니 상품 제거 기능 구현</li>
	      <li>장바구니 목록 합산 가격 및 수량 출력 기능 추가</li>
	      <li>주문 기능 구현</li>
	      <li>주문 페이지 구현중</li>
		    <li>담으려는 상품이 이미 장바구니에 있는 경우, 중복 담기 제한</li>
		    <li>0개 이하의 상품을 담는 경우에 대한 유효성 검사 수정</li>
		    <li>장바구니 페이징 기능 제거</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.08 Thu</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>주문 기능 테스트</li>
	      <li>주문 페이지 구현</li>
	      <li>판매자 목록 페이징 기능 구현중</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.10 Sat</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>회원가입 시, City, Street, Zipcode 입력하도록 수정</li>
	      <li>주문 시, 상품 엔티티의 특정 필드(재고, 수정일)만 변경되도록 @DynamicUpdate 적용</li>
	      <li>팀 데이터의 STREET, CITY, ZIPCODE 필드 초기화</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.11 Sun</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>가격의 타입을 float -> BigDecimal로 변경
	      <ul>
		      <li>Update 쿼리 실행 시, 값의 유실이 발생하기 때문</li>
	      </ul></li>
	      <li>@DynamicUpdate 제거 및 변경 감지를 적용한 상품 엔티티 전체 필드 Update 쿼리 확인</li>
	      <li>장바구니에 상품을 담을 때, setScale(2, RoundingMode.CEILING)하고 주문 시, 해당 값 그대로 사용</li>
		    <li>팀 목록 페이지에서 Street, City, Zipcode 출력, remark 미출력하도록 수정</li>
		    <li>컨트롤러, 서비스(엔티티), 레포지토리 간 메서드 명명 규칙 설정 및 이에 맞게 메서드 명 변경</li>
		    <li>ItemRepository -> ItemJpaRepository / MemberRepository -> MemberJpaRepository 전체 대체</li>
		    <li>주문 목록 조회 기능, 페이지, 자세히 버튼 추가</li>
		    <li>세션 내 정보가 master, team인 경우에만 상품 목록 페이지에서 담기 버튼이 보이도록 수정</li>
		    <li>주문 내역에서 주문 ID, 배송 상태가 출력되도록 수정</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.12 Mon</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>상품 담기 버튼 클릭 시, 장바구니 이동 분기 추가</li>
	      <li>장바구니 제거 버튼 -> 비우기 버튼으로 수정, 모두 비우기 버튼 및 기능 추가</li>
	      <li>주문 시, 기존 장바구니를 비우도록 수정</li>
		    <li>상세 주문 취소 기능 추가</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.13 Tue</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>상품 등록 폼과 상품 수정 폼 분리</li>
	      <li>Bean Validation 적용</li>
	      <li>상품 등록 및 수정 Validation 기능 추가</li>
		    <li>상품 등록 및 수정 테스트</li>
		    <li>MainController에서 LogInController로 로그인/로그아웃 기능 분리</li>
		    <li>세션 타임아웃 30분 설정</li>
		    <li>로그용 인터셉터 추가</li>
		    <li>로그인용 인터셉터 추가</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.14 Wed</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>인터셉터 이름 수정
	      <ul>
		<li>로그용 인터셉터 ; Log_Interceptor</li>
		<li>로그인용 인터셉터 ; LogIn_Interceptor</li>
	      </ul></li>
	      <li>로그인 인터셉터 구현 완료</li>
	      <li>로그인 인터셉터 테스트</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.15 Thu</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>POS기로 상품 정보를 보내는 API 구현</li>
	      <li>H2 -> MySQL로 사용 DB 변경</li>
	      <li>AWS RDS MySQL 생성</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.19 Mon</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>빌드 파일 재배포 및 정상 실행 확인</li>
	      <li>메인 페이지 null 출력 부분 해결</li>
	      <li>MySQL workbench를 통한 초기 데이터 삽입</li>
	      <li>공지사항 목록 페이지 구현</li>
		    <li>공지사항 목록 페이지 구현</li>
		    <li>공지사항 이미지 파일 업로드 기능 구현</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.20 Tue</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>공지사항 삭제 기능 구현</li>
	      <li>공지사항 삭제 시, 연관 파일 삭제 구현</li>
	      <li>공지사항 수정 폼 객체(NoticeUpdateForm) 생성</li>
	      <li>상품 수정 페이지 구현</li>
		    <li>파일 업로드 관련 서비스 이름 변경
		    <ul>
			    <li>FileUtil -> FileService</li>
		    </ul></li>
		    <li>파일 업로드 정보 엔티티 이름 변경
		    <ul>
			    <li>UploadFile -> FileNameTable 변경</li>
		    </ul></li>
		    <li>로그인용 인터셉터에 제외 경로 추가
		    <ul>
			    <li>공지사항 목록(notice-list) 경로 제외</li>
		    </ul></li>
		    <li>공지사항 수정 기능 구현</li>
		    <li>공지사항 파일에 대한 업로드/수정/삭제 구현</li>
	    </ol>
	  </div>
	</details>
	</li>
      <li>
	<details>
	  <summary><b>24.08.21 Wed</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>순수 JPA 레포지토리를 스프링 데이터 JPA로 대체
	      <ul>
		<li>TeamRepository -> TeamJpaRepository</li>
		<li>SellerRepository -> SellerJpaRepository</li>
	      </ul></li>
	      <li>상품 상세 페이지 구현</li>
	      <li>상품 목록에서 "상품 ID", "상품명" 클릭 시, 상품 상세 페이지로 이동하도록 구현</li>
		    <li>상품 수정 페이지, 상품 수정 기능 구현</li>
		    <li>공지 수정 시, "제목", "본문" 수정 사항이 적용되도록 수정</li>
		    <li>상품 상세 페이지에서 "CLOSED" 상태인 상품은 가격에 취소선, 담기 비활성화, 안내 문구가 나타나도록 수정</li>
	    </ol>
	  </div>
	</details>
     </li>
	    <li>
	<details>
	  <summary><b>24.08.22 Thu</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>README.md 업데이트</li>
	      <li>AWS EC2와 RDS의 동일하지 않은 가용영역으로 인한 과금 내역 확인</li>
	      <li>AWS RDS 리전 변경 및 재생성(Sydney -> Seoul)</li>
		<li>MySQL Workbench에서 EC2 SSH 터널링을 통한 접속</li>
	    </ol>
	  </div>
	</details>
     </li>
     <li>
	<details>
	  <summary><b>24.08.23 Fri</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>장바구니 리스트, 주문 리스트, 주문 상세에서 상품 등록 버튼 미동작 오류 수정
	      <ul>
		<li>ajax 비동기 post 전송 코드 추가</li>
	      </ul></li>
	      <li>Address 엔티티 기본 생성자 -> @NoArgsConstructor 수정</li>
	      <li>Team 엔티티와 Kiosk 엔티티 간 연관관계 수정</li>
		<li>팀, 판매자 상세 클릭 시, 임시 경고창을 띄우도록 수정</li>
	    </ol>
	  </div>
	</details>
     </li>
     <li>
	<details>
	  <summary><b>24.08.24 Sat</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>주문 상태 ACCEPTED 추가</li>
	      <li>주문 상태가 ACCEPTED인 경우, 주문 취소 불가하도록 수정</li>
	      <li>판매자가 본인 가게에 대한 주문 내역만 볼 수 있도록 수정</li>
	    </ol>
	  </div>
	</details>
     </li>
     <li>
	<details>
	  <summary><b>24.08.26 Mon</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>QueryDSL 설정 추가</li>
	      <li>주문 상태 REJECTED 추가</li>
	      <li>판매자 전용 주문된 목록 페이지 추가</li>
		    <li>올바른 접근이 아닌 경우, 메시지를 띄우도록 Message 객체 추가</li>
		    <li>해당 판매자가 아닌 사용자의 주문된 목록 페이지 접근 제한</li>
		    <li>상품 상세 페이지에서 판매자는 장바구니 담기 불가능하도록 수정</li>
		    <li>주문 상품 생성 시, 생성일 세팅되도록 수정</li>
		    <li>"제휴된 판매자"에 대한 기능을 위한 PartnerSeller 객체 추가</li>
	    </ol>
	  </div>
	</details>
     </li>
     <li>
	<details>
	  <summary><b>24.08.27 Thu</b></summary>
	  <div markdown="1">
	    <ol>
	      <li>판매자 제휴 맺기 기능 추가</li>
	      <li>판매자 제휴 맺기 테스트 추가</li>
	      <li>올바른 접근이 아닌 경우에 대한 코드를 LogInService로 Extract</li>
	      <li>제휴 판매자 목록 접근이 올바르지 않은 경우에 대한 처리 추가</li>
	      <li>헤더에 "제휴 판매자 목록" 버튼 추가</li>
	      <li>sellerOrderedItems -> sellerOrders 이름 변경</li>
	      <li>"전체 판매자 목록"에 "제휴 맺기" 버튼 추가</li>
	      <li>PartnerSeller -> Partner 이름 변경</li>
	      <li>제휴 상태 객체 PartnerStatus 추가</li>
	      <li>"제휴 판매자 조회"를 위한 객체 PartnerDto 추가</li>
	      <li>"제휴 판매자 목록" 페이지 추가</li>
	      <li>접속한 팀과 제휴된 "제휴 판매자 조회" 기능 추가</li>
	    </ol>
	  </div>
	</details>
     </li>
    </ul>
  </div>
</details>


<br>
<br>

## 프로젝트 설명
<details>
  <summary><b>DB 설계</b></summary>
  <div markdown="1">
    <ul>
	<p align="center">
		<img src="https://github.com/user-attachments/assets/bd08599f-dd50-4dce-95c0-2458f620df24" />
	</p>
    </ul>
  </div>
</details>

<details>
  <summary><b>Master-Flow</b></summary>
  <div markdown="1">
    <ul>
      	<p align="center">
		<img src="https://github.com/user-attachments/assets/a449eb4b-a69b-4188-b7b6-4af778a048c9" width="1000" height="500"/>
	</p>
    </ul>
  </div>
</details>

<details>
  <summary><b>Team-Flow</b></summary>
  <div markdown="1">
    <ul>
      	<p align="center">
		<img src="https://github.com/user-attachments/assets/9d0c259c-41ea-487d-972c-815e5862bda5" width="950" height="500"/>
	</p>
    </ul>
  </div>
</details>

<details>
  <summary><b>Seller-Flow</b></summary>
  <div markdown="1">
    <ul>
      	<p align="center">
		<img src="https://github.com/user-attachments/assets/398039d0-a62e-48e0-8768-609fbb5daf88" width="850" height="500"/>
	</p>
    </ul>
  </div>
</details>

<br>
<br>

## 트러블 슈팅
<details>
  <summary><b>임시</b></summary>
  <div markdown="1">
    <ul>
      <li>이미지</li>
    </ul>
  </div>
</details>
<br>
<br>

## 기술 스택
<!-- <div align=center>
	<h3>⭐ Tech</h3>
	<p>📚 Languages & Framworks 📓</p>
</div> -->
<div align="left">
	<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white" />
	<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white" />
	<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white" />
	<br>
	<img src="https://img.shields.io/badge/Bootstrap-7952B3?style=flat&logo=Bootstrap&logoColor=white" />
	<img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat&logo=Thymeleaf&logoColor=white" />
	<br>
	<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Jameson&logoColor=white" />
	<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white" />
	<img src="https://img.shields.io/badge/Hibernate-59666C?style=flat&logo=Hibernate&logoColor=white" />
<!-- 	<img src="https://img.shields.io/badge/Oracle%20SQL-F80000?style=flat&logo=Oracle&logoColor=white" />
	<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white" /> -->
</div>
<!-- <div align=center>
	<p>🔨 Tools</p>
</div> -->
<div align=left>
	<img src="https://img.shields.io/badge/IntelliJ-512BD4?style=flat&logo=intellijidea&logoColor=white" />
	<img src="https://img.shields.io/badge/Tomcat-F8DC75?style=flat&logo=ApacheTomcat&logoColor=white" />
<!-- 	<img src="https://img.shields.io/badge/AWS-232F3E?style=flat&logo=AmazonAWS&logoColor=white" /> -->
	<img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white" />
</div>

<!--
<div align=center>
	<p>📞 Contact </p>
</div>
<div align=center>
	<a href="https://develop-log-book.tistory.com/">
		<img src="https://img.shields.io/badge/Blog-FF9800?style=flat&logo=tvtime&logoColor=white" />
	</a>
	<a href="mailto:kimkitae1208@gmail.com">
		<img src="https://img.ㅃshields.io/badge/Gmail-EA4335?style=flat&logo=Gmail&logoColor=white" />
	</a>
	<a href="https://www.notion.so/kimkitae1208/Python-7b471b62323749819c2b047024f037de?pvs=4">
		<img src="https://img.shields.io/badge/Notion-000000?style=flat&logo=Notion&logoColor=white" />
	</a>
	<br>
</div>
-->


<br>
<br>
<br>
<br>
