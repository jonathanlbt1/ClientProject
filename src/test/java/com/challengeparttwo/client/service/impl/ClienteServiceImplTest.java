package com.challengeparttwo.client.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.challengeparttwo.client.client.Boleto;
import com.challengeparttwo.client.client.BoletoClient;
import com.challengeparttwo.client.entity.Cliente;
import com.challengeparttwo.client.repository.ClienteRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClienteServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ClienteServiceImplTest {

    @MockBean
    private BoletoClient boletoClient;

    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    private static ArrayList<Boleto> getBoletos() {
        ArrayList<Boleto> boletoList = new ArrayList<>();
        var boleto = new Boleto(
                1L,
                "383.440.410-15",
                100.00,
                100.00,
                "2024-01-10",
                "2024-01-10",
                "PAGO"
        );
        boletoList.add(boleto);

        var boleto2 = new Boleto(
                2L,
                "383.440.410-15",
                100.00,
                100.00,
                "2024-01-10",
                "2024-01-10",
                "PAGO"
        );
        boletoList.add(boleto2);
        return boletoList;
    }

    @Test
    void testThatGetBoletosByClienteIdIsTrue() {
        
        var boletoList = getBoletos();
        when(boletoClient.getBoletosByClienteId(Mockito.any())).thenReturn(boletoList);

        var actualBoletosByClienteId = clienteServiceImpl.getBoletosByClienteId("383.440.410-15");

        verify(boletoClient).getBoletosByClienteId(eq("383.440.410-15"));
        assertFalse(actualBoletosByClienteId.isEmpty());
        assertSame(boletoList, actualBoletosByClienteId);
    }


    @Test
    void testThatGetBoletosByClienteIdIsFalse() {

        when(boletoClient.getBoletosByClienteId(Mockito.any())).thenThrow(new RuntimeException("foo"));

        assertThrows(RuntimeException.class, () -> clienteServiceImpl.getBoletosByClienteId("760.532.716-16"));
        verify(boletoClient).getBoletosByClienteId(eq("760.532.716-16"));
    }


    @Test
    void testThatGetClienteByCpfReturnsCliente() {

        var cliente = new Cliente();
        cliente.setCpf("622.723.933-06");
        cliente.setDataNascimento(Date.from(LocalDate.of(1970, 1, 1)
                .atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente.setEndereco("Endereco");
        cliente.setNome("John Doe");

        var ofResult = Optional.of(cliente);
        when(clienteRepository.findByCpf(Mockito.any())).thenReturn(ofResult);

        var actualClienteByCpf = clienteServiceImpl.getClienteByCpf("622.723.933-06");
        verify(clienteRepository, atLeast(1)).findByCpf(eq("622.723.933-06"));
        assertSame(ofResult, actualClienteByCpf);
    }


    @Test
    void testThatGetClienteByCpfReturnsNothing() {

        Optional<Cliente> emptyResult = Optional.empty();
        when(clienteRepository.findByCpf(Mockito.any())).thenReturn(emptyResult);

        assertThrows(RuntimeException.class, () -> clienteServiceImpl.getClienteByCpf("713.841.815-98"));
        verify(clienteRepository).findByCpf(eq("713.841.815-98"));
    }


    @Test
    void testThatGetClienteByCpfReturnsNotFound() {

        when(clienteRepository.findByCpf(Mockito.any())).thenThrow(new RuntimeException("Cliente não encontrado"));

        assertThrows(RuntimeException.class, () -> clienteServiceImpl.getClienteByCpf("642.508.470-71"));
        verify(clienteRepository).findByCpf(eq("642.508.470-71"));
    }


    @Test
    void testThatCreateClientReturnsExceptionWhenClienteAlreadyExists() {

        var cliente = new Cliente();
        cliente.setCpf("472.958.295-04");
        cliente.setDataNascimento(Date.from(LocalDate.of(1970, 1, 1)
                .atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente.setEndereco("Endereco");
        cliente.setNome("Tony Stark");
        Optional<Cliente> ofResult = Optional.of(cliente);
        when(clienteRepository.findByCpf(Mockito.any())).thenReturn(ofResult);

        var cliente2 = new Cliente();
        cliente2.setCpf("472.958.295-04");
        cliente2.setDataNascimento(Date.from(LocalDate.of(1975, 1, 1)
                .atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente2.setEndereco("Endereco");
        cliente2.setNome("Captain America");

        assertThrows(RuntimeException.class, () -> clienteServiceImpl.createClient(cliente2));
        verify(clienteRepository).findByCpf(eq("472.958.295-04"));
    }


    @Test
    void testThatCreateClientReturnsSuccessMessage() {

        when(clienteRepository.findByCpf(Mockito.any()))
                .thenThrow(new RuntimeException("Cliente salvo com sucesso"));

        var cliente = new Cliente();
        cliente.setCpf("355.508.243-45");
        cliente.setDataNascimento(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente.setEndereco("Endereco");
        cliente.setNome("Scartlet Witch");

        assertThrows(RuntimeException.class, () -> clienteServiceImpl.createClient(cliente));
        verify(clienteRepository).findByCpf(eq("355.508.243-45"));
    }


    @Test
    void testThatUpdateClienteReturnsSuccessMessage() {

        var cliente = new Cliente();
        cliente.setCpf("088.667.587-10");
        cliente.setDataNascimento(Date.from(LocalDate.of(1970, 1, 1)
                .atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente.setEndereco("Endereco");
        cliente.setNome("John Doe");
        Optional<Cliente> ofResult = Optional.of(cliente);

        var cliente2 = new Cliente();
        cliente2.setCpf("088.667.587-10");
        cliente2.setDataNascimento(Date.from(LocalDate.of(1970, 1, 1)
                .atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente2.setEndereco("Endereco");
        cliente2.setNome("John Von Doe");
        when(clienteRepository.save(Mockito.any())).thenReturn(cliente2);
        when(clienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        var cliente3 = new Cliente();
        cliente3.setCpf("088.667.587-10");
        cliente3.setDataNascimento(Date.from(LocalDate.of(1970, 1, 1)
                .atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente3.setEndereco("Endereco");
        cliente3.setNome("John Dulop");

        var actualUpdateClientResult = clienteServiceImpl.updateClient(cliente3);

        verify(clienteRepository).findById(isNull());
        verify(clienteRepository).save(isA(Cliente.class));
        assertEquals("Cliente atualizado com sucesso", actualUpdateClientResult);
    }


    @Test
    void testThatUpdateClienteReturnsRuntimeException() {

        var cliente = new Cliente();
        cliente.setCpf("424.841.166-08");
        cliente.setDataNascimento(Date.from(LocalDate.of(1970, 1, 1)
                .atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente.setEndereco("Endereco");
        cliente.setNome("Sarah O'Connor");
        Optional<Cliente> ofResult = Optional.of(cliente);
        when(clienteRepository.save(Mockito.<Cliente>any()))
                .thenThrow(new RuntimeException("Cliente atualizado com sucesso"));
        when(clienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        var cliente2 = new Cliente();
        cliente2.setCpf("424.841.166-08");
        cliente2.setDataNascimento(Date.from(LocalDate.of(1970, 1, 1)
                .atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente2.setEndereco("Endereco");
        cliente2.setNome("Sarah O'Connor");

        assertThrows(RuntimeException.class, () -> clienteServiceImpl.updateClient(cliente2));
        verify(clienteRepository).findById(isNull());
        verify(clienteRepository).save(isA(Cliente.class));
    }

    @Test
    void testThatDeleteClienteByIdReturnsSuccessMessage() {

        var cliente = new Cliente();
        cliente.setCpf("843.728.867-30");
        cliente.setDataNascimento(Date.from(LocalDate.of(1970, 1, 1)
                .atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente.setEndereco("Endereco");
        cliente.setNome("Woody WoodPecker");
        Optional<Cliente> ofResult = Optional.of(cliente);
        doNothing().when(clienteRepository).deleteById(Mockito.<Long>any());
        when(clienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        var actualDeleteClientResult = clienteServiceImpl.deleteClient(1L);

        verify(clienteRepository).deleteById(eq(1L));
        verify(clienteRepository).findById(eq(1L));
        assertEquals("Cliente deletado com sucesso", actualDeleteClientResult);
    }


    @Test
    void testThatDeleteClientThrowsRuntimeException() {

        var cliente = new Cliente();
        cliente.setCpf("816.887.671-77");
        cliente.setDataNascimento(Date.from(LocalDate.of(1970, 1, 1)
                .atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente.setEndereco("Endereco");
        cliente.setNome("Peter Parker");

        Optional<Cliente> ofResult = Optional.of(cliente);
        doThrow(new RuntimeException("Cliente deletado com sucesso")).when(clienteRepository)
                .deleteById(Mockito.<Long>any());
        when(clienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        assertThrows(RuntimeException.class, () -> clienteServiceImpl.deleteClient(1L));
        verify(clienteRepository).deleteById(eq(1L));
        verify(clienteRepository).findById(eq(1L));
    }


    @Test
    void testThatListClientesReturnsAListOfClientes() {

        when(clienteRepository.findAll()).thenReturn(new ArrayList<>());
        var actualListClientsResult = clienteServiceImpl.listClients();

        verify(clienteRepository).findAll();
        assertTrue(actualListClientsResult.isEmpty());
    }


    @Test
    void testThatGetClientByIdReturnsCliente() {

        var cliente = new Cliente();
        cliente.setCpf("622.336.967-00");
        cliente.setDataNascimento(Date.from(LocalDate.of(1970, 1, 1)
                .atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        cliente.setEndereco("Endereco");
        cliente.setNome("Bruce Wayne");
        Optional<Cliente> ofResult = Optional.of(cliente);
        when(clienteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Optional<Cliente> actualClient = clienteServiceImpl.getClient(1L);

        verify(clienteRepository, atLeast(1)).findById(eq(1L));
        assertSame(ofResult, actualClient);
    }


    @Test
    void testThatGetClienteByIdThrowsRuntimeException() {

        Optional<Cliente> emptyResult = Optional.empty();
        when(clienteRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        assertThrows(RuntimeException.class, () -> clienteServiceImpl.getClient(1L));
        verify(clienteRepository).findById(eq(1L));
    }


    @Test
    void testThatGetClienteByIdReturnsClienteNotFoundMessage() {

        when(clienteRepository.findById(Mockito.<Long>any()))
                .thenThrow(new RuntimeException("Cliente não encontrado."));

        assertThrows(RuntimeException.class, () -> clienteServiceImpl.getClient(1L));
        verify(clienteRepository).findById(eq(1L));
    }
}
