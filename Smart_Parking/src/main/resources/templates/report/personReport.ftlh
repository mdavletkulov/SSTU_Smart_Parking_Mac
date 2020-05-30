<#import "../parts/reportNav.ftlh" as rN>
<#import "../parts/dateReport.ftlh" as r>
<@rN.reportNav>
    <h4>Отчет о водителе</h4>
    <table id="persons" class="table table-striped no-border text-center table-dark" style="width:100%">
        <thead>
        <tr>
            <th class="align-middle">Водитель</th>
            <th class="align-middle">Автомобиль</th>
            <th class="align-middle">Номер</th>
            <th class="align-middle">Статус</th>
            <th class="align-middle">Подразделение</th>
            <th class="align-middle">Должность</th>
            <th class="align-middle">Институт</th>
            <th class="align-middle">Группа</th>
            <th class="align-middle">Курс</th>
            <th class="align-middle">Пропуск</th>
            <th class="align-middle">Дата истечения пропуска</th>
            <th class="align-middle">Специальный статус</th>
            <th class="align-middle"></th>
        </tr>
        </thead>
        <tbody>
        <#list persons as person>
            <tr>
                <th class="align-middle">${person.getFullName()}</th>
                <#list automobiles as auto>
                    <#if auto.getPerson().getId() == person.getId()><td class="align-middle">${auto.getModel()} </td></#if>
                    <#if auto.getPerson().getId() == person.getId()><td class="align-middle">${auto.getNumber()} </td></#if>
                </#list>
                <td class="align-middle"><#if person.isStudent()>Студент</#if> <#if person.isEmployee()>Сотрудник</#if></td>
                <td class="align-middle"><#if person.getJobPosition()??>${person.getJobPosition().getTypeJobPosition()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.getJobPosition()??>${person.getJobPosition().getNamePosition()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.getSubdivision()??>${person.getSubdivision().getDivision().getName()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.getGroupName()??>${person.getGroupName()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.getCourse()??>${person.getCourse()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.getPassNum()??>${person.getPassNum()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.getPassEndDate()??>${person.getPassEndDate()}<#else>&#x2716;</#if></td>
                <td class="align-middle"><#if person.isSpecialStatus()>&#9745; <#else>&#x2716;</#if></td>
                <td class="align-middle"><a href="/report/person/${person.getId()}" class="btn btn-primary stretched-link">Отчет</a></td>
            </tr>
        </#list>
        </tbody>
    </table>

    <script>
        jQuery(document).ready(function ($) {

            $('#persons').DataTable({
                "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "All"]],
                columnDefs: [
                    {width: '15%', targets: 0},
                    {width: '10%', targets: 1},
                    {width: '10%', targets: 2},
                    {width: '5%', targets: 3},
                    {width: '10%', targets: 4},
                    {width: '5%', targets: 5},
                    {width: '5%', targets: 6},
                    {width: '5%', targets: 7},
                    {width: '5%', targets: 8},
                    {width: '5%', targets: 9},
                    {width: '10%', targets: 10},
                    {width: '5%', targets: 11},
                    {width: '10%', "orderable": false, targets: 12},
                ],
                language: {
                    url: '/static/localization/Russian.json'
                },
                fixedColumns: true
            });
        });
            </script>
</@rN.reportNav>