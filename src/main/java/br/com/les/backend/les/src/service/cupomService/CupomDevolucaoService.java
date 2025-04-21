package br.com.les.backend.les.src.service.cupomService;

import br.com.les.backend.les.src.application.dto.cupomDTO.CupomDevolucaoDTO;
import br.com.les.backend.les.src.model.clientModels.Client;
import br.com.les.backend.les.src.model.cuponsModels.CupomDevolucao;
import br.com.les.backend.les.src.repostory.clientRepository.ClientRepository;
import br.com.les.backend.les.src.repostory.cupomRepository.CupomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CupomDevolucaoService {
    private final CupomRepository cupomRepository;
    private final ClientRepository clientRepository;

    public CupomDevolucao criar(CupomDevolucaoDTO dto) {
        if (dto.valor == null || dto.valor <= 0) {
            throw new IllegalArgumentException("O valor da devolução deve ser maior que zero.");
        }

        Client cliente = clientRepository.findByCpf(dto.cpfClient)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        CupomDevolucao cupom = new CupomDevolucao();
        cupom.setValor(dto.valor);
        cupom.setClient(cliente);
        cupom.setValidade(dto.validade);
        cupom.setUsado(false);

        return cupomRepository.save(cupom);
    }

    public List<CupomDevolucao> listarCuponsCliente(String cpfCliente) {
        return cupomRepository.findByClientCpf(cpfCliente);
    }


}

