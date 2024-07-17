package cl.goviedo.emails.grupovias.bo;

/**
 * Clase de la logica de envio de correo
 */
public class GrupoViasBO {

    public GrupoViasBO() {

    }

    public String getSubject() {
        return "Recordatorio de correcciones y acuerdos. Gonzalo Oviedo. Casa 19";
    }

    public String getBody() {

        StringBuilder s = new StringBuilder();
        s.append("Les recuerdo que estan pendientes los arreglos de la casa 19");
        s.append("Si necesitan mas informacion, ");
        s.append("enviar correo a goviedo.sevenit@gmail.com");
        s.append(". Tambien tienen mi Telefono +56963723603");
        return s.toString();
    }
}
