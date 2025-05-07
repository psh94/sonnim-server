# 📚 SONNIM
최근 여행의 인기 숙박 상품으로 떠오르는 게스트 하우스 전용 예약 플랫폼

## 📖 개요
- 프로젝트 명 : SONNIM
- 개발 인원 : 1명
- 개발 기간 : 2024.03 ~ 2024.07

## 📖 사용 기술 및 환경
- JAVA17
- SPRING BOOT 3.2.5
- SPRING DATA JPA(Hibernate 6.4.4)
- MariaDB 10.11(AWS RDS)
- AWS EC2
- AWS ECR
- REDIS 3.2.4
- DOCKER
- GITHUB ACTIONS

## 📖 프로젝트 목표
- 야놀자와 유사한 컨셉의 게스트하우스 예약 플랫폼용 REST API 서버를 구축합니다.
- 객체지향적인 설계를 바탕으로, 재사용성과 유지보수성이 높은 코드를 작성합니다.
- CI/CD 파이프라인을 구축하여, 무중단 배포가 가능하도록 합니다.


## 📖 서버 아키텍쳐
![diagram2](https://github.com/psh94/sonnim-server/assets/84213252/b2a4d60d-367d-43fa-98fb-865765c5e3ea)

## 📖 ERD
![1](https://github.com/user-attachments/assets/34fb36d7-79cd-4f2b-9181-298efce38d18)

## 📖 프로토타입
https://www.figma.com/proto/0c7UO75qnPNAdkJml748bZ/Untitled?node-id=0-1&t=xVOZMgmbYDsomvjh-1


## 📖 중점사항
- 객체지향 원칙에 맞는 설계 및 코드 작성여부
- JWT를 이용한 Token 방식 로그인
- JWT Refresh 방식을 이용하여 보안 강화
- EC2상의 Redis Session에 Refresh Token 저장
- 결제 시, 날짜별 재고 관리
- GitHub Actions, Docker, AWS ECR을 활용하여 CI/CD 파이프라인을 구축
- AWS EC2 환경에서 무중단 배포를 구현하여 서비스 중단 없이 신규 버전을 적용

## 📖 개선사항(아쉬운 점)
- N+1 문제 방지를 위해 findAll()을 피하고 findById()와 같은 메서드를 활용하여 N+1 문제를 피하였으나 다른 선택사항을 충분히 고려하지 못했음
  
(WIKI : https://github.com/psh94/sonnim-server/wiki/N%EF%BC%8B1-%EB%AC%B8%EC%A0%9C-%EA%B0%9C%EC%84%A0%EC%82%AC%ED%95%AD)


## 📖 API

**MEMBER**

| 메서드 | 엔드포인트  | 설명         |
|---------|-----------|---------------------|
| POST    | /signup      | 회원 등록           |
| GET     | /members/me  | 회원정보 조회        |
| DELETE  | /members     | 회원 삭제           |
| POST    | /auth/login  | 로그인             |
| POST    | /auth/logout | 로그아웃            |
| POST    | /auth/refresh | 리프레시 토큰 발급   |


**RESERVATION**

| 메서드 | 엔드포인트  | 설명         |
|--------|-----------|---------------------|
| GET    | /reservations/{id}  | 예약 조회     |
| GET    | /members/{memberId}/reservations  | 회원별 예약 전체 조회     |
| POST   | /reservations       | 예약 생성     |
| POST   | /reservations/{id}  | 예약 취소     |
| DELETE | /reservations/{id}  | 예약 삭제     |


**GUESTHOUSE**

| 메서드 | 엔드포인트  | 설명         |
|--------|-----------|---------------------|
| GET    | /guesthouses/{id}  | 게스트하우스 조회     |
| GET    | /guesthouses/region/{regionCode}  | 지역별 게스트하우스 조회     |
| GET    | /guesthouses/search | 게스트하우스 검색     |
| POST   | /guesthouses       | 게스트하우스 생성     |
| DELETE | /guesthouses/{id}  | 게스트하우스 삭제     |

**ROOM**

| 메서드 | 엔드포인트  | 설명         |
|--------|-----------|---------------------|
| GET    | /rooms/{id}  | 방 정보 조회     |
| GET    | /guesthouse/{guesthouseId}/rooms  | 게스트하우스별 방 정보 전체 조회     |
| POST   | /rooms       | 방 정보 생성     |
| DELETE | /rooms/{id}  | 방 정보 삭제     |

**ROOMINVENTORY**

| 메서드 | 엔드포인트  | 설명         |
|--------|-----------|---------------------|
| GET    | /roomsInventories/{id}  | 방 조회     |
| POST   | /roomsInventories       | 방 생성     |
| DELETE | /roomsInventories/{id}  | 방 삭제     |

**PAYMENT**

| 메서드 | 엔드포인트  | 설명         |
|--------|-----------|---------------------|
| POST    | /payments | 결제 생성      |
| POST   | /payments/complete/{paymentId} | 결제 완료 처리      |
| POST   | /payments/cancel/{paymentId} | 결제 취소 처리      |
| GET    | /members/{memberId}/payments | 회원별 결제내역 조회      |
| GET    | /payments/{paymentId} |  결제내역 단건 조회      |


**REVIEW**

| 메서드 | 엔드포인트  | 설명         |
|--------|-----------|---------------------|
| POST    | /guesthouse/{guesthouseId}/reviews | 게스트하우스 리뷰 생성      |
| GET    | /guesthouse/{guesthouseId}/reviews | 게스트하우스별 리뷰 조회      |
| POST    | /reviews/{id} | 리뷰 단건 조회      |
| POST    | /member/{memberId}/reviews | 회원별 리뷰 조회      |
| DELETE    | /reviews/{id} | 리뷰 삭제      |










