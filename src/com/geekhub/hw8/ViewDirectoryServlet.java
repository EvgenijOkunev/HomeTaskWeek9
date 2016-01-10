package com.geekhub.hw8;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(value = "/dir/view", initParams = {
        @WebInitParam(name = "root", value = "D:\\")
})
public class ViewDirectoryServlet extends HttpServlet {

    private static Path ROOT_PATH;
    private static List<String> messages = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        ROOT_PATH = Paths.get(config.getInitParameter("root"));
        messages.add("[" + DateFormat.getDateTimeInstance().format(new Date()) + "] " + " launched. Enjoy ) ");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> directoryLinks = new HashMap<>();
        Map<String, String> fileLinks = new HashMap<>();

        String pathParam = (String) req.getSession().getAttribute("path");
        Path currentPath = (pathParam == null) ? ROOT_PATH : Paths.get(pathParam);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(currentPath)) {
            for (Path path : directoryStream) {
                String linkView = path.getName(path.getNameCount() - 1).toString();
                Path linkPath = path.toAbsolutePath();
                if (Files.isDirectory(linkPath, LinkOption.NOFOLLOW_LINKS)) {
                    directoryLinks.put(linkView, linkPath.toString());
                } else {
                    fileLinks.put(linkView, linkPath.toString());
                }
            }
        } catch (IOException e) {
            req.getSession().setAttribute("path", currentPath.getParent().toString());
            resp.sendRedirect("/dir/view");
            return;
        }

        req.getSession().setAttribute("path", currentPath.toString());
        req.setAttribute("backPath", (!currentPath.equals(ROOT_PATH)) ? currentPath.getParent().toString() : ROOT_PATH.toString());
        req.setAttribute("rootPath", ROOT_PATH.toString());
        req.setAttribute("directoryLinks", directoryLinks);
        req.setAttribute("fileLinks", fileLinks);

        Object message = req.getSession().getAttribute("message");
        if (message != null) {
            messages.add((String) message);
        }
        req.getSession().setAttribute("message", null);
        req.getSession().setAttribute("messages", messages);

        req.getRequestDispatcher("/WEB-INF/jsp/viewDirectory.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getSession().setAttribute("path", req.getParameter("path"));
        resp.sendRedirect("/dir/view");
    }

}
