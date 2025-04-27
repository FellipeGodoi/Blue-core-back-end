package br.com.les.backend.les.src.service.orderService;

import br.com.les.backend.les.src.application.dto.orderRequest.CreateOrderRequest;
import br.com.les.backend.les.src.application.dto.orderRequest.OrderResponse;
import br.com.les.backend.les.src.model.cartModels.CartItem;
import br.com.les.backend.les.src.model.clientModels.Address;
import br.com.les.backend.les.src.model.cuponsModels.Cupom;
import br.com.les.backend.les.src.model.enums.OrderStatus;
import br.com.les.backend.les.src.model.orderModels.Order;
import br.com.les.backend.les.src.model.orderModels.OrderAddress;
import br.com.les.backend.les.src.model.orderModels.OrderItem;
import br.com.les.backend.les.src.model.orderModels.PaymentCard;
import br.com.les.backend.les.src.repostory.clientRepository.AddressRepository;
import br.com.les.backend.les.src.repostory.clientRepository.CreditCardRepository;
import br.com.les.backend.les.src.repostory.cupomRepository.CupomRepository;
import br.com.les.backend.les.src.repostory.orderRepository.OrderRepository;
import br.com.les.backend.les.src.repostory.productRepository.ProcessorRepository;
import br.com.les.backend.les.src.service.cartService.CartService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final AddressRepository addressRepository;
    private final CupomRepository cupomRepository;
    private final CreditCardRepository cardRepository;
    private final ProcessorRepository processorRepository;

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        // 1. Buscar itens do carrinho pelo CPF
        List<CartItem> cartItems = cartService.getCartItemsByClientCpf(request.getClientCPF());
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Carrinho está vazio");
        }

        // 2. Criar os OrderItems a partir dos CartItems
        List<OrderItem> orderItems = createOrderItems(cartItems);

        // 3. Buscar endereço
        OrderAddress orderAddress = createOrderAddress(request);

        // 4. Verificar se o cliente passou cartões de pagamento
        List<PaymentCard> paymentCards = createPaymentCards(request);

        // 5. Calcular total do pedido (antes dos descontos)
        Double totalAmount = calculateTotalAmount(orderItems);

        // 6. Aplicar cupons (usando calcularDesconto)
        totalAmount = applyDiscountCoupons(request, totalAmount);

        if (totalAmount < 0) totalAmount = 0.0;

        // 7. Verificar se o valor pago nos cartões é maior que o valor total
        if (isPaymentGreaterThanOrderAmount(paymentCards, totalAmount)) {
            // Se o valor pago pelos cartões for maior que o valor total, alterar o status para "PAGAMENTO REPROVADO"
            return createRejectedOrder(request, orderItems, paymentCards, orderAddress, totalAmount);
        }

        // 8. Criar Order
        return createProcessedOrder(request, orderItems, paymentCards, orderAddress, totalAmount);
    }

    @Transactional
    public List<OrderResponse> listAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getClientCPF(),
                        order.getOrderDate(),
                        order.getTotalAmount(),
                        order.getStatus()
                ))
                .collect(Collectors.toList());
    }

    //--------------------------------------------------------------------

    // Metodo para criar os OrderItems a partir dos CartItems
    private List<OrderItem> createOrderItems(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductCode(cartItem.getProcessor().getCodigo());
                    orderItem.setQuantity(cartItem.getQtd());
                    orderItem.setProductPriceAtOrder(cartItem.getProcessor().getPrecoVenda());
                    return orderItem;
                })
                .collect(Collectors.toList());
    }

    // Metodo para criar o OrderAddress
    private OrderAddress createOrderAddress(CreateOrderRequest request) {
        Address existingAddress = addressRepository.findById(request.getDeliveryAddressId())
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado"));

        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setStreet(existingAddress.getLogradouro());
        orderAddress.setNumber(existingAddress.getNumero());
        orderAddress.setComplement(existingAddress.getComplemento());
        orderAddress.setNeighborhood(existingAddress.getBairro());
        orderAddress.setCity(existingAddress.getCidade());
        orderAddress.setState(existingAddress.getEstado());
        orderAddress.setPostalCode(existingAddress.getCep());

        return orderAddress;
    }

    // Metodo para criar os PaymentCards
    private List<PaymentCard> createPaymentCards(CreateOrderRequest request) {
        List<PaymentCard> paymentCards = new ArrayList<>();
        if (request.getPaymentCards() != null && !request.getPaymentCards().isEmpty()) {
            paymentCards = request.getPaymentCards().stream()
                    .map(paymentCardRequest -> {
                        PaymentCard card = new PaymentCard();
                        card.setCardNumber(paymentCardRequest.getCardNumber());
                        card.setAmountPaid(paymentCardRequest.getAmountPaid());
                        return card;
                    })
                    .collect(Collectors.toList());
        }
        return paymentCards;
    }

    // Metodo para calcular o total do pedido antes dos descontos
    private Double calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToDouble(item -> item.getProductPriceAtOrder() * item.getQuantity())
                .sum();
    }

    // Metodo para aplicar os cupons de desconto
    private Double applyDiscountCoupons(CreateOrderRequest request, Double totalAmount) {
        if (request.getDiscountCouponId() != null) {
            Cupom discountCoupon = cupomRepository.findById(request.getDiscountCouponId())
                    .orElseThrow(() -> new IllegalArgumentException("Cupom de desconto inválido"));
            totalAmount -= discountCoupon.calcularDesconto(totalAmount);
        }

        if (request.getRefundCouponId() != null) {
            Cupom refundCoupon = cupomRepository.findById(request.getRefundCouponId())
                    .orElseThrow(() -> new IllegalArgumentException("Cupom de reembolso inválido"));
            totalAmount -= refundCoupon.calcularDesconto(totalAmount);
        }

        return totalAmount;
    }

    // Metodo para verificar se o pagamento é maior que o valor do pedido
    private boolean isPaymentGreaterThanOrderAmount(List<PaymentCard> paymentCards, Double totalAmount) {
        Double totalPaidByCards = paymentCards.stream()
                .mapToDouble(PaymentCard::getAmountPaid)
                .sum();
        return totalPaidByCards > totalAmount;
    }

    // Metodo para criar o pedido com status de "PAGAMENTO REPROVADO"
    private Order createRejectedOrder(CreateOrderRequest request, List<OrderItem> orderItems,
                                      List<PaymentCard> paymentCards, OrderAddress orderAddress, Double totalAmount) {
        Order order = new Order();
        order.setClientCPF(request.getClientCPF());
        order.setOrderDate(LocalDate.now());
        order.setRefundCouponId(request.getRefundCouponId());
        order.setDiscountCouponId(request.getDiscountCouponId());
        order.setDeliveryAddress(orderAddress);
        order.setContactPhoneNumber(request.getContactPhoneNumber());
        order.setStatus(OrderStatus.PAGAMENTO_REPROVADO);
        order.setItems(orderItems);
        order.setPaymentCards(paymentCards);
        order.setTotalAmount(totalAmount);

        orderItems.forEach(item -> item.setOrder(order));
        paymentCards.forEach(card -> card.setOrder(order));

        return orderRepository.save(order);
    }

    // Metodo para criar o pedido com status de "EM PROCESSAMENTO"
    private Order createProcessedOrder(CreateOrderRequest request, List<OrderItem> orderItems,
                                       List<PaymentCard> paymentCards, OrderAddress orderAddress, Double totalAmount) {
        Order order = new Order();
        order.setClientCPF(request.getClientCPF());
        order.setOrderDate(LocalDate.now());
        order.setRefundCouponId(request.getRefundCouponId());
        order.setDiscountCouponId(request.getDiscountCouponId());
        order.setDeliveryAddress(orderAddress);
        order.setContactPhoneNumber(request.getContactPhoneNumber());
        order.setStatus(OrderStatus.EM_PROCESSAMENTO);
        order.setItems(orderItems);
        order.setPaymentCards(paymentCards);
        order.setTotalAmount(totalAmount);

        orderItems.forEach(item -> item.setOrder(order));
        paymentCards.forEach(card -> card.setOrder(order));

        cartService.clearCartByCpf(request.getClientCPF());

        return orderRepository.save(order);
    }



}