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
    <div><h5 class="mb-3">Цвета автомобилей</h5>
    </div>
    <table class="table table-striped text-center table-hover table-dark" id="colors" style="width:45%">
        <thead>
        <tr>
            <th class="align-middle" scope="col">Цвет</th>
            <th></th>
            <th class="align-middle" scope="col">
                <a href="/dataEdit/color/add" class="btn btn-primary stretched-link">
                    Добавить новый цвет</a></th>
        </tr>
        </thead>
        <tbody>
        <#list colors as color>
            <tr>
                <th class="align-middle" scope="row">${color.getName()}</th>
                <td class="align-middle">
                    <button id="${color.getId()}" type="button" class="btn btn-primary askDelete"
                            data-toggle="modal">
                        Удалить
                    </button>
                </td>
                <td class="align-middle"><a href="/dataEdit/color/edit/${color.id}"
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
                    Вы действительно хотите удалить цвет?
                </div>
                <div class="modal-footer">
                    <button id="delete" type="button" class="btn btn-secondary">Удалить</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Отменить</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        var colorId;
        jQuery(document).ready(function ($) {
            $('#colors').DataTable({
                "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Все"]],
                columnDefs: [
                    {width: '60%', targets: 0},
                    {width: '15%', "orderable": false, targets: 1},
                    {width: '25%', "orderable": false, targets: 2},
                ],
                language: {
                    url: '/static/localization/Russian.json'
                },
                fixedColumns: true
            });

            $(document).on('click', 'button.askDelete', function(e) {
                jQuery.noConflict();
                $('#exampleModal').modal('show');
                colorId = $(this).attr('id');
            });

            $('button#delete').click(function () {
                window.location.href = '/dataEdit/color/delete/' + colorId;
                return false;
            });
        });
    </script>

</@c.page>