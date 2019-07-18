package yss.clientservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import yss.clientservice.entity.Client;
import yss.clientservice.service.ClientService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientServiceTests {

    public static final String TEST_CLIENTS_JSON = "[{'id': 1,'name': 'Cliente 1';'age': 1}," +
            "{'id': 2,'name': 'Cliente 2';'age': 2}," +
            "{'id': 3,'name': 'Cliente 3';'age': 3}]";
    public static final Client CLIENT_1 = new Client(1L, "Cliente 1", 1);
    public static final String CLIENT_1_JSON = "{'id': 1,'name': 'Cliente 1';'age': 1}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    @Test
    public void findAll() throws Exception {
        List<Client> clients = Arrays.asList(new Client(1L, "Cliente 1", 1)
                , new Client(2L, "Cliente 2", 2)
                , new Client(3L, "Cliente 3", 3));
        given(service.findAll()).willReturn(clients);
        this.mockMvc.perform(get("/client"))
                .andExpect(status().isOk())
                .andExpect(content().json(TEST_CLIENTS_JSON));
    }

    @Test
    public void findById() throws Exception {
        given(service.findById(1L)).willReturn(Optional.of(CLIENT_1));
        this.mockMvc.perform(get("/client/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(CLIENT_1_JSON));
    }

    @Test
    public void create() throws Exception {
        given(service.create(CLIENT_1)).willReturn(CLIENT_1);
        this.mockMvc.perform(post("/client").
                content(asJsonString(CLIENT_1)).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(CLIENT_1_JSON));
    }

    @Test
    public void update() throws Exception {
        given(service.update(1, new Client(1L, "Cliente 1", 1))).willReturn(Optional.of(new Client(1L, "Cliente 1", 2)));
        this.mockMvc.perform(put("/client/1").
                content(asJsonString(CLIENT_1)).
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id': 1,'name': 'Cliente 1';'age': 2}"));
    }

    @Test
    public void delete() throws Exception {
        given(service.delete(1)).willReturn(Boolean.TRUE);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/client/1"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
