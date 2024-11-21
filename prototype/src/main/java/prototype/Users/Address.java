package prototype.Users;

public class Address {

    private String number, street, postalCode;

    public Address(String number, String street, String postalCode) {
        this.number = number;
        this.street = street;
        this.postalCode = postalCode;
    }

    public String getNumber() {
        return this.number;
    }

    public String getStreet() {
        return this.street;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}