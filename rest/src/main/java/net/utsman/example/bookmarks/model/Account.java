package net.utsman.example.bookmarks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
public class Account {
    @OneToMany(mappedBy = "account", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<Bookmark> bookmarks = new HashSet<>();
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    @JsonIgnore
    private String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
