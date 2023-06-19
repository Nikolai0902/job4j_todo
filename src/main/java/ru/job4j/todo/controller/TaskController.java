package ru.job4j.todo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.SimpleTaskService;

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
     * @param model
     * @return возвращает предствление главная страница (все задания).
     */
    @GetMapping({"/", "/allTasks"})
    public String getAllTask(Model model) {
        model.addAttribute("tasks", taskService.allTask());
        return "allTasks";
    }

    /**
     * Возвращает страницу всех выполненных заданий.
     * @param model
     * @return возвращает предствление выполненных заданий.
     */
    @GetMapping({"/allTasksDoneTrue"})
    public String getAllTaskDoneTrue(Model model) {
        model.addAttribute("tasks", taskService.allTaskTrue());
        return "allTasks";
    }

    /**
     * Возвращает страницу всех невыполненных заданий.
     * @param model
     * @return возвращает предствление невыполненных заданий.
     */
    @GetMapping({"/allTasksDoneFalse"})
    public String getAllTaskDoneFalse(Model model) {
        model.addAttribute("tasks", taskService.allTaskFalse());
        return "allTasks";
    }

    /**
     * Возвращает страницу добавления нового задания.
     * @param model
     * @return возвращает предствление добавления нового задания.
     */
    @GetMapping("/addTask")
    public String createTask(Model model) {
        model.addAttribute("tasks", taskService.allTask());
        return "addTask";
    }

    /**
     * Метод создания задания.
     * @param task обьект задания собранный в модели.
     * @return список всех задний.
     */
    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/task/allTasks";
    }

    /**
     * Возвращает страницу с описанием конкретного задания.
     * @param model
     * @param id конретного задания.
     * @return возвращает предствление задания по id.
     */
    @GetMapping("/formOneTask/{idTask}")
    public String oneTask(Model model, @PathVariable("idTask") int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "tasks id not found");
            LOG.error(String.format("tasks id %d not found", id));
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "formOneTask";
    }

    /**
     * Перевод задания в состояние выполненного после нажатия "Выполнено".
     * @param model
     * @param id конретного задания.
     * @return список всех задний.
     */
    @PostMapping("/doneTask/{id}")
    public String doneTask(Model model, @PathVariable("id") int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "tasks id not found");
            LOG.error(String.format("tasks id %d not found", id));
            return "errors/404";
        }
        Task task = taskOptional.get();
        task.setDone(true);
        taskService.update(task);
        return "redirect:/task/allTasks";
    }

    /**
     * Возвращает страницу с редактированием конкретного задания после нажатия "Редактировать".
     * @param model
     * @param id конретного задания.
     * @return возвращает предствление редактирования задания.
     */
    @GetMapping("/formUpdateTask/{idT}")
    public String formUpdateTask(Model model, @PathVariable("idT") int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "tasks id not found");
            LOG.error(String.format("tasks id %d not found", id));
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "updateTask";
    }

    /**
     * Метод редактирования задания по собранной модели из представления.
     * @param task обьект задания собранный в модели.
     * @return список всех задний.
     */
    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        taskService.update(task);
        return "redirect:/task/allTasks";
    }

    /**
     * Выполняет удаление задания после нажатия "Удалить".
     * @param id конретного задания.
     * @return список всех задний.
     */
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") int id) {
        taskService.delete(id);
        return "redirect:/task/allTasks";
    }
}
