package pl.net.malinowski.travelagency.controller.advice;

import org.springframework.stereotype.Controller;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalAdviceController {

    @ExceptionHandler({HttpSessionRequiredException.class})
    public String handleSessionError() {
        return "redirect:/";
    }
}
