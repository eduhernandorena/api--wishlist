package br.com.wishlist.application.controller.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class WishlistRequest {
    private UUID clientId;
    private Set<ItemRequest> itemList;
    private String name;
    private String comment;
}
