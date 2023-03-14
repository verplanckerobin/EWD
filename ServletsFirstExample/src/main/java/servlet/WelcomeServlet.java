package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WelcomeServlet
 */
@WebServlet("/welcome1")
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelcomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    	throws ServletException, IOException {
        
        String firstname = request.getParameter("firstname");
        
        request.setAttribute("firstName", firstname);
    	
    	RequestDispatcher view = request.getRequestDispatcher("welcome.jsp");
    	view.forward(request, response);

//        response.setContentType("text/html");

//        try (PrintWriter out = response.getWriter()) {
//            // start HTML document
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            
//            // head section of document
//            out.println("<head>");
//            out.println("<title>A Simple Servlet Example</title>");
//            out.println("</head>");
//            
//            // body section of document
//            out.println("<body>");
//            
//            //out.println("<h1>Welcome to Servlets!</h1>");
//            out.printf("<h1>Welcome to Servlets! %s</h1>", firstname);
//
//            out.println("</body>");
//            // end HTML document
//            out.println("</html>");
//            
//        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
	}
    
}