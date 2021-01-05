package masterofmusic.masterofmusic.controllers.GameControllers;


import masterofmusic.masterofmusic.models.*;
import masterofmusic.masterofmusic.repositories.*;
import masterofmusic.masterofmusic.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class LyricController {

    @Autowired
    private SongService songService;
    private long currentGameID;

    private final SongRepository songDao;
    private final LyricAnswerRepository lyricAnswerDao;
    private final GameRepository gameDao;
    private final PlayerGameRepository playerGameDao;
    private final PlayerGameRoundRepository playerGameRoundDao;
    private final GenreRepository genreDao;

    public LyricController(GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDoa, GenreRepository genreDao, LyricAnswerRepository lyricAnswerDao, SongRepository songDao) {
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDoa;
        this.genreDao = genreDao;
        this.lyricAnswerDao = lyricAnswerDao;
        this.songDao = songDao;
    }

    String songDifficulty;
    String songGenre;
    ArrayList<Song> chosenSongs = new ArrayList<>();

    @PostMapping("/lyric-master/")
    public String lyricMasterIndex(
            @RequestParam(name = "songDifficulty") String difficultySelection,
            @RequestParam(name = "songGenre") String genreSelection,
            @RequestParam(name = "round") long round
    ) {

        songDifficulty = difficultySelection;
        songGenre = genreSelection;

        Date date = new Date();
        PlayerGame gameStart = new PlayerGame();
        if (round == 1) {
            chosenSongs = new ArrayList<>();
            gameStart.setGame(gameDao.getOne(1L));
            gameStart.setScore(0);
            gameStart.setTimeElapsed(new Timestamp(date.getTime()));
            gameStart.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            PlayerGame dbGameStart = playerGameDao.save(gameStart);
            currentGameID = dbGameStart.getId();
        }

        return "redirect:/lyric-master";
    }

    @GetMapping("lyric-master")
    public String index(
            ModelMap modelMap) {

        Random rand = new Random();

        int questions = 0;
        int timeLimit = 0;
        switch (songDifficulty) {
            case "easy":
                timeLimit = 45;
                questions = 5;
                break;
            case "medium":
                timeLimit = 30;
                questions = 6;
                break;
            case "hard":
                timeLimit = 15;
                questions = 9;
                break;
        }

        Genre genreID = genreDao.getOne(0L);
        switch (songGenre) {
            case ("Rock"):
                genreID = genreDao.getOne(1L);
                break;
            case ("Hip-Hop"):
                genreID = genreDao.getOne(2L);
                break;
            case ("Pop"):
                genreID = genreDao.getOne(3L);
                break;
            case ("Country"):
                genreID = genreDao.getOne(4L);
                break;
        }

        Iterable<Song> songs = songService.findAll();
        ArrayList<Song> songsByGenre = new ArrayList<>();

        for (Song song : songs) {
            if (song.getSong_genres().contains(genreID)) {
                songsByGenre.add(song);
            }
        }

        ArrayList<Song> songsToPass = new ArrayList<>();

        for (var i = 0; i < questions; i++) {
            Song randSong = songsByGenre.get(rand.nextInt(songsByGenre.size()));
            songsToPass.add(randSong);
            songsByGenre.remove(randSong);

        }

        modelMap.addAttribute("songDifficulty", songDifficulty);
        modelMap.addAttribute("chosenSongs", songsToPass);
        modelMap.addAttribute("timeLimit", timeLimit);
        modelMap.addAttribute("playerGame", currentGameID);

        return "lyric-master/form";
    }

    @PostMapping("lyric-master/submit")
    public String submit(
            HttpServletRequest request) {

        PlayerGameRound newRoundCompleted = new PlayerGameRound();
        newRoundCompleted.setDifficulty(songDifficulty);
        newRoundCompleted.setLevel(playerGameDao.getOne(Long.parseLong(request.getParameter("playerGame"))).getPlayerGameRounds().size() + 1);
        newRoundCompleted.setPlayerGame(playerGameDao.getOne(Long.parseLong(request.getParameter("playerGame"))));
        newRoundCompleted.setScore(0);
        newRoundCompleted.setPlay_time(String.valueOf(new Timestamp(0)));

        ArrayList<String> incorrectUserAnswers = new ArrayList<>();
        ArrayList<Song> correctSongs = new ArrayList<>();
        ArrayList<Song> incorrectSongs = new ArrayList<>();
        ArrayList<Song> testedSongs = new ArrayList<>();

        String[] songIds = request.getParameterValues("songId");

        for (String songId : songIds) {
            testedSongs.add(songDao.getOne(Long.parseLong(songId)));
        }

        for (Song song : testedSongs) {
            long answerIdCorrect = songService.findAnswerIdCorrect(song.getId());
            if (answerIdCorrect == Long.parseLong(request.getParameter("song_" + song.getId()))) {
                newRoundCompleted.setScore(newRoundCompleted.getScore() + 100);
                correctSongs.add(song);

            } else if (answerIdCorrect != Long.parseLong(request.getParameter("song_" + song.getId()))) {
                incorrectUserAnswers.add(lyricAnswerDao.getOne(Long.parseLong(request.getParameter("song_" + song.getId()))).getLyricAnswer());
                incorrectSongs.add(song);
            }
        }


        playerGameDao.getOne(newRoundCompleted.getPlayerGame().getId()).setScore(newRoundCompleted.getPlayerGame().getScore() + newRoundCompleted.getScore());
        playerGameRoundDao.save(newRoundCompleted);

        request.setAttribute("correctSongs", correctSongs);
        request.setAttribute("score", newRoundCompleted.getScore());
        request.setAttribute("incorrectUserAnswers", incorrectUserAnswers);
        request.setAttribute("incorrectSongs", incorrectSongs);
        request.setAttribute("currentLevel", newRoundCompleted.getLevel());
        request.setAttribute("round", playerGameDao.getOne(Long.parseLong(request.getParameter("playerGame"))).getPlayerGameRounds().size() + 1);

        return "lyric-master/result";
    }
}
