package es.upm.miw.devops.resources.dtos;

import es.upm.miw.devops.infrastructure.data.models.User;

public class UserDTO {

    private Long id;
    private String firstName;
    private String familyName;
    private String email;
    private String identity;
    private String address;
    private String city;
    private String province;
    private String postalCode;
    private boolean billable;
    private boolean active;

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String familyName,
                   String email, String identity, String address,
                   String city, String province, String postalCode,
                   boolean billable, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.email = email;
        this.identity = identity;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.billable = billable;
        this.active = active;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.familyName = user.getFamilyName();
        this.email = user.getEmail();
        this.identity = user.getIdentity();
        this.address = user.getAddress();
        this.city = user.getCity();
        this.province = user.getProvince();
        this.postalCode = user.getPostalCode();
        this.billable = user.isBillable();
        this.active = user.isActive();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getEmail() {
        return email;
    }

    public String getIdentity() {
        return identity;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public boolean isBillable() {
        return billable;
    }

    public boolean isActive() {
        return active;
    }
}