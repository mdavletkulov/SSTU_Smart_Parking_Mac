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
    <form action="/dataEdit/job/add" method="post" class="mt-2 col-sm-4">

        <div class="form-group typeJobPosition">
            <label class="typeJobPosition" for="typeJobPosition">Подразделение</label>
            <select class="form-control typeJobPosition" name="typeJobPosition">
                <#list typeJobPositions as typeJobPosition>
                    <option>${typeJobPosition}</option>
                </#list>
            </select>
            <#if typeJobPositionsError??>
                <div class="invalid-feedback">
                    ${typeJobPositionError}
                </div>
            </#if>
        </div>

        <div class="form-group">
            <label for="namePosition">Название должности</label>
            <input type="text" class="form-control ${(namePositionError??)?string('is-invalid', '')} "
                   id="namePosition"
                   value="<#if jobPosition??>${jobPosition.namePosition}</#if>"
                   placeholder="Введите Название должности"
                   name="namePosition">
            <#if namePositionError??>
                <div class="invalid-feedback">
                    ${namePositionError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Создать</button>
    </form>
</@c.page>