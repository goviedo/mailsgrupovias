package cl.goviedo.emails.grupovias.utils;

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
}
