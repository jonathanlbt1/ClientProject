package com.challengeparttwo.client.config;

import com.challengeparttwo.client.model.Cliente;
import com.challengeparttwo.client.service.impl.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteBuilder {

    private final ClienteServiceImpl clienteService;

    @Autowired
    public ClienteBuilder(ClienteServiceImpl clienteService) {
        this.clienteService = clienteService;
    }

    public void runOnStartUp() {

        System.out.println("Iniciando Banco em memoria...");

        List<Cliente> clientes = new ArrayList<>();

        var cliente1 = new Cliente(
                "João Lopes de Souza", "485.561.292-09",
                Date.valueOf("2000-01-01"), "Rua 1, São Paulo");
        clientes.add(cliente1);

        var cliente2 = new Cliente(
                "Maria Silva", "308.027.846-84",
                Date.valueOf("2002-02-02"), "Rua 2, São Paulo");
        clientes.add(cliente2);

        var cliente3 = new Cliente(
                "José Santos", "974.485.302-47",
                Date.valueOf("1978-02-13"), "Rua 3, São Paulo");
        clientes.add(cliente3);

        var cliente4 = new Cliente(
                "Ana Oliveira", "702.146.910-81",
                Date.valueOf("1985-03-21"), "Rua 4, São Paulo");
        clientes.add(cliente4);

        var cliente5 = new Cliente(
                "Carlos Pereira", "629.858.403-10",
                Date.valueOf("1993-12-25"), "Rua 5, São Paulo");
        clientes.add(cliente5);

        var cliente6 = new Cliente(
                "Marta Pereira", "518.739.261-20",
                Date.valueOf("1963-09-15"), "Rua 6, São Paulo");
        clientes.add(cliente6);

        var cliente7 = new Cliente(
                "Pedro Pereira", "103.624.587-33",
                Date.valueOf("1997-01-31"), "Rua 7, São Paulo");
        clientes.add(cliente7);

        var cliente8 = new Cliente(
                "Paulo Pereira", "841.927.605-15",
                Date.valueOf("1988-04-08"), "Rua 8, São Paulo");
        clientes.add(cliente8);

        var cliente9 = new Cliente(
                "Lucas Pereira", "216.537.894-60",
                Date.valueOf("1975-05-13"), "Rua 9, São Paulo");
        clientes.add(cliente9);

        var cliente10 = new Cliente(
                "Lucia Pereira", "375.682.019-07",
                Date.valueOf("2000-09-11"), "Rua 10, São Paulo");
        clientes.add(cliente10);

        clientes.forEach(clienteService::createClient);
    }
}
