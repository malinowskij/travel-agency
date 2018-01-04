package pl.net.malinowski.travelagency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.travelagency.data.entity.Attraction;
import pl.net.malinowski.travelagency.logic.service.interfaces.AttractionService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/attraction")
@Slf4j
public class AttractionController {

    private final AttractionService attractionService;

    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    @ResponseBody
    public List<Attraction> findAll() {
        return attractionService.findAll();
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    @ResponseBody
    public Attraction addAttraction(@Valid @ModelAttribute("attraction") Attraction attraction,
                                          BindingResult result) {
        if (result.hasErrors())
            throw new RuntimeException("attracton Api post Exception (validation)");

        attraction = attractionService.save(attraction);

        log.info("ATTRACTION CREATED ID = " + attraction.getId());

        return attraction;
    }
}
