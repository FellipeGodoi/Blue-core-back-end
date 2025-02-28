package br.com.les.backend.les.src.application.controllers.clientController;

import br.com.les.backend.les.src.model.clientModels.Address;
import br.com.les.backend.les.src.service.clientService.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/address")
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/{cpf}")
    public ResponseEntity<Address> addAddress(@PathVariable String cpf, @RequestBody Address address) {
        return ResponseEntity.ok(addressService.createAddress(cpf, address));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Address>> getAddress(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddress(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        return ResponseEntity.ok(addressService.uptadeAddress(id, address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Address> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
