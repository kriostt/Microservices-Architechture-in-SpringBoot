# CPRO 2501 Assignment 3
## Microservices Architecture in SpringBoot with Test Cases
<p align="justify">
    In this assignment, I implemented two separate Spring Boot applications that communicate with each other using RESTful APIs. The first microservice is ‘BookService’ and the other is ‘OrderService’. The ‘OrderService’ is designed to accept user input for a book title, which is then sent to the ‘BookService’ that checks if the book is in the database. If the book exists, it is returned to the ‘OrderService’. 
</p>

<p align="justify">
    In the ‘BookService’ microservice, I implemented CRUD operations for a defined ‘Book’ entity that is stored in an H2 database. This includes the following endpoints: ‘/books/add’ for adding new books, ‘/books/all’ for retrieving all books, ‘/books/{id}’ for retrieving a book by its ID, ‘/books/title/{title}’ for retrieving a book by its title, ‘/books/update/{id}’ for updating books, and ‘books/delete/{id}’ for deleting books. 
</p>

<p align="justify">
    In the ‘OrderService’ microservice, I implemented two endpoints that need to communicate with the ‘BookService’. The ‘/orders/available-books’ endpoint retrieves a list of books from the ‘BookService’. This endpoint utilizes the ‘OrderService’ class method ‘getAllBooks()’, which communicates with the ‘BookService’ endpoint ‘/books/all’ using RestTemplate. On the other hand, the ‘/orders/book/{title}’ endpoint accepts a book title from the user and returns the order information if the book exists in the ‘BookService’. This endpoint relies on the ‘OrderService’ class method ‘getBookByTitle()’, which communicates with the ‘BookService’ endpoint ‘/books/title/{title}’ using RestTemplate. This method receives the book from ‘BookService’ and returns the order information with book details and order placement confirmation if the book is found. Otherwise, an error message indicating that the book could not be found is returned. 
</p>
