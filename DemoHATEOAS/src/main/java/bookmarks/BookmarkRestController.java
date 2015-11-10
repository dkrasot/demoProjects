package bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
                    Bookmark bookmark = bookmarkRepository.save(
                            new Bookmark(account, input.uri, input.description));
                    HttpHeaders httpHeaders = new HttpHeaders();

                    Link forOneBookmark = new BookmarkResource(bookmark).getLink("self");
                    httpHeaders.setLocation(URI.create(forOneBookmark.getHref()));
//                    httpHeaders.setLocation(
//                            ServletUriComponentsBuilder
//                                    .fromCurrentRequest().path("/{id}")
//                                    .buildAndExpand(bookmark.getId()).toUri());
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();
    }

    @RequestMapping(value = "/{bookmarkId}", method = RequestMethod.GET)
    BookmarkResource readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId){
        this.validateUser(userId);
        //return new this.bookmarkRepository.findOne(bookmarkId); --> Bookmark
        return new BookmarkResource(this.bookmarkRepository.findOne(bookmarkId));
    }

    @RequestMapping(method = RequestMethod.GET)
    Resources<BookmarkResource> readBookmarks(@PathVariable String userId){
        this.validateUser(userId);
        List<BookmarkResource> bookmarkResourceList = bookmarkRepository.findByAccountUsername(userId)
                .stream().map(BookmarkResource::new).collect(Collectors.toList());
        //return this.bookmarkRepository.findByAccountUsername(userId); --> Collection<Bookmark>
        return new Resources<BookmarkResource>(bookmarkResourceList);
    }

    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(
                ()-> new UserNotFoundException(userId));
    }
}
