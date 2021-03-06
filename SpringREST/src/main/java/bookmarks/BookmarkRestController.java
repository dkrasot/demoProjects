package bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;


//manual test POST requests in Chrome plugin (Advanced REST client)
// {link}  http://localhost:8080/jlong/bookmarks
// {headersTab} Content-type: application/json; charset=utf-8
// {payload} {"uri":"http://bookmark.com/2/TEST001","description":"TEST002"}

@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkRestController {

    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public BookmarkRestController(BookmarkRepository bookmarkRepository, AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input){
        this.validateUser(userId);
        return this.accountRepository.findByUsername(userId)
                .map( account -> {
                    Bookmark result = bookmarkRepository.save(
                            new Bookmark(account, input.uri, input.description));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(
                            ServletUriComponentsBuilder
                                    .fromCurrentRequest().path("/{id}")
                                    .buildAndExpand(result.getId()).toUri());
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();
    }

    @RequestMapping(value = "/{bookmarkId}", method = RequestMethod.GET)
    Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId){
        this.validateUser(userId);
        return this.bookmarkRepository.findOne(bookmarkId);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Bookmark> readBookmarks(@PathVariable String userId){
        this.validateUser(userId);
        return this.bookmarkRepository.findByAccountUsername(userId);
    }


    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(
                ()-> new UserNotFoundException(userId));
    }
}
