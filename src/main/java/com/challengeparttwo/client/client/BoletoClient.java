package com.challengeparttwo.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "boleto-service", url = "http://localhost:8082")
public interface BoletoClient {

    @GetMapping("/boleto")
    List<Boleto> getBoletosByClienteId(@RequestParam String clienteId);
}
