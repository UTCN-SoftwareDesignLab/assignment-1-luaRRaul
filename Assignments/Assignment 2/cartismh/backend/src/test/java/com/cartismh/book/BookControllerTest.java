package com.cartismh.book;

import com.cartismh.BaseControllerTest;
import com.cartismh.TestCreationFactory;
import com.cartismh.book.dto.BookDTO;
import com.cartismh.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.cartismh.TestCreationFactory.*;
import static com.cartismh.UrlMapping.BOOKS;
import static com.cartismh.UrlMapping.ENTITY;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    protected void setUp() {
        super.setUp();
//        MockitoAnnotations.openMocks(this);
        controller = new BookController(bookService, reportServiceFactory);
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

    @Test
    void edit() throws Exception {
        long id = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomFloat())
                .quantity(randomInt())
                .build();

        when(bookService.edit(id, reqBook)).thenReturn(reqBook);

        ResultActions result = performPutWithRequestBodyAndPathVariable(BOOKS+ENTITY, reqBook, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void changeTitle() throws Exception {
        long id = randomLong();
        String newName = randomString();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .title(newName)
                .author(randomString())
                .genre(randomString())
                .price(randomFloat())
                .quantity(randomInt())
                .build();

        when(bookService.changeTitle(id, newName)).thenReturn(reqBook);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(BOOKS+ENTITY, newName, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void getItem() throws Exception {
        long id  = randomLong();
        BookDTO reqBook = BookDTO.builder()
                .id(id)
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomFloat())
                .quantity(randomInt())
                .build();
        when(bookService.get(id)).thenReturn(reqBook);

        ResultActions result = performGetWithPathVariable(BOOKS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void delete() throws Exception {
        long id  = randomLong();
        doNothing().when(bookService).delete(id);

        ResultActions result = performDeleteWithPathVariable(BOOKS + ENTITY, id);
        result.andExpect(status().isOk());
    }
}