package pl.petlovers.Pet2Vet.note;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.petlovers.Pet2Vet.appUser.AppUserNotFoundException;

@ControllerAdvice
public class NoteNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String appUserNotFoundHandler(NoteNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String nullPointerHandler(NullPointerException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String illegalArgumentHandler(IllegalArgumentException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String illegalStateHandler(IllegalStateException exception) {
        return exception.getMessage();
    }

}
