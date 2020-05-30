<#import "../../parts/common.ftlh" as c>

<@c.page>

    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <div>
        <a href="/dataEdit/parking"
           class="btn btn-dark stretched-link my-3">Назад</a>
    </div>
    <h5>Парковка</h5>
    <form action="/dataEdit/parking/add" method="post" class="mt-2 col-sm-4" enctype="multipart/form-data">
        <div class="form-group ">
            <label for="description">Название парковки</label>
            <input type="text" class="form-control ${(descriptionError??)?string('is-invalid', '')}"
                   id="description"
                   value="<#if parking??>${parking.description}</#if>"
                   placeholder="Введите название парковки"
                   name="description">
            <#if descriptionError??>
                <div class="invalid-feedback">
                    ${descriptionError}
                </div>
            </#if>
        </div>
        <div class="form-group ">
            <label for="file">Выберите фото парковки</label>
            <input name="image" type="file" class="filestyle">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Создать</button>
    </form>
    <script>
        $(".filestyle").filestyle({text: "Выберите фото"});
    </script>
</@c.page>