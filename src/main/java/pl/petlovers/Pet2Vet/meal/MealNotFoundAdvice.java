package pl.petlovers.Pet2Vet.meal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class MealNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(MealNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String mealNotFoundHandler(MealNotFoundException exception) {
        log.error("Error: ", exception);
        return exception.getMessage();
    }
}