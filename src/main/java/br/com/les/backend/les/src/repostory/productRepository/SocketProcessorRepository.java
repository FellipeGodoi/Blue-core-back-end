package br.com.les.backend.les.src.repostory.productRepository;

import br.com.les.backend.les.src.model.productModels.Socket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocketProcessorRepository extends JpaRepository<Socket, Long> {
    Optional<Socket> findByModelo(String modelo);
}
