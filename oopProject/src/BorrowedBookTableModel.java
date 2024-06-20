import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BorrowedBookTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Book Name", "Member Name", "Borrow Date", "Due Date", "Return Date"};
    private List<BorrowedBook> borrowedBooks;

    public void setBorrowedBooks(List<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return borrowedBooks == null ? 0 : borrowedBooks.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BorrowedBook borrowedBook = borrowedBooks.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return borrowedBook.getBookName();
            case 1:
                return borrowedBook.getMemberName();
            case 2:
                return borrowedBook.getBorrowDate();
            case 3:
                return borrowedBook.getDueDate();
            case 4:
                return borrowedBook.getReturnDate();
            default:
                return null;
        }
    }

    public BorrowedBook getBorrowedBookAt(int rowIndex) {
        return borrowedBooks.get(rowIndex);
    }
}
