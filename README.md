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

## 브랜치 전략
본 프로젝트는 Git Branch 전략을 사용하여 커밋 이력을 관리하였습니다. 다만, 프로젝트를 진행하던 중 보안에 민감한 정보들이 여러 시점의 커밋에 노출되는 사고가 발생하여 새로운 원격 리포지토리를 만들어 백업하고 본래의 원격 리포지토리를 삭제하여 기존의 커밋 이력이 남아있지 않습니다.
- **main**: 배포를 위한 브랜치입니다. 해당 브랜치에서는 코드 작성을 하지 않습니다.
- **develop**: 개발을 위한 브랜치입니다. 각각의 개발 브랜치들을 통합하여 main에 배포합니다.
- **feature**: 실질적인 개발을 수행하는 브랜치입니다. develop 브랜치로부터 분기하여 개발을 진행하며, 완료 후 develop 브랜치에 merge한 다음 해당 브랜치를 제거합니다.
- **hotfix**: 서버 배포 후 급히 수정할 사항이 있을 때 main 브랜치로부터 분기하여 코드를 수정합니다.
- **release**: 출시 대기 중인 브랜치입니다. 개발이 완료되었지만 아직 main에는 배포되지 않은 브랜치입니다. 본 프로젝트에선 사용하지 않았습니다.

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
| POST   | /reservations       | 예약 생성     |
| POST   | /reservations/{id}  | 예약 취소     |
| DELETE | /reservations/{id}  | 예약 삭제     |


**GUESTHOUSE**

| 메서드 | 엔드포인트  | 설명         |
|--------|-----------|---------------------|
| GET    | /guesthouses/{id}  | 게스트하우스 조회     |
| POST   | /guesthouses       | 게스트하우스 생성     |
| DELETE | /guesthouses/{id}  | 게스트하우스 삭제     |

**ROOM**

| 메서드 | 엔드포인트  | 설명         |
|--------|-----------|---------------------|
| GET    | /rooms/{id}  | 방 정보 조회     |
| POST   | /rooms       | 방 정보 생성     |
| DELETE | /rooms/{id}  | 방 정보 삭제     |

**ROOMINVENTORY**

| 메서드 | 엔드포인트  | 설명         |
|--------|-----------|---------------------|
| GET    | /roomsInventories/{id}  | 방 조회     |
| POST   | /roomsInventories       | 방 생성     |
| DELETE | /roomsInventories/{id}  | 방 삭제     |
