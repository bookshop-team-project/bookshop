package bookshop.shop.exception.item;

import bookshop.shop.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ItemExceptionHandler {

    @ExceptionHandler(FileSaveException.class)
    public ResponseEntity<ErrorResponse> fileSaveExHandler(FileSaveException e) {
        ErrorResponse response = ErrorResponse.builder().code("파일 저장 실패").message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NotFoundMainImageException.class)
    public ResponseEntity<ErrorResponse> notFoundMainImageExHandler(NotFoundMainImageException e) {
        ErrorResponse response = ErrorResponse.builder().code("메인 이미지 없음").message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
