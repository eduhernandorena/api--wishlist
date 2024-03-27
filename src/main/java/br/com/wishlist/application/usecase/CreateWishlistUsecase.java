package br.com.wishlist.application.usecase;

import br.com.wishlist.application.service.WishlistService;
import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.infrasctructure.web.controller.dto.WishlistRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.wishlist.application.mapper.WishlistMapper.MAPPER;

@Service
@RequiredArgsConstructor
public class CreateWishlistUsecase {

    private final WishlistService wishlistService;

    public Wishlist createWishlist(final WishlistRequest request) {
        Optional<Wishlist> existingWishlist = wishlistService.findByClientId(request.getClientId());
        if (existingWishlist.isPresent()) {
            throw new IllegalStateException("O cliente já possui uma Wishlist.");
        }

        var domain = MAPPER.toDomain(request);

        return wishlistService.save(domain);
    }
}
