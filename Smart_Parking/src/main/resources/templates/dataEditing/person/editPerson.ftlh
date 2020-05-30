<#import "../../parts/common.ftlh" as c>

<@c.page>

    <#if message?has_content>
        <div class="alert alert-${messageType} " role="alert">
            ${message}
        </div>
    </#if>
    <div>
        <a href="/dataEdit/person"
           class="btn btn-dark stretched-link my-3">Назад</a>
    </div>
    <h5>Водитель</h5>
    <form action="/dataEdit/person/edit/${person.getId()}" method="post" class="mt-2 col-sm-4">
        <div class="form-group">
            <label for="firstName">Имя</label>
            <input type="text" class="form-control ${(firstNameError??)?string('is-invalid', '')} "
                   id="firstName"
                   value="${person.firstName}"
                   placeholder="Введите имя водителя"
                   name="firstName">
            <#if firstNameError??>
                <div class="invalid-feedback">
                    ${firstNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="secondName">Фамилия</label>
            <input type="text" class="form-control ${(secondNameError??)?string('is-invalid', '')} "
                   id="secondName"
                   value="${person.secondName}"
                   placeholder="Введите фамилию водителя"
                   name="secondName">
            <#if secondNameError??>
                <div class="invalid-feedback">
                    ${secondNameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="middleName">Отчество</label>
            <input type="text" class="form-control ${(middleNameError??)?string('is-invalid', '')} "
                   id="middleName"
                   value="<#if person.middleName??>${person.middleName}</#if>"
                   placeholder="Введите отчество водителя"
                   name="middleName">
            <#if middleNameError??>
                <div class="invalid-feedback">
                    ${middleNameError}
                </div>
            </#if>
        </div>
        <div>
            <div class="btn-group btn-group-toggle form-group" data-toggle="buttons">
                <label class="btn btn-secondary student <#if person.isStudent()> focus active </#if>">
                    <input <#if person.isStudent()> checked="checked"</#if> type="radio" name="student" id="student">
                    Студент
                </label>
                <label class="btn btn-secondary employee <#if person.isEmployee()> focus active </#if>">
                    <input <#if person.isEmployee()> checked="checked"</#if> type="radio" name="employee" id="employee">
                    Сотрудник
                </label>
            </div>
            <#if statusError??>
                <div class="invalid-feedback mb-3" style="display: block">
                    ${statusError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label class="course" for="course" <#if !person.isStudent()>hidden="hidden"</#if>>Курс</label>
            <input type="text" class="course form-control ${(courseError??)?string('is-invalid', '')} "
                   id="course"
                   value="<#if person.getCourse()??>${person.course}</#if>"
                   placeholder="Введите курс водителя"
                   name="course"
                   <#if !person.isStudent()>hidden="hidden"</#if>>
            <#if courseError??>
                <div class="invalid-feedback">
                    ${courseError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label class="group" for="groupName" <#if !person.isStudent()>hidden="hidden"</#if>>Группа</label>
            <input type="text" class="group form-control ${(groupNameError??)?string('is-invalid', '')} "
                   id="groupName"
                   value="<#if person.getGroupName()??>${person.groupName}</#if>"
                   placeholder="Введите группу водителя"
                   name="groupName"
                   <#if !person.isStudent()>hidden="hidden"</#if>>
            <#if groupNameError??>
                <div class="invalid-feedback">
                    ${groupNameError}
                </div>
            </#if>
        </div>
        <div class="form-group jobPosition">
            <label class="jobPosition" for="jobPosition"
                   <#if !person.isEmployee()>hidden="hidden"</#if>>Должность</label>
            <select class="form-control jobPosition " name="jobPosition"
                    <#if !person.isEmployee()>hidden="hidden"</#if>>
                <option value=""/>
                <#list jobPositions as jobPosition>
                    <option <#if person.getJobPosition()?? && person.getJobPosition() == jobPosition> selected="selected"</#if>
                            value="${jobPosition.getId()}">${jobPosition.getNamePosition()}</option>
                </#list>
            </select>
            </label>
            <#if jobPositionError??>
                <div class="invalid-feedback mb-3" style="display: block">
                    ${jobPositionError}
                </div>
            </#if>
        </div>
        <div class="form-group division">
            <label class="division" for="division">Институт</label>
            <select class="form-control division " name="division">
                <option selected="selected" value=""/>
                <#list divisions as division>
                    <option <#if person.getDivision()?? && person.getDivision() == division> selected="selected"</#if>
                            value="${division.getId()}">${division.getName()}</option>
                </#list>
            </select>
            <#if divisionErr??>
                <div class="invalid-feedback mb-3" style="display: block">
                    ${divisionErr}
                </div>
            </#if>
        </div>

        <div class="form-group subdivision">
            <label class="subdivision" for="subdivision"
                   <#if !person.getSubdivision()??>hidden="hidden"</#if> >Кафедра</label>
            <select class="form-control subdivision " name="subdivision"
                    <#if !person.getSubdivision()??>hidden="hidden"</#if>>
                <option selected="selected" value=""></option>
                <#if subdivisions??>
                <#list subdivisions as subdivision>
                    <option <#if person.getSubdivision()?? && person.getSubdivision() == subdivision> selected="selected"</#if>
                            value="${subdivision.getId()}">${subdivision.getName()}</option>
                </#list>
                </#if>
            </select>
        </div>
        <div class="form-group">
            <label for="passNum">Номер пропуска</label>
            <input type="text" class="form-control ${(passError??)?string('is-invalid', '')} "
                   id="passNum"
                   value="<#if person.getPassNum()??>${person.getPassNumString()}</#if>"
                   placeholder="Введите номер пропуска водителя"
                   name="passNum"
                   onblur="this.value=removeSpaces(this.value);">
            <#if passError??>
                <div class="invalid-feedback">
                    ${passError}
                </div>
            </#if>
        </div>

        <div class="form-group ">
            <label for="passEndDate">Срок истечения пропуска</label>
            <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
                <input type="text" id="passEndDate" name="passEndDate"
                       class="form-control  datetimepicker-input ${(passDateError??)?string('is-invalid', '')}"
                       data-target="#datetimepicker2" placeholder="Введите дату истечения пропуска"
                       value="<#if person.getPassEndDate()??>${person.getStringPassEndDate()}</#if> "/>
                <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                </div>
            </div>
            <#if passDateError??>
                <div style="display: block" class="invalid-feedback">
                    ${passDateError}
                </div>
            </#if>
        </div>

        <div class="form-check">
            <input name="specialStatus" type="checkbox" <#if person.isSpecialStatus()> checked="checked"</#if>
                   class="form-check-input ${(specialStatusError??)?string('is-invalid', '')}" id="specialStatus">
            <label class="form-check-label" for="exampleCheck1">Специальный статус</label>
            <#if specialStatusError??>
                <div class="invalid-feedback">
                    ${specialStatusError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary mt-3">Обновить</button>
    </form>

    <script>

        var _csrf = '${_csrf.token}';
        var divisionsSelect = $("select.division");
        var divisionsLabel = $("label.division");
        var subdivisionSelect = $("select.subdivision");
        var subdivisionLabel = $("label.subdivision");
        var jobSelect = $("select.jobPosition");
        var jobLabel = $("label.jobPosition");
        var groupInput = $("input.group");
        var groupLabel = $("label.group");
        var courseInput = $("input.course");
        var courseLabel = $("label.course");
        var jobs = [<#list jobPositions as jobPosition>['${jobPosition.getNamePosition()}', "${jobPosition.getId()}"], </#list>];
        var error = "${(passDateError??)?string('yes', 'no')}";

        $(document).ready(function () {

            function removeSpaces(string) {
                return string.split(' ').join('');
            }

            $("label.student").click(function () {
                jobSelect.empty();
                jobSelect.attr("hidden", "hidden");
                jobLabel.attr("hidden", "hidden");
                courseInput.empty();
                courseInput.removeAttr("hidden");
                courseLabel.removeAttr("hidden");
                groupInput.empty();
                groupInput.removeAttr("hidden");
                groupLabel.removeAttr("hidden");
            });

            $("label.employee").click(function () {
                courseInput.empty();
                courseInput.attr("hidden", "hidden");
                courseLabel.attr("hidden", "hidden");
                groupInput.empty();
                groupInput.attr("hidden", "hidden");
                groupLabel.attr("hidden", "hidden");
                jobSelect.empty();
                jobSelect.removeAttr("hidden");
                jobLabel.removeAttr("hidden");
                jobSelect.append('<option selected="selected" value=""/>');
                console.log(jobs);
                for (var i = 0; i < jobs.length; i++) {
                    populateJob('select.jobPosition', jobs[i][0], jobs[i][1]);
                }
            });

            divisionsSelect.change(function () {
                if (!isEmpty($(this).children("option:selected"))) {
                    var selectedDivision = $(this).children("option:selected").text();
                    subdivision(selectedDivision);
                } else {
                    subdivisionSelect.empty();
                    subdivisionSelect.attr("hidden", "hidden");
                    subdivisionLabel.attr("hidden", "hidden");
                }

            });
        });

        function subdivision(selectedDivision) {
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "/report/common/subdivision/" + selectedDivision,
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

        function populateJob(selector, name, id) {
            $(selector)
                .append('<option value="' + id + '">' + name + '</option>')
        }

        function isEmpty(el) {
            return !$.trim(el.html())
        }

        $(function () {
            $('#datetimepicker2').datetimepicker({
                locale: 'ru',
                viewMode: 'days',
                format: 'DD/MM/YYYY',
                minDate: new Date()
            });
        });
    </script>
</@c.page>