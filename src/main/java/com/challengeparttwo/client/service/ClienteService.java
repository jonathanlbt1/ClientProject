package com.challengeparttwo.client.service;

import com.challengeparttwo.client.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    String createClient(Cliente cliente);

    String updateClient(Cliente cliente);

    String deleteClient(Long id);

    List<Cliente> listClients();

    Optional<Cliente> getClient(Long id);

}
