package pl.net.malinowski.travelagency.controller.advice;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.net.malinowski.travelagency.controller.exceptions.*;
import pl.net.malinowski.travelagency.data.entity.Booking;
import pl.net.malinowski.travelagency.data.entity.Trip;
import pl.net.malinowski.travelagency.logic.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class GlobalAdviceController {

    @ExceptionHandler({HttpSessionRequiredException.class})
    public String handleSessionError() {
        return "redirect:/";
    }

    @ExceptionHandler({TripHasNotFreePlaces.class})
    public String handleErrorWhileBookingNotFreePlaces(TripHasNotFreePlaces ex) {
        Booking booking = ex.getBooking();
        if (booking.getPeopleQuantity() > 4) booking.setPeopleQuantity(4);
        return "redirect:/trip/search?country=" + booking.getTrip().getDestinationCountry().getId()
                + "&startDate=" + DateUtil.formatDate(booking.getTrip().getStartDate())
                + "&endDate=" + DateUtil.formatDate(booking.getTrip().getEndDate())
                + "&peopleCount=" + booking.getPeopleQuantity() + "&cantBook";
    }

    @ExceptionHandler({StorageNotFoundException.class})
    public String handleStorageNotFoundException() {
        return "redirect:/";
    }

    @ExceptionHandler({AccessDeniedException.class})
    public String handleAccessDeniedException(Model model) {
        model.addAttribute("message", "Przykro nam ale nie masz dostępu do zasobu, którego żądasz!");
        return "/errors/alertError";
    }

    @ExceptionHandler({CannotMakeOperationOnTripException.class})
    public String tripOperationExceptionHandle(CannotMakeOperationOnTripException ex, RedirectAttributes model) {
        Trip t = ex.getTrip();
        model.addFlashAttribute("message",
                "Nie można modyfikować wybranej podróży o ID: " + t.getId() + " Nazwa: " + t.getTitle());
        return "redirect:/admin/trip?operationException";
    }

    @ExceptionHandler({TripCreationException.class})
    public String tripCreationExceptionHandle() {
        return "redirect:/admin/trip/creator?tripCreationException";
    }

    @ExceptionHandler({TooLateForBookingManipulateException.class})
    public String tooLateForBookingManipulationException() {
        return "redirect:/user/profile?tooLateForBookingManipulationException";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
}
