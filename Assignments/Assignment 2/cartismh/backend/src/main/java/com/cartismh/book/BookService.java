package com.cartismh.book;

import com.cartismh.book.dto.BookDTO;
import com.cartismh.book.mapper.BookMapper;
import com.cartismh.book.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDTO> allBooksForList() {
        return bookRepository.findAll()
                .stream().map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.fromDto(book)));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = findById(id);
        actBook.setTitle(book.getTitle());
        actBook.setAuthor(book.getAuthor());
        actBook.setGenre(book.getGenre());
        actBook.setPrice(book.getPrice());
        actBook.setQuantity(book.getQuantity());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public BookDTO changeTitle(Long id, String newName) {
        Book actBook = findById(id);
        actBook.setTitle(newName);
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public BookDTO get(Long id) {
        return bookMapper.toDto(findById(id));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public boolean sell(Long id) {
        Book actBook = findById(id);
        int quantity = actBook.getQuantity();
        if( quantity>=1 ) {
            actBook.setQuantity(quantity - 1);
            bookRepository.save(actBook);
            return true;
        }else
            return false;
    }
}
