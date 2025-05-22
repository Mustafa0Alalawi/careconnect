package com.careconnect.careconnect.controller;

import com.careconnect.careconnect.model.Client;
import com.careconnect.careconnect.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientRepository repository;

    @Autowired
    public ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Client> getAllClients() {
        return repository.findAll();
    }

    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return repository.save(client);
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client updateClient) {
        return repository.findById(id).map(client -> {
            client.setName(updateClient.getName());
            client.setEmail(updateClient.getEmail());
            client.setPhone(updateClient.getPhone());
            return repository.save(client);
        }).orElseThrow(() -> new RuntimeException("Client not found with id " + id));
    }
}
