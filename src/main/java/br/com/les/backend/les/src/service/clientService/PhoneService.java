package br.com.les.backend.les.src.service.clientService;

import br.com.les.backend.les.src.application.dto.error.RecursoNaoEncontradoException;
import br.com.les.backend.les.src.model.clientModels.Client;
import br.com.les.backend.les.src.model.clientModels.Phone;
import br.com.les.backend.les.src.repostory.clientRepository.ClientRepository;
import br.com.les.backend.les.src.repostory.clientRepository.PhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PhoneService {

    private PhoneRepository phoneRepository;
    private ClientRepository clientRepository;

    public Phone createPhone( String cpf, Phone phone){
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(()-> new RuntimeException("Client não encontrado"));

        phone.setClient(client);
        return phoneRepository.save(phone);
    }

    public Optional<Phone> getPhoneByPhoneId(Long id){
        return phoneRepository.findById(id);
    }

    public Phone updatePhone(Long phoneId, Phone updatedPhone){
        return phoneRepository.findById(phoneId)
                .map(phone -> {
                    phone.setPhoneName(updatedPhone.getPhoneName());
                    phone.setAreaCode(updatedPhone.getAreaCode());
                    phone.setPhoneNumber(updatedPhone.getPhoneNumber());
                    phone.setPhonetype(updatedPhone.getPhonetype());
                    return phoneRepository.save(phone);
                })
                .orElseThrow(()-> new RecursoNaoEncontradoException("telefone não encontrado"));
    }

    public void deletePhone(Long phoneId){
        if (!phoneRepository.existsById(phoneId)) {
            throw new RuntimeException("Telefone com ID " + phoneId + " não encontrado");
        }
        phoneRepository.deleteById(phoneId);
    }
}
