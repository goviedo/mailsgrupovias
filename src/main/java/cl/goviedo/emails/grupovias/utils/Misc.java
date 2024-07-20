package cl.goviedo.emails.grupovias.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utilidades
 */
public class Misc {

    // Quartz cada 2 dias
    public static Integer cadaDosDias() {
        return Integer.valueOf(2 * 24 * 60 * 60);
    }

    public static Integer cadaDosMinutos() {
        return Integer.valueOf(2 *  60);
    }

    public static String formatDateToTimeZone(Date date) {

        if(date==null) {
            return "Sin fecha/hora aun";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Santiago"));
        return sdf.format(date);
    }
}
