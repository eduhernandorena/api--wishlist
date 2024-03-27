package br.com.wishlist.infrasctructure.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ItemRequest {
    private String productDescription;
    private UUID productId;
}
