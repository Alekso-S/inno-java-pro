package ru.inno.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.entity.Payment;
import ru.inno.model.PaymentDto;
import ru.inno.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void add(PaymentDto paymentDto) {
        paymentRepository.save(new Payment(paymentDto.getUid(), paymentDto.getUserId(), paymentDto.getAmount()));
    }

    @Transactional
    public void discard(UUID uid) {
        paymentRepository.deleteByUid(uid);
    }

    public BigDecimal sumByUserId(Long userId) {
        return paymentRepository.sumByUserId(userId);
    }

    @Transactional
    public void reset() {
        paymentRepository.deleteAll();
    }
}
