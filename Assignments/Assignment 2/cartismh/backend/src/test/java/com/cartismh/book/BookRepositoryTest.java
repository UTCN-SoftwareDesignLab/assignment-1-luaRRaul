package com.cartismh.book;

import com.cartismh.book.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BookRepositoryTest {
    @Autowired
    private BookRepository repository;

    @BeforeEach
    public void beforeEach(){
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book itemSaved = repository.save(Book.builder().title("whatever").author("author test").genre("genre").price((float) 99.2).quantity(20).build());

        assertNotNull(itemSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

}