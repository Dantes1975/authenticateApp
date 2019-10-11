<%@ page import="by.it_academy.util.ApplicationConstant" %>

<%
    Object error = session.getAttribute(ApplicationConstant.ERROR_KEY);

    if (error != null) {
        String errorMsg = String.valueOf(error);
        if (!errorMsg.isEmpty()){
            out.print("<h3 style=\"color:red; \"> " + "* " + errorMsg + "</h3>");
            session.removeAttribute(ApplicationConstant.ERROR_KEY);
        }
    }
%>
