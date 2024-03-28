package br.com.wishlist.infrasctructure.web.controller;

import br.com.wishlist.application.usecase.*;
import br.com.wishlist.domain.model.Wishlist;
import br.com.wishlist.infrasctructure.web.controller.dto.ItemRequest;
import br.com.wishlist.infrasctructure.web.controller.dto.WishlistRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wishlist")
@AllArgsConstructor
@Api(tags = "Wishlist Controller")
public class WishlistController {

    private final CreateWishlistUsecase createWishlistUsecase;
    private final DeleteWishlistUsecase deleteWishlistUsecase;
    private final InsertItemUsecase insertItemUsecase;
    private final RemoveItemUsecase removeItemUsecase;
    private final VerifyItemUsecase verifyItemUsecase;

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Lista de desejos criada com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação nos dados fornecidos"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    @ApiOperation("Cria uma nova lista de desejos para o cliente")
    @PostMapping("/create")
    public ResponseEntity<Wishlist> createWishlist(@RequestBody @Valid WishlistRequest request) {
        var wishlist = createWishlistUsecase.createWishlist(request);

        return new ResponseEntity<>(wishlist, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Lista de desejos removida com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação nos dados fornecidos"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    @ApiOperation("Deleta a lista de desejos do cliente")
    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable @Valid @NotNull(message = "O id do cliente deve estar preenchido") UUID clientId) {
        deleteWishlistUsecase.deleteWishlist(clientId);
        return ResponseEntity.noContent().build();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item adicionado a lista de desejos com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação nos dados fornecidos"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    @ApiOperation("Adiciona um produto na lista de desejos do cliente")
    @PutMapping("/insertItem/{clientId}")
    public ResponseEntity<Wishlist> insertItem(@PathVariable @Valid @NotNull(message = "O id do cliente deve estar preenchido") UUID clientId,
                                               @RequestBody @Valid ItemRequest item) {
        var wishlist = insertItemUsecase.addItem(clientId, item);

        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Item removido a lista de desejos com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação nos dados fornecidos"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    @ApiOperation("Remove um produto na lista de desejos do cliente")
    @PutMapping("/removeItem/{clientId}/{productId}")
    public ResponseEntity<Wishlist> removeItem(@PathVariable @Valid @NotNull(message = "O id do cliente deve estar preenchido") UUID clientId,
                                               @PathVariable @Valid @NotNull(message = "O id do produto deve estar preenchido") UUID productId) {
        var wishlist = removeItemUsecase.removeItem(clientId, productId);

        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Verificação efetuada com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação nos dados fornecidos"),
            @ApiResponse(code = 500, message = "Erro interno no servidor")
    })
    @ApiOperation("Verifica se um produto esta na lista de desejos do cliente")
    @GetMapping("/verifyItem/{clientId}/{productId}")
    public ResponseEntity<Boolean> verifyItem(@PathVariable @Valid @NotNull(message = "O id do cliente deve estar preenchido") UUID clientId,
                                              @PathVariable @Valid @NotNull(message = "O id do produto deve estar preenchido") UUID productId) {
        boolean itemExists = verifyItemUsecase.verifyItemInWishlist(clientId, productId);
        return ResponseEntity.ok(itemExists);
    }
}

