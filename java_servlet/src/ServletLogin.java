import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class ServletLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        PrintWriter pw = resp.getWriter();
        StringBuilder builder = new StringBuilder();


        builder.append("<html>");
        if (login.length() == 0 || password.length() == 0 )
        {
            builder.append("<p>login and password must be not null</p");
        }
        else if (password.length() < 4)
        {
            builder.append("<p>password must have 4 or more symbols</p");
        }
        else
        {
            builder.append("<h2> Your user name is: " + login + "</br>");
            builder.append("Your password is : " + password + "</h2>");
        }

        builder.append("</html>");

        pw.write(builder.toString());
    }
}
