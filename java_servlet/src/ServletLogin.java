import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class ServletLogin extends HttpServlet {
    private void send(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        PrintWriter pw = response.getWriter();
        StringBuilder builder = new StringBuilder();

        builder.append("<html>");
        if (login.length() == 0 || password.length() == 0 )
        {
            builder.append("<p>login and password must be not null</p");
        }
        else if (!Validation.passwordIsValid(password))
        {
            builder.append("<p>correct password is 'root'</p");
        }
        else
        {
            builder.append("<h2> Your user name is: " + login + "</br>");
            builder.append("Your password is : " + password + "</h2>");
        }

        builder.append("</html>");

        pw.write(builder.toString());
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        send(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        send(request, response);
    }
}
