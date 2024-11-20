package com.mrd.pt;

import com.mrd.pt.auth.config.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
public class PtApplication {

    public static void main(String[] args) {
        SpringApplication.run(PtApplication.class, args);
    }

}
