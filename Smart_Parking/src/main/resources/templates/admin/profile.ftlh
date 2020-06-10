<#import "../parts/common.ftlh" as c>

<@c.page>
    <h5>${user.getFullName()}</h5>
    <#if message?has_content>
        ${message}
    </#if>
    <form action="profile" method="post" class="mt-2 col-sm-4">
        <div class="form-group">
            <label for="secondName">Фамилия</label>
            <input type="text" class="form-control ${(secondNameError??)?string('is-invalid', '')} "
                   id="secondName"
                   value="<#if user??>${user.secondName}</#if>"
                   placeholder="Введите фамилию"
                   name="secondName">
            <#if secondNameError??>
                <div class="invalid-feedback">
                    ${secondNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="firstName">Имя</label>
            <input type="text" class="form-control ${(firstNameError??)?string('is-invalid', '')} "
                   id="firstName"
                   value="<#if user??>${user.firstName}</#if>"
                   placeholder="Введите имя пользователя"
                   name="firstName">
            <#if firstNameError??>
                <div class="invalid-feedback">
                    ${firstNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="middleName">Отчество</label>
            <input type="text" class="form-control ${(middleNameError??)?string('is-invalid', '')} "
                   id="middleName"
                   value="<#if user??>${user.middleName}</#if>"
                   placeholder="Введите отчество"
                   name="middleName">
        </div>
        <div class="form-group">
            <label for="username">E-mail:</label>
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')} " id="username"
                   value="<#if user??>${user.username}</#if>"
                   placeholder="Введите e-mail"
                   name="username">
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <a href="changePassword" class="btn btn-primary stretched-link">Изменить пароль</a>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Обновить</button>
    </form>
</@c.page>