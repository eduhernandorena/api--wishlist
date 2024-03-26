package br.com.wishlist.application.usecase;

import br.com.wishlist.application.controller.dto.ItemRequest;
import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.domain.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.wishlist.application.mapper.WishlistMapper.MAPPER;

@Service
@RequiredArgsConstructor
public class InsertItemUsecase {

    @Value("${wishlist.limit-size:20}")
    private Integer limitSize;

    private final WishlistRepository wishlistRepository;

    public Wishlist addItem(final UUID clientId,
                            final ItemRequest itemRequest) {
        var wishlist = wishlistRepository.findByClientId(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Lista de desejos não encontrada para o usuario."));

        val itemListSize = wishlist.getItemList().size();

        if(itemListSize >= limitSize) throw new IllegalArgumentException("Limite da lista de desejos alcancado.");

        var item = MAPPER.toDomain(itemRequest);
        wishlist.getItemList().add(item);
        wishlist.setCreationDate(LocalDateTime.now());

        return wishlistRepository.save(wishlist);
    }

}
