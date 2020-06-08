<#import "../parts/common.ftlh" as c>

<@c.page>

    <h3 class="my-3">${parking.getDescription()}</h3>

<table class="table table-striped text-center table-hover table-dark" id="events" style="width: 100%">
    <thead>
    <tr>
        <th class="align-middle" scope="col">Номер марковочного места</th>
        <th class="align-middle" scope="col">Состояние</th>
        <th class="align-middle" scope="col">Водитель</th>
        <th class="align-middle" scope="col">Марка автомобиля</th>
        <th class="align-middle" scope="col">Номер автомобиля</th>
        <th class="align-middle" scope="col">Дата начала</th>
    </tr>
    </thead>
    <tbody>
    <#list places as place>
        <#assign eventPresent = 0>
    <#list events as event>
        <#if event.getPlace().getId() == place.getId()>
            <#assign eventPresent = 1>
        <tr class="hvr" onclick="document.location = '${parking.getId()}/${place.id}';">
            <td class="align-middle" scope="row"><#if event.getPlace()??>${event.getPlace().getPlaceNumber()}</#if></td>
            <td class="align-middle <#if event.getPassNumViolation() || event.getAutoViolation() || event.getPersonViolation()> viol<#else> active</#if>" scope="row"><b>Занято
                <#if event.getPassNumViolation() || event.getAutoViolation() || event.getPersonViolation()> (Есть нарушение) </#if></b></td>
            <td class="align-middle" scope="row"><#if event.getPerson()??>${event.getPerson().getFullName()}<#else>&#x2716;</#if></td>
            <td class="align-middle" scope="row"><#if event.getAutomobile()?? && event.getAutomobile().getModel()?? >${event.getAutomobile().getModel()}<#else>&#x2716;</#if></td>
            <td class="align-middle" scope="row"><#if event.getAutomobile()??>${event.getAutomobile().getNumber()}<#else>&#x2716;</#if></td>
            <td class="align-middle" scope="row">${event.getStartDateString()}</td>
        </tr>
        </#if>
    </#list>
        <#if eventPresent == 0>
            <tr>
                <td class="align-middle" scope="row"><#if place??>${place.getPlaceNumber()}</#if></td>
                <td class="align-middle free" scope="row"><b>Свободно</b></td>
                <td class="align-middle" scope="row">&#x2716;</td>
                <td class="align-middle" scope="row">&#x2716;</td>
                <td class="align-middle" scope="row">&#x2716;</td>
                <td class="align-middle" scope="row">&#x2716;</td>
            </tr>
        </#if>
    </#list>
    </tbody>
</table>

    <style>
        tr.hvr{
            height: 80px;
        }
        tr.hvr:hover{
            cursor: pointer;
        }
    </style>

<script>

    jQuery(document).ready(function ($) {

        jQuery.extend( jQuery.fn.dataTableExt.oSort, {
            "de_datetime-asc": function ( a, b ) {
                var x, y;
                if (jQuery.trim(a) !== '') {
                    var deDatea = jQuery.trim(a).split(' ');
                    var deTimea = deDatea[1].split(':');
                    var deDatea2 = deDatea[0].split('.');
                    if(typeof deTimea[2] != 'undefined') {
                        x = (deDatea2[2] + deDatea2[1] + deDatea2[0] + deTimea[0] + deTimea[1] + deTimea[2]) * 1;
                    } else {
                        x = (deDatea2[2] + deDatea2[1] + deDatea2[0] + deTimea[0] + deTimea[1]) * 1;
                    }
                } else {
                    x = -Infinity; // = l'an 1000 ...
                }

                if (jQuery.trim(b) !== '') {
                    var deDateb = jQuery.trim(b).split(' ');
                    var deTimeb = deDateb[1].split(':');
                    deDateb = deDateb[0].split('.');
                    if(typeof deTimeb[2] != 'undefined') {
                        y = (deDateb[2] + deDateb[1] + deDateb[0] + deTimeb[0] + deTimeb[1] + deTimeb[2]) * 1;
                    } else {
                        y = (deDateb[2] + deDateb[1] + deDateb[0] + deTimeb[0] + deTimeb[1]) * 1;
                    }
                } else {
                    y = -Infinity;
                }
                var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
                return z;
            },

            "de_datetime-desc": function ( a, b ) {
                var x, y;
                if (jQuery.trim(a) !== '') {
                    var deDatea = jQuery.trim(a).split(' ');
                    var deTimea = deDatea[1].split(':');
                    var deDatea2 = deDatea[0].split('.');
                    if(typeof deTimea[2] != 'undefined') {
                        x = (deDatea2[2] + deDatea2[1] + deDatea2[0] + deTimea[0] + deTimea[1] + deTimea[2]) * 1;
                    } else {
                        x = (deDatea2[2] + deDatea2[1] + deDatea2[0] + deTimea[0] + deTimea[1]) * 1;
                    }
                } else {
                    x = Infinity;
                }

                if (jQuery.trim(b) !== '') {
                    var deDateb = jQuery.trim(b).split(' ');
                    var deTimeb = deDateb[1].split(':');
                    deDateb = deDateb[0].split('.');
                    if(typeof deTimeb[2] != 'undefined') {
                        y = (deDateb[2] + deDateb[1] + deDateb[0] + deTimeb[0] + deTimeb[1] + deTimeb[2]) * 1;
                    } else {
                        y = (deDateb[2] + deDateb[1] + deDateb[0] + deTimeb[0] + deTimeb[1]) * 1;
                    }
                } else {
                    y = -Infinity;
                }
                var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));
                return z;
            },

            "de_date-asc": function ( a, b ) {
                var x, y;
                if (jQuery.trim(a) !== '') {
                    var deDatea = jQuery.trim(a).split('.');
                    x = (deDatea[2] + deDatea[1] + deDatea[0]) * 1;
                } else {
                    x = Infinity; // = l'an 1000 ...
                }

                if (jQuery.trim(b) !== '') {
                    var deDateb = jQuery.trim(b).split('.');
                    y = (deDateb[2] + deDateb[1] + deDateb[0]) * 1;
                } else {
                    y = -Infinity;
                }
                var z = ((x < y) ? -1 : ((x > y) ? 1 : 0));
                return z;
            },

            "de_date-desc": function ( a, b ) {
                var x, y;
                if (jQuery.trim(a) !== '') {
                    var deDatea = jQuery.trim(a).split('.');
                    x = (deDatea[2] + deDatea[1] + deDatea[0]) * 1;
                } else {
                    x = -Infinity;
                }

                if (jQuery.trim(b) !== '') {
                    var deDateb = jQuery.trim(b).split('.');
                    y = (deDateb[2] + deDateb[1] + deDateb[0]) * 1;
                } else {
                    y = Infinity;
                }
                var z = ((x < y) ? 1 : ((x > y) ? -1 : 0));
                return z;
            }
        } );


        $('#events').DataTable({
            "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Все"]],
            columnDefs: [
                {width: '10%', targets: 0},
                {width: '20%', targets: 1},
                {width: '25%', targets: 2},
                {width: '15%', targets: 3},
                {width: '10%', targets: 4},
                {width: '20%', type: 'de_datetime', targets: 5}
            ],
            "order": [[ 0, "asc" ]],
            language: {
                url: '/static/localization/Russian.json'
            },
            fixedColumns: true
        });
    });

</script>

</@c.page>