<#assign config_v="20141009044">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="${shishuo_seo_description}">
    <meta name="author" content="师说CMS">
    <link rel="icon" href="${TEMPLATE_BASE_PATH}/images/favicon.ico">

    <title>${shishuo_seo_title}</title>

    <!-- Bootstrap core CSS -->
    <link href="${TEMPLATE_BASE_PATH}/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${TEMPLATE_BASE_PATH}/css/blog.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]>
    <script src="${TEMPLATE_BASE_PATH}/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <script type="text/javascript">
        window.BasePath = "${BASE_PATH}";
        window.UEDITOR_HOME_URL = "${BASE_PATH}/";
    </script>

    <script src="${TEMPLATE_BASE_PATH}/js/jquery.min.js?v=${config_v}"></script>
    <script src="${TEMPLATE_BASE_PATH}/js/ie-emulation-modes-warning.js"></script>
    <script src="${TEMPLATE_BASE_PATH}/js/jquery.form.min.js?v=${config_v}"></script>
    <script src="${TEMPLATE_BASE_PATH}/js/bootbox.min.js?v=${config_v}"></script>
    <script type="text/javascript" charset="utf-8" src="${TEMPLATE_BASE_PATH}/assets/ueditor/ueditor.config.js?v=${config_v}"></script>
    <script type="text/javascript" charset="utf-8" src="${TEMPLATE_BASE_PATH}/assets/ueditor/ueditor.all.min.js?v=${config_v}"> </script>
    <script type="text/javascript" charset="utf-8" src="${TEMPLATE_BASE_PATH}/assets/ueditor/lang/zh-cn/zh-cn.js?v=${config_v}"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="${TEMPLATE_BASE_PATH}/js/html5shiv.min.js"></script>
    <script src="${TEMPLATE_BASE_PATH}/js/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="blog-masthead">
    <div class="container">
        <nav class="blog-nav">
        <@shishuo_folder_list_tag folderId= 0>
            <a class="blog-nav-item <#if 0==g_folderId>active</#if>" href="${BASE_PATH}/index.htm">首页</a>
            <#list tag_folder_list as tag_folder>
                <a class="blog-nav-item <#if tag_folder.folderId==g_folderId>active</#if>"
                   href="<@shishuo_folder_url_tag folderId=tag_folder.folderId/>">${tag_folder.name}</a>
            </#list>
            <a class="blog-nav-item" href="${BASE_PATH}/user/logout.htm">注销</a>
        </@shishuo_folder_list_tag>
        </nav>
    </div>
</div>
<div class="container">
    <div class="blog-header">
        <h1 class="blog-title">${shishuo_seo_title}</h1>

        <p class="lead blog-description">${shishuo_seo_description}</p>
    </div>