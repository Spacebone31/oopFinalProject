public class Member {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String contact;
    private double debt;

    public Member(int id, String name, String address, String phone, String contact, double debt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.contact = contact;
        this.debt = debt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    // Method to format debt as IDR currency
    public String getFormattedDebt() {
        return String.format("IDR %.2f", debt);
    }
}
