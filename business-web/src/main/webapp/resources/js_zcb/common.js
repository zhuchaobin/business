	function transAplyPsrltCd(aplyPsrltCd) {
			if (aplyPsrltCd == '01') {
				return "通过";
			} else if (aplyPsrltCd == '02') {
				return "退回";
			} else if (aplyPsrltCd == '03') {
				return "拒绝";
			}  else if (aplyPsrltCd == '04') {
				return "撤销";
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
			} else if (value == '31') {
				return "发起";
			} else if (value == '32') {
				return "审核";
			} else if (value == '33') {
				return "保证金支付";
			} else if (value == '34') {
				return "融资发放";
			} else if (value == '35') {
				return "货款支付";
			} else if (value == '36') {
				return "货款支付";
			} else if (value == '37') {
				return "确认收款";
			} else if (value == '38') {
				return "确认承保";
			} else if (value == '39') {
				return "终止";
			} else if (value == '61') {
				return "发货发起";
			} else if (value == '62') {
				return "接货承运";
			} else if (value == '63') {
				return "选择赎货方式";
			} else if (value == '64') {
				return "回款赎货";
			} else if (value == '65') {
				return "质押置换";
			} else if (value == '66') {
				return "出质";
			} else if (value == '67') {
				return "通知转货权";
			} else if (value == '68') {
				return "通知转货权";
			} else if (value == '69') {
				return "转货权";
			} else if (value == '70') {
				return "确定质押";
			} else if (value == '71') {
				return "存入自有货物";
			} else if (value == '72') {
				return "通知解押";
			} else if (value == '73') {
				return "转货权";
			} else if (value == '74') {
				return "提取货物";
			} else if (value == '79') {
				return "终止";
			} else
				return value;
		}	
		
		function getNextStepName(currStep, flag){
			// currStep,当前环节；flag，跳转方向1通过，2退回
			var nextStepName = "";
			if("01" == currStep){
				if(1==flag) nextStepName="长约确认(供应链)";
				else if(2==flag) nextStepName="";
			} else if("02" == currStep){
				if(1==flag) nextStepName="长约确认(下游)";
				else if(2==flag) nextStepName="长约发起（平台）";
			}  else if("03" == currStep){
				if(1==flag) nextStepName="确认承保（保险公司）";
				else if(2==flag) nextStepName="长约确认(供应链)";
			} else if("04" == currStep){
				if(1==flag) nextStepName="长约一审（平台）";
				else if(2==flag) nextStepName="长约确认(下游)";
			} else if("05" == currStep){
				if(1==flag) nextStepName="长约二审（平台）";
				else if(2==flag) nextStepName="确认承保（保险公司）";
			} else if("06" == currStep){
				if(1==flag) nextStepName="长约终止（平台）";
				else if(2==flag) nextStepName="长约一审（平台）";
			} else if("07" == currStep){
				if(1==flag) nextStepName="长约结束";
				else if(2==flag) nextStepName="长约二审（平台）";
			} else if("31" == currStep){
				if(1==flag) nextStepName="订单审核(平台)";
				else if(2==flag) nextStepName="";
			} else if("32" == currStep){
				if(1==flag) nextStepName="保证金支付(下游)";
				else if(2==flag) nextStepName="订单发起(供应链)";
			} else if("33" == currStep){
				if(1==flag) nextStepName="融资发放(银行)";
				else if(2==flag) nextStepName="订单审核(平台)";
			} else if("34" == currStep){
				if(1==flag) nextStepName="货款支付(下游)";
				else if(2==flag) nextStepName="保证金支付(下游)";
			} else if("35" == currStep){
				if(1==flag) nextStepName="货款支付(供应链)";
				else if(2==flag) nextStepName="融资发放(银行)";
			} else if("36" == currStep){
				if(1==flag) nextStepName="确认收款(供应链)";
				else if(2==flag) nextStepName="货款支付(下游)";
			} else if("37" == currStep){
				if(1==flag) nextStepName="确认承保(保险公司)";
				else if(2==flag) nextStepName="货款支付(供应链)";
			} else if("38" == currStep){
				if(1==flag) nextStepName="订单终止(供应链)";
				else if(2==flag) nextStepName="确认收款(供应链)";
			} else if("39" == currStep){
				if(1==flag) nextStepName="订单完结";
				else if(2==flag) nextStepName="确认承保(保险公司)";
			} else if("61" == currStep){
				if(1==flag) nextStepName="接货承运(物流)";
				else if(2==flag) nextStepName="订单终止(供应链)";
			} else if("62" == currStep){
				if(1==flag) nextStepName="选择赎货方式(供应链)";
				else if(2==flag) nextStepName="发货发起(上游)";
			} else if("63" == currStep){
				if(1==flag) nextStepName="回款赎货(下游)";
				else if(2==flag) nextStepName="接货承运(物流)";
			} else if("64" == currStep){
				if(1==flag) nextStepName="质押置换(供应链)";
				else if(2==flag) nextStepName="选择赎货方式(供应链)";
			} else if("65" == currStep){
				if(1==flag) nextStepName="出质(银行)";
				else if(2==flag) nextStepName="回款赎货(下游)";
			} else if("66" == currStep){
				if(1==flag) nextStepName="通知转货权(银行)";
				else if(2==flag) nextStepName="质押置换(供应链)";
			} else if("67" == currStep){
				if(1==flag) nextStepName="通知转货权(供应链)";
				else if(2==flag) nextStepName="出质(银行)";
			} else if("68" == currStep){
				if(1==flag) nextStepName="转货权1(仓储)";
				else if(2==flag) nextStepName="通知转货权(银行)";
			} else if("69" == currStep){
				if(1==flag) nextStepName="确定质押(仓储)";
				else if(2==flag) nextStepName="通知转货权(供应链)";
			} else if("70" == currStep){
				if(1==flag) nextStepName="存入自有货物(下游)";
				else if(2==flag) nextStepName="转货权1(仓储)";
			} else if("71" == currStep){
				if(1==flag) nextStepName="通知解押(银行)";
				else if(2==flag) nextStepName="确定质押(仓储)";
			} else if("72" == currStep){
				if(1==flag) nextStepName="转货权2(仓储)";
				else if(2==flag) nextStepName="存入自有货物(下游)";
			} else if("73" == currStep){
				if(1==flag) nextStepName="提取货物(下游)";
				else if(2==flag) nextStepName="通知解押(银行)";
			} else if("74" == currStep){
				if(1==flag) nextStepName="发货终止(供应链)";
				else if(2==flag) nextStepName="转货权2(仓储)";
			}
			return nextStepName;
		}
		