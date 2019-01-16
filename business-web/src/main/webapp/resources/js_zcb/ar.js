		function showOrHideDetail(editable, flag) {
			if (editable) {
				$('#fld_detail').prop('disabled', false);
				$('#btn_save').show();
			} else {
				$('#fld_detail').prop('disabled', true);
				$('#btn_save').hide();
			}
			// 长约列表查询
			if(flag == 1){
				$('#main_search_detail').fadeIn(300);
				$('#main_edit_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').hide();
				$('#submit_ar_detail').hide();
				// 返回按钮
				$('#btn_close_div').hide();	
				$('#query_ar_submmit_detail').hide();
			} else if(flag == 2){// 长约发起	
				// section
				$('#main_edit_detail_title').text("长约发起");
				$('#main_edit_detail').fadeIn(300);
				$('#main_search_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').hide();
				$('#submit_ar_detail').hide();
				$('#query_ar_submmit_detail').hide();
				// 返回按钮
				$('#btn_close_div').hide();	
				// 提交详情页面的返回
				$('#submmit_btn_close_div').hide();
				// 附件
				$('#upload_ar_div').show();
				$('#query_ar_atch_div').hide();
				// 保险信息输入部门默认隐藏
				show_or_hide_ins_div(false);
				// 参数
				$('#arIdDiv').hide();
				// 按钮				
				$('#btn_close').show();
				$('#btn_save').show();
				//面板收缩控制
				var div = document.getElementById('main_edit_detail_clapse'); 
				div.setAttribute("class", "box box-info"); 
				//面板收缩+-号
				div = document.getElementById('main_edit_detail_li'); 
				div.setAttribute("class", "fa fa-minus"); 
			} else if(flag == 3){// 长约详情查看
				//$('#main_edit_detail_title').text("长约信息");
				// section
				// 保险信息输入部门显示隐藏
				show_or_hide_ins_div(true);
				$('#main_edit_detail').fadeIn(300);
				$('#main_search_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').fadeIn(300);
				$('#submit_ar_detail').hide();
				$('#query_ar_submmit_detail').hide();
				// 附件
				$('#upload_ar_div').hide();
//				$('#query_ar_atch_div').show();//改为在查询后控制显示，没有数据的不显示
				// 返回按钮
				$('#btn_close_div').show();
				// 提交详情页面的返回
				$('#submmit_btn_close_div').hide();
				// 参数
				$('#arIdDiv').show();
				// 按钮	
				$('#btn_close').hide();
				$('#btn_save').hide();
				$('#btn_save_submmit').hide();
				
				//面板收缩控制
				var div = document.getElementById('ar_lnk_jrnl_inf_detail_clapse_box'); 
				div.setAttribute("class", "box box-info");
				div = document.getElementById('main_edit_detail_clapse'); 
				div.setAttribute("class", "box box-info"); 
				//面板收缩+-号
				div = document.getElementById('main_edit_detail_li'); 
				div.setAttribute("class", "fa fa-minus"); 
			} else if(flag == 6){// 长约修改
				// section
				$('#main_edit_detail_title').text("长约修改");
				$('#main_edit_detail').fadeIn(300);
				$('#main_search_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').hide();
				$('#submit_ar_detail').hide();
				$('#query_ar_submmit_detail').hide();
				// 返回按钮
				$('#btn_close_div').hide();	
				// 提交详情页面的返回
				$('#submmit_btn_close_div').hide();
				// 附件
				$('#upload_ar_div').show();
				$('#query_ar_atch_div').hide();
				// 保险信息输入部门默认隐藏
				show_or_hide_ins_div(true);
				// 参数
				$('#arIdDiv').hide();
				// 按钮				
				$('#btn_close').show();
				$('#btn_save').show();
				//面板收缩控制
				var div = document.getElementById('main_edit_detail_clapse'); 
				div.setAttribute("class", "box box-info"); 
				//面板收缩+-号
				div = document.getElementById('main_edit_detail_li'); 
				div.setAttribute("class", "fa fa-minus"); 
			} else if(flag == 4){// 长约提交
				//$('#main_edit_detail_title').text("长约信息");
				// section
				$('#main_edit_detail').fadeIn(300);
				$('#main_search_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').fadeIn(300);
				$('#submit_ar_detail').fadeIn(300);
				$('#query_ar_submmit_detail').hide();
				// 附件
				$('#upload_ar_div').hide();
				$('#query_ar_atch_div').show();
				// 返回按钮
				$('#btn_close_div').hide();
				// 提交详情页面的返回
				$('#submmit_btn_close_div').hide();
				// 参数
				$('#arIdDiv').show();
				// 按钮
				$('#btn_close').hide();
				$('#btn_save').hide();
				//面板收缩控制
				var div = document.getElementById('ar_lnk_jrnl_inf_detail_clapse_box'); 
				div.setAttribute("class", "box box-info collapsed-box");
				div = document.getElementById('main_edit_detail_clapse'); 
				div.setAttribute("class", "box box-info collapsed-box"); 
				//面板收缩+-号
				div = document.getElementById('main_edit_detail_li'); 
				div.setAttribute("class", "fa fa-plus"); 
				div = document.getElementById('ar_lnk_jrnl_inf_detail_li'); 
				div.setAttribute("class", "fa fa-plus"); 
			} else if(flag == 5){//长约提交详情查看
				$('#main_search_detail').hide();
				$('#main_edit_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').hide();
				$('#submit_ar_detail').hide();
				// 返回按钮
				$('#btn_close_div').hide();	
				$('#query_ar_submmit_detail').fadeIn(300);	
				// 提交详情页面的返回
				$('#submmit_btn_close_div').show();
			}
		}

		function transAplyPcstpCd(value) {
			if (value == '01') {
				return "发起";
			} else if (value == '02') {
				return "供应链确认";
			} else if (value == '03') {
				return "下游确认";
			} else if (value == '04') {
				return "承保";
			} else if (value == '05') {
				return "一审";
			} else if (value == '06') {
				return "二审";;
			} else if (value == '07') {
				return "审批通过";
			} else if (value == '') {
				return "完结";
			} else
				return value;
		}
		
		function getArSubmmitDetail(id, arId, aplyPcstpCd){
 			// 带过来的环节编号
			E7.post(pageSetting.resetPath + '/getArSubmmitDetail', {
				'id' : id,'arId':arId,'aplyPcstpCd':aplyPcstpCd
			}, function(result) {
//				fillForm('#frm_detail2', result.data);
				var detail = result.data;
/* 				$('#frm_detail2 [name="submmitUsername"]').val(detail["username"]);
				$('#frm_detail2 [name="companyName"]').val(detail["name"]);
				$('#frm_detail2 [name="aplyPcstpCdDesc"]').val(transAplyPcstpCd(detail["aplyPcstpCd"]));
				$('#frm_detail2 [name="lnkEdtm"]').val(transDateTime(detail["lnkEdtm"]));
				$('#frm_detail2 [name="aplyPsrltCdDesc"]').val(transAplyPsrltCd(detail["aplyPsrltCd"]));
				$('#frm_detail2 [name="rmrkDesc"]').val(detail["rmrk"]); */
				document.getElementById('companyName').innerText = "公    司    :    " + detail["name"];
				document.getElementById('submmitUsername').innerText = "提交人    :    " + detail["username"];
				document.getElementById('aplyPcstpCdDesc').innerText = "环    节    :    " + transAplyPcstpCd(detail["aplyPcstpCd"]);
				document.getElementById('lnkEdtm').innerText = "时    间    :    " + transDateTime(detail["lnkEdtm"]);
				document.getElementById('aplyPsrltCdDesc').innerText = "结    果    :    " + transAplyPsrltCd(detail["aplyPsrltCd"]);
				document.getElementById('rmrkDesc').innerText = "备    注    :    " + detail["rmrk"];
				///附件列表
				var td = detail["t2UploadAtch01List"];
				var insertHtml="";
				$('#query_ar_atch_div2').hide();	
				if(null != td && undifined != td){
					for ( var kk = 0; kk < td.length; kk++) {
						insertHtml = insertHtml + '<div class="col-sm-2"><i class="iconfont icon-kl-fujian"></i><a href="'+ td[kk].srFileRte +'" target="_blank"><div>'+td[kk].oriFileNm+'<p>'+getFileType(td[kk].oriFileNm) + transDateTime(suffix) +'</div></a></div>';
					}								
					if(td.length > 0){
						var testdiv = document.getElementById("upload_atch_div2");
						testdiv.innerHTML=insertHtml;
						$('#query_ar_atch_div2').fadeIn(300);
					} else {
						$('#query_ar_atch_div2').hide();	
					}
				}
				showOrHideDetail(false,5);
			}); 
		} 

/*		 // 长约相关js
	function queryArDetails(id, aplyPcstpCd, type){// type 1:长约提交  0：长约详情查询
//		alert(aplyPcstpCd);
			// 带过来的环节编号
			$('#frm_submit_ar [name="aplyPcstpCd"]').val(aplyPcstpCd);
 
			if ($('#frm_detail').data('bootstrapValidator')) {
				$('#frm_detail').data('bootstrapValidator').resetForm(true);
			}
			E7.post(pageSetting.resetPath + '/getDetail', {
				'id' : id
			}, function(result) {
				fillForm('#frm_detail', result.data);
				var detail = result.data;
				for (key in detail) {
					$('#frm_detail [name="' + key + '"]').val(detail[key]);
				}
				///
				$('#frm_submit_ar [name="arId"]').val(detail["arId"]);
				// 长约详情div标题
				$('#main_edit_detail_title').text("长约信息 [ " + detail["arId"]+" ]");
				// 投保信息是否显示
				var isNeedIns = detail["isNeedIns"];
				if(isNeedIns == 1){
					show_or_hide_ins_div(true);
				} else {
					show_or_hide_ins_div(false);
				}
				///附件列表
				var td = detail["t2UploadAtch01List"];
				var insertHtml="";
				for ( var kk = 0; kk < td.length; kk++) { 
					insertHtml = insertHtml + '<div class="col-sm-2"><i class="iconfont icon-kl-fujian"></i><a href="'+ td[kk].srFileRte +'" target="_blank"><div>'+td[kk].oriFileNm+'<p>'+getFileType(td[kk].oriFileNm)+'</div></a></div>';
				}
				if(td.length > 0){
					var testdiv = document.getElementById("upload_atch_div");
					testdiv.innerHTML=insertHtml;
					$('#query_ar_atch_div').fadeIn(300);
				} else {
					$('#query_ar_atch_div').hide();	
				}				
//				bespeakStatus = detail['bespeakStatus'];				
				///环节流转列表table生成
 				var lnk_jrnl_inf = detail["list"];
				insertHtml="";
				insertHtml = '<table id="tbl_Lnk_Jrnl_Inf2" align="center"class="table table-hover"> <thead> <tr> <th style="border-bottom: none;">环节</th><th style="border-bottom: none;">结果</th><th style="border-bottom: none;">提交人</th><th style="border-bottom: none;">公司</th><th style="border-bottom: none;">备注</th><th style="border-bottom: none;">处理时间</th><th style="border-bottom: none;">详情</th></tr> </thead><tbody>';
				for ( var jj = 0; jj < lnk_jrnl_inf.length; jj++) {
					var rmrkDesc = lnk_jrnl_inf[jj].rmrk;
					if((null != rmrkDesc) && (rmrkDesc.length > 5)){
						rmrkDesc = '<a onclick=getArSubmmitDetail("'+ lnk_jrnl_inf[jj].id +'","'+ lnk_jrnl_inf[jj].rltvId +'","'+ lnk_jrnl_inf[jj].aplyPcstpCd + '")>' + rmrkDesc.substring(0,5) + "..." + '</a>';
					}
					if((null == rmrkDesc) || (""==rmrkDesc)){
						rmrkDesc = "无";
					}
					insertHtml = insertHtml + '<tr><td>'+transAplyPcstpCd(lnk_jrnl_inf[jj].aplyPcstpCd)+'</td><td>'+transAplyPsrltCd(lnk_jrnl_inf[jj].aplyPsrltCd)+'</td><td>'+lnk_jrnl_inf[jj].username+'</td><td>'+lnk_jrnl_inf[jj].companyName+
					'</td><td>'+rmrkDesc+'</td><td>'+transDateTime(lnk_jrnl_inf[jj].lnkEdtm)+
					'</td><td><input type="button" class="btn btn_small" onclick=getArSubmmitDetail("'+ lnk_jrnl_inf[jj].id +'","'+ lnk_jrnl_inf[jj].rltvId +'","'+ lnk_jrnl_inf[jj].aplyPcstpCd + '") value="查看"> </td></tr>';
				}				
				insertHtml = insertHtml + "</tbody></table>";
				testdiv = document.getElementById("lnk_jrnl_inf_tbl_div");
				// 长约流转div标题
				$('#ar_lnk_jrnl_inf_detail_title').text("流转信息 [ " + detail["arId"]+" ]");
				// 长约提交div标题				
				var titleDesc = "长约" + transAplyPcstpCd(aplyPcstpCd);	
//				alert(aplyPcstpCd);
				$('#submit_ar_detail_title').text("您所在位置 : 长约管理 > 待审批长约 > " + titleDesc + " [ " + detail["arId"] + " ]");
				// 长约提交详情查询div标题
				$('#query_ar_submmit_title').text("长约提交详情 [ " + detail["arId"]+" ]");
				
				testdiv.innerHTML=insertHtml;
				
				// 决定显示哪一个返回按钮
				if(type == 1){
					// 返回到长约详情查询
					$('#btn_close_return4').fadeIn(300);
					$('#btn_close_return3').hide();	
				} else {

					// 返回到长约提交
					$('#btn_close_return3').fadeIn(300);
					$('#btn_close_return4').hide();	
				}
			});
		}
	
	function getArSubmmitDetail(id, arId, aplyPcstpCd){
			// 带过来的环节编号
		E7.post(pageSetting.resetPath + '/getArSubmmitDetail', {
			'id' : id,'arId':arId,'aplyPcstpCd':aplyPcstpCd
		}, function(result) {
//			fillForm('#frm_detail2', result.data);
			var detail = result.data;
 				$('#frm_detail2 [name="submmitUsername"]').val(detail["username"]);
			$('#frm_detail2 [name="companyName"]').val(detail["name"]);
			$('#frm_detail2 [name="aplyPcstpCdDesc"]').val(transAplyPcstpCd(detail["aplyPcstpCd"]));
			$('#frm_detail2 [name="lnkEdtm"]').val(transDateTime(detail["lnkEdtm"]));
			$('#frm_detail2 [name="aplyPsrltCdDesc"]').val(transAplyPsrltCd(detail["aplyPsrltCd"]));
			$('#frm_detail2 [name="rmrkDesc"]').val(detail["rmrk"]); 
			document.getElementById('companyName').innerText = "公    司    :    " + detail["name"];
			document.getElementById('submmitUsername').innerText = "提交人    :    " + detail["username"];
			document.getElementById('aplyPcstpCdDesc').innerText = "环    节    :    " + transAplyPcstpCd(detail["aplyPcstpCd"]);
			document.getElementById('lnkEdtm').innerText = "时    间    :    " + transDateTime(detail["lnkEdtm"]);
			document.getElementById('aplyPsrltCdDesc').innerText = "结    果    :    " + transAplyPsrltCd(detail["aplyPsrltCd"]);
			document.getElementById('rmrkDesc').innerText = "备    注    :    " + detail["rmrk"];
			///附件列表
			var td = detail["t2UploadAtch01List"];
			var insertHtml="";
			for ( var kk = 0; kk < td.length; kk++) {
				var oriFileNm = td[kk].oriFileNm;
				var index1=oriFileNm.lastIndexOf(".");
				var index2=oriFileNm.length;
				var suffix=oriFileNm.substring(index1+1,index2);//后缀名

				insertHtml = insertHtml + '<div class="col-sm-2"><i class="iconfont icon-kl-fujian"></i><a href="'+ td[kk].srFileRte +'" target="_blank"><div>'+td[kk].oriFileNm+'<p>'+getFileType(td[kk].oriFileNm) + transDateTime(suffix) +'</div></a></div>';
			}								
			if(td.length > 0){
				var testdiv = document.getElementById("upload_atch_div2");
				testdiv.innerHTML=insertHtml;
				$('#query_ar_atch_div2').fadeIn(300);
			} else {
				$('#query_ar_atch_div2').hide();	
			}				
			showOrHideDetail(false,5);
		}); 
	}                                                                                
			
	function transAplyPsrltCd(aplyPsrltCd) {
		if (aplyPsrltCd == '01') {
			return "通过";
		} else if (aplyPsrltCd == '02') {
			return "退回";
		} else if (aplyPsrltCd == '03') {
			return "拒绝";
		}  else
			return aplyPsrltCd;
	}
	
	function transAplyPcstpCd(value) {
		if (value == '01') {
			return "发起";
		} else if (value == '02') {
			return "供应链确认";
		} else if (value == '03') {
			return "下游确认";
		} else if (value == '04') {
			return "承保";
		} else if (value == '05') {
			return "一审";
		} else if (value == '06') {
			return "二审";;
		} else if (value == '07') {
			return "终止";
		} else if (value == '') {
			return "完成";
		} else
			return value;
	}	
	
	function showViewSubmitAr(id, aplyPcstpCd) {
//		alert("2222");
		E7.resetForm('#frm_submit_ar');
		$('#frm_submit_ar').data('bootstrapValidator').updateStatus('aplyPsrltCd', 'NOT_VALIDATED',null).validateField('aplyPsrltCd');  
		// 初始化上传附件控件
		initInputItem(2);
		queryArDetails(id, aplyPcstpCd, 1);// flag 1:长约提交  0：长约详情查询
		showOrHideDetail(false, 4);
	}	
	function showArDetailDialog() {
		var rows = $('#tbl_query').e7table('getSelectedRows');
		if (rows.length == 0) {
			bootbox.alert({
				'title' : '错误消息',
				'message' : '请选择数据！'
			});
			return;
		} else if (rows.length > 1) {
			bootbox.alert({
				'title' : '错误消息',
				'message' : '一次只能修改一条数据！'
			});
			return;
		}
		if ($('#frm_detail').data('bootstrapValidator')) {
			$('#frm_detail').data('bootstrapValidator').resetForm(true);
		}
		queryArDetails(rows[0]['id'], "", 0);// flag 1:长约提交  0：长约详情查询
		showOrHideDetail(false, 3);
	}
	
	function del() {
//		alert($('#queryTypeId').val());
		var rows = $('#tbl_query').e7table('getSelectedRows');
		if (rows.length == 0) {
			bootbox.alert({
				'title' : '错误消息',
				'message' : '请选择数据！'
			});
			return;
		} else if (rows.length > 1) {
			bootbox.alert({
				'title' : '错误消息',
				'message' : '一次只能修改一条数据！'
			});
			return;
		}
		if ($('#frm_detail').data('bootstrapValidator')) {
			$('#frm_detail').data('bootstrapValidator').resetForm(true);
		}
		E7.post(pageSetting.resetPath + '/deleteAr', {
			'id' : rows[0]['id']
		}, function(result) {
			query_ar_list();
			bootbox.alert({
				'title' : "消息",
				'message' : '删除成功！'
			});
		});
	}

	function validate_frm_detail(){
		$('#frm_detail').bootstrapValidator('validate');
		if (!$('#frm_detail').data('bootstrapValidator').isValid()) {
			return;
		} else {
			$('#saveSubmitModal').modal('show');
		}
	}
	
	function save() {			
		for (instance in CKEDITOR.instances) {
			CKEDITOR.instances[instance].updateElement();
		}

		var formParams = E7.serialForm('#frm_detail');
		E7.post(pageSetting.resetPath + '/save', formParams,
				function(data) {
					query_ar_list();
					//showOrHideDetail(true, 1);
					bootbox.alert({
						'title' : "消息",
						'message' : '长约发起成功！'
					});
					$('#tbl_query').e7table(
							'reload',
							function() {
								if (formParams['id'] != '') {
									$('#tbl_query').e7table('selectById',
											formParams['id']);
								}
							});
				});
	}
	function showEditDialog(disable) {
		var rows = $('#tbl_query').e7table('getSelectedRows');
		if (rows.length == 0) {
			bootbox.alert({
				'title' : '错误消息',
				'message' : '请选择数据！'
			});
			return;
		} else if (rows.length > 1) {
			bootbox.alert({
				'title' : '错误消息',
				'message' : '一次只能修改一条数据！'
			});
			return;
		}
		if ($('#frm_detail').data('bootstrapValidator')) {
			$('#frm_detail').data('bootstrapValidator').resetForm(true);
		}
		E7
				.post(
						pageSetting.resetPath + '/getDetail',
						{
							'id' : rows[0]['id']
						},
						function(result) {
							fillForm('#frm_detail', result.data);
							// 实时判断长约是否可以修改	
							if(result.data["aplyPcstpCd"] != "01"){
								bootbox.alert({
									'title' : '错误消息',
									'message' : '当前长约不在发起环节禁止修改！请退回到发起环节再修改。'
								});
								return;
							}
							// 多次修改，清空上次div追加的内容
							$("#fujianId").empty(); 
								if (result.data.t2UploadAtch01List != undefined) {
								for (i = 0; i < result.data.t2UploadAtch01List.length; i++) {
									var elem = result.data.t2UploadAtch01List[i];
 										var item = '<div name="inputDiv">'
											+ elem.oriFileNm
											+ '<input name="fileName" type="file" src="'+elem.oriFileNm+'"  class="form-control" value="'+elem.oriFileNm+'"/><a onclick="deleteItem(this)">删除</a> <br/></div>'; 
									var atchId = elem.id;
											var item = '<div name="inputDiv"> <a href="' + elem.srFileRte +'" target="_blank">'+elem.oriFileNm  +'&nbsp&nbsp</a>&nbsp&nbsp<a onclick="deleteItem2(this, ' + elem.id +')"> 删除</a> <br/></div>';
											
											$("#fujianId").append(item);
								}
							} 
							showOrHideDetail(true, 6);
							// 展示保险div
							show_or_hide_ins_div(true);
						});
	}
	fillForm = function(frm, data, reset, parent) {
		if (E7.isEmpty(parent)
				&& (E7.getType(reset) == 'undefined' || reset))
			E7.resetForm(frm);
		for ( var key in data) {
			var elName = E7.isEmpty(parent) ? key : (parent + '.' + key);
			var elValue = data[key];

			if (elValue == null)
				continue;
			if (E7.getType(elValue) == 'object') {
				E7.fillForm(frm, elValue, false, elName);
				continue;
			}
			var els = $(frm + ' [name="' + elName + '"]');
			if (els.length == 0)
				continue;
			if (els[0].type == 'radio') {
				$(frm + ' [name="' + elName + '"][value="' + elValue + '"]')
						.prop("checked", true);
			} else if (els[0].type == 'select-multiple') {
				if (elValue instanceof Array) {
					els.val(elValue);
				} else if (typeof elValue == 'string') {
					els.val(elValue.split(','));
				} else if (typeof elValue == 'number') {
					els.val(elValue + '');
				} else {
					els.val(elValue);
				}
			} else if (els[0].type == 'checkbox') {
				if (els.length == 1) {
					if (els.val() == elValue) {
						els.prop('checked', true);
					}
				} else {
					if (elValue instanceof Array) {
						els.val(elValue);
					} else if (typeof elValue == 'string') {
						els.val(elValue.split(','));
					} else if (typeof elValue == 'number') {
						els.val(elValue + '');
					} else {
						els.val(elValue);
					}
				}
			} else if (els[0].id == 'editor1') {
				CKEDITOR.instances.editor1.setData(elValue);
			} else {
				els.val(elValue + '');
			}
		}
	}

	function submitTransferFile(flag) {
		if(flag == 1){			
		var file = document.getElementsByName("fileName")
		//var file = $('.select-file').files//document.getElementById("file_transfer").files;
		//TODO:valid
		var formData = new FormData();
		for (i = 0; i < file.length; i++) {
			formData.append('file', file[i].files[0]);
		}
		E7.postMultipart('[[@{/arManagement/ossUpload}]]', formData,
				function(result) {
					var fileUrl = result.data;
					$("#fileId").val(fileUrl);
					bootbox.alert({
						'title' : '消息',
						'message' : '上传成功, 文件：<code>' + result.data
								+ '</code>！'
					});
				});
		} else if(flag == 2){
			var file = document.getElementsByName("fileName2")
			//var file = $('.select-file').files//document.getElementById("file_transfer").files;
			//TODO:valid
			var formData = new FormData();
			for (i = 0; i < file.length; i++) {
				formData.append('file', file[i].files[0]);
			}
			E7.postMultipart('[[@{/arManagement/ossUpload}]]', formData,
					function(result) {
						var fileUrl2 = result.data;
						$("#fileId2").val(fileUrl2);
						bootbox.alert({
							'title' : '消息',
							'message' : '上传成功, 文件：<code>' + result.data
									+ '</code>！'
						});
					});
		}
	}

	function addInputItem(flag) {

		if(flag == 1){
			var item = '<div name="inputDiv"><input name="fileName" type="file"  class="form-control"/><a onclick="deleteItem(this)">删除</a> <br/></div>';
			$("#fujianId").append(item);
		} else if (flag == 2){
			var item = '<div name="inputDiv"><input name="fileName2" type="file"  class="form-control"/><a onclick="deleteItem(this)">删除</a> <br/></div>';
			$("#fujianId2").append(item);
		}
	}
	function initInputItem(flag) {
		
		if(flag ==1 ){
			var item = '<div name="inputDiv"><input name="fileName" type="file"  class="form-control"/><a onclick="deleteItem(this)">删除</a> <br/></div>';
			var testdiv = document.getElementById("fujianId");
			testdiv.innerHTML=item;
		} else if(flag == 2){
			var item = '<div name="inputDiv"><input name="fileName2" type="file"  class="form-control"/><a onclick="deleteItem(this)">删除</a> <br/></div>';
			var testdiv = document.getElementById("fujianId2");
			testdiv.innerHTML=item;
		}
	}
	function deleteItem(input) {
		$(input).parent().remove();
	}
	function deleteItem2(input, id) {
		var preValue = $("#fileToDeleteId").val();
		if(preValue == null || preValue == "")
			$("#fileToDeleteId").val(id);
		else				
			$("#fileToDeleteId").val(preValue + "||" + id);

		$(input).parent().remove();
	}
		
	 日期选择控件-长约开始日期 
	$('#arStdtDTPicker').datetimepicker({
        language: 'zh-CN',
        minView: "month",//设置只显示到月份
        format : "yyyy-mm-dd",//日期格式
        autoclose:true,//选中关闭
        todayBtn: true//今日按钮
     }).on('changeDate',function(ev){
         $('#arEddtDTPicker').datetimepicker('setStartDate',ev.date);
          var oneYearAfter = ev.date; 
         oneYearAfter.setFullYear(oneYearAfter.getFullYear()+1); 
         oneYearAfter.setDate(oneYearAfter.getDate()-1);
         $('#arEddtDTPicker').datetimepicker('setDate', oneYearAfter);

         $('#frm_detail').data('bootstrapValidator').updateStatus('arStdt', 'NOT_VALIDATED',null).validateField('arStdt');  
         $('#frm_detail').data('bootstrapValidator').updateStatus('arEddt', 'NOT_VALIDATED',null).validateField('arEddt');   
         // 判断保险起止时间与长约起止时间，并提示，但是不禁止提交
         checkInsDtAndArDt();
     }); 
	 日期选择控件-长约结束日期 
	$('#arEddtDTPicker').datetimepicker({
        language: 'zh-CN',
        minView: "month",//设置只显示到月份
        format : "yyyy-mm-dd",//日期格式
        autoclose:true,//选中关闭
        todayBtn: true//今日按钮
     }).on('changeDate',function(ev){   
         $('#frm_detail').data('bootstrapValidator').updateStatus('arStdt', 'NOT_VALIDATED',null).validateField('arStdt');  
         $('#frm_detail').data('bootstrapValidator').updateStatus('arEddt', 'NOT_VALIDATED',null).validateField('arEddt'); 
         // 判断保险起止时间与长约起止时间，并提示，但是不禁止提交
         checkInsDtAndArDt(); 
     }); 
	 日期选择控件 - 保险开始日期
	$('#insStdtDTPicker').datetimepicker({
        language: 'zh-CN',
        minView: "month",//设置只显示到月份
        format : "yyyy-mm-dd",//日期格式
        autoclose:true,//选中关闭
        todayBtn: true//今日按钮
     }).on('changeDate',function(ev){
         $('#insEddtDTPicker').datetimepicker('setStartDate',ev.date);
         var oneYearAfter = ev.date; 
         oneYearAfter.setFullYear(oneYearAfter.getFullYear()+1); 
         oneYearAfter.setDate(oneYearAfter.getDate()-1); 

         $('#insEddtDTPicker').datetimepicker('setDate',oneYearAfter);
         $('#frm_detail').data('bootstrapValidator').updateStatus('insStdt', 'NOT_VALIDATED',null).validateField('insStdt');  
         $('#frm_detail').data('bootstrapValidator').updateStatus('insEddt', 'NOT_VALIDATED',null).validateField('insEddt');
         // 判断保险起止时间与长约起止时间，并提示，但是不禁止提交
         checkInsDtAndArDt();
     }); 
	 日期选择控件 - 保险结束日期
	$('#insEddtDTPicker').datetimepicker({
        language: 'zh-CN',
        minView: "month",//设置只显示到月份
        format : "yyyy-mm-dd",//日期格式
        autoclose:true,//选中关闭
        todayBtn: true//今日按钮
     }).on('changeDate',function(ev){ 
         $('#frm_detail').data('bootstrapValidator').updateStatus('insStdt', 'NOT_VALIDATED',null).validateField('insStdt');  
         $('#frm_detail').data('bootstrapValidator').updateStatus('insEddt', 'NOT_VALIDATED',null).validateField('insEddt'); 
      // 判断保险起止时间与长约起止时间，并提示，但是不禁止提交
         checkInsDtAndArDt();
     }); 
	
	   // 判断保险起止时间与长约起止时间，并提示，但是不禁止提交
	function checkInsDtAndArDt(){
	    var msg = "";
	    if(($('#insStdtDTPicker').val() != "") && ($('#arStdtDTPicker').val() !="") && ($('#insStdtDTPicker').val() > $('#arStdtDTPicker').val())){
	   	 msg += "[保险开始日期] 晚于 [长约开始日期] !";
	    }
	    if( ($('#insEddtDTPicker').val() != "") && ($('#arEddtDTPicker').val() !="") && ($('#insEddtDTPicker').val() < $('#arEddtDTPicker').val())){
	   	 msg += "[保险结束日期] 早于 [长约结束日期] !";
	    }
	    if(msg != ""){
				bootbox.alert({
					'title' : '特别提醒',
					'message' : msg
				});
	    }
	}
	
	*//***
	 * 查询
	 *//*
	function query_ar_list() {
		showOrHideDetail(true, 1);
		$('#tbl_query').e7table('query', '#frm_query');

	}		
	function showOrHideDetail(editable, flag) {
		if (editable) {
			$('#fld_detail').prop('disabled', false);
			$('#btn_save').show();
		} else {
			$('#fld_detail').prop('disabled', true);
			$('#btn_save').hide();
		}
		// 长约列表查询
		if(flag == 1){
			$('#main_search_detail').fadeIn(300);
			$('#main_edit_detail').hide();
			$('#ar_lnk_jrnl_inf_detail').hide();
			$('#submit_ar_detail').hide();
			// 返回按钮
			$('#btn_close_div').hide();	
			$('#query_ar_submmit_detail').hide();
		} else if(flag == 2){// 长约发起	
			// section
			$('#main_edit_detail_title').text("长约发起");
			$('#main_edit_detail').fadeIn(300);
			$('#main_search_detail').hide();
			$('#ar_lnk_jrnl_inf_detail').hide();
			$('#submit_ar_detail').hide();
			$('#query_ar_submmit_detail').hide();
			// 返回按钮
			$('#btn_close_div').hide();	
			// 附件
			$('#upload_ar_div').show();
			$('#query_ar_atch_div').hide();
			// 保险信息输入部门默认隐藏
			show_or_hide_ins_div(false);
			// 参数
			$('#arIdDiv').hide();
			// 按钮				
			$('#btn_close').show();
			$('#btn_save').show();
			//面板收缩控制
			var div = document.getElementById('main_edit_detail_clapse'); 
			div.setAttribute("class", "box box-info"); 
			//面板收缩+-号
			div = document.getElementById('main_edit_detail_li'); 
			div.setAttribute("class", "fa fa-minus"); 
		} else if(flag == 3){// 长约详情查看
			//$('#main_edit_detail_title').text("长约信息");
			// section
			$('#main_edit_detail').fadeIn(300);
			$('#main_search_detail').hide();
			$('#ar_lnk_jrnl_inf_detail').fadeIn(300);
			$('#submit_ar_detail').hide();
			$('#query_ar_submmit_detail').hide();
			// 附件
			$('#upload_ar_div').hide();
//			$('#query_ar_atch_div').show();//改为在查询后控制显示，没有数据的不显示
			// 返回按钮
			$('#btn_close_div').show();
			// 参数
			$('#arIdDiv').show();
			// 按钮	
			$('#btn_close').hide();
			$('#btn_save').hide();
			//面板收缩控制
			var div = document.getElementById('ar_lnk_jrnl_inf_detail_clapse_box'); 
			div.setAttribute("class", "box box-info");
			div = document.getElementById('main_edit_detail_clapse'); 
			div.setAttribute("class", "box box-info"); 
			//面板收缩+-号
			div = document.getElementById('main_edit_detail_li'); 
			div.setAttribute("class", "fa fa-minus"); 
		} else if(flag == 6){// 长约修改
			// section
			$('#main_edit_detail_title').text("长约修改");
			$('#main_edit_detail').fadeIn(300);
			$('#main_search_detail').hide();
			$('#ar_lnk_jrnl_inf_detail').hide();
			$('#submit_ar_detail').hide();
			$('#query_ar_submmit_detail').hide();
			// 返回按钮
			$('#btn_close_div').hide();	
			// 附件
			$('#upload_ar_div').show();
			$('#query_ar_atch_div').hide();
			// 保险信息输入部门默认隐藏
			show_or_hide_ins_div(false);
			// 参数
			$('#arIdDiv').hide();
			// 按钮				
			$('#btn_close').show();
			$('#btn_save').show();
			//面板收缩控制
			var div = document.getElementById('main_edit_detail_clapse'); 
			div.setAttribute("class", "box box-info"); 
			//面板收缩+-号
			div = document.getElementById('main_edit_detail_li'); 
			div.setAttribute("class", "fa fa-minus"); 
		} else if(flag == 4){// 长约提交
			//$('#main_edit_detail_title').text("长约信息");
			// section
			$('#main_edit_detail').fadeIn(300);
			$('#main_search_detail').hide();
			$('#ar_lnk_jrnl_inf_detail').fadeIn(300);
			$('#submit_ar_detail').fadeIn(300);
			$('#query_ar_submmit_detail').hide();
			// 附件
			$('#upload_ar_div').hide();
			$('#query_ar_atch_div').show();
			// 返回按钮
			$('#btn_close_div').hide();
			// 参数
			$('#arIdDiv').show();
			// 按钮
			$('#btn_close').hide();
			$('#btn_save').hide();
			//面板收缩控制
			var div = document.getElementById('ar_lnk_jrnl_inf_detail_clapse_box'); 
			div.setAttribute("class", "box box-info collapsed-box");
			div = document.getElementById('main_edit_detail_clapse'); 
			div.setAttribute("class", "box box-info collapsed-box"); 
			//面板收缩+-号
			div = document.getElementById('main_edit_detail_li'); 
			div.setAttribute("class", "fa fa-plus"); 
			div = document.getElementById('ar_lnk_jrnl_inf_detail_li'); 
			div.setAttribute("class", "fa fa-plus"); 
		} else if(flag == 5){//长约提交详情查看
			$('#main_search_detail').hide();
			$('#main_edit_detail').hide();
			$('#ar_lnk_jrnl_inf_detail').hide();
			$('#submit_ar_detail').hide();
			// 返回按钮
			$('#btn_close_div').hide();	
			$('#query_ar_submmit_detail').fadeIn(300);	
		}
	}
	*//***
	 * 重置
	 *//*
	function reset() {
		E7.resetForm('#frm_query');
	}
	
	
	function validate_frm_submit_ar(){
		$('#frm_submit_ar').bootstrapValidator('validate');
		if (!$('#frm_submit_ar').data('bootstrapValidator').isValid()) {
			return;
		} else {
			$('#submitArModal').modal('show');
		}
	}
	
	*//***
	 * 长约提交
	 *//*
	function submitAr() {			
		for (instance in CKEDITOR.instances) {
			CKEDITOR.instances[instance].updateElement();
		}
		var formParams = E7.serialForm('#frm_submit_ar');
		E7.post(pageSetting.resetPath + '/submitAr', formParams, function(
				data) {
			showOrHideDetail(true, 1);
			bootbox.alert({
				'title' : "消息",
				'message' : '提交成功！'
			});
			$('#tbl_query').e7table(
					'reload',
					function() {
						if (formParams['id'] != '') {
							$('#tbl_query').e7table('selectById',
									formParams['id']);
						}
					});
		});
	}

	*//***
	 * 显示发起对话框
	 *//*
	function showAddDialog() {
		// 初始化上传附件控件
		initInputItem(1);
		E7.resetForm('#frm_detail');
		//		CKEDITOR.instances.editor1.setData('');
		$('#frm_detail').data('bootstrapValidator').resetForm(true);
		showOrHideDetail(true, 2);
	}
	
	*//***
	 * 表单校验控件初始化2(长约各环节提交)
	 *//*
	function validator_2_Init() {			
		$('#frm_submit_ar').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
		    	"aplyPsrltCd" : {
					validators : {
						notEmpty : {message : '处理结果不能为空'}
					}
				}
			}
		});

		$('#btn_submit_ar').click(function() {
			$('#frm_submit_ar').bootstrapValidator('validate');
		});
	}
	*//***
	 * 表单校验控件初始化1（长约发起提交）
	 *//*
	function validator_1_Init() {						
		$('#frm_detail').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
		    	"arTp" : {
					validators : {
						notEmpty : {message : '长约类型不能为空'}
					}
				},
		    	"fncEntp" : {
					validators : {
						notEmpty : {message : '融资企业不能为空'}
					}
				},
		    	"ustrmSplr" : {
					validators : {
						notEmpty : {message : '上游供应商不能为空'}
					}
				},
		    	"stgco" : {
					validators : {
						notEmpty : {message : '仓储公司不能为空'}
					}
				},
		    	"bnk" : {
					validators : {
						notEmpty : {message : '银行不能为空'}
					}
				},
		    	"lgstcCo" : {
					validators : {
						notEmpty : {message : '物流公司不能为空'}
					}
				},
		    	"insCo" : {
					validators : {
						notEmpty : {message : '保险公司不能为空'}
					}
				},
		    	"splchainCo" : {
					validators : {
						notEmpty : {message : '供应链公司不能为空'}
					}
				},
		    	"pckupgdsCyc" : {
					validators : {
						notEmpty : {message : '提货周期不能为空'},
						digits: {message: '提货周期只能输入数字'}
					}
				},
		    	"btp" : {
					validators : {
						notEmpty : {message : '业务类型不能为空'}
					}
				},
		    	"fncPctg" : {
					validators : {
						notEmpty : {message : '融资比例不能为空'},
						digits: {message: '输入只能为0~100的数字'},
						greaterThan: {value: 0, message:'输入不能小于0'},
						lessThan: {value: 101, message:'输入不能超过100'}
					}
				},
		    	"prpyGldRto" : {
					validators : {
						notEmpty : {message : '预付金比率不能为空'},
						digits: {message: '输入只能为数字'},
						greaterThan: {value: 0, message:'输入不能小于0'},
						lessThan: {value: 101, message:'输入不能超过100'}
					}
				},
		    	"lmt" : {
					validators : {
						notEmpty : {message : '额度不能为空'},
						digits: {message: '输入只能为数字'}
					}
				},
		    	"crdn" : {
					validators : {
						notEmpty : {message : '警戒线不能为空'},
						digits: {message: '输入只能为数字'},
						greaterThan: {value: 0, message:'输入不能小于0'},
						lessThan: {value: 101, message:'输入不能超过100'}
					}
				},
		    	"clsposLn" : {
					validators : {
						notEmpty : {message : '平仓线不能为空'},
						digits: {message: '输入只能为数字'},
						greaterThan: {value: 0, message:'输入不能小于0'},
						lessThan: {value: 101, message:'输入不能超过100'}
					}
				},
		    	"svcfeeCmd" : {
					validators : {
						notEmpty : {message : '服务费收取方式不能为空'}
					}
				},
		    	"splovCrrovMod" : {
					validators : {
						notEmpty : {message : '溢短结转方式不能为空'}
					}
				},

		    	"plchd" : {
					validators : {
						notEmpty : {message : '投保人不能为空'}
					}
				},
		    	"plchdAdr" : {
					validators : {
						notEmpty : {message : '投保人地址不能为空'}
					}
				},
		    	"insrd" : {
					validators : {
						notEmpty : {message : '被保险人不能为空'}
					}
				},
		    	"insrdAdr" : {
					validators : {
						notEmpty : {message : '被保险人地址不能为空'}
					}
				},
		    	"fstBenf" : {
					validators : {
						notEmpty : {message : '第一受益人不能为空'}
					}
				},
		    	"fstBenfAdr" : {
					validators : {
						notEmpty : {message : '第一受益人地址不能为空'}
					}
				},
		    	"insPtyAdr" : {
					validators : {
						notEmpty : {message : '保险财产地址不能为空'}
					}
				},
		    	"insObjNm" : {
					validators : {
						notEmpty : {message : '保险标的名称不能为空'}
					}
				},
		    	"insAmt" : {
					validators : {
						notEmpty : {message : '保险金额不能为空'},
						digits: {message: '输入只能为数字'}
					}
				},
		    	"dctbAmt" : {
					validators : {
						notEmpty : {message : '免赔额不能为空'},
						digits: {message: '输入只能为数字'}
					}
				},
		    	"epap" : {
					validators : {
						notEmpty : {message : '特别约定不能为空'}
					}
				},
		    	"insStdt" : {
		    		trigger:"change3", //监听change动作
					validators : {
						notEmpty : {message : '保险开始日期不能为空'}
					}
				},
		    	"insEddt" : {
		    		trigger:"change4", //监听change动作
					validators : {
						notEmpty : {message : '保险结束日期不能为空'}
					}
				},
		    	"arStdt" : {
		//    		trigger:"change1", //监听change动作
					validators : {
						notEmpty : {message : '长约开始日期不能为空'}
					}
				},
		    	"arEddt" : {
		  //  		trigger:"change2", //监听change动作
					validators : {
						notEmpty : {message : '长约结束日期不能为空'}
					}
				},
		    	"isNeedIns" : {
					validators : {
						notEmpty : {message : '投保类型不能为空'}
					}
				}
			}
		});

		$('#btn_save').click(function() {
			$('#frm_detail').bootstrapValidator('validate');
		});
	}
	
	function show_or_hide_ins_div(flag){
		if(flag){
			document.getElementById('ins_div').style.display="";
		} else {
			document.getElementById('ins_div').style.display="none";
		}
	}*/