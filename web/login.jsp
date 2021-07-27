
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="switchboard.utils.FlashMessage" %>
<!DOCTYPE html>

<html lang="en">
    
    <jsp:include page="res/includes/head.jsp">
        <jsp:param name="title" value="Login | FUTOSwitchBoard" />
    </jsp:include>
    
    <body>
        
        <jsp:include page="res/includes/header.jsp" />
        
        <% FlashMessage data = new FlashMessage(session); %>
        
        <main>
            
            <div class="container">
                
                <form method="POST" action="login" class="main-form scale-upx my-5 mx-auto p-4 rounded shadow">

                    <h2 class="text-center text-green mb-3 ff-bold">Login</h2>
                    
                    <% if (data.hasError()) { %>
                    <div class="alert alert-danger" role="alert"><%= data.getFormError() %></div>
                    <% } %>
                    
                    <div class="input-group mb-3">
                        <label class="visually-hidden" for="phone_number_input">Phone number</label>
                        <div class="input-group-text"><%@ include file="res/icon/phone.svg" %></div>
                        <input type="tel" class="form-control" id="phone_number_input" name="phone_number" placeholder="Phone number" 
                               value="<%= data.getData("phone_number")%>" minlength="11" required />
                    </div>
                    
                    <div class="input-group mb-3">
                        <label class="visually-hidden" for="password_input">Password</label>
                        <div class="input-group-text"><%@ include file="res/icon/lock.svg" %></div>
                        <input type="password" class="form-control" id="password_input" name="password" placeholder="Password" minlength="6" required />
                    </div>
                    
                    <div class="mb-3">
                        <button type="submit" class="btn bg-green text-white w-100">Login</button>
                    </div>
                    
                </form>
                
            </div>
            
        </main>
        
        
    </body>
    
</html>



