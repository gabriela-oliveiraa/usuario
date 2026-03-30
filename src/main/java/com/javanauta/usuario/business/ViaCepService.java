package com.javanauta.usuario.business;

import com.javanauta.usuario.infrastucture.clients.ViaCepClient;
import com.javanauta.usuario.infrastucture.clients.ViaCepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient viaCepClient;

    public ViaCepDTO buscarDadosCep(String cep) {
        return viaCepClient.buscarDadosEndereco(cep);
    }

    private String processarCep(String cep) {
        String cepFormatado = cep.replace(" ", "").
                replace("-", "");

        if (!cepFormatado.matches("\\d+")
                || !Objects.equals(cepFormatado.length(), 8)){
            throw new IllegalArgumentException("O cep contém caracteres inválidos, favor verificar!");
        }
        return cepFormatado;
    }
}
