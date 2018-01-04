package pl.net.malinowski.travelagency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.net.malinowski.travelagency.controller.exceptions.StorageNotFoundException;
import pl.net.malinowski.travelagency.data.entity.PdfFile;
import pl.net.malinowski.travelagency.logic.service.interfaces.PdfFileService;
import pl.net.malinowski.travelagency.logic.service.pdf.PdfService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
@RequestMapping("/download")
public class DownloadController {

    private static final String APPLICATION_PDF = "application/pdf";
    private final PdfService pdfService;
    private final PdfFileService pdfFileService;

    @Autowired
    public DownloadController(PdfService pdfService, PdfFileService pdfFileService) {
        this.pdfService = pdfService;
        this.pdfFileService = pdfFileService;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/{id}", produces = APPLICATION_PDF)
    @ResponseBody
    public Resource downloadPdf(@PathVariable Long id, HttpServletResponse response) {
        PdfFile pdfFile = pdfFileService.findOne(id);
        if (pdfFile == null)
            throw new StorageNotFoundException("NOT FOUND PDF WITH ID = " + id);
        File file = pdfService.getFile(pdfFile.getPath());
        response.setContentType(APPLICATION_PDF);
        response.setHeader("Content-Disposition", "inline; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        return new FileSystemResource(file);
    }


}
