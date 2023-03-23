package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.BeerExpertBean;
import domain.BeerProperty;

/**
 * Servlet implementation class BeerSelectServlet
 */
@WebServlet("/beerSelect")
public class BeerSelectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private BeerExpertBean bean = new BeerExpertBean();

    public BeerSelectServlet() {
	super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	ServletContext application = getServletContext();
	application.setAttribute("beerColors", new BeerProperty().getColors());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	RequestDispatcher view = request.getRequestDispatcher("beerChoice.jsp");
	view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	String color = request.getParameter("color");
	List<String> result = bean.getBrands(color);
	request.setAttribute("color", color);
	request.setAttribute("listBeer", result);
	RequestDispatcher view = request.getRequestDispatcher("beerResult.jsp");
	view.forward(request, response);
    }
}
