package ru.job4j.todo.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс конфигурирования/
 * @author Buslaev
 */
@Configuration
public class SFConfiguration {

    /**
     * SessionFactory - это объект конфигуратор. Он создается один раз на все приложение.
     * В нем происходит создания пулов, загрузка кешей, проверка моделей.
     * SessionFactory имеет метод openSession, который позволяет записать,
     * удалить и прочитать данные из базы. Этот объект создается быстро.
     * @return SessionFactory
     */
    @Bean(destroyMethod = "close")
    public SessionFactory sf() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }
}
