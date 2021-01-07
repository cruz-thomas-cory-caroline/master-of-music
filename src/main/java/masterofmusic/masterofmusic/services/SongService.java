package masterofmusic.masterofmusic.services;

import masterofmusic.masterofmusic.models.Song;

public interface SongService {

    public Iterable<Song> findAll();

    public long findAnswerIdCorrect(long songId);

    public long findAnswerIdIncorrect(long songId);

    public Song findSongIdByGenre(long genreId);
}
