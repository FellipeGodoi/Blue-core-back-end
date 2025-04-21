package br.com.les.backend.les.src.application.dto.cupomDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CupomDescontoDTO {
    public String codigoCupom;
    public Double percentual;
    public Double valorMinimo;
    public LocalDate validade;
}