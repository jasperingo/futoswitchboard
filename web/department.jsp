<%@page import="switchboard.models.Staff"%>
<%@page import="java.util.List"%>
<%@page import="switchboard.models.Department"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    
    <jsp:include page="res/includes/head.jsp">
        <jsp:param name="title" value="Department | FUTOSwitchBoard" />
    </jsp:include>

    <body>
        
        <jsp:include page="res/includes/dashboard_header.jsp" />
        
        <main>
            
            <div class="container-fluid">
                
                <jsp:include page="res/includes/dashboard_nav.jsp" />
                
                <section class="dashboard-section fade-in mt-3 mt-md-0 px-md-4">
                        
                    <h3>Department</h3>
                    
                    <% Department d = (Department) request.getAttribute("department"); %>
                    
                    <% if (d != null) { %>
                    
                        <h2 class="fs-1 fw-bold"><%= d.getName() %></h2>

                        <h2 class="border-bottom">Prefix: <%= d.getPrefix()%></h2>


                        <% List<Staff> list = (List<Staff>) request.getAttribute("staffs"); %>

                        <% if (!list.isEmpty()) { %>
                            <h5 class="mt-4 text-green">Department staffs</h5>
                            <ul class="list-group my-3">
                                <% for (int i=0; i<list.size(); i++) { %>
                                    <% Staff staff = list.get(i); %>
                                    <li class="list-group-item">
                                        <a href="staff?id=<%= staff.getId() %>" class="list-group-item list-group-item-action fs-4">
                                            <%= staff.getFirstName() %> <%= staff.getLastName() %>
                                        </a>
                                    </li>
                                <% } %>
                            </ul>
                        <% } else { %>
                            <div class="alert alert-danger my-5" role="alert">No staff in this department</div>
                        <% } %>

                        <% String url = "department?id="+request.getParameter("id")+"&"; %>

                        <jsp:include page="res/includes/pager.jsp">
                            <jsp:param name="url" value="<%= url %>" />
                        </jsp:include>
                        
                    <% } else { %>
                        
                        <div class="alert alert-danger my-5" role="alert">Department do not exist</div>
                        
                    <% } %>
                    
                </section>
                    
                
            </div>
            
        </main>
      
    </body>

</html>





