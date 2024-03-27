package br.com.wishlist.application.usecase;

import br.com.wishlist.application.service.WishlistService;
import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.infrasctructure.config.properties.WishlistProperties;
import br.com.wishlist.infrasctructure.web.controller.dto.ItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.wishlist.application.mapper.WishlistMapper.MAPPER;

@Service
@RequiredArgsConstructor
public class InsertItemUsecase {

    private final WishlistService wishlistService;
    private final WishlistProperties wishlistProperties;

    public Wishlist addItem(final UUID clientId,
                            final ItemRequest itemRequest) {
        var wishlist = wishlistService.findByClientId(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Lista de desejos não encontrada para o usuario."));

        var itemListSize = wishlist.getItemList().size();

        if (itemListSize >= wishlistProperties.getLimitSize()) {
            throw new IllegalArgumentException("Limite da lista de desejos alcancado.");
        }

        var item = MAPPER.toDomain(itemRequest);
        wishlist.getItemList().add(item);
        wishlist.setCreationDate(LocalDateTime.now());

        return wishlistService.save(wishlist);
    }

}
