package br.com.les.backend.les.src.repostory.productRepository;

import br.com.les.backend.les.src.model.productModels.GPU;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GPUProcessorRespository extends JpaRepository <GPU, Long> {
    Optional<GPU> findByModelo(String modelo);
}
