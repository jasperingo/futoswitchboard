
<%@ page isErrorPage="true" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <jsp:include page="res/includes/head.jsp">
        <jsp:param name="title" value="Error page | FUTOSwitchBoard" />
    </jsp:include>
    
    <body>
        
        <header class="py-3 ff-bold">
            <div class="container">
                <a class="d-inline-block align-middle" href="<%= request.getContextPath() %>">
                    <img src="res/img/logo.jpg" width="50" height="50" alt="The website's and FUTO's logo" class="bg-white d-block" />
                </a>
                <div class="d-inline-block align-middle ms-2">
                    <h1 class="text-truncate text-green">FUTOSwitchBoard</h1>
                </div>
            </div>
        </header>
        
        <main>
            
            <div class="container">
                
                <h2 class="text-danger fw-bold mt-4">Oops! <%= request.getAttribute("javax.servlet.error.status_code")%> error occurred</h2>
                
		<h3 class="mt-4"> At this url: <%= request.getAttribute("javax.servlet.error.request_uri")%></h3>
                
            </div>
            
        </main>
        
        
    </body>
</html>
