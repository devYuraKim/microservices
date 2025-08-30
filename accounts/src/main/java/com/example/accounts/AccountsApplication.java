package com.example.accounts;

import com.example.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(value={AccountsContactInfoDto.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
    info = @Info(
        title = "Accounts microservice REST API documentation",
        description = "Accounts REST API documentation",
        version = "1.0",
        contact = @Contact(
            name = "dev",
            email = "dev@mail.com",
            url = "https://www.example.com"
        ),
        license = @License(
            name = "MIT License",
            url = "https://choosealicense.com/licenses/mit/"
        )
    ),
    externalDocs = @ExternalDocumentation(
            description = "Accounts Service REST API Documentation",
            url = "https://example.com/swagger-ui/index.html"
    )
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
