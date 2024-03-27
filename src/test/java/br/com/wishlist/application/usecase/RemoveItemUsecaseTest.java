package br.com.wishlist.application.usecase;

import br.com.wishlist.application.service.WishlistService;
import br.com.wishlist.domain.model.Item;
import br.com.wishlist.domain.model.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class RemoveItemUsecaseTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private RemoveItemUsecase removeItemUsecase;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        removeItemUsecase = new RemoveItemUsecase(wishlistService);
    }

    @Test
    void testRemoveItem_PositiveCase() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Wishlist wishlistFind = Wishlist.builder()
                .clientId(clientId)
                .itemList(new HashSet<>())
                .build();
        Item item = new Item("Product Description", productId);
        wishlistFind.getItemList().add(item);

        Wishlist wishlistSave = Wishlist.builder()
                .clientId(clientId)
                .itemList(new HashSet<>())
                .updateDate(LocalDateTime.now())
                .build();

        when(wishlistService.findByClientId(clientId)).thenReturn(Optional.of(wishlistFind));
        when(wishlistService.save(any(Wishlist.class))).thenReturn(wishlistSave);

        Wishlist updatedWishlist = removeItemUsecase.removeItem(clientId, productId);

        assertTrue(updatedWishlist.getItemList().isEmpty());
        verify(wishlistService, times(1)).save(wishlistFind);
    }

    @Test
    void testRemoveItem_WishlistNotFound() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        when(wishlistService.findByClientId(clientId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> removeItemUsecase.removeItem(clientId, productId));
        verify(wishlistService, never()).save(any());
    }

    @Test
    void testRemoveItem_ItemNotFound() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Wishlist wishlist = Wishlist.builder()
                .clientId(clientId)
                .itemList(new HashSet<>())
                .build();

        when(wishlistService.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        assertThrows(IllegalArgumentException.class, () -> removeItemUsecase.removeItem(clientId, productId));
        verify(wishlistService, never()).save(any());
    }
}