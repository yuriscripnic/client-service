package yss.clientservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import yss.clientservice.entity.Client;
import yss.clientservice.repository.ClientRepository;
import yss.clientservice.service.GeoDataService;

import java.util.Random;
import java.util.stream.IntStream;

@SpringBootApplication
public class ClientServiceApplication {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ClientRepository repository, GeoDataService geoDataService) {
        Random rad = new Random(System.currentTimeMillis());
        String ip = "172.68.25.122";
        return args -> {
            logger.info("Criando clientes para teste");
            repository.deleteAll();
            IntStream.range(1, 11)
                    .mapToObj(i -> {
                        Client c = new Client();
                        c.setName("Cliente " + i);
                        c.setAge(rad.nextInt(100));
                        return c;
                    })
                    .map(v -> {
                        Client created = repository.save(v);
                        geoDataService.saveClientGeoData(created, ip);
                        return created;
                    })
                    .forEach(t -> logger.info(t.toString()));
        };
    }

}
