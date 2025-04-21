package br.com.les.backend.les.src.application.dto.cartDTO;

import br.com.les.backend.les.src.model.cartModels.Cart;
import br.com.les.backend.les.src.model.cartModels.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CartResponseDTO {
    private Long id;
    private Double total;
    private List<CartItem> itens;

    public CartResponseDTO(Cart cart) {
        this.id = cart.getId();
        this.total = cart.getTotal();
        this.itens = cart.getItens();
    }

}
