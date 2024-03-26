package br.com.wishlist.application.usecase;

import br.com.wishlist.application.controller.dto.ItemRequest;
import br.com.wishlist.application.mapper.WishlistMapper;
import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.domain.repository.WishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class InsertItemUsecase {
    private final WishlistRepository wishlistRepository;
    private final WishlistMapper mapper;

    public Wishlist addItem(final UUID clientId,
                            final ItemRequest itemRequest) {
        var wishlist = wishlistRepository.findByClientId(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist não encontrada."));

        var item = mapper.toDomain(itemRequest);
        wishlist.getItemList().add(item);

        return wishlistRepository.save(wishlist);
    }

}
