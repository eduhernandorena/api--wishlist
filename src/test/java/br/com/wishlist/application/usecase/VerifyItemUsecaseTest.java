package br.com.wishlist.application.usecase;

import br.com.wishlist.application.service.WishlistService;
import br.com.wishlist.domain.model.Item;
import br.com.wishlist.domain.model.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class VerifyItemUsecaseTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private VerifyItemUsecase verifyItemUsecase;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    void testVerifyItemInWishlist_PositiveCase() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Wishlist wishlist = Wishlist.builder()
                .clientId(clientId)
                .itemList(Set.of(new Item("Produto", productId)))
                .build();

        when(wishlistService.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        assertTrue(verifyItemUsecase.verifyItemInWishlist(clientId, productId));
    }

    @Test
    void testVerifyItemInWishlist_NegativeCase() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        Wishlist wishlist = Wishlist.builder()
                .clientId(clientId)
                .itemList(Set.of(new Item("Produto", UUID.randomUUID())))
                .build();

        when(wishlistService.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        assertFalse(verifyItemUsecase.verifyItemInWishlist(clientId, productId));
    }

    @Test
    void testVerifyItemInWishlist_EmptyWishlist() {
        UUID clientId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        when(wishlistService.findByClientId(clientId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> verifyItemUsecase.verifyItemInWishlist(clientId, productId));
    }
}