package br.com.les.backend.les.scripts;

import br.com.les.backend.les.src.model.clientModels.Address;
import br.com.les.backend.les.src.model.clientModels.Client;
import br.com.les.backend.les.src.model.enums.TipoLogradouro;
import br.com.les.backend.les.src.model.enums.TipoResidencial;
import br.com.les.backend.les.src.repostory.clientRepository.AddressRepository;
import br.com.les.backend.les.src.repostory.clientRepository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class AddressData implements CommandLineRunner {

    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;

    public AddressData(AddressRepository addressRepository, ClientRepository clientRepository) {
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (addressRepository.count() == 0) {
            List<Client> clients = clientRepository.findAll();

            if (clients.isEmpty()) {
                System.out.println("Nenhum cliente encontrado. Execute ClientData primeiro.");
                return;
            }

            clients.forEach(client -> {
                Address address1 = new Address(null, "Casa", generateCep(), generateNumero(), "SP", "São Paulo",
                        TipoLogradouro.RUA, "Rua das Flores", TipoResidencial.CASA, "Centro", "Bloco A", client);

                Address address2 = new Address(null, "Trabalho", generateCep(), generateNumero(), "RJ", "Rio de Janeiro",
                        TipoLogradouro.AVENIDA, "Avenida Brasil", TipoResidencial.APARTAMENTO, "Copacabana", "Apto 101", client);

                addressRepository.saveAll(List.of(address1, address2));
            });

            System.out.println("Endereços populados no banco de dados.");
        }
    }

    private String generateCep() {
        Random random = new Random();
        return String.format("%05d-%03d", random.nextInt(100000), random.nextInt(1000));
    }

    private String generateNumero() {
        return String.valueOf(new Random().nextInt(1000) + 1);
    }
}
