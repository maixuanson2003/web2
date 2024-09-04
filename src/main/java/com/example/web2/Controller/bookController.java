package com.example.web2.Controller;
import com.example.web2.DTO.request.BookRequest;
import com.example.web2.DTO.response.BookResponse;
import com.example.web2.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class bookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks()throws IOException {
        List<BookResponse> books = bookService.getALLBook();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable int id)throws IOException {
        BookResponse book = bookService.findBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> findBooksByCondition(
            @RequestParam(required = false) String nameAuthor,
            @RequestParam(required = false, defaultValue = "0") int totalPages,
            @RequestParam(required = false) String dateStart,
            @RequestParam(required = false) String dateEnd,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String bookCoditionRequest) throws IOException {
        List<BookResponse> books = bookService.findBookByCodition(nameAuthor, totalPages, dateStart, dateEnd, bookCoditionRequest, category);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/sortByDate")
    public ResponseEntity<List<BookResponse>> sortBooksByDate()throws IOException {
        List<BookResponse> books = bookService.sortBookByDate();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest bookRequest)throws IOException {
        BookResponse book = bookService.createBook(bookRequest);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable int id)throws IOException {
        String response = bookService.DeleteBookById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
