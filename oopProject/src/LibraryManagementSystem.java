import javax.swing.*;
import java.awt.*;

public class LibraryManagementSystem extends JFrame {
    public LibraryManagementSystem() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create Panels
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Books", new BookListPanel());
        tabbedPane.addTab("Members", new MemberListPanel());
        tabbedPane.addTab("Borrowed Books", new BorrowedBookListPanel());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryManagementSystem().setVisible(true);
        });
    }
}
