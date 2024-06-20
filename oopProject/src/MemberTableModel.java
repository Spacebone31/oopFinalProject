import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MemberTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Name", "Address", "Phone", "Email", "Debt (IDR)"};
    private List<Member> members;

    public void setMembers(List<Member> members) {
        this.members = members;
        fireTableDataChanged();
    }

    public Member getMemberAt(int rowIndex) {
        return members.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return members == null ? 0 : members.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (members == null) return null;

        Member member = members.get(rowIndex);
        switch (columnIndex) {
            case 0: return member.getName();
            case 1: return member.getAddress();
            case 2: return member.getPhone();
            case 3: return member.getContact();
            case 4: return member.getFormattedDebt();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
