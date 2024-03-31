package CPRO2501.OrderService.service;

import CPRO2501.BookService.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
    // mock RestTemplate
    @Mock
    RestTemplate restTemplate;

    // create an instance of OrderService and inject the mock restTemplate
    @InjectMocks
    private OrderService orderService;

    // set up Mockito annotations before each test method
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllExistingBooksTest() {
        // create a sample book
        Book book = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // create a list containing the sample book
        List<Book> expectedBooks = List.of(book);

        // define the ResponseEntity that will be returned by the mocked RestTemplate
        ResponseEntity<List<Book>> responseEntity = new ResponseEntity<>(expectedBooks, HttpStatus.OK);

        // mock the behaviour of restTemplate.exchange() to return responseEntity
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))
        ).thenReturn(responseEntity);

        // call the getAllBooks method
        List<Book> actualBooks = orderService.getAllBooks();

        // assert that the expected list of books is equal to the actual list of books
        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    void getAllNonexistentBooksTest() {
        // define the ResponseEntity that will be returned by the mocked RestTemplate
        ResponseEntity<List<Book>> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);

        // mock the behaviour of restTemplate.exchange() to return responseEntity
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))
        ).thenReturn(responseEntity);

        // call the getAllBooks method
        List<Book> actualBooks = orderService.getAllBooks();

        // assert that the expected response is equal to the actual response
        assertEquals(null, actualBooks);
    }

    @Test
    void getExistingBookByTitleTest() {
        // create a sample book
        Book expectedBook = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // create an expected response
        String expectedResponse = "Book: " + expectedBook.getTitle() + "\n"
                + "Author: " + expectedBook.getAuthor() + "\n"
                + "Genre: " + expectedBook.getGenre() + "\n"
                + "ISBN: " + expectedBook.getIsbn() + "\n"
                + "Price: " + expectedBook.getPrice() + "\n"
                + "Status: Order placed!";

        // define the ResponseEntity that will be returned by the mocked RestTemplate
        ResponseEntity<Book> responseEntity = new ResponseEntity<>(expectedBook, HttpStatus.OK);

        // mock the behaviour of restTemplate.exchange() to return responseEntity
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))
        ).thenReturn(responseEntity);

        // call the getBookByTitle method
        String actualResponse = orderService.getBookByTitle("Night");

        // assert that the expected response is equal to the actual response
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getNonexistentBookByTitleTest() {
        // create an expected response
        String expectedResponse = "Book could not be found.";

        // define the ResponseEntity that will be returned by the mocked RestTemplate
        ResponseEntity<Book> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);

        // mock the behaviour of restTemplate.exchange() to return responseEntity
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))
        ).thenReturn(responseEntity);

        // call the getBookByTitle method
        String actualResponse = orderService.getBookByTitle("Night");

        // assert that the expected response is equal to the actual response
        assertEquals(expectedResponse, actualResponse);
    }
}
