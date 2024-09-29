# SONNIM
최근 여행의 인기 숙박 상품으로 떠오르는 게스트 하우스 전용 예약 플랫폼

## 프로젝트 목표
- 야놀자와 같은 컨셉의 게스트하우스 예약 플랫폼 서버를 구축합니다.
- 객체지향적이고 재사용성이 높은 코드를 작성할 수 있도록 합니다.
- CI/CD를 구축하여 무중단 배포를 할 수 있도록 합니다.

## 사용 기술 및 환경
- JAVA17
- SPRING BOOT 3.2.5
- SPRING DATA JPA(Hibernate 6.4.4)
- MariaDB 10.11(AWS RDS)
- AWS EC2
- AWS ECR
- REDIS 3.2.4
- DOCKER
- GITHUB ACTIONS

## 서버 아키텍쳐
![diagram2](https://github.com/psh94/sonnim-server/assets/84213252/b2a4d60d-367d-43fa-98fb-865765c5e3ea)

## ERD

![erd](https://github.com/psh94/sonnim-server/assets/84213252/fd2b027e-49b3-4649-8410-ce0801b9e4d6)

## 중점사항
- 객체지향 원칙에 맞는 설계 및 코드 작성여부
- JWT를 이용한 Token 방식 로그인
- JWT Refresh 방식을 이용하여 보안 강화
- EC2상의 Redis Session에 Refresh Token 저장
- 로그인 여부에 따른 서비스 접근 허용/제한
- Member의 Role에 따른 서비스 접근 허용/제한
- Github Actions를 이용하여 CI/CD 구축
- Docker, AWS ECR을 이용한 무중단 배포 구현

## API

**MEMBER**

| 메서드 | 엔드포인트  | 설명         |
|---------|-----------|---------------------|
| GET     | /members/me  | 회원정보 조회        |
| POST    | /members/join| 회원 등록           |
| DELETE  | /members     | 회원 삭제           |
| POST    | /auth/login  | 로그인             |
| POST    | /auth/logout | 로그아웃            |


**RESERVATION**

| 메서드 | 엔드포인트  | 설명         |
|--------|-----------|---------------------|
| GET    | /reservations/{id}  | 예약 조회     |
| GET    | /members/{id}/reservations  | 회원별 예약 전체 조회     |
| POST   | /reservations       | 예약 생성     |
| POST   | /reservations/{id}  | 예약 취소     |
| DELETE | /reservations/{id}  | 예약 삭제     |


**GUESTHOUSE**

| 메서드 | 엔드포인트  | 설명         |
|--------|-----------|---------------------|
| GET    | /guesthouses/{id}  | 게스트하우스 조회     |
| GET    | /guesthouses/searchRegion  | 지역별 게스트하우스 조회     |
| GET    | /guesthouses/searchWord  | 게스트하우스 검색     |
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
