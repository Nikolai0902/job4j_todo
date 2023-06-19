package ru.job4j.todo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Модель данных задача.
 * @author Buslaev
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
    private String description;

    @EqualsAndHashCode.Include
    private LocalDateTime created = LocalDateTime.now();
    private boolean done;
}
