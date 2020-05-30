<#import "../parts/common.ftlh" as c>

<@c.page>
    <h5 class="mb-3"> Список пользователей </h5>
    <#if message?has_content>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
    <table id="users" class="table table-striped no-border table-dark" style="width:100%">
        <thead>
        <tr>
            <th scope="col">E-mail</th>
            <th scope="col">Пользователь</th>
            <th scope="col">Роль</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
        <#if user.username != currentUser.username>
        <tr>
            <td>${user.username}</td>
            <td>${user.getFullName()}</td>
            <td>${user.getRole()}</td>
            <td><a href="/user/edit/${user.id}" class="btn btn-primary stretched-link">Редактировать</a>
            </td>
            <td>
                <button id="${user.getId()}" type="button" class="btn btn-primary askDelete" data-toggle="modal">
                    Удалить
                </button>
            </td>
            </#if>
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
                    Вы действительно хотите удалить пользователя?
                </div>
                <div class="modal-footer">
                    <button id="delete" type="button" class="btn btn-secondary">Удалить</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Отменить</button>
                </div>
            </div>
        </div>
    </div>

    <style>
        table#persons{
            margin: 0 auto;
            width: 100%;
            clear: both;
            border-collapse: collapse;
            table-layout: fixed; // ***********add this
        word-wrap:break-word; // ***********and this
        }
        </style>

    <script>
        var userId;
        jQuery(document).ready(function ($) {

            $('#users').DataTable( {
                "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
                columnDefs: [
                    { width: '30%', targets: 0 },
                    { width: '30%', targets: 1 },
                    { width: '10%', targets: 2 },
                    { width: '20%', "orderable": false, targets: 3 },
                    { width: '10%', "orderable": false, targets: 4 },
                ],
                language: {
                    url: 'static/localization/Russian.json'
                },
                fixedColumns: true
            } );


            $('button.askDelete').click(function () {
                jQuery.noConflict();
                $('#exampleModal').modal('show');
                userId = $(this).attr('id');
            });
        });

        $('button#delete').click(function() {
            window.location.href = '/user/delete/'+userId;
            return false;
        });
    </script>

</@c.page>