package br.com.les.backend.les.src.service.clientService;

import br.com.les.backend.les.src.application.dto.error.RecursoNaoEncontradoException;
import br.com.les.backend.les.src.model.clientModels.Client;
import br.com.les.backend.les.src.model.clientModels.CreditCard;
import br.com.les.backend.les.src.repostory.clientRepository.ClientRepository;
import br.com.les.backend.les.src.repostory.clientRepository.CreditCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final ClientRepository clientRepository;

    public CreditCard createCreditCard(String cpf, CreditCard creditCard) {
        Client cliente = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        creditCard.setClient(cliente);
        return creditCardRepository.save(creditCard);
    }

    public Optional<CreditCard> getCreditCard(Long id) {
        return creditCardRepository.findById(id);
    }

    public CreditCard updateCreditCard(Long id, CreditCard updatedCreditCard) {
        return creditCardRepository.findById(id)
                .map( card -> {
                            card.setApelidoCartao(updatedCreditCard.getApelidoCartao());
                            card.setCodigoSeguranca(updatedCreditCard.getCodigoSeguranca());
                            card.setBandeira(updatedCreditCard.getBandeira());
                            card.setVencimentoDoCartao(updatedCreditCard.getVencimentoDoCartao());
                            card.setNomePresenteNoCartao(updatedCreditCard.getNomePresenteNoCartao());
                            card.setNumeroCartao(updatedCreditCard.getNumeroCartao());
                            return creditCardRepository.save(card);
                        }
                ).orElseThrow(()-> new RecursoNaoEncontradoException("cartão não encontrado"));
    }

    public void deleteCreditCard(Long id) {
        if (!creditCardRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("cartão não encontrado");
        }
        creditCardRepository.deleteById(id);
    }
}
