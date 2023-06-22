package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.SimpleUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final SimpleUserService userService;

    public UserController(SimpleUserService userService) {
        this.userService = userService;
    }

    /**
     * форма регистрации.
     * @return форма регистрации.
     */
    @GetMapping("/register")
    public String getRegistrationPage() {
        return "users/register";
    }

    /**
     * Добавление пользователя.
     * Обеспечение уникальности будет нести ответственность СУБД.
     * @param model
     * @param user собранная модель данных пользователя.
     * @return если пользователь создан то redirect:/index или предупреждение о существующем пользователе.
     */
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user) {
        var savedUser = userService.create(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("error", "Пользователь с такой почтой уже существует");
            return "users/register";
        }
        return "redirect:/task/all";
    }

    /**
     * форма входа пользователя в систему.
     * @return форма входа.
     */
    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    /**
     * Осуществление входа пользователя.
     * @param user собранная модель данных пользователя.
     * @param model
     * @param request оъект HttpServletRequest для связи пользователя с текущей сессией.
     * Объект HttpSession можно получить через HttpServletRequest.
     * В нем можно хранить информацию о текущем пользователе, которую можно получить на другой странице.
     * Данные сессии привязываются к клиенту и доступны во всем приложении.
     * @return представление ошибки входа либо кинотеку.
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByLoginAndPW(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/task/all";
    }

    /**
     * Выход из системы.
     * @param session
     * @return старницу входа в систему.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
