package ru.inno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.inno.entity.Payment;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select coalesce(sum(p.amount),0) from Payment p where p.userId = ?1")
    BigDecimal sumByUserId(Long userId);

    void deleteByUid(UUID uid);

}
