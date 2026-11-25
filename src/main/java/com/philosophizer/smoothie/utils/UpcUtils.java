package com.philosophizer.smoothie.utils;



    public final class UpcUtils {
        private UpcUtils() {}

        /** Canonical form: digits only, left-padded to 13. */
        public static String normalize(String upc) {
            if (upc == null) throw new IllegalArgumentException("UPC must not be null");
            String digits = upc.replaceAll("\\D", "");
            if (digits.isEmpty() || digits.length() > 13) {
                throw new IllegalArgumentException("Unsupported UPC: " + upc);
            }
            return "0".repeat(13 - digits.length()) + digits;
        }
    }

