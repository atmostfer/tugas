package id.kawahedukasi.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class MailService {

    @Inject
    Mailer mailer;

    @Inject
    ExportService exportService;

    public void sendEmail(String email){
        mailer.send(
                Mail.withHtml(email,
                        "Test FROM CRUD API Quarkus",
                        "<h1>Hello</h1>, this is Quarkus Store-Service")
        );
    }

    public void sendExcelTOEmail(String email) throws IOException {
        mailer.send(
                Mail.withHtml(email,
                        "Excel Store Batch 6",
                        "<h1>Hello</h1>, this is your excel file")
                        .addAttachment("list-store.xslx",
                                exportService.excelStore().toByteArray(),
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        );
    }
}
