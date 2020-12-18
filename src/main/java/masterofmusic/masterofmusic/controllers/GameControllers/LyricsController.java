package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.Song;
import masterofmusic.masterofmusic.repositories.AnswerRepository;
import masterofmusic.masterofmusic.repositories.SongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LyricsController {
    private final SongRepository songDao;
    private final AnswerRepository answerDao;

    public LyricsController(SongRepository songDao, AnswerRepository answerDao) {
        this.songDao = songDao;
        this.answerDao = answerDao;
    }

    @GetMapping("/finish-lyrics")
    public String viewQuizFormat( Model model) {
        model.addAttribute("songs", songDao.findAll());
        int count = songDao.findAll().size();
        List<List<String>> lyricAnswers = new ArrayList<>();
        List<Long> chosenSongs = new ArrayList<>();
        List<List<String>> lyricQuestions = new ArrayList<>();
        chosenSongs.add((long) -1);


        for (Song song : songDao.findAll()) {
            int counter = 3;//add one for correct answer
            List<String> lyricsSet = new ArrayList<>();
            List<String> questionSet = new ArrayList<>();
            while (counter > 0) {
                Song songOne = songDao.getOne(((long) (Math.random() * (count - 1 + 1) + 1)));
                for (Long songId : chosenSongs) {
                    if (songId != songOne.getId()) {
                        chosenSongs.add(songOne.getId());

                        String lyricToManipulate = songOne.getLyrics();
                        int index = lyricToManipulate.lastIndexOf(" ");
                        System.out.println(index);

                        String cutLyric = (lyricToManipulate.substring(index + 1));
                        System.out.println(cutLyric);

                        String cutQuestion = (lyricToManipulate.substring(0, lyricToManipulate.lastIndexOf(" ")));
                        System.out.println(cutQuestion);

                        questionSet.add(cutQuestion);
                        lyricsSet.add(cutLyric);//passed into new variable string to user
                        break;
                    }
                }
                counter = counter - 1;
            }
            lyricAnswers.add(lyricsSet);
            lyricQuestions.add(questionSet);
        }

//        for(List<String> list : lyricQuestions){
//            for (String stringQuestion : list){
//                System.out.println(stringQuestion);
//            }
//        }
        model.addAttribute("questions", lyricQuestions);

        for(List<String> list : lyricAnswers){
            System.out.println(list);
            for(String stringLyric : list){
                System.out.println(stringLyric);
            }
        }

        model.addAttribute("answers", lyricAnswers);
        System.out.println(lyricAnswers);
        return "finish-lyrics";
    }
}
