package Servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("file");
		String path = getServletContext().getRealPath("/uploads/" + fileName);
		File downloadFile = new File(path);

		if (downloadFile.exists()) {
			FileInputStream inStream = new FileInputStream(downloadFile);

			ServletContext context = getServletContext();
			String mimeType = context.getMimeType(path);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			response.setHeader(headerKey, headerValue);

			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[4068];
			int bytesRead = -1;

			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inStream.close();
			outStream.close();
		} else {
			response.setHeader("Content-Type", "text/plain");
			response.setHeader("success", "yes");
			PrintWriter writer = response.getWriter();
			writer.write("File Not Found\n");
			writer.close();
		}
	}
}
