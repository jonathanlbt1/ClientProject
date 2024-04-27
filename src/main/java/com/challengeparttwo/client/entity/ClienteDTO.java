package com.challengeparttwo.client.entity;

import com.challengeparttwo.client.client.Boleto;

import java.util.List;
import java.util.Optional;

public record ClienteDTO(
        Optional<Cliente> cliente,
        List<Boleto> boletos) {
}
