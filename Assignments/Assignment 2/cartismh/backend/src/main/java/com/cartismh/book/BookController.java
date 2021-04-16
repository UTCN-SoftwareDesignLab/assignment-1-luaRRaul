package com.cartismh.book;

import com.cartismh.book.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartismh.UrlMapping.BOOKS;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> allBooks(){
        return bookService.allBooksForList();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book){
        return bookService.create(book);
    }
}
