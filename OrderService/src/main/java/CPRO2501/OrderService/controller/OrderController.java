package CPRO2501.OrderService.controller;

import CPRO2501.BookService.entity.Book;
import CPRO2501.OrderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// handle HTTP requests related to book orders
@RestController
@RequestMapping("/orders")
public class OrderController {
    // automatic injection of OrderService instance
    @Autowired
    private OrderService orderService;

    // handle HTTP GET request to retrieve all books
    @GetMapping("/available-books")
    public List<Book> getAllBooks() {
        return orderService.getAllBooks();
    }

    // handle HTTP GET request to retrieve a book by title
    @GetMapping("/book/{title}")
    public String getBookByTitle(@PathVariable String title) {
        return orderService.getBookByTitle(title);
    }
}
