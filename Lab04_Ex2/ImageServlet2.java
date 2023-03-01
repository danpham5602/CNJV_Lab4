package Servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ImageServlet2")
public class ImageServlet2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ImageServlet2() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filePath = getServletContext().getRealPath("/uploads/image2.png");
        File downloadFile = new File(filePath);
        FileInputStream inStream = new FileInputStream(downloadFile);

        // Set content type and header for image
        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "attachment; filename=image2.png");

        // Write image content to response output stream
        OutputStream outStream = response.getOutputStream();
        Files.copy(downloadFile.toPath(), outStream);

        inStream.close();
        outStream.flush();
        outStream.close();
    }
}