package learn.gamer.data;

import learn.gamer.models.MatchSent;

import java.util.List;

public interface MatchSentRepository {
    MatchSent findByKey(int matchReceiverId, int matchSenderId);

    boolean add(MatchSent matchSent);

    boolean deleteByKey(int matchReceiverId, int matchSenderId);
}
