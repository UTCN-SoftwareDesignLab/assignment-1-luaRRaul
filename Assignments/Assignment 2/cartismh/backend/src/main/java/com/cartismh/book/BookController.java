package com.cartismh.book;

import com.cartismh.book.dto.BookDTO;
import com.cartismh.report.ReportServiceFactory;
import com.cartismh.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartismh.UrlMapping.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

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

    @PatchMapping(ENTITY)
    public boolean sell(@PathVariable Long id){
        return bookService.sell(id);
    }
// nu merge cu 2 patch mapping aparent
//    @PatchMapping(ENTITY)//partial edit
//    public BookDTO changeTitle(@PathVariable Long id, @RequestBody String newName){
//        return bookService.changeTitle(id, newName);
//    }


    @GetMapping(ENTITY)
    public BookDTO getItem(@PathVariable Long id){
        return bookService.get(id);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id)
    {
        bookService.delete(id);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }


}
