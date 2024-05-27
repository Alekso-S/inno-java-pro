package ru.inno.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.inno.api.dto.ApiPaymentDto;
import ru.inno.api.dto.ApiResponseError;
import ru.inno.dto.PaymentDto;
import ru.inno.exception.InsufficientBalanceEx;
import ru.inno.exception.ProductNotFoundEx;
import ru.inno.service.PaymentService;

@RestController
@RequestMapping("payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {this.service = service;}

    @PostMapping
    public void execute(@RequestBody ApiPaymentDto apiPaymentDto) {
        PaymentDto paymentDto = new PaymentDto(apiPaymentDto.getProductId(), apiPaymentDto.getAmount());
        service.execute(paymentDto);
    }

    @ExceptionHandler(ProductNotFoundEx.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponseError handle404Error(RuntimeException e) {
        return new ApiResponseError(e.getMessage());
    }

    @ExceptionHandler(InsufficientBalanceEx.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiResponseError handle422Error(RuntimeException e) {
        return new ApiResponseError(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponseError handleError(RuntimeException e) {
        return new ApiResponseError(e.getMessage());
    }
}
