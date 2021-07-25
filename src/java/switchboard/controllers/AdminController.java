
package switchboard.controllers;

import switchboard.database.AdministratorDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import switchboard.models.Administrator;
import switchboard.utils.FormData;


@WebServlet(name = "AdminController", urlPatterns = {"/login", "/logout"})
public class AdminController extends HttpServlet {
    
    private HttpServletRequest request;
    private HttpServletResponse response;
     
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        this.request = request;
        this.response = response;
        
        
        switch (request.getServletPath()) {
            
            case "/login" :
                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                break;
                
            case "/logout" :
                getServletContext().getRequestDispatcher("/logout.jsp").forward(request, response);
                break;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        this.request = request;
        this.response = response;
        
        switch (request.getServletPath()){
            case "/login":
                login();
                break;
                
            case "/logout":
                logout();
                break;
            
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + " 4 POST</h1>");
        }
    }
    
    private void login() throws ServletException, IOException {
        
        FormData formData = new FormData();
        
        String phone = request.getParameter("phone_number");
        String password = request.getParameter("password");
        
        if (phone == null || phone.length() != 11 || password == null || password.length() < 6) {
            formData.setFormError("Phone number or password is incorrect");
            
        }
        
        if (!formData.hasErrors()) {
            
            Administrator user = AdministratorDB.findByPhoneNumber(phone);
            
            if (user == null) {
                
                formData.setFormError("An error occured try again");
                
            } else if (user.getId() == 0 || !user.getPassword().equals(password)) {
                
                formData.setFormError("Phone number or password is incorrect");
                
            } else {
                
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("departments");
                return;
            }
        }
        
        formData.putData("phone_number", phone);
        HttpSession session = request.getSession();
        session.setAttribute("form_data", formData);
        response.sendRedirect("login");
        
    }

    private void logout() throws IOException {
        
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("login");
    }

    
}





