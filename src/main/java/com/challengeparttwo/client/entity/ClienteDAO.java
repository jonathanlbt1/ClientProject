package com.challengeparttwo.client.entity;

import com.challengeparttwo.client.client.Boleto;

import java.util.List;

public record ClienteDAO(Cliente cliente, List<Boleto> boletos) {
}
