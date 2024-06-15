<h1>SONNIM</h1>

<h3>1) 개요</h3>
최근 여행의 인기 숙박 상품으로 떠오르는 게스트 하우스 전용 예약 플랫폼
</br>
</br>

<h3>2) 사용 기술 및 환경</h3>

- JAVA17
- SPRING BOOT 3.2.5
- SPRING DATA JPA
- AWS RDS (Maria DB)
- AWS EC2
- AWS ECR
- REDIS
- DOCKER
- GITHUB ACTIONS


</br>

<h3>3) 서버 아키텍쳐</h3>

![diagram2](https://github.com/psh94/sonnim-server/assets/84213252/b2a4d60d-367d-43fa-98fb-865765c5e3ea)

</br>

<h3>4) ERD</h3>

![erd](https://github.com/psh94/sonnim-server/assets/84213252/fd2b027e-49b3-4649-8410-ce0801b9e4d6)

</br>

<h3>4) 중점사항</h3>
- 객체지향 원칙에 맞는 설계 및 코드 작성
- 로그인 여부에 따른 서비스 접근 허용/제한
- Member의 Role에 따른 서비스 접근 허용/제한
- Redis Session을 이용하여 JWT Refresh 방식의 로그인 구현
- Docker Hub를 이용하여 CI/CD 구축
- Docker, ECR을 이용한 무중단 배포 구현
