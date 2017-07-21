package net.utsman.example.bookmarks.core.config;

import net.utsman.example.bookmarks.core.model.Account;
import net.utsman.example.bookmarks.core.model.Bookmark;
import net.utsman.example.bookmarks.core.repository.AccountRepository;
import net.utsman.example.bookmarks.core.repository.BookmarkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractDummyDataCommandLineRunner {
    @Bean
    CommandLineRunner init(AccountRepository accountRepository, BookmarkRepository bookmarkRepository) {
        return saveDatas(accountRepository, bookmarkRepository);
    }

    protected CommandLineRunner saveDatas(AccountRepository accountRepository, BookmarkRepository bookmarkRepository) {
        List<String> names = Arrays.asList("jhoeller", "dsyer", "pwebb", "ogierke", "rwinch", "mfisher", "mpollack", "jlong");
        return args -> names.forEach(name -> {
            Account account = accountRepository.save(new Account(name, "password"));
            bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + name, "A description"));
            bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + name, "B description"));
        });
    }
}
