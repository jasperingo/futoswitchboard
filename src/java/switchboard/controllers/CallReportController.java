
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
import switchboard.database.CallReportDB;
import switchboard.database.DepartmentDB;
import switchboard.database.StaffDB;
import switchboard.filters.PaginationFilter;
import switchboard.models.CallReport;
import switchboard.models.Department;
import switchboard.models.Staff;
import switchboard.utils.FormData;



@WebServlet(name = "CallReportController", urlPatterns = {"/call-reports"})
public class CallReportController extends HttpServlet {

    
    private HttpServletRequest request;
    private HttpServletResponse response;
     
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        this.request = request;
        this.response = response;
        
        
        switch (request.getServletPath()) {
                
            case "/call-reports" : 
                sendListView();
                break;
                
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
        }
    }
    
    private void sendListView() throws ServletException, IOException {
        
        List<Integer> pager = (List<Integer>) request.getAttribute("pager");
        
        List<CallReport> list = CallReportDB.findAll(PaginationFilter.LIMIT, pager.get(1));
       
        int listCount = CallReportDB.countAll();
         
        request.setAttribute("call_reports", list);
        
        request.setAttribute("data_count", listCount);
        
        getServletContext().getRequestDispatcher("/call-reports.jsp").forward(request, response);
    }
   
    
   
    

}


