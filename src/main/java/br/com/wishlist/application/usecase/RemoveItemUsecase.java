package br.com.wishlist.application.usecase;

import br.com.wishlist.domain.model.Item;
import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.domain.repository.WishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RemoveItemUsecase {
    private final WishlistRepository wishlistRepository;

    public Wishlist removeItem(UUID clientId, UUID productId) {
        Wishlist wishlist = wishlistRepository.findByClientId(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist não encontrada."));

        Item item = wishlist.getItemList().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado na Wishlist."));

        wishlist.getItemList().remove(item);

        return wishlistRepository.save(wishlist);
    }
}
