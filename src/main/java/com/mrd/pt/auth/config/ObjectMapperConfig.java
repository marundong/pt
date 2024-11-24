//package com.mrd.pt.auth.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mrd.pt.auth.entity.AuthPtUser;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.jackson2.CoreJackson2Module;
//
//@Configuration
//public class ObjectMapperConfig {
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new AuthPtUser());
//        // ... your other configuration
//        return mapper;
//    }
//}
