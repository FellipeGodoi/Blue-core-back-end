package br.com.les.backend.les.src.service.clientService;

import br.com.les.backend.les.src.application.dto.error.RecursoNaoEncontradoException;
import br.com.les.backend.les.src.model.clientModels.Address;
import br.com.les.backend.les.src.model.clientModels.Client;
import br.com.les.backend.les.src.repostory.clientRepository.AddressRepository;
import br.com.les.backend.les.src.repostory.clientRepository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;

    public Address createAddress(String cpf, Address address) {
        Client client = clientRepository.findByCpf(cpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Client não encontrado"));
        address.setClient(client);
        return addressRepository.save(address);
    }

    public Optional<Address> getAddress(Long id) {
        return addressRepository.findById(id);
    }

    public Address uptadeAddress(Long id, Address updatedAddress) {
        return addressRepository.findById(id)
                .map(address -> {
                    address.setApelidoEndereco(updatedAddress.getApelidoEndereco());
                    address.setCep(updatedAddress.getCep());
                    address.setNumero(updatedAddress.getNumero());
                    address.setEstado(updatedAddress.getEstado());
                    address.setCidade(updatedAddress.getEstado());
                    address.setTipoLogradouro(updatedAddress.getTipoLogradouro());
                    address.setLogradouro(updatedAddress.getLogradouro());
                    address.setTipoResidencial(updatedAddress.getTipoResidencial());
                    address.setBairro(updatedAddress.getBairro());
                    address.setComplemento(updatedAddress.getComplemento());
                    return addressRepository.save(address);
                })
                .orElseThrow(()-> new RecursoNaoEncontradoException("endereço não encontrado"));
    }

    public void deleteAddress(Long id) {
        if(!addressRepository.existsById(id)) {
            throw new RuntimeException("endereço com ID " + id + " não encontrado");
        }
        addressRepository.deleteById(id);
    }

}
