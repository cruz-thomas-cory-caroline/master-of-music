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
//@RequestMapping("song")
public class LyricController {

    @Autowired
    private SongService songService;

    private final SongRepository songDao;
    private final LyricAnswerRepository lyricAnswerDao;
    private final GameRepository gameDao;
    private final PlayerGameRepository playerGameDao;
    private final PlayerGameRoundRepository playerGameRoundDao;
    private final GenreRepository genreDao;
    private List<Long> chosenSongIDs = new ArrayList<>();
    private List<Song> chosenSongs = new ArrayList<>();
    private long currentGameID;

    public LyricController(GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDoa, GenreRepository genreDao, LyricAnswerRepository lyricAnswerDao, SongRepository songDao) {
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDoa;
        this.genreDao = genreDao;
        this.lyricAnswerDao = lyricAnswerDao;
        this.songDao = songDao;
    }

    Long round;
    String songDifficulty;
    String songGenre;
//    PlayerGame currentPlayerGame = new PlayerGame();
//    int totalScore = 0;
//    Genre currentGenre = new Genre();
//    Timestamp gameTime = new Timestamp(0);
//    int gameLevel = 0;
//    String play_time = "";


    @PostMapping("/lyric-master/")
    public String lyricMasterIndex(
            @RequestParam(name = "songDifficulty") String difficultySelection,
            @RequestParam(name = "songGenre") String genreSelection,
            @RequestParam(name = "round") long num
            ) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        songDifficulty = difficultySelection;
        songGenre = genreSelection;
        round = num;

        if(num == 1) {
            PlayerGame gameStart = new PlayerGame();
            gameStart.setGame(gameDao.getOne(1L));
            gameStart.setScore(0);
            Date date = new Date();
            gameStart.setTimeElapsed(new Timestamp(date.getTime()));
            gameStart.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            PlayerGame dbGameStart = playerGameDao.save(gameStart);
            currentGameID = dbGameStart.getId();
            chosenSongIDs = new ArrayList<>();
        }

//        Game game = gameDao.getOne(1L);
//        currentPlayerGame.setUser(user);
//        currentPlayerGame.setGame(game);
//        currentPlayerGame.setScore(totalScore);
//        currentPlayerGame.setTimeElapsed(gameTime);
//        playerGameDao.save(currentPlayerGame);
        return "redirect:/lyric-master";
    }

    //    @RequestMapping(method = RequestMethod.GET)
    @GetMapping("lyric-master")
    public String index(
            ModelMap modelMap) {


        Random rand = new Random();


        int timeLimit = 0;

        switch (songDifficulty) {
            case "easy":
                timeLimit = 45;
                break;
            case "medium":
                timeLimit = 30;
                break;
            case "hard":
                timeLimit = 15;
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
        ArrayList<Song> chosenSongs = new ArrayList<>();
        for (var i = 0; i < 10; i++) {
            Song randRock = songsByGenre.get(rand.nextInt(songsByGenre.size()));
            chosenSongs.add(randRock);
            songsByGenre.remove(randRock);
        }


        modelMap.put("songs", songService.findAll());
        modelMap.addAttribute("songDifficulty", songDifficulty);
        modelMap.addAttribute("chosenSongs", chosenSongs);
        modelMap.addAttribute("timeLimit", timeLimit);

//    }
        return "lyric-master/form";
    }


    @PostMapping("lyric-master/submit")
    public String submit(

            HttpServletRequest request) {

        PlayerGameRound newRoundCompleted = new PlayerGameRound();
        newRoundCompleted.setDifficulty(songDifficulty);
        newRoundCompleted.setLevel(playerGameDao.getOne(Long.valueOf(request.getParameter("playerGame"))).getPlayerGameRounds().size()+1);
        newRoundCompleted.setPlayerGame(playerGameDao.getOne(Long.valueOf(request.getParameter("playerGame"))));
        newRoundCompleted.setPlay_time("here");
        newRoundCompleted.setScore(0);

        ArrayList<String> correctAnswers = new ArrayList<>();
        ArrayList<String> incorrectAnswers = new ArrayList<>();
        ArrayList<String> correctSongs = new ArrayList<>();
        ArrayList<String> incorrectSongs = new ArrayList<>();
        ArrayList<String> userAnswers = new ArrayList<>();

        int score = 0;
        String[] songIds = request.getParameterValues("songId");

        for (String songId : songIds) {
            long answerIdCorrect = songService.findAnswerIdCorrect(Long.parseLong(songId));
            long answerIncorrect = songService.findAnswerIdIncorrect(Long.parseLong(songId));
            if (answerIdCorrect == Long.parseLong(request.getParameter("song_" + songId))) {
                correctAnswers.add(lyricAnswerDao.getOne(answerIdCorrect).getLyricAnswer());
                correctSongs.add(songDao.getOne(Long.valueOf(songId)).getLyrics());
                newRoundCompleted.setScore(newRoundCompleted.getScore()+100);
//                score += 100;

            } else if (answerIdCorrect != Long.parseLong(request.getParameter("song_" + songId))) {
                incorrectAnswers.add(lyricAnswerDao.getOne(answerIdCorrect).getLyricAnswer());
                incorrectSongs.add(songDao.getOne(Long.parseLong(songId)).getLyrics());

//            } else if (answerIncorrect != Long.parseLong(request.getParameter("song_" + songId))) {
//                userAnswers.add(lyricAnswerDao.getOne(Long.parseLong(songId)).getLyricAnswer());
            }
            System.out.println(songId);
        }

        playerGameDao.getOne(newRoundCompleted.getPlayerGame().getId()).setScore(newRoundCompleted.getPlayerGame().getScore()+newRoundCompleted.getScore());
        playerGameRoundDao.save(newRoundCompleted);

        System.out.println(userAnswers);
        request.setAttribute("score", newRoundCompleted.getScore());
//        request.setAttribute("score", score);
        request.setAttribute("correctAnswers", correctAnswers);
        request.setAttribute("correctSongs", correctSongs);
        request.setAttribute("incorrectAnswers", incorrectAnswers);
        request.setAttribute("incorrectSongs", incorrectSongs);
        request.setAttribute("userAnswer", userAnswers);
        request.setAttribute("currentLevel", newRoundCompleted.getLevel());
//        request.setAttribute("user", user);


        return "lyric-master/result";
    }
}
