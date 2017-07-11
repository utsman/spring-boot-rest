package net.utsman.example.bookmarks.hateoas.resource;

import lombok.Getter;
import net.utsman.example.bookmarks.core.model.Bookmark;
import net.utsman.example.bookmarks.hateoas.controller.BookmarkRestHateoasController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class BookmarkResource extends ResourceSupport {
    final Bookmark bookmark;

    public BookmarkResource(Bookmark bookmark) {
        this.bookmark = bookmark;

        String username = bookmark.getAccount().getUsername();
        this.add(new Link(bookmark.getUri(), "bookmark-uri"));
        this.add(linkTo(BookmarkRestHateoasController.class, username).withRel("bookmarks"));
        this.add(
                linkTo(
                        methodOn(BookmarkRestHateoasController.class, username)
                                .readBookmark(username, bookmark.getId()))
                        .withSelfRel()
        );
    }
}
