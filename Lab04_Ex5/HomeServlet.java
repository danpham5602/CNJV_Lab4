package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HomeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String page = request.getParameter("page");
		List<String> pages = Arrays.asList("contact", "help", "about");
		RequestDispatcher rd = null;

		if (page != null && pages.contains(page)) {
			rd = request.getRequestDispatcher(page + ".jsp");
		} else {
			rd = request.getRequestDispatcher("home.jsp");
		}

		rd.forward(request, response);
	}
}
