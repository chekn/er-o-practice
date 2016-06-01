<#setting locale="en" />

<#list dates as date>
	${date?string("MMMM")}		${date?string("MMM")} ${date?string("d")}
</#list>