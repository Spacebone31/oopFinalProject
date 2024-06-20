import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements BookDAOInterface {
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getBoolean("available")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void addBook(Book book) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Books (title, author, genre, available) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getGenre());
            pstmt.setBoolean(4, book.isAvailable());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE Books SET title = ?, author = ?, genre = ?, available = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getGenre());
            pstmt.setBoolean(4, book.isAvailable());
            pstmt.setInt(5, book.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Books WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBookAvailability() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE Books SET available = CASE WHEN id IN (SELECT book_id FROM BorrowedBooks WHERE return_date IS NULL) THEN 0 ELSE 1 END";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
