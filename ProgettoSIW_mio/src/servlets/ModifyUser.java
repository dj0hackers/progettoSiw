package servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;
import persistence.DBManager;

public class ModifyUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = (String) request.getSession().getAttribute("email");
		System.out.println("modifica " + email);
		if (email == null) {
			response.sendRedirect("login.jsp");
		} else {
			doPost(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = DBManager.getInstance().getUserDAO()
				.findByPrimaryKey((String) request.getSession().getAttribute("email"));

		if (request.getParameterNames().hasMoreElements()) {
			String jsonString = request.getParameter("toModify");
			if (jsonString != null) {
				System.out.println("jsonString " + jsonString);
				ObjectMapper mapper = new ObjectMapper();
				User toModify = (User) mapper.readValue(jsonString, User.class);
				update(toModify);

				Enumeration<String> attributes = request.getSession().getAttributeNames();

				removeAttributes(attributes, request.getSession());
				addNewAttributes(toModify, request.getSession());

				mapper.writeValue(response.getOutputStream(), toModify);
				
//				response.sendRedirect("login.jsp");
			}
		} else {
			request.getSession().removeAttribute("update");
			request.getSession().setAttribute("name", user.getName());
			request.getSession().setAttribute("surname", user.getSurname());
			request.getSession().setAttribute("email", user.getEmail());
			request.getSession().setAttribute("phone", user.getPhone());
			request.getSession().setAttribute("address", user.getAddress());
			request.getSession().setAttribute("password", user.getPassword());
			request.getSession().setAttribute("seller", user.isSeller());
			response.sendRedirect("account.jsp");
		}
	}

	private void addNewAttributes(User toModify, HttpSession session) {
		session.setAttribute("update", "ok");
		session.setAttribute("name", toModify.getName());
		session.setAttribute("surname", toModify.getSurname());
		session.setAttribute("email", toModify.getEmail());
		session.setAttribute("phone", toModify.getPhone());
		session.setAttribute("address", toModify.getAddress());
		session.setAttribute("password", toModify.getPassword());
		session.setAttribute("seller", toModify.isSeller());

	}

	private void removeAttributes(Enumeration<String> attributes, HttpSession session) {
		session.removeAttribute("update");
		while (attributes.hasMoreElements()) {
			String attribute = attributes.nextElement();
			session.removeAttribute(attribute);
		}

	}

	private void update(User user) {
		DBManager.getInstance().getUserDAO().update(user);
	}

}
