package br.com.les.backend.les.src.model.orderModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_cards")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    private Double amountPaid;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
