<#macro login>
    <form action="/login" method="post" class="mt-2 col-sm-4">
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
            <label for="password">Пароль:</label>
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')} "
                   id="password" placeholder="Введите пароль"
                   name="password">
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Войти</button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Выйти</button>
    </form>
</#macro>

<#macro registration>
    <form action="/registration" method="post" class="mt-2 col-sm-4">
        <div class="form-group">
            <label for="secondName">Фамилия</label>
            <input type="text" class="form-control ${(secondNameError??)?string('is-invalid', '')} "
                   id="secondName"
                   value="<#if userTemp??>${userTemp.secondName}</#if>"
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
                   value="<#if userTemp??>${userTemp.firstName}</#if>"
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
                   value="<#if userTemp??>${userTemp.middleName}</#if>"
                   placeholder="Введите отчество"
                   name="middleName">
            <#if middleNameError??>
                <div class="invalid-feedback">
                    ${middleNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="username">E-mail:</label>
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid', '')} " id="username"
                   value="<#if userTemp??>${userTemp.username}</#if>"
                   placeholder="Введите e-mail"
                   name="username">
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="password">Пароль:</label>
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')} "
                   id="password" placeholder="Введите пароль"
                   name="password">
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="password2">Подтверждение пароля:</label>
            <input type="password" class="form-control ${(password2Error??)?string('is-invalid', '')} "
                   id="password2" placeholder="Повторите пароль"
                   name="password2">
            <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
                </div>
            </#if>
        </div>

        <div class="btn-group btn-group-toggle form-group" data-toggle="buttons">
            <#list roles as role>
                <#if role == "ADMIN" && currentUser.getRole() != "ADMIN">
                    <#else>
                        <label class="btn btn-secondary">
                            <input type="radio" name="role" id="${role}" value="${role}"> ${role}
                        </label>
                </#if>
            </#list>
        </div>
            <#if roleError??>
                <div class="invalid-feedback mb-3" style="display: block">
                    ${roleError}
                </div>
            </#if>

        <div>
            <div class="g-recaptcha" data-sitekey="6Ld2WPIUAAAAAD7PuG0wfhYFEIK408dIjng1u1fa"></div>
            <#if captchaError??>
            <div class="invalid-feedback mb-3 mt-3" style="display: block">
                ${captchaError}
            </div>
        </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Зарегистрировать</button>
    </form>
</#macro>