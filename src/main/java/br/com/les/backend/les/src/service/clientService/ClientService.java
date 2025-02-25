package br.com.les.backend.les.src.service.clientService;

import br.com.les.backend.les.src.application.dto.clientDTO.ClientDTO;
import br.com.les.backend.les.src.repostory.clientRepository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clienteRepository;

    public List<ClientDTO> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(client -> new ClientDTO(client.getIdClient(), client.getNameClient(), client.getCpf()))
                .collect(Collectors.toList());
    }
}
