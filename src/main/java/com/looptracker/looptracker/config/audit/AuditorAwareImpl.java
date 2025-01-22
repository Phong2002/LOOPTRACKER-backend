package com.looptracker.looptracker.config.audit;
import com.looptracker.looptracker.entity.User;
import com.looptracker.looptracker.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<User> {
   @Autowired
    private IUserRepository userRepository;

    @Override
    public Optional<User> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<User> user = userRepository.findByUsername(username);
            System.out.println("Current auditor: " + user.orElse(null));
            return user;
        }
        System.out.println("No authenticated user found.");
        return Optional.empty();
    }
}
