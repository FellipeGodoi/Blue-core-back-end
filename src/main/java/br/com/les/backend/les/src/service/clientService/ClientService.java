package br.com.les.backend.les.src.service.clientService;

import br.com.les.backend.les.src.application.dto.clientDTO.ClientDTO;
import br.com.les.backend.les.src.application.dto.error.RecursoNaoEncontradoException;
import br.com.les.backend.les.src.model.clientModels.Client;
import br.com.les.backend.les.src.repostory.clientRepository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<ClientDTO> searchClients(String filter) {
        return clienteRepository.searchByCpfOrName(filter).stream()
                .map(client -> new ClientDTO(client.getIdClient(), client.getNameClient(), client.getCpf()))
                .collect(Collectors.toList());
    }

    public Optional<Client> getClientByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public Client createClient(Client client) {
        return clienteRepository.save(client);
    }

    public boolean deleteClient(String cpf) {
        Optional<Client> existingClient = clienteRepository.findByCpf(cpf);
        if (existingClient.isPresent()) {
            clienteRepository.delete(existingClient.get());
            return true;
        }
        return false;
    }

    public Optional<Client> updateClient(String cpf, Client clientDetails) {
        Optional<Client> existingClient = clienteRepository.findByCpf(cpf);
        if (existingClient.isPresent()) {
            Client client = existingClient.get();
            client.setNameClient(clientDetails.getNameClient());
            client.setEmailClient(clientDetails.getEmailClient());
            client.setBirthDate(clientDetails.getBirthDate());
            client.setPassword(clientDetails.getPassword());
            client.setGender(clientDetails.getGender());
            return Optional.of(clienteRepository.save(client));
        }
        return Optional.empty();
    }




}
