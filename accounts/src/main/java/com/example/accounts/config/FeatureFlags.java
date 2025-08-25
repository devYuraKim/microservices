package com.example.accounts.config;

import com.example.accounts.constants.ProfileConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class FeatureFlags {

    public static final String DELETE_ACCOUNT_EXCEPTION_SIMULATION = "trigger";

    //Keeps a registry of all dev-only flags.
    private static final Set<String> DEV_FLAGS = Set.of(
            DELETE_ACCOUNT_EXCEPTION_SIMULATION
    );

    //Spring can only inject values into an instance, not into static fields.
    @Value("${spring.profiles.active}")
    private String activeProfile;

    //Instance method because it reads activeProfile, which is per-instance injected by Spring.
    //Static method only reads static fields.
    public boolean isDevFeatureEnabled(String flagName) {
        return ProfileConstants.DEV.equalsIgnoreCase(activeProfile) && DEV_FLAGS.contains(flagName);
    }

}
