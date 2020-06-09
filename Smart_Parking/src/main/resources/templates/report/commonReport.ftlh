<#import "../parts/reportNav.ftlh" as rN>
<#import "../parts/dateReport.ftlh" as r>

<@rN.reportNav>
    <#if message?has_content>
        <div class="alert alert-${messageType} col-5" role="alert">
            ${message}
        </div>
    </#if>
    <#if violation??> <h4>Отчет о нарушениях</h4> </#if>
    <#if employee??><h5>Отчет о сотрудниках</h5> </#if>
    <#if student??><h5>Отчет о студентах</h5> </#if>
    <#if !student?? && !employee??><h5>Общий отчет</h5> </#if>
    <form method="post" action="/report/common" class="col-sm-4">
        <div class="form-group  division">
            <label class="division" for="division" >Институт (опционально)</label>
            <select class="form-control division" name="division" >
                <option selected="selected" value=""/>
                <#list divisions as division>
                    <option>${division.getName()}</option>
                </#list>
            </select>
        </div>

        <div class="form-group  typesJob">
            <label class="typesJob" for="typesJob"  hidden="hidden">Категория сотрудника
                (опционально)</label>
            <select class="form-control typesJob" name="typesJob"  hidden="hidden">
                <option selected="selected" value=""/>
                <#list typesJob as typeJob>
                    <option>${typeJob}</option>
                </#list>
            </select>
        </div>

        <div class="form-group  subdivisions">
            <label class="subdivision" for="subdivision" hidden="hidden">Кафедра/Подразделение (опционально)</label>
            <select class="form-control subdivision" name="subdivision" hidden="hidden">
            </select>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if employee??>
            <input type="hidden" name="employee" value="${employee}"/>
        </#if>
        <#if student??>
            <input type="hidden" name="student" value="${student}"/>
        </#if>
        <#if violation??>
            <input type="hidden" name="violation" value="${violation}"/>
        </#if>
        <#if fileName??>
            <input type="hidden" name="fileName" class="fileName" value="${fileName}"/>
        </#if>
        <@r.dateReport/>
        <button type="submit" class="btn btn-primary mt-3 send">Генерация отчета</button>
    </form>
    <script>


        var divisions = [<#list divisions as division>'${division.getName()}', </#list>];
        var _csrf = '${_csrf.token}';
        var divisionsSelect = $("select.division");
        var divisionsLabel = $("label.division");
        var typesJobSelect = $("select.typesJob");
        var typesJobLabel = $("label.typesJob");
        var subdivisionSelect = $("select.subdivision");
        var subdivisionLabel = $("label.subdivision");
        var employee = '${(employee??)?string('yes', 'no')}';
        var success = '${(success??)?string('yes', 'no')}';


        $(document).ready(function () {

            var fileName = $("input.fileName").val();

            function download() {
                window.location.href = '/report/download/' + fileName;
                setTimeout(deleteReport, 5000);
            }

            function deleteReport() {
                $.ajax({
                    url: '/report/delete/' + fileName
                });
            }

            if (success === 'yes') {
                download();
            }
            subdivisionSelect.empty();
            if (employee === 'yes') {
                typesJobSelect.change(function () {
                    var selectedTypeJob = $(this).children("option:selected").val();
                    var selectedDivision = divisionsSelect.children("option:selected").val();
                    subdivision(selectedDivision, selectedTypeJob);
                });
            }
            divisionsSelect.change(function () {
                if (!isEmpty($(this).children("option:selected"))) {
                    if (employee === 'yes') {
                        typesJob();
                    } else {
                        var selectedDivision = $(this).children("option:selected").val();
                        subdivision(selectedDivision);
                    }
                } else {
                    typesJobSelect.attr("hidden", "hidden");
                    typesJobLabel.attr("hidden", "hidden");
                    subdivisionSelect.empty();
                    subdivisionSelect.attr("hidden", "hidden");
                    subdivisionLabel.attr("hidden", "hidden");
                }

            });

        });


        function subdivision(selectedDivision, selectedTypeJob) {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "/report/common/subdivision?division=" + selectedDivision +"&typeJob=" + selectedTypeJob,
                dataType: 'json',
                cache: false,    //This will force requested pages not to be cached by the browser
                timeout: 600000,
                success: function (data) {
                    subdivisionSelect.empty();
                    subdivisionSelect.removeAttr("hidden");
                    subdivisionLabel.removeAttr("hidden");
                    subdivisionSelect
                        .append('<option selected="selected" value=""/>');
                    for (var i = 0; i < data.length; i++) {
                        populate('select.subdivision', data[i]);
                    }
                }
            });
        }

        function populate(selector, name) {
            $(selector)
                .append('<option value="' + name + '">' + name + '</option>')
        }

        function division(selectedTypeJob) {
            if (selectedTypeJob === 'ППС') {
                divisionsSelect.empty();
                divisionsSelect.removeAttr("hidden");
                divisionsLabel.removeAttr("hidden");
                divisionsSelect
                    .append('<option selected="selected" value=""/>');
                for (var i = 0; i < divisions.length; i++) {
                    populate('select.division', divisions[i]);
                }
            } else {
                divisionsSelect.empty();
                divisionsSelect.attr("hidden", "hidden");
                divisionsLabel.attr("hidden", "hidden");
                subdivisionSelect.empty();
                subdivisionSelect.attr("hidden", "hidden");
                subdivisionLabel.attr("hidden", "hidden");
            }
        }

        function typesJob() {
            typesJobSelect.removeAttr("hidden");
            typesJobLabel.removeAttr("hidden");
        }

        function isEmpty(el) {
            return !$.trim(el.html())
        }


    </script>
</@rN.reportNav>