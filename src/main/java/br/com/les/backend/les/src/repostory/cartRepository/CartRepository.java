package br.com.les.backend.les.src.repostory.cartRepository;

import br.com.les.backend.les.src.model.cartModels.Cart;
import br.com.les.backend.les.src.model.clientModels.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCliente(Client cliente);
}
