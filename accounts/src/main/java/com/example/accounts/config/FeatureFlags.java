package com.example.accounts.config;

import com.example.accounts.constants.ProfileConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Set;

/**
 * FeatureFlags is a flag-centric, profile-aware feature toggle system.
 *
 * This class allows enabling/disabling features per Spring profile This class allows enabling/disabling features per Spring profile.
 * Currently, it only supports 'profile-based flags'. Future expansions could include user-level, beta, or other conditional flags.
 */
@Component
public class FeatureFlags {

    public static final String DELETE_ACCOUNT_EXCEPTION_SIMULATION = "trigger";

    /**
     * FLAG_PROFILE_MAP maps a feature flag → set of allowed profiles.
     * Immutable map and sets, safe for concurrent read-only use.
     */
    private static final Map<String, Set<String>> FLAG_PROFILE_MAP = Map.of(
            DELETE_ACCOUNT_EXCEPTION_SIMULATION, Set.of(ProfileConstants.DEV)
    );

    //Current Spring profile (injected)
    //Spring can only inject values into an instance, not into static fields.
    @Value("${spring.profiles.active}")
    private String activeProfile;

    //Instance method because it reads activeProfile, which is per-instance injected by Spring.
    //Static method only reads static fields.
    /**
     * Checks if a given flag is enabled for the current active profile.
     *
     * @param flagName the name of the feature flag
     * @return true if the flag is enabled in the current profile, false otherwise
     */
    public boolean isFeatureEnabled(String flagName) {
        //Set.of: create immutable set (unlike HashSet)
        //getOrDefault: if flagName is not found, return empty set, prevent NullPointerException
        Set<String> allowedProfiles = FLAG_PROFILE_MAP.getOrDefault(flagName, Set.of());
        return allowedProfiles.contains(activeProfile.toLowerCase());
    }

}
