<#import "../parts/common.ftlh" as c>

<@c.page>
    <div>
        <a href="/user"
           class="btn btn-dark stretched-link">Назад</a>
    </div>
    <h6> Редактирование пользователя</h6>
    <h6> ${user.getFullName()}</h6>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <form action="/user/edit/${user.id}" method="post" class="mt-2 col-sm-4">
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
        <div class="btn-group btn-group-toggle form-group" data-toggle="buttons">
            <#list roles as role>
                <label class="btn btn-secondary <#if user.getRole() == role>focus active</#if>">
                    <input type="radio" name="role" id="${role}" value="${role}"
                           <#if user.getRole() == role>checked="checked"</#if>> ${role}
                </label>
            </#list>
        </div>
        <#if roleError??>
            <div class="invalid-feedback mb-3" style="display: block">
                ${roleError}
            </div>
        </#if>
        <div class="form-group">
            <a href="changePassword/${user.getId()}" class="btn btn-primary stretched-link">Изменить пароль</a>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Обновить</button>
    </form>
</@c.page>