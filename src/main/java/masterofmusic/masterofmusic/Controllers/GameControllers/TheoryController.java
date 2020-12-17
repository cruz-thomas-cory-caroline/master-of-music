package masterofmusic.masterofmusic.controllers.GameControllers;


import masterofmusic.masterofmusic.models.Answer;
import masterofmusic.masterofmusic.models.Question;
import masterofmusic.masterofmusic.repositories.AnswerRepository;
import masterofmusic.masterofmusic.repositories.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class TheoryController {

    private final QuestionRepository questionDao;
    private final AnswerRepository answerDao;

    public TheoryController(QuestionRepository questionDao, AnswerRepository answerDao){
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @GetMapping("/music-theory/{id}")
    public String viewQuizFormat(@PathVariable int id, Model model){
        ArrayList<Question> theoryList = questionDao.findAllByGameId(2L);
        model.addAttribute("questions", theoryList.get(id).getQuestion());
        long questionId = theoryList.get(id).getId();
        model.addAttribute("answers", answerDao.getAllByQuestionId(questionId));
        return "/music-theory";
    }


    @PostMapping("/music-theory/{id}")
    public String createScore(@RequestParam(name = "answers")String userAnswer, @PathVariable int id){
        ArrayList<Question> theoryList = questionDao.findAllByGameId(2L);
        long questionId = theoryList.get(id).getId();
        ArrayList<Answer> answerList = answerDao.getAllByQuestionId(questionId);
        String correctAnswer;
        for (Answer answer: answerList){
            if(answer.isCorrect()){
                correctAnswer = answer.getAnswer();
            }
        }
        System.out.println(userAnswer);
//        System.out.println(correctAnswer);

        return "music-theory";
    }

}
