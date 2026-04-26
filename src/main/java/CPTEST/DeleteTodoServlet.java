package CPTEST;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete")
public class DeleteTodoServlet extends HttpServlet {

    private TodoDao dao;

    @Override
    public void init() {
        dao = new TodoDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                long id = Long.parseLong(idParam);
                dao.removeById(id);
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID: " + idParam);
            }
        }

        response.sendRedirect("todos");
    }
} // End
