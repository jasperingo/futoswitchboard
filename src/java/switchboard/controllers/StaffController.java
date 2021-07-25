
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
import switchboard.database.ExtensionDB;
import switchboard.database.StaffDB;
import switchboard.filters.PaginationFilter;
import switchboard.models.Department;
import switchboard.models.Extension;
import switchboard.models.Staff;
import switchboard.utils.FormData;


@WebServlet(name = "StaffController", urlPatterns = {"/add-staff", "/staff"})
public class StaffController extends HttpServlet {

    private HttpServletRequest request;
    private HttpServletResponse response;
     
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        this.request = request;
        this.response = response;
        
        
        switch (request.getServletPath()) {
                
            case "/staff" : 
                sendGetView();
                break;
                
            case "/add-staff":
                sendAddView();
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
            case "/add-staff":
                add();
                break;
            
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + " 4 POST</h1>");
        }
    }
    
    
    private void sendGetView() throws ServletException, IOException {
        
        String staffId =  request.getParameter("id");
        
        Staff staff = StaffDB.find(Long.valueOf(staffId));
        
        request.setAttribute("staff", staff);
        
        getServletContext().getRequestDispatcher("/staff.jsp").forward(request, response);
    }
    
    
    private void sendAddView() throws ServletException, IOException {
        
        List<Department> list = DepartmentDB.findAll();
        
        request.setAttribute("departments", list);
        
        getServletContext().getRequestDispatcher("/add-staff.jsp").forward(request, response);
    }
    
    
    private void add() throws IOException {
        
        FormData formData = new FormData();
        
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String phoneNumber = request.getParameter("phone_number");
        String gender = request.getParameter("gender");
        String department = request.getParameter("department");
        String pin = request.getParameter("pin");
        String minorExtension = request.getParameter("minor_extension");
        
        formData.putData("first_name", firstName);
        formData.putData("last_name", lastName);
        formData.putData("phone_number", phoneNumber);
        
        if (firstName == null || firstName.isEmpty()) {
            formData.putError("first_name", "First name field is invalid");
        }
        
        if (lastName == null || lastName.isEmpty()) {
            formData.putError("last_name", "Last name field is invalid");
        }
        
        if (phoneNumber == null || phoneNumber.length() != 11) {
            formData.putError("phone_number", "Phone number field is invalid");
        }
        
        if (gender == null || (!gender.equals(Staff.GENDER_MALE) && !gender.equals(Staff.GENDER_FEMALE))) {
            formData.putError("gender", "Gender field is invalid");
        }
        
        if (department == null || department.isEmpty()) {
            formData.putError("department", "Department field is invalid");
        }
        
        if (pin != null && !pin.isEmpty() && pin.length() != 4) {
            formData.putError("pin", "Pin field is invalid");
        }
        
        if (!formData.hasError("phone_number")) {
            
            long phoneId = StaffDB.findIdByPhoneNumber(phoneNumber);
            
            if (phoneId > 0) {
                formData.putError("phone_number", "Phone number exists");
            } else if (phoneId < 0) {
                formData.setFormError("An error occured try again");
            }
        }
        
        Department dept = DepartmentDB.find(Long.valueOf(department));
        
        if (!formData.hasError("department")) {
            
            if (dept == null) {
                formData.setFormError("An error occured try again");
            } else if (dept.getId() < 1) {
                formData.putError("department", "Department do not exists");
            }
        }
        
        if (formData.hasErrors()) {
            sendAddError(formData);
            return;
        }
        
        Staff staff = new Staff();
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        staff.setGender(gender);
        staff.setPhoneNumber(phoneNumber);
            
        if (dept != null && (!dept.isMinor() || (dept.isMinor() && minorExtension != null))) {
            
            String extensionLastCode = ExtensionDB.lastExtensionOfDepartment(dept.getId());
            
            if (extensionLastCode == null) {
                formData.setFormError("An error occured try again");
                sendAddError(formData);
                return;
            }
            
            int newCode = extensionLastCode.isEmpty() || extensionLastCode.substring(3).isEmpty() ? 0 : 
                    Integer.parseInt(extensionLastCode.substring(3))+1;
            
            String newCodeString = newCode < 10 ? dept.getPrefix()+"0"+String.valueOf(newCode) 
                    : dept.getPrefix()+String.valueOf(newCode);
            
            Extension ext = new Extension();
            ext.setDepartment(dept);
            ext.setCode(newCodeString);
            ext.setPin(pin);
            ext.setSecured(pin != null && !pin.isEmpty());
            
            if (!StaffDB.insert(staff, ext)) {
                formData.setFormError("An error occured try again");
                sendAddError(formData);
                return;
            }
            
            
        } else if (dept != null && (dept.isMinor())) {
            
            long extensionId = ExtensionDB.findIdByDepartment(dept.getId());
            
            if (extensionId < 0) {
                formData.setFormError("An error occured try again");
                sendAddError(formData);
                return;
            }
            
            if (!StaffDB.insert(staff, extensionId)) {
                formData.setFormError("An error occured try again");
                sendAddError(formData);
                return;
            }
            
        }
        
        FormData fd = new FormData();
        fd.setFormSuccess("Staff added");
        sendAddError(fd);
        
    }
    
    private void sendAddError(FormData formData) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("form_data", formData);
        response.sendRedirect("add-staff");
    }
    
    

}

