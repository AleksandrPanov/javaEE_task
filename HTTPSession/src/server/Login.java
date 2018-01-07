package server;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

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
        String useCache = req.getParameter("cache");
        StringBuilder builder = new StringBuilder();

        builder.append("<html>");
        if (login.length() == 0 && password.length() == 0 && !useCache.equals("nothing"))
        {
            if (useCache.equals("session"))
            {
                if (session != null )
                {
                    login = (String) session.getAttribute("login");
                    password = (String) session.getAttribute("password");
                    redirectToWelcomePage(builder, login, password);
                }
                else
                    builder.append("session is empty");
            }
            else if (useCache.equals("cookies"))
            {
                Cookie[] cookies = req.getCookies();
                if (cookies != null)
                {
                    builder.append("<p>num of cookies "+cookies.length+"</p>");
                    for (Cookie cookie : cookies)
                    {
                        if (cookie.getName().equals("login"))
                        {
                            String s[] = cookie.getValue().split("_");
                            login = s[0];
                            password = s[1];
                            redirectToWelcomePage(builder, login, password);
                        }
                    }
                }
                else
                    builder.append("<p>num of cookies 0</p>");
            }
        }
        else if (!Validation.loginIsValid(login) || !Validation.passwordIsValid(password))
        {
            if (login.length() == 0 || password.length() == 0)
            builder.append("<p>login and password must be not null</p");
            else
            builder.append("<p>password must have 4 or more symbols, login and password cannot contained '_' or ' '</p");
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
                    String s = login + "_" + password;
                    Cookie cookie = new Cookie("login", s);
                    resp.addCookie(cookie);
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
