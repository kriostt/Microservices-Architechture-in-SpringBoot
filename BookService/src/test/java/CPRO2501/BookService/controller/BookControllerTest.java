package CPRO2501.BookService.controller;

import CPRO2501.BookService.entity.Book;
import CPRO2501.BookService.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class BookControllerTest {
    // initialize the MockMvc instance
    private MockMvc mockMvc;

    // mock the BookService dependency
    @Mock
    private BookService bookService;

    // inject the mocked BookService into the BookController
    @InjectMocks
    private BookController bookController;

    // runs before each test method
    @BeforeEach
    void setUp() {
        // initializes annotated mock objects
        MockitoAnnotations.openMocks(this);

        // build standalone MockMvc instance for the BookController
        mockMvc = standaloneSetup(bookController).build();
    }

    @Test
    void addBookTestShouldReturnString() throws Exception {
        // create a sample book
        Book book = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // mock the behaviour of bookService.addBook() to return a success message
        given(bookService.addBook(book)).willReturn("Book successfully added!");

        // perform a POST request to the "/books/add" endpoint
        mockMvc.perform(post("/books/add")
                        // set the content type of the request to JSON
                        .contentType(MediaType.APPLICATION_JSON)
                        // set the content of the request with a JSON representation of the book
                        .content("{\"id\": 1, \"title\": \"Night\", \"author\": \"Elie Wiesel\", \"genre\": \"Memoir\", \"isbn\": \"978-0-8090-7350-4\", \"price\": 15.00}"))
                // expect a status code of 200 (OK)
                .andExpect(status().isOk())
                // expect the response content to match the provided string
                .andExpect(content().string("Book successfully added!"));
    }

    @Test
    void getAllBooksTestShouldReturnAllBooks() throws Exception {
        // create a sample book
        Book book = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // create a list containing the sample book
        List<Book> books = List.of(book);

        // mock the behaviour of bookService.getAllBooks() to return the list of books
        given(bookService.getAllBooks()).willReturn(books);

        // perform a GET request to the "/books/all" endpoint
        mockMvc.perform(get("/books/all"))
                // expect a status code of 200 (OK)
                .andExpect(status().isOk())
                // expect the response content to match the provided JSON representation of the book
                .andExpect(content().json("[{'id': 1, 'title': 'Night', 'author': 'Elie Wiesel', 'genre': 'Memoir', 'isbn': '978-0-8090-7350-4', 'price': 15.00}]"));
    }

    @Test
    void getBookByIdTestShouldReturnBook() throws Exception {
        // create a sample book
        Book expectedBook = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // mock the behaviour of bookService.getBookById() to return the expected book
        given(bookService.getBookById(1L)).willReturn(expectedBook);

        // perform a GET request to the "/books/{id}" endpoint with ID 1
        mockMvc.perform(get("/books/{id}", 1L))
                // expect a status code of 200 (OK)
                .andExpect(status().isOk())
                // expect the response content to match the provided JSON representation of the book
                .andExpect(content().json("{'id': 1, 'title': 'Night', 'author': 'Elie Wiesel', 'genre': 'Memoir', 'isbn': '978-0-8090-7350-4', 'price': 15.00}"));
    }

    @Test
    void updateBookTestShouldReturnSuccessMessage_WhenBookExists() throws Exception {
        // create a sample book
        Book updatedBook = new Book(1, "New Title", "New Author", "New Genre", "978-0-1234-5678-9", 20.00);

        // mock the behavior of bookService.updateBook() to return a success message
        given(bookService.updateBook(eq(1L), eq(updatedBook))).willReturn("Book successfully updated!");

        // perform a PUT request to the "/books/update/{id}" endpoint
        mockMvc.perform(put("/books/update/{id}", 1L)
                        // set the content type of the request to JSON
                        .contentType(MediaType.APPLICATION_JSON)
                        // set the content of the request with a JSON representation of the updated book
                        .content("{\"id\": 1, \"title\": \"New Title\", \"author\": \"New Author\", \"genre\": \"New Genre\", \"isbn\": \"978-0-1234-5678-9\", \"price\": 20.00}"))
                // expect a status code of 200 (OK)
                .andExpect(status().isOk())
                // expect the response content to match the provided string
                .andExpect(content().string("Book successfully updated!"));
    }

    @Test
    void updateBookTestShouldReturnErrorMessage_WhenBookDoesNotExist() throws Exception {
        // mock the behavior of bookService.updateBook() to return an error message
        given(bookService.updateBook(eq(1L), any(Book.class))).willReturn("Book could not be found.");

        // perform a PUT request to the "/books/update/{id}" endpoint
        mockMvc.perform(put("/books/update/{id}", 1L)
                    // set the content type of the request to JSON
                    .contentType(MediaType.APPLICATION_JSON)
                    // set the content of the request with a JSON representation of the updated book
                    .content("{\"id\": 1, \"title\": \"New Title\", \"author\": \"New Author\", \"genre\": \"New Genre\", \"isbn\": \"978-0-1234-5678-9\", \"price\": 20.00}"))
                // expect a status code of 200 (OK)
                .andExpect(status().isOk())
                // expect the response content to match the provided string
                .andExpect(content().string("Book could not be found."));
    }

    @Test
    void deleteBookTestShouldReturnSuccessMessage_WhenBookExists() throws Exception {
        // create a sample book
        Book book = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // mock the behaviour of bookService.deleteBook() to return success message
        given(bookService.deleteBook(1L)).willReturn("Book successfully deleted!");

        // perform a DELETE request to the "/books/delete/{id}" endpoint
        mockMvc.perform(delete("/books/delete/{id}", 1L))
                // expect a status code of 200 (OK)
                .andExpect(status().isOk())
                // expect the response content to match the provided string
                .andExpect(content().string("Book successfully deleted!"));
    }

    @Test
    void deleteBookTestShouldReturnErrorMessage_WhenBookDoesNotExist() throws Exception {
        // mock the behaviour of bookService.deleteBook() to return error message
        given(bookService.deleteBook(1L)).willReturn("Book could not be found.");

        // perform a DELETE request to the "/books/delete/{id}" endpoint
        mockMvc.perform(delete("/books/delete/{id}", 1L))
                // expect a status code of 200 (OK)
                .andExpect(status().isOk())
                // expect the response content to match the provided string
                .andExpect(content().string("Book could not be found."));
    }
}
