package br.com.wishlist.application.mapper;

import br.com.wishlist.application.controller.dto.ItemRequest;
import br.com.wishlist.application.controller.dto.WishlistRequest;
import br.com.wishlist.domain.model.Item;
import br.com.wishlist.domain.model.Wishlist;
import org.mapstruct.Mapper;

@Mapper
public interface WishlistMapper {
    Wishlist toDomain(WishlistRequest request);
    Item toDomain(ItemRequest itemRequest);
}
