package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("hello") // 웹 어플리케이션에서 /hello라고 들어오면 해당 메소드를 실행시켜준다.
    public String hello(Model model) {
        model.addAttribute("data", "hello!!"); // model에 (키, 값)을 담아서
        return "hello"; // templates/hello.html을 찾아간다.
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }
}
