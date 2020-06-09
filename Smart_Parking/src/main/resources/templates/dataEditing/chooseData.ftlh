<#import "../parts/common.ftlh" as c>

<@c.page>
<h5 class="mb-3"> Редактирование данных</h5>
    <table class="table-hover table table-borderless table-striped col-6 text-center table-dark">
        <thead >
        <tr class="row">
            <th class="col-sm-8" scope="col">Сущность</th>
            <th class="col-sm-4" scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <tr class="row">
            <td class="col-sm-8">Водители</td>
            <td class="col-sm-4"><a href="/dataEdit/person/" class="btn btn-primary stretched-link ">Редактировать</a></td>
        </tr>
        <tr class="row">
            <td class="col-sm-8">Автомобили</td>
            <td class="col-sm-4"><a href="/dataEdit/auto/" class="btn btn-primary stretched-link ">Редактировать</a></td>
        </tr>
        <tr class="row">
            <td class="col-sm-8">Парковки</td>
            <td class="col-sm-4"><a href="/dataEdit/parking/" class="btn btn-primary stretched-link ">Редактировать</a></td>
        </tr>
        <tr class="row">
            <td class="col-sm-8">Парковочные места</td>
            <td class="col-sm-4"><a href="/dataEdit/place/" class="btn btn-primary stretched-link ">Редактировать</a></td>
        </tr>
        <tr class="row">
            <td class="col-sm-8">Институты</td>
            <td class="col-sm-4"><a href="/dataEdit/division/" class="btn btn-primary stretched-link ">Редактировать</a></td>
        </tr>
        <tr class="row">
            <td class="col-sm-8">Кафедры/Подразделения</td>
            <td class="col-sm-4"><a href="/dataEdit/subdivision/" class="btn btn-primary stretched-link ">Редактировать</a></td>
        </tr>
        <tr class="row">
            <td class="col-sm-8">Должности</td>
            <td class="col-sm-4"><a href="/dataEdit/job/" class="btn btn-primary stretched-link ">Редактировать</a></td>
        </tr>
        <tr class="row">
            <td class="col-sm-8">Цвета автомобилей</td>
            <td class="col-sm-4"><a href="/dataEdit/color/" class="btn btn-primary stretched-link ">Редактировать</a></td>
        </tr>
        </tbody>
    </table>
</@c.page>
