package com.cartismh.book.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private float price;
    private int quantity;
}
