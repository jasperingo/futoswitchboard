<%@page import="switchboard.utils.FlashMessage"%>
<%@page import="java.util.List"%>
<%@page import="switchboard.models.Department"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    
    <jsp:include page="res/includes/head.jsp">
        <jsp:param name="title" value="Add Staff | FUTOSwitchBoard" />
    </jsp:include>

    <body>
        
        <jsp:include page="res/includes/dashboard_header.jsp" />
        
        <main>
            
            <div class="container-fluid">
                
                <jsp:include page="res/includes/dashboard_nav.jsp" />
                
                <section class="dashboard-section fade-in mt-3 mt-md-0 px-md-4">
                    
                    <% FlashMessage data = new FlashMessage(session); %>
                    
                    <form method="POST" action="" class="main-form my-5 mx-auto p-4 rounded shadow">
                        
                        <h2 class="text-center text-green mb-3 ff-bold">Add Staff</h2>
                        
                        <% if (data.hasError()) { %>
                        <div class="alert alert-danger" role="alert">
                            <div><%= data.getFormError() %></div>
                            <div><%= data.getError("first_name") %></div>
                            <div><%= data.getError("last_name") %></div>
                            <div><%= data.getError("phone_number") %></div>
                            <div><%= data.getError("gender") %></div>
                            <div><%= data.getError("department") %></div>
                            <div><%= data.getError("pin") %></div>
                        </div>
                        <% } %>
                        
                        <% if (data.hasSuccess()) { %>
                        <div class="alert alert-success" role="alert"><%= data.getFormSuccess() %></div>
                        <% } %>

                        <div class="input-group mb-3">
                            <label class="visually-hidden" for="fname_input">First name</label>
                            <div class="input-group-text"><%@ include file="res/icon/user.svg" %></div>
                            <input type="text" class="form-control" id="fname_input" name="first_name" 
                                   placeholder="First name" value="<%= data.getData("first_name")%>" required />
                        </div>
                        
                        <div class="input-group mb-3">
                            <label class="visually-hidden" for="lname_input">Last name</label>
                            <div class="input-group-text"><%@ include file="res/icon/user.svg" %></div>
                            <input type="text" class="form-control" id="lname_input" name="last_name" 
                                   placeholder="Last name" value="<%= data.getData("last_name")%>" required />
                        </div>
                        
                        <div class="input-group mb-3">
                            <label class="visually-hidden" for="phone_number_input">Phone number</label>
                            <div class="input-group-text"><%@ include file="res/icon/phone.svg" %></div>
                            <input type="tel" class="form-control" id="phone_number_input" name="phone_number" 
                                   placeholder="Phone number" value="<%= data.getData("phone_number")%>" minlength="11" required />
                        </div>
                        
                        <fieldset class="input-group mb-3 border p-2">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="gender" id="male_input" value="male" checked="" />
                                <label class="form-check-label" for="male_input">Male</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="gender" id="female_input" value="female" />
                                <label class="form-check-label" for="female_input">Female</label>
                            </div>
                        </fieldset>
                        
                        <div class="input-group mb-3">
                            <label class="visually-hidden" for="department_input">Department</label>
                            <div class="input-group-text"><%@ include file="res/icon/building.svg" %></div>
                            <select class="form-control" id="department_input" name="department" required>
                                <option value="" selected="">--Department--</option>
                                <% List<Department> list = (List<Department>) request.getAttribute("departments"); %>
                                <% for (int i=0; i<list.size(); i++) { %>
                                <% Department dept = list.get(i); %>
                                    <option value="<%= dept.getId() %>"><%= dept.getName() %></option>
                                <% } %>
                            </select>
                        </div>
                        
                        <div class="input-group mb-3">
                            <label class="visually-hidden" for="pin_input">Pin</label>
                            <div class="input-group-text"><%@ include file="res/icon/lock.svg" %></div>
                            <input type="password" class="form-control" id="pin_input" name="pin" 
                                   placeholder="PIN" minlength="4" />
                        </div>
                        
                        <fieldset class="input-group mb-3 border p-2">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="1" id="minor_input" name="minor_extension">
                                <label class="form-check-label" for="minor_input">Personal extension code</label>
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



