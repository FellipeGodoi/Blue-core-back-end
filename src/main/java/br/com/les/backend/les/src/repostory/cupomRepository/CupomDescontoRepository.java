package br.com.les.backend.les.src.repostory.cupomRepository;

import br.com.les.backend.les.src.model.cuponsModels.CupomDesconto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CupomDescontoRepository extends JpaRepository<CupomDesconto, Long> {
    CupomDesconto findByCodigoCupomAndUsadoFalse(String codigoCupom);

    CupomDesconto findByCodigoCupom(String codigoCupom);
}
