package br.com.les.backend.les.scripts;

import br.com.les.backend.les.src.model.clientModels.Client;
import br.com.les.backend.les.src.model.enums.Gender;
import br.com.les.backend.les.src.repostory.clientRepository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class ClientData implements CommandLineRunner {
    private final ClientRepository clientRepository;

    public ClientData(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (clientRepository.count() == 0) {
            Client[] clients = {
                    new Client("12345678901", "mario.silva@example.com", "Mário Silva", LocalDate.parse("1990-05-20"), "password123", Gender.MASCULINO),
                    new Client("23456789012", "ana.souza@example.com", "Ana Souza", LocalDate.parse("1985-08-22"), "password456", Gender.FEMININO),
                    new Client("34567890123", "bruno.oliveira@example.com", "Bruno Oliveira", LocalDate.parse("1992-01-30"), "password789", Gender.MASCULINO),
                    new Client("45678901234", "lucia.martins@example.com", "Lucia Martins", LocalDate.parse("1987-12-10"), "password321", Gender.FEMININO),
                    new Client("56789012345", "fernando.costa@example.com", "Fernando Costa", LocalDate.parse("1995-07-05"), "password654", Gender.MASCULINO),
                    new Client("67890123456", "patricia.silva@example.com", "Patricia Silva", LocalDate.parse("2000-03-25"), "password987", Gender.FEMININO),
                    new Client("78901234567", "carlos.pereira@example.com", "Carlos Pereira", LocalDate.parse("1983-11-17"), "password741", Gender.MASCULINO),
                    new Client("89012345678", "mariana.rodrigues@example.com", "Mariana Rodrigues", LocalDate.parse("1998-04-08"), "password852", Gender.FEMININO),
                    new Client("90123456789", "thiago.gomes@example.com", "Thiago Gomes", LocalDate.parse("1993-06-20"), "password963", Gender.MASCULINO),
                    new Client("01234567890", "juliana.oliveira@example.com", "Juliana Oliveira", LocalDate.parse("1996-09-15"), "password258", Gender.FEMININO)
            };

            Arrays.stream(clients).forEach(clientRepository::save);
            System.out.println("10 clientes populados no banco de dados.");
        }
    }

}
