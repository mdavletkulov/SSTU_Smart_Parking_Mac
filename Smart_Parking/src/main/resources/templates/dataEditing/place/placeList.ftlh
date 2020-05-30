<#import "../../parts/common.ftlh" as c>

<@c.page>

    <#if message?has_content>
        <div class="alert alert-${messageType} col-4" role="alert">
            ${message}
        </div>
    </#if>
    <div>
        <a href="/dataEdit"
           class="btn btn-dark stretched-link my-3">Назад</a>
    </div>
    <div><h5 class="mb-3">Парковочные места</h5>
    </div>

    <table class="table table-striped text-center table-hover table-dark" id="places">
        <thead>
        <tr>
            <th class="align-middle" scope="col">Парковка</th>
            <th class="align-middle" scope="col">Номер места</th>
            <th class="align-middle" scope="col">Место для инвалидов</th>
            <th></th>
            <th class="align-middle" scope="col">
                <a href="/dataEdit/place/add" class="btn btn-primary stretched-link">
                    Добавить новое парковочное место</a></th>
        </tr>
        </thead>
        <tbody>
        <#list places as place>
            <tr>
                <th class="align-middle" scope="row">${place.getParking().getDescription()}</th>
                <td class="align-middle" scope="row">${place.getPlaceNumber()}</td>
                <td class="align-middle" scope="row"><#if place.isSpecialStatus()>&#9745; <#else>&#x2716;</#if></td>
                <td>
                    <button id="${place.getId()}" type="button" class="btn btn-primary askDelete"
                            data-toggle="modal">
                        Удалить
                    </button>
                </td>
                <td><a href="/dataEdit/place/edit/${place.id}"
                                        class="btn btn-primary stretched-link">Редактировать</a>
                </td>

            </tr>
        </#list>
        </tbody>
    </table>

    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Подтвердите удаление</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Вы действительно хотите удалить парковочное место?
                </div>
                <div class="modal-footer">
                    <button id="delete" type="button" class="btn btn-secondary">Удалить</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Отменить</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        var placeId;
        jQuery(document).ready(function ($) {

            $('#places').DataTable({
                "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Все"]],
                columnDefs: [
                    {width: '45%', targets: 0},
                    {width: '10%', targets: 1},
                    {width: '10%', targets: 2},
                    {width: '20%', "orderable": false, targets: 3},
                    {width: '15%', "orderable": false, targets: 4},
                ],
                language: {
                    url: '/static/localization/Russian.json'
                },
                fixedColumns: true
            });

            $(document).on('click', 'button.askDelete', function(e) {
                jQuery.noConflict();
                $('#exampleModal').modal('show');
                placeId = $(this).attr('id');
            });
        });

        $('button#delete').click(function () {
            window.location.href = '/dataEdit/place/delete/' + placeId;
            return false;
        });
    </script>

</@c.page>