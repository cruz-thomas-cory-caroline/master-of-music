package masterofmusic.masterofmusic.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class TestsController {

    @Value("${app.url}")
    private String appUrl;

    @GetMapping("/testPathContext")
    @ResponseBody
    public String testPathContext(){
        System.out.println(appUrl);
        return "app.url = " + appUrl;
    }
}

