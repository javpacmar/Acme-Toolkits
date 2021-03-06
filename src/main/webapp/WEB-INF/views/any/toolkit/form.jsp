<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="any.toolkit.form.label.code" path="code"/>
	<acme:input-textarea code="any.toolkit.form.label.title" path="title"/>
	<acme:input-textbox code="any.toolkit.form.label.description" path="description"/>
	<acme:input-textarea code="any.toolkit.form.label.notes" path="notes"/>
	<acme:input-url code="any.toolkit.form.label.link" path="link"/>
	<acme:input-money code="any.toolkit.form.label.total-price" path="totalPrice"/> 
	<acme:button code="any.toolkit.form.button.tool" action="/any/item/list-tool-toolkit?id=${ toolkitId }"/>		
	<acme:button code="any.toolkit.form.button.component" action="/any/item/list-component-toolkit?id=${ toolkitId }"/>	
	
</acme:form>