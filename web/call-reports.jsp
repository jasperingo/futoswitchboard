<%@page import="switchboard.models.CallReport"%>
<%@page import="switchboard.models.Staff"%>
<%@page import="java.util.List"%>
<%@page import="switchboard.models.Department"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en">
    
    <jsp:include page="res/includes/head.jsp">
        <jsp:param name="title" value="Call Reports | FUTOSwitchBoard" />
    </jsp:include>

    <body>
        
        <jsp:include page="res/includes/dashboard_header.jsp" />
        
        <main>
            
            <div class="container-fluid">
                
                <jsp:include page="res/includes/dashboard_nav.jsp" />
                
                <section class="dashboard-section fade-in mt-3 mt-md-0 px-md-4">
                        
                    <h3>Call Reports</h3>
                    
                    <% List<CallReport> list = (List<CallReport>) request.getAttribute("call_reports"); %>
                    
                    <% if (list !=  null && !list.isEmpty()) { %>
                    
                    <ul class="list-unstyled">
                        
                        <% for (int i=0; i<list.size(); i++) { %>
                        <% CallReport rep = list.get(i); %>
                    
                        <li>
                            
                            <dl class="border rounded p-2">
                                
                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <dt class="d-inline-block">Call session ID:</dt>
                                        <dd class="d-inline-block"><%= rep.getSessionId() %></dd>
                                    </div>

                                    <div class="col-md-6 mb-2">
                                        <dt class="d-inline-block">Caller number:</dt>
                                        <dd class="d-inline-block"><%= rep.getCallerNumber() %></dd>
                                    </div>
                                </div>
                                
                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <dt class="d-inline-block">Staff name:</dt>
                                        <dd class="d-inline-block">
                                            <%= rep.getStaffExtension().getStaff().getFirstName() %>
                                            <%= rep.getStaffExtension().getStaff().getLastName() %>
                                        </dd>
                                    </div>

                                    <div class="col-md-6 mb-2">
                                        <dt class="d-inline-block">Staff number:</dt>
                                        <dd class="d-inline-block"><%= rep.getStaffExtension().getStaff().getPhoneNumber() %></dd>
                                    </div>
                                </div>
                                 
                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <dt class="d-inline-block">Staff extension code:</dt>
                                        <dd class="d-inline-block"><%= rep.getStaffExtension().getExtension().getCode() %></dd>
                                    </div>

                                    <div class="col-md-6 mb-2">
                                        <dt class="d-inline-block">Staff department :</dt>
                                        <dd class="d-inline-block"><%= rep.getStaffExtension().getExtension().getDepartment().getName() %></dd>
                                    </div>
                                </div>
                                
                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <dt class="d-inline-block">Call status:</dt>
                                        <dd class="d-inline-block"><%= rep.getStatus() %></dd>
                                    </div>
                                    
                                    <div class="col-md-6 mb-2">
                                        <dt class="d-inline-block">Started at:</dt>
                                        <dd class="d-inline-block"><%= rep.getStartedAt() %></dd>
                                    </div>
                                </div>
                                
                                <div class="row">
                                    <div class="col-md-6 mb-2">
                                        <dt class="d-inline-block">Call duration:</dt>
                                        <dd class="d-inline-block"><%= rep.getDuration() %>s</dd>
                                    </div>
                                    
                                    <div class="col-md-6 mb-2">
                                        <dt class="d-inline-block">Cost of call:</dt>
                                        <dd class="d-inline-block">N <%= rep.getCost() %></dd>
                                    </div>
                                </div>
                                
                                <div class="mb-2">
                                    <dt class="d-inline-block align-middle">Call record:</dt>
                                    <dd class="d-inline-block align-middle">
                                        <audio controls class="d-block">
                                            <source src="<%= rep.getRecordUrl() %>" type="audio/ogg" />
                                            <span>Your browser does not support the audio element.</span>
                                        </audio> 
                                    </dd>
                                </div>
                                
                            </dl>
                            
                        </li>
                        
                        <% } %>
                    
                    </ul>
                        
                    <% } else { %>
                    <div class="alert alert-danger my-5" role="alert">No call report</div>
                    <% } %>
                    
                    <jsp:include page="res/includes/pager.jsp">
                        <jsp:param name="url" value="call-reports?" />
                    </jsp:include>
                                   
                </section>
                    
                
            </div>
            
        </main>
      
    </body>

</html>





