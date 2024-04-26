package com.challengeparttwo.client.controller;

import com.challengeparttwo.client.entity.Cliente;
import com.challengeparttwo.client.entity.ClienteDTO;
import com.challengeparttwo.client.service.impl.ClienteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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
    public ClienteDTO getBoletosByClienteId(@RequestParam String clienteId){
        if(Objects.nonNull(clienteId)){
            var cliente = clienteService.getClienteByCpf(clienteId);
            var boletos = clienteService.getBoletosByClienteId(clienteId);
            return new ClienteDTO(cliente, boletos);
        } else {
            throw new RuntimeException("Id do cliente não pode ser nulo");
        }
    }

    @Operation(summary = "Cria um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Cliente não pode ser nulo")
    })
    @PostMapping
    public String createClient(@RequestBody Cliente cliente){
        return clienteService.createClient(cliente);
    }

    @Operation(summary = "Atualiza um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Cliente não pode ser nulo")
    })
    @PutMapping("/{id}")
    public String updateClient(@RequestBody Cliente cliente){
        return clienteService.updateClient(cliente);
    }

    @Operation(summary = "Deleta um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Id do cliente não pode ser nulo")
    })
    @DeleteMapping("/{id}")
    public String deleteClient(@RequestParam Long id){
        if(Objects.nonNull(id)){
            clienteService.deleteClient(id);
            return "Cliente deletado com sucesso";
        } else {
            throw new RuntimeException("Id do cliente não pode ser nulo");
        }
    }

    @Operation(summary = "Lista todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de clientes")
    })
    @GetMapping
    public List<Cliente> listClients(){
        return clienteService.listClients();
    }

    @Operation(summary = "Retorna um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um cliente"),
            @ApiResponse(responseCode = "400", description = "Id do cliente não pode ser nulo")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cliente>> getClientById(@RequestParam Long id){
        if(Objects.nonNull(id)){
            return ResponseEntity.ok(clienteService.getClient(id));
        } else {
            throw new RuntimeException("Id do cliente não pode ser nulo");
        }
    }

    @Operation(summary = "Retorna um cliente pelo cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna um cliente"),
            @ApiResponse(responseCode = "400", description = "Cpf do cliente não pode ser nulo")
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Optional<Cliente>> getClienteByCpf(@RequestParam String cpf){
        if(Objects.nonNull(cpf)){
           return ResponseEntity.ok(clienteService.getClienteByCpf(cpf));
        } else {
            throw new RuntimeException("Cpf do cliente não pode ser nulo");
        }
    }
}
