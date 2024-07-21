# Curls

# Negocio

## Generar un Compromiso o Urgencia.

```bash
curl --location 'http://localhost:8080/servicio/compromisourgencia?compromiso=Me%20deben%20el%20dinero%20del%20Pie%20de%20la%20casa%2C%20creo%20que%20va%20mas%20de%20un%20a%C3%B1o.%20Ustedes%20ya%20se%20han%20ahorrado%20mucho!.&urgencia=Devolver%20el%20dinero%20a%20mi%20cuenta%20BCI.&nombreJob=prueba&cron=0%201%2F1%20*%20*%20*%20%3F&destinatario=goviedo%40sb.cl&cc=goviedo.sevenit%40gmail.com&cuentaBancaria=true'
```

## Urgencias a Grupo Vias.

```bash
curl --location 'http://localhost:8080/servicio/urgencia?urgencia=Enchufes%20en%20mal%20estado&nombreUnico=enchufes9am&cron=0%200%209%2C18%20*%20*%20%3F'
```

## Cron Maker API.

```bash
curl --location 'http://www.cronmaker.com/rest/sampler?expression=0%200%209%2C18%20*%20*%20%3F&count=40'
```

# Systema o Cron

## Triggers

```bash
curl --location 'http://localhost:8080/info/triggers'
```

## Info Jobs

```bash
curl --location 'http://localhost:8080/info/jobs'
```