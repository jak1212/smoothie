package com.philosophizer.smoothie;

import com.philosophizer.smoothie.connectivity.kroger.KrogerClient;
import com.philosophizer.smoothie.utils.SearchTermNormalizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.philosophizer.smoothie.model.Kroger.ScoredProduct;

import java.util.Optional;

@SpringBootApplication
public class SmoothieMain {



    public static void main(String[] args) {
        SpringApplication.run(SmoothieMain.class, args);
    }

    @Bean
    CommandLineRunner debugRun(KrogerClient krogerClient, SearchTermNormalizer searchTermNormalizer) {
        return args -> {
            Optional<ScoredProduct> token = krogerClient.pickBest("milk chocolate");

            System.out.println(token.toString());

        };
    }
}
