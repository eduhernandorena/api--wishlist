package br.com.wishlist.infrasctructure.web.controller;

import br.com.wishlist.infrasctructure.web.controller.dto.ItemRequest;
import br.com.wishlist.infrasctructure.web.controller.dto.WishlistRequest;
import br.com.wishlist.application.usecase.*;
import br.com.wishlist.domain.model.Wishlist;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wishlist")
@AllArgsConstructor
public class WishlistController {

    private final CreateWishlistUsecase createWishlistUsecase;
    private final DeleteWishlistUsecase deleteWishlistUsecase;
    private final InsertItemUsecase insertItemUsecase;
    private final RemoveItemUsecase removeItemUsecase;
    private final VerifyItemUsecase verifyItemUsecase;

    @PostMapping("/create")
    public ResponseEntity<Wishlist> createWishlist(@RequestBody WishlistRequest request) {
        var wishlist = createWishlistUsecase.createWishlist(request);

        return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable UUID clientId) {
        deleteWishlistUsecase.deleteWishlist(clientId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/insertItem/{clientId}")
    public ResponseEntity<Wishlist> insertItem(@PathVariable UUID clientId, @RequestBody ItemRequest item) {
        var wishlist = insertItemUsecase.addItem(clientId, item);

        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @DeleteMapping("/removeItem/{clientId}/{productId}")
    public ResponseEntity<Wishlist> removeItem(@PathVariable UUID clientId, @PathVariable UUID productId) {
        var wishlist = removeItemUsecase.removeItem(clientId, productId);

        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @GetMapping("/verifyItem/{clientId}/{productId}")
    public ResponseEntity<Boolean> verifyItem(@PathVariable UUID clientId, @PathVariable UUID productId) {
        boolean itemExists = verifyItemUsecase.verifyItemInWishlist(clientId, productId);
        return ResponseEntity.ok(itemExists);
    }
}

