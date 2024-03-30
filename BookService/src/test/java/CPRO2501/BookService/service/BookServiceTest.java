package CPRO2501.BookService.service;

import CPRO2501.BookService.entity.Book;
import CPRO2501.BookService.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class BookServiceTest {
    // mock the BookRepository interface
    @Mock
    IBookRepository repository;

    // create an instance of BookService and inject the mock repository
    @InjectMocks
    private BookService bookService;

    // set up Mockito annotations before each test method
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBookTest() {
        // create a sample book
        Book book = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // create an expected response
        String expectedResponse = "Book successfully added!";

        // mock the behavior of the repository to do nothing when save(book) is called
        given(repository.save(book)).willReturn(null);

        // call the addBook method
        String actualResponse = bookService.addBook(book);

        // assert that the expected response is equal to the actual response
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getAllBooksTest() {
        // create a sample book
        Book book = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // create a list containing the sample book
        List<Book> expectedBooks = List.of(book);

        // mock the behaviour of the repository to return the list of expected books when findAll() is called
        given(repository.findAll()).willReturn(expectedBooks);

        // call the getAllBooks method
        List<Book> actualBooks = bookService.getAllBooks();

        // assert that the expected list of books is equal to the actual list of books
        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    void getBookByIdTest() {
        // create a sample book
        Book expectedBook = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // mock the behaviour of the repository to return the expected book when findById() is called
        given(repository.findById(1L)).willReturn(Optional.of(expectedBook));

        // call the getBookById method
        Book actualBook = bookService.getBookById(1L);

        // assert that the expectedBook is equal to the actualBook
        assertEquals(expectedBook, actualBook);
    }

    @Test
    void updateExistingBookTest() {
        // create a sample book
        Book existingBook = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);
        Book updatedBook = new Book(1, "New Title", "New Author", "New Genre", "978-0-1234-5678-9", 20.00);

        // create an expected response
        String expectedResponse = "Book successfully updated!";

        // mock the behaviour of the repository to return the existing book when findById() is called
        given(repository.findById(1L)).willReturn(Optional.of(existingBook));

        // call the updateBook method
        String actualResponse = bookService.updateBook(1L, updatedBook);

        // verify that the repository's findById method was called with the correct ID
        verify(repository).findById(1L);

        // assert that the expected response is equal to the actual response
        assertEquals(expectedResponse, actualResponse);

        // assert that the existing book was updated with the new price
        assertEquals("New Title", existingBook.getTitle());
        assertEquals("New Author", existingBook.getAuthor());
        assertEquals("New Genre", existingBook.getGenre());
        assertEquals("978-0-1234-5678-9", existingBook.getIsbn());
        assertEquals(20.00, existingBook.getPrice());
    }

    @Test
    void updateNonexistentBookTest() {
        // create an expected response
        String expectedResponse = "Book could not be found.";

        // mock the behaviour of the repository to return null when findById is called
        given(repository.findById(1L)).willReturn(Optional.empty());

        // call the updateBook method
        String actualResponse = bookService.updateBook(1L, new Book());

        // verify that the repository's findById method was called with the correct ID
        verify(repository).findById(1L);

        // assert that the expected response is equal to the actual response
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteExistingBookTest() {
        // create a sample book
        Book existingBook = new Book(1, "Night", "Elie Wiesel", "Memoir", "978-0-8090-7350-4", 15.00);

        // create an expected response
        String expectedResponse = "Book successfully deleted!";

        // mock the behaviour of the repository to return the existing book when findById() is called
        given(repository.findById(1L)).willReturn(Optional.of(existingBook));

        // call the deleteBook method
        String actualResponse = bookService.deleteBook(1L);

        // verify that the repository's findById method was called with the correct ID
        verify(repository).findById(1L);

        // assert that the expected response is equal to the actual response
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteNonexistentBookTest() {
        // create an expected response
        String expectedResponse = "Book could not be found.";

        // mock the behaviour of the repository to return null when findById is called
        given(repository.findById(1L)).willReturn(Optional.empty());

        // call the deleteBook method
        String actualResponse = bookService.deleteBook(1L);

        // verify that the repository's findById method was called with the correct ID
        verify(repository).findById(1L);

        // assert that the expected response is equal to the actual response
        assertEquals(expectedResponse, actualResponse);
    }
}
