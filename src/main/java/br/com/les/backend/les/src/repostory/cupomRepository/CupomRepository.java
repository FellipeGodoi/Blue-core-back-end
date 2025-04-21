package br.com.les.backend.les.src.repostory.cupomRepository;

import br.com.les.backend.les.src.model.cuponsModels.Cupom;
import br.com.les.backend.les.src.model.cuponsModels.CupomDevolucao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CupomRepository extends JpaRepository<Cupom, Long> {

    List<CupomDevolucao> findByClientCpf(String clienteCpf);

}
