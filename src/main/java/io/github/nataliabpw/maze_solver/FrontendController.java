package io.github.nataliabpw.maze_solver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MazeController {
    
    @RequestMapping("/")
    public String index(){
        return "index.html";
    }
}
