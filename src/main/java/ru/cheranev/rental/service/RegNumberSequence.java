package ru.cheranev.rental.service;

/**
 * @author Cheranev N.
 * created on 26.05.2019.
 */
public class RegNumberSequence {

    private static final String[] lit = {"А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х"};
    private static final String[] num = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    // последний в import.sql А005АА59
    private static String lastRegNumber = "А000АА59";

    // todo можно создать до 1000 номеров, дальше нужно менять серию
    public static String getNextRegNumber() {
        String lastNumber = lastRegNumber.substring(1, 4);
        String nextNumber = String.format("%03d", Integer.valueOf(lastNumber) + 1);
        lastRegNumber = lastRegNumber.replace(lastNumber, nextNumber);
        return lastRegNumber;
    }
}
