package br.com.les.backend.les.src.application.controllers.clientController;

import br.com.les.backend.les.src.application.dto.clientDTO.ClientDTO;
import br.com.les.backend.les.src.service.clientService.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> listarClientes() {
        List<ClientDTO> clientes = clientService.listarClientes();
        return ResponseEntity.ok(clientes);
    }
}
