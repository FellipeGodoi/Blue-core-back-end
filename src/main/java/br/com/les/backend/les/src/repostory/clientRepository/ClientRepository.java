package br.com.les.backend.les.src.repostory.clientRepository;

import br.com.les.backend.les.src.model.clientModels.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    @Query("SELECT c FROM Client c WHERE LOWER(c.cpf) LIKE LOWER(CONCAT('%', :filter, '%')) OR LOWER(c.nameClient) LIKE LOWER(CONCAT('%', :filter, '%'))")
    List<Client> searchByCpfOrName(String filter);

    Optional<Client> findByCpf(String cpf);
}
