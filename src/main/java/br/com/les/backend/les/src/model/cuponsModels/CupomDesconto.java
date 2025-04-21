package br.com.les.backend.les.src.model.cuponsModels;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CupomDesconto extends Cupom {

    @Column(nullable = false, unique = true)
    public String codigoCupom;
    private Double percentual;
    private Double valorMinimo;

    @Override
    public Double calcularDesconto(Double totalCompra) {
        if (totalCompra >= valorMinimo) {
            return totalCompra * percentual;
        }
        return 0.0;
    }
}
