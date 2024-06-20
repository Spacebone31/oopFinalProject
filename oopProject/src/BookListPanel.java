import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BookListPanel extends JPanel {
    private BookDAOInterface bookDAO;
    private BookTableModel bookTableModel;
    private JTable bookTable;

    public BookListPanel() {
        bookDAO = new BookDAO();
        bookTableModel = new BookTableModel();

        setLayout(new BorderLayout());

        bookTable = new JTable(bookTableModel);
        add(new JScrollPane(bookTable), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(e -> addBook());
        JButton editButton = new JButton("Edit Book");
        editButton.addActionListener(e -> editBook());
        JButton deleteButton = new JButton("Delete Book");
        deleteButton.addActionListener(e -> deleteBook());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadBooks());

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(refreshButton);

        add(panel, BorderLayout.SOUTH);

        loadBooks();
    }

    private void loadBooks() {
        List<Book> books = bookDAO.getAllBooks();
        bookTableModel.setBooks(books);
    }

    private void addBook() {
        BookDialog dialog = new BookDialog(null);
        dialog.setVisible(true);
        Book book = dialog.getBook();
        if (book != null) {
            bookDAO.addBook(book);
            loadBooks();
            JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Book addition failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            Book selectedBook = bookTableModel.getBookAt(selectedRow);
            BookDialog dialog = new BookDialog(selectedBook);
            dialog.setVisible(true);
            Book updatedBook = dialog.getBook();
            if (updatedBook != null) {
                bookDAO.updateBook(updatedBook);
                loadBooks();
                JOptionPane.showMessageDialog(this, "Book updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow >= 0) {
            Book selectedBook = bookTableModel.getBookAt(selectedRow);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bookDAO.deleteBook(selectedBook.getId());
                loadBooks();
                JOptionPane.showMessageDialog(this, "Book deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
