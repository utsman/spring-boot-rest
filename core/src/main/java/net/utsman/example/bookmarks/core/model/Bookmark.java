package net.utsman.example.bookmarks.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Getter
@Entity
public class Bookmark {
    @JsonIgnore
    @ManyToOne
    private Account account;
    @Id
    @GeneratedValue
    private Long id;
    private String uri;
    private String description;

    public Bookmark(Account account, String uri, String description) {
        this.account = account;
        this.uri = uri;
        this.description = description;
    }

}
