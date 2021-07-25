<%@page import="switchboard.utils.FlashMessage"%>
<%@page import="java.util.List"%>
<%@page import="switchboard.models.Department"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    
    <jsp:include page="res/includes/head.jsp">
        <jsp:param name="title" value="Add department | FUTOSwitchBoard" />
    </jsp:include>

    <body>
        
        <jsp:include page="res/includes/dashboard_header.jsp" />
        
        <main>
            
            <div class="container-fluid">
                
                <jsp:include page="res/includes/dashboard_nav.jsp" />
                
                <section class="dashboard-section fade-in mt-3 mt-md-0 px-md-4">
                    
                    <% FlashMessage data = new FlashMessage(session); %>
                    
                    <form method="POST" action="" class="main-form my-5 mx-auto p-4 rounded shadow">
                        
                        <h2 class="text-center text-green mb-3 ff-bold">Add Department</h2>
                        
                        <% if (data.hasError()) { %>
                        <div class="alert alert-danger" role="alert">
                            <div><%= data.getFormError() %></div>
                            <div><%= data.getError("name") %></div>
                            <div><%= data.getError("prefix") %></div>
                        </div>
                        <% } %>
                        
                        <% if (data.hasSuccess()) { %>
                        <div class="alert alert-success" role="alert"><%= data.getFormSuccess() %></div>
                        <% } %>

                        <div class="input-group mb-3">
                            <label class="visually-hidden" for="name_input">Department name</label>
                            <div class="input-group-text"><%@ include file="res/icon/building.svg" %></div>
                            <input type="text" class="form-control" id="name_input" name="name" 
                                   placeholder="Department name" value="<%= data.getData("name")%>" required />
                        </div>
                        
                        <div class="input-group mb-3">
                            <label class="visually-hidden" for="prefix_input">Extension prefix</label>
                            <div class="input-group-text"><%@ include file="res/icon/phone.svg" %></div>
                            <input type="tel" class="form-control" id="prefix_input" name="prefix" 
                                   placeholder="Extension prefix" value="<%= data.getData("prefix")%>" minlength="3" required />
                        </div>
                        
                        <fieldset class="input-group mb-3 border p-2">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="1" id="minor_input" name="minor">
                                <label class="form-check-label" for="minor_input">Is a minor department</label>
                            </div>
                        </fieldset>

                        <div class="mb-3">
                            <button type="submit" class="btn bg-green text-white w-100">Submit</button>
                        </div>

                    </form>
                    
                    
                </section>
                    
                
            </div>
            
        </main>
      
    </body>

</html>






