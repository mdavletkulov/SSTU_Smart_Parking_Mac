<#macro dateReport>
    <script type="text/javascript" src="/static/js/dateReport.js"></script>
    <div class="form-group">
        <label for="startTime">Начальная дата</label>
        <div class="input-group date" id="datetimepicker1" data-target-input="nearest">
            <input type="text" id="startTime" name="startTime" class="date form-control ${(startTimeError??)?string('is-invalid', '')} datetimepicker-input"
                   data-target="#datetimepicker1"/>
            <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
                <div class="input-group-text"><i class="fa fa-calendar"></i></div>
            </div>
            <#if startTimeError??>
                <div class="invalid-feedback">
                    ${startTimeError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group">
        <label for="endTime">Конечная дата</label>
        <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
            <input name="endTime" id="endTime" type="text" class="date form-control ${(endTimeError??)?string('is-invalid', '')} datetimepicker-input"
                   data-target="#datetimepicker2"/>
            <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
                <div class="input-group-text"><i class="fa fa-calendar"></i></div>
            </div>
            <#if endTimeError??>
                <div class="invalid-feedback">
                    ${endTimeError}
                </div>
            </#if>
        </div>
    </div>
</#macro>

