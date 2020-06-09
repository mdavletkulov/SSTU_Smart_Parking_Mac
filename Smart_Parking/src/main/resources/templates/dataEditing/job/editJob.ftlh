<#import "../../parts/common.ftlh" as c>

<@c.page>

    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <div>
        <a href="/dataEdit/job"
           class="btn btn-dark stretched-link my-3">Назад</a>
    </div>
    <h5>Должность</h5>
    <form action="/dataEdit/job/edit/${job.getId()}" method="post" class="mt-2 col-sm-4">

        <div class="form-group">
            <label for="namePosition">Название должности</label>
            <input type="text" class="form-control ${(namePositionError??)?string('is-invalid', '')}"
                   id="namePosition"
                   value="${job.namePosition}"
                   placeholder="Введите Название должности"
                   name="namePosition">
            <#if namePositionError??>
                <div class="invalid-feedback">
                    ${namePositionError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Обновить</button>
    </form>
</@c.page>