package id.kawahedukasi.service;

import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

@ApplicationScoped
public class ScheduleService {
    @Inject
    MailService mailService;

    Logger logger = LoggerFactory.getLogger(ScheduleService.class);
    @Scheduled(every = "1h")
    public void generateKawahEdukasi(){
        logger.info("kawahedukasi_{}", LocalDateTime.now());
    }

    @Scheduled(cron = "* 36 15 12 * ? *")
    public void scheduleSendEmailListStore() throws IOException {
        mailService.sendExcelTOEmail("haryadyanpasa@gmail.com");
        logger.info("SEND EMAIL SUCCESS");
    }
}
