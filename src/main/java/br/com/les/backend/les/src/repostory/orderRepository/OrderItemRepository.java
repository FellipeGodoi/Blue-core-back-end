package br.com.les.backend.les.src.repostory.orderRepository;

import br.com.les.backend.les.src.model.orderModels.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
