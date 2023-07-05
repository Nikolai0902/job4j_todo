package ru.job4j.todo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * Класс для проверки запроса на загрузку Task и связанного с ним Priority(связь @ManyToOne
 * + в этой аннотации используется ленивая стратегия загрузки).
 * Добавив к запросу "JOIN FETCH f.priority" указываем Hibernate, что объект
 * Priority нужно сразу загрузить из базы при выполнении нашего запроса.
 *
 * Иначе мы получаем org.hibernate.LazyInitializationException:
 * Данная проблема связана с тем, что мы пытаемся получить доступ к вложенному объекту вне текущей сессии
 * с которой ассоциирован этот объект и поэтому за пределами сессии он уже становится недоступен.
 */
public class ToDoRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            var stored = listOf("FROM Task f JOIN FETCH f.priority", Task.class, sf);
            for (Task task : stored) {
                System.out.println(task.getPriority());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> List<T> listOf(String query, Class<T> model, SessionFactory sf) {
        Session session = sf.openSession();
        var rsl = session.createQuery(query, model)
                .getResultList();
        session.close();
        return rsl;
    }
}
