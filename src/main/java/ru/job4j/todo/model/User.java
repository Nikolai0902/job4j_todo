package ru.job4j.todo.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Модель пользлвателя.
 * timezone - поле с указанием его часового пояса. В БД - user_zone.
 */
@Data
@Entity
@Table(name = "todo_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String login;
    private String password;

    @Column(name = "user_zone")
    private String timezone;
}
