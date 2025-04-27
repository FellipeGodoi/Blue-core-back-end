package br.com.les.backend.les.src.repostory.orderRepository;

import br.com.les.backend.les.src.model.orderModels.OrderAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAddressRepository extends JpaRepository<OrderAddress, Long> {
}
