
package switchboard.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import switchboard.database.DepartmentDB;
import switchboard.database.StaffDB;
import switchboard.filters.PaginationFilter;
import switchboard.models.Department;
import switchboard.models.Staff;
import switchboard.utils.FormData;


@WebServlet(name = "DepartmentController", urlPatterns = {"/departments", "/add-department", "/department"})
public class DepartmentController extends HttpServlet {

    private HttpServletRequest request;
    private HttpServletResponse response;
     
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        this.request = request;
        this.response = response;
        
        
        switch (request.getServletPath()) {
                
            case "/departments" : 
                sendListView();
                break;
                
            case "/department" : 
                sendGetView();
                break;
                
            case "/add-department":
                getServletContext().getRequestDispatcher("/add-department.jsp").forward(request, response);
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
            case "/add-department":
                add();
                break;
            
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + " 4 POST</h1>");
        }
    }
    
    
    
    private void sendListView() throws ServletException, IOException {
        
        List<Integer> pager = (List<Integer>) request.getAttribute("pager");
        
        List<Department> list = DepartmentDB.findAll(PaginationFilter.LIMIT, pager.get(1));
        
        int listCount = DepartmentDB.countAll();
        
        request.setAttribute("departments", list);
        
        request.setAttribute("data_count", listCount);
        
        getServletContext().getRequestDispatcher("/departments.jsp").forward(request, response);
    }
    
    
    private void sendGetView() throws IOException, ServletException {
        
        String deptId =  request.getParameter("id");
        
        List<Integer> pager = (List<Integer>) request.getAttribute("pager");
        
        Department dept = DepartmentDB.find(Long.valueOf(deptId));
        
        List<Staff> list = StaffDB.findAllByDepartment(Long.valueOf(deptId), PaginationFilter.LIMIT, pager.get(1));
        
        int listCount = StaffDB.countAllByDepartment(Long.valueOf(deptId));
        
        request.setAttribute("department", dept);
        
        request.setAttribute("staffs", list);
        
        request.setAttribute("data_count", listCount);
        
        getServletContext().getRequestDispatcher("/department.jsp").forward(request, response);
    }
    
    
    private void add() throws IOException {
        
        FormData formData = new FormData();
        
        String name = request.getParameter("name");
        String prefix = request.getParameter("prefix");
        String minor = request.getParameter("minor");
        
        if (name == null || name.isEmpty()) {
            formData.putError("name", "Name field is invalid");
        }
        
        if (prefix == null || prefix.length() != 3) {
            formData.putError("prefix", "Prefix field is invalid");
        }
        
        if (!formData.hasError("name")) {
            
            long deptId = DepartmentDB.findIdByName(name);
            
            if (deptId > 0) {
                formData.putError("name", "Department exists");
            } else if (deptId < 0) {
                formData.setFormError("An error occured try again");
            }
        }
        
        if (!formData.hasError("prefix")) {
            
            long deptId = DepartmentDB.findIdByPrefix(prefix);
            
            if (deptId > 0) {
                formData.putError("prefix", "Extension exists");
            } else if (deptId < 0) {
                formData.setFormError("An error occured try again");
            }
        }
        
        if (formData.hasErrors()) {
            formData.putData("name", name);
            formData.putData("prefix", prefix);
            HttpSession session = request.getSession();
            session.setAttribute("form_data", formData);
            response.sendRedirect("add-department");
            return;
        }
        
        Department d = new Department();
        d.setName(name);
        d.setPrefix(prefix);
        d.setMinor(minor != null);
        
        if (!DepartmentDB.insert(d)) {
            formData.setFormError("An error occured try again");
            formData.putData("name", name);
            formData.putData("prefix", prefix);
            HttpSession session = request.getSession();
            session.setAttribute("form_data", formData);
            response.sendRedirect("add-department");
            return;
        }
        
        
        formData.setFormSuccess("Department added");
        HttpSession session = request.getSession();
        session.setAttribute("form_data", formData);
        response.sendRedirect("add-department");
        
    }
    
    
}




