package br.com.les.backend.les.src.repostory.orderRepository;

import br.com.les.backend.les.src.model.orderModels.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
