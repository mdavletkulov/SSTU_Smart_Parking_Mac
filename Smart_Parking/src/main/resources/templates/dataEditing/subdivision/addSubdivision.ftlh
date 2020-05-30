<#import "../../parts/common.ftlh" as c>

<@c.page>

    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <div>
        <a href="/dataEdit/subdivision"
           class="btn btn-dark stretched-link my-3">Назад</a>
    </div>
    <h5>Кафедра</h5>
    <form action="/dataEdit/subdivision/add" method="post" class="mt-2 col-sm-4">
        <div class="form-group  division">
            <label class="division" for="division">Институты</label>
            <select id="division" class="form-control division " name="division" >

                <#list divisions as division>
                    <option value="${division.getId()}">${division.getName()}</option>
                </#list>
            </select>
            <#if divisionError??>
                <div class="invalid-feedback">
                    ${divisionError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="name">Название кафедры</label>
            <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')} "
                   id="name"
                   value="<#if subdivision??>${subdivision.name}</#if>"
                   placeholder="Введите название кафедры"
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

    <script>
        </script>
</@c.page>