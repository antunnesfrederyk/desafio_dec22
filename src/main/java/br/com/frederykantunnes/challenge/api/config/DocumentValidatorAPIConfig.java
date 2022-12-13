package br.com.frederykantunnes.challenge.api.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class DocumentValidatorAPIConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

}


