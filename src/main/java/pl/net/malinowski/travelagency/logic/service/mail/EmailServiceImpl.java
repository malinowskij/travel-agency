package pl.net.malinowski.travelagency.logic.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.User;
import pl.net.malinowski.travelagency.logic.service.mail.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;
    private MailContentBuilder mailBuilder;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, MailContentBuilder mailBuilder) {
        this.javaMailSender = javaMailSender;
        this.mailBuilder = mailBuilder;
    }

    @Async
    @Override
    public void sendWelcomeMessage(User user) {
        String header = "Witaj na pokładzie " + user.getFirstName();
        String title = "Rejestracja przebiegła pomyślnie!";
        String description = "Od teraz możesz logować się z pomocą emaila: " + user.getEmail();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("Witaj w biurze podróży " + user.getFirstName());
            messageHelper.setText(mailBuilder.buildWelcomeMail(
                    header, title, description), true);
        };
        javaMailSender.send(messagePreparator);
    }

    @Async
    @Override
    public void sendBookingMessage(Booking booking) {
        String header = booking.getCustomer().getFirstName() + " gratulujemy udanej rezerwacji!";
        String title = "Rezerwacja podróży " + booking.getTrip().getTitle();

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(booking.getCustomer().getEmail());
            messageHelper.setSubject("Rezerwacja podróży " + booking.getTrip().getTitle());
            messageHelper.setText(mailBuilder.buildBookingMail(header, title, booking), true);
        };
        javaMailSender.send(messagePreparator);
    }
}
