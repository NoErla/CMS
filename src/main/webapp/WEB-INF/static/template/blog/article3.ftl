<#include "header.ftl">
<!--main content start-->
<section id="main-content">
	<section class="wrapper">
		<!-- page start-->
		<div class="panel-body">
			<#list commentList.list as e>
				<div>
                      <label>评论者id:${e.name!}</label>
                </div>
				<div>
                      <label>评论:</label>
                      <div>${e.content!}</div>
                </div>
			</#list>
		</div>
		<form id="addComment" class="form-horizontal" action="${BASE_PATH}/blog/comment/add.json"  autocomplete="off"  method="post"
			enctype="multipart/form-data">
		<div class="row">
			<input type="hidden" name="articleId">
			<div class="col-lg-12">
				<section class="panel">
					<header class="panel-heading"> 
						评论
					</header>
					<input type="hidden" style="font-size:15px;width: 300px;" class="form-control" name="userId"
                              	placeholder="用户Id" id="userId" value="${SESSION_USER.userId}" >
                        </input>
						<input type="hidden" style="font-size:15px;width: 300px;" class="form-control" name="fatherId"
                              	placeholder="文章Id" id="fatherId" value="${article.articleId }" >
                        </input>
                        <input type="hidden" style="font-size:15px;width: 300px;" class="form-control" name="url"
                              	placeholder="评论url" id="url" value="1" >
                        </input>
                     
						<div class="form-group">
                          <label class="col-sm-2 col-sm-2 control-label">文章标题</label>
                          <div class="col-sm-10">
                              <input type="text" style="font-size:15px;width: 300px;" class="form-control" name="name"
                              	placeholder="文章标题" id="name" >
                              </input>
                          </div>
                        </div>
                   
                        
                        </div>                                              
						<div class="form-group">
                              <label class="col-sm-2 col-sm-2 control-label">评论</label>
                              <div class="col-sm-10">
                                  <script id="content" name="content" type="text/plain"
										style="width: 100%; height: 260px;"></script>
								  <script type="text/javascript">
									var contentEditor;
									$(function() {
										contentEditor = UE.getEditor('content');
									});
								  </script>
                              </div>
                        </div>
                        <div class="form-group">
                      	  <div class="col-lg-offset-2 col-lg-10">
                          <button class="btn btn-shadow update" href="javascript:void(0);" type="submit">评论</button>
                          </div>
                      </div>
					</div>
				</section>
			</div>
		</div>
		</form>
		<!-- page end-->
	</section>
</section>
<!--main content end-->
<script type="text/javascript">
	$(function(){
		$('#addComment').ajaxForm({
			dataType : 'json',
			type: 'POST',
			success : function(data) {
				if (data.result) {
					bootbox.alert("保存成功，将刷新页面", function() {
						window.location.reload();
					});
				}else{
					bootbox.alert("本站目前禁止评论", function() {
						window.location.reload();
					});
				}
			}
		});
	});
</script>
<#include "footer.ftl">