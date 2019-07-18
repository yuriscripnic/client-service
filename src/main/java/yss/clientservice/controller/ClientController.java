package yss.clientservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yss.clientservice.entity.Client;
import yss.clientservice.entity.GeoData;
import yss.clientservice.service.ClientService;
import yss.clientservice.service.GeoDataService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/client")
public class ClientController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ClientService service;
    private final GeoDataService geoService;

    ClientController(ClientService service, GeoDataService geoService) {
        this.service = service;
        this.geoService = geoService;
    }

    //Listar todos os Clientes salvos
    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        logger.info("Listando todos os clientes");
        return ResponseEntity.ok(service.findAll());
    }

    //Listar todas informações de clima salvas
    @GetMapping(path = "/clima")
    public ResponseEntity<List<GeoData>> findAllGeoData() {
        logger.info("Listando todos os clientes");
        return ResponseEntity.ok(geoService.findAll());
    }

    //Consultar um Cliente por id
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Client> findById(@PathVariable long id) {
        logger.info("Consultando cliente por id: {}", id);
        return service.findById(id)
                .map(rec -> ResponseEntity.ok().body(rec))
                .orElse(ResponseEntity.notFound().build());
    }


    //Criar um Cliente
    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client client, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        logger.info("Criando novo cliente: {} do ip: {}", client.toString(), ip);
        Client createdClient = service.create(client);
        geoService.saveClientGeoData(createdClient, ip);
        return ResponseEntity.ok(createdClient);
    }

    //Alterar um Cliente
    @PutMapping(value = "/{id}")
    public ResponseEntity<Client> update(@PathVariable("id") long id,
                                         @RequestBody Client client) {
        logger.info("Alterando cliente: {}", client.toString());
        return service.update(id, client).
                map(rec -> ResponseEntity.ok().body(rec)).
                orElse(ResponseEntity.notFound().build());
    }

    //Remover Cliente por id
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        logger.info("Removendo cliente id: {}", id);
        if (service.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
