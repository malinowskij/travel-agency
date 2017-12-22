package pl.net.malinowski.travelagency.logic.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.net.malinowski.travelagency.data.entity.Booking;

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

    public String buildBookingMail(String header, String title, Booking booking) {
        Context context = new Context();
        context.setVariable("header", header);
        context.setVariable("title", title);
        context.setVariable("booking", booking);
        return templateEngine.process("/mail/bookingMail", context);
    }
}
