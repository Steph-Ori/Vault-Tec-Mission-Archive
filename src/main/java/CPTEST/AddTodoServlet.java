package CPTEST;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/add")
public class AddTodoServlet extends HttpServlet {

    private TodoDao dao;

    @Override
    public void init() {
        dao = new TodoDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String category = request.getParameter("category");
        String priority = request.getParameter("priority");

        if (title != null && !title.trim().isEmpty()) {
            dao.add(title.trim(), category, priority);
        }

        response.sendRedirect("todos");
    }
} // End