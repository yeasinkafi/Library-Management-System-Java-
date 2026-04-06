package library.management.system;

import java.time.LocalDate;

public final class ParseUtil {
    private ParseUtil() {}

    public static int safeInt(String s) {
        try { return Integer.parseInt(s.trim()); }
        catch (Exception e) { return 0; }
    }

    public static double safeDouble(String s) {
        try { return Double.parseDouble(s.trim()); }
        catch (Exception e) { return 0.0; }
    }

    public static LocalDate safeDate(String s) {
        try { return LocalDate.parse(s.trim()); }
        catch (Exception e) { return null; }
    }

    public static Order.Status safeStatus(String s) {
        if (s == null) return null;
        try { return Order.Status.valueOf(s.trim().toUpperCase()); }
        catch (Exception e) { return null; }
    }
}
