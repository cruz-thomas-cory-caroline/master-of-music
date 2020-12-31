package masterofmusic.masterofmusic.services;

import masterofmusic.masterofmusic.models.LyricAnswer;
import masterofmusic.masterofmusic.models.Song;
import masterofmusic.masterofmusic.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("songService")
public class SongServiceImpl implements SongService{

    @Autowired
    private SongRepository songRepository;

    @Override
    public Iterable<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    public long findAnswerIdCorrect(long songId) {
       Song song = songRepository.findById(songId).get();
       for(LyricAnswer lyricAnswer : song.getLyricAnswers()) {
           if(lyricAnswer.isCorrect()) {
               return lyricAnswer.getId();
           }
       }
       return -1;
    }
}
