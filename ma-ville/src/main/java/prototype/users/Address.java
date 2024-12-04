package prototype.users;

public class Address {

    private String number, street, postalCode, borough;

    public Address(String number, String street, String postalCode, String borough) {
        this.number = number;
        this.street = street;
        this.postalCode = postalCode;
        this.borough = borough;
    }

    public String getNumber() {return this.number;}

    public String getStreet() {return this.street;}

    public String getPostalCode() {return this.postalCode;}

    public String getBorough() {return this.borough;}

    public void setNumber(String number) {this.number = number;}

    public void setStreet(String street) {this.street = street;}

    public void setPostalCode(String postalCode) {this.postalCode = postalCode;}

    public void setBorough(String borough) {this.borough = borough;}

}
