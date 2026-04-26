package CPTEST;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/updateStatus")
public class UpdateStatusServlet extends HttpServlet {

    private final TodoDao dao = new TodoDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idValue = request.getParameter("id");
        String status = request.getParameter("status");

        if (idValue != null && status != null) {
            long id = Long.parseLong(idValue);
            dao.updateStatus(id, status);
        }

        response.sendRedirect("todos");
    }
}