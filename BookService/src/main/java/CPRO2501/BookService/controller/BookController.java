package CPRO2501.BookService.controller;

import CPRO2501.BookService.entity.Book;
import CPRO2501.BookService.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// handle HTTP requests related to Book entities
@RestController
@RequestMapping("/books")
public class BookController {
    // automatic injection of BookService instance
    @Autowired
    private BookService bookService;

    // handle HTTP POST request to add a new book
    @PostMapping("/add")
    public String addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    // handle HTTP GET request to retrieve all books
    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // handle HTTP GET request to retrieve a book by ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable long id) {
        return bookService.getBookById(id);
    }

    // handle HTTP GET request to retrieve a book by title
    @GetMapping("/title/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    // handle HTTP PUT request to update existing book
    @PutMapping("/update/{id}")
    public String updateBook(@PathVariable long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    // handle HTTP DELETE request to delete a book
    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable long id) {
        return bookService.deleteBook(id);
    }
}
