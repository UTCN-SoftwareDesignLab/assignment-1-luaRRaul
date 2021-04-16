package com.cartismh.book.mapper;

import com.cartismh.book.dto.BookDTO;
import com.cartismh.book.dto.BookMinimalDTO;
import com.cartismh.book.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public  interface BookMapper {
    @Mappings({
            @Mapping(target = "title", source = "book.title"),
            @Mapping(target = "author", source = "book.author")
    })
    BookMinimalDTO bookMinimalFromBook(Book book);


    BookDTO toDto(Book book);

    Book fromDto(BookDTO book);
}
