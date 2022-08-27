package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("hello-string")
    @ResponseBody // StringConverter 동작 -> 문자 반환
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // "hello spring!!"
    }

    @GetMapping("hello-api")
    @ResponseBody // JsonConverter 동작 -> JSON 반환
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // {"name":"spring!!"}
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
