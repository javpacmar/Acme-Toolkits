<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="inventor.patronage.list.label.status" path="status" width="10%"/>
	<acme:list-column code="inventor.patronage.list.label.draftMode" path="draftMode" width="10%"/>
	<acme:list-column code="inventor.patronage.list.label.legalStuff" path="legalStuff" width="10%"/>
	<acme:list-column code="inventor.patronage.list.label.budget" path="budget" width="10%"/>
	<acme:list-column code="inventor.patronage.list.label.creationMoment" path="creationMoment" width="10%"/>
	<acme:list-column code="inventor.patronage.list.label.startDate" path="startDate" width="10%"/>
	<acme:list-column code="inventor.patronage.list.label.finishDate" path="finishDate" width="10%"/>
	<acme:list-column code="inventor.patronage.list.label.link" path="link" width="20%"/>
	
</acme:list>	