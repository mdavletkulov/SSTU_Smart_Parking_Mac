<#import "../../parts/common.ftlh" as c>

<@c.page>

    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <div>
        <a href="/dataEdit/place"
           class="btn btn-dark stretched-link my-3">Назад</a>
    </div>
    <h5>Парковочное место</h5>
    <form action="/dataEdit/place/edit/${place.getId()}" method="post" class="mt-2 col-sm-4">
        <div class="form-group parking">
            <label class="parking" for="parking">Парковки</label>
            <select class="form-control parking" name="parking">
                <#list parkings as parking>
                    <option <#if place.getParking().getDescription() == parking.getDescription()> selected="selected"
                                                                                                  </#if>value="${parking.getId()}">${parking.getDescription()}</option>
                </#list>
            </select>
            <#if parkingError??>
                <div class="invalid-feedback">
                    ${parkingError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="placeNumber">Номер места</label>
            <input type="text" class="form-control ${(placeNumberError??)?string('is-invalid', '')} "
                   id="placeNumber"
                   value="${place.placeNumber}"
                   placeholder="Введите номер парковочного места"
                   name="placeNumber">
            <#if placeNumberError??>
                <div class="invalid-feedback">
                    ${placeNumberError}
                </div>
            </#if>
        </div>
        <div class="form-check">
            <input name="specialStatus" type="checkbox"
                   class="form-check-input ${(specialStatusError??)?string('is-invalid', '')}" id="specialStatus"
                <#if place.isSpecialStatus()> checked="checked" </#if>
            <label class="form-check-label" for="exampleCheck1">Специальный статус</label>
            <#if specialStatusError??>
                <div class="invalid-feedback">
                    ${specialStatusError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Обновить</button>
    </form>
</@c.page>