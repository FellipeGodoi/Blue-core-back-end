package br.com.les.backend.les.src.application.controllers.clientController;

import br.com.les.backend.les.src.model.clientModels.CreditCard;
import br.com.les.backend.les.src.service.clientService.CreditCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/card")
public class CreditCardController {
    private final CreditCardService creditCardService;

    @PostMapping("/{cpf}")
    public ResponseEntity<CreditCard> creditCard(@PathVariable String cpf, @RequestBody CreditCard creditCard) {
        return ResponseEntity.ok( creditCardService.createCreditCard(cpf, creditCard) );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CreditCard>> creditCard(@PathVariable Long id) {
        return ResponseEntity.ok(creditCardService.getCreditCard(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCard> updateCreditCard(@PathVariable Long id, @RequestBody CreditCard creditCard) {
        return ResponseEntity.ok(creditCardService.updateCreditCard(id, creditCard));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable Long id) {
        creditCardService.deleteCreditCard(id);
        return ResponseEntity.noContent().build();
    }
}
