package masterofmusic.masterofmusic.controllers.GameControllers;


import masterofmusic.masterofmusic.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping("song")
public class LyricController {

    @Autowired
    private SongService songService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        modelMap.put("songs", songService.findAll());
        return "lyric-master/form";
    }

    @RequestMapping(value = "submit", method = RequestMethod.POST)
//    @PostMapping("lyric-master/submit")
    public String submit(HttpServletRequest request) {
        int score = 0;
        String []songIds = request.getParameterValues("songId");
        for(String songId : songIds){
            long answerIdCorrect = songService.findAnswerIdCorrect(Long.parseLong(songId));
            if(answerIdCorrect == Long.parseLong(request.getParameter("song_" + songId))){
                score++;
            }
            request.setAttribute("score", score);
            System.out.println(songId);
        }
        return "lyric-master/result";
    }
}
