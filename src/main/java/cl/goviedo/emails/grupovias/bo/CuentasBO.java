package cl.goviedo.emails.grupovias.bo;

/**
 * Permite obtener informacion
 * de mis cuentas bancarias
 */
public class CuentasBO {

    public static String mercadoPagoHtml() {
        StringBuilder c = new StringBuilder();

        c.append("<ul>");
        c.append("<li>")
                .append("Nombre: Gonzalo Eduardo Oviedo Lambert")
                .append("</li>")
                .append("<li>")
                .append("RUT: 143553809")
                .append("</li>")
                .append("<li>")
                .append("Banco: Mercado Pago")
                .append("</li>")
                .append("<li>")
                .append("Tipo: Cuenta Vista")
                .append("</li>")
                .append("<li>")
                .append("Numero de cuenta: 1084546738")
                .append("</li>")
                .append("<li>")
                .append("Correo: goviedo.sevenit@gmail.com")
                .append("</li>");
        c.append("</ul>");

        c.append("</ul>");
        return c.toString();
    }

    public static String bciHtml() {

        StringBuilder c = new StringBuilder();

        c.append("<ul>");
        c.append("<li>")
                .append("Nombre: GONZALO EDUARDO OVIEDO")
                .append("</li>")
                .append("<li>")
                .append("RUT: 143553809")
                .append("</li>")
                .append("<li>")
                .append("Banco: BCI")
                .append("</li>")
                .append("<li>")
                .append("Tipo: Cuenta Corriente")
                .append("</li>")
                .append("<li>")
                .append("Numero de cuenta: 46814400")
                .append("</li>")
                .append("<li>")
                .append("Correo: goviedo.sevenit@gmail.com")
                .append("</li>");
        c.append("</ul>");
        return c.toString();
    }
}
