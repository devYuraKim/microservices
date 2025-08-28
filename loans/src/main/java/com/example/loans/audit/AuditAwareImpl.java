package com.example.loans.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware {

    //TODO: modify the existing method to get current user from security context
    // OR (✅recommended) create a new implementation of AuditorAware and switch using DI
    // -option1. reuse the same component name "auditAwareImpl" for the new class
    // -option2. use @Profile to switch between implementations (@Profile("dev") → this class, @Profile("prod") → SecurityAuditorAwareImpl)
    // -option3. use @Primary to mark the default implementation when multiple components exist
    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("LOANS_MS");
    }
}