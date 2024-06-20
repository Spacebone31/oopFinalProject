import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class MemberDialog extends JDialog {
    private JTextField nameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField contactField;
    private JTextField debtField;
    private JButton okButton;
    private JButton cancelButton;
    private Member[] memberHolder;

    public MemberDialog(Member member) {
        setTitle(member == null ? "Add Member" : "Edit Member");
        setLayout(new GridLayout(6, 2, 10, 10));
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.memberHolder = new Member[]{member};

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Email:"));
        contactField = new JTextField();
        add(contactField);

        add(new JLabel("Debt:"));
        debtField = new JTextField();
        add(debtField);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    if (memberHolder[0] == null) {
                        memberHolder[0] = new Member(
                            0, 
                            nameField.getText(), 
                            addressField.getText(), 
                            phoneField.getText(), 
                            contactField.getText(), 
                            Double.parseDouble(debtField.getText())
                        );
                    } else {
                        memberHolder[0].setName(nameField.getText());
                        memberHolder[0].setAddress(addressField.getText());
                        memberHolder[0].setPhone(phoneField.getText());
                        memberHolder[0].setContact(contactField.getText());
                        memberHolder[0].setDebt(Double.parseDouble(debtField.getText()));
                    }
                    JOptionPane.showMessageDialog(MemberDialog.this, "Operation successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });
        add(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memberHolder[0] = null;
                JOptionPane.showMessageDialog(MemberDialog.this, "Operation cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });
        add(cancelButton);

        if (member != null) {
            nameField.setText(member.getName());
            addressField.setText(member.getAddress());
            phoneField.setText(member.getPhone());
            contactField.setText(member.getContact());
            debtField.setText(String.valueOf(member.getDebt()));
        }

        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private boolean validateInput() {
        if (nameField.getText().isEmpty() || addressField.getText().isEmpty() || phoneField.getText().isEmpty() || contactField.getText().isEmpty() || debtField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isValidEmail(contactField.getText())) {
            JOptionPane.showMessageDialog(this, "Contact must be a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(phoneField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Phone must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(debtField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Debt must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return email != null && pat.matcher(email).matches();
    }

    public Member getMember() {
        return memberHolder[0];
    }
}
