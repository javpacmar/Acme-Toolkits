<%@ page language="java" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>

	<acme:input-textbox code="inventor.item.form.label.name" path="name"/>
	<acme:input-textbox code="inventor.item.form.label.code" path="code"/>
	<acme:input-textbox code="inventor.item.form.label.technology" path="technology"/>
	<acme:input-textbox code="inventor.item.form.label.description" path="description"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish')}">
			<acme:input-money readonly="true" code="inventor.item.form.label.price" path="price"/>
			<acme:input-money readonly="true" code="inventor.item.form.label.exchange" path="exchangePrice"/>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
		<acme:input-money code="inventor.item.form.label.price" path="price"/>
		</jstl:when>
	</jstl:choose> 
	
	<acme:input-url code="inventor.item.form.label.link" path="link"/>
	<acme:input-select code="inventor.item.form.label.type" path="type">
		<acme:input-option code="inventor.item.form.label.component" value="COMPONENT" selected="${ type == 'COMPONENT' }"/>
		<acme:input-option code="inventor.item.form.label.tool" value="TOOL" selected="${ type == 'TOOL' }"/>
	</acme:input-select>

	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete, publish') && published == false }">
		
			<h2><acme:message code="inventor.item.form.label.info"/></h2> 
	 		<acme:input-textbox readonly="true" code="inventor.item.form.label.fullName" path="inventorFullName"/>
    		<acme:input-textbox readonly="true" code="inventor.item.form.label.name" path="inventorName"/>
    		<acme:input-textbox readonly="true" code="inventor.item.form.label.surname" path="inventorSurname"/>
    		<acme:input-email readonly="true" code="inventor.item.form.label.email" path="inventorEmail"/>
    		
    		
			<acme:submit code="inventor.item.form.button.update" action="/inventor/item/update?id=${id}"/>
			<acme:submit code="inventor.item.form.button.delete" action="/inventor/item/delete"/>
			<acme:submit code="inventor.item.form.button.publish" action="/inventor/item/publish"/>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
			<acme:submit code="inventor.item.form.button.create" action="/inventor/item/create"/>
		</jstl:when>
	</jstl:choose> 
	
</acme:form>

