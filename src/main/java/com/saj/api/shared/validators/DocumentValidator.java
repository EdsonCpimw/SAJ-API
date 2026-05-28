package com.saj.api.shared.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentValidator implements ConstraintValidator<Document, String> {

    private static final String CPF_REGEX  = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
    private static final String CNPJ_REGEX = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return false;

        if (value.matches(CPF_REGEX))  return isValidCpf(value);
        if (value.matches(CNPJ_REGEX)) return isValidCnpj(value);

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                "Formato inválido. Esperado: CPF (XXX.XXX.XXX-XX) ou CNPJ (XX.XXX.XXX/XXXX-XX)"
        ).addConstraintViolation();

        return false;
    }

    private boolean isValidCpf(String cpf) {
        String digits = cpf.replaceAll("\\D", "");
        if (digits.length() != 11 || digits.chars().distinct().count() == 1) return false;

        int sum = 0;
        for (int i = 0; i < 9; i++) sum += (digits.charAt(i) - '0') * (10 - i);
        int first = (sum * 10 % 11) % 10;
        if (first != digits.charAt(9) - '0') return false;

        sum = 0;
        for (int i = 0; i < 10; i++) sum += (digits.charAt(i) - '0') * (11 - i);
        int second = (sum * 10 % 11) % 10;
        return second == digits.charAt(10) - '0';
    }

    private boolean isValidCnpj(String cnpj) {
        String digits = cnpj.replaceAll("\\D", "");
        if (digits.length() != 14 || digits.chars().distinct().count() == 1) return false;

        int[] weights1 = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
        int[] weights2 = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

        int sum = 0;
        for (int i = 0; i < 12; i++) sum += (digits.charAt(i) - '0') * weights1[i];
        int first = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        if (first != digits.charAt(12) - '0') return false;

        sum = 0;
        for (int i = 0; i < 13; i++) sum += (digits.charAt(i) - '0') * weights2[i];
        int second = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        return second == digits.charAt(13) - '0';
    }
}