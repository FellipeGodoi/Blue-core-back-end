package br.com.les.backend.les.src.model.clientModels;

import br.com.les.backend.les.src.model.enums.TipoLogradouro;
import br.com.les.backend.les.src.model.enums.TipoResidencial;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String apelidoEndereco;
    private String cep;
    private String numero;
    private String estado;
    private String cidade;
    private TipoLogradouro tipoLogradouro;
    private String logradouro;
    private TipoResidencial tipoResidencial;
    private String bairro;
    private String complemento;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_cpf", nullable = false)
    private Client client;

    public Address (String apelidoEndereco,
                    String cep,
                    String numero,
                    String estado,
                    String cidade,
                    TipoLogradouro tipoLogradouro,
                    String Logradouro,
                    String bairro,
                    String complemento) {
        this.apelidoEndereco = apelidoEndereco;
        this.cep = cep;
        this.numero = numero;
        this.estado = estado;
        this.cidade = cidade;
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = Logradouro;
        this.bairro = bairro;
        this.complemento = complemento;

    }

}
