package pl.net.malinowski.travelagency.logic.service.pdf;

import pl.net.malinowski.travelagency.data.entity.Booking;

public interface PdfService {
    void init();

    String generatePdfForBooking(Booking booking);
}
