<%@include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html>
<html lang="en" class="body-full-height">
<head>
    <!-- META SECTION -->
    <title>Road Runner</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <link rel="icon" href="../../theme/img/faviconCD.ico" type="image/x-icon"/>
    <!-- END META SECTION -->

    <!-- CSS INCLUDE -->
    <link rel="stylesheet" type="text/css" id="theme" href="../../theme/css/theme-default.css"/>

    <script type="text/javascript" src="../../theme/js/plugins/jquery/jquery.min.js"></script>
</head>
<body>

<div class="login-container">

    <div class="login-box animated fadeInDown">
        <div class="login-logo"></div>
        <div class="login-body">
            <div class="login-title"><strong>Welcome</strong>, Please login</div>
            <form method="post" action="/login" class="form-horizontal" >
                <div class="form-group">
                    <div class="col-md-12">
                        <input type="text" class="form-control" placeholder="Username" name="username" id="username"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12">
                        <input type="password" class="form-control" placeholder="Password" name="password" id="password"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12">
                        <label for="remember-me" style="color: white">Remember me &nbsp;</label>
                        <input type="checkbox" name="remember-me" id="remember-me">
                        <c:if test="${param.error ne null}">
                            <div class="alert-danger">Invalid username or password.</div>
                        </c:if>
                        <c:if test="${param.logout ne null}">
                            <div class="alert-normal">You have been logged out.</div>
                        </c:if>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-6">
                        <a href="#" class="btn btn-link btn-block">Forgot your password?</a>
                    </div>
                    <div class="col-md-6">
                        <button class="btn btn-info btn-block">Log In</button>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </div>
            </form>
        </div>
        <div class="login-footer">
            <div class="pull-left">
                &copy; 2018 LongLiveTMJ
            </div>
            <div class="pull-right">
                <a href="#">About</a> |
                <a href="#">Privacy</a> |
                <a href="#">Contact Us</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>






