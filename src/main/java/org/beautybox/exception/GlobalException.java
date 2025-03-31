package org.beautybox.exception;

import lombok.extern.slf4j.Slf4j;
import org.beautybox.response.ApiResponse;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> exception(RuntimeException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> exception(HttpRequestMethodNotSupportedException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.error("Phương thức '" + e.getMethod() + "' không được hỗ trợ cho truy vấn này"));
    }

    /**
     * Xử lí lỗi, chuyển đổi kiểu dữ liệu thời gian
     */
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiResponse> exception(DateTimeParseException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.error("Định dạng dữ liệu thời gian chưa chính xác"));
    }

    /**
     * Bắt lỗi thiếu tham số truyền vào qua request param
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParam(MissingServletRequestParameterException ex) {
        String errorMessage = "Tham số '" + ex.getParameterName() + "' là bắt buộc!";
        return ResponseEntity.badRequest().body(ApiResponse.error(errorMessage));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> illegalArgumentException(IllegalArgumentException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
    }

    /**
     * Lỗi kết nối đến database hay là bên thứ 3
     */
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<ApiResponse> timeoutException(DataAccessResourceFailureException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.error("Lỗi kết nối !"));
    }

    /**
     * Xử lí validate dữ liệu đầu vào của Body
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> methodArgument(MethodArgumentNotValidException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.error(Objects.requireNonNull(e.getFieldError()).getDefaultMessage()));
    }


    /**
     * Xử lí lỗi của hệ thống tự định nghĩa ra
     */
    @ExceptionHandler(BeautyBoxException.class)
    public ResponseEntity<ApiResponse> bookingCareAppException(BeautyBoxException e){
        ErrorDetail errorDetail= e.getErrorDetail();
        log.error(errorDetail.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.error(errorDetail.getCode(), errorDetail.getMessage()));
    }
}
