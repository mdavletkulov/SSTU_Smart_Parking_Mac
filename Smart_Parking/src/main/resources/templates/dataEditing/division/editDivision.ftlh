<#import "../../parts/common.ftlh" as c>

<@c.page>

    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <div>
        <a href="/dataEdit/division"
           class="btn btn-dark stretched-link my-3">Назад</a>
    </div>
    <h5>Институт</h5>
    <form action="/dataEdit/division/edit/${division.getId()}" method="post" class="mt-2 col-sm-4">
        <div class="form-group">
            <label for="name">Название института</label>
            <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')} "
                   id="name"
                   value="${division.name}"
                   placeholder="Введите название института"
                   name="name">
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Создать</button>
    </form>
</@c.page>