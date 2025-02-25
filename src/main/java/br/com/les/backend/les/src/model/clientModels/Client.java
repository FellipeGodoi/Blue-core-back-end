package br.com.les.backend.les.src.model.clientModels;

import br.com.les.backend.les.src.model.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String birthDate;
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

//    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Address> addresses;
//
//    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Card> cards;
//
//    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Phone> phones;

    //para inserção de dados
    public Client(String cpf, String emailClient, String nameClient, String birthDate, String password, Gender gender) {
        this.cpf = cpf;
        this.emailClient = emailClient;
        this.nameClient = nameClient;
        this.birthDate = birthDate;
        this.password = password;
        this.gender = gender;
    }

}
