package br.com.les.backend.les.scripts;

import br.com.les.backend.les.src.model.clientModels.Client;
import br.com.les.backend.les.src.model.clientModels.Phone;
import br.com.les.backend.les.src.model.enums.PhoneType;
import br.com.les.backend.les.src.repostory.clientRepository.ClientRepository;
import br.com.les.backend.les.src.repostory.clientRepository.PhoneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PhoneData implements CommandLineRunner {

    private final PhoneRepository phoneRepository;
    private final ClientRepository clientRepository;

    public PhoneData(PhoneRepository phoneRepository, ClientRepository clientRepository) {
        this.phoneRepository = phoneRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (phoneRepository.count() == 0) {
            List<Client> clients = clientRepository.findAll();

            if (clients.isEmpty()) {
                System.out.println("Nenhum cliente encontrado. Execute ClientData primeiro.");
                return;
            }

            clients.forEach(client -> {
                Phone phone1 = new Phone(null, "Celular Pessoal", "11", generatePhoneNumber(), PhoneType.CELULAR, client);
                Phone phone2 = new Phone(null, "Residencial", "11", generatePhoneNumber(), PhoneType.RESIDENCIAL, client);

                phoneRepository.saveAll(List.of(phone1, phone2));
            });

            System.out.println("Telefones populados no banco de dados.");
        }
    }

    private String generatePhoneNumber() {
        return String.valueOf((long) (Math.random() * 900000000L + 100000000L));
    }
}