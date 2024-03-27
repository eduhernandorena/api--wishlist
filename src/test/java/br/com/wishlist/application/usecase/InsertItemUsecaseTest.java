package br.com.wishlist.application.usecase;

import br.com.wishlist.application.service.WishlistService;
import br.com.wishlist.domain.model.Item;
import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.infrasctructure.config.properties.WishlistProperties;
import br.com.wishlist.infrasctructure.web.controller.dto.ItemRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class InsertItemUsecaseTest {

    @Mock
    private WishlistService wishlistService;

    @Mock
    private WishlistProperties wishlistProperties;

    @InjectMocks
    private InsertItemUsecase insertItemUsecase;

    @BeforeEach
    public void setup() {
        openMocks(this);
        when(wishlistProperties.getLimitSize()).thenReturn(20);
    }

    @Test
    void testAddItemWithValidInput() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        ItemRequest itemRequest = new ItemRequest("Descrição do Item Teste", productId);
        Wishlist wishlist = Wishlist.builder()
                .clientId(clientId)
                .itemList(new HashSet<>())
                .build();

        Wishlist wishlistSave = Wishlist.builder()
                .clientId(clientId)
                .itemList(Set.of(new Item("Descrição do Item Teste", productId)))
                .updateDate(LocalDateTime.now())
                .build();

        when(wishlistService.findByClientId(clientId)).thenReturn(java.util.Optional.of(wishlist));
        when(wishlistService.save(any(Wishlist.class))).thenReturn(wishlistSave);

        Wishlist result = insertItemUsecase.addItem(clientId, itemRequest);

        assertNotNull(result);
        assertEquals(1, result.getItemList().size());
        verify(wishlistService, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testAddItemWithExceededLimit() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        ItemRequest itemRequest = new ItemRequest("Descrição do Item Teste", productId);
        Set<Item> itemList = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            itemList.add(new Item("Descrição do Item " + i, UUID.randomUUID()));
        }
        Wishlist wishlist = Wishlist.builder()
                .clientId(clientId)
                .itemList(itemList)
                .build();

        when(wishlistService.findByClientId(clientId)).thenReturn(java.util.Optional.of(wishlist));

        assertThrows(IllegalArgumentException.class, () -> insertItemUsecase.addItem(clientId, itemRequest));
        verify(wishlistService, never()).save(any(Wishlist.class));
    }

    @Test
    void testAddItemWithInvalidClientId() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        ItemRequest itemRequest = new ItemRequest("Descrição do Item Teste", productId);

        when(wishlistService.findByClientId(clientId)).thenReturn(java.util.Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> insertItemUsecase.addItem(clientId, itemRequest));
        verify(wishlistService, never()).save(any(Wishlist.class));
    }
}