package br.com.les.backend.les.src.repostory.orderRepository;

import br.com.les.backend.les.src.model.orderModels.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCardRepository extends JpaRepository<PaymentCard, Long> {
}
