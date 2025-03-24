package br.com.les.backend.les.src.application.controllers.productController;

import br.com.les.backend.les.src.model.productModels.Processor;
import br.com.les.backend.les.src.service.product.ProcessorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/processors")
@AllArgsConstructor
public class ProcessorController {

    private final ProcessorService processorService;

    @PostMapping
    public ResponseEntity<Processor> createProcessor(@RequestBody Processor processor) {
        Processor savedProcessor = processorService.createProcessor(processor);
        return ResponseEntity.ok(savedProcessor);
    }

    @GetMapping
    public ResponseEntity<List<Processor>> getAllProcessors() {
        List<Processor> processors = processorService.getAllProcessors();
        return ResponseEntity.ok(processors);
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<Processor>> getInactiveProcessors() {
        List<Processor> inactiveProcessors = processorService.getInactiveProcessors();
        return ResponseEntity.ok(inactiveProcessors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processor> getProcessorById(@PathVariable Long id) {
        Optional<Processor> processor = processorService.getProcessorById(id);
        return processor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Processor> updateProcessor(@PathVariable Long id, @RequestBody Processor processor) {
        try {
            Processor updatedProcessor = processorService.updateProcessor(id, processor);
            return ResponseEntity.ok(updatedProcessor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProcessor(@PathVariable Long id) {
        processorService.deleteProcessor(id);
        return ResponseEntity.noContent().build();
    }
}
