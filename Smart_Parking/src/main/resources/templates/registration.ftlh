<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div class="mb-1">
        <h5 class="mb-3">Регистрация пользовтеля</h5>
    </div>
    <#if message?has_content>
        <div class="col-6 alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
    <@l.registration/>
</@c.page>

