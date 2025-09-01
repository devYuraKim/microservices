# REST Microservices with Spring Boot

**Learning Period**: 2025.08.24 - Present

A production-ready Spring Boot microservices ecosystem featuring automated configuration management, containerized deployment, and zero-downtime configuration updates.

## 📋 Project Summary

### 🏗️ Core Features & Architecture

**Clean Layered Architecture**
- **Controller → Service → Repository** pattern for clear separation of concerns
- **Entity/DTO Separation** with dedicated mappers for loose coupling
- **Global Exception Handling** ensuring consistent success/error responses
- **Audit Trail** via Base Entity for automatic created/modified timestamps and user tracking
- **Feature Toggles** using Spring Profiles for environment-specific behavior
- **Auto-Generated Documentation** with `springdoc-openapi`

### 🚀 Deployment & Configuration

**Container Orchestration**
- **Dockerized Microservices** with Docker Compose for seamless local execution
- **Dedicated Database** - Each microservice maintains its own dedicated database
- **Automatic Database Setup** - Databases automatically created and configured via Docker Compose

**Advanced Configuration Management**
- **Centralized Config** via Spring Cloud Config Server with profile support (`default`, `qa`, `prod`)
- **Zero-Downtime Updates** through GitHub Webhook → Config Server(/monitor) → Spring Cloud Bus + RabbitMQ → Microservices
- **Live Propagation** - Configuration changes apply without service restarts
- **Secure Access** to private GitHub repository via SSH authentication

### 🎯 Key Achievements

✅ **Microservice Patterns** - Implemented layered architecture (Controller → Service → Repository), DTO separation, exception handling, and auditing.  
✅ **DevOps Automation** - Achieved fully automated configuration management with live updates  
✅ **Environment Management** - Profile-based execution across development, QA, and production  
✅ **Container Orchestration** - Complete containerization of microservices, databases, Config Server, and RabbitMQ, enabling consistent, scalable deployments

---

## 🚀 Running the Microservices

All microservices are packaged as Docker images and available on **Docker Hub**. Choose your preferred deployment method below.

### Profile-Based Deployment

**Directory Structure**
```
docker-compose/
├── default/    # Development environment
├── qa/         # Quality assurance environment  
└── prod/       # Production environment
```

**Execution Steps**
```bash
# Navigate to desired profile directory
cd docker-compose/<profile>  # Choose: default, qa, or prod

# Start all services for the selected profile
docker compose up -d
```

> ⚠️ **Important**: Execute commands from within the specific profile directory to ensure correct configuration and container deployment.

**Container Services**
The following containers will be deployed:

| Service | Description | Port |
|---------|-------------|------|
| `accounts` | Accounts microservice | 8080:8080 |
| `loans` | Loans microservice | 8090:8090 |
| `cards` | Cards microservice | 9000:9000 |
| `configserver` | Spring Cloud Config Server | 8071:8071 |
| `rabbit` | RabbitMQ message broker | 5672:5672, 15672:15672 |
| `accountsdb` | MySQL for accounts service | 3306:3306 |
| `loansdb` | MySQL for loans service | 3307:3306 |
| `cardsdb` | MySQL for cards service | 3308:3306 |

> ⚠️ Note: Each MySQL container uses the default container port `3306`, but the host ports are mapped differently (`3306`, `3307`, `3308`) to allow multiple databases to run simultaneously without conflicts.

> 📋 **Startup Order**: `rabbit` → `configserver` → microservices (handled automatically by Docker Compose)

> 💡 **Auto-Setup**: Database tables are automatically created when containers start - no manual configuration required.

**Testing Automatic Configuration Refresh**
1. Modify configuration files in the private GitHub repository
2. GitHub Webhook triggers Config Server refresh
3. Changes propagate via Spring Cloud Bus + RabbitMQ to all services
4. Make the same API request → observe updated configuration in action

> ⚠️ **SSH Access Required**: To test automatic refresh functionality, ensure GitHub SSH access is properly configured.

### Service Testing

**Accounts Service**
```bash
curl -X POST http://localhost:8080/api/create \
     -H "Content-Type: application/json" \
     -d '{
           "name": "John Doe",
           "email": "john.doe@email.com", 
           "mobileNumber": "01012345678"
         }'
```

**Loans Service**
```bash
curl -X POST "http://localhost:8090/api/create?mobileNumber=01012345678"
```

**Cards Service** 
```bash
curl -X POST "http://localhost:9000/api/create?mobileNumber=01012345678"
```

**Cleanup**
```bash
# Stop services and remove containers
docker-compose down

# Remove volumes (if needed)
docker-compose down -v
```

---

## 🔗 Additional Resources

- **Docker Images**: Available at [Docker Hub - yurakimyurakim](https://hub.docker.com/repositories/yurakimyurakim)
- **API Documentation**: Access Swagger UI at `http://localhost:8080/swagger-ui.html` for each service
- **Configuration Repository**: Private GitHub repository with environment-specific configurations

## 🛠️ Technology Stack

- **Framework**: Spring Boot, Spring Cloud
- **Configuration**: Spring Cloud Config Server
- **Messaging**: RabbitMQ, Spring Cloud Bus  
- **Containerization**: Docker, Docker Compose
- **Databases**: MySQL (isolated per service)
- **Documentation**: OpenAPI 3.0

---

*Built with Spring Boot ecosystem and modern DevOps practices for enterprise-grade microservices architecture.*
