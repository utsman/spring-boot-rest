package net.utsman.example.bookmarks.core.repository;

import net.utsman.example.bookmarks.core.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByAccountUsername(String username);
}
