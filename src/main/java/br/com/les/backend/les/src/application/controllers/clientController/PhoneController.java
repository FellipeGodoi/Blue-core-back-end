package br.com.les.backend.les.src.application.controllers.clientController;

import br.com.les.backend.les.src.model.clientModels.Phone;
import br.com.les.backend.les.src.service.clientService.PhoneService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/phone")
@AllArgsConstructor
public class PhoneController {
    private final PhoneService phoneService;

    @PostMapping("/{cpf}")
    public ResponseEntity<Phone> createPhone(@PathVariable String cpf, @RequestBody Phone phone) {
        return ResponseEntity.ok(phoneService.createPhone(cpf, phone));
    }

    @GetMapping("/{phoneId}")
    public ResponseEntity<Optional<Phone>> getPhoneById(@PathVariable Long phoneId) {
        return ResponseEntity.ok(phoneService.getPhoneByPhoneId(phoneId));
    }

    @PutMapping("/{phoneID}")
    public ResponseEntity<Phone> updatePhone(@PathVariable Long phoneID, @RequestBody Phone phone) {
        return ResponseEntity.ok(phoneService.updatePhone(phoneID, phone));
    }

    @DeleteMapping("/{phoneId}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long phoneId) {
        phoneService.deletePhone(phoneId);
        return ResponseEntity.noContent().build();
    }


}
