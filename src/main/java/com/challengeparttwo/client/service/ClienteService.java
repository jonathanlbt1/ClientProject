package com.challengeparttwo.client.service;

import com.challengeparttwo.client.entity.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "cliente-service")
public interface ClienteService {

    @RequestMapping(method = RequestMethod.POST, value = "/cliente")
    String createClient(Cliente cliente);

    @RequestMapping(method = RequestMethod.PUT, value = "/cliente")
    String updateClient(Cliente cliente);

    @RequestMapping(method = RequestMethod.DELETE, value = "/cliente")
    String deleteClient(Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/cliente")
    List<Cliente> listClients();

    @RequestMapping(method = RequestMethod.GET, value = "/cliente/{id}")
    Optional<Cliente> getClient(Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/cliente/cpf/{cpf}")
    Optional<Cliente> getClienteByCpf(String cpf);

}
