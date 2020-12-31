package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.LyricAnswer;
import masterofmusic.masterofmusic.models.Song;
import masterofmusic.masterofmusic.repositories.LyricAnswerRepository;
import masterofmusic.masterofmusic.repositories.PlayerGameRepository;
import masterofmusic.masterofmusic.repositories.SongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LyricsController {
    private final SongRepository songDao;
    private final LyricAnswerRepository lyricAnswerDao;
    private final PlayerGameRepository playerGameDao;

    public LyricsController(SongRepository songDao, LyricAnswerRepository lyricAnswerDao, PlayerGameRepository playerGameDao) {
        this.songDao = songDao;
        this.lyricAnswerDao = lyricAnswerDao;
        this.playerGameDao = playerGameDao;
    }

    @GetMapping("/lyric-master/{id}")
    public String lyricsQuiz(@PathVariable int id, Model model) {
        ArrayList<Song> songDaoAllByGameIdList = songDao.findAllByGameId(1L);
        long songId = songDaoAllByGameIdList.get(id).getId();

        model.addAttribute("songs", songDao.findAll());

        List<String> lyricQuestions = new ArrayList<>();
        for (Song song : songDao.findAllByGameId(1L)) {
            Song songOne = songDao.getOne(song.getId());

            String lyricToManipulate = songOne.getLyrics();

            String cutQuestion = (lyricToManipulate.substring(0, lyricToManipulate.lastIndexOf(" ")));

            lyricQuestions.add(cutQuestion);
        }

        model.addAttribute("answers", lyricAnswerDao.getAllBySongId(songId));
        System.out.println(lyricAnswerDao.getAllBySongId(songId));
        model.addAttribute("questions", lyricQuestions.get(id));
        return "lyric-master";
    }
}



//    @GetMapping("/lyric-master")
//    public String lyricsQuiz(Model model) {
//        model.addAttribute("songs", songDao.findAll());
//        ArrayList<LyricAnswer> lyricAnswers = new ArrayList<>();
//
//        ArrayList<Song> songIdArrayList = songDao.findAllByGameId(1L);
//        ArrayList<String> lyricQuestions = new ArrayList<>();
//
//        for (Song song : songIdArrayList) {
//            Song songOne = songDao.getOne(song.getId());
//
//            String lyricToManipulate = songOne.getLyrics();
//
//            String cutQuestion = (lyricToManipulate).substring(0, lyricToManipulate.lastIndexOf(" "));
//
//            lyricQuestions.add(cutQuestion);
//        }
//        model.addAttribute("answers", lyricAnswerDao.getAllBySongId(songIdArrayList.size()));
//        System.out.println(lyricAnswerDao.getAllBySongId(songIdArrayList.size()));
//
//        model.addAttribute("questions", lyricQuestions);
//        System.out.println(lyricQuestions);
//        return "lyric-master";
//    }
//}








//  int count = songDao.findAll().size();
//        List<String> lyricAnswers = new ArrayList<>();
//        List<Long> chosenSongs = new ArrayList<>()
//        for (Song song : songDao.findAll()) {
//            chosenSongs = new ArrayList<>();
//            int counter = 3;//add one for correct answer
//            List<String> lyricsSet = new ArrayList<>();
//            while (counter > 0) {
//                Song songOne = songDao.getOne(((long) (Math.random() * (count - 1 + 1) + 1)));
//                System.out.println(songOne.getId());
//                if (!chosenSongs.contains(songOne.getId())) {
//                    chosenSongs.add(songOne.getId());
//
//                    String lyricToManipulate = songOne.getLyrics();
//                    int index = lyricToManipulate.lastIndexOf(" ");
//
//                    String cutLyric = (lyricToManipulate.substring(index + 1));
//
//                    lyricsSet.add(cutLyric);
//                    System.out.println("hello");
//                    counter = counter - 1;
//                }
//            }
//            lyricAnswers.add(lyricsSet);
//        }
//        model.addAttribute("answers", lyricAnswers);
//        return "finish-lyrics";
//    }
//}


