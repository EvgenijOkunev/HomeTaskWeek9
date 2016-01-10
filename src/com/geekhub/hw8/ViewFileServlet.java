package com.geekhub.hw8;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/file/view")
public class ViewFileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Path path = Paths.get(req.getParameter("path"));

        List<String> fileLines = new ArrayList<>();
        String exception = "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path.toString()), "windows-1251"))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                fileLines.add(currentLine);
            }
        } catch (IOException e) {
            exception = e.getMessage();
        }

        if (exception.equals("")) {
            req.setAttribute("backPath", path.getParent().toString());
            req.setAttribute("fileName", path.getFileName());
            req.setAttribute("fileLines", fileLines);
            req.getSession().setAttribute("message", "[" + DateFormat.getDateTimeInstance().format(new Date()) + "] file " + path.getFileName() + " has been opened");
            req.getRequestDispatcher("/WEB-INF/jsp/viewFile.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("message", "[" + DateFormat.getDateTimeInstance().format(new Date()) + "] " + exception);
            resp.sendRedirect("/dir/view");
        }
    }

}
