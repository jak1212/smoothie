package com.philosophizer.smoothie.service;

import org.springframework.stereotype.Service;

@Service
public class OpenFoodFactsService {

    static boolean isValidGtin(String d) {
        if (!d.matches("\\d{8}|\\d{12}|\\d{13}|\\d{14}")) return false;
        int sum = 0;
        for (int i = 0; i < d.length() - 1; i++) {
            int digit = d.charAt(d.length() - 2 - i) - '0';
            sum += (i % 2 == 0) ? digit * 3 : digit;
        }
        return (10 - sum % 10) % 10 == d.charAt(d.length() - 1) - '0';
    }
}
