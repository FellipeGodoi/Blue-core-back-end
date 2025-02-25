package br.com.les.backend.les.src.repostory.clientRepository;

import br.com.les.backend.les.src.model.clientModels.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {

}
