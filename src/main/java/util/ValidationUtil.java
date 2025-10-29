package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtil {
    public static boolean isNotEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }
    public static boolean isPositiveDouble(String s) {
        try {
            return Double.parseDouble(s) > 0;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isPositiveInt(String s) {
        try {
            return Integer.parseInt(s) > 0;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isValidDate(String s) {
        try {
            LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
