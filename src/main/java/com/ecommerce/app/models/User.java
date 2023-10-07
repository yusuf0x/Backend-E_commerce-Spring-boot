package com.ecommerce.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String token;
    private boolean status = true ;
    private boolean verified_email;
    private LocalDateTime created_at;
    @OneToOne
    @JoinColumn(name = "person_id",referencedColumnName = "id")

    private Person person;

    private Date passwordChangedAt;

    private String passwordResetCode;

    private Date passwordResetExpires;

    private boolean passwordResetVerified;

//    @Enumerated(EnumType.STRING)
//    private UserRole role = UserRole.USER;

    private boolean active = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses;


    public void setPerson(Person person) {
        this.person = person;
        person.setUser(this);
    }
}
