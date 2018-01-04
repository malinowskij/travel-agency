package pl.net.malinowski.travelagency.logic.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.stereotype.Service;
import pl.net.malinowski.travelagency.data.entity.PdfFile;
import pl.net.malinowski.travelagency.data.repository.PdfFileRepository;
import pl.net.malinowski.travelagency.logic.service.acl.AclManager;
import pl.net.malinowski.travelagency.logic.service.interfaces.PdfFileService;
import pl.net.malinowski.travelagency.logic.service.interfaces.UserService;

import java.util.List;

@Service
public class PdfFileServiceImpl implements PdfFileService {

    private final PdfFileRepository pdfFileRepository;
    private final AclManager aclManager;
    private final UserService userService;

    @Autowired
    public PdfFileServiceImpl(PdfFileRepository pdfFileRepository, AclManager aclManager, UserService userService) {
        this.pdfFileRepository = pdfFileRepository;
        this.aclManager = aclManager;
        this.userService = userService;
    }

    @Override
    public PdfFile save(PdfFile pdfFile) {
        pdfFile = pdfFileRepository.save(pdfFile);
        aclManager.buildFullPermission(PdfFile.class, pdfFile.getId(),
                new PrincipalSid(userService.getLoggedInUser().getEmail()));

        return pdfFile;
    }

    @Override
    public List<PdfFile> findAll() {
        return pdfFileRepository.findAll();
    }

    @Override
    public PdfFile findOne(Long id) {
        return pdfFileRepository.findOne(id);
    }
}
