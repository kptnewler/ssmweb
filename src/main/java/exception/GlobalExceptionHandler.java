package exception;

import controller.LoginController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;

@RestControllerAdvice(basePackageClasses = LoginController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Exception handleException(Exception e) {
        return e;
    }
}
