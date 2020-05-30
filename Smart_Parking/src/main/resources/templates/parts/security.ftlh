<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
        >

<#if known>
    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getFullName()
        isAdmin = user.isAdmin()
        isManager = user.isManager()
            >
    <#else>
        <#assign
        name = "unknown"
        isAdmin = false
        isManager = false
        >
</#if>