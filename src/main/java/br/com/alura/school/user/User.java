package br.com.alura.school.user;

import br.com.alura.school.registration.Registration;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Size(max=20)
    @NotBlank(message = "User name")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "E-mail")
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Deprecated
    public User() {}

    User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
