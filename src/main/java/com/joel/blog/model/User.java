package com.joel.blog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdGenerator")
    @SequenceGenerator(name = "userIdGenerator", sequenceName = "user_id_sequence", allocationSize = 1, initialValue = 101)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String userName;

    @Column(unique = true)
    private String userEmail;

    private String userPassword;
    private String userAbout;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "userId")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "roleId")
            }
    )
    private Set<Role> roles;

}
