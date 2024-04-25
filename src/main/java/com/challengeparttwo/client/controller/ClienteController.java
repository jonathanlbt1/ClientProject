package com.challengeparttwo.client.controller;

import com.challengeparttwo.client.client.Boleto;
import com.challengeparttwo.client.entity.Cliente;
import com.challengeparttwo.client.entity.ClienteDAO;
import com.challengeparttwo.client.service.impl.ClienteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteServiceImpl clienteService;

    public ClienteController(ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }


    @Operation(summary = "Retorna os boletos de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de boletos"),
            @ApiResponse(responseCode = "400", description = "Id do cliente não pode ser nulo")

    })
    @GetMapping("/{id}/boletos")
    public ClienteDAO getBoletosByClienteId(@RequestParam String clienteId){
        if(clienteId != null){
            Cliente cliente = clienteService.getClienteByCpf(clienteId);
            List<Boleto> boletos = clienteService.getBoletosByClienteId(clienteId);
            return new ClienteDAO(cliente, boletos);
        } else {
            throw new RuntimeException("Id do cliente não pode ser nulo");
        }
    }


}
