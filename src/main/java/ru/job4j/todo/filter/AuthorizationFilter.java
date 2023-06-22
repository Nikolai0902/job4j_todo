package ru.job4j.todo.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Фильтр авторизации пользователя.
 * @author Buslaev
 */
@Component
@Order(1)
public class AuthorizationFilter extends HttpFilter {

    private final Set<String> allowedLinks1 = Set.of("register", "login", "errors", "logout");

    /**
     * Проверяем обращается ли пользователь к общедоступным адресам. Если да, то сразу пропускаем запрос.
     * Далее если пользователь обращается адресам, требующих прав, то мы проверяем вошел ли пользователь в систему.
     * Если не вошел, то перебрасываем его на страницу входа.
     * Наконец, если пользователь залоггинен в системе, то мы разрешаем дальнейшее выполнение запроса.
     *
     * chain.doFilter(request, response) вызывает следующий в цепочке фильтр. Если его нет, то запрос уходит к контроллеру.
     * response.sendRedirect(url) выполняет перенаправление по URL. Это аналогично "redirect:/.....".
     *
     * @param request запрос для возврата URI.
     * @param response ответ.
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var uri = request.getRequestURI();
        if (isAlwaysPermitted(uri)) {
            chain.doFilter(request, response);
            return;
        }
        var userLoggedIn = request.getSession().getAttribute("user") != null;
        if (!userLoggedIn) {
            var loginPageUrl = request.getContextPath() + "/users/login";
            response.sendRedirect(loginPageUrl);
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * Проверка uri на допустимость.
     * @param uri
     * @return
     */
    private boolean isAlwaysPermitted(String uri) {
        String[] split = uri.split("/");
        return split.length > 0
                && (allowedLinks1.contains(split[split.length - 1])
                || (split.length > 2 &&  allowedLinks1.contains(split[split.length - 2]))
        );
    }
}
