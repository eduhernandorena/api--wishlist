package br.com.wishlist.infrasctructure.web.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Validated
@AllArgsConstructor
public class WishlistRequest {

    @NotNull(message = "Id do cliente deve estar preenchido.")
    private UUID clientId;
    private Set<ItemRequest> itemList;
    @NotBlank(message = "Nome da lista de desejos deve estar preenchido.")
    private String name;
    private String comment;
}
