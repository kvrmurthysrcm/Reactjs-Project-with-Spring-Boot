package com.reactjs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data // TODO:: remove and test...
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idusers;

    // @Column(unique = true)
    private String userId;

    private String password;

    private String firstName;
    private String lastName;

    private Boolean active;
    private String userscol;


    public User(String userId, String encode, String firstName) {
        this.userId = userId;
        this.firstName = firstName;
        this.password = encode; // saving encoded password here..
    }
} //End of User Entity