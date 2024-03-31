package CPRO2501.OrderService.controller;

import CPRO2501.BookService.entity.Book;
import CPRO2501.OrderService.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class OrderControllerTest {
    // initialize the MockMvc instance
    private MockMvc mockMvc;

    // mock the OrderService dependency
    @Mock
    private OrderService orderService;

    // inject the mocked OrderService into the OrderController
    @InjectMocks
    private OrderController orderController;

    // runs before each test method
    @BeforeEach
    void setUp() {
        // initializes annotated mock objects
        MockitoAnnotations.openMocks(this);

        // build standalone MockMvc instance for the OrderController
        mockMvc = standaloneSetup(orderController).build();
    }

    @Test
    void getAllBooksTestShouldReturnAllBooks_WhenBooksExist() throws Exception {
        // create a sample book
        Book book = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // create a list containing the sample book
        List<Book> books = List.of(book);

        // mock the behaviour of orderService.getAllBooks() to return the list of books
        given(orderService.getAllBooks()).willReturn(books);

        // perform a GET request to the "/orders/available-books" endpoint
        mockMvc.perform(get("/orders/available-books"))
                // expect a status code of 200 (OK)
                .andExpect(status().isOk())
                // expect the response content to match the provided JSON representation of the book
                .andExpect(content().json("[{'id': 1, 'title': 'Night', 'author': 'Elie Wiesel', 'genre': 'Memoir', 'isbn': '978-0-8090-7350-4', 'price': 15.00}]"));
    }

    @Test
    void getAllBooksTestShouldReturnNothing_WhenBooksDoNotExist() throws Exception {
        // mock the behaviour of orderService.getAllBooks() to return null
        given(orderService.getAllBooks()).willReturn(null);

        // perform a GET request to the "/orders/available-books" endpoint
        mockMvc.perform(get("/orders/available-books"))
                // expect a status code of 200 (OK)
                .andExpect(status().isOk())
                // expect the response content to be empty
                .andExpect(content().string(""));
    }

    @Test
    void getBookByTitleShouldReturnOrderPlaced_WhenBookExists() throws Exception {
        // create a sample book
        Book expectedBook = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // define expected response when getting a book by title
        String expectedResponse = "Book: " + expectedBook.getTitle() + "\n"
                + "Author: " + expectedBook.getAuthor() + "\n"
                + "ISBN:  " + expectedBook.getIsbn() + "\n"
                + "Genre:  " + expectedBook.getGenre() + "\n"
                + "Price:  " + expectedBook.getPrice() + "\n"
                + "Status: Order placed!";

        // mock the behaviour of orderService.getBookByTitle() to return the expected book
        given(orderService.getBookByTitle("Night")).willReturn(expectedResponse);

        // perform a GET request to the "/orders/book/{title}" endpoint
        mockMvc.perform(get("/orders/book/{title}", "Night"))
                // expect a status cde of 200 (OK)
                .andExpect(status().isOk())
                // expect the response content to match the provided JSON representation of the book
                .andExpect(content().string(expectedResponse));
    }

    @Test
    void getBookByTitleShouldReturnErrorMessage_WhenBookDoesNotExist() throws Exception {
        // mock the behaviour of orderService.getBookByTitle() to return error message
        given(orderService.getBookByTitle("Night")).willReturn("Book could not be found.");

        // perform a GET request to the "/orders/book/{title}" endpoint
        mockMvc.perform(get("/orders/book/{title}", "Night"))
                // expect a status cde of 200 (OK)
                .andExpect(status().isOk())
                // expect the response content to match the provided JSON representation of the book
                .andExpect(content().string("Book could not be found."));
    }
}
