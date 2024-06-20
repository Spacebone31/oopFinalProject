import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BookTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Title", "Author", "Genre", "Available"};
    private List<Book> books;

    public void setBooks(List<Book> books) {
        this.books = books;
        fireTableDataChanged();
    }

    public Book getBookAt(int rowIndex) {
        return books.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return books == null ? 0 : books.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (books == null) return null;

        Book book = books.get(rowIndex);
        switch (columnIndex) {
            case 0: return book.getTitle();
            case 1: return book.getAuthor();
            case 2: return book.getGenre();
            case 3: return book.isAvailable();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
