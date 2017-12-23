package pl.net.malinowski.travelagency.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Image cannot retrieve")
public class FileRetrieveException extends RuntimeException {
}
