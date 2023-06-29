package ru.job4j.todo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.SimpleTaskService;

import javax.servlet.http.HttpSession;

/**
 * Контроллер. Задачи.
 *
 * @author Buslaev
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskController.class.getName());

    private final SimpleTaskService taskService;

    public TaskController(SimpleTaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Возвращает страницу со списком всех заданий.
     *
     * @param model
     * @return возвращает предствление главная страница (все задания).
     */
    @GetMapping({"/", "/all"})
    public String getAllTask(Model model) {
        model.addAttribute("tasks", taskService.allTask());
        return "tasks/all";
    }

    /**
     * Возвращает страницу всех выполненных заданий.
     *
     * @param model
     * @return возвращает предствление выполненных заданий.
     */
    @GetMapping({"/done"})
    public String getAllDoneTrue(Model model) {
        model.addAttribute("tasks", taskService.allTaskTrueOrFalse(true));
        return "tasks/all";
    }

    /**
     * Возвращает страницу всех невыполненных заданий.
     *
     * @param model
     * @return возвращает предствление невыполненных заданий.
     */
    @GetMapping({"/undone"})
    public String getAllDoneFalse(Model model) {
        model.addAttribute("tasks", taskService.allTaskTrueOrFalse(false));
        return "tasks/all";
    }

    /**
     * Возвращает страницу добавления нового задания.
     *
     * @param model
     * @return возвращает предствление добавления нового задания.
     */
    @GetMapping("/add")
    public String create(Model model) {
        model.addAttribute("tasks", taskService.allTask());
        return "tasks/add";
    }

    /**
     * Метод создания задания.
     *
     * @param task обьект задания собранный в модели.
     * @return список всех задний.
     */
    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpSession httpSession) {
        task.setUser((User) httpSession.getAttribute("user"));
        taskService.add(task);
        return "redirect:/task/all";
    }

    /**
     * Возвращает страницу с описанием конкретного задания.
     *
     * @param model
     * @param id    конретного задания.
     * @return возвращает предствление задания по id.
     */
    @GetMapping("/one/{id}")
    public String one(Model model, @PathVariable("id") int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "tasks id not found");
            LOG.error(String.format("tasks id %d not found", id));
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    /**
     * Перевод задания в состояние выполненного после нажатия "Выполнено".
     *
     * @param model
     * @param id    конретного задания.
     * @return список всех задний.
     */
    @PostMapping("/done/{id}")
    public String done(Model model, @PathVariable("id") int id) {
        if (!taskService.updateDone(id)) {
            model.addAttribute("message", "Не удалось обновить");
            LOG.error("tasks has not been update");
            return "errors/404";
        }
        return "redirect:/task/all";
    }

    /**
     * Возвращает страницу с редактированием конкретного задания после нажатия "Редактировать".
     *
     * @param model
     * @param id    конретного задания.
     * @return возвращает предствление редактирования задания.
     */
    @GetMapping("/update/{id}")
    public String formUpdate(Model model, @PathVariable("id") int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "tasks id not found");
            LOG.error(String.format("tasks id %d not found", id));
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/update";
    }

    /**
     * Метод редактирования задания по собранной модели из представления.
     *
     * @param task обьект задания собранный в модели.
     * @return список всех задний.
     */
    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Task task) {
        if (!taskService.update(task)) {
            model.addAttribute("message", "Не удалось обновить");
            LOG.error("tasks has not been update");
            return "errors/404";
        }
        return "redirect:/task/all";
    }

    /**
     * Выполняет удаление задания после нажатия "Удалить".
     *
     * @param id конретного задания.
     * @return список всех задний.
     */
    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") int id) {
        if (!taskService.delete(id)) {
            model.addAttribute("message", "Не удалось удалить");
            LOG.error("tasks has not been delete");
            return "errors/404";
        }
        return "redirect:/task/all";
    }
}
