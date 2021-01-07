package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.PlayerGameRound;
import masterofmusic.masterofmusic.models.Song;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UnscrambleController {
    private final SongRepository songDao;
    private final GameRepository gameDao;
    private final PlayerGameRepository playerGameDao;
    private final PlayerGameRoundRepository playerGameRoundDoa;
    private final GenreRepository genreDao;
    private List<Long> chosenSongIDs = new ArrayList<>();
    private List<Song> chosenSongs = new ArrayList<>();
    private long currentGameID;

    public UnscrambleController(SongRepository songDao, GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDoa, GenreRepository genreDao) {
        this.songDao = songDao;
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDoa = playerGameRoundDoa;
        this.genreDao = genreDao;
    }


    @GetMapping("/unscramble")
    public String unscrambleGame(@RequestParam(name = "difficulty") String difficulty,
                                 @RequestParam(name = "genre") String genre,
                                 @RequestParam(name = "round") long num,
                                 Model model) {

        chosenSongs = new ArrayList<>();

        if(num == 1) {
            PlayerGame gameStart = new PlayerGame();
            gameStart.setGame(gameDao.getOne(4L));
            gameStart.setScore(0);
            Date date = new Date();
            gameStart.setTimeElapsed(new Timestamp(date.getTime()));
            gameStart.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            PlayerGame dbGameStart = playerGameDao.save(gameStart);
            currentGameID = dbGameStart.getId();
            chosenSongIDs = new ArrayList<>();
        }

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

        long genreID = 0;

        switch (genre) {
            case "rock":
                genreID = 1;
                break;
            case "pop":
                genreID = 2;
                break;
            case "hip-hop":
                genreID = 3;
                break;
            case "country":
                genreID = 4;
                break;
        }

        List<Song> allSongsOfGenre = genreDao.getOne(genreID).getSongs();
        System.out.println(allSongsOfGenre.size());
        System.out.println(genreID);
        System.out.println(timeLimit);

        while (chosenSongs.size() < numberOfQuestions) {
            int indexToAdd = ThreadLocalRandom.current().nextInt(0, allSongsOfGenre.size());
            Song randomSong = allSongsOfGenre.get(indexToAdd);
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
        model.addAttribute("genre", genre);
        model.addAttribute("playerGame", currentGameID);
        model.addAttribute("round", num);
        return "unscramble";
    }


    @PostMapping("/unscramble/results")
    public String submitAnswers(@RequestParam(name = "difficulty") String difficulty,
                                @RequestParam(name = "playerGame") long num,
                                HttpServletRequest request,
                                Model model) {

        PlayerGameRound newRoundCompleted = new PlayerGameRound();
        newRoundCompleted.setDifficulty(difficulty);
        newRoundCompleted.setLevel(playerGameDao.getOne(num).getPlayerGameRounds().size()+1);
        newRoundCompleted.setPlayerGame(playerGameDao.getOne(num));
        newRoundCompleted.setPlay_time("here");
        newRoundCompleted.setScore(0);

        List<String> userAnswers = new ArrayList<>();
        List<Integer> correctAnswers = new ArrayList<>();
        List<Integer> showGreen = new ArrayList<>();
        int countCorrect = 0;
        for (Song song : chosenSongs) {
            userAnswers.add(request.getParameter("song" + chosenSongs.indexOf(song)));
            System.out.println(request.getParameter("song" + chosenSongs.indexOf(song)));
            System.out.println(song.getLyrics());
            if (request.getParameter("song" + chosenSongs.indexOf(song)).equalsIgnoreCase(song.getLyrics())) {
                showGreen.add(1);
                correctAnswers.add(chosenSongs.indexOf(song));
                newRoundCompleted.setScore(newRoundCompleted.getScore()+100);
                countCorrect++;
            } else {
                showGreen.add(0);
            }
        }
        playerGameDao.getOne(newRoundCompleted.getPlayerGame().getId()).setScore(newRoundCompleted.getPlayerGame().getScore()+newRoundCompleted.getScore());
        playerGameRoundDoa.save(newRoundCompleted);
        model.addAttribute("score", newRoundCompleted.getScore());
        model.addAttribute("songs", chosenSongs);
        model.addAttribute("userAnswers", userAnswers);
        if (countCorrect >= chosenSongs.size()/2) {
            model.addAttribute("canAdvance", true);
        }
        model.addAttribute("currentLevel", newRoundCompleted.getLevel());
        model.addAttribute("showGreen", showGreen);
        return "final";
    }


}
