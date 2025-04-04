package br.com.les.backend.les.src.application.controllers.productController;

import br.com.les.backend.les.src.application.dto.ProcessorDTO;
import br.com.les.backend.les.src.model.productModels.Processor;
import br.com.les.backend.les.src.service.product.ProcessorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
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
    public Page<ProcessorDTO> getFilteredProcessors(
            @RequestParam(required = false) String socketModel,
            @RequestParam(required = false) String gpuModel,
            @RequestParam(required = false) Boolean hasIntegratedGraphics,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return processorService.getFilteredProcessors(socketModel, gpuModel, hasIntegratedGraphics, brand, searchQuery, sortBy, page, size);
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
