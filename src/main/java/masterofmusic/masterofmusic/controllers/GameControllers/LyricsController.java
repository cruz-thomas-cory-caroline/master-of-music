package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.LyricAnswer;
import masterofmusic.masterofmusic.models.Question;
import masterofmusic.masterofmusic.models.Song;
import masterofmusic.masterofmusic.repositories.AnswerRepository;
import masterofmusic.masterofmusic.repositories.LyricAnswerRepository;
import masterofmusic.masterofmusic.repositories.SongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LyricsController {
    private final SongRepository songDao;
    private final LyricAnswerRepository answerDao;

    public LyricsController(SongRepository songDao, LyricAnswerRepository answerDao) {
        this.songDao = songDao;
        this.answerDao = answerDao;
    }

    @GetMapping("/finish-lyrics")
    public String lyricsQuiz(Model model) {
        List<String> lyricQuestions = new ArrayList<>();
        List<Long> chosenSongs = new ArrayList<>();

        chosenSongs.add((long) -1);

        for (Song song : songDao.findAll()) {
            Song songOne = songDao.getOne(song.getId());
            if (!chosenSongs.contains(songOne.getId())) {
                chosenSongs.add(songOne.getId());

                String lyricToManipulate = songOne.getLyrics();

                String cutQuestion = (lyricToManipulate.substring(0, lyricToManipulate.lastIndexOf(" ")));

                lyricQuestions.add(cutQuestion);
            }
            for (long i : chosenSongs) {
                System.out.println(i);
            }
        }
        model.addAttribute("questions", lyricQuestions);
        return "finish-lyrics";
    }
}


//public String lyricsQuiz(Model model) {
//        model.addAttribute("songs", songDao.findAll());
//        int count = songDao.findAll().size();
//        List<List<String>> lyricAnswers = new ArrayList<>();
//        List<Long> chosenSongs = new ArrayList<>();
//        List<String> lyricQuestions = new ArrayList<>();
//        chosenSongs.add((long) -1);
//
//        for (Song song : songDao.findAll()) {
//        Song songOne = songDao.getOne(((long) (Math.random() * (count - 1 + 1) + 1)));
//        if (!chosenSongs.contains(songOne.getId())) {
//        chosenSongs.add(songOne.getId());
//
//        String lyricToManipulate = songOne.getLyrics();
//
//        String cutQuestion = (lyricToManipulate.substring(0, lyricToManipulate.lastIndexOf(" ")));
//
//        lyricQuestions.add(cutQuestion);
//        }
//        for (long i : chosenSongs) {
//        System.out.println(i);
//        }
//        }
//        model.addAttribute("questions", lyricQuestions);
//
//            for (Song song : songDao.findAll()) {
//                chosenSongs = new ArrayList<>();
//                int counter = 3;//add one for correct answer
//                List<String> lyricsSet = new ArrayList<>();
//                while (counter > 0) {
//                    Song songOne = songDao.getOne(((long) (Math.random() * (count - 1 + 1) + 1)));
//                    System.out.println(songOne.getId());
//                    if (!chosenSongs.contains(songOne.getId())) {
//                        chosenSongs.add(songOne.getId());
//
//                        String lyricToManipulate = songOne.getLyrics();
//                        int index = lyricToManipulate.lastIndexOf(" ");
//
//                        String cutLyric = (lyricToManipulate.substring(index + 1));
//
//                        lyricsSet.add(cutLyric);
//                        System.out.println("hello");
//                        counter = counter - 1;
//                    }
//                }
//                lyricAnswers.add(lyricsSet);
//            }
//            model.addAttribute("answers", lyricAnswers);


