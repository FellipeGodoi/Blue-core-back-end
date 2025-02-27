package br.com.les.backend.les.src.application.controllers.clientController;

import br.com.les.backend.les.src.application.dto.clientDTO.ClientDTO;
import br.com.les.backend.les.src.application.dto.error.RecursoNaoEncontradoException;
import br.com.les.backend.les.src.model.clientModels.Client;
import br.com.les.backend.les.src.service.clientService.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> listarClientes(
            @RequestParam(required = false) String filter
    ) {
        if (filter != null && !filter.isEmpty()) {
            List<ClientDTO> clientes = clientService.searchClients(filter);
            if (clientes.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhum cliente encontrado com esses cpf ou nome");
            }
            return ResponseEntity.ok(clientes);
        }
        List<ClientDTO> clientes = clientService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/active/{cpf}")
    public ResponseEntity<Client> activateClient(@PathVariable String cpf) {
        Optional<Client> client = clientService.swapActiveClient(cpf);
        return client.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Client> listarCpf( @PathVariable String cpf){
        Optional<Client> client = clientService.getClientByCpf(cpf);
        return client.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client newClient = clientService.createClient(client);
        return ResponseEntity.status(201).body(newClient);
    }
    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<Void> deleteClient(@PathVariable String cpf) {
        boolean isDeleted = clientService.deleteClient(cpf);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/cpf/{cpf}")
    public ResponseEntity<Client> updateClient(@PathVariable String cpf, @RequestBody Client clientDetails) {
        if (clientService.verifyActiveClient(cpf)){
            Optional<Client> updatedClient = clientService.updateClient(cpf, clientDetails);
            return updatedClient.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }
        throw new RecursoNaoEncontradoException("a conta do cliente precisa estar ativa para realizar essa ação");
    }

}
