<%@page import="switchboard.utils.FlashMessage"%>
<%@page import="java.util.List"%>
<%@page import="switchboard.models.Department"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    
    <jsp:include page="res/includes/head.jsp">
        <jsp:param name="title" value="Logout | FUTOSwitchBoard" />
    </jsp:include>

    <body>
        
        <jsp:include page="res/includes/dashboard_header.jsp" />
        
        <main>
            
            <div class="container-fluid">
                
                <jsp:include page="res/includes/dashboard_nav.jsp" />
                
                <section class="dashboard-section fade-in mt-3 mt-md-0 px-md-4">
                    
                    <div class="main-form my-5 mx-auto">
                        
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Log out</h5>
                                <p class="card-text">Are you sure you want to log out?</p>
                                <a href="#" class="card-link btn btn-primary">NO</a>
                                <form class="ms-4 d-inline-block"method="POST" action="">
                                   <button class="btn btn-danger">YES</button>
                                </form>
                            </div>
                        </div>
                        
                    </div>
                    
                </section>
                
                
            </div>
            
        </main>
      
    </body>

</html>



