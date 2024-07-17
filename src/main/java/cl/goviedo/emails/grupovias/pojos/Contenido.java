package cl.goviedo.emails.grupovias.pojos;

/**
 * El contenido de un correo
 * @param from Quien envia
 * @param to Hacia Quien Envia
 * @param subject Titulo
 * @param body Cuerpo del Mail
 */
public record Contenido(String from, String to, String subject, String body) {
}
