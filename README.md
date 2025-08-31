## REST Microservices with Spring Boot

### 학습 기간
2025.08.24 - 

## 프로젝트 개요 | Project Summary 

### Core Features & Architecture
- Spring Boot-based CRUD microservices with layered architecture: Controller → Service → Repository
- Entity/DTO separation with Mapper for data transformation
- Global exception handling with consistent success/error response structure
- Audit using Base Entity for automatic created/modified tracking
- Feature toggle with Spring Profile for environment-specific behavior
- Automatic REST API documentation using springdoc-openapi

### Deployment & Configuration
- Dockerized microservices with Docker Compose, allowing easy local execution
- Spring Cloud Config Server for centralized configuration management per profile (default, qa, prod)
- Automatic configuration refresh via GitHub Webhook → Config Server → Spring Cloud Bus + RabbitMQ
   - Private GitHub repo access via SSH authentication
   - Changes propagate without restarting services

### Highlights / Achievements
- Applied core microservice patterns (layered architecture, exception handling, auditing, profile-based features)
- Configured environment/profile-based execution and centralized configuration
- Enabled automatic config updates in live services
- Fully containerized services for easy local testing and development

<br>

## 마이크로서비스 실행 방법 | Running Microservices
각 마이크로서비스는 Docker 이미지로 패키징되어 Docker Hub에서 확인할 수 있습니다. 아래 명령어로 로컬 환경에서 실행 가능합니다.

<br>

### Section 0: 프로필별 Docker Compose

**1. 프로필 디렉토리 구조**
Spring Profile에 맞춰 Docker Compose 디렉토리를 선택해 실행할 수 있습니다.
``` sh
docker-compose/
├── default/
├── qa/
└── prod/
``` 

**2. 실행 명령**
```
# 원하는 프로필 디렉토리로 이동
cd docker-compose/<profile>  # default, qa, prod

# 해당 프로필의 모든 서비스 실행
docker compose up -d

```
> ⚠️ 각 프로필 디렉토리 안에서 실행해야 해당 프로필에 맞는 설정과 컨테이너가 올바르게 실행됩니다.

**3. 자동 설정(config) 갱신 확인**
- GitHub 저장소(private)에서 설정 파일(application.yml) 변경
- Webhook → Spring Cloud Config Server → Spring Cloud Bus + RabbitMQ → 모든 서비스로 변경 사항 전파
- 서비스에서 동일한 요청 수행 → 변경된 설정 적용
> ⚠️ 자동 갱신 기능을 테스트하려면 GitHub SSH 접근 권한 필요

<br>

### Section 1: 일괄 Docker Compose (Recommended)
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