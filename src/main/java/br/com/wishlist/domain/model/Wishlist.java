package br.com.wishlist.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Document(collection = "wishlists")
public class Wishlist {
    @Id
    private UUID id;
    private UUID clientId;
    private Set<Item> itemList;
    private String name;
    private String comment;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
