package br.com.les.backend.les.src.application.controllers.cupomController;

import br.com.les.backend.les.src.application.dto.cupomDTO.CupomDescontoDTO;
import br.com.les.backend.les.src.application.dto.cupomDTO.CupomDevolucaoDTO;
import br.com.les.backend.les.src.model.cuponsModels.Cupom;
import br.com.les.backend.les.src.model.cuponsModels.CupomDesconto;
import br.com.les.backend.les.src.model.cuponsModels.CupomDevolucao;
import br.com.les.backend.les.src.service.cupomService.CupomDescontoService;
import br.com.les.backend.les.src.service.cupomService.CupomDevolucaoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cupons")
@AllArgsConstructor
public class CupomController {
    private final CupomDescontoService cupomDescontoService;

    private final CupomDevolucaoService cupomDevolucaoService;

    @PostMapping("/desconto")
    public ResponseEntity<?> criarDesconto(@RequestBody CupomDescontoDTO dto) {
        try {
            return ResponseEntity.ok(cupomDescontoService.criar(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desconto/{codigo}")
    public ResponseEntity<?> atualizarDesconto(@PathVariable String codigo, @RequestBody CupomDescontoDTO dto) {
        try {
            return ResponseEntity.ok(cupomDescontoService.atualizar(dto));
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/devolucao")
    public ResponseEntity<?> criarDevolucao(@RequestBody CupomDevolucaoDTO dto) {
        try {
            return ResponseEntity.ok(cupomDevolucaoService.criar(dto));
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<CupomDesconto>> listarTodos() {
        List<CupomDesconto> cupons = cupomDescontoService.listar();
        return ResponseEntity.ok(cupons);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<CupomDesconto> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(cupomDescontoService.buscarPorCodigoCupom(codigo));
    }

    @GetMapping("/client/{cpf}")
    public ResponseEntity<List<CupomDevolucao>> listarCuponsDevolucao (@PathVariable String cpf){
        List<CupomDevolucao> cupons = cupomDevolucaoService.listarCuponsCliente(cpf);
        return ResponseEntity.ok(cupons);
    }



}

