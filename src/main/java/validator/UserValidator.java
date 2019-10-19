package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pojo.User;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", null, "登录名不能为空");
        ValidationUtils.rejectIfEmpty(errors, "password", null, "密码不能为空");

        User user = (User) target;
        if (user.getName().length() > 20) {
            errors.rejectValue("name", null, "用户名不能超过10个字符");
        }

        if (user.getPassword()!=null && !user.getPassword().isEmpty() && user.getPassword().length() < 6) {
            errors.rejectValue("password", null, "密码不能小于6位");
        }
    }
}
