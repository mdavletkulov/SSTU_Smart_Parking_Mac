<#include "parts/security.ftlh">
<#import "parts/login.ftlh" as l>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Carousel Template for Bootstrap</title>
    <base href="/">
    <!-- Bootstrap core CSS -->
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.25.3/moment.min.js"></script>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.25.3/locale/ru.min.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="/static/car.css">
    <link rel="stylesheet" href="/static/style.css">
</head>
<body>

<header>
    <nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="/">Умная Парковка</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
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
</header>

<main role="main">

    <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img class="first-slide" src="/img/carusel_1.jpg" alt="First slide">
                <div class="container">
                    <div class="carousel-caption">
                        <div class="caption-text">
                            <h1>Система "Умная парковка"</h1>
                            <p>Система "Умная парковка" предназначена для автоматизированного управления парковочными местами университета
                                и может быть масштабирована до уровня большого города</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <img class="second-slide" src="/img/carusel_2.jpg" alt="Second slide">
                <div class="container">
                    <div class="carousel-caption" style="color: black;">
                        <div class="caption-text">
                            <h1>Выявление нарушений</h1>
                            <p>Система автоматически определяет нарушения парковки: проезд на парковку без пропуска или с проспроченным пропуском,
                                нелегальная поставновка автомобиля на парковочное место для людей с ограниченными возможностями</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <img class="third-slide" src="/img/carusel_3.jpg" alt="Third slide">
                <div class="container">
                    <div class="carousel-caption" style="color: black;">
                        <div class="caption-text">
                            <h1>Использование современных технологий</h1>
                            <p>Система разработана с помощью передовых технологий в сфере умного города,
                                которые используются в инновационных проектах по всему миру</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <a class="carousel-control-prev" href="#myCarousel" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#myCarousel" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>


    <!-- Marketing messaging and featurettes
    ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->
    <div class="container marketing">
        <div class="row featurette">
        </div>
        <hr class="featurette-divider">
        <div class="row featurette">
        </div>
        <!-- Three columns of text below the carousel -->
        <div class="row">
            <div class="col-lg-4">
                <img class="rounded-circle" src="/img/main_parking.jpg" alt="Generic placeholder image" width="140"
                     height="140">
                <h2>Автоматизация постановки автомобиля на парковку</h2>
                <p>Самостоятельная работа системы, основанной на новейшихх технологиях в сфере умного города
                    с участием лишь одного человека,
                    который будет следить за изменением состояния парковочных мест
                    при помощи веб-интерфейса</p>
            </div><!-- /.col-lg-4 -->
            <div class="col-lg-4">
                <img class="rounded-circle" src="/img/main_safety.jpg" alt="Generic placeholder image" width="140"
                     height="140">
                <h2>Безопасность вашего автомобиля на парковочной площадке</h2>
                <p>Обеспечение безопасности автомобилей на парковочной площадке благодаря
                    использованию специальных камер, которые определяют номер каждой машины,
                    заезжающей на парковку</p>
            </div><!-- /.col-lg-4 -->
            <div class="col-lg-4 ">
                <img class="rounded-circle" src="/img/special.jpg" alt="Generic placeholder image" width="140"
                     height="140">
                <h2>Для людей с ограниченными возможностями</h2>
                <p>Система контролирует правомерность парковки
                    на места со специальным статусом, следовательно,
                    количество нарушений правил парковки на этих места уменьшается,
                    и такие места чаще будут оставать свободными</p>
            </div><!-- /.col-lg-4 -->
        </div><!-- /.row -->


        <!-- START THE FEATURETTES -->

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading ml-3"> <span
                            class="text-muted">Проблема парковки автомобиля </span></h2>
                <p class="lead ml-3">В наше время с увеличением количества автомобилей,
                    дорожного трафика увеличивается загруженность улиц,
                    вследствие чего наблюдается нехватка парковочных мест,
                    повышение уровня аварийности на дорогах,
                    что в настоящее время является общемировой проблемой мегаполисов,
                    которая в настоящее время решается с помощью активного использования цифровых технологий.</p>
            </div>
            <div class="col-md-5">
                <img class="featurette-image img-fluid mx-auto" src="/img/feature_1.jpg">
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7 order-md-2">
                <h2 class="featurette-heading"><span
                            class="text-muted">Умная парковка</span></h2>
                <p class="lead">Умная парковка – специализированное место для парковки автомобилей,
                    созданное с использованием датчиков, видеокамер и современных цифровых технологий
                    для быстрого и удобного поиска парковочных мест,
                    обеспечения безопасности постановки автомобиля на стоянку.</p>
            </div>
            <div class="col-md-5 order-md-1">
                <img class="featurette-image img-fluid mx-auto ml-5"  src="/img/feature_2.jpg">
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading ml-3"><span class="text-muted">Мобильное приложение</span></h2>
                <p class="lead ml-3">Для работы с системой "Умная парковка" вы можете воспользоваться
                    разработанным нами мобильным приложением,
                    которое умеет показывать свободные и занятые места на парковочной
                    площадке и сэкономит вам огромное количество времени</p>
            </div>
            <div class="col-md-5">
                <img class="featurette-image img-fluid mx-auto" data-src="holder.js/500x500/auto">
            </div>
        </div>

        <hr class="featurette-divider">
        <div class="row featurette mb-5">
        </div>

        <!-- /END THE FEATURETTES -->

    </div><!-- /.container -->

    <!-- FOOTER -->
    <footer class="page-footer font-small bg-dark">

        <!-- Copyright -->
        <div class="footer-copyright text-center py-3 text-white">© 2020 Copyright:
            <a href="#"> Davletkulov Maksim</a>
        </div>
        <!-- Copyright -->

    </footer>
</main>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>
<!-- <script>window.jQuery || document.write('<script src="../../../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script> -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/2.4.0/umd/popper.min.js"></script>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/js/bootstrap.min.js"></script>
<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/holder/2.9.7/holder.min.js"></script>
</body>
</html>
