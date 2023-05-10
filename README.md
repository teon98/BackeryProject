# 실시간 빵 예약 프로그램
신한DS 금융SW 아카데미 1차 개인프로젝트 
## 목차
- [개발목적](#개발목적)
- [개발환경](#개발환경)
- [시스템 개요](#시스템개요)
- [테이블구조](#테이블구조)
- [시스템 구조](#시스템구조)
- [프로그램 화면](#프로그램화면)
- [예외처리 정리](#예외처리-정리)
- [중요 소스코드 및 학습내용](#중요소스코드-및-학습내용)
- [추가 구현한 기능](#추가적으로-알게-된-내용)
- [마무리](#마무리)
## 개발목적
- 고객이 갓 구운 빵의 정보를 실시간으로 조회하고, 빵을 예약/예약 조회/예약 수정/예약 취소하는 기능을 구현한다.
- 이를 통해 JDBC를 이용해 자바와 오라클 DB를 연결하고, MVC 패턴으로 프로그램을 구성하는 것에 대해 학습한다.
## 개발환경
1. 사용언어: JAVA, SQL
2. 사용 툴: eclipse, Oracle SQL Developer
3. 데이터베이스: Oracle 11g Express Edition
## 시스템개요
<img src="https://user-images.githubusercontent.com/49816869/230572171-f01be023-ed55-4170-a28c-89006637334b.jpg" width="80%" />

## 테이블구조
<img src="https://user-images.githubusercontent.com/49816869/230572377-e1ddfeeb-d58f-48fb-9dec-6c61bf0cb642.jpg" width="80%" />

## 시스템구조
<img src="https://user-images.githubusercontent.com/49816869/230572493-1cea6586-df9d-4c15-9e34-c6aa055b152e.jpg" width="80%" />

## 프로그램화면
<img src="https://user-images.githubusercontent.com/49816869/230571992-24359cbe-3b4a-44e4-9dec-3da89780eb1b.gif" width="80%" />

## 예외처리 정리
프로젝트를 진행하며 처리했던 예외처리 목록들을 정리하였습니다.
<img src="https://user-images.githubusercontent.com/49816869/230573353-a8d5e858-d84a-4d19-bf1e-eebcf2feb51e.jpg" width="80%" />

## 중요소스코드 및 학습내용
### 1. 트리거
<img src="https://user-images.githubusercontent.com/49816869/230574415-bfa44744-6483-42c9-8229-60f8b1a8ccd7.jpg" width="80%" />

### 2. JDBC의 자원연결과 반납
<img src="https://user-images.githubusercontent.com/49816869/230574734-bedef6ce-69f1-47e6-9c5a-662cd90022c0.jpg" width="80%" />

### 3. JDBC의 SQL문 처리
1) 일반적인 SQL문 처리
<img src="https://user-images.githubusercontent.com/49816869/230574960-a3b9c2b3-07f1-4bcb-a462-2e75bb128fe0.jpg" width="80%" />
2) 매개변수가 있는 SQL문 처리
<img src="https://user-images.githubusercontent.com/49816869/230575072-f9fa8420-1696-4523-b099-5048f12d0fb8.jpg" width="80%" />
3) 프로시저를 처리하는 SQL문 처리
<img src="https://user-images.githubusercontent.com/49816869/230575161-96d6151a-4ecc-441f-aa95-7645349a8f1c.jpg" width="80%" />

### 4.MVC 패턴
<img src="https://user-images.githubusercontent.com/49816869/230576017-da561888-045d-4191-9ccd-92b5d61128e8.jpg" width="80%" />

## 추가적으로 알게 된 내용
JDBC에서 시분초도 표시하고 싶으면 Date 클래스가 아닌 TimeStamp 클래스를 사용하면 됨을 알게되었습니다.
<img src="https://user-images.githubusercontent.com/49816869/230575784-c8090beb-3a9f-4ee2-ab36-17f8420d57c7.jpg" width="80%" />

## 마무리
### 추가적으로 구현하고 싶은 기능
1) 주문 수량 변경 시 처음 제공된 빵의 수량보다 더 많은 수량으로 변경 될 경우(잘못된 수량 변경 요청시) 수량이 변경되지 못하도록 하는 조건 추가
2) 다른 날에 장사를 시작할 시 Orders 테이블과, Todaybackerys 테이블이 비워지는 기능 추가
<img src="https://github.com/teon98/BackeryProject/assets/49816869/4075b7d4-7106-487d-914a-5817c5b13643" width="80%" />
<img src="https://github.com/teon98/BackeryProject/assets/49816869/43475f08-05a3-475c-8f95-605d91b0164e" width="80%" />

### 아쉬웠던 점
Orders 테이블과 Todaybackerys 테이블을 join 하여 쓸 일이 많았는데, 프로젝트를 마무리 한 후에 보니 뷰를 만들어 이용해볼껄 하는 아쉬움이 남았습니다. <br />
<img src="https://github.com/teon98/BackeryProject/assets/49816869/5e145fc3-0889-45fd-ba78-0c71f74148e9" width="80%" />





