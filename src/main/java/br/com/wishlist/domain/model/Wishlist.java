package br.com.wishlist.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@Document(collection = "wishlists")
public class Wishlist implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    private UUID clientId;
    private Set<Item> itemList;
    private String name;
    private String comment;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
