		 // 根据附件后缀返回文件类型
		function getFileType(fileNm){ 
			var index1=fileNm.lastIndexOf(".");
			var index2=fileNm.length;
			var suffix=fileNm.substring(index1+1,index2);//后缀名			
			 var fileType = suffix;
			if("jpg" == suffix || "png" == suffix || "bmp" == suffix || "gif" == suffix || "tif" == suffix || "pic" == suffix){
				fileType = "[ 图片 ]";
			} else if("docx" == suffix || "doc" == suffix){
				fileType = "[ Word文档 ]";
			} else if("xls" == suffix || "exls" == suffix){
				fileType = "[ Excel文档 ]";
			} else if("rar" == suffix || "zip" == suffix || "gz" == suffix){
				fileType = "[ 压缩文档 ]";
			} 
			return fileType;
		 }
		
		// 引入日期时间格式转换
		Date.prototype.Format = function(fmt)   
		 { 
		//author:wangweizhen
		   var o = {   
		     "M+" : this.getMonth()+1,                 //月份   
		     "d+" : this.getDate(),                    //日
		    "h+" : this.getHours(),                   //小时   
		     "m+" : this.getMinutes(),                 //分   
		     "s+" : this.getSeconds(),                 //秒   
		     "q+" : Math.floor((this.getMonth()+3)/3), //季度   
		     "S"  : this.getMilliseconds()             //毫秒   
		   };   
		   if(/(y+)/.test(fmt))   
		     fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
		   for(var k in o)   
		     if(new RegExp("("+ k +")").test(fmt))   
		   fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
		   return fmt;   
		 }; 
		 // js 一串数字1403149534转换为日期格式 
		function transDateTime(timestamp){
			var date = new Date(timestamp);    //根据时间戳生成的时间对象
	        return date.format("yyyy-MM-dd hh:mm:ss");
        }
		
		 // 后续版本待完善功能提醒
		function notFinishTips(){
			bootbox.alert({
				'title' : "友情提醒",
				'message' : '对不起，该功能暂未实施，请等待后续开发完成后测试...'
			});
		}
		
		function drawProcessDiagraph(st, processType, pymtMod, selRdmgdsMod){

			if("01"==processType){
				// 展示流程图
				$(".steps").step({
					  stepNames: [ '长约发起','长约确认','长约确认','确认承保','长约一审','长约二审','长约终结'],
					  initStep: getStepSeq(st, processType, 0, 0)
				})
			} else if("02"==processType){
				if("01"==pymtMod){
					// 展示流程图
					$(".steps").step({
						  stepNames: [ '发起','审核','保证金支付','融资发放','货款支付','货款支付','确认承保','订单完成'],
						  initStep: getStepSeq(st, processType, pymtMod, 0)
					})
				} else if("02"==pymtMod){
					// 展示流程图
					$(".steps").step({
						  stepNames: [ '发起','审核','保证金支付','融资发放','货款支付','确认收款','确认承保','订单完成'],
						  initStep: getStepSeq(st, processType, pymtMod, 0)
					})
				} else if("03"==pymtMod){
					// 展示流程图
					$(".steps").step({
						  stepNames: [ '发起','审核','保证金支付','融资发放','货款支付','确认收款','订单完成'],
						  initStep: getStepSeq(st, processType, pymtMod, 0)
					})
				}
			}
		}

		function getStepSeq(aplyPcstpCd, processType, pymtMod, selRdmgdsMod){

			if("01"==processType){
				if("01" == aplyPcstpCd)
					return 0;
				else if("02" == aplyPcstpCd)
					return 1;
				else if("03" == aplyPcstpCd)
					return 2;
				else if("04" == aplyPcstpCd)
					return 3;
				else if("05" == aplyPcstpCd)
					return 4;
				else if("06" == aplyPcstpCd)
					return 5;
				else if("07" == aplyPcstpCd)
					return 6;
				else if("10" == aplyPcstpCd)
					return 0;
				else if("11" == aplyPcstpCd)
					return 0;
				else if("99" == aplyPcstpCd)
					return 7;
			} else if("02"==processType) {
				if("01"==pymtMod){
					if("31" == aplyPcstpCd)
						return 0;
					else if("32" == aplyPcstpCd)
						return 1;
					else if("33" == aplyPcstpCd)
						return 2;
					else if("34" == aplyPcstpCd)
						return 3;
					else if("35" == aplyPcstpCd)
						return 4;
					else if("36" == aplyPcstpCd)
						return 5;
					else if("38" == aplyPcstpCd)
						return 6;
					else if("39" == aplyPcstpCd)
						return 7;
					else if("10" == aplyPcstpCd)
						return 0;
					else if("11" == aplyPcstpCd)
						return 0;
					else if("99" == aplyPcstpCd)
						return 7;
				} else 	if("02"==pymtMod){
					if("31" == aplyPcstpCd)
						return 0;
					else if("32" == aplyPcstpCd)
						return 1;
					else if("33" == aplyPcstpCd)
						return 2;
					else if("34" == aplyPcstpCd)
						return 3;
					else if("35" == aplyPcstpCd)
						return 4;
					else if("37" == aplyPcstpCd)
						return 5;
					else if("38" == aplyPcstpCd)
						return 6;
					else if("39" == aplyPcstpCd)
						return 7;
					else if("10" == aplyPcstpCd)
						return 0;
					else if("11" == aplyPcstpCd)
						return 0;
					else if("99" == aplyPcstpCd)
						return 7;
				} else 	if("03"==pymtMod){
					if("31" == aplyPcstpCd)
						return 0;
					else if("32" == aplyPcstpCd)
						return 1;
					else if("33" == aplyPcstpCd)
						return 2;
					else if("34" == aplyPcstpCd)
						return 3;
					else if("35" == aplyPcstpCd)
						return 4;
					else if("37" == aplyPcstpCd)
						return 5;
					else if("39" == aplyPcstpCd)
						return 6;
					else if("10" == aplyPcstpCd)
						return 0;
					else if("11" == aplyPcstpCd)
						return 0;
					else if("99" == aplyPcstpCd)
						return 6;
				}
			}
		}