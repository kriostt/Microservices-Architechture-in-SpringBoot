package CPRO2501.OrderService.service;

import CPRO2501.BookService.entity.Book;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

// handles business logic related to book orders
@Service
public class OrderService {
    // retrieve list of books from BookService
    public List<Book> getAllBooks() {
        // specifies endpoint URL where HTTP request will be sent
        String url = "http://localhost:8080/books/all";

        // create new instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // use RestTemplate exchange method to execute HTTP GET request on the defined URL
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY, // no request body required
                new ParameterizedTypeReference<List<Book>>() {} // specifies type of response body
        );

        // return list of books from endpoint
        return response.getBody();
    }

    // retrieve a book with a specific title
    public String getBookByTitle(String title) {
        // get list of books from getAllBooks method that communicates with BookService
        List<Book> books = getAllBooks();

        // initialize the Book object outside the loop
        Book foundBook = null;

        // iterate through list of books to check if they have the specified title
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                // set the foundBook
                foundBook = b;

                break;
            }
        }

        // if book with specified title is found, create string with order details and return
        if (foundBook != null) {
            String orderInfo = "Book: " + foundBook.getTitle() + "\n"
                    + "Author: " + foundBook.getAuthor() + "\n"
                    + "Genre: " + foundBook.getGenre() + "\n"
                    + "ISBN: " + foundBook.getIsbn() + "\n"
                    + "Price: " + foundBook.getPrice() + "\n"
                    + "Status: Order placed!";
            return orderInfo;
        } else {
            // if no book is found, return error message
            return ("Book could not be found.");
        }
    }
}
