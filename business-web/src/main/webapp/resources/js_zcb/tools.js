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