package br.com.wishlist.application.usecase;

import br.com.wishlist.application.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteWishlistUsecase {

    private final WishlistService wishlistService;

    public void deleteWishlist(UUID clientId) {
        wishlistService.deleteByClientId(clientId);
    }
}
