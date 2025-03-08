package br.com.les.backend.les.src.application.controllers.enunsController;

import br.com.les.backend.les.src.model.enums.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/enums")
public class EnumsController {

    @GetMapping("/generos")
    public List<Gender> getGenders() {
        return Arrays.asList(Gender.values());
    }

    @GetMapping("/bandeiras")
    public List<CardBrands> getCreditCards() {
        return Arrays.asList(CardBrands.values());
    }

    @GetMapping("/logradouro")
    public List<TipoLogradouro> getTiposLogradouros() {
        return Arrays.asList(TipoLogradouro.values());
    }

    @GetMapping("/residencia")
    public List<TipoResidencial> getTiposResidencials() {
        return Arrays.asList(TipoResidencial.values());
    }

    @GetMapping("/telefone")
    public List<PhoneType> getPhoneTypes() {
        return Arrays.asList(PhoneType.values());
    }
}
