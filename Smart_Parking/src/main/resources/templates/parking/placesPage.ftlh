<#import "../parts/common.ftlh" as c>
<@c.page>
    <div>
        <a href="/parking"
           class="btn btn-dark stretched-link">Назад</a>
    </div>
    <#list places as place>
        <#if (place?index%5==0) || (place?index==0)>
            <div class="row mt-5 equal">
        </#if>
        <div class="card col-sm-3 m-2 parent">
            <a id="${place.getPlaceNumber()}" href="${parking.getId()}/${place.id}">
                <div class="card-body">
                    <h3 class="card-title" id="${place.getPlaceNumber()}h3">Парковочное
                        место ${place.getPlaceNumber()}</h3>
                </div>
<#--                <#if place.isSpecialStatus()>-->
<#--                    <div class="img-container">-->
<#--                    <img class="mb-3" src="/img/invalid1.png">-->
<#--                    </div>-->
<#--                </#if>-->

            </a>
        </div>
        <#if (place?index%5==4) || !(place_has_next)>
            </div>
        </#if>

    </#list>

    <script type="text/javascript">
        var parkingId = "${parking.getId()}";

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
                        var placeNum = data[i].placeNum;
                        var placeNumElement = $('#' + placeNum + "h3");
                        var placeA = $('a#' + placeNum);
                        placeA.removeClass('disabled');
                        placeNumElement.removeClass('active');
                        placeNumElement.removeClass('viol');
                        placeNumElement.removeClass('free');
                        if (data[i].active) {
                            placeA.removeClass('disabled');
                            placeNumElement.removeClass('active');
                            placeNumElement.removeClass('viol');
                            placeNumElement.removeClass('free');
                            placeNumElement.addClass('active');
                        }
                        if (data[i].violation) {
                            placeA.removeClass('disabled');
                            placeNumElement.removeClass('active');
                            placeNumElement.removeClass('viol');
                            placeNumElement.removeClass('free');
                            placeNumElement.addClass('viol');
                        }
                        if (!data[i].violation && !data[i].active) {
                            placeA.removeClass('disabled');
                            placeNumElement.removeClass('active');
                            placeNumElement.removeClass('viol');
                            placeNumElement.removeClass('free');
                            placeNumElement.addClass('free');
                            placeA.addClass('disabled');
                        }
                        console.log(data[i].placeNum + " " + data[i].violation + " " + data[i].active);
                    }
                },
            });
            setTimeout(foo, 4000);
        }


        $(document).ready(foo());
    </script>


</@c.page>