package br.com.wishlist.infrasctructure.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class WishlistRequest {
    private UUID clientId;
    private Set<ItemRequest> itemList;
    private String name;
    private String comment;
}
