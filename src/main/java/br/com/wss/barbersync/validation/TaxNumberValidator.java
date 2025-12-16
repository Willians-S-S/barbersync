package br.com.wss.barbersync.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.InputMismatchException;

public class TaxNumberValidator implements ConstraintValidator<TaxNumberValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        String taxNumber = value.replaceAll("[^0-9]", "");
        if (taxNumber.length() == 11) return isValidCPF(taxNumber);
        if (taxNumber.length() == 14) return isValidCNPJ(taxNumber);
        return false;
    }

    private static boolean isValidCPF(final String cpf) {
        if (cpf.length() != 11) return false;

        // sequências inválidas
        if (cpf.chars().distinct().count() == 1) return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = cpf.charAt(i) - '0';
                sm += (num * peso);
                peso--;
            }

            r = 11 - (sm % 11);
            dig10 = ((r == 10) || (r == 11)) ? '0' : (char) (r + '0');

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = cpf.charAt(i) - '0';
                sm += (num * peso);
                peso--;
            }

            r = 11 - (sm % 11);
            dig11 = ((r == 10) || (r == 11)) ? '0' : (char) (r + '0');

            return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
        } catch (InputMismatchException e) {
            return false;
        }
    }

    private static boolean isValidCNPJ(final String cnpj) {
        if (cnpj.length() != 14) return false;

        // sequências inválidas
        if (cnpj.chars().distinct().count() == 1) return false;

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = cnpj.charAt(i) - '0';
                sm += (num * peso);
                peso++;
                if (peso == 10) peso = 2;
            }

            r = sm % 11;
            dig13 = ((r == 0) || (r == 1)) ? '0' : (char) ((11 - r) + '0');

            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = cnpj.charAt(i) - '0';
                sm += (num * peso);
                peso++;
                if (peso == 10) peso = 2;
            }

            r = sm % 11;
            dig14 = ((r == 0) || (r == 1)) ? '0' : (char) ((11 - r) + '0');

            return (dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13));
        } catch (InputMismatchException e) {
            return false;
        }
    }
}
