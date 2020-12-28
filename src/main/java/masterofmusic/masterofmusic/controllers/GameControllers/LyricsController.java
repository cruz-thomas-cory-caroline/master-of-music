package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.Answer;
import masterofmusic.masterofmusic.models.LyricAnswer;
import masterofmusic.masterofmusic.models.Question;
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

//    @GetMapping("/finish-lyrics/{id}")
//    public String lyricsQuiz(@PathVariable int id, Model model) {
//        ArrayList<Song> lyricQuestions = songDao.findAllByGameId(1L);
//        long songId = lyricQuestions.get(id).getId();
//        ArrayList<LyricAnswer> lyricAnswers = lyricAnswerDao.getAllBySongId(songId);
//
//        model.addAttribute("questions", lyricQuestions.get(id).getLyrics());
//
//        model.addAttribute("lyricAnswers", lyricAnswers);
//        return "finish-lyrics";
//    }
//}

    @GetMapping("/finish-lyrics/{id}")
    public String lyricsQuiz(@PathVariable int id, Model model) {
        ArrayList<Song> songDaoAllByGameIdList = songDao.findAllByGameId(1L);
        long songId = songDaoAllByGameIdList.get(id).getId();

        model.addAttribute("songs", songDao.findAll());
        int count = songDao.findAll().size();
        List<String> lyricAnswers = new ArrayList<>();
        List<Long> chosenSongs = new ArrayList<>();
        List<String> lyricQuestions = new ArrayList<>();

//        for (Song song : songDao.findAll())
            for (Song song : songDao.findAllByGameId(1L)){
            Song songOne = songDao.getOne(song.getId());

                String lyricToManipulate = songOne.getLyrics();

                String cutQuestion = (lyricToManipulate.substring(0, lyricToManipulate.lastIndexOf(" ")));

                lyricQuestions.add(cutQuestion);
        }



        model.addAttribute("answers", lyricAnswerDao.getAllBySongId(songId));

        model.addAttribute("questions", lyricQuestions.get(id));
        return "finish-lyrics";
    }

    @PostMapping("/finish-lyrics/{id}")
    public String submitAnswer(@RequestParam(name = "answers")String userAnswer, @PathVariable int id, Model model){
        ArrayList<Song> theoryList = songDao.findAllByGameId(1L);
        long songId = theoryList.get(id).getId();
        ArrayList<LyricAnswer> lyricAnswerArrayList = lyricAnswerDao.getAllBySongId(songId);
        String correctAnswer = "";
        for (LyricAnswer lyricAnswer: lyricAnswerArrayList){
            if(lyricAnswer.isCorrect()) {
                correctAnswer = lyricAnswer.getLyricAnswer();
            }
        }
        if(userAnswer.equalsIgnoreCase(correctAnswer)){
            model.addAttribute("correct", "Great Job!");

        }
        return "finish-lyrics";
    }

}

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


