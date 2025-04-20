package br.com.les.backend.les.scripts;

import br.com.les.backend.les.src.model.enums.ProcessorBrand;
import br.com.les.backend.les.src.model.productModels.GPU;
import br.com.les.backend.les.src.model.productModels.Processor;
import br.com.les.backend.les.src.model.productModels.Socket;
import br.com.les.backend.les.src.repostory.productRepository.GPUProcessorRespository;
import br.com.les.backend.les.src.repostory.productRepository.ProcessorRepository;
import br.com.les.backend.les.src.repostory.productRepository.SocketProcessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class ProcessorData implements CommandLineRunner {
    private final ProcessorRepository processorRepository;
    private final GPUProcessorRespository gpuRepository;
    private final SocketProcessorRepository socketRepository;


    @Override
    public void run(String... args) throws Exception {
        if (processorRepository.count() == 0) {

// Garantir que os sockets existam ou sejam criados
            Socket am4Socket = socketRepository.findByModelo("AM4").orElseGet(() -> socketRepository.save(new Socket(null, "AM4")));
            Socket am5Socket = socketRepository.findByModelo("AM5").orElseGet(() -> socketRepository.save(new Socket(null, "AM5")));
            Socket lga1151Socket = socketRepository.findByModelo("LGA1151").orElseGet(() -> socketRepository.save(new Socket(null, "LGA1151")));
            Socket lga1200Socket = socketRepository.findByModelo("LGA 1200").orElseGet(() -> socketRepository.save(new Socket(null, "LGA 1200")));
            Socket lga2011_3Socket = socketRepository.findByModelo("LGA 2011-3").orElseGet(() -> socketRepository.save(new Socket(null, "LGA 2011-3")));

// Garantir que as GPUs existam ou sejam criadas
            GPU vega8 = gpuRepository.findByModelo("Vega 8").orElseGet(() -> gpuRepository.save(new GPU(null, "Vega 8", 1250, 8)));
            GPU intelUHD630 = gpuRepository.findByModelo("Intel UHD Graphics 630").orElseGet(() -> gpuRepository.save(new GPU(null, "Intel UHD Graphics 630", 1100, 24)));
            GPU intelUHD750 = gpuRepository.findByModelo("Intel UHD Graphics 750").orElseGet(() -> gpuRepository.save(new GPU(null, "Intel UHD Graphics 750", 1300, 32)));



//             Processadores Intel i3, i5, i7, i9
            Processor[] intelProcessors = {
                    // i3
                    new Processor(null, "Intel Core i3-10100","i3",  generateRandomDigits() , 122.00, 112.00, 3.6, 100, 4, 4, 256, 6144, 65, 4.3, "PCIe 3.0", "14nm", "64-bit", "DDR4", true, false, true, ProcessorBrand.INTEL, intelUHD630, lga1200Socket),
                    new Processor(null, "Intel Core i3-9100F","i3",  generateRandomDigits() , 105.00, 95.00, 3.6, 80, 4, 4, 256, 6144, 65, 4.2, "PCIe 3.0", "14nm", "64-bit", "DDR4", false, false, true, ProcessorBrand.INTEL, null, lga1151Socket),
                    new Processor(null, "Intel Core i3-8300","i3",  generateRandomDigits() , 133.00, 120.00, 4.0, 60, 4, 4, 256, 6144, 62, 4.2, "PCIe 3.0", "14nm", "64-bit", "DDR4", false, false, true, ProcessorBrand.INTEL, intelUHD630, lga1151Socket),

                    // i5
                    new Processor(null, "Intel Core i5-10400","i5", generateRandomDigits() , 157.00, 147.00, 2.9, 120, 6, 6, 512, 12288, 65, 4.3, "PCIe 3.0", "14nm", "64-bit", "DDR4", true, false, true, ProcessorBrand.INTEL, intelUHD630, lga1200Socket),
                    new Processor(null, "Intel Core i5-10600K","i5", generateRandomDigits() , 262.00, 247.00, 4.1, 100, 6, 6, 512, 12288, 95, 4.8, "PCIe 3.0", "14nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.INTEL, intelUHD630, lga1200Socket),
                    new Processor(null, "Intel Core i5-11600K","i5", generateRandomDigits() , 294.00, 274.00, 3.9, 100, 6, 12, 512, 12288, 125, 4.9, "PCIe 4.0", "14nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.INTEL, intelUHD750, lga1200Socket),

                    // i7
                    new Processor(null, "Intel Core i7-10700K","i7",  generateRandomDigits() , 374.00, 349.00, 3.8, 100, 8, 16, 512, 16384, 125, 5.1, "PCIe 3.0", "14nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.INTEL, intelUHD630, lga1200Socket),
                    new Processor(null, "Intel Core i7-11700K","i7",  generateRandomDigits() , 414.00, 389.00, 3.6, 100, 8, 16, 512, 16384, 125, 5.0, "PCIe 4.0", "14nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.INTEL, intelUHD750, lga1200Socket),
                    new Processor(null, "Intel Core i7-9700K","i7",  generateRandomDigits() , 379.00, 359.00, 3.6, 100, 8, 8, 512, 12288, 95, 4.9, "PCIe 3.0", "14nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.INTEL, intelUHD630, lga1151Socket),

                    // i9
                    new Processor(null, "Intel Core i9-10900K","i9", generateRandomDigits() , 499.00, 459.00, 3.7, 100, 10, 20, 512, 20480, 125, 5.3, "PCIe 3.0", "14nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.INTEL, intelUHD630, lga1200Socket),
                    new Processor(null, "Intel Core i9-11900K","i9", generateRandomDigits() , 539.00, 499.00, 3.5, 100, 8, 16, 512, 20480, 125, 5.3, "PCIe 4.0", "14nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.INTEL, intelUHD750, lga1200Socket),
                    new Processor(null, "Intel Core i9-10850K","i9", generateRandomDigits() , 459.00, 429.00, 3.6, 100, 10, 20, 512, 20480, 125, 5.2, "PCIe 3.0", "14nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.INTEL, intelUHD630, lga1200Socket)
            };

            Processor[] ryzen3Processors = {
                    new Processor(null, "Ryzen 3 3200G","Ryzen3", generateRandomDigits() , 129.99, 119.99, 3.6, 50, 4, 4, 192, 1024, 65, 3.7, "PCIe 3.0", "12nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.AMD, vega8, am4Socket),
                    new Processor(null, "Ryzen 3 3300X","Ryzen3", generateRandomDigits() , 139.99, 129.99, 3.8, 60, 4, 4, 192, 1024, 65, 4.0, "PCIe 3.0", "12nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.AMD, null, am4Socket),
                    new Processor(null, "Ryzen 3 3100","Ryzen3", generateRandomDigits() , 99.99, 89.99, 3.6, 40, 4, 4, 192, 512, 65, 3.9, "PCIe 3.0", "12nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.AMD, null, am4Socket)
            };

            Processor[] ryzen5Processors = {
                    new Processor(null, "Ryzen 5 3600","Ryzen5",  generateRandomDigits() , 199.99, 179.99, 3.6, 80, 6, 12, 512, 12288, 65, 4.2, "PCIe 4.0", "12nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.AMD, null, am4Socket),
                    new Processor(null, "Ryzen 5 5600X","Ryzen5", generateRandomDigits() , 299.00, 279.00, 3.7, 120, 6, 12, 512, 12288, 65, 4.6, "PCIe 4.0", "7nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.AMD, null, am5Socket),
                    new Processor(null, "Ryzen 5 7600X","Ryzen5", generateRandomDigits() , 359.00, 329.00, 4.7, 100, 6, 12, 512, 12288, 65, 5.3, "PCIe 5.0", "5nm", "64-bit", "DDR5", true, true, true, ProcessorBrand.AMD, null, am5Socket)
            };

            Processor[] ryzen7Processors = {
                    new Processor(null, "Ryzen 7 3700X","Ryzen7",  generateRandomDigits() , 329.99, 309.99, 3.6, 90, 8, 16, 512, 16384, 65, 4.4, "PCIe 4.0", "12nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.AMD, null, am4Socket),
                    new Processor(null, "Ryzen 7 5800X","Ryzen7",  generateRandomDigits() , 449.00, 429.00, 3.8, 120, 8, 16, 512, 16384, 105, 4.7, "PCIe 4.0", "7nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.AMD, null, am5Socket),
                    new Processor(null, "Ryzen 7 7800X","Ryzen7",  generateRandomDigits() , 499.00, 469.00, 4.5, 100, 8, 16, 512, 16384, 125, 5.0, "PCIe 5.0", "5nm", "64-bit", "DDR5", true, true, true, ProcessorBrand.AMD, null, am5Socket)
            };

            Processor[] ryzen9Processors = {
                    new Processor(null, "Ryzen 9 3900X","Ryzen9", generateRandomDigits() , 499.99, 469.99, 3.8, 100, 12, 24, 512, 20480, 105, 4.6, "PCIe 4.0", "12nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.AMD, null, am4Socket),
                    new Processor(null, "Ryzen 9 5900X","Ryzen9",  generateRandomDigits() , 799.99, 749.99, 3.7, 140, 12, 24, 512, 20480, 105, 4.8, "PCIe 4.0", "7nm", "64-bit", "DDR4", true, true, true, ProcessorBrand.AMD, null, am5Socket),
                    new Processor(null, "Ryzen 9 7900X","Ryzen9",  generateRandomDigits() , 799.00, 749.00, 4.7, 120, 12, 24, 512, 20480, 125, 5.6, "PCIe 5.0", "5nm", "64-bit", "DDR5", true, true, true, ProcessorBrand.AMD, null, am5Socket)
            };

            // Xeon Processors
            Processor[] xeonProcessors = {
                    new Processor(null, "Xeon E5-2699 V4","Xeon",  generateRandomDigits() , 2299.99, 2100.00, 2.2, 30, 22, 44, 1024, 40960, 135, 3.6, "PCIe 3.0", "14nm", "64-bit", "DDR4", false, false, true, ProcessorBrand.INTEL, null, lga2011_3Socket),
                    new Processor(null, "Xeon E5-2697 V4","Xeon", generateRandomDigits() , 1999.99, 1850.00, 2.3, 40, 18, 36, 1024, 40960, 135, 3.6, "PCIe 3.0", "14nm", "64-bit", "DDR4", false, false, true, ProcessorBrand.INTEL, null, lga2011_3Socket)
            };

            for (Processor processor : intelProcessors) {
                processorRepository.save(processor);
            }

            Arrays.stream(ryzen3Processors).forEach(processorRepository::save);
            Arrays.stream(ryzen5Processors).forEach(processorRepository::save);
            Arrays.stream(ryzen7Processors).forEach(processorRepository::save);
            Arrays.stream(ryzen9Processors).forEach(processorRepository::save);
            Arrays.stream(xeonProcessors).forEach(processorRepository::save);


            System.out.println("Processadores Intel i5, i7, i9, Ryzen 5, Ryzen 7, Ryzen 9 (AM5) e Xeons populados no banco de dados.");
        }
        System.out.println("Processador OK");

    }

    public static String generateRandomDigits() {
        return String.format("%012d", new SecureRandom().nextLong() & Long.MAX_VALUE).substring(0, 12);
    }
}
