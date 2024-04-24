package com.challengeparttwo.client.service.impl;

import com.challengeparttwo.client.model.Cliente;
import com.challengeparttwo.client.repository.ClienteRepository;
import com.challengeparttwo.client.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    @Override
    public String createClient(Cliente cliente) {
        if(cliente != null){
            clienteRepository.save(cliente);
            return "Cliente salvo com sucesso";
        } else {
            throw new RuntimeException("Cliente não pode ser nulo");
        }
    }

    @Override
    public String updateClient(Cliente cliente) {
        if(cliente != null){
            clienteRepository.findById(cliente.getId())
                    .map(cliente1 -> {
                        cliente1.setNome(cliente.getNome());
                        cliente1.setCpf(cliente.getCpf());
                        cliente1.setEndereco(cliente.getEndereco());
                        cliente1.setDataNascimento(cliente.getDataNascimento());
                        cliente1.setEndereco(cliente.getEndereco());
                        return clienteRepository.save(cliente1);
                    });
        } else {
            throw new RuntimeException("Cliente não pode ser nulo");
        }
        return "Cliente atualizado com sucesso";
    }

    @Override
    public String deleteClient(Long id) {
        if(id != null){
            clienteRepository.deleteById(id);
            return "Cliente deletado com sucesso";
        } else {
            throw new RuntimeException("Id do cliente não pode ser nulo");
        }
    }

    @Override
    public List<Cliente> listClients() {
        return clienteRepository.findAll().stream().toList();
    }

    @Override
    public Optional<Cliente> getClient(Long id) {
        if(id != null){
            return clienteRepository.findById(id);
        }
        return Optional.empty();
    }
}