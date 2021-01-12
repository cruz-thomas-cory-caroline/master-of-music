package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.*;
import masterofmusic.masterofmusic.repositories.*;
import org.springframework.beans.factory.annotation.Value;
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
    private final AchievementRepository achievementDao;
    private final UserRepository userDao;
    private List<Long> chosenSongIDs = new ArrayList<>();
    private List<Song> chosenSongs = new ArrayList<>();
    private long currentGameID;
    boolean usedCheckFeature;

//    matches the name from applications.properties
    @Value("${deezer.api.key}")
    private String deezerAPI;


    public UnscrambleController(SongRepository songDao, GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDoa, GenreRepository genreDao, AchievementRepository achievementDao, UserRepository userDao) {
        this.songDao = songDao;
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDoa = playerGameRoundDoa;
        this.genreDao = genreDao;
        this.achievementDao = achievementDao;
        this.userDao = userDao;
    }


    @GetMapping("/unscramble")
    public String unscrambleGame(@RequestParam(name = "difficulty") String difficulty,
                                 @RequestParam(name = "genre") String genre,
                                 @RequestParam(name = "round") long num,
                                 Model model) {

        usedCheckFeature = false;
        chosenSongs = new ArrayList<>();
        chosenSongIDs = new ArrayList<>();

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
                timeLimit = 90;
                break;
            case "medium":
                numberOfQuestions = 7;
                timeLimit = 60;
                break;
            case "hard":
                numberOfQuestions = 10;
                timeLimit = 30;
                break;
        }

        long genreID = 0;

        switch (genre) {
            case "rock":
                genreID = 1;
                break;
            case "pop":
                genreID = 3;
                break;
            case "hip-hop":
                genreID = 2;
                break;
            case "country":
                genreID = 4;
                break;
        }

        List<Song> allSongsOfGenre = genreDao.getOne(genreID).getSongs();

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
                    break;
                default:
                    break;
            }
            scrambledLyricsList.add(scrambledLyric);
        }
        model.addAttribute("deezerAPI", deezerAPI);
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
        User userToGetID = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getOne(userToGetID.getId());
        PlayerGameRound newRoundCompleted = new PlayerGameRound();
        newRoundCompleted.setDifficulty(difficulty);
        newRoundCompleted.setLevel(playerGameDao.getOne(num).getPlayerGameRounds().size()+1);
        newRoundCompleted.setPlayerGame(playerGameDao.getOne(num));
        newRoundCompleted.setPlay_time("here");
        newRoundCompleted.setScore(0);

        List<List<String>> splitSongLyrics = new ArrayList<>();
        List<String> splitLyricSet = new ArrayList<>();

        List<List<String>> splitUserLyrics = new ArrayList<>();
        List<String> splitUserSet = new ArrayList<>();
        int wordTotal = 0;
        int wordsCorrect = 0;

        for (Song song : chosenSongs) {
            splitLyricSet = new ArrayList<>(Arrays.asList(song.getLyrics().split(" ")));
            wordTotal += splitLyricSet.size();
            splitSongLyrics.add(splitLyricSet);

            String userLyric = request.getParameter("song" + chosenSongs.indexOf(song));
            splitUserSet = new ArrayList<>(Arrays.asList(userLyric.split(" ")));
            while (splitUserSet.size() < splitLyricSet.size()) {
                splitUserSet.add("xyz");
            }
            splitUserLyrics.add(splitUserSet);

            for (var i = 0; i < splitLyricSet.size(); i++) {
                if (splitLyricSet.get(i).equals(splitUserSet.get(i))) {
                    wordsCorrect += 1;
                }
            }
        }

        int totalPossScore = 0;
        int finalScore = 0;

        switch(difficulty) {
            case "easy":
                totalPossScore = wordTotal * 10;
                finalScore = wordsCorrect * 10;
                break;
            case "medium":
                totalPossScore = wordTotal * 15;
                finalScore = wordsCorrect * 15;
                break;
            case "hard":
                totalPossScore = wordTotal * 20;
                finalScore = wordsCorrect * 20;
                break;
        }


        newRoundCompleted.setScore(finalScore);
        playerGameDao.getOne(newRoundCompleted.getPlayerGame().getId()).setScore(newRoundCompleted.getPlayerGame().getScore()+newRoundCompleted.getScore());
        PlayerGameRound savedRound = playerGameRoundDoa.save(newRoundCompleted);


        model.addAttribute("score", savedRound.getScore());
        model.addAttribute("songs", chosenSongs);
        if (finalScore >= totalPossScore/2) {
            model.addAttribute("canAdvance", true);
        }
        model.addAttribute("currentLevel", newRoundCompleted.getLevel());
        model.addAttribute("userAnswers", splitUserLyrics);
        model.addAttribute("songLyrics", splitSongLyrics);


        List<Achievement> gameAchievements = achievementDao.findAllByGameId(4);
        List<Achievement> userAchievements = user.getUsers_achievements();

        if (userAchievements == null) {
            System.out.println("Nothing has been Earned");
            userAchievements = new ArrayList<>();
        } else {
            for (Achievement ach : userAchievements) {
                System.out.println("You've earned: " + ach.getName());
            }
        }

        boolean awardEarned = false;
        List<Achievement> newAwards = new ArrayList<>();

        if(finalScore >= 300 && !userAchievements.contains(gameAchievements.get(0))) {
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

        if (playerGameDao.getOne(newRoundCompleted.getPlayerGame().getId()).getScore() >= 1000 && !userAchievements.contains(gameAchievements.get(1))) {
            Achievement achToChange = achievementDao.getOne(gameAchievements.get(1).getId());
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

        if (finalScore >= 500 && !usedCheckFeature && !userAchievements.contains(gameAchievements.get(2))) {
            Achievement achToChange = achievementDao.getOne(gameAchievements.get(2).getId());
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

        if (difficulty.equalsIgnoreCase("hard") && finalScore == totalPossScore && !userAchievements.contains(gameAchievements.get(3))) {
            Achievement achToChange = achievementDao.getOne(gameAchievements.get(3).getId());
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

        model.addAttribute("awardEarned", awardEarned);
        model.addAttribute("newAwards", newAwards);
        return "final";
    }

    @RequestMapping("/check")
    @ResponseBody
    public List<Integer> check(@RequestParam(name = "id") long id,
                        @RequestParam(name = "userAnswer") String userAnswer,
                        Model model) {

        usedCheckFeature = true;
        String lyrics = songDao.getOne(id).getLyrics();

        List<Integer> rightWrong = new ArrayList<>();

        List<String> lyricWordList = new ArrayList<>();
        String str[] = lyrics.split(" ");
         lyricWordList = new ArrayList<>(Arrays.asList(str));

        List<String> userAnswerWordList = new ArrayList<>();
        String str1[] = userAnswer.split(" ");
        userAnswerWordList = new ArrayList<>(Arrays.asList(str1));

        while (userAnswerWordList.size() < lyricWordList.size()) {
            userAnswerWordList.add("xyz");
        }

        for (var i = 0; i < lyricWordList.size(); i++) {
            if (lyricWordList.get(i).equals(userAnswerWordList.get(i))) {
                rightWrong.add(1);
            } else {
                rightWrong.add(0);
            }
        }

        return rightWrong;
    }

}
