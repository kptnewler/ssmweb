package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "用户处理发生异常", code = HttpStatus.INTERNAL_SERVER_ERROR)
public class UserException extends RuntimeException {

}
