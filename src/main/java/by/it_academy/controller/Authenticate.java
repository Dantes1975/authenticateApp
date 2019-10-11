package by.it_academy.controller;

import by.it_academy.bean.Role;
import by.it_academy.bean.User;
import by.it_academy.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.it_academy.util.ApplicationConstant.*;
import static by.it_academy.util.ErrorConstant.*;

@WebServlet(name = "Authenticate", urlPatterns = {"/index", "/index.jsp", "/login", "/registration", "/logout", "/add", "/delete"},
        loadOnStartup = 1,
        initParams = {
                @WebInitParam(name = F_USER_INIT_PARAM_KEY, value = F_USER_INIT_PARAM_VALUE),
                @WebInitParam(name = S_USER_INIT_PARAM_KEY, value = S_USER_INIT_PARAM_VALUE),
        }
)
public class Authenticate extends HttpServlet {

    private final UserRepository userRepository = new UserRepository();

    @Override
    public void init() throws ServletException {
        String[] fUser = getInitParameter(F_USER_INIT_PARAM_KEY).split(SEPARATOR);
        String[] sUser = getInitParameter(S_USER_INIT_PARAM_KEY).split(SEPARATOR);

        userRepository.create(new User(fUser[0], fUser[1], Role.ADMIN));
        userRepository.create(new User(sUser[0], sUser[1], Role.ADMIN));

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter(ACTION_KEY);

        String login = request.getParameter(LOGIN_KEY);
        String password = request.getParameter(PASSWORD_KEY);
        String role = request.getParameter(ROLE_KEY);
        User user = null;

        try {

            if (action.toLowerCase().equals(LOGOUT_KEY)) {
                request.getSession().invalidate();
                response.sendRedirect(LOGIN_KEY);
                return;
            }

            if (login == null || login.isEmpty()) {
                throw new RuntimeException(INVALID_USER_LOGIN);
            }
            if (password == null || password.isEmpty()) {
                throw new RuntimeException(INVALID_USER_PASSWORD);
            }

            if (action.toLowerCase().equals(ADD_KEY)) {
                if (!userRepository.isUserLoginExist(login)) {
                    if (role.equals("USER")) {
                        user = userRepository.create(new User(login, password, Role.USER));
                    } else if (role.equals("ADMIN")) {
                        user = userRepository.create(new User(login, password, Role.ADMIN));
                    }
                }
                return;
            }

            if (action.toLowerCase().equals(DELETE_KEY)) {
                user = userRepository.getByLoginAndPassword(login, password);
                userRepository.delete(user.getId());
                return;
            }

            if (action.toLowerCase().equals(LOGIN_KEY)) {
                if (!userRepository.isUserLoginExist(login)) {
                    throw new RuntimeException(INVALID_USER_AUTHENTICATION_DATA);
                }

                user = userRepository.getByLoginAndPassword(login, password);

            } else {

                if (userRepository.isUserLoginExist(login)) {
                    throw new RuntimeException(USER_ALREADY_EXIST);
                }

                user = userRepository.create(new User(login, password, Role.USER));

            }

        } catch (RuntimeException e) {

            request.getSession().setAttribute(ERROR_KEY, e.getMessage());
            response.sendRedirect(action.toLowerCase());
            return;
        }

        List<User> users = userRepository.getAllUsers();

        HttpSession session = request.getSession();
        session.setAttribute(USER_KEY, user);
        session.setAttribute("users", users);

        request.setAttribute(TITLE_KEY, MAIN_PAGE_TITLE);
        getServletContext().getRequestDispatcher(MAIN_PAGE).forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(USER_KEY) != null) {
            request.setAttribute(TITLE_KEY, MAIN_PAGE_TITLE);
            getServletContext().getRequestDispatcher(MAIN_PAGE).forward(request, response);
        }

        String title, action, ahref;

        if (requestURI.contains(REGISTRATION_KEY)) {
            title = REGISTRATION_PAGE_TITLE;
            action = REGISTRATION_KEY;
            ahref = LOGIN_KEY;
        } else {
            title = LOGIN_PAGE_TITLE;
            action = LOGIN_KEY;
            ahref = REGISTRATION_KEY;
        }

        request.setAttribute(TITLE_KEY, title);
        request.setAttribute(ACTION_KEY, action);
        request.setAttribute(AHREF_KEY, ahref);

        getServletContext().getRequestDispatcher(AUTHENTICATE_PAGE).forward(request, response);

    }
}
