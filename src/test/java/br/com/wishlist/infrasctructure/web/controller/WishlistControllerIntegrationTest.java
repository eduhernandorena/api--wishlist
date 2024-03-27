package br.com.wishlist.infrasctructure.web.controller;

import br.com.wishlist.domain.model.Item;
import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.infrasctructure.persistence.mongo.MongoWishlistRepository;
import br.com.wishlist.infrasctructure.web.controller.dto.ItemRequest;
import br.com.wishlist.infrasctructure.web.controller.dto.WishlistRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WishlistControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private static final UUID CLIENT_ID_01 = UUID.randomUUID();
    private static final UUID CLIENT_ID = UUID.randomUUID();
    private static final UUID PRODUCT_ID_01 = UUID.randomUUID();
    private static final UUID PRODUCT_ID = UUID.randomUUID();
    private static final Gson gson = new Gson();

    @Autowired
    private MongoWishlistRepository mongoWishlistRepository;

    @BeforeEach
    public void setup() {
        Wishlist wishlist = Wishlist.builder()
                .id(UUID.randomUUID())
                .name("Lista da Maria")
                .comment("Casamento da Maria e do Joao")
                .clientId(CLIENT_ID)
                .itemList(Set.of(new Item("Jogo de panelas", PRODUCT_ID)))
                .creationDate(LocalDateTime.now())
                .build();

        mongoWishlistRepository.save(wishlist);

    }

    @AfterEach
    public void cleanup() {
        mongoWishlistRepository.deleteAll();
    }

    @Test
    void testCreateWishlist() throws Exception {
        WishlistRequest request = new WishlistRequest(CLIENT_ID_01, Set.of(), "Minha lista", "Lista de casa nova");

        mockMvc.perform(MockMvcRequestBuilders.post("/wishlist/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void testInsertItem() throws Exception {
        ItemRequest item = new ItemRequest("Meu produto", PRODUCT_ID_01);

        mockMvc.perform(MockMvcRequestBuilders.post("/wishlist/insertItem/{clientId}", CLIENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(item)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void testVerifyItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/wishlist/verifyItem/{clientId}/{productId}", CLIENT_ID, PRODUCT_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    void testRemoveItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/wishlist/removeItem/{clientId}/{productId}", CLIENT_ID, PRODUCT_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void testDeleteWishlist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/wishlist/delete/{clientId}", CLIENT_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
