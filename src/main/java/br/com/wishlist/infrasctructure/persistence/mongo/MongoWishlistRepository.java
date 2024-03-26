package br.com.wishlist.infrasctructure.persistence.mongo;

import br.com.wishlist.domain.repository.WishlistRepository;
import br.com.wishlist.domain.model.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MongoWishlistRepository extends WishlistRepository, MongoRepository<Wishlist, UUID> {
}
