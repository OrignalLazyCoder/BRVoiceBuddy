package Model;

public class Contact {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String homeIntercom;
    private String officeIntercom;
    private String email;
    private String phoneNumber;
    private String dept;

    public Contact() {
    }

    public Contact(String id, String firstName, String lastName, String address, String homeIntercom, String officeIntercom, String email, String phoneNumber, String dept) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.homeIntercom = homeIntercom;
        this.officeIntercom = officeIntercom;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dept = dept;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomeIntercom() {
        return homeIntercom;
    }

    public void setHomeIntercom(String homeIntercom) {
        this.homeIntercom = homeIntercom;
    }

    public String getOfficeIntercom() {
        return officeIntercom;
    }

    public void setOfficeIntercom(String officeIntercom) {
        this.officeIntercom = officeIntercom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", homeIntercom=" + homeIntercom +
                ", officeIntercom=" + officeIntercom +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
