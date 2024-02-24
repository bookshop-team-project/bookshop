package bookshop.shop.exception.member;

import bookshop.shop.exception.ErrorResponse;
import bookshop.shop.exception.item.FileSaveException;
import bookshop.shop.exception.item.NotFoundMainImageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {


    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> memberExceptionHandler(MemberException e) {
        ErrorResponse response = ErrorResponse.builder().code("유효하지 않은 입력").message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MemberPresentException.class)
    public ResponseEntity<ErrorResponse> memberPresentExceptionHandler(MemberPresentException e) {
        ErrorResponse response = ErrorResponse.builder().code("등록 회원 존재").message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> memberNotFoundExceptionHandler(MemberNotFoundException e) {
        ErrorResponse response = ErrorResponse.builder().code("존재하지 않는 회원").message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
