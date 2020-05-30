<#import "../parts/reportNav.ftlh" as rN>
<#import "../parts/dateReport.ftlh" as r>
<@rN.reportNav>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-5" role="alert">
            ${message}
        </div>
    </#if>
    <#if personId??><h4>Отчет о водителе</h4> </#if>
    <form method="post" action="/report/simple" class="col-sm-4">
        <@r.dateReport/>
        <input type="hidden" name="_csrf" value="${_csrf.token}" class="col-sm-4"/>
        <#if personId??><input type="hidden" name="personId" value="${personId}"/></#if>
        <#if fileName??>
            <input type="hidden" name="fileName" class="fileName" value="${fileName}" class="col-sm-4"/>
        </#if>
        <button type="submit" class="btn btn-primary mt-3 send">Генерация отчета</button>
    </form>

    <script>
        var success = '${(success??)?string('yes', 'no')}';

        $(document).ready(function () {

            var fileName = $("input.fileName").val();

            function download() {
                window.location.href = '/report/download/' + fileName;
                setTimeout(deleteReport, 5000);
            }

            function deleteReport() {
                $.ajax({
                    url: '/report/delete/'+fileName
                });
            }

            if (success === 'yes') {
                download();
            }
        });

    </script>
</@rN.reportNav>