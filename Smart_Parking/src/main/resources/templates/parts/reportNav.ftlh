<#import "common.ftlh" as c>
<#macro reportNav>
    <@c.page>
        <h5>Генерация отчетов</h5>
        <ul class="nav nav-pills mb-5">
            <li class="nav-item text-white bg-dark">
                <a class="nav-link report-a text-white bg-dark <#if simple??>active</#if>" href="/report/simple">Общий отчет в промежуток
                    времени</a>
            </li>
            <li class="nav-item text-white bg-dark">
                <a class="nav-link report-a text-white bg-dark" href="/report/parking">Отчет по определенным парковкам</a>
            </li>
            <li class="nav-item report-a text-white bg-dark">
                <a class=" text-white bg-dark nav-link report-a <#if person??>active</#if>" href="/report/person">Отчет по отдельным водителям</a>
            </li>
            <li class="nav-item dropdown report-a text-white bg-dark">
                <a class=" text-white bg-dark nav-link dropdown-toggle report-a <#if criteria??>active</#if>" data-toggle="dropdown"
                   href="#"
                   role="button" aria-haspopup="true" aria-expanded="false">Отчет по подразделениям</a>
                <div class="text-white bg-dark dropdown-menu">
                    <a class=" text-white bg-dark dropdown-item report-a  <#if criteria_common??>active</#if>"
                       href="/report/common">Общий</a>
                    <div class="text-white bg-dark dropdown-divider"></div>
                    <a class=" text-white bg-dark dropdown-item report-a <#if criteria_employee??>active</#if>"
                       href="/report/common/employee">Сотрудники</a>
                    <a class=" text-white bg-dark dropdown-item report-a <#if criteria_student??>active</#if>"
                       href="/report/common/student">Студенты</a>
                </div>
            </li>
            <li class="text-white bg-dark nav-item dropdown report-a">
                <a class="text-white bg-dark nav-link dropdown-toggle report-a <#if violation??>active</#if>" data-toggle="dropdown"
                   href="#"
                   role="button" aria-haspopup="true" aria-expanded="false">Отчет о нарушениях</a>
                <div class=" text-white bg-dark dropdown-menu">
                    <a class="text-white bg-dark dropdown-item report-a  <#if violation_common??>active</#if>"
                       href="/report/common/violation">Общий</a>
                    <div class="text-white bg-dark dropdown-divider"></div>
                    <a class="text-white bg-dark dropdown-item report-a <#if violation_employee??>active</#if>"
                       href="/report/common/employee/violation">Сотрудники</a>
                    <a class="text-white bg-dark dropdown-item report-a <#if violation_student??>active</#if>"
                       href="/report/common/student/violation">Студенты</a>
                </div>
            </li>
        </ul>
        <script>
            var common = "${(simple??)?string('yes', 'no')}";
            var drivers = "${(person??)?string('yes', 'no')}";
            var criteria_common = "${(criteria_common??)?string('yes', 'no')}";
            var employee = "${(criteria_employee??)?string('yes', 'no')}";
            var students = "${(criteria_student??)?string('yes', 'no')}";
            var violation = "${(violation??)?string('yes', 'no')}";
            var violation_common = "${(violation_common??)?string('yes', 'no')}";
            var violation_employee = "${(violation_employee??)?string('yes', 'no')}";
            var violation_student = "${(violation_student??)?string('yes', 'no')}";
            var criteria = "${(criteria??)?string('yes', 'no')}";
            var active = (common === 'yes' || drivers === 'yes' || criteria_common === 'yes' ||
                employee === 'yes' || students === 'yes' || violation === 'yes'
                || criteria === 'yes' || violation_common === 'yes'
                || violation_employee === 'yes' || violation_student === 'yes');

            $('a.report-a').hover(
                function () {
                    if (!active)
                        $(this).addClass('active')
                },
                function () {
                    if (!active)
                        $(this).removeClass('active')
                }
            )

        </script>
        <#nested>

    </@c.page>
</#macro>