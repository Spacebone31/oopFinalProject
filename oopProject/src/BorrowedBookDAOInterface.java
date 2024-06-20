import java.util.List;

public interface BorrowedBookDAOInterface {
    List<BorrowedBook> getAllBorrowedBooks();
    void addBorrowedBook(BorrowedBook borrowedBook);
    void updateBorrowedBook(BorrowedBook borrowedBook);
    void deleteBorrowedBook(int id);
    void deleteBorrowedBooksByMemberId(int memberId);
}
