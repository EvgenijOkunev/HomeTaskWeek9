package com.geekhub.hw8;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Date;

@WebServlet("/file/create")
public class CreateFileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String directory = (String) req.getSession().getAttribute("path");
        String fileName = req.getParameter("fileName");
        String result;
        Path path;

        try {
            path = Paths.get(directory, fileName);
            Files.createFile(path);
            result = "file " + path.getFileName() + " has been created";
        } catch (InvalidPathException e) {
            result = "invalid path";
        } catch (IOException e) {
            result = "error while creating file: " + e;
        }

        req.getSession().setAttribute("message", "[" + DateFormat.getDateTimeInstance().format(new Date()) + "] " + result);
        resp.sendRedirect("/dir/view");
    }

}