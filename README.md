## REST Microservices with Spring Boot

### 학습 기간
2025.08.24 - 

## 프로젝트 개요

### 주요 구현 내용
- **환경 구성**: `application.yml` 기반 환경(dev/prod/test)별 설정 관리  
- **REST API 설계**: Controller → Service(Interface + Impl) → JPA Repository 계층 구조, CRUD 구현  
- **도메인 모델링**: Entity/DTO 분리, Mapper 활용 데이터 변환  
- **예외 처리**: 글로벌 Exception Handling 적용, 성공/오류 공통 응답 구조 정의  
- **감사(Audit)**: Base Entity 활용 생성/수정 정보 자동 관리  
- **Feature Toggle**: Spring Profile 기반 환경별 기능 제어  
- **API 문서화**: `springdoc-openapi`를 활용한 REST API 문서 자동 생성

- **컨테이너화**: Docker 및 Docker Compose를 활용하여 마이크로서비스를 컨테이너화하고, 로컬 개발 환경에서 모든 서비스를 한 번에 실행 가능하도록 구성
- **Spring Cloud Config 적용**: Config Server를 통해 마이크로서비스의 설정 외부화 및 환경별 설정 통합 관리

### 성과 요약
- Spring Boot 기반 CRUD 및 Microservice 핵심 기능 이해  
- 계층형 아키텍처 설계, 예외 처리, 감사, 환경별 기능 관리 등 핵심 패턴 적용  
- API 문서화 및 테스트 수행으로 실제 서비스에 적용 가능한 수준 구현
- Docker 및 Docker Compose를 활용한 마이크로서비스 실행 환경 구성
- Spring Cloud Config를 활용한 설정 중앙화 및 서비스 간 환경 일관성 확보

<br>

## 프로젝트 실행 방법

## Running Microservices
각 마이크로서비스는 Docker 이미지로 패키징되어 Docker Hub에서 확인할 수 있습니다. 아래 명령어로 로컬 환경에서 실행 가능합니다.

<br>

### Section 1: Docker Compose (Recommended)
1. Make sure Docker and Docker Compose are installed.
2. From the project root, run:
``` sh
docker-compose up -d
```
> Note: detached mode - runs all services in the background

3. Verify all services are running:
```sh
docker-compose ps
```
You should see all containers listed with their respective ports.

4. Test the services:
#### Accounts Service
```sh
curl -X POST http://localhost:8080/api/create \
     -H "Content-Type: application/json" \
     -d '{
           "name": "test",
           "email": "test@mail.com",
           "mobileNumber": "01012345678"
         }'
```
#### Loans Service
```sh
curl -X POST "http://localhost:8090/api/create?mobileNumber=01012345678"
```
#### Cards Service
```sh
curl -X POST "http://localhost:9000/api/create?mobileNumber=01012345678"
```
5. Stop services and remove containers when done:
```sh
docker-compose down
```
<br>


### Section 2: Individual Services (Optional)
If you prefer, you can still run each microservice individually by pulling its Docker image from Docker Hub.
> Note: This approach is useful if you only want to run a single service for testing or debugging.

#### Accounts Service
1. Run the Accounts service:
```sh
docker pull yurakimyurakim/accounts:0.0.1-SNAPSHOT
docker run -d -p 8080:8080 --name accounts yurakimyurakim/accounts:0.0.1-SNAPSHOT
```
2. After running the container, test it:
```sh
curl -X POST http://localhost:8080/api/create \
     -H "Content-Type: application/json" \
     -d '{
           "name": "test",
           "email": "test@mail.com",
           "mobileNumber": "01012345678"
         }'
```
3. Expected: a response confirming the account was created.

### Loans Service
1. Run the Loans service:
```sh
docker pull yurakimyurakim/loans:0.0.1-SNAPSHOT
docker run -d -p 8090:8090 --name loans yurakimyurakim/loans:0.0.1-SNAPSHOT
```
2. After running the container, test it:
```sh
curl -X POST "http://localhost:8090/api/create?mobileNumber=01012345678"
```
3. Expected: a response confirming the loan was created.

### Cards Service
1. Run the Cards service:
```sh
docker pull yurakimyurakim/cards:0.0.1-SNAPSHOT
docker run -d -p 9000:9000 --name cards yurakimyurakim/cards:0.0.1-SNAPSHOT
```
2. After running the container, test it:
```sh
curl -X POST "http://localhost:9000/api/create?mobileNumber=01012345678"
```
3. Expected: a response confirming the card was created.

