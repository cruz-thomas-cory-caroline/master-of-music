package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.PlayerGameRound;
import masterofmusic.masterofmusic.models.Song;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.GameRepository;
import masterofmusic.masterofmusic.repositories.PlayerGameRepository;
import masterofmusic.masterofmusic.repositories.SongRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private final PlayerGameRepository playerGameDao;
    private List<Long> chosenSongIDs = new ArrayList<>();
    private List<Song> chosenSongs = new ArrayList<>();

    public UnscrambleController(SongRepository songDao, GameRepository gameDao, PlayerGameRepository playerGameDao) {
        this.songDao = songDao;
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;

    }


    @GetMapping("/unscramble")
    public String unscrambleGame(@RequestParam(name = "inlineRadioOptions") String difficulty,
                                 @RequestParam(name = "inlineRadioOptions1") String genre,
                                 Model model) {

        PlayerGame gameStart = new PlayerGame();
        gameStart.setGame(gameDao.getOne(4L));
        gameStart.setScore(0);
        gameStart.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        int numberOfQuestions = 0;
        int timeLimit = 0;

        switch (difficulty) {
            case "easy":
                numberOfQuestions = 5;
                timeLimit = 45;
                break;
            case "medium":
                numberOfQuestions = 10;
                timeLimit = 30;
                break;
            case "hard":
                numberOfQuestions = 15;
                timeLimit = 15;
                break;
        }

        chosenSongIDs = new ArrayList<>();
        chosenSongs = new ArrayList<>();

        List<Song> allSongs = songDao.findAll();

        while (chosenSongs.size() < numberOfQuestions) {
            int indexToAdd = ThreadLocalRandom.current().nextInt(0, allSongs.size());
            Song randomSong = allSongs.get(indexToAdd);
            if (!chosenSongIDs.contains(randomSong.getId())) {
                chosenSongIDs.add(randomSong.getId());
                chosenSongs.add(randomSong);
            }
        }

        List<String> lyricsToScramble = new ArrayList<>();
        List<String> lyricsStart = new ArrayList<>();
        List<List<String>> scrambledLyricsList = new ArrayList<>();

        for (Song song : chosenSongs) {
            lyricsToScramble.add(song.getLyrics());
        }

        for (String lyric : lyricsToScramble) {

            List<String> singleWords = new ArrayList<>();
            String str[] = lyric.split(" ");
            singleWords = Arrays.asList(str);

            int lyricLength = singleWords.size();

            List<String> scrambledLyric = new ArrayList<>();
            String lyricStart = "";
            String lyricEnd = "";
            int wordCount = (int) (Math.random() * ((lyricLength - 1) + 1) + 1);
            int switchChoice = (int) (Math.random() * ((4 - 1) + 1) + 1);

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
        model.addAttribute("originalLyrics", lyricsToScramble);
        model.addAttribute("songs", chosenSongs);
        model.addAttribute("timeLimit", timeLimit);
        model.addAttribute("difficulty", difficulty);
        model.addAttribute("playerGame", gameStart.getId());
        return "unscramble";
    }


    @PostMapping("/unscramble/results")
    public String submitAnswers(@RequestParam(name = "difficulty") String difficulty,
                                @RequestParam(name = "playerGame") long num,
                                HttpServletRequest request,
                                Model model) {

        PlayerGameRound newRoundCompleted = new PlayerGameRound();
        newRoundCompleted.setDifficulty(difficulty);
        newRoundCompleted.setLevel(0);
        newRoundCompleted.setLevel(newRoundCompleted.getLevel()+1);
        newRoundCompleted.setPlayerGame(playerGameDao.getOne(num));

        List<String> userAnswers = new ArrayList<>();
        List<Integer> correctAnswers = new ArrayList<>();
        int countCorrect = 0;
        for (Song song : chosenSongs) {
            userAnswers.add(request.getParameter("song" + chosenSongs.indexOf(song)));
            System.out.println(request.getParameter("song" + chosenSongs.indexOf(song)));
            System.out.println(song.getLyrics());
            if (request.getParameter("song" + chosenSongs.indexOf(song)).equalsIgnoreCase(song.getLyrics())) {
                correctAnswers.add(chosenSongs.indexOf(song));
                newRoundCompleted.setScore(newRoundCompleted.getScore()+1);
                countCorrect++;
            }
        }
        model.addAttribute("score", newRoundCompleted.getScore());
        model.addAttribute("songs", chosenSongs);
        model.addAttribute("userAnswers", userAnswers);
        if (countCorrect >= chosenSongs.size()/2) {
            model.addAttribute("canAdvance", true);
        }
        return "final";
    }

}
