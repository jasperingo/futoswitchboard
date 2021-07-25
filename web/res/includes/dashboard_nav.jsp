

<%@page import="switchboard.models.Administrator"%>

<% Administrator admin = (Administrator) session.getAttribute("user"); %>

<nav class="dashboard-nav">
    
    <div class="text-center">
        <img alt="avatar" src="res/img/user.png" width="80" height="80" class="m-auto my-md-4 d-inline-block d-md-block" />
        <div class="fw-bold mx-2 fs-4 d-inline-block d-md-block">
            <%= admin.getFirstName() %> 
            <%= admin.getLastName() %> 
        </div>
    </div>
    
    <ul class="m-0 p-0 pe-md-4 list-unstyled">
         
        <li>
            <a href="departments" class="nav-slide-in-left list-group-item my-4 rounded-end 
               <%= request.getServletPath().equals("/departments.jsp") ? "list-group-item-success" : "" %>">
                <span>Departments</span>
            </a>
        </li>
        
        <li>
            <a href="add-staff" class="nav-slide-in-leftx list-group-item my-4 rounded-end 
               <%= request.getServletPath().equals("/add-staff.jsp") ? "list-group-item-success" : "" %>">
                <span>Add staff</span>
            </a>
        </li>
        
        <li>
            <a href="add-department" class="nav-slide-in-left list-group-item my-4 rounded-end 
               <%= request.getServletPath().equals("/add-department.jsp") ? "list-group-item-success" : "" %>">
                <span>Add department</span>
            </a>
        </li>

        <li>
            <a href="call-reports.jsp" class="nav-slide-in-leftx list-group-item my-4 rounded-end 
               <%= request.getServletPath().equals("/call-reports.jsp") ? "list-group-item-success" : "" %>">
                <span>Call reports</span>
            </a>
        </li>

        <li>
            <a href="recordings.jsp" class="nav-slide-in-left list-group-item my-4 rounded-end 
               <%= request.getServletPath().equals("/recordings.jsp") ? "list-group-item-success" : "" %>">
                <span>Recordings</span>
            </a>
        </li>

        <li>
            <a href="logout" class="nav-slide-in-leftx list-group-item list-group-item-danger my-4 rounded-end">
                <span>Log out</span>
            </a>
        </li>
    </ul>
</nav>
           
                
                