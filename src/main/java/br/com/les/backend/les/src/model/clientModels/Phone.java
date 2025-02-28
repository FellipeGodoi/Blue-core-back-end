package br.com.les.backend.les.src.model.clientModels;

import br.com.les.backend.les.src.model.enums.PhoneType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long phoneId;
    private String phoneName;
    private String areaCode;
    private String phoneNumber;
    private PhoneType phonetype;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_cpf", nullable = false)
    private Client client;

    public Phone(String phoneName, String areaCode, String phoneNumber, PhoneType phonetype, Client client) {
        this.phoneName = phoneName;
        this.areaCode = areaCode;
        this.phoneNumber = phoneNumber;
        this.phonetype = phonetype;
        this.client = client;
    }

    public Phone(String phoneName, String areaCode, String phoneNumber, PhoneType phonetype) {
        this.phoneName = phoneName;
        this.areaCode = areaCode;
        this.phoneNumber = phoneNumber;
        this.phonetype = phonetype;
    }
}
