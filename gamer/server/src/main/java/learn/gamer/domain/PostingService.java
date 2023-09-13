package learn.gamer.domain;

import learn.gamer.data.DataAccessException;
import learn.gamer.data.PostingRepository;
import learn.gamer.models.Posting;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostingService {
    //bringing in the repository
    private final PostingRepository repository;
    //constructor
    public PostingService(PostingRepository repository) {
        this.repository = repository;
    }
    //keeping track of current date always
//    public static LocalDate getMaxDate() {
//        return LocalDate.now();
//    }


    //CRUD METHODS
    //READ
    public List<Posting> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public Posting findById(int postingId) throws DataAccessException {
        return repository.findById(postingId);
    }

    public List<Posting> findByGamerTag(String gamerTag) throws DataAccessException {
        return repository.findByGamerTag(gamerTag);
    }

    public List<Posting> findByGameTitle(String gameTitle) throws DataAccessException {
        return repository.findByGameTitle(gameTitle);
    }

    public List<Posting> findByGameId(int gameId) throws DataAccessException {
        return repository.findByGameId(gameId);
    }

    public List<Posting> findByDate(LocalDate datPosted) throws DataAccessException {
        return repository.findByDate(datPosted);
    }

    //CREATE
    public Result<Posting> create(Posting posting) throws DataAccessException {
        Result<Posting> result = validate(posting);
        if (!result.isSuccess()) {
            return result;
        }

        if (posting != null && posting.getPostingId() > 0) {
            result.addMessage("Post `ID` should not be set.", ResultType.INVALID);
            return result;
        }

        posting = repository.create(posting);
        result.setPayload(posting);
        return result;
    }


    //UPDATE
    public Result<Posting> update(Posting posting) throws DataAccessException {
        Result<Posting> result = validate(posting);
        if (!result.isSuccess()) {
            return result;
        }

        if (posting == null && posting.getPostingId() <= 0) {
            result.addMessage("Post `ID` must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(posting)) {
            String msg = String.format("Post ID: %s, not found", posting.getPostingId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }


    //DELETE
    public boolean deleteById(int postingId) throws DataAccessException {
        return repository.deleteById(postingId);
    }



    //VALIDATION
    private Result<Posting> validate(Posting posting) throws DataAccessException {
        Result<Posting> result = new Result<>();
        if (posting == null) {
            result.addMessage("Post cannot be null", ResultType.INVALID);
            return result;
        }

        if (posting.getHeader() == null || posting.getHeader().isBlank()) {
            result.addMessage("Header is required", ResultType.INVALID);
        }

        if (posting.getDescription() == null || posting.getDescription().isBlank()) {
            result.addMessage("Description is required", ResultType.INVALID);
        }


        //checking for duplicate posts
        if (result.isSuccess()) {
            List<Posting> posts = repository.findAll();
            if (posts.size() > 0) {
                if (posts.stream().anyMatch(p -> p.getHeader().equals(posting.getHeader()) && p.getDescription().equals(posting.getDescription()))) {
                    result.addMessage("This post has already been made", ResultType.INVALID);
                }
            }
//            for (Posting post: posts) {
//                if (posting.getHeader().equalsIgnoreCase(post.getHeader()) && posting.getDescription().equalsIgnoreCase(post.getDescription())) {
//                    result.addMessage("This post has already been made", ResultType.INVALID);
//                }
//            }
        }

        return result;
    }

}
