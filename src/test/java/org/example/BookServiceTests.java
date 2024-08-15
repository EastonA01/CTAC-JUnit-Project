package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


@DisplayName("Book Service Tests")
@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }
    // Book searching
    @Test
    @DisplayName("SearchBook Positive Case")
    public void testSearchBookPositiveCase(){

        // Add some books to the database
        Book book1 = new Book("Book1", "Author1", "Genre1", 300);
        Book book2 = new Book("Book2", "Author2", "Genre2", 25);
        Book book3 = new Book("Book3", "Author1", "Genre3",4200);
        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);

        // Search for books by author
        List<Book> searchResult = bookService.searchBook("Author1");
        Assertions.assertEquals(2, searchResult.size(), "Should find 2 books by Author1");

        // Search for books by title
        searchResult = bookService.searchBook("Book2");
        Assertions.assertEquals(1, searchResult.size(), "Should find 1 book with title Book2");

        // Search for books by genre
        searchResult = bookService.searchBook("Genre2");
        Assertions.assertEquals(1, searchResult.size(), "Should find 1 book with genre Genre2");
    }
    @Test
    @DisplayName("SearchBook Negative Case: No Matching Books")
    public void testSearchBookNegativeCase_NoMatchingBooks(){
        // Add some books to the database
        Book book1 = new Book("Book1", "Author1", "Genre1", 300);
        Book book2 = new Book("Book2", "Author2", "Genre2", 25);
        bookService.addBook(book1);
        bookService.addBook(book2);

        // Search for books that don't exist
        List<Book> searchResult = bookService.searchBook("NonExistingBook");
        Assertions.assertEquals(0, searchResult.size(), "Should find 0 books");
    }
    @Test
    @DisplayName("SearchBook Edge Case: Null Book Search")
    public void testSearchBookEdgeCase(){

        // Search for books with a null keyword
        List<Book> searchResult = bookService.searchBook(null);
        Assertions.assertEquals(0, searchResult.size(), "Should find 0 books");
    }

    // Book Purchasing Tests
    @Test
    @DisplayName("BuyBook Positive Case")
    public void testBuyBookPositiveCase(){
        //Create user and books
        User person1 = new User("JohnDoe", "password", "johndoe@gmail.com");
        Book book1 = new Book("Book1", "Author1", "Genre1", 300);
        bookService.addBook(book1);
        Assertions.assertTrue(bookService.purchaseBook(person1, book1));
    }
    @Test
    @DisplayName("BuyBook Negative Case: Book not found")
    public void testBuyBookNegativeCase(){
        User person1 = new User("JohnDoe", "password", "johndoe@gmail.com");
        Book book1 = new Book("Book1", "Author1", "Genre1", 300);
        Book book2 = new Book("Book2", "Author2", "Genre2", 25);
        bookService.addBook(book1);
        Assertions.assertFalse(bookService.purchaseBook(person1, book2));
    }
    @Test
    @DisplayName("BuyBook Edge Case: Buy a Removed Book")
    public void testBuyBookEdgeCase(){
        User person1 = new User("JohnDoe", "password", "johndoe@gmail.com");
        Book book1 = new Book("Book1", "Author1", "Genre1", 300);
        Book book2 = new Book("Book2", "Author2", "Genre2", 25);
        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.purchaseBook(person1, book2);
        bookService.removeBook(book2);
        bookService.removeBook(book1);
        Assertions.assertFalse(bookService.purchaseBook(person1, book2));
    }
}
