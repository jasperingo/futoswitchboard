<%@page import="switchboard.models.Staff"%>
<%@page import="java.util.List"%>
<%@page import="switchboard.models.Department"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    
    <jsp:include page="res/includes/head.jsp">
        <jsp:param name="title" value="Staff | FUTOSwitchBoard" />
    </jsp:include>

    <body>
        
        <jsp:include page="res/includes/dashboard_header.jsp" />
        
        <main>
            
            <div class="container-fluid">
                
                <jsp:include page="res/includes/dashboard_nav.jsp" />
                
                <section class="dashboard-section fade-in mt-3 mt-md-0 px-md-4">
                        
                    <h3>Staff</h3>
                    
                    <% Staff staff = (Staff) request.getAttribute("staff"); %>
                    
                    <% if (staff != null) { %>
                    
                    <dl>
                        <div class="row my-3 border-bottom">
                            <div class="col-md-6">
                                <dt>First name</dt>
                                <dd class="fs-4"><%= staff.getFirstName() %></dd>
                            </div>
                            <div class="col-md-6">
                                <dt>Last name</dt>
                                <dd  class="fs-4"><%= staff.getLastName() %></dd>
                            </div>
                        </div>
                            
                        <div class="row  mb-3 border-bottom">
                            <div class="col-md-6">
                                <dt>Phone number</dt>
                                <dd><%= staff.getPhoneNumber() %></dd>
                            </div>
                            <div class="col-md-6">
                                <dt>Gender</dt>
                                <dd><%= staff.getGender()%></dd>
                            </div>
                        </div>
                            
                        <div class="row  mb-3 border-bottom">
                            <div class="col-md-6">
                                <dt>Extension code</dt>
                                <dd><%= staff.getStaffExtension().getExtension().getCode() %></dd>
                            </div>
                            <div class="col-md-6">
                                <dt>Extension is secured</dt>
                                <dd><%= staff.getStaffExtension().getExtension().isSecured()%></dd>
                            </div>
                        </div>
                        
                        <div class=" mb-3 border-bottom">
                            <dt>Department</dt>
                            <dd>
                                <a class="btn btn-primary"
                                    href="department?id=<%= staff.getStaffExtension().getExtension().getDepartment().getId() %>">
                                    <%= staff.getStaffExtension().getExtension().getDepartment().getName() %>
                                </a>
                            </dd>
                        </div>
                        
                    </dl>
                            
                    <% } else { %>
                        <div class="alert alert-danger my-5" role="alert">Staff do not exist</div>
                    <% } %>
                                        
                </section>
                    
                
            </div>
            
        </main>
      
    </body>

</html>





