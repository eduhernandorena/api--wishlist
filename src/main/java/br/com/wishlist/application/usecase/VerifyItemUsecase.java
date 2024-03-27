package br.com.wishlist.application.usecase;

import br.com.wishlist.application.service.WishlistService;
import br.com.wishlist.domain.model.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerifyItemUsecase {
    
    private final WishlistService wishlistService;

    public boolean verifyItemInWishlist(UUID clientId, UUID productId) {
        Wishlist wishlist = wishlistService.findByClientId(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist não encontrada."));

        return wishlist.getItemList().stream()
                .anyMatch(item -> item.getProductId().equals(productId));
    }
}
