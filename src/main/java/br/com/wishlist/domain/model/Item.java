package br.com.wishlist.domain.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String productDescription;
    private UUID productId;
}
