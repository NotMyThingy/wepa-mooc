package wad.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

  @RequestMapping("*")
  @ResponseBody
  public String home() {
    return "Hello World!";
  }

	/**
	* Create string representation of HelloWorldController for printing
	* @return
	*/
	@Override
	public String toString() {
		return "HelloWorldController []";
	}
}
