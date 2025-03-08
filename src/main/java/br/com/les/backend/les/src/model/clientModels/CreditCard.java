package br.com.les.backend.les.src.model.clientModels;

import br.com.les.backend.les.src.model.enums.CardBrands;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String apelidoCartao;
    private String numeroCartao;
    private CardBrands bandeira;
    private LocalDate vencimentoDoCartao;
    private String nomePresenteNoCartao;
    private String codigoSeguranca;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_cpf", nullable = false)
    private Client client;

    public CreditCard(String apelidoCartao,
                      String numeroCartao,
                      CardBrands bandeira,
                      LocalDate vencimentoDoCartao,
                      String nomePresenteNoCartao,
                      String codigoSeguranca,
                      Client cliente)
    {
        this.apelidoCartao = apelidoCartao;
        this.numeroCartao = numeroCartao;
        this.bandeira = bandeira;
        this.vencimentoDoCartao = vencimentoDoCartao;
        this.nomePresenteNoCartao = nomePresenteNoCartao;
        this.codigoSeguranca = codigoSeguranca;
        this.client = cliente;
    }

}
