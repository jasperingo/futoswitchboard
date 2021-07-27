
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    
    <jsp:include page="/res/includes/head.jsp">
        <jsp:param name="title" value="FUTO SwitchBoard" />
    </jsp:include>
    
    <body>
        
        <jsp:include page="res/includes/header.jsp" />
        
        <main>
            
            <section class="container">
                
                <div class="scale-up text-center">
                    <h2 class="mt-5 fw-bold text-green">Welcome to FUTOSwitchBoard, A Privacy-Enabled Call System</h2>
                </div>
                
                <div class="my-5">
                    <div class="row">
                       
                        <div class="slide-in-left col-md-6">
                            <h3 class="text-green">Privacy and Security is our Aim</h3>
                            <p class="page-font">
                                FUTO SwitchBoard is a system that provides call privacy and security for both staff and student. 
                                It provides several beneficial services and is very easy to use.<br />
                                We are Particular about the Future and we stand for Integrity. <br />
                                Your privacy is your right.
                            </p>
                            <a class="btn text-white my-5 bg-green" href="login">Login</a> 
                        </div>
                        
                        <div class="slide-in-right mt-5 mt-md-auto col-md-6">
                            <img src="res/icon/call-center.svg" alt="An image of happy people" class="index-img d-block w-100" width="300" height="300" />
                        </div>
                    
                    </div>
                </div>
                    
                
            </section>
            
        </main>
        
    </body>
    
</html>



