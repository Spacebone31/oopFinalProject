import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MemberListPanel extends JPanel {
    private MemberDAOInterface memberDAO;
    private MemberTableModel memberTableModel;
    private JTable memberTable;

    public MemberListPanel() {
        memberDAO = new MemberDAO();
        memberTableModel = new MemberTableModel();

        setLayout(new BorderLayout());

        memberTable = new JTable(memberTableModel);
        add(new JScrollPane(memberTable), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add Member");
        addButton.addActionListener(e -> addMember());
        JButton editButton = new JButton("Edit Member");
        editButton.addActionListener(e -> editMember());
        JButton deleteButton = new JButton("Delete Member");
        deleteButton.addActionListener(e -> deleteMember());

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.SOUTH);

        loadMembers();
    }

    private void loadMembers() {
        List<Member> members = memberDAO.getAllMembers();
        memberTableModel.setMembers(members);
    }

    private void addMember() {
        MemberDialog dialog = new MemberDialog(null);
        dialog.setVisible(true);
        Member member = dialog.getMember();
        if (member != null) {
            memberDAO.addMember(member);
            loadMembers();
            JOptionPane.showMessageDialog(this, "Member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Member addition failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow >= 0) {
            Member selectedMember = memberTableModel.getMemberAt(selectedRow);
            MemberDialog dialog = new MemberDialog(selectedMember);
            dialog.setVisible(true);
            Member updatedMember = dialog.getMember();
            if (updatedMember != null) {
                memberDAO.updateMember(updatedMember);
                loadMembers();
                JOptionPane.showMessageDialog(this, "Member updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow >= 0) {
            Member selectedMember = memberTableModel.getMemberAt(selectedRow);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this member?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                memberDAO.deleteMember(selectedMember.getId());
                loadMembers();
                JOptionPane.showMessageDialog(this, "Member deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
