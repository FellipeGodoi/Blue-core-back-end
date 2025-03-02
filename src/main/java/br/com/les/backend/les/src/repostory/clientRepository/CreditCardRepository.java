package br.com.les.backend.les.src.repostory.clientRepository;

import br.com.les.backend.les.src.model.clientModels.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
