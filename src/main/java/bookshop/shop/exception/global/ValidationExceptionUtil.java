package bookshop.shop.exception.global;

import jakarta.validation.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationExceptionUtil {
    public static void checkBindingResult(BindingResult bindingResult) {
        if (bindingResult != null && bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                throw new ValidationException(fieldError.getDefaultMessage());
            } else {
                throw new ValidationException("알 수 없는 유효성 검사 오류가 발생했습니다.");
            }
        }
    }
}
