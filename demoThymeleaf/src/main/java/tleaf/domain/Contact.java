package tleaf.domain;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "CONTACTS")
public class Contact {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FIRSTNAME")
    private String firstName;

//    @NotNull
//    @Size(min = 2, max = 30, message = "{lastName.size}")
    @Column(name = "LASTNAME")
    private String lastName;

//    @NotNull
//    @Size(min = 5, max = 30, message = "{telephone.size}")

    @Column(name = "TELEPHONE")
    private String telephone;

//    @NotNull
//    @Email(message = "Please enter a valid email address")
//TODO Email Hibernate validator - how to get results? payload=Lalala.class ? how to catch errors with @Email
    @Column(name = "EMAIL")
    private String email;

//TODO constructors??


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
