package br.com.wishlist.application.usecase;

import br.com.wishlist.application.service.WishlistService;
import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.infrasctructure.web.controller.dto.WishlistRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

public class CreateWishlistUsecaseTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private CreateWishlistUsecase createWishlistUsecase;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void testCreateWishlist_Positive() {
        UUID clientId = UUID.randomUUID();

        WishlistRequest request = new WishlistRequest(clientId, new HashSet<>(), "Lista de Desejos", "Chá de casa nova");
        Wishlist wishlist = Wishlist.builder().clientId(clientId).build();

        Mockito.when(wishlistService.findByClientId(clientId)).thenReturn(Optional.empty());
        Mockito.when(wishlistService.save(any(Wishlist.class))).thenReturn(wishlist);

        Wishlist result = createWishlistUsecase.createWishlist(request);

        assertNotNull(result);
        assertEquals(clientId, result.getClientId());
        verify(wishlistService, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testCreateWishlist_Negative() {
        UUID clientId = UUID.randomUUID();

        WishlistRequest request = new WishlistRequest(clientId, new HashSet<>(), "Lista de Desejos", "Chá de casa nova");
        Wishlist existingWishlist = Wishlist.builder()
                .clientId(clientId)
                .build();

        Mockito.when(wishlistService.findByClientId(clientId)).thenReturn(Optional.of(existingWishlist));
        Mockito.when(wishlistService.save(any(Wishlist.class))).thenReturn(existingWishlist);

        assertThrows(IllegalStateException.class, () -> createWishlistUsecase.createWishlist(request));
        verify(wishlistService, never()).save(any(Wishlist.class));
    }
}