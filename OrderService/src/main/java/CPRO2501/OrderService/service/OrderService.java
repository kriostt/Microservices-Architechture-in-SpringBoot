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
    // add RestTemplate as property to be able to inject restTemplate mock into OrderService
    private RestTemplate restTemplate = new RestTemplate();

    // retrieve list of books from BookService
    public List<Book> getAllBooks() {
        // specifies endpoint URL where HTTP request will be sent
        String url = "http://localhost:8080/books/all";

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
        // specifies endpoint URL where HTTP request will be sent
        String url = "http://localhost:8080/books/title/" + title;

        // use RestTemplate exchange method to execute HTTP GET request on the defined URL
        ResponseEntity<Book> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY, // no request body required
                new ParameterizedTypeReference<Book>() {} // specifies type of response body
        );

        // create foundBook variable to hold the body of the response
        Book foundBook = response.getBody();

        // check if a book was returned
        if (foundBook != null) {
            // create string with order information
            String orderInfo = "Book: " + foundBook.getTitle() + "\n"
                    + "Author: " + foundBook.getAuthor() + "\n"
                    + "Genre: " + foundBook.getGenre() + "\n"
                    + "ISBN: " + foundBook.getIsbn() + "\n"
                    + "Price: " + foundBook.getPrice() + "\n"
                    + "Status: Order placed!";

            // return the order information
            return orderInfo;
        } else {
            // if no book is found, return error message
            return "Book could not be found.";
        }
    }
}
