package masterofmusic.masterofmusic.controllers.GameControllers;


import masterofmusic.masterofmusic.models.*;
import masterofmusic.masterofmusic.repositories.*;
import masterofmusic.masterofmusic.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

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
    private final UserRepository userDao;
    private final AchievementRepository achievementDao;

    public LyricController(GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDoa, GenreRepository genreDao, LyricAnswerRepository lyricAnswerDao, SongRepository songDao, UserRepository userDao, AchievementRepository achievementDao) {
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDoa;
        this.genreDao = genreDao;
        this.lyricAnswerDao = lyricAnswerDao;
        this.songDao = songDao;
        this.userDao = userDao;
        this.achievementDao = achievementDao;
    }

    Long round;
    String songDifficulty;
    String songGenre;

    @PostMapping("/lyric-master/")
    public String lyricMasterIndex(
            @RequestParam(name = "songDifficulty") String difficultySelection,
            @RequestParam(name = "songGenre") String genreSelection,
            @RequestParam(name = "round") long roundNumber
    ) {

        songDifficulty = difficultySelection;
        songGenre = genreSelection;
        round = roundNumber;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PlayerGame playerGame = new PlayerGame();

        if (roundNumber == 1) {
            playerGame.setGame(gameDao.getOne(1L));
            playerGame.setUser(user);
            playerGame.setScore(0);
            playerGame.setTimeElapsed(new Timestamp(0));
            playerGameDao.save(playerGame);
            currentGameID = playerGameDao.save(playerGame).getId();
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
                timeLimit = 400;
                questions = 6;
                break;
            case "medium":
                timeLimit = 360;
                questions = 8;
                break;
            case "hard":
                timeLimit = 300;
                questions = 10;
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
        modelMap.addAttribute("numberOfQuestion", questions);
        modelMap.addAttribute("chosenSongs", songsToPass);
        modelMap.addAttribute("timeLimit", timeLimit);
        modelMap.addAttribute("playerGame", currentGameID);

        return "lyric-master/form";
    }

    @PostMapping("lyric-master/submit")
    public String submit(@RequestParam(name = "songDifficulty") String difficulty,
                         @RequestParam(value = "playerGame") long playerGameId,
                         HttpServletRequest request
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user_db = userDao.findById(user.getId());
        int gameLevel = 0;
        gameLevel += 1;
        PlayerGameRound newPlayerGameRound = new PlayerGameRound();
        PlayerGame currentPlayerGame = playerGameDao.getOne(playerGameId);
        newPlayerGameRound.setDifficulty(songDifficulty);
        newPlayerGameRound.setLevel(gameLevel);
        newPlayerGameRound.setScore(0);
        newPlayerGameRound.setPlay_time(String.valueOf(new Timestamp(0)));
        newPlayerGameRound.setPlayerGame(currentPlayerGame);


        ArrayList<String> incorrectUserAnswers = new ArrayList<>();
        ArrayList<Song> correctSongs = new ArrayList<>();
        ArrayList<Song> incorrectSongs = new ArrayList<>();
        ArrayList<Song> testedSongs = new ArrayList<>();
        ArrayList<Integer> scoreAchievements = new ArrayList<>();

        String[] songIds = request.getParameterValues("songId");

        int score = 0;
        for (String songId : songIds) {
            testedSongs.add(songDao.getOne(Long.parseLong(songId)));
        }

        for (Song song : testedSongs) {
            long answerIdCorrect = songService.findAnswerIdCorrect(song.getId());
            if (answerIdCorrect == Long.parseLong(request.getParameter("song_" + song.getId()))) {
                newPlayerGameRound.setScore(newPlayerGameRound.getScore() + 100);
                correctSongs.add(song);
//                score += 100;

            } else if (answerIdCorrect != Long.parseLong(request.getParameter("song_" + song.getId()))) {
                incorrectUserAnswers.add(lyricAnswerDao.getOne(Long.parseLong(request.getParameter("song_" + song.getId()))).getLyricAnswer());
                incorrectSongs.add(song);
            }
        }


        //  CHECK FOR ACHIEVEMENTS
        ArrayList<PlayerGame> playerGamesForUser = playerGameDao.findAllByUserId(user.getId());
        ArrayList<List<PlayerGameRound>> playerGameRoundsForLM = new ArrayList<>();
        List<Achievement> gameAchievements = achievementDao.findAllByGameId(1);
        List<Achievement> userAchievements = user.getUsers_achievements();

        if (userAchievements == null) {
            System.out.println("Nothing has been Earned");
            userAchievements = new ArrayList<>();
        } else {
            for (Achievement achievement : userAchievements) {
                System.out.println("You've earned: " + achievement.getName());
            }
        }

        int finalScore = 0;

        switch (songDifficulty) {
            case "easy":
                finalScore = 600;
                break;
            case "medium":
                finalScore = 800;
                break;
            case "hard":
                finalScore = 1000;
                break;
        }

        boolean awardEarned = false;
        List<Achievement> newAwards = new ArrayList<>();

        if (finalScore >= 100 && !userAchievements.contains(gameAchievements.get(0))) {
            Achievement achToChange = achievementDao.getOne(gameAchievements.get(0).getId());
            List<User> usersWhoHaveBadge = achToChange.getUsers();
            if (usersWhoHaveBadge == null) {
                usersWhoHaveBadge = new ArrayList<>();
            }
            if (!usersWhoHaveBadge.contains(userDao.getOne(user.getId()))) {
                usersWhoHaveBadge.add(user);
                achToChange.setUsers(usersWhoHaveBadge);
                achievementDao.save(achToChange);

                userAchievements.add(achToChange);
                User userToSave = userDao.getOne(user.getId());
                userToSave.setUsers_achievements(userAchievements);
                userDao.save(userToSave);

                awardEarned = true;
                newAwards.add(achToChange);
            }
        }


        var gameCount = 0;
        var scoreCount = 0;
        boolean triviaGemAward;
        boolean easyPerfect = false;
        boolean mediumPerfect = false;
        boolean hardPerfect = false;


        // CHECK FOR GEM - PERFECT SCORE IN ALL DIFFICULTIES (13)
        for (List<PlayerGameRound> playerGameRound : playerGameRoundsForLM) {
            for (PlayerGameRound playerGameRound1 : playerGameRound) {
                if (playerGameRound1.getDifficulty().equals("easy") && playerGameRound1.getScore() == 600) {
                    easyPerfect = true;
                    break;
                }
            }
        }

        for (List<PlayerGameRound> playerGameRound : playerGameRoundsForLM) {
            for (PlayerGameRound playerGameRound1 : playerGameRound) {
                if (playerGameRound1.getDifficulty().equals("medium") && playerGameRound1.getScore() == 800) {
                    mediumPerfect = true;
                    break;
                }
            }
        }

        for (List<PlayerGameRound> playerGameRound : playerGameRoundsForLM) {
            for (PlayerGameRound playerGameRound1 : playerGameRound) {
                if (playerGameRound1.getDifficulty().equals("hard") && playerGameRound1.getScore() == 1000) {
                    hardPerfect = true;
                    break;
                }
            }
        }

        boolean gemAwardEarned = false;

        if (userAchievements == null) {
            userAchievements = new ArrayList<>();
        }

        if ((easyPerfect && mediumPerfect && hardPerfect) && !userAchievements.contains(gameAchievements.get(0))) {
            Achievement achToChange = achievementDao.getOne(gameAchievements.get(0).getId());
            List<User> usersWhoHaveBadge = achToChange.getUsers();
            if (usersWhoHaveBadge == null) {
                usersWhoHaveBadge = new ArrayList<>();
            }
            if (!usersWhoHaveBadge.contains(userDao.getOne(user.getId()))) {
                usersWhoHaveBadge.add(user);
                achToChange.setUsers(usersWhoHaveBadge);
                achievementDao.save(achToChange);
                userAchievements.add(achToChange);
                User userToSave = userDao.getOne(user.getId());
                userToSave.setUsers_achievements(userAchievements);
                userDao.save(userToSave);
                gemAwardEarned = true;
                newAwards.add(achToChange);
            }
        }

        PlayerGameRound playerGameRoundDB = playerGameRoundDao.save(newPlayerGameRound);
        currentPlayerGame.setScore(playerGameRoundDB.getPlayerGame().getScore() + playerGameRoundDB.getScore());
        playerGameDao.save(currentPlayerGame);

        request.setAttribute("awardEarned", awardEarned);
        request.setAttribute("newAwards", newAwards);
        request.setAttribute("scoreAchievements", scoreAchievements);
        request.setAttribute("correctSongs", correctSongs);
        request.setAttribute("score", newPlayerGameRound.getScore());
        request.setAttribute("totalRoundsScore", currentPlayerGame.getScore());
        request.setAttribute("incorrectUserAnswers", incorrectUserAnswers);
        request.setAttribute("incorrectSongs", incorrectSongs);
        request.setAttribute("currentLevel", newPlayerGameRound.getLevel());
        request.setAttribute("round", playerGameDao.getOne(Long.parseLong(request.getParameter("playerGame"))).getPlayerGameRounds().size() + 1);

        return "lyric-master/result";
    }
}
