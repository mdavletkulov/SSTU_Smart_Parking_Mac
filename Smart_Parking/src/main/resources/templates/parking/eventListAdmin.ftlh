<#import "../parts/common.ftlh" as c>

<@c.page>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>

    <div><h5 class="mb-3">Парковочные события</h5>
    </div>


    <table class="table table-striped text-center table-hover table-dark table-dark" id="events" style="width: 100%">
        <thead>
        <tr>
            <th class="align-middle" scope="col">Парковка</th>
            <th class="align-middle" scope="col">Номер марковочного места</th>
            <th class="align-middle" scope="col">Водитель</th>
            <th class="align-middle" scope="col">Марка автомобиля</th>
            <th class="align-middle" scope="col">Номер автомобиля</th>
            <th class="align-middle" scope="col">Дата начала</th>
            <th class="align-middle" scope="col">Дата окончания</th>
            <th class="align-middle" scope="col">Правомерность парковки</th>
            <th class="align-middle" scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list events as event>
            <tr>
                <th class="align-middle" scope="row"><#if event.getPlace()??>${event.getPlace().getParking().getDescription()}<#else>&#x2716;</#if></th>
                <td class="align-middle" scope="row"><#if event.getPlace()??>${event.getPlace().getPlaceNumber()}<#else>&#x2716;</#if></td>
                <td class="align-middle" scope="row"><#if event.getPerson()??>${event.getPerson().getFullName()}<#else>&#x2716;</#if></td>
                <td class="align-middle" scope="row"><#if event.getAutomobile()?? && event.getAutomobile().getModel()?? >${event.getAutomobile().getModel()}<#else>&#x2716;</#if></td>
                <td class="align-middle" scope="row"><#if event.getAutomobile()??>${event.getAutomobile().getNumber()}<#else>&#x2716;</#if></td>
                <td class="align-middle" scope="row">${event.getStartDateString()}</td>
                <td class="align-middle" scope="row"><#if event.getEndTime()??>${event.getEndDateString()}</#if></td>
                <td class="align-middle" scope="row"><#if event.getPassNumViolation() || event.getAutoViolation() || event.getPersonViolation()>&#x2716;<#else>&#9745;</#if></td>
                <td class="align-middle" scope="row"><#if !event.getEndTime()??><a href="/parking/event/stop/${event.getId()}"
                                                                                  class="btn btn-primary stretched-link my-3">Закончить</a></#if></td>
            </tr>
        </#list>
        </tbody>
    </table>

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
                    {width: '15%', targets: 0},
                    {width: '5%', targets: 1},
                    {width: '15%', targets: 2},
                    {width: '10%', targets: 3},
                    {width: '10%', targets: 4},
                    {width: '10%', type: 'de_datetime', targets: 5},
                    {width: '13%', type: 'de_datetime', targets: 6},
                    {width: '15%', targets: 7},
                    {width: '7%', "orderable": false, targets: 8},
                ],
                "order": [[ 6, "asc" ]],
                language: {
                    url: '/static/localization/Russian.json'
                },
                fixedColumns: true
            });
        });

    </script>

</@c.page>