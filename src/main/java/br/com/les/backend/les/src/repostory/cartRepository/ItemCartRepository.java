package br.com.les.backend.les.src.repostory.cartRepository;

import br.com.les.backend.les.src.model.cartModels.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCartRepository extends JpaRepository<CartItem, Long> {
}
