import java.util.List;

public interface MemberDAOInterface {
    List<Member> getAllMembers();
    void addMember(Member member);
    void updateMember(Member member);
    void deleteMember(int id);
}
