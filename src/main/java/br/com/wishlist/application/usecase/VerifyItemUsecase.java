package br.com.wishlist.application.usecase;

import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.domain.repository.WishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class VerifyItemUsecase {
    private final WishlistRepository wishlistRepository;

    public boolean verifyItemInWishlist(UUID clientId, UUID productId) {
        Wishlist wishlist = wishlistRepository.findByClientId(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist não encontrada."));

        return wishlist.getItemList().stream()
                .anyMatch(item -> item.getProductId().equals(productId));
    }
}
