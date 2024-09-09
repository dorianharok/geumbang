# 💰금방 프로젝트
## 🏁 프로젝트 소개
원티드 프리온보딩 3주차 과제(금방주식회사 백엔드 입사과제)입니다.

## 📚기술 스택
<div align=center> 
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white"> 
  <img src="https://img.shields.io/badge/springboot%203.3.3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
</br>
  <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white">
</br>
  <img src="https://img.shields.io/badge/spring%20data%20jpa-6D597A?style=for-the-badge&logo=spring&logoColor=white">
</br>
  <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/docker--compose-F7A81B?style=for-the-badge&logo=docker&logoColor=white">
</br>
  <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white">
</div>

## 🚀 빠른 시작
### ⚠️ 전제 조건
- Docker
- JAVA 17

### ➡️ 실행 방법

#### 1. 저장소를 클론합니다:
```
$ git clone https://github.com/dorianharok/keumbang.git
$ cd keumbang
```
#### 2. 환경 변수 파일을 설정합니다:</br>
   `.env.example` 파일을 복사하여 `.env` 파일을 만들고, 필요한 값들을 채워넣습니다.
```
JWT_SECRET=
AUTH_DB_PASSWORD=
RESOURCE_DB_PASSWORD=

AUTH_DB_ROOT_PASSWORD=
RESOURCE_DB_ROOT_PASSWORD=
AUTH_DB_USER=
RESOURCE_DB_USER=
```

#### 3. Docker Compose를 사용하여 서비스를 시작합니다:
```
$ ./gradlew bootJar
$ docker-compose up -d
```
#### 4. 서비스가 실행되면, 다음 주소에서 API를 사용할 수 있습니다:
- Auth Server: http://localhost:8888
- Resource Server: http://localhost:9999

## 📑 API 문서
Swagger UI를 통해 API 문서를 확인할 수 있습니다:
- Auth Server: http://localhost:8888/swagger-ui.html
- Resource Server: http://localhost:9999/swagger-ui.html

## 📮 Postman
### [Postman 컬렉션 링크]

## 데이터베이스 스키마
![img](https://github.com/user-attachments/assets/9045970e-7ffe-4734-9b7b-b25832d256d8)
### [DB diagram 링크](https://dbdiagram.io/d/66df0a47550cd927eaa3fde7)

## 프로젝트 구조

프로젝트는 다음과 같은 구조로 이루어져 있습니다:

- `auth-server`: 인증 서버
- `resource-server`: 리소스 서버
- `common`: 공통 모듈