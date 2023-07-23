package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель данных задача.
 * @author Buslaev
 *
 *
 * @ManyToOne - связь Задач и одного Пользователя.
 * @JoinColumn - по какому полю связан задачи (Task) и User в таблице todo_user.
 *
 * @ManyToOne - связь Задач и приоритета.
 * fetch = FetchType.LAZY - в аннотации ManyToOne используется ленивая стратегия загрузки.
 * @JoinColumn - по какому полю связан задачи (Task) и приоритет в таблице priorities.
 *
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String title;
    private String description;
    private LocalDateTime created;

    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private List<Category> category = new ArrayList<>();
}
