package br.com.les.backend.les.src.repostory.productRepository;

import br.com.les.backend.les.src.model.productModels.Processor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProcessorRepository extends JpaRepository<Processor, Long> {
    List<Processor> findByPodeSerVendidoTrue();
    List<Processor> findByPodeSerVendidoFalse();
    Optional<Processor> findByCodigo(String codigo);
}
