package Servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ImageServlet1")
public class ImageServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ImageServlet1() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String imagePath = getServletContext().getRealPath("/uploads/image1.jpg");
		Path path = Paths.get(imagePath);

		if (Files.exists(path)) {
			response.setContentType("image/jpeg");
			ServletOutputStream out = response.getOutputStream();
			Files.copy(path, out);
			out.close();
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}
