<#include "security.ftlh">
<#import "login.ftlh" as l>
<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/">Умная Парковка</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <#if user??>
            <li class="nav-item">
                <a class="nav-link" href="/parking">Парковки</a>
            </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/parking/adminEvents">Парковочные события</a>
                </li>
                <#else>
            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/parking/events">Парковочные события</a>
                </li>
            </#if>
            </#if>
            <#if isAdmin || isManager>
                <li class="nav-item">
                    <a class="nav-link" href="/report">Формирование отчетов</a>
                </li>
            </#if>
            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/user/profile">Профиль</a>
                </li>
            </#if>
            <#if isAdmin>
            <li class="nav-item">
                <a class="nav-link" href="/user">Пользователи</a>
            </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/dataEdit">Редактирование данных</a>
                </li>
            </#if>
        </ul>

        <div class="navbar-text mr-3">
            <#if user??>
            ${name}</div>
        <#if isAdmin || isManager>
            <form action="/registration" method="get">
                <button type="submit" class="btn btn-primary mr-2">Зарегистрировать</button>
            </form>
        </#if>
        <@l.logout />

        <#else>
            <form action="/login" method="get">
                <button type="submit" class="btn btn-primary">Войти</button>
            </form>
        </#if>
    </div>
</nav>