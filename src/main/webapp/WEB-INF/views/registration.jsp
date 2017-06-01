<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>User Registration Form</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
</head>

<script type="text/javascript">
    
    $(document).submit(function(event){
        
        $('#createUser').submit()(function(event){
            var id = $('#id').val();
            var firstName = $('#firstName').val();
            var lastName = $('#lastName').val();
            var userName = $('#userName').val();
            var password = $('#password').val();
            var email = $('#email').val();
            var userProfile = $('#userProfile').val();
            
            var json = {
                "id": id,
                "firstName" : firstName,
                "lastName" : lastName,
                "username" : userName,
                "password": password,
                "email": email,
                "userProfile": userProfile
                };
                
            $.ajax({
               url: $('#createUser').attr("action"),
               data: JSON.stringify(json),
               type: POST,
               
               beforeSend: function (xhr) {
                        xhr.setRequestHeader("Accept", "application/json");
                        xhr.setRequestHeader("Content-Type", "application/json");
                        $(".error").remove();
               },               
            
               success: function (user) {
                        var respContent = "";
                        
                respContent += "<span class='success'>User was created: [";
                respContent += user.firstName + " : ";
                respContent += user.lastName + "]</span>";

                $("#sUserFromResponse").html(respContent);
               },
               error: function(jqXHR, textStatus, errorThrown) {
                   var respBody = $parseJSON(jqXHR.responseText);
                   var respContent = "";
                   
                   respContent += "<span class='error-main'";
                   respContent += resBody.message;
                   respContent += "</span>";
                   
                   $("#sUserFromResponse").html(respContent);
                   
                   $.each(respBody.fieldErrors, function(index, errEntity){
                      var tdEl = $("."+errEntity.fieldName+"-info");
                      tdEl.html("<span class\"error\">"+ errEntity.fieldError+"</span>");
                   });
    
               }
               
            });
            
            event.preventDefault();
            
        });
    });
    
    
</script>

<body>
 	<div class="generic-container">
		<%@include file="authheader.jsp" %>

		<div class="well lead">User Registration Form</div>
                <div id="sUserFromResponse"></div>
                <form:form method="POST" modelAttribute="user" action="${pageContext.request.contextPath}/newuser" class="form-horizontal" id="createUser">
			<form:input type="hidden" path="id" id="id"/>
			
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="firstName">First Name</label>
					<div class="col-md-7">
						<form:input type="text" path="firstName" id="firstName" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="firstName" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="lastName">Last Name</label>
					<div class="col-md-7">
						<form:input type="text" path="lastName" id="lastName" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="lastName" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="userName">userName</label>
					<div class="col-md-7">
						<c:choose>
							<c:when test="${edit}">
								<form:input type="text" path="userName" id="userName" class="form-control input-sm" disabled="true"/>
							</c:when>
							<c:otherwise>
								<form:input type="text" path="userName" id="userName" class="form-control input-sm" />
								<div class="has-error">
									<form:errors path="userName" class="help-inline"/>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="password">Password</label>
					<div class="col-md-7">
						<form:input type="password" path="password" id="password" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="password" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="email">Email</label>
					<div class="col-md-7">
						<form:input type="text" path="email" id="email" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="email" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="userProfile">Roles</label>
					<div class="col-md-7">
						<form:select path="userProfile" items="${roles}" multiple="true" itemValue="id" itemLabel="type" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="userProfile" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-actions floatRight">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/list' />">Cancel</a>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/list' />">Cancel</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>