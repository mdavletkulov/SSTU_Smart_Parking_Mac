<#import "../parts/common.ftlh" as c>
<@c.page>
        <#list parkings as parking>
            <#if (parking?index %3==0) || (parking?index == 0)>
                <div class="row mt-5 equal">
            </#if>
            <div class="col-sm-4">
                <div class="card" style="height: 32rem">
                    <a href="/parking/${parking.id}">
                        <#if parking.imageName??>
                            <img src="/img/parking/${parking.imageName}" class="card-img-top img-fluid" style="height: 25rem">
                        </#if>
                        <div class="card-body">
                            <h5 class="card-title">${parking.getDescription()}</h5>
                            <#list placeNumbers as key, value>
                                <#if key == parking.getId()>
                                    <p class="card-text">Количество парковочных мест: ${value}</p>
                                </#if>
                            </#list>
                        </div>
                    </a>
                </div>
            </div>
            <#if (parking?index %3==2) || !(parking_has_next)>
                </div>
            </#if>

        </#list>
</@c.page>