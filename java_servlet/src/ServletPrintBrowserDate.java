import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/ServletPrintBrowserDate")
public class ServletPrintBrowserDate extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String data = request.getParameter("browserInfo");
        PrintWriter pw = response.getWriter();
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append("<p>browser name</p>");
        builder.append("<p>"+data+"</p>");
        builder.append("<p>"+request.getHeader("user-agent")+"</p>");
        builder.append("<p>Data/time: "+new Date().toString()+"</p>");
        builder.append("</html>");
        pw.write(builder.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
