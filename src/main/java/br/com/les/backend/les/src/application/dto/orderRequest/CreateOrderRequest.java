package br.com.les.backend.les.src.application.dto.orderRequest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    private String clientCPF;
    private Long refundCouponId;
    private Long discountCouponId;
    private Long deliveryAddressId;
    private String contactPhoneNumber;
    private List<PaymentCardRequest> paymentCards;

    @Getter
    @Setter
    public static class PaymentCardRequest {
        private String cardNumber;
        private Double amountPaid;
    }
}
