package br.com.les.backend.les.src.service.cupomService;

import br.com.les.backend.les.src.application.dto.cupomDTO.CupomDescontoDTO;
import br.com.les.backend.les.src.model.cuponsModels.CupomDesconto;
import br.com.les.backend.les.src.repostory.cupomRepository.CupomDescontoRepository;
import br.com.les.backend.les.src.repostory.cupomRepository.CupomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CupomDescontoService {
    private final  CupomRepository cupomRepository;
    private final CupomDescontoRepository cupomDescontoRepository;

    public CupomDesconto criar(CupomDescontoDTO dto) {
        if (dto.percentual == null || dto.percentual <= 0 || dto.valorMinimo == null || dto.valorMinimo <= 0) {
            throw new IllegalArgumentException("Percentual e valor mínimo devem ser maiores que zero.");
        }

        CupomDesconto cupom = new CupomDesconto();
        cupom.setCodigoCupom(dto.codigoCupom);
        cupom.setPercentual(dto.percentual);
        cupom.setValorMinimo(dto.valorMinimo);
        cupom.setValidade(dto.validade);
        cupom.setUsado(false);

        return cupomRepository.save(cupom);
    }

    public CupomDesconto atualizar(CupomDescontoDTO dto) {


        if (dto.percentual == null || dto.percentual <= 0 || dto.valorMinimo == null || dto.valorMinimo <= 0) {
            throw new IllegalArgumentException("Percentual e valor mínimo devem ser maiores que zero.");
        }

        CupomDesconto cupom = cupomDescontoRepository.findByCodigoCupom(dto.codigoCupom);
        cupom.setPercentual(dto.percentual);
        cupom.setValorMinimo(dto.valorMinimo);
        cupom.setValidade(dto.validade);

        return cupomRepository.save(cupom);
    }

    public List<CupomDesconto> listar() {
        return cupomDescontoRepository.findAll();
    }

    public CupomDesconto buscarPorCodigoCupom(String cpf) {
        return cupomDescontoRepository.findByCodigoCupomAndUsadoFalse(cpf);
    }


}
