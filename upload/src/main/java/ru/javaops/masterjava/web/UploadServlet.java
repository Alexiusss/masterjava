package ru.javaops.masterjava.web;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import ru.javaops.masterjava.xml.schema.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

import static ru.javaops.masterjava.util.UserStaxUtil.getTestUserList;

//@WebServlet("/upload")
public class UploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/templates/UploadForm.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<User> userList;
        try {
            userList = getTestUserList(request, "upfile");
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }

        TemplateEngine templateEngine = (TemplateEngine) request.getServletContext().getAttribute("templateEngine");
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale());

        context.setVariable("users", userList);
        templateEngine.process("UserList", context, response.getWriter());
    }
}
