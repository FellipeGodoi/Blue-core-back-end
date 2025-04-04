package br.com.les.backend.les.src.application.dto;

import br.com.les.backend.les.src.model.productModels.Processor;

public record ProcessorDTO(String modelo, Double precoOriginal, Double precoVenda, String codigo, boolean podeSerVendido, String linha) {
    public static ProcessorDTO fromEntity(Processor processor) {
        return new ProcessorDTO(
                processor.getModelo(),
                processor.getPrecoOriginal(),
                processor.getPrecoVenda(),
                processor.getCodigo(),
                processor.isPodeSerVendido(),
                processor.getLinha()
        );
    }
}
