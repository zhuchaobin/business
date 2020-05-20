		function showOrHideDetail(editable, flag) {
			if (editable) {
				$('#fld_detail').prop('disabled', false);
				$('#btn_save').show();
			} else {
				$('#fld_detail').prop('disabled', true);
				$('#btn_save').hide();
			}
			// 订单列表查询
			if (flag == 1) {
				$('#main_search_detail').fadeIn(800);
				$('#main_edit_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').hide();
				$('#submit_ar_detail').hide();
				// 详情页面返回按钮
				$('#btn_close_div').hide();
				// 提交详情页面的返回
				$('#submmit_btn_close_div').hide();
				$('#query_ar_submmit_detail').hide();
                $('#main_spg_edit_detail').hide();

			} else if (flag == 2) {// 订单发起	
				// section
				$('#main_edit_detail_title').text("订单发起");
				$('#main_edit_detail').fadeIn(800);
				$('#main_search_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').hide();
				$('#submit_ar_detail').hide();
				$('#query_ar_submmit_detail').hide();
				$('#frm_detail_box-footer_div').fadeIn(200);
				// 详情页面返回按钮
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
			} else if (flag == 3) {// 订单详情查看
				//$('#main_edit_detail_title').text("订单信息");
				// section
				// 保险信息输入部门显示隐藏
				show_or_hide_ins_div(true);
				$('#main_edit_detail').fadeIn(800);
				$('#main_search_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').fadeIn(800);
				$('#submit_ar_detail').hide();
				$('#query_ar_submmit_detail').hide();
				$('#frm_detail_box-footer_div').hide();

				// 附件
				$('#upload_ar_div').hide();
				//				$('#query_ar_atch_div').show();//改为在查询后控制显示，没有数据的不显示
				// 详情页面返回按钮
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
				var div = document
						.getElementById('ar_lnk_jrnl_inf_detail_clapse_box');
				div.setAttribute("class", "box box-info");
				div = document.getElementById('main_edit_detail_clapse');
				div.setAttribute("class", "box box-info");
				//面板收缩+-号
				div = document.getElementById('main_edit_detail_li');
				div.setAttribute("class", "fa fa-minus");
			} else if (flag == 6) {// 订单修改
				// section
				$('#main_edit_detail_title').text("订单修改");
				$('#main_edit_detail').fadeIn(800);
				$('#main_search_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').hide();
				$('#submit_ar_detail').hide();
				$('#query_ar_submmit_detail').hide();
				$('#frm_detail_box-footer_div').fadeIn(200);
				// 详情页面返回按钮
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
				$('#btn_save_submmit').show();
				//面板收缩控制
				var div = document.getElementById('main_edit_detail_clapse');
				div.setAttribute("class", "box box-info");
				//面板收缩+-号
				div = document.getElementById('main_edit_detail_li');
				div.setAttribute("class", "fa fa-minus");
			} else if (flag == 4) {// 订单提交
				//$('#main_edit_detail_title').text("订单信息");
				// section
				$('#main_edit_detail').fadeIn(800);
				$('#main_search_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').fadeIn(800);
				$('#submit_ar_detail').fadeIn(800);
				$('#query_ar_submmit_detail').hide();
				// 附件
				$('#upload_ar_div').hide();
				$('#query_ar_atch_div').show();

				// 详情页面返回按钮
				$('#btn_close_div').show();
				// 提交详情页面的返回
				$('#submmit_btn_close_div').hide();
				// 参数
				$('#arIdDiv').show();
				// 按钮
				$('#btn_close').hide();
				$('#btn_save').hide();
				//面板收缩控制
				var div = document
						.getElementById('ar_lnk_jrnl_inf_detail_clapse_box');
				div.setAttribute("class", "box box-info collapsed-box");
				div = document.getElementById('main_edit_detail_clapse');
				div.setAttribute("class", "box box-info collapsed-box");
				//面板收缩+-号
				div = document.getElementById('main_edit_detail_li');
				div.setAttribute("class", "fa fa-plus");
				div = document.getElementById('ar_lnk_jrnl_inf_detail_li');
				div.setAttribute("class", "fa fa-plus");
			} else if (flag == 5) {//订单提交详情查看
				$('#main_search_detail').hide();
				$('#main_edit_detail').hide();
				$('#ar_lnk_jrnl_inf_detail').hide();
				$('#submit_ar_detail').hide();
				$('#query_ar_submmit_detail').fadeIn(800);
				
				// 详情页面返回按钮
				$('#btn_close_div').hide();
				// 提交详情页面的返回
				$('#submmit_btn_close_div').show();
			}else if (flag == 7) {//新发起发货
            $('#main_search_detail').hide();
            $('#main_edit_detail').hide();
            $('#ar_lnk_jrnl_inf_detail').hide();
            $('#submit_ar_detail').hide();
            // 返回按钮
            $('#btn_close_div').hide();
            //长约审批提交
            $('#query_ar_submmit_detail').hide();
            //新发起订单
            $('#main_spg_edit_detail').fadeIn(800);
        }
		}
		
		
		function transToEng(cng) {
			if (cng == "品名")
				return "pdNm";
			else if (cng == "规格")
				return "spec";
			else if (cng == "型号")
				return "modl";
			else if (cng == "质量标准")
				return "qlyStd";
			else if (cng == "品牌")
				return "brnd";
			else if (cng == "生产厂家")
				return "pdFctr";
			else if (cng == "数量")
				return "num";
			else if (cng == "计量单位")
				return "msunit";
			else if (cng == "采购单价")
				return "pchUnitprc";
			else if (cng == "交易差价")
				return "txnPrcdif";
			else if (cng == "销售暂定价")
				return "saleTntvPrc";
			else
				return "";
		}
		
		
				function getArSubmmitDetail(id, arId, aplyPcstpCd){
 			// 带过来的环节编号
			E7.post(pageSetting.resetPath + '/getArSubmmitDetail', {
				'id' : id,'arId':arId,'aplyPcstpCd':aplyPcstpCd
			}, function(result) {
//				fillForm('#frm_detail2', result.data);
				var detail = result.data;
				
				 // 个人化审批参数查询展示
				 var testdiv22 = document
					.getElementById("query_order_submmit_div");
				testdiv22.innerHTML = detail["aplyPsrlt"];
				$('#fld_detail_query_order_submmit').prop('disabled', true);
				///附件列表
				var td = detail["t2UploadAtch01List"];
				var insertHtml="";
				$('#query_ar_atch_div2').hide();
				if(null != td && undefined != td){
					for (var kk = 0; kk < td.length; kk++) {
						insertHtml = insertHtml
								+ '<div class="col-sm-2"><i class="iconfont icon-kl-fujian"></i><a href="'+ td[kk].srFileRte +'" target="_blank"><div>'
								+ td[kk].oriFileNm + '<p>'
								+ getFileType(td[kk].oriFileNm)
							/* 	+ transDateTime(suffix) */
								+ '</div></a></div>';
					}
					if (td.length > 0) {
						document.getElementById("query_ar_atch_div2").style.display="";
						var testdiv = document.getElementById("upload_atch_div2");
						testdiv.innerHTML=insertHtml;
					} else {
						document.getElementById("query_ar_atch_div2").style.display="none";//
					} 
				}			
				/* showOrHideDetail(false, 5); */
				 var divContent = document.getElementById('query_ar_submmit_detail').innerHTML;
					//页面层
					layer.open({		
					shade: true,
					title: false, //不显示标题
					type: 1,
					move:true,
					shadeClose:true,
					//shift:7,//动画
					skin: 'layui-layer-molv', //加上边框
					shade: [0.2,'#fff'],
					//skin: 'layer-ext-moon',
					//maxmin: true,
					area: ['80%', ],
					  content: divContent
					}); 
			}); 
		} 
		
		
		