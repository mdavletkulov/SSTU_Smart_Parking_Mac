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
    <div><h5 class="mb-3">Автомобили</h5>
    </div>

    <table class="table table-striped text-center table-hover table-dark" id="autos" style="width:100%">
        <thead>
        <tr>
            <th class="align-middle" scope="col">Водитель</th>
            <th class="align-middle" scope="col">Модель</th>
            <th class="align-middle" scope="col">Номер</th>
            <th class="align-middle" scope="col">Цвет</th>
            <th class="align-middle"  scope="col">
            </th>
            <th class="align-middle"  scope="col"><a href="/dataEdit/auto/add"
                                                                class="btn btn-primary stretched-link">
                    Добавить новый автомобиль</a></th>
        </tr>
        </thead>
        <tbody>
        <#list automobiles as automobile>
            <tr>
                <th class="align-middle" scope="row"><#if automobile.getPerson()??>${automobile.getPerson().getFullName()}<#else>&#x2716;</#if></th>
                <td class="align-middle"><#if automobile.getModel()??>${automobile.getModel()}<#else>&#x2716;</#if></td>
                <td class="align-middle">${automobile.getNumber()}</td>
                <td class="align-middle"><#if automobile.getColor()??>${automobile.getColor().getName()} <#else>&#x2716;</#if></td>
                <td class="align-middle">
                    <button id="${automobile.getId()}" type="button" class="btn btn-primary askDelete"
                            data-toggle="modal">
                        Удалить
                    </button>
                </td>
                <td class="align-middle"><a href="/dataEdit/auto/edit/${automobile.id}"
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
                    Вы действительно хотите удалить автомобиль?
                </div>
                <div class="modal-footer">
                    <button id="delete" type="button" class="btn btn-secondary">Удалить</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Отменить</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        var automobileId;
        jQuery(document).ready(function ($) {
            $(document).on('click', 'button.askDelete', function(e) {
                jQuery.noConflict();
                $('#exampleModal').modal('show');
                automobileId = $(this).attr('id');
            });

            $('#autos').DataTable({
                "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Все"]],
                columnDefs: [
                    {width: '30%', targets: 0},
                    {width: '20%', targets: 1},
                    {width: '15%',  targets: 2},
                    {width: '13%',  targets: 3},
                    {width: '5%', "orderable": false, targets: 4},
                    {width: '17%', "orderable": false, targets: 5},
                ],
                language: {
                    url: '/static/localization/Russian.json'
                },
                fixedColumns: true
            });
            $('button#delete').click(function () {
                window.location.href = '/dataEdit/auto/delete/' + automobileId;
                return false;
            });
        });



    </script>

</@c.page>