package com.example.subscribly;

import com.example.subscribly.model.*;
import com.example.subscribly.service.PlanService;
import com.example.subscribly.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class SubscriblyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscriblyApplication.class, args);
    }

    @Bean
    CommandLineRunner initPlans(PlanService planService) {
        return args -> {
            if (planService.countPlans() == 0) {
                Plan starter = new Plan();
                starter.setName("Starter");
                starter.setDescription("Starter plan for small teams");
                starter.setBillingPeriod(BillingPeriod.MONTHLY);
                starter.setPricePerPeriod(new BigDecimal("29.00"));
                starter.setMaxUsers(10);
                starter.setApiCallLimit(10000L);
                starter.setExtraApiCallPricePerThousand(new BigDecimal("2.50"));
                planService.savePlan(starter);

                Plan pro = new Plan();
                pro.setName("Pro");
                pro.setDescription("Pro plan for growing companies");
                pro.setBillingPeriod(BillingPeriod.MONTHLY);
                pro.setPricePerPeriod(new BigDecimal("99.00"));
                pro.setMaxUsers(50);
                pro.setApiCallLimit(100000L);
                pro.setExtraApiCallPricePerThousand(new BigDecimal("1.80"));
                planService.savePlan(pro);

                Plan enterprise = new Plan();
                enterprise.setName("Enterprise");
                enterprise.setDescription("Enterprise plan with higher limits");
                enterprise.setBillingPeriod(BillingPeriod.YEARLY);
                enterprise.setPricePerPeriod(new BigDecimal("999.00"));
                enterprise.setMaxUsers(500);
                enterprise.setApiCallLimit(1000000L);
                enterprise.setExtraApiCallPricePerThousand(new BigDecimal("1.00"));
                planService.savePlan(enterprise);
            }
        };
    }

    @Bean
    CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@subscribly.local").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@subscribly.local");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
            }
        };
    }
}
