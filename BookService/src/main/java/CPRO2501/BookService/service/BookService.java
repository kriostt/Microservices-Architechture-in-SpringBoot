package CPRO2501.BookService.service;

import CPRO2501.BookService.entity.Book;
import CPRO2501.BookService.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

// handles business logic related to Book entities
@Service
public class BookService {
    // automatic injection of IBookRepository instance
    @Autowired
    private IBookRepository repository;

    // add a new book
    public String addBook(Book book) {
        repository.save(book);
        return "Book successfully added!";
    }

    // retrieve all books
    public List<Book> getAllBooks() {
        List<Book> books = repository.findAll();
        return books != null ? books : Collections.emptyList();
    }

    // retrieve a single book by ID
    public Book getBookById(long id) {
        return repository.findById(id).orElse(null);
    }

    // retrieve a single book by title
    public Book getBookByTitle(String title) {
        // get list of books
        List<Book> books = repository.findAll();

        // iterate through list of books to check if they have the specified title
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                // if book with specified title found, return the book
                return b;
            }
        }

        // if no book with the specified title found, return null
        return null;
    }

    // update existing book
    public String updateBook(long id, Book updatedBook) {
        // retrieve the book with an id that matches the provided book's id
        Book existingBook = repository.findById(id).orElse(null);

        // if the book is found, set its data to the new input data
        if (existingBook != null) {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setGenre(updatedBook.getGenre());
            existingBook.setIsbn(updatedBook.getIsbn());
            existingBook.setPrice(updatedBook.getPrice());

            // return success message
            return "Book successfully updated!";
        } else {
            // return error message if book not found
            return "Book could not be found.";
        }
    }

    // delete a book
    public String deleteBook(long id) {
        // retrieve the book with an id that matches the provided book's id
        Book existingBook = repository.findById(id).orElse(null);

        // if the book is found, delete it
        if (existingBook != null) {
            repository.deleteById(id);

            // return success message
            return "Book successfully deleted!";
        } else {
            // return error message if book not found
            return "Book could not be found.";
        }
    }
}
