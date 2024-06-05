package ru.inno.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.inno.api.dto.ApiPaymentDto;
import ru.inno.api.dto.ApiResponseError;
import ru.inno.model.PaymentDto;
import ru.inno.service.PaymentService;

@RestController
@RequestMapping("payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public void add(@RequestBody @Valid ApiPaymentDto payment) {
        paymentService.add(new PaymentDto(payment.getUid(), payment.getUserId(), payment.getAmount()));
    }

    @DeleteMapping
    public void discard(@RequestBody @Valid ApiPaymentDto paymentDto) {
        paymentService.discard(paymentDto.getUid());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError> handle400Error(MethodArgumentNotValidException e) {
        return ResponseEntity.status(e.getStatusCode()).body(new ApiResponseError(e.getMessage()));
    }

    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiResponseError handle422Error(RuntimeException e) {
        return new ApiResponseError(e.getMessage());
    }
}
