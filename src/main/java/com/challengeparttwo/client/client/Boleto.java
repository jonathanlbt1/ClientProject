package com.challengeparttwo.client.client;

public record Boleto(
        Long id,
        String clienteId,
        Double valor,
        Double valorPago,
        String dataVencimento,
        String dataPagamento,
        String status
) {
}
