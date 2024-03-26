package br.com.wishlist.domain.repository;

import br.com.wishlist.domain.model.Wishlist;

import java.util.Optional;
import java.util.UUID;

public interface WishlistRepository {
    Optional<Wishlist> findByClientId(UUID clientId);
    Wishlist save(Wishlist wishlist);
    void deleteById(UUID id);
}
