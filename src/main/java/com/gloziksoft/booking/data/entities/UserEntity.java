package com.gloziksoft.booking.data.entities;

import com.gloziksoft.booking.data.enums.Role;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users") // Maps this entity to the "users" table in the database
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the primary key
    private Long id;

    @Column(unique = true, nullable = false) // Email must be unique and not null
    private String email;

    @Column(nullable = false) // Password must not be null
    private String password;

    @Column(name = "first_name", nullable = false) // Maps to "first_name" column, not null
    private String firstName;

    @Column(name = "last_name", nullable = false) // Maps to "last_name" column, not null
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * Returns the full name of the user by concatenating first and last names.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
