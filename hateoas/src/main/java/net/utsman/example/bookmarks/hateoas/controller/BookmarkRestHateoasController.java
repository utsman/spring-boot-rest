package net.utsman.example.bookmarks.hateoas.controller;

import net.utsman.example.bookmarks.core.exception.UserNotFoundException;
import net.utsman.example.bookmarks.core.model.Account;
import net.utsman.example.bookmarks.core.model.Bookmark;
import net.utsman.example.bookmarks.core.repository.AccountRepository;
import net.utsman.example.bookmarks.core.repository.BookmarkRepository;
import net.utsman.example.bookmarks.hateoas.resource.BookmarkResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/{userId}/bookmarks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BookmarkRestHateoasController {
    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    BookmarkRestHateoasController(BookmarkRepository bookmarkRepository,
                           AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Resources<BookmarkResource> readBookmarks(@PathVariable String userId){
        this.validateUser(userId);
        return new Resources<>(this.bookmarkRepository.findByAccountUsername(userId)
                .stream()
                .map(BookmarkResource::new)
                .collect(Collectors.toList())
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
        this.validateUser(userId);
        return this.accountRepository.findByUsername(userId)
                .map(account -> {
                    Bookmark bookmark = bookmarkRepository.save(createBookmark(input, account));
                    Link self = new BookmarkResource(bookmark).getLink("self");
                    return ResponseEntity.created(URI.create(self.getHref())).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    private URI getLocationById(Bookmark bookmark) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(bookmark.getId()).toUri();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    public BookmarkResource readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
        this.validateUser(userId);
        return new BookmarkResource(this.bookmarkRepository.findOne(bookmarkId));
    }

    private Bookmark createBookmark(Bookmark bookmark, Account account) {
        return new Bookmark(account, bookmark.getUri(), bookmark.getDescription());
    }

    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
    }
}
