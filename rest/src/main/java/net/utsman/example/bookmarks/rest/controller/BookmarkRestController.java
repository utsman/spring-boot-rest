package net.utsman.example.bookmarks.rest.controller;

import net.utsman.example.bookmarks.core.exception.UserNotFoundException;
import net.utsman.example.bookmarks.core.model.Account;
import net.utsman.example.bookmarks.core.model.Bookmark;
import net.utsman.example.bookmarks.core.repository.AccountRepository;
import net.utsman.example.bookmarks.core.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkRestController {
    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    BookmarkRestController(BookmarkRepository bookmarkRepository,
                           AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Bookmark> readBookmarks(@PathVariable String userId){
        this.validateUser(userId);
        return this.bookmarkRepository.findByAccountUsername(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
        this.validateUser(userId);
        return this.accountRepository.findByUsername(userId)
                .map(account -> {
                    Bookmark bookmark = bookmarkRepository.save(createBookmark(input, account));
                    return ResponseEntity.created(getLocationById(bookmark)).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    private URI getLocationById(Bookmark bookmark) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(bookmark.getId()).toUri();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
        this.validateUser(userId);
        return this.bookmarkRepository.findOne(bookmarkId);
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
