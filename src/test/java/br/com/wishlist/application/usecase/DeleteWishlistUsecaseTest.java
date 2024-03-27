package br.com.wishlist.application.usecase;

import br.com.wishlist.application.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeleteWishlistUsecaseTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private DeleteWishlistUsecase deleteWishlistUsecase;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    void testDeleteWishlist_Positive() {
        UUID wishlistId = UUID.randomUUID();
        deleteWishlistUsecase.deleteWishlist(wishlistId);

        Mockito.verify(wishlistService, Mockito.times(1)).deleteByClientId(wishlistId);
    }

    @Test
    void testDeleteWishlist_Negative() {
        UUID wishlistId = UUID.randomUUID();
        Mockito.doThrow(RuntimeException.class).when(wishlistService).deleteByClientId(wishlistId);

        assertThrows(RuntimeException.class, () -> deleteWishlistUsecase.deleteWishlist(wishlistId));
    }
}