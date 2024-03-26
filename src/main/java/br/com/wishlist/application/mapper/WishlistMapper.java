package br.com.wishlist.application.mapper;

import br.com.wishlist.application.controller.dto.ItemRequest;
import br.com.wishlist.application.controller.dto.WishlistRequest;
import br.com.wishlist.domain.model.Item;
import br.com.wishlist.domain.model.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WishlistMapper {
    WishlistMapper MAPPER = Mappers.getMapper(WishlistMapper.class);
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    Wishlist toDomain(WishlistRequest request);
    Item toDomain(ItemRequest itemRequest);
}
