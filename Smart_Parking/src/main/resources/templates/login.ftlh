<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION?? && RequestParameters.error??>
        <div class="alert alert-danger" role="alert">
           ${SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-sm-4" role="alert">
            ${message}
        </div>
    </#if>
<@l.login/>
</@c.page>