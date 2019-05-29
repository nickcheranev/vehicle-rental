package ru.cheranev.rental.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author Cheranev N.
 * created on 28.05.2019.
 */
public class DateFormatTest {

    @Test
    public void formatDateTime() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }

}
