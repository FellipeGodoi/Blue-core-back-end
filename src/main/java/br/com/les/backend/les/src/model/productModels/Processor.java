package br.com.les.backend.les.src.model.productModels;

import br.com.les.backend.les.src.model.enums.ProcessorBrand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Processor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String modelo;
    private String linha;
    @Column(unique = true)
    private String codigo;
    private Double precoOriginal;
    private Double precoVenda;
    private Double clockBase;
    private int estoque;
    private int nucleos;
    private int threads;
    private int cacheL2;
    private int cacheL3;
    private int tdp;
    private Double clockMax;
    private String pci;
    private String arquiteturaContrucao;
    private String arquiteturaBits;
    private String memorias;
    private boolean desbloqueado;
    private boolean cooler;
    private boolean podeSerVendido;

    @Enumerated(EnumType.STRING)
    private ProcessorBrand brand;

    @ManyToOne
    @JoinColumn(name = "gpu_id")
    private GPU gpu;

    @ManyToOne
    @JoinColumn(name = "socket_id", nullable = false)
    private Socket socket;

}
