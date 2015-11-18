<#assign menu="user">
<#assign submenu="user_list">
<#include "../head.ftl">
<style type="text/css">
.pagination {
    border-radius: 4px;
    display: inline-block;
    margin: 0;
    padding-left: 0;
}
</style>
	<!--main content start-->
	<section id="main-content">
		<section class="wrapper">
        	<!-- page start-->
            <section class="panel">
            	<header class="panel-heading">
               				                <div class="row">
		                  		<div class="col-lg-4">
							<ul class="breadcrumb" style="margin-bottom:0px;">
								<li>
									<a href="${BASE_PATH}/manage/article/list.htm">所有用户信息</a>
								</li>
							</ul>
						   </div>
						   <div class="col-lg-8">
								<a class="btn btn-primary" style="float:right;" href="${BASE_PATH}/manage/user/add.htm">增加用户</a>
						   </div>
				</div>
                </header>
                <div class="panel-body">
                	<div class="adv-table">
                    	<div role="grid" class="dataTables_wrapper" id="hidden-table-info_wrapper">
                            <table class="table table-striped table-advance table-hover">
                            	<thead>
                                	<tr>
                						<th>用户名称</th>
                						<th>昵称</th>
                						<th>姓名</th>
                						<th>时间</th>
                						<th>操作</th>
              						</tr>
                                </thead>
                            	<tbody role="alert" aria-live="polite" aria-relevant="all">
                            		<#list pageVo.list as e>
                            		<tr class="gradeA odd">
                            			<td class="js_userId">${e.userId}</td>
                                    	<td>${e.username}</td>
                                    	<td>${e.nickname}</td>
                                    	<td>${e.name}</td>
                                    	<td>${e.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    	<td>
                  							<!-- Icons -->
                							<a href="${BASE_PATH}/manage/user/update.htm?userId=${e.userId}" title="修改">
                								<button class="btn btn-primary btn-xs">
                									<i class="icon-pencil"></i>
                								</button>
                							</a>
                							<a href="javascript:void(0);" userId="${e.userId}" title="删除"  class="js_delete_user" id="delete">
                  								<button class="btn btn-danger btn-xs">
                  									<i class="icon-trash "></i>
                  								</button>
                  							</a>
                						</td>
                                	</tr>
                                	</#list>
                               	</tbody>
                              </table>
                              <div style="height: 30px;">
                             	<div class="pagination">${pageVo.pageNumHtml} </div>
                              </div>
                           </div>
                        </div>
                  </div>
              </section>
              <!-- page end-->
          </section>
		</section>
		<!--main content end-->
<script>
$(function(){
	$(".js_userId").hide();
	
	$('.js_delete_user').click(function() {
		var userId= $(this).attr('userId');
        bootbox.dialog({
            message: "是否" + $(this).attr('title') + "用户",
            title: "提示",
            buttons: {
                "delete": {
                    label: "删除",
                    className: "btn-success",
                    callback: function() {
                        $.post("${BASE_PATH}/manage/user/delete.json", {
                            "userId": userId
                        },
                        function(data) {
                            if (data.result) {
                                bootbox.alert("删除成功",
                                function() {
                                   window.location.reload();
                                });
                            } else {
                                bootbox.alert(data.msg,
                                function() {});
                            }
                        },
                        "json");
                    }
                },
                "cancel": {
                    label: "取消",
                    className: "btn-primary",
                    callback: function() {}
                }
            }
        });
	});
});
</script>
<#include "../foot.ftl">
