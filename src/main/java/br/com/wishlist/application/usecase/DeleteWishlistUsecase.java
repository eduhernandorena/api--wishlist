package br.com.wishlist.application.usecase;

import br.com.wishlist.domain.repository.WishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteWishlistUsecase {

    private final WishlistRepository wishlistRepository;

    public void deleteWishlist(UUID wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }
}
