package com.example.demo.config.auditing;

import com.example.demo.entity.auth.AuthUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditAwareImpl implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        if (authentication.getPrincipal() instanceof  Integer)
        {
            return Optional.of(((Integer) authentication.getPrincipal()));
        }

        return Optional.of(((AuthUser) authentication.getPrincipal()).getId());

    }
}
