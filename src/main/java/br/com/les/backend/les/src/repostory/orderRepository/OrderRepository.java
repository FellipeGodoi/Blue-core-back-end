package br.com.les.backend.les.src.repostory.orderRepository;

import br.com.les.backend.les.src.model.enums.OrderStatus;
import br.com.les.backend.les.src.model.orderModels.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);

}
