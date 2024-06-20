import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookDialog extends JDialog {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JCheckBox availableCheckBox;
    private JButton okButton;
    private JButton cancelButton;
    private Book[] bookHolder;

    public BookDialog(Book book) {
        setTitle(book == null ? "Add Book" : "Edit Book");
        setLayout(new GridLayout(5, 2, 10, 10));
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.bookHolder = new Book[]{book};

        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        add(authorField);

        add(new JLabel("Genre:"));
        genreField = new JTextField();
        add(genreField);

        add(new JLabel("Available:"));
        availableCheckBox = new JCheckBox();
        add(availableCheckBox);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    if (bookHolder[0] == null) {
                        bookHolder[0] = new Book(0, titleField.getText(), authorField.getText(), genreField.getText(), availableCheckBox.isSelected());
                    } else {
                        bookHolder[0].setTitle(titleField.getText());
                        bookHolder[0].setAuthor(authorField.getText());
                        bookHolder[0].setGenre(genreField.getText());
                        bookHolder[0].setAvailable(availableCheckBox.isSelected());
                    }
                    JOptionPane.showMessageDialog(BookDialog.this, "Operation successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });
        add(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookHolder[0] = null;
                JOptionPane.showMessageDialog(BookDialog.this, "Operation cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        add(cancelButton);

        if (book != null) {
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            genreField.setText(book.getGenre());
            availableCheckBox.setSelected(book.isAvailable());
        }

        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private boolean validateInput() {
        if (titleField.getText().isEmpty() || authorField.getText().isEmpty() || genreField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public Book getBook() {
        return bookHolder[0];
    }
}
