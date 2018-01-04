package pl.net.malinowski.travelagency.logic.service.interfaces;

import pl.net.malinowski.travelagency.data.entity.PdfFile;

import java.util.List;

public interface PdfFileService {
    PdfFile save(PdfFile pdfFile);

    List<PdfFile> findAll();

    PdfFile findOne(Long id);
}
