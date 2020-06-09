<#import "../parts/common.ftlh" as c>
<@c.page>
    <div>
        <a href="/parking/${parkingEvent.getPlace().getParking().getId()}"
           class="btn btn-dark stretched-link mb-4">Назад</a>
    </div>
    <h3 class="mb-4">${parkingName}</h3>
    <div class="row">
    <table class="table table-borderless table-striped col-4 table-hover table-dark">
        <thead>
        <tr class="d-flex">
            <th scope="col" colspan="2">Парковочное место номер ${placeNum}</th>
        </tr>
        </thead>
        <tbody>
        <#if automobile??>
            <#if automobile.getNumber()??>
                <tr class="d-flex">
                    <th scope="row">Номер автомобиля</th>
                    <td>${automobile.getNumber()}</td>
                </tr>
            </#if>
            <#if automobile.getModel()??>
                <tr class="d-flex">
                    <th scope="row">Автомобиль</th>
                    <#if automobile.getColor()??>
                    <td>${automobile.getInfo()}</td>
                        <#else>
                            <td>${automobile.getModel()}</td>
                    </#if>
                </tr>
            </#if>
        </#if>
        <#if !automobile?? && parkingEvent.getUnknownNum()??>
            <tr class="d-flex">
                <th scope="row">Считанный номер автомобиля</th>
                <td>${parkingEvent.getUnknownNum()}</td>
            </tr>
        </#if>
        <#if person??>
            <tr class="d-flex">
                <th scope="row">Владелец</th>
                <td>${person.getFullName()}</td>
            </tr>
            <tr class="d-flex">
                <th scope="row">Должность</th>
                <#if person.isStudent()>
                    <td>Студент</td>
                </#if>
                <#if person.isEmployee()>
                    <td>${person.getJobPosition().getNamePosition()}</td>
                </#if>
            </tr>

            <#if person.getSubdivision()??>
                <tr class="d-flex">
                    <th scope="row">Институт</th>
                    <td>${person.getSubdivision().getDivision().getName()}</td>
                </tr>
                <tr class="d-flex">
                    <th scope="row">Кафедра/Подразделение</th>
                    <td>${person.getSubdivision().getName()}</td>
                </tr>
            </#if>

            <#if person.getCourse()??>
                <tr class="d-flex">
                    <th scope="row">Курс</th>
                    <td>${person.getCourse()}</td>
                </tr>
            </#if>

            <#if person.getGroupName()??>
                <tr class="d-flex">
                    <th scope="row">Группа</th>
                    <td>${person.getGroupName()}</td>
                </tr>
            </#if>
        </#if>


        <tr class="d-flex">
            <th scope="row">Дата начала парковки</th>
            <td>${parkingEvent.getStartTime()}</td>
        </tr>
        <#--        <tr class="d-flex">-->
        <#--            <th scope="row">Место для инвалидов</th>-->
        <#--            <#if specialStatus>-->
        <#--                <td content="\2611">&#9745;</td>-->
        <#--            <#else>-->
        <#--                <td content="\2612">&#x2716;</td>-->
        <#--            </#if>-->
        <#--        </tr>-->
        <#if person??>
            <#if person.getPassNum()??>
                <tr class="d-flex">
                    <th scope="row">Пропуск номер:</th>
                    <td>${person.getPassNum()}</td>
                </tr>
            <#else>
                <tr class="d-flex bg-danger">
                    <th scope="row" class="bg-danger">Пропуск</th>
                    <td class="bg-danger">Пропуск просрочен или не действителен!</td>
                </tr>
            </#if>
        </#if>
        <#--        <tr class="d-flex">-->
        <#--            <#if violation>-->
        <#--                <td class="bg-danger w-100">Нарушение правил парковки на место для инвалидов!</td>-->
        <#--            </#if>-->
        <#--        </tr>-->
        <#if autoViolation?? && autoViolation>
            <tr class="d-flex">
                <td class="bg-danger w-100">Неизвестный автомобиль!</td>
            </tr>
        </#if>
        </tbody>
    </table>
        <#if imgName??>
            <div class="col-4 ml-5">
                <img src="/img/autos/${imgName}" class="card-img-top img-fluid">
            </div>
        </#if>

    </div>
</@c.page>