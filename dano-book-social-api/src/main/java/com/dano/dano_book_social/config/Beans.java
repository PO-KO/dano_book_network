package com.dano.dano_book_social.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import com.dano.dano_book_social.auditing.HandleAuditing;

@Configuration
public class Beans {
    
    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new HandleAuditing();
    }


}
