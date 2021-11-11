import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import com.cwk2.jaxrs.client.*;


// Implementation of the Web Interface Servlet
@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Servlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // gets the actor's properties that are going to be sent to the Client when invoking the first web service
        String gender = request.getParameter("gender");
        String type = request.getParameter("type");

        System.out.println("gender: " + gender);
        System.out.println("type: " + type);

        //Invocation of the Client with the actor's properties
        Client cli = new Client();
        cli.main(new String[]{gender, type});
        PrintWriter writer = response.getWriter();

        // build the HTML code and print out that a tweet has been posed if the call to the client was successful
        String htmlRespone = "<html>";
        htmlRespone += "<h2>A tweet has been posted by @DSCwk2 with a movie recommendation with a(n) : " + type + ", " + gender + " actor. </h2> <br/>";
        htmlRespone += "</html>";

        writer.println(htmlRespone);





    }

}
