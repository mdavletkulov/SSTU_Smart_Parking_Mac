<#import "../parts/common.ftlh" as c>

<@c.page>
    <div>
        <a href="/user/edit/${user.getId()}"
           class="btn btn-dark stretched-link">Назад</a>
    </div>
    <h5>${user.getFullName()}</h5>
    <h6>Изменение пароля</h6>
    <#if message?has_content>
        <div class="alert alert-success col-sm-4" role="alert">
            ${message}
        </div>
    </#if>
    <form action="${user.getId()}" method="post" class="mt-2 col-sm-4">
        <div class="form-group">
            <label for="password">Новый пароль</label>
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')} "
                   id="password" placeholder="Введите новый пароль"
                   name="password">
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="password2">Подтверждение пароля</label>
            <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')} "
                   id="password2" placeholder="Повторите пароль"
                   name="password2">
            <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
                </div>
            </#if>
        </div>
        <input type="hidden" name="firstName" value="${user.getFirstName()}"/>
        <input type="hidden" name="secondName" value="${user.getSecondName()}"/>
        <input type="hidden" name="thirdName" value="${user.getMiddleName()}"/>
        <input type="hidden" name="role" value="${user.getRole()}"/>
        <input type="hidden" name="username" value="${user.getUsername()}"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Обновить пароль</button>
    </form>
    <a href="/user/profile" class="btn btn-dark stretched-link mt-3">Назад</a>
</@c.page>