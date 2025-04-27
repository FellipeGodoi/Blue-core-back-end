package br.com.les.backend.les.src.service.cartService;

import br.com.les.backend.les.src.model.cartModels.Cart;
import br.com.les.backend.les.src.model.cartModels.CartItem;
import br.com.les.backend.les.src.model.clientModels.Client;
import br.com.les.backend.les.src.model.productModels.Processor;
import br.com.les.backend.les.src.repostory.cartRepository.CartRepository;
import br.com.les.backend.les.src.repostory.cartRepository.ItemCartRepository;
import br.com.les.backend.les.src.repostory.clientRepository.ClientRepository;
import br.com.les.backend.les.src.repostory.productRepository.ProcessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProcessorRepository processorRepository;
    private final ClientRepository clientRepository;

    public List<CartItem> getCartItemsByClientCpf(String cpf) {
        List<CartItem> cartItems = getCartByCpf(cpf).getItens();
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Carrinho não encontrado" + cpf);
        }
        return cartItems;
    }

    public Cart getCartByCpf(String cpf) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return cartRepository.findByCliente(client)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));
    }


    public Cart addItemToCartByCpf(String cpf, Long processorId, int qtd) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Cart cart = client.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setCliente(client);
            cart.setItens(new ArrayList<>());
            cart.setTotal(0.0);
            client.setCart(cart);
        }

        Processor processor = processorRepository.findById(processorId)
                .orElseThrow(() -> new RuntimeException("Processador não encontrado"));

        if (processor.getEstoque() < qtd && qtd > 0) {
            throw new RuntimeException("Estoque insuficiente para o processador");
        }

        Optional<CartItem> existingItemOpt = cart.getItens().stream()
                .filter(item -> item.getProcessor().getId().equals(processorId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem item = existingItemOpt.get();

            if (qtd <= 0) {
                cart.getItens().remove(item);
            } else {
                item.setQtd(qtd);
                item.setPrecoTotal(processor.getPrecoVenda() * qtd);
                item.setPodeVender(true);
            }
        } else {
            if (qtd > 0) {
                CartItem newItem = new CartItem();
                newItem.setCart(cart);
                newItem.setProcessor(processor);
                newItem.setQtd(qtd);
                newItem.setPrecoTotal(processor.getPrecoVenda() * qtd);
                newItem.setPodeVender(true);
                cart.getItens().add(newItem);
            }
        }

        updateCartTotal(cart);
        return cartRepository.save(cart);
    }

    public void clearCartByCpf(String cpf) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Cart cart = client.getCart();
        if (cart != null) {
            cart.getItens().clear();
            cart.setTotal(0.0);
            cartRepository.save(cart);
        }
    }

    private void updateCartTotal(Cart cart) {
        double total = cart.getItens().stream()
                .mapToDouble(CartItem::getPrecoTotal)
                .sum();
        cart.setTotal(total);
    }
}

