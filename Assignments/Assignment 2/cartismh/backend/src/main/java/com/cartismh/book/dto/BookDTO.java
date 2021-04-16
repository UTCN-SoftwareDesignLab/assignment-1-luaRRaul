package com.cartismh.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
public class BookDTO extends BookMinimalDTO{
    private String genre;
    private float price;
    private int quantity;
}
