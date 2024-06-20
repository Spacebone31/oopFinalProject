import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO implements MemberDAOInterface {
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Members";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Member member = new Member(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("contact"),
                    rs.getDouble("debt")
                );
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public void addMember(Member member) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Members (name, address, phone, contact, debt) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getAddress());
            pstmt.setString(3, member.getPhone());
            pstmt.setString(4, member.getContact());
            pstmt.setDouble(5, member.getDebt());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMember(Member member) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE Members SET name = ?, address = ?, phone = ?, contact = ?, debt = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getAddress());
            pstmt.setString(3, member.getPhone());
            pstmt.setString(4, member.getContact());
            pstmt.setDouble(5, member.getDebt());
            pstmt.setInt(6, member.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMember(int id) {
        BorrowedBookDAO borrowedBookDAO = new BorrowedBookDAO();
        borrowedBookDAO.deleteBorrowedBooksByMemberId(id);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM Members WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
