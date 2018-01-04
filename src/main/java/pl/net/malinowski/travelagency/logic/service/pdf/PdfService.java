package pl.net.malinowski.travelagency.logic.service.pdf;

import pl.net.malinowski.travelagency.data.entity.Booking;

import java.io.File;

public interface PdfService {
    void init();

    String generatePdfForBooking(Booking booking);

    File getFile(String pdfPath);
}
