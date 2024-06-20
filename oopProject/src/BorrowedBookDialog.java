import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

public class BorrowedBookDialog extends JDialog {
    private JComboBox<String> bookComboBox;
    private JComboBox<String> memberComboBox;
    private JTextField borrowDateField;
    private JTextField dueDateField;
    private JTextField returnDateField;
    private JButton okButton;
    private JButton cancelButton;
    private BorrowedBook[] borrowedBookHolder;
    private List<Book> books;
    private List<Member> members;

    public BorrowedBookDialog(BorrowedBook borrowedBook, List<Book> books, List<Member> members) {
        setTitle(borrowedBook == null ? "Add Borrowed Book" : "Edit Borrowed Book");
        setLayout(new GridLayout(6, 2, 10, 10));
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.borrowedBookHolder = new BorrowedBook[]{borrowedBook};
        this.books = books;
        this.members = members;

        add(new JLabel("Book:"));
        bookComboBox = new JComboBox<>();
        for (Book book : books) {
            bookComboBox.addItem(book.getTitle());
        }
        add(bookComboBox);

        add(new JLabel("Member:"));
        memberComboBox = new JComboBox<>();
        for (Member member : members) {
            memberComboBox.addItem(member.getName());
        }
        add(memberComboBox);

        add(new JLabel("Borrow Date (YYYY-MM-DD):"));
        borrowDateField = new JTextField();
        add(borrowDateField);

        add(new JLabel("Due Date (YYYY-MM-DD):"));
        dueDateField = new JTextField();
        add(dueDateField);

        add(new JLabel("Return Date (YYYY-MM-DD):"));
        returnDateField = new JTextField();
        add(returnDateField);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    int selectedBookId = books.get(bookComboBox.getSelectedIndex()).getId();
                    int selectedMemberId = members.get(memberComboBox.getSelectedIndex()).getId();
                    if (borrowedBookHolder[0] == null) {
                        borrowedBookHolder[0] = new BorrowedBook(0, selectedBookId, selectedMemberId, Date.valueOf(borrowDateField.getText()), Date.valueOf(dueDateField.getText()), returnDateField.getText().isEmpty() ? null : Date.valueOf(returnDateField.getText()), bookComboBox.getSelectedItem().toString(), memberComboBox.getSelectedItem().toString());
                    } else {
                        borrowedBookHolder[0].setBookId(selectedBookId);
                        borrowedBookHolder[0].setMemberId(selectedMemberId);
                        borrowedBookHolder[0].setBorrowDate(Date.valueOf(borrowDateField.getText()));
                        borrowedBookHolder[0].setDueDate(Date.valueOf(dueDateField.getText()));
                        borrowedBookHolder[0].setReturnDate(returnDateField.getText().isEmpty() ? null : Date.valueOf(returnDateField.getText()));
                        borrowedBookHolder[0].setBookName(bookComboBox.getSelectedItem().toString());
                        borrowedBookHolder[0].setMemberName(memberComboBox.getSelectedItem().toString());
                    }
                    JOptionPane.showMessageDialog(BorrowedBookDialog.this, "Operation successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });
        add(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowedBookHolder[0] = null;
                JOptionPane.showMessageDialog(BorrowedBookDialog.this, "Operation cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        add(cancelButton);

        if (borrowedBook != null) {
            bookComboBox.setSelectedItem(borrowedBook.getBookName());
            memberComboBox.setSelectedItem(borrowedBook.getMemberName());
            borrowDateField.setText(String.valueOf(borrowedBook.getBorrowDate()));
            dueDateField.setText(String.valueOf(borrowedBook.getDueDate()));
            returnDateField.setText(borrowedBook.getReturnDate() != null ? String.valueOf(borrowedBook.getReturnDate()) : "");
        }

        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private boolean validateInput() {
        if (bookComboBox.getSelectedIndex() == -1 || memberComboBox.getSelectedIndex() == -1 || borrowDateField.getText().isEmpty() || dueDateField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields except return date must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Date.valueOf(borrowDateField.getText());
            Date.valueOf(dueDateField.getText());
            if (!returnDateField.getText().isEmpty()) {
                Date.valueOf(returnDateField.getText());
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Invalid input format.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public BorrowedBook getBorrowedBook() {
        return borrowedBookHolder[0];
    }
}
