package CPTEST;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/todos")
public class ListTodoServlet extends HttpServlet {

    private TodoDao dao;

    @Override
    public void init() {
        dao = new TodoDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String search = request.getParameter("search");

        if (search != null && !search.isEmpty()) {
            request.setAttribute("todoItems", dao.findByTitle(search));
            request.setAttribute("searchTerm", search);
        } else {
            request.setAttribute("todoItems", dao.getAll());
        }

        request.setAttribute("totalMissions", dao.countAll());
        request.setAttribute("criticalMissions", dao.countByPriority("Vault Emergency"));
        request.setAttribute("completedMissions", dao.countByStatus("Completed"));

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
} // End