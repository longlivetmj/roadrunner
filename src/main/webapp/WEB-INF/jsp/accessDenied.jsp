<!DOCTYPE html>
<html lang="en">
    <head>        
        <!-- META SECTION -->
        <title>Road Runner</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        
        <link rel="icon" href="favicon.ico" type="image/x-icon" />
        <!-- END META SECTION -->
        
        <!-- CSS INCLUDE -->        
        <link rel="stylesheet" type="text/css" id="theme" href="../../theme/css/theme-default.css"/>
        <!-- EOF CSS INCLUDE -->                                     
    </head>
    <body>
        <div class="error-container">
            <div class="error-code">Access Denied</div>
            <div class="error-text">You do not have Permission to view this page</div>
            <div class="error-actions">                                
                <div class="row">
                    <div class="col-md-6">
                        <button class="btn btn-info btn-block btn-lg" onClick="document.location.href = '/';">Back to dashboard</button>
                    </div>
                    <div class="col-md-6">
                        <button class="btn btn-primary btn-block btn-lg" onClick="history.back();">Previous page</button>
                    </div>
                </div>                                
            </div>
            <div class="error-subtext">Or you can use search to find anything you need.</div>
            <div class="row">
                <div class="col-md-12">
                    <div class="input-group">
                        <input type="text" placeholder="Search..." class="form-control"/>
                        <div class="input-group-btn">
                            <button class="btn btn-primary"><span class="fa fa-search"></span></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>                 
    </body>
</html>






