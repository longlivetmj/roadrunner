<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- META SECTION -->
    <title>Road Runner</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <link rel="icon" href="favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" id="theme" href="../../theme/css/theme-default.css"/>
</head>
<body>
<form method="post" name="systemSelection" id="systemSelection" action="/companySelection">
    <div class="error-container">
        <div class="error-text">Company Profile Selection</div>
        <div class="error-subtext">Please Select the Company of your Preference to access the Road Runner Application</div>
        <div class="row">
            <div class="form-group">
                <h3 class="text-center">Available Companies</h3>
                <div class="col-md-12 col-xs-12 input-lg input-group-lg">
                    <select name="companyProfileSeq" id="companyProfileSeq" class="form-control select">
                        <c:forEach items="${companyProfileList}" var="companyProfile">
                            <option value="${companyProfile.companyProfileSeq}">${companyProfile.companyName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="error-actions">
            <div class="row" style="padding-top: 10px">
                <div class="col-md-6">
                    <button name="operation" value="changeCompany" type="submit" class="btn btn-info btn-block btn-lg">
                        Proceed
                    </button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="hidden" name="_csrf_header" value="${_csrf.headerName}">
                </div>
                <div class="col-md-6">
                    <a href="/logout">
                        <button class="btn btn-primary btn-block btn-lg">Logout</button>
                    </a>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>






