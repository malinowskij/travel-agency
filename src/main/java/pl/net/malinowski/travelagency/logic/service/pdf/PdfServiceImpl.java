package pl.net.malinowski.travelagency.logic.service.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.controller.exceptions.StorageException;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.logic.util.DateUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class PdfServiceImpl implements PdfService {

    private final Path rootLocation = Paths.get("pdf-dir");
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            log.info("INITIALIZE PDF-STORAGE " + rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String generatePdfForBooking(Booking booking) {
        Document document = new Document();
        String fileName = generatePdfFileName(booking) + ".pdf";
        try {
            PdfWriter.getInstance(document, new FileOutputStream(rootLocation + "/" + fileName));
            document.open();

            addMetaData(document, booking);
            addTitlePage(document, booking);

            document.close();

            log.info("Pdf document created for booking.id = " + booking.getId() + " and customer " +
                    booking.getCustomer().getEmail() + " pdf filename = " + fileName);
        } catch (DocumentException | FileNotFoundException e) {
            log.error("Could not create pdf document " + fileName);
            throw new StorageException("Could not create pdf document " + fileName);
        }

        return rootLocation + "/" + fileName;
    }

    private void addMetaData(Document document, Booking booking) {
        document.addTitle("Rezerwacja podróży " + booking.getTrip().getTitle());
        document.addAuthor("Travel Agency - Jakub Malinowski");
        document.addCreator("Jakub Malnowski");
    }

    private void addTitlePage(Document document, Booking booking)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Potwierdzenie rezerwacji podrozy " +
                booking.getTrip().getTitle(), catFont));

        addEmptyLine(preface, 1);

        preface.add(new Paragraph(
                "Dokument wygenerowany przez: Jakub Malinowski, " + new Date(), smallBold));
        addEmptyLine(preface, 3);

        document.add(preface);
        createTable(document, booking);
    }

    private void createTable(Document document, Booking booking) throws DocumentException {
        PdfPTable table = new PdfPTable(2);

        PdfPCell c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Dane"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        table.addCell("Rezerwujacy: ");
        table.addCell(booking.getCustomer().getFirstName() + " " + booking.getCustomer().getLastName());

        table.addCell("Data rezerwacji: ");
        table.addCell(DateUtil.formatDate(booking.getBookingDate()));

        table.addCell("Liczba osob: ");
        table.addCell(booking.getPeopleQuantity().toString());

        table.addCell("Podroz: ");
        table.addCell(booking.getTrip().getTitle() + ", "
                + booking.getTrip().getDestinationCountry().getName() +
                ", " + booking.getTrip().getDestinationCity().getName());

        table.addCell("Data: ");
        table.addCell("od " + booking.getTrip().getStartDate() + " do " + booking.getTrip().getEndDate());

        table.addCell("All inclusive: ");
        table.addCell(booking.isAllInclusive() ? "Tak" : "Nie");

        table.addCell("Cena/os: ");
        table.addCell(booking.getTrip().getTripPrice().toString() + " PLN/os");

        if (booking.getTrip().isAllInclusiveAvailable()) {
            table.addCell("Cena all inclusive/os: ");
            table.addCell(booking.getTrip().getAllInclusivePrice() + " PLN/os");
        }

        BigDecimal normalPrice = booking.getTrip().getTripPrice()
                .multiply(BigDecimal.valueOf(booking.getPeopleQuantity()));

        table.addCell("Cena ostateczna: ");
        if (booking.isAllInclusive()) {
            BigDecimal allInclusivePrice = booking.getTrip().getAllInclusivePrice()
                    .multiply(BigDecimal.valueOf(booking.getPeopleQuantity()));

            table.addCell(String.valueOf(normalPrice.add(allInclusivePrice)) + " PLN");
        } else
            table.addCell(String.valueOf(normalPrice) + " PLN");

        document.add(table);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private String generatePdfFileName(Booking booking) {
        StringBuilder builder = new StringBuilder();
        builder.append(booking.getTrip().getTitle()).append("-")
                .append(booking.getCustomer().getFirstName()).append("-")
                .append(booking.getCustomer().getLastName()).append("-")
                .append(UUID.randomUUID().toString().substring(0, 6));
        return builder.toString();
    }
}
