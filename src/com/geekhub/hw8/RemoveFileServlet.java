package com.geekhub.hw8;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Date;

@WebServlet("/file/remove")
public class RemoveFileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Path path = Paths.get(req.getParameter("path"));
        String result;

        try {
            Files.delete(path);
            result = "file " + path.getFileName() + " has been deleted";
        } catch (IOException e) {
            result = "this file can not be deleted";
        }

        req.getSession().setAttribute("message", "[" + DateFormat.getDateTimeInstance().format(new Date()) + "] " + result);
        resp.sendRedirect("/dir/view");
    }

}
