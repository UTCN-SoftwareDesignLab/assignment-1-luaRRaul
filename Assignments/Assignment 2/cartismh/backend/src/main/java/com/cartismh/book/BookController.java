package com.cartismh.book;

import com.cartismh.book.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartismh.UrlMapping.BOOKS;
import static com.cartismh.UrlMapping.ENTITY;

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

    @PutMapping(ENTITY)//full edit
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book){
        return bookService.edit(id, book);
    }

    @PatchMapping(ENTITY)//partial edit
    public BookDTO changeTitle(@PathVariable Long id, @RequestBody String newName){
        return bookService.changeTitle(id, newName);
    }

    @GetMapping(ENTITY)
    public BookDTO getItem(@PathVariable Long id){
        return bookService.get(id);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id)
    {
        bookService.delete(id);
    }


}
