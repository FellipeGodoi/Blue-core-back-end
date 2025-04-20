package br.com.les.backend.les.src.service.product;

import br.com.les.backend.les.src.application.dto.ProcessorDTO;
import br.com.les.backend.les.src.model.productModels.GPU;
import br.com.les.backend.les.src.model.productModels.Processor;
import br.com.les.backend.les.src.model.productModels.Socket;
import br.com.les.backend.les.src.repostory.productRepository.GPUProcessorRespository;
import br.com.les.backend.les.src.repostory.productRepository.ProcessorRepository;
import br.com.les.backend.les.src.repostory.productRepository.SocketProcessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProcessorService {

    private final ProcessorRepository processorRepository;
    private final GPUProcessorRespository gpuProcessorRepository;
    private final SocketProcessorRepository socketProcessorRepository;

    public Processor createProcessor(Processor processor) {
        associateExistingGpuAndSocket(processor);
        return processorRepository.save(processor);
    }

    public List<Processor> getAllProcessors() {
        return processorRepository.findByPodeSerVendidoTrue();
    }


    public Page<ProcessorDTO> getFilteredProcessors(String socketModel, String gpuModel, Boolean hasIntegratedGraphics, String brand, String searchQuery, String sortBy, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Processor> processors = processorRepository.findAll();

        if (socketModel != null) {
            processors = processors.stream()
                    .filter(p -> p.getSocket().getModelo().equalsIgnoreCase(socketModel))
                    .collect(Collectors.toList());
        }

        if (gpuModel != null) {
            processors = processors.stream()
                    .filter(p -> p.getGpu() != null && p.getGpu().getModelo().equalsIgnoreCase(gpuModel))
                    .collect(Collectors.toList());
        }

        if (hasIntegratedGraphics != null) {
            processors = processors.stream()
                    .filter(p -> p.isDesbloqueado() == hasIntegratedGraphics)
                    .collect(Collectors.toList());
        }

        if (brand != null) {
            processors = processors.stream()
                    .filter(p -> p.getBrand().toString().equalsIgnoreCase(brand))
                    .collect(Collectors.toList());
        }

        if (searchQuery != null && !searchQuery.isBlank()) {
            String queryLower = searchQuery.toLowerCase();
            processors = processors.stream()
                    .filter(p -> p.getModelo().toLowerCase().contains(queryLower) ||
                            p.getBrand().toString().toLowerCase().contains(queryLower) ||
                            (p.getGpu() != null && p.getGpu().getModelo().toLowerCase().contains(queryLower)) ||
                            p.getSocket().getModelo().toLowerCase().contains(queryLower))
                    .collect(Collectors.toList());
        }

        // 🔹 Ordenação
        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "name_asc":
                    processors.sort(Comparator.comparing(Processor::getModelo, String.CASE_INSENSITIVE_ORDER));
                    break;
                case "name_desc":
                    processors.sort(Comparator.comparing(Processor::getModelo, String.CASE_INSENSITIVE_ORDER).reversed());
                    break;
                case "price_asc":
                    processors.sort(Comparator.comparing(Processor::getPrecoVenda));
                    break;
                case "price_desc":
                    processors.sort(Comparator.comparing(Processor::getPrecoVenda).reversed());
                    break;
                case "stock_asc":
                    processors.sort(Comparator.comparing(Processor::getEstoque));
                    break;
                case "stock_desc":
                    processors.sort(Comparator.comparing(Processor::getEstoque).reversed());
                    break;
                default:
                    break;
            }
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), processors.size());
        List<ProcessorDTO> pageContent = processors.subList(start, end).stream()
                .map(ProcessorDTO::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(pageContent, pageable, processors.size());
    }

    public List<Processor> getInactiveProcessors() {
        return processorRepository.findByPodeSerVendidoFalse();
    }

    public Optional<Processor> getProcessorById(Long id) {
        return processorRepository.findById(id);
    }

    public Optional<Processor> getProcessorByCode(String code) {
        return processorRepository.findByCodigo(code);
    }

    public Processor updateProcessor(Long id, Processor updatedProcessor) {
        return processorRepository.findById(id)
                .map(existingProcessor -> {
                    existingProcessor.setModelo(updatedProcessor.getModelo());
                    existingProcessor.setCodigo(updatedProcessor.getCodigo());
                    existingProcessor.setPrecoOriginal(updatedProcessor.getPrecoOriginal());
                    existingProcessor.setPrecoVenda(updatedProcessor.getPrecoVenda());
                    existingProcessor.setClockBase(updatedProcessor.getClockBase());
                    existingProcessor.setEstoque(updatedProcessor.getEstoque());
                    existingProcessor.setNucleos(updatedProcessor.getNucleos());
                    existingProcessor.setThreads(updatedProcessor.getThreads());
                    existingProcessor.setCacheL2(updatedProcessor.getCacheL2());
                    existingProcessor.setCacheL3(updatedProcessor.getCacheL3());
                    existingProcessor.setTdp(updatedProcessor.getTdp());
                    existingProcessor.setClockMax(updatedProcessor.getClockMax());
                    existingProcessor.setPci(updatedProcessor.getPci());
                    existingProcessor.setArquiteturaContrucao(updatedProcessor.getArquiteturaContrucao());
                    existingProcessor.setArquiteturaBits(updatedProcessor.getArquiteturaBits());
                    existingProcessor.setMemorias(updatedProcessor.getMemorias());
                    existingProcessor.setDesbloqueado(updatedProcessor.isDesbloqueado());
                    existingProcessor.setCooler(updatedProcessor.isCooler());
                    existingProcessor.setPodeSerVendido(updatedProcessor.isPodeSerVendido());
                    existingProcessor.setBrand(updatedProcessor.getBrand());
                    associateExistingGpuAndSocket(updatedProcessor);

                    return processorRepository.save(existingProcessor);
                }).orElseThrow(() -> new RuntimeException("Processador não encontrado"));
    }

    public void deleteProcessor(Long id) {
        processorRepository.findById(id).ifPresent(processor -> {
            processor.setPodeSerVendido(false);
            processorRepository.save(processor);
        });
    }

    private void associateExistingGpuAndSocket(Processor processor) {
        GPU existingGpu = gpuProcessorRepository.findByModelo(processor.getGpu().getModelo())
                .orElseGet(() -> gpuProcessorRepository.save(processor.getGpu()));

        Socket existingSocket = socketProcessorRepository.findByModelo(processor.getSocket().getModelo())
                .orElseGet(() -> socketProcessorRepository.save(processor.getSocket()));

        processor.setGpu(existingGpu);
        processor.setSocket(existingSocket);
    }
}
