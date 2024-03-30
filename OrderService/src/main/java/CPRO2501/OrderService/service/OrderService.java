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

        // iterate through list of books to check if they have the specified title
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                // create a string with the book details and the status of the order
                String orderInfo = "Book: " + b.getTitle() + "\n"
                    + "Author: " + b.getAuthor() + "\n"
                    + "ISBN:  " + b.getIsbn() + "\n"
                    + "Genre:  " + b.getGenre() + "\n"
                    + "Price:  " + b.getPrice() + "\n"
                    + "Status: Order placed!";

                // return the order information
                return orderInfo;
            }
        }

        // return error message if no book found
        return ("Book could not be found.");
    }
}
