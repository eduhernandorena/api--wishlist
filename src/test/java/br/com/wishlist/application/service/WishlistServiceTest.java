package br.com.wishlist.application.service;

import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.infrasctructure.persistence.mongo.MongoWishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class WishlistServiceTest {

    @Mock
    private MongoWishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testFindByClientId_Positive() {
        UUID clientId = UUID.randomUUID();
        Wishlist wishlist = Wishlist.builder().clientId(clientId).build();
        when(wishlistRepository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        Optional<Wishlist> result = wishlistService.findByClientId(clientId);

        assertTrue(result.isPresent());
        assertEquals(wishlist, result.get());
    }
    @Test
    void testSave_Positive() {
        UUID clientId = UUID.randomUUID();
        Wishlist wishlist = Wishlist.builder().clientId(clientId).build();
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);

        Wishlist result = wishlistService.save(wishlist);

        assertEquals(wishlist, result);
    }

    @Test
    void testDeleteByClientId_Positive() {
        UUID clientId = UUID.randomUUID();

        wishlistService.deleteByClientId(clientId);

        verify(wishlistRepository, times(1)).deleteByClientId(clientId);
    }
}