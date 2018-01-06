package server;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Login")
public class Login extends HttpServlet {
    private HttpSession session;
    private String getResourcePath(String file) {
        return getClass().getResource("/").getPath() + "resources/" + file;
    }
    private void redirectToWelcomePage(StringBuilder builder, String login, String password)
    {
        builder.append("<p>Welcome!</p>");
        builder.append("<h2> Your user name is: " + login + "</br>");
        builder.append("Your password is : " + password + "</h2>");
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        StringBuilder builder = new StringBuilder();

        builder.append("<html>");
        if (login.length() == 0 && password.length() == 0 && session != null)
        {
            login = (String) session.getAttribute("login");
            password = (String) session.getAttribute("password");
            redirectToWelcomePage(builder, login, password);
        }
        else if (!Validation.loginIsValid(login))
        {
            builder.append("<p>login must be not null</p");
        }
        else if (!Validation.passwordIsValid(password))
        {
            builder.append("<p>password must have 4 or more symbols</p");
        }
        else
        {
            String path = getResourcePath("users.txt");
            try
            {
                server.DataBase users = new server.DataBase(path);
                if (users.isContained(login, password))
                {
                    session = req.getSession();
                    session.setAttribute("login", login);
                    session.setAttribute("password", password);
                    redirectToWelcomePage(builder, login, password);
                }
                else if (users.isContained(login))
                {
                    builder.append("password "+ password + " is wrong");
                }
                else
                {
                    builder.append("login "+login+"doesn't exist, do you want create this user?");
                    //редирект
                    RequestDispatcher summary = getServletContext().getRequestDispatcher("/create.html");
                    if (summary != null)
                    {
                        try {
                            summary.forward(req, resp);
                            //summary.include(req, resp);
                        }
                        catch (IOException e) {
                            builder.append("io exception");
                        }
                        catch (ServletException e) {
                            builder.append("servlet exception");
                        }
                    }
                }
            }
            catch(IOException ex)
            {
                builder.append("Database is lost");
            }
        }
        builder.append("</html>");
        PrintWriter pw = resp.getWriter();
        pw.write(builder.toString());
    }
}
