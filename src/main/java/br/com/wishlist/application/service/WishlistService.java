package br.com.wishlist.application.service;

import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.infrasctructure.persistence.mongo.MongoWishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final MongoWishlistRepository wishlistRepository;

    @Cacheable(value = "wishlist", key = "#clientId", unless = "#result == null")
    public Optional<Wishlist> findByClientId(final UUID clientId){
        return wishlistRepository.findByClientId(clientId);
    }

    @CachePut(value = "wishlist", key = "#wishlist.clientId")
    @Transactional
    public Wishlist save(final Wishlist wishlist){
        return wishlistRepository.save(wishlist);
    }

    @CacheEvict(value = "wishlist", key = "#clientId")
    @Transactional
    public void deleteByClientId(final UUID clientId){
        wishlistRepository.deleteByClientId(clientId);
    }
}

