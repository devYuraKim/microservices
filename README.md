# REST Microservices with Spring Boot

**Learning Period**: 2025.08.24 - 2025.09.03

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
- **Service Registry & Client-Side Load Balancing** with Eureka Server
- **API Gateway** for centralized routing, security, and cross-cutting concerns

### 🚀 Deployment & Configuration

**Container Orchestration**
- **Dockerized services** with Docker Compose for seamless local execution
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
✅ **Service Discovery** - Established a centralized service registry with Eureka and achieved client-side load balancing.
✅ **API Gateway** - Implemented centralized entry point with Spring Cloud Gateway for unified routing and security
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

| Service         | Description                        | Port                   |
|-----------------|------------------------------------|------------------------|
| `rabbitmq`      | RabbitMQ message broker            | 5672:5672, 15672:15672 |
| `configserver`  | Spring Cloud Config Server         | 8071:8071              |
| `eurekaserver`  | Eureka Server for Service Registry | 8070:8070              |
| `accountsdb`    | MySQL for accounts service         | 3306:3306              |
| `loansdb`       | MySQL for loans service            | 3307:3306              |
| `cardsdb`       | MySQL for cards service            | 3308:3306              |
| `accounts`      | Accounts microservice              | 8080:8080              |
| `loans`         | Loans microservice                 | 8090:8090              |
| `cards`         | Cards microservice                 | 9000:9000              |
| `gatewayserver` | Spring Cloud Gateway               | 8072:8072              |

> ⚠️ Note: Each MySQL container uses the default container port `3306`, but the host ports are mapped differently (`3306`, `3307`, `3308`) to allow multiple databases to run simultaneously without conflicts.

> 📋 **Startup Order**: 
> - **Core Infrastructure**: `rabbitmq` → `configserver` → `eurekaserver`
> - **Databases**: all database containers (`accountsdb`, `loansdb`, `cardsdb`)
> - **Microservices**: all microservices (`accounts`, `loans`, `cards`) 
> - **API Gateway**: `gatewayserver` (starts after all microservices are healthy)
> -  ⚠️ **Note**:  This order is managed **automatically** by Docker Compose using `depends_on rules`. For **manual execution**, ensure the **core infrastructure** and the **databases** are started **_before the microservices_**, and the **gateway** **_after the microservices_**.

> 💡 **Auto-Setup**: Database tables are automatically created when containers start - no manual configuration required.

**Testing Automatic Configuration Refresh**
1. Modify configuration files in the private GitHub repository
2. GitHub Webhook triggers Config Server refresh
3. Changes propagate via Spring Cloud Bus + RabbitMQ to all services
4. Make the same API request → observe updated configuration in action

> ⚠️ **SSH Access Required**: To test automatic refresh functionality, ensure GitHub SSH access is properly configured.

### Gateway-Routed Service Testing

**Accounts Service**
```bash
curl -X POST http://localhost:8072/microservices/accounts/api/create \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Jane Doe",
           "email": "jane.doe@email.com", 
           "mobileNumber": "01012345678"
         }'
```

**Loans Service**
```bash
curl -X POST "http://localhost:8072/microservices/loans/api/create?mobileNumber=01012345678"
```

**Cards Service** 
```bash
curl -X POST "http://localhost:8072/microservices/cards/api/create?mobileNumber=01012345678"
```

**Fetch CustomerDetails(Composite Service)**
```bash
curl -X GET "http://localhost:8072/microservices/accounts/api/fetchCustomerDetails?mobileNumber=01012345678"
```
> 📝 **Note**: The `fetchCustomerDetails` endpoint demonstrates a composite service pattern, likely aggregating data from multiple microservices (accounts, loans, cards) through the gateway.
**Cleanup**
```bash
# Stop services and remove containers
docker-compose down

# Remove volumes (if needed)
docker-compose down -v
```

<br>

### Service Discovery & Gateway Testing

### Eureka Server Testing
* **Access the Eureka Dashboard**: After starting all services, open your web browser and navigate to `http://localhost:8070`.
* **Verify Service Registration**: On the dashboard, confirm that your microservices (`ACCOUNTS`, `LOANS`, `CARDS`) are listed under "Instances currently registered with Eureka" and are showing a status of `UP`.

### Gateway Server Testing
* **Health Check**: Verify gateway is running at http://localhost:8072/actuator/health
* **Route Discovery**: Check available routes at http://localhost:8072/actuator/gateway/routes
* **Service Routing**: Test that requests to http://localhost:8072/microservices/accounts/api/fetch?mobileNumber=01012345678 are properly routed to the accounts service
---

## 🔗 Additional Resources

- **Docker Images**: Available at [Docker Hub - yurakimyurakim](https://hub.docker.com/repositories/yurakimyurakim)
- **API Documentation**: Access Swagger UI at `http://localhost:8080/swagger-ui.html` for each service
- **Configuration Repository**: Private GitHub repository with environment-specific configurations

## 🛠️ Technology Stack

- **Framework**: Spring Boot, Spring Cloud
- **API Gateway**: Spring Cloud Gateway
- **Configuration**: Spring Cloud Config Server
- **Messaging**: RabbitMQ, Spring Cloud Bus  
- **Service Registry**: Eureka Server
- **Containerization**: Docker, Docker Compose
- **Databases**: MySQL (isolated per service)
- **Documentation**: OpenAPI 3.0

---

*Built with Spring Boot ecosystem and modern DevOps practices for enterprise-grade microservices architecture.*
