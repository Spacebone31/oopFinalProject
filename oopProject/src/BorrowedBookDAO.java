import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowedBookDAO implements BorrowedBookDAOInterface {
    public List<BorrowedBook> getAllBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT b.id, b.book_id, b.member_id, b.borrow_date, b.due_date, b.return_date, bk.title AS book_name, m.name AS member_name " +
                           "FROM BorrowedBooks b " +
                           "JOIN Books bk ON b.book_id = bk.id " +
                           "JOIN Members m ON b.member_id = m.id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                BorrowedBook borrowedBook = new BorrowedBook(
                    rs.getInt("id"),
                    rs.getInt("book_id"),
                    rs.getInt("member_id"),
                    rs.getDate("borrow_date"),
                    rs.getDate("due_date"),
                    rs.getDate("return_date"),
                    rs.getString("book_name"),
                    rs.getString("member_name")
                );
                borrowedBooks.add(borrowedBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowedBooks;
    }

    public void addBorrowedBook(BorrowedBook borrowedBook) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO BorrowedBooks (book_id, member_id, borrow_date, due_date, return_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, borrowedBook.getBookId());
            pstmt.setInt(2, borrowedBook.getMemberId());
            pstmt.setDate(3, new java.sql.Date(borrowedBook.getBorrowDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(borrowedBook.getDueDate().getTime()));
            pstmt.setDate(5, borrowedBook.getReturnDate() != null ? new java.sql.Date(borrowedBook.getReturnDate().getTime()) : null);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBorrowedBook(BorrowedBook borrowedBook) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE BorrowedBooks SET book_id = ?, member_id = ?, borrow_date = ?, due_date = ?, return_date = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, borrowedBook.getBookId());
            pstmt.setInt(2, borrowedBook.getMemberId());
            pstmt.setDate(3, new java.sql.Date(borrowedBook.getBorrowDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(borrowedBook.getDueDate().getTime()));
            pstmt.setDate(5, borrowedBook.getReturnDate() != null ? new java.sql.Date(borrowedBook.getReturnDate().getTime()) : null);
            pstmt.setInt(6, borrowedBook.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBorrowedBook(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM BorrowedBooks WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBorrowedBooksByMemberId(int memberId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM BorrowedBooks WHERE member_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
