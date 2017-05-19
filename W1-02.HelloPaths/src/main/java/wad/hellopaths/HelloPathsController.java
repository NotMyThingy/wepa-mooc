package wad.hellopaths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloPathsController {

  @RequestMapping("/hello")
  @ResponseBody
  public String hello() {
    return "Hello";
  }

  @RequestMapping("/paths")
  @ResponseBody
  public String paths() {
    return "Paths";
  }
}
