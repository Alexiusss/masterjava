package ru.javaops.masterjava.util;

import ru.javaops.masterjava.xml.schema.FlagType;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserStaxUtil {
    public static List<User> getTestUserList(HttpServletRequest request, String fileName) throws ServletException, IOException, XMLStreamException {
        InputStream inputStream = request.getPart(fileName).getInputStream();
        StaxStreamProcessor processor = new StaxStreamProcessor(inputStream);

        List<User> userList = new ArrayList<>();
        while (processor.startElement("User", "Users")) {
            User user = new User();
            user.setEmail(processor.getAttribute("email"));
            user.setFlag(FlagType.fromValue(processor.getAttribute("flag")));
            user.setValue(processor.getText());
            userList.add(user);
        }
        return userList;
    }
}