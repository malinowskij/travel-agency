package pl.net.malinowski.travelagency;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PdfTest {

    @Test
    public void simpleDocumentTest() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("pdf-dir/iTextHelloWorld.pdf"));
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Hello World", font);

            document.add(chunk);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            Assert.assertFalse(true);
        }
    }
}
