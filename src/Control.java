import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user")
public class Control extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String viewPath = "/WEB-INF/pages/user/showUserForm.jsp";
		request.getRequestDispatcher(viewPath).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Departement departement = new Departement();
        departement.setId(request.getParameter("id"));
        departement.setName(request.getParameter("name"));
		departement.setMainCity(request.getParameter("mainCity"));

        if (departement.checkVeracity()) {
            // Congratulations message, new departement?
        } else {
            // wrong answers, try again
        }

		// request.setAttribute("user", user);
		// String viewPath = "/WEB-INF/pages/user/showUser.jsp";
		// request.getRequestDispatcher(viewPath).forward(request, response);
	}
}