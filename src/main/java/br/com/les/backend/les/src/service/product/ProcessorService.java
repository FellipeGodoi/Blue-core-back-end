package br.com.les.backend.les.src.service.product;

import br.com.les.backend.les.src.application.dto.ProcessorDTO;
import br.com.les.backend.les.src.model.productModels.GPU;
import br.com.les.backend.les.src.model.productModels.Processor;
import br.com.les.backend.les.src.model.productModels.Socket;
import br.com.les.backend.les.src.repostory.productRepository.GPUProcessorRespository;
import br.com.les.backend.les.src.repostory.productRepository.ProcessorRepository;
import br.com.les.backend.les.src.repostory.productRepository.SocketProcessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<ProcessorDTO> getFilteredProcessors(String socketModel, String gpuModel, Boolean hasIntegratedGraphics, String brand) {
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

        return processors.stream()
                .map(ProcessorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<Processor> getInactiveProcessors() {
        return processorRepository.findByPodeSerVendidoFalse();
    }

    public Optional<Processor> getProcessorById(Long id) {
        return processorRepository.findById(id);
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
