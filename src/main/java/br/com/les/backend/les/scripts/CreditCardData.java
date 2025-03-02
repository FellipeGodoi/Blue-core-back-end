package br.com.les.backend.les.scripts;

import br.com.les.backend.les.src.model.clientModels.Client;
import br.com.les.backend.les.src.model.clientModels.CreditCard;
import br.com.les.backend.les.src.model.enums.CardBrands;
import br.com.les.backend.les.src.repostory.clientRepository.ClientRepository;
import br.com.les.backend.les.src.repostory.clientRepository.CreditCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
public class CreditCardData implements CommandLineRunner {
    private final ClientRepository clientRepository;
    private final CreditCardRepository creditCardRepository;

    @Override
    public void run(String... args) throws Exception {
        if (creditCardRepository.count() == 0) {
            List<Client> clients = clientRepository.findAll();

            if (clients.isEmpty()) {
                System.out.println("Nenhum cliente encontrado. Execute ClientData primeiro.");
                return;
            }

            clients.forEach(client -> {
                CreditCard card1 = new CreditCard(
                        "Cartão Principal",
                        generateCardNumber(),
                        CardBrands.VISA,
                        generateExpirationDate(),
                        client.getNameClient(),
                        generateCodigoDeSeguranca(),
                        client
                );

                CreditCard card2 = new CreditCard(
                        "Cartão Secundário",
                        generateCardNumber(),
                        CardBrands.MASTERCARD,
                        generateExpirationDate(),
                        client.getNameClient(),
                        generateCodigoDeSeguranca(),
                        client
                );

                creditCardRepository.saveAll(List.of(card1, card2));
            });

            System.out.println("Cartões de crédito populados no banco de dados.");
        }
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    private LocalDate generateExpirationDate() {
        int year = LocalDate.now().getYear() + (new Random().nextInt(5) + 1);
        int month = new Random().nextInt(12) + 1;
        return LocalDate.of(year, month, 1);
    }

    private String generateCodigoDeSeguranca() {
        return String.valueOf(new Random().nextInt(1000) + 1);
    }
}
