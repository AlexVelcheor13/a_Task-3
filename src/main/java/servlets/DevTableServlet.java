package servlets;

import dao.DeveloperDao;
import model.Developer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/")
public class DevTableServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DeveloperDao developerDao;

    @Override
    public void init() throws ServletException {
        developerDao = new DeveloperDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/insert":
                    insertDeveloper(req, resp);
                    break;
                case "/delete":
                    deleteDeveloper(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/update":
                    updateDeveloper(req, resp);
                    break;
                default:
                    listDeveloper(req, resp);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void listDeveloper(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Developer> listDeveloper = developerDao.selectAllDevelopers();
        request.setAttribute("listDeveloper", listDeveloper);
        RequestDispatcher dispatcher = request.getRequestDispatcher("developer-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("developer-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Developer existingDeveloper = developerDao.selectDeveloper(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("developer-form.jsp");
        request.setAttribute("developer", existingDeveloper);
        dispatcher.forward(request, response);

    }

    private void insertDeveloper(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String specialty = request.getParameter("specialty");
        int salary = Integer.parseInt(request.getParameter("salary"));
        Developer newDeveloper = new Developer(name, specialty, salary);
        developerDao.insertDeveloper(newDeveloper);
        response.sendRedirect("list");
    }

    private void updateDeveloper(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String specialty = request.getParameter("specialty");
        int salary = Integer.parseInt(request.getParameter("salary"));

        Developer developer = new Developer(id, name, specialty, salary);
        developerDao.updateDeveloper(developer);
        response.sendRedirect("list");
    }

    private void deleteDeveloper(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        developerDao.deleteDeveloper(id);
        response.sendRedirect("list");

    }

    @Override
    public void destroy() {
        System.out.println("Destroy method");
    }

}
