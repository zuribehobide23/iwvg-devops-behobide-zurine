package es.upm.miw.devops.infrastructure.data.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "family_name")
    private String familyName;

    private String email;

    private String identity;

    private String address;

    private String city;

    private String province;

    @Column(name = "postal_code")
    private String postalCode;

    private boolean active = false;

    @Enumerated(EnumType.STRING)
    private Role role = Role.MANAGER;

    private String mobile;

    public User() {
    }

    public User(String firstName, String familyName, String email,
                String identity, String address, String city,
                String province, String postalCode, String mobile) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.email = email;
        this.identity = identity;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.mobile = mobile;
    }

    public User(String firstName, String familyName, String email,
                String identity, String address, String city,
                String province, String postalCode, Role role, String mobile) {
        this(firstName, familyName, email, identity, address, city, province, postalCode, mobile);
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isBillable() {
        return hasContent(firstName)
                && hasContent(familyName)
                && hasContent(email)
                && hasContent(identity)
                && hasContent(address)
                && hasContent(city)
                && hasContent(province)
                && hasContent(postalCode);
    }

    private boolean hasContent(String value) {
        return value != null && !value.isBlank();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}