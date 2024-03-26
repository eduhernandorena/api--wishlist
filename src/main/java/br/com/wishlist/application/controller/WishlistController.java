package br.com.wishlist.application.controller;

import br.com.wishlist.application.controller.dto.ItemRequest;
import br.com.wishlist.application.controller.dto.WishlistRequest;
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
    public ResponseEntity<Wishlist> createWishlist(@RequestParam WishlistRequest request) {
        var wishlist = createWishlistUsecase.createWishlist(request);

        return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteWishlist(@RequestParam UUID clientId) {
        deleteWishlistUsecase.deleteWishlist(clientId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/insertItem")
    public ResponseEntity<Wishlist> insertItem(@RequestParam UUID clientId, @RequestParam ItemRequest item) {
        var wishlist = insertItemUsecase.addItem(clientId, item);

        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @DeleteMapping("/removeItem")
    public ResponseEntity<Wishlist> removeItem(@RequestParam UUID clientId, @RequestParam UUID productId) {
        var wishlist = removeItemUsecase.removeItem(clientId, productId);

        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @GetMapping("/verifyItem")
    public ResponseEntity<Boolean> verifyItem(@RequestParam UUID clientId, @RequestParam UUID productId) {
        boolean itemExists = verifyItemUsecase.verifyItemInWishlist(clientId, productId);
        return ResponseEntity.ok(itemExists);
    }
}

