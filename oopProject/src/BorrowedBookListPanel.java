import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BorrowedBookListPanel extends JPanel {
    private BookDAOInterface bookDAO;
    private MemberDAOInterface memberDAO;
    private BorrowedBookDAOInterface borrowedBookDAO;
    private BorrowedBookTableModel borrowedBookTableModel;
    private JTable borrowedBookTable;

    public BorrowedBookListPanel() {
        bookDAO = new BookDAO();
        memberDAO = new MemberDAO();
        borrowedBookDAO = new BorrowedBookDAO();
        borrowedBookTableModel = new BorrowedBookTableModel();

        setLayout(new BorderLayout());

        borrowedBookTable = new JTable(borrowedBookTableModel);
        add(new JScrollPane(borrowedBookTable), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add Borrowed Book");
        addButton.addActionListener(e -> addBorrowedBook());
        JButton editButton = new JButton("Edit Borrowed Book");
        editButton.addActionListener(e -> editBorrowedBook());
        JButton deleteButton = new JButton("Delete Borrowed Book");
        deleteButton.addActionListener(e -> deleteBorrowedBook());

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.SOUTH);

        loadBorrowedBooks();
    }

    private void loadBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = borrowedBookDAO.getAllBorrowedBooks();
        borrowedBookTableModel.setBorrowedBooks(borrowedBooks);
    }

    private void addBorrowedBook() {
        List<Book> books = bookDAO.getAllBooks();
        List<Member> members = memberDAO.getAllMembers();
        BorrowedBookDialog dialog = new BorrowedBookDialog(null, books, members);
        dialog.setVisible(true);
        BorrowedBook borrowedBook = dialog.getBorrowedBook();
        if (borrowedBook != null) {
            borrowedBookDAO.addBorrowedBook(borrowedBook);
            bookDAO.updateBookAvailability(); // Update the availability of books
            loadBorrowedBooks();
            JOptionPane.showMessageDialog(this, "Borrowed book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Borrowed book addition failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editBorrowedBook() {
        int selectedRow = borrowedBookTable.getSelectedRow();
        if (selectedRow >= 0) {
            BorrowedBook selectedBorrowedBook = borrowedBookTableModel.getBorrowedBookAt(selectedRow);
            List<Book> books = bookDAO.getAllBooks();
            List<Member> members = memberDAO.getAllMembers();
            BorrowedBookDialog dialog = new BorrowedBookDialog(selectedBorrowedBook, books, members);
            dialog.setVisible(true);
            BorrowedBook updatedBorrowedBook = dialog.getBorrowedBook();
            if (updatedBorrowedBook != null) {
                borrowedBookDAO.updateBorrowedBook(updatedBorrowedBook);
                bookDAO.updateBookAvailability(); // Update the availability of books
                loadBorrowedBooks();
                JOptionPane.showMessageDialog(this, "Borrowed book updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a borrowed book to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBorrowedBook() {
        int selectedRow = borrowedBookTable.getSelectedRow();
        if (selectedRow >= 0) {
            BorrowedBook selectedBorrowedBook = borrowedBookTableModel.getBorrowedBookAt(selectedRow);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this borrowed book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                borrowedBookDAO.deleteBorrowedBook(selectedBorrowedBook.getId());
                bookDAO.updateBookAvailability(); // Update the availability of books
                loadBorrowedBooks();
                JOptionPane.showMessageDialog(this, "Borrowed book deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a borrowed book to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
