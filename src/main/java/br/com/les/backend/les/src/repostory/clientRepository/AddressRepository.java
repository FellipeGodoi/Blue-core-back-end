package br.com.les.backend.les.src.repostory.clientRepository;

import br.com.les.backend.les.src.model.clientModels.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
