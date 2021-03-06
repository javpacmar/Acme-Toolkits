<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="inventor.patronage-report.list.label.creationMoment" path="creationMoment" width="10%"/>
	<acme:list-column code="inventor.patronage-report.list.label.memorandum" path="memorandum" width="10%"/>
	<acme:list-column code="inventor.patronage-report.list.label.link" path="link" width="20%"/>
</acme:list>

<acme:button code="master.menu.inventor.patronage-report.create" action="/inventor/patronage-report/create"/>