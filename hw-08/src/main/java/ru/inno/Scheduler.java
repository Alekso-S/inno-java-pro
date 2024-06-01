package ru.inno;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.inno.service.PaymentService;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final PaymentService paymentService;

    @Scheduled(cron = "@daily")
    public void reset() {
        paymentService.reset();
    }
}
