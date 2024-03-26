package br.com.wishlist.application.usecase;

import br.com.wishlist.application.controller.dto.WishlistRequest;
import br.com.wishlist.application.mapper.WishlistMapper;
import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.domain.repository.WishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CreateWishlistUsecase {

    private final WishlistRepository wishlistRepository;
    private final WishlistMapper mapper;

    public Wishlist createWishlist(final WishlistRequest request) {
        Optional<Wishlist> existingWishlist = wishlistRepository.findByClientId(request.getClientId());
        if (existingWishlist.isPresent()) {
            throw new IllegalStateException("O cliente já possui uma Wishlist.");
        }

        var domain = mapper.toDomain(request);

        return wishlistRepository.save(domain);
    }
}
