package br.com.wishlist.application.controller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ItemRequest {
    private String productDescription;
    private UUID productId;
}
