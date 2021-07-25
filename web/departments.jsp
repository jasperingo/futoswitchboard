<%@page import="java.util.List"%>
<%@page import="switchboard.models.Department"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    
    <jsp:include page="res/includes/head.jsp">
        <jsp:param name="title" value="Departments | FUTOSwitchBoard" />
    </jsp:include>

    <body>
        
        <jsp:include page="res/includes/dashboard_header.jsp" />
        
        <main>
            
            <div class="container-fluid">
                
                <jsp:include page="res/includes/dashboard_nav.jsp" />
                
                <section class="dashboard-section fade-in mt-3 mt-md-0 px-md-4">

                    <h3>Departments</h3>
                    
                    <% List<Department> list = (List<Department>) request.getAttribute("departments"); %>
                    
                    <% if (!list.isEmpty()) { %>
                    
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Name</th>
                                <th scope="col">Extension Prefix</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        
                        
                        <tbody>
                            <% for (int i=0; i<list.size(); i++) { %>
                            <% Department dept = list.get(i); %>
                            <tr>
                                <th scope="row"><%= dept.getId() %></th>
                                <td><%= dept.getName() %></td>
                                <td><%= dept.getPrefix()%></td>
                                <td>
                                    <a href="department?id=<%= dept.getId() %>">View</a>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    
                    <% } else { %>
                    <div class="alert alert-danger my-5" role="alert">No department</div>
                    <% } %>
                    
                    <jsp:include page="res/includes/pager.jsp">
                        <jsp:param name="url" value="departments?" />
                    </jsp:include>
                    
                </section>
                    
                
            </div>
            
        </main>
      
    </body>

</html>






