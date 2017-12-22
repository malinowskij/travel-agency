package pl.net.malinowski.travelagency.logic.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildWelcomeMail(String header, String title, String description) {
        Context context = new Context();
        context.setVariable("header", header);
        context.setVariable("title", title);
        context.setVariable("description", description);
        return templateEngine.process("/mail/welcomeMail", context);
    }
}
