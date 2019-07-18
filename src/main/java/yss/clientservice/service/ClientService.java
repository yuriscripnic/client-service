package yss.clientservice.service;

import org.springframework.stereotype.Service;
import yss.clientservice.entity.Client;
import yss.clientservice.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Optional<Client> findById(long id) {
        return repository.findById(id);
    }

    public Client create(Client client) {
        return repository.save(client);
    }

    public Optional<Client> update(long id, Client client) {
        return repository.findById(id)
                .map(record -> {
                    record.setName(client.getName());
                    record.setAge(client.getAge());
                    Client updated = repository.save(record);
                    return Optional.of(updated);
                }).orElse(Optional.empty());
    }

    public Boolean delete(long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return true;
                }).orElse(false);
    }
}
