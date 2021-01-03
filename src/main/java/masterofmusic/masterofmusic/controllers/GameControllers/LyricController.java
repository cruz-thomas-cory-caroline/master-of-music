package masterofmusic.masterofmusic.controllers.GameControllers;


import masterofmusic.masterofmusic.models.*;
import masterofmusic.masterofmusic.repositories.*;
import masterofmusic.masterofmusic.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Controller
//@RequestMapping("song")
public class LyricController {

    @Autowired
    private SongService songService;

    private final GameRepository gameDao;
    private final PlayerGameRepository playerGameDao;
    private final PlayerGameRoundRepository playerGameRoundDoa;
    private final GenreRepository genreDao;
    private List<Long> chosenSongIDs = new ArrayList<>();
    private List<Song> chosenSongs = new ArrayList<>();
    private long currentGameID;


    public LyricController(GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDoa, GenreRepository genreDao) {
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDoa = playerGameRoundDoa;
        this.genreDao = genreDao;
    }


    String songDifficulty;
    String songGenre;
    boolean easyOption = false;
    long mediumOption = 10000;
    long hardOption = 2000;

    @PostMapping("/lyric-master/")
    public String lyricMasterIndex(
            @RequestParam(name = "songDifficulty") String difficultySelection,
            @RequestParam(name = "songGenre") String genreSelection,
            Model viewModel
    ) {
        songDifficulty = difficultySelection;
        songGenre = genreSelection;

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

//        Genre rockGenre = genreDao.getOne(1L);
//       if (songGenre.equals("Rock")){
//            ArrayList<Song> rockSongs = new ArrayList<>();
//            for (Song song : songs) {
//                if (song.getSong_genres().contains(rockGenre)) {
//                    rockSongs.add(song);
//                }
//            }

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

    //    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @PostMapping("lyric-master/submit")
    public String submit(HttpServletRequest request) {
        int score = 0;
        String[] songIds = request.getParameterValues("songId");
        for (String songId : songIds) {
            long answerIdCorrect = songService.findAnswerIdCorrect(Long.parseLong(songId));
            if (answerIdCorrect == Long.parseLong(request.getParameter("song_" + songId))) {
                score++;
            }
            request.setAttribute("score", score);
            System.out.println(songId);
        }
        return "lyric-master/result";
    }
}
