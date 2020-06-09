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
        <tr class="hvr" id="trPlaceNum${place.id}" onclick="document.location = '${parking.getId()}/${place.id}';">
            <td id="placeNum${place.id}" class="align-middle" scope="row"><#if event.getPlace()??>${event.getPlace().getPlaceNumber()}</#if></td>
            <td id="status${place.id}" class="align-middle <#if event.getPassNumViolation() || event.getAutoViolation() || event.getPersonViolation()> viol<#else> active</#if>" scope="row"><b>Занято
                <#if event.getPassNumViolation() || event.getAutoViolation() || event.getPersonViolation()> (Есть нарушение) </#if></b></td>
            <td id="person${place.id}" class="align-middle" scope="row"><#if event.getPerson()??>${event.getPerson().getFullName()}<#else>&#x2716;</#if></td>
            <td id="model${place.id}" class="align-middle" scope="row"><#if event.getAutomobile()?? && event.getAutomobile().getModel()?? >${event.getAutomobile().getModel()}<#else>&#x2716;</#if></td>
            <td id="number${place.id}" class="align-middle" scope="row"><#if !event.getAutomobile()?? && event.getUnknownNum()??>${event.getUnknownNum()}<#else><#if event.getAutomobile()??>${event.getAutomobile().getNumber()}<#else>&#x2716;</#if></#if></td>
            <td id="startDate${place.id}" class="align-middle" scope="row">${event.getStartDateString()}</td>
        </tr>
        </#if>
    </#list>
        <#if eventPresent == 0>
            <tr id="trPlaceNum${place.id}">
                <td id="placeNum${place.id}" class="align-middle" scope="row"><#if place??>${place.getPlaceNumber()}</#if></td>
                <td id="status${place.id}" class="align-middle free" scope="row"><b>Свободно</b></td>
                <td id="person${place.id}" class="align-middle" scope="row">&#x2716;</td>
                <td id="model${place.id}" class="align-middle" scope="row">&#x2716;</td>
                <td id="number${place.id}" class="align-middle" scope="row">&#x2716;</td>
                <td id="startDate${place.id}" class="align-middle" scope="row">&#x2716;</td>
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

    var parkingId = "${parking.getId()}";

    jQuery(document).ready(function ($) {

        function foo() {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "updateStatus/" + parkingId,
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var placeNum = data[i].placeId;
                        var status = $('#status'+placeNum);
                        var person = data[i].person;
                        var model = data[i].model;
                        var number = data[i].number;
                        var startDate = data[i].startDate;
                        var personEl = $('#person'+placeNum);
                        var modelEl = $('#model'+placeNum);
                        var numberEl = $('#number'+placeNum);
                        var startDateEl = $('#startDate'+placeNum);
                        var trPlaceNum = $('#trPlaceNum'+placeNum);
                        status.removeClass('active');
                        status.removeClass('viol');
                        status.removeClass('free');
                        if (data[i].active) {
                            status.removeClass('active');
                            status.removeClass('viol');
                            status.removeClass('free');
                            status.addClass('active');
                            status.empty();
                            status.text("Занято");
                            personEl.empty();
                            personEl.text(person);
                            numberEl.empty();
                            numberEl.text(number);
                            startDateEl.empty();
                            startDateEl.text(startDate);
                            modelEl.empty();
                            modelEl.text(model);
                            trPlaceNum.removeClass("hvr");
                            trPlaceNum.attr("class","hvr");
                            trPlaceNum.removeAttr("onclick");
                            var href = parkingId+"\u002F"+placeNum;
                            trPlaceNum.attr("onclick", "document.location = '"+href+"'");
                        }
                        if (data[i].violation) {
                            status.removeClass('active');
                            status.removeClass('viol');
                            status.removeClass('free');
                            status.addClass('viol');
                            status.empty();
                            status.text("Занято (Есть нарушение)");
                            personEl.empty();
                            personEl.text(person);
                            numberEl.empty();
                            numberEl.text(number);
                            startDateEl.empty();
                            startDateEl.text(startDate);
                            modelEl.empty();
                            modelEl.text(model);
                            trPlaceNum.removeClass("hvr");
                            trPlaceNum.attr("class","hvr");
                            trPlaceNum.removeAttr("onclick");
                            var href = parkingId+"\u002F"+placeNum;
                            trPlaceNum.attr("onclick", "document.location = '"+href+"'");
                        }
                        if (!data[i].violation && !data[i].active) {
                            status.removeClass('active');
                            status.removeClass('viol');
                            status.removeClass('free');
                            status.addClass('free');
                            status.empty();
                            status.text("Свободно");
                            personEl.empty();
                            personEl.text("\u2716");
                            numberEl.empty();
                            numberEl.text("\u2716");
                            startDateEl.empty();
                            startDateEl.text("\u2716");
                            modelEl.empty();
                            modelEl.text("\u2716");
                            trPlaceNum.removeAttr("onclick");
                            trPlaceNum.removeClass("hvr");
                        }
                    }
                },
            });
            setTimeout(foo, 4000);
        }

        foo();

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