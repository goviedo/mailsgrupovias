package cl.goviedo.emails.grupovias.bo;

/**
 * Clase de la lógica de envio de correo
 */
public class GrupoViasBO {

    private final String ENTER = "<br/>";
    private String from;
    private String urgencia;

    public GrupoViasBO(String from, String urgencia) {
        this.from = from;
        this.urgencia = urgencia;
    }

    public String getSubject() {
        return "Recordatorio de correcciones y acuerdos. Gonzalo Oviedo. Casa 19";
    }

    public String getBody() {

        StringBuilder s = new StringBuilder();
        s.append("<h1>Muy buenos d&iacute;as</h1>");
        s.append(ENTER);
        s.append("Les recuerdo que estan pendientes los arreglos de la casa 19. ");
        s.append("Si necesitan mas informaci&oacute;n, ");
        s.append("enviar correo (hagan click) a ");
        s.append(htmlLinkCorreo());
        s.append(".");
        s.append(ENTER);
        s.append("Tambien tienen mi Tel&eacute;fono/Whatsapp: <b>+56963723603</b>");
        s.append(ENTER);
        s.append(ENTER);
        s.append("<h2>");
        s.append("La urgencia inmediata es:");
        s.append(ENTER);
        s.append("<ul>");
        s.append("<li>");
        s.append(urgencia);
        s.append("</li>");
        s.append("</ul>");
        s.append("</h2>");
        return s.toString();
    }

    private String htmlLinkCorreo() {
        StringBuilder correo = new StringBuilder();

        correo.append("<a href=mailto:");
        correo.append(from);
        correo.append(">");
        correo.append(from);
        correo.append("</a>");
        return correo.toString();
    }
}
