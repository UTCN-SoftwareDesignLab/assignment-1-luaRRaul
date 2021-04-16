package com.cartismh.book;

import com.cartismh.BaseControllerTest;
import com.cartismh.TestCreationFactory;
import com.cartismh.book.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.cartismh.TestCreationFactory.*;
import static com.cartismh.UrlMapping.BOOKS;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allBooks() throws Exception {
        List<BookDTO> bookListDTOs = TestCreationFactory.listOf(BookDTO.class);
        when(bookService.allBooksForList()).thenReturn(bookListDTOs);

        ResultActions result = mockMvc.perform(get(BOOKS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookListDTOs));
    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomFloat())
                .quantity(randomInt())
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOKS, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));

    }
}