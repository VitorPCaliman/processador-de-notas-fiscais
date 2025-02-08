package com.app.nfprocessor.utils;

import java.util.regex.Pattern;

public class CNPJUtils {

    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");

    public static boolean isCnpjValido(String cnpj) {
        if (cnpj == null || !CNPJ_PATTERN.matcher(cnpj).matches()) {
            return false;
        }
        return true;
    }

}
