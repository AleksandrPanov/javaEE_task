package server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Create")
public class Create extends HttpServlet {
    private String getResourcePath(String file) {
        return getClass().getResource("/").getPath() + "resources/" + file;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        PrintWriter pw = response.getWriter();
        StringBuilder builder = new StringBuilder();
        String path = getResourcePath("users.txt");
        builder.append("<html>");
        if (!Validation.loginIsValid(login))
        {
            builder.append("<p>login must be not null</p");
        }
        else if (!Validation.passwordIsValid(password))
        {
            builder.append("<p>password must have 4 or more symbols</p");
        }
        else
        {
            try
            {
                server.DataBase users = new server.DataBase(path);
                if (users.isContained(login))
                {
                    builder.append("user " + login + " already exists");
                }
                else
                {
                    users.insert(login, password);
                    builder.append("welcome " + login + ", registration completed successfully");
                }
            }
            catch (IOException ex)
            {
                builder.append("Database is lost");
            }
            builder.append("</html>");
        }
        pw.write(builder.toString());
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
