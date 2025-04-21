package br.com.les.backend.les.src.model.clientModels;

import br.com.les.backend.les.src.model.cartModels.Cart;
import br.com.les.backend.les.src.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idClient;

    @Column(unique = true)
    private String cpf;
    @Column(unique = true)

    private String emailClient;
    private String nameClient;
    private LocalDate birthDate;
    private String password;

    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditCard> cards;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones;

    @JsonIgnore
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Cart cart;

    //para inserção de dados
    public Client(String cpf, String emailClient, String nameClient, LocalDate birthDate, String password, Gender gender) {
        this.cpf = cpf;
        this.emailClient = emailClient;
        this.nameClient = nameClient;
        this.birthDate = birthDate;
        this.password = password;
        this.gender = gender;
    }

}
