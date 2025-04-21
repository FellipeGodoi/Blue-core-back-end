package br.com.les.backend.les.src.application.dto.cupomDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CupomDevolucaoDTO {
    public Double valor;
    public String cpfClient;
    public LocalDate validade;
}
