package net.utsman.hateoas.bookmarks.resource;

import lombok.Getter;
import net.utsman.example.bookmarks.model.Bookmark;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Getter
public class BookmarkResource extends ResourceSupport {
    final Bookmark bookmark;

    public BookmarkResource(Bookmark bookmark) {
        this.bookmark = bookmark;

        String username = bookmark.getAccount().getUsername();
        this.add(new Link(bookmark.getUri(), "bookmark-uri"));
    }
}
