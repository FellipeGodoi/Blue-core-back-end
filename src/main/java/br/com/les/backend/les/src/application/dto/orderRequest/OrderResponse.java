package br.com.les.backend.les.src.application.dto.orderRequest;

import br.com.les.backend.les.src.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String clientCPF;
    private LocalDate orderDate;
    private Double totalAmount;
    private OrderStatus status;
}
