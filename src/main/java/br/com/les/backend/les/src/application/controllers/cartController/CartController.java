package br.com.les.backend.les.src.application.controllers.cartController;

import br.com.les.backend.les.src.application.dto.cartDTO.CartResponseDTO;
import br.com.les.backend.les.src.model.cartModels.Cart;
import br.com.les.backend.les.src.service.cartService.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{cpf}")
    public ResponseEntity<CartResponseDTO> getCartByCpf(@PathVariable String cpf) {
        Cart cart = cartService.getCartByCpf(cpf);
        return ResponseEntity.ok(new CartResponseDTO(cart));
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> adicionarItem(
            @RequestParam String cpf,
            @RequestParam Long processorId,
            @RequestParam int qtd) {

        Cart cart = cartService.addItemToCartByCpf(cpf, processorId, qtd);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> limparCarrinho(@RequestParam String cpf) {
        cartService.clearCartByCpf(cpf);
        return ResponseEntity.noContent().build();
    }
}
