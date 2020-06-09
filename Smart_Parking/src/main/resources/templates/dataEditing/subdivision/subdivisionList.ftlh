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
    <div><h5 class="mb-3">Кафедры</h5>
    </div>
    <table class="table table-striped text-center table-hover table-dark" id="subdivisions" style="width:80%">
        <thead >
        <tr>
            <th class="align-middle" scope="col">Институт</th>
            <th class="align-middle" scope="col">Категория</th>
            <th class="align-middle" scope="col">Кафедра/Подразделение</th>
            <th></th>
            <th class="align-middle"  scope="col">
                <a href="/dataEdit/subdivision/add" class="btn btn-primary stretched-link">
                    Добавить новую кафедру</a></th>
        </tr>
        </thead>
        <tbody>
        <#list subdivisions as subdivision>
            <tr>
                <th class="align-middle" scope="row"><#if subdivision.getDivision()??>${subdivision.getDivision().getName()}<#else>&#x2716;</#if></th>
                <td class="align-middle" scope="row">${subdivision.getTypeJobPosition()}</td>
                <td class="align-middle" scope="row">${subdivision.getName()}</td>
                <td>
                    <button id="${subdivision.getId()}" type="button" class="btn btn-primary askDelete"
                            data-toggle="modal">
                        Удалить
                    </button>
                </td>
                <td><a href="/dataEdit/subdivision/edit/${subdivision.id}"
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
                    Вы действительно хотите удалить кафедру?
                </div>
                <div class="modal-footer">
                    <button id="delete" type="button" class="btn btn-secondary">Удалить</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Отменить</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        var subdivisionId;
        jQuery(document).ready(function ($) {
            $(document).on('click', 'button.askDelete', function(e) {
                jQuery.noConflict();
                $('#exampleModal').modal('show');
                subdivisionId = $(this).attr('id');
            });
        });

        $('#subdivisions').DataTable({
            "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Все"]],
            columnDefs: [
                {width: '15%', targets: 0},
                {width: '10%', targets: 1},
                {width: '40%', targets: 2},
                {width: '20%', "orderable": false, targets: 3},
                {width: '15%', "orderable": false, targets: 4},
            ],
            language: {
                url: '/static/localization/Russian.json'
            },
            fixedColumns: true
        });

        $('button#delete').click(function () {
            window.location.href = '/dataEdit/subdivision/delete/' + subdivisionId;
            return false;
        });
    </script>

</@c.page>