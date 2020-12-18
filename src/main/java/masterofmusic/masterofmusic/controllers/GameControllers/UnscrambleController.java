package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.Song;
import masterofmusic.masterofmusic.repositories.GameRepository;
import masterofmusic.masterofmusic.repositories.SongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.concurrent.ThreadLocalRandom;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UnscrambleController {
    private final SongRepository songDao;
    private final GameRepository gameDao;

    public UnscrambleController(SongRepository songDao, GameRepository gameDao){
        this.songDao = songDao;
        this.gameDao = gameDao;
    }

    @GetMapping("/unscramble")
    public String unscrambleGame(Model model) {
        List<Song> songs = songDao.findAll();
        List<String> lyricsToScramble = new ArrayList<>();
        List<String> lyricsStart = new ArrayList<>();
        List<List<String>> scrambledLyricsList = new ArrayList<>();

        for (Song song : songs) {
            lyricsToScramble.add(song.getLyrics());
        }

        for (String lyric : lyricsToScramble) {
//            Pattern pattern = Pattern.compile("\\w+");
//            Matcher matcher = pattern.matcher(lyric);
//            List<String> singleWords = new ArrayList<>();
//            while(matcher.find()) {
//                singleWords.add(matcher.group());
//            }
            String str[] = lyric.split(" ");
            List<String> singleWords;
            singleWords = Arrays.asList(str);

            int lyricLength = singleWords.size();
            System.out.println(lyricLength);
            for (String wordshow : singleWords) {
                System.out.println(wordshow + " " + singleWords.indexOf(wordshow));
            }

            List<String> scrambledLyric = new ArrayList<>();
            String lyricStart = "";
            String lyricEnd = "";
            int wordCount = (int) (Math.random()*((lyricLength-1)+1) +1);
            int switchChoice = (int) (Math.random()*((4-1)+1) +1);

            switch (1) {
                case 1:
                    List<Integer> indexesChosen = new ArrayList<>();
                    do {
                        int indexToAdd = ThreadLocalRandom.current().nextInt(0, lyricLength);
                        if (!indexesChosen.contains(indexToAdd)) {
                            indexesChosen.add(indexToAdd);
                            scrambledLyric.add(singleWords.get(indexToAdd));
                        }
                    } while (scrambledLyric.size() < singleWords.size());

//                    THIS IS FOR SHOWING THE START OF THE LYRIC UNSCRAMBLED AND THE END SCRAMBLED
//                    for (var i = 0; i < wordCount; i++) {
//                        lyricStart += singleWords.get(i);
//                        lyricStartToCombine.add(singleWords.get(i));
//                    }
//                    for (var i = wordCount; i < lyricLength; i++) {
//                        lyricEnd += singleWords.get(i);
//                        lyricStartToCombine.add(singleWords.get(i));
//                    }
                    break;
                default:
                    break;
            }
            scrambledLyricsList.add(scrambledLyric);
        }
        model.addAttribute("scrambledLyricsSet", scrambledLyricsList);
        return "unscramble";
    }
}
