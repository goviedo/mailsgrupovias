package cl.goviedo.emails.grupovias.controller;

import cl.goviedo.emails.grupovias.jobs.EmailGrupoViasJobUrgencia;
import cl.goviedo.emails.grupovias.jobs.ejemplos.EnviaCorreoJob;
import cl.goviedo.emails.grupovias.services.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/servicio")
public class JobsController {

    private JobService jobService;

    @Autowired
    public JobsController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping(value = "/ejemplo")
    public void ejemplo() throws SchedulerException {
        log.info("JobsController::ejemplo");

        // Aqui puede llamarse tanto a EnviaCorreoJob como EnvioCorreoJobSimple
        // Tener en cuenta la construccion de estos objetos, sin constructor y con
        // @Autowired en los atributos, no el constructor para hacer el autowired
        // sino no funciona.
        jobService.addJob("trabajo", "ejemplos", EnviaCorreoJob.class, 10);
    }

    @GetMapping(value = "/urgencia")
    public void otraUrgenciaGrupoVias(@RequestParam("urgencia") String urgencia, @RequestParam("nombreUnico") String nombreUnico, String cron) throws SchedulerException {
        log.info("JobsController::otraUrgenciaGrupoVias");

        EmailGrupoViasJobUrgencia email = new EmailGrupoViasJobUrgencia();


        // "0 0/5 * * * ?" 5 min

        jobService.addJobCron(nombreUnico, "grupovias", email.getClass(), urgencia,cron,"Para enviar diferentes tipos de urgencia");
    }


}
