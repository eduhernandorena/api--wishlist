package br.com.wishlist.infrasctructure.web.controller;

import br.com.wishlist.domain.model.Item;
import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.infrasctructure.persistence.mongo.MongoWishlistRepository;
import br.com.wishlist.infrasctructure.web.controller.dto.ItemRequest;
import br.com.wishlist.infrasctructure.web.controller.dto.WishlistRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @MockBean
    private MongoWishlistRepository mongoWishlistRepository;

    @Test
    void testCreateWishlist() throws Exception {
        when(mongoWishlistRepository.save(any())).thenReturn(getWishlist());

        WishlistRequest request = new WishlistRequest(CLIENT_ID_01, Set.of(), "Minha lista", "Lista de casa nova");

        mockMvc.perform(MockMvcRequestBuilders.post("/wishlist/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        verify(mongoWishlistRepository, times(1)).save(any());
    }

    @Test
    void testInsertItem() throws Exception {
        when(mongoWishlistRepository.findByClientId(CLIENT_ID)).thenReturn(Optional.of(getWishlist()));
        when(mongoWishlistRepository.save(any())).thenReturn(getWishlist());

        ItemRequest item = new ItemRequest("Meu produto", PRODUCT_ID_01);

        mockMvc.perform(MockMvcRequestBuilders.put("/wishlist/insertItem/{clientId}", CLIENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(item)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        verify(mongoWishlistRepository, times(1)).findByClientId(CLIENT_ID);
        verify(mongoWishlistRepository, times(1)).save(any());
    }

    @Test
    void testVerifyItem() throws Exception {
        when(mongoWishlistRepository.findByClientId(CLIENT_ID)).thenReturn(Optional.of(getWishlist()));
        mockMvc.perform(MockMvcRequestBuilders.get("/wishlist/verifyItem/{clientId}/{productId}", CLIENT_ID, PRODUCT_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));

        verify(mongoWishlistRepository, times(1)).findByClientId(CLIENT_ID);
    }

    @Test
    void testRemoveItem() throws Exception {
        when(mongoWishlistRepository.save(any())).thenReturn(getWishlist());
        when(mongoWishlistRepository.findByClientId(CLIENT_ID)).thenReturn(Optional.of(getWishlist()));

        mockMvc.perform(MockMvcRequestBuilders.put("/wishlist/removeItem/{clientId}/{productId}", CLIENT_ID, PRODUCT_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        verify(mongoWishlistRepository, times(1)).save(any());
    }

    @Test
    void testDeleteWishlist() throws Exception {
        when(mongoWishlistRepository.findByClientId(CLIENT_ID)).thenReturn(Optional.of(getWishlist()));
        mockMvc.perform(MockMvcRequestBuilders.delete("/wishlist/delete/{clientId}", CLIENT_ID))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(mongoWishlistRepository, times(1)).deleteByClientId(CLIENT_ID);
    }

    private static Wishlist getWishlist(){
        HashSet<Item> items = new HashSet<>();
        items.add(new Item("Jogo de panelas", PRODUCT_ID));

        return Wishlist.builder()
                .id(UUID.randomUUID())
                .name("Lista da Maria")
                .comment("Casamento da Maria e do Joao")
                .clientId(CLIENT_ID)
                .itemList(items)
                .creationDate(LocalDateTime.now())
                .build();
    }
}
