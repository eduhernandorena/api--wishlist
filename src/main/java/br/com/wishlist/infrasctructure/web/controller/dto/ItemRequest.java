package br.com.wishlist.infrasctructure.web.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Getter
@Setter
@Validated
@AllArgsConstructor
public class ItemRequest {
    @NotBlank(message = "A descrição do produto deve estar preenchida")
    private String productDescription;
    @NotNull(message = "O id do produto deve estar preenchido")
    private UUID productId;
}
