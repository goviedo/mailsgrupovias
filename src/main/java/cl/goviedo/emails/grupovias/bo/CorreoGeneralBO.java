package cl.goviedo.emails.grupovias.bo;

public class CorreoGeneralBO {

    private final String ENTER = "<br/>";
    private String compromiso;
    private String urgencia;
    private String subject;
    private final String from = "goviedo.sevenit@gmail.com";
    private boolean cuentaBancaria;

    /**
     * La cuentaBancaria indica si es necesario colocar datos bancarios o no.
     * @param compromiso
     * @param urgencia
     * @param cuentaBancaria
     */
    public CorreoGeneralBO(String compromiso, String urgencia, boolean cuentaBancaria) {
        this.compromiso = compromiso;
        this.urgencia = urgencia;
        this.cuentaBancaria = cuentaBancaria;
    }

    public String getSubject() {
        return this.subject==null || this.subject.isEmpty()?"Compromisos adquiridos":this.subject;
    }

    public String getBody() {
        StringBuilder s = new StringBuilder();
        s.append("<h1>Muy buenos d&iacute;as.</h1>");
        s.append(ENTER);
        s.append("Les recuerdo y agradezco profundamente el compromiso que han adquirido. Para recordar es lo siguiente: ");
        s.append(ulLi(compromiso));
        s.append(ENTER);
        s.append("Si necesitan mas informaci&oacute;n, ");
        s.append("enviar correo (hagan click) a ");
        s.append(htmlLinkCorreo());
        s.append(".");
        s.append(ENTER);
        s.append("Tambien tienen mi Tel&eacute;fono/Whatsapp: <b>+56963723603</b>");

        if(cuentaBancaria) {
            s.append(ENTER);
            s.append("Para que se les haga m&aacute;s f&aacute;cil, aqu&iacute; se encuentran mis datos bancarios: ");
            s.append("<hr/>");
            s.append(CuentasBO.mercadoPagoHtml());
            s.append("<hr/>");
            s.append(CuentasBO.bciHtml());
            s.append("<hr/>");
        }
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
        s.append(footer());
        return s.toString();
    }

    private String footer() {
        StringBuilder s = new StringBuilder();

        s.append(ENTER);
        s.append(ENTER);
        s.append("Sin más, agradeciendo sus tareas o gestiones,");
        s.append(ENTER);
        s.append("<b>Les deseo un maravilloso d&iacute;a,</b>");
        s.append(ENTER);
        s.append("<i>Gonzalo Oviedo Lambert.</i>");
        return s.toString();
    }

    /**
     * Crea un ul - li
     * @param compromiso
     * @return
     */
    private String ulLi(String compromiso) {

        StringBuilder s = new StringBuilder();

        s.append("<h2>");
        s.append("<ul>");
        s.append("<li>");
        s.append(compromiso);
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

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
