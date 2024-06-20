import java.util.List;

public interface BookDAOInterface {
    List<Book> getAllBooks();
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int id);
    void updateBookAvailability();
}
