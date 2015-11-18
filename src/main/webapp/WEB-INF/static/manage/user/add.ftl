<#assign menu="user">
<#assign submenu="add_user">
<#include "../head.ftl">
<style type="text/css">
.m-bot15 {
    margin-bottom: 5px;
}
.form-control {
    border: 1px solid #E2E2E4;
    box-shadow: none;
    color: #C2C2C2;
}
.input-lg {
    border-radius: 6px;
    font-size: 15px;
    height: 40px;
    line-height: 1.33;
    padding: 9px 15px；
}

</style>
		<!--main content start-->
		<section id="main-content">
			<section class="wrapper">
              <!-- page start-->
              <div class="row">
                  <div class="col-lg-12">
                      <section class="panel">
                          <header class="panel-heading">
                            	 添加用户
                          </header>
                          <div class="panel-body">
                              <form id="add_user_form" method="post" class="form-horizontal" autocomplete="off" action="${BASE_PATH}/manage/user/addNew.json">
                              	<fieldset>
                                  <div class="form-group">
                                      <label class="col-sm-2 col-sm-2 control-label">用户名称</label>
                                      <div class="col-sm-10">
                                          <input type="text" class="form-control" name="username"
                                          	placeholder="用户名" id="username" vaule="${username}">
                                      </div>
                                  </div>
                                  <div class="form-group">
                                      <label class="col-sm-2 col-sm-2 control-label">用户密码</label>
                                      <div class="col-sm-10">
                                          <input type="text" class="form-control" name="password"
                                          	placeholder="用户密码" id="password" vaule="${password}">
                                      </div>
                                  </div>
                                  <div class="form-group">
                                      <label class="col-sm-2 col-sm-2 control-label">用户昵称</label>
                                      <div class="col-sm-10">
                                          <input type="text" class="form-control" name="nickname"
                                          	placeholder="用户昵称" id="nickname" vaule="${nickname}">
                                      </div>
                                  </div>
                                  <div class="form-group">
                                      <label class="col-sm-2 col-sm-2 control-label">姓名</label>
                                      <div class="col-sm-10">
                                          <input type="text" class="form-control" name="name"
                                          	placeholder="姓名" id="name" vaule="${name}">
                                      </div>
                                  </div>
                                  <div class="form-group">
                                  	<label class="col-sm-2 col-sm-2 control-label"></label>
                                      <button class="btn btn-danger" type="submit">增加</button>
                                  </div>
                                 </fieldset>
                              </form>
                          </div>
                      </section>
                  </div>
              </div>
              <!-- page end-->
          </section>
		</section>
		<!--main content end-->
<script type="text/javascript">
	$(function() {
		
		$('#add_user_form').ajaxForm({
			dataType : 'json',
			success : function(data) {
				if (data.result) {
					bootbox.alert("保存成功，将刷新页面", function() {
						window.location.reload();
					});
				}else{
					showErrors($('#add_user_form'),data.errors);
				}
			}
		}); 
	});	
</script>
<#include "../foot.ftl">