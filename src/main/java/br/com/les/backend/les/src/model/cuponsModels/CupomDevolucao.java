package br.com.les.backend.les.src.model.cuponsModels;

import br.com.les.backend.les.src.model.clientModels.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CupomDevolucao extends Cupom {
    private Double valor;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "client_cpf", nullable = false)
    private Client client;


    @Override
    public Double calcularDesconto(Double totalCompra) {
        return Math.min(valor, totalCompra);
    }
}
