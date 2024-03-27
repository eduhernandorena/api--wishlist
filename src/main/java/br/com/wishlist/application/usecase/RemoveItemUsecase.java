package br.com.wishlist.application.usecase;

import br.com.wishlist.application.service.WishlistService;
import br.com.wishlist.domain.model.Item;
import br.com.wishlist.domain.model.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RemoveItemUsecase {

    private final WishlistService wishlistService;

    public Wishlist removeItem(UUID clientId, UUID productId) {
        Wishlist wishlist = wishlistService.findByClientId(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist não encontrada."));

        Item item = wishlist.getItemList().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado na Wishlist."));

        wishlist.getItemList().remove(item);
        wishlist.setCreationDate(LocalDateTime.now());

        return wishlistService.save(wishlist);
    }
}
