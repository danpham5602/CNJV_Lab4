package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig()
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("upload.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			List<String> extensions = Arrays.asList("txt", "doc", "docx", "jpg", "png", "pdf", "rar", "zip");

			String customFileName = request.getParameter("filename");
			String override = request.getParameter("override");
			Part part = request.getPart("file");

			String fileName = Path.of(part.getSubmittedFileName()).getFileName().toString();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

			if (!extensions.contains(extension)) {
				out.print("<h2>File's type is invalid</h2>");
				return;
			}

			String realPath = getServletContext().getRealPath("/uploads");
			String newFileName = Path.of(customFileName + "." + extension).getFileName().toString();

			if (!Files.exists(Path.of(realPath))) {
				Files.createDirectory(Path.of(realPath));
			}

			if (Files.exists(Path.of(realPath + "/" + newFileName))) {
				if (override != null) {
					int i = 0;
					while (Files.exists(Path.of(realPath + "/" + newFileName))) {
						newFileName = Path.of(customFileName + "-(" + i + ")." + extension).getFileName().toString();
						i += 1;
					}
				} else {
					out.print("<h2>File already exists</h2>");
					out.print("<a href='./upload.jsp'>Back</a>");
					return;
				}
			}

			part.write(realPath + "/" + newFileName);

			out.print("<h2>File uploaded successfully!</h2>");
			out.print("<p>Click <a href='./download?file="+newFileName+"'>here</a> to download file</p>");
			out.print("<a href='./upload'>Back</a>");
		} catch (Exception e) {

		}
	}
}
