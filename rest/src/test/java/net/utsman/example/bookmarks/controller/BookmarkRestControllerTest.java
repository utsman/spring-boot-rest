package net.utsman.example.bookmarks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.utsman.example.bookmarks.TestApplication;
import net.utsman.example.bookmarks.model.Account;
import net.utsman.example.bookmarks.model.Bookmark;
import net.utsman.example.bookmarks.repository.AccountRepository;
import net.utsman.example.bookmarks.repository.BookmarkRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@AutoConfigureMockMvc
public class BookmarkRestControllerTest {
    private MediaType contentType = MediaType.APPLICATION_JSON_UTF8;
    private String userName = "bdussault";

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MockMvc mockMvc;

    private Account account;
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Before
    public void setup() {
        this.account = accountRepository.save(new Account(userName, "password"));
        addBookMark("http://bookmark.com/1/" + userName);
        addBookMark("http://bookmark.com/2/" + userName);
    }

    @After
    public void clean () {
        accountRepository.deleteAll();
    }

    private boolean addBookMark(String uri) {
        return this.bookmarkList.add(
                bookmarkRepository.save(new Bookmark(account, uri, "A description"))
        );
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc
                .perform(
                    post("/utsman/bookmarks")
                        .content(json(new Bookmark()))
                        .contentType(contentType)
                )
                .andExpect(status().isNotFound());

    }

    @Test
    public void readSingleBookmark() throws Exception {
        int bookmarkId = bookmarkList.get(0).getId().intValue();
        mockMvc
                .perform(get("/" + userName + "/bookmarks/" + bookmarkId))
                .andExpect( status().isOk() )
                .andExpect( content().contentType(contentType) )
                .andExpect( jsonPath("$.id", is(bookmarkId)) )
                .andExpect( jsonPath("$.uri", is("http://bookmark.com/1/" + userName)) )
                .andExpect( jsonPath("$.description", is("A description")) )
        ;
    }

    @Test
    public void readBookmarks() throws Exception {
        mockMvc.perform(get("/" + userName + "/bookmarks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(this.bookmarkList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].uri", is("http://bookmark.com/1/" + userName)))
                .andExpect(jsonPath("$[0].description", is("A description")))
                .andExpect(jsonPath("$[1].id", is(this.bookmarkList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].uri", is("http://bookmark.com/2/" + userName)))
                .andExpect(jsonPath("$[1].description", is("A description")));
    }

    @Test
    public void createBookmark() throws Exception {
        String bookmarkJson = json(new Bookmark(
                this.account, "http://spring.io", "a bookmark to the best resource for Spring news and information"));

        this.mockMvc
                .perform(
                    post("/" + userName + "/bookmarks")
                        .contentType(contentType)
                        .content(bookmarkJson)
                )
                .andExpect(status().isCreated());
    }

    private String json(Bookmark bookmark) throws IOException {
        return new ObjectMapper().writeValueAsString(bookmark);
    }
}










