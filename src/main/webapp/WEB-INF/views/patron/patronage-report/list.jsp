<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="patron.patronage-report.list.label.creationMoment" path="creationMoment" width="10%"/>
	<acme:list-column code="patron.patronage-report.list.label.memorandum" path="memorandum" width="10%"/>
	<acme:list-column code="patron.patronage-report.list.label.link" path="link" width="20%"/>
	<acme:list-column code="patron.patronage-report.list.label.patronage.code" path="patronageCode" width="10%"/>
</acme:list>