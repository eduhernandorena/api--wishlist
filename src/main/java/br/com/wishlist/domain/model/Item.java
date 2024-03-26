package br.com.wishlist.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class Item {
    private String productDescription;
    private UUID productId;
}
