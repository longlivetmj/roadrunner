<%@ include file="/WEB-INF/jsp/include.jsp" %>
<html lang="en">
<head>
    <!-- META SECTION -->
    <title>Road Runner</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link rel="icon" type="image/x-icon" href="../../theme/img/faviconCD.ico" />
    <link rel="stylesheet" type="text/css" id="theme" href="../../theme/css/theme-black.css"/>
    <link rel="stylesheet" type="text/css" href="../../theme/css/custom.css" />
</head>
<body onbeforeunload="force_logout()">
<!-- START PAGE CONTAINER -->
<div class="page-container">

    <!-- START PAGE SIDEBAR -->
    <div class="page-sidebar">

        <!-- START X-NAVIGATION -->
        <ul class="x-navigation">
            <li class="xn-logo">
                <a href="/">ROAD_RUNNER</a>
                <a href="#" class="x-navigation-control"></a>
                <input type="hidden" id="defaultCompanyProfileSeq" value="<c:out value="${companyProfileSeq}" />">
            </li>
            <li class="xn-profile">
                <a href="#" class="profile-mini">
                    <img src="../../theme/assets/images/users/avatar.jpg"
                         alt="${user.firstName}&nbsp;${user.secondName}"/>
                </a>

                <div class="profile" style="cursor: pointer" onclick="load_user_profile()">
                    <div class="profile-image">
                        <img src="/getLogo/${user.uploadDocumentSeq}" alt="${user.firstName}&nbsp;${user.secondName}"/>
                    </div>
                    <div class="profile-data">
                        <div class="profile-data-name">${user.firstName}&nbsp;${user.secondName}</div>
                        <div class="profile-data-title">User Profile</div>
                    </div>
                    <div class="profile-controls">
                        <a href="pages-profile.html" class="profile-control-left"><span class="fa fa-info"></span></a>
                        <a href="pages-messages.html" class="profile-control-right"><span class="fa fa-envelope"></span></a>
                    </div>
                </div>
            </li>
            <span id="sideBarData">
            <%@include file="sidebar.jsp" %>
            </span>

        </ul>

    </div>
    <!-- END PAGE SIDEBAR -->

    <!-- PAGE CONTENT -->
    <div class="page-content">

        <%@include file="header.jsp" %>

        <!-- PAGE CONTENT WRAPPER -->
        <div class="page-content-wrap" id="pageContent">


        </div>
    </div>
    <!-- START DASHBOARD CHART -->
    <div id="dashboard-area-1"></div>
</div>
<!-- END PAGE CONTENT WRAPPER -->
<div>
    <!-- END PAGE CONTENT -->
</div>
<!-- END PAGE CONTAINER -->

<!-- MESSAGE BOX-->
<div class="message-box animated fadeIn" data-sound="alert" id="mb-signout">
    <div class="mb-container">
        <div class="mb-middle">
            <div class="mb-title"><span class="fa fa-sign-out"></span> Log <strong>Out</strong> ?</div>
            <div class="mb-content">
                <p>Are you sure you want to log out?</p>

                <p>Press No if you want to continue work. Press Yes to logout current user.</p>
            </div>
            <div class="mb-footer">
                <div class="pull-right">
                    <a href="/logout" class="btn btn-success btn-lg">Yes</a>
                    <button class="btn btn-default btn-lg mb-control-close">No</button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal" style="display: none">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Change User Credentials</h4>
            </div>
            <form class="form-horizontal userProfile" id="userProfile" name="modal" >
                <div class="modal-body panel">

                    <div class="form-group">
                        <label class="col-md-3 col-xs-12 control-label">Username</label>
                        <div class="col-md-6 col-xs-12">
                            <input type="text" class="form-control"
                                   maxlength="50"
                                   readonly
                                   value="${user.username}"/>
                            <input type="hidden" name="userSeq" value="${user.userSeq}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-xs-12 control-label">Old Password</label>
                        <div class="col-md-6 col-xs-12">
                            <input type="password" name="oldPassword" id="oldPassword" class="form-control"
                                   maxlength="20" minlength="5" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 col-xs-12 control-label">New Password</label>
                        <div class="col-md-6 col-xs-12">
                            <input type="password" class="form-control"
                                   name="newPassword"
                                   id="newPassword"
                                   maxlength="20" minlength="5"
                                   aria-required="true"
                                   required/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-xs-12 control-label">Confirm Password</label>
                        <div class="col-md-6 col-xs-12">
                            <input type="password" class="form-control"
                                   name="confirmPassword"
                                   id="confirmPassword"
                                   maxlength="20" minlength="5"
                                   aria-required="true"
                                   required/>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary"
                            onclick="change_user_credentials()">Update
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- END MESSAGE BOX-->
<!-- Preloader -->
<div id="loading" style="display:none">
    <p><img alt="loading" src="../../theme/img/loaders/default.gif"/> Please Wait</p>
</div>
<div id="loadShadowing"></div>
<!--/Preloader -->

<!-- START PRELOADS -->
<audio id="audio-alert" src="../../theme/audio/alert.mp3" preload="auto"></audio>
<audio id="audio-fail" src="../../theme/audio/fail.mp3" preload="auto"></audio>
<!-- END PRELOADS -->

<!-- START SCRIPTS -->
<!-- START PLUGINS -->
<script type="text/javascript" src="../../theme/js/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/jquery/jquery-ui.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/jquery/jquery.serializejson.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/jquery/jquery.aCollapTable.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/bootstrap/bootstrap.min.js"></script>
<script type='text/javascript' src='../../theme/js/rivets.bundled.min.js'></script>
<!-- END PLUGINS -->

<!-- START THIS PAGE PLUGINS-->
<script type='text/javascript' src='../../theme/js/plugins/icheck/icheck.min.js'></script>
<script type="text/javascript" src="../../theme/js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/scrolltotop/scrolltopcontrol.js"></script>

<script type="text/javascript" src="../../theme/js/plugins/morris/raphael-min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/morris/morris.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/rickshaw/d3.v3.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/rickshaw/rickshaw.min.js"></script>
<script type='text/javascript' src='../../theme/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js'></script>
<script type='text/javascript' src='../../theme/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js'></script>
<script type="text/javascript" src="../../theme/js/plugins/owl/owl.carousel.min.js"></script>

<script type="text/javascript" src="../../theme/js/plugins/moment.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/bootstrap/bootstrap-select.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/bootstrap/ajax-bootstrap-select.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/dropzone/dropzone.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/fileinput/fileinput.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/filetree/jqueryFileTree.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/tableexport/tableExport.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/tableexport/jquery.base64.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/tableexport/html2canvas.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/tableexport/jspdf/libs/sprintf.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/tableexport/jspdf/jspdf.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/tableexport/jspdf/libs/base64.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/datatables/jquery.dataTables.custom.min.js"></script>
<!-- END THIS PAGE PLUGINS-->

<!-- START TEMPLATE -->
<script type="text/javascript" src="../../theme/js/settings.js"></script>

<script type="text/javascript" src="../../theme/js/plugins.js"></script>
<script type="text/javascript" src="../../theme/js/actions.js"></script>
<script type="text/javascript" src="../../customjs/index.js"></script>
<script type="text/javascript" src="../../customjs/form.transaction.js?V=2.0"></script>
<script type="text/javascript" src="../../theme/js/plugins/jquery-validation/validator.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/maskedinput/jquery.maskedinput.min.js"></script>
<script type='text/javascript' src='../../theme/js/plugins/noty/jquery.noty.js'></script>
<script type='text/javascript' src='../../theme/js/plugins/bootstrap/bootstrap-confirmation.min.js'></script>
<script type="text/javascript" src="../../theme/js/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/jquery/jquery.jeditable.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/jquery/jquery.jeditable.custom.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/jquery/jquery-ui-timepicker-addon.min.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/tagsinput/jquery.tagsinput.min.js"></script>
<script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyAzyI37Eo9WFfcC5U2S4adtTg0jGLMGg0c"></script>
<script type ="text/javascript" src="../../theme/js/plugins/markerwithlabel.js"></script>
<script type="text/javascript" src="../../theme/js/plugins/jquery/infobox.js"></script>
<script src="https://www.gstatic.com/firebasejs/5.5.2/firebase.js"></script>
<script>
    // Initialize Firebase
    var config = {
        apiKey: "AIzaSyAeK_SI9G64UrOYkFu-SsQrtLA0YU7a1qo",
        authDomain: "my-project-1519143246409.firebaseapp.com",
        databaseURL: "https://my-project-1519143246409.firebaseio.com",
        projectId: "my-project-1519143246409",
        storageBucket: "my-project-1519143246409.appspot.com",
        messagingSenderId: "112353755967"
    };
    firebase.initializeApp(config);
</script>
<!-- END TEMPLATE -->
<!-- END SCRIPTS -->
</body>
</html>
