;(function($) {
	serviceTime = {
		opt : {
			timeId : null,
			countdownId : "expireTime",
			url : "getServiceTimeAction.action",
			timeFormat : "yyyy/MM/dd hh:mm:ss",
			expireFormat : "mm:ss",
			serviceTimeMillis : null,	// 系統端時間(毫秒)
			expireTime : null			// token 到期時間(毫秒)
		},
		clientTimeMillis : new Date().getTime(),	// 本機端時間(毫秒)
		previousTime : null,						// 上次執行本機端時間(毫秒)
		getServiceTime : function() {
			$.getJSON(_cp + "/" + this.opt.url, function(result, status, xhr) {
				if ("success" == status) {
					serviceTime.opt.serviceTimeMillis = result.currentTimeMillis;
					serviceTime.opt.expireTime = result.expireTimeMillis;
					serviceTime.clientTimeMillis = new Date().getTime();
				} else {
					serviceTime.opt.timeFormat = "系統連線失敗";
				}
			});
		},
		showTime : function() {
			if (!this.opt.serviceTimeMillis || !this.clientTimeMillis)
				this.getServiceTime();

			var clientTime = new Date().getTime();
			if (this.previousTime)
				if (clientTime - this.previousTime > 3 * 1000 || clientTime - this.previousTime < 0) {
					this.getServiceTime();
					this.previousTime = null;
				}

			var thisTime = new Date(parseInt(this.opt.serviceTimeMillis) + ((this.previousTime) ? (clientTime - this.clientTimeMillis) : 0));
			// 顯示系統時間
			if (this.opt.timeFormat) {
				var timeStr = this.opt.timeFormat;
				timeStr = timeStr.replace(/yyyy/, thisTime.getFullYear());
				timeStr = timeStr.replace(/MM/, this.appendZero(thisTime.getMonth() + 1, 2));
				timeStr = timeStr.replace(/dd/, this.appendZero(thisTime.getDate(), 2));
				timeStr = timeStr.replace(/hh/, this.appendZero(thisTime.getHours(), 2));
				timeStr = timeStr.replace(/mm/, this.appendZero(thisTime.getMinutes(), 2));
				timeStr = timeStr.replace(/ss/, this.appendZero(thisTime.getSeconds(), 2));
				$("#" + this.opt.timeId).text(timeStr);
			}

			// 顯示倒數計時
			if (this.opt.expireTime && this.opt.expireFormat) {
				var timeStr = this.opt.expireFormat;
				var expireLong = (this.opt.expireTime - thisTime) / 1000;
				if (expireLong > 0) {
					timeStr = timeStr.replace(/mm/, this.appendZero(parseInt(expireLong / 60), 2));
					timeStr = timeStr.replace(/ss/, this.appendZero(parseInt(expireLong % 60), 2));
				} else {
					timeStr = "逾時";
				}

				$("#" + this.opt.countdownId).text(timeStr);
			}

			this.previousTime = clientTime;
		},
		appendZero : function(value, length) {
			if (value != undefined) {
				value = (value).toString();
				if (value.length < length) {
					for ( var i = 0; i < length - value.length; i++) {
						value = "0" + value;
					}
				}
			}
			return value;
		}
	}
	
	jQuery.fn.showSystemTime = function(options) {
		serviceTime.opt = $.extend(serviceTime.opt, {timeId : $(this).attr("id")}, options || {});
		serviceTime.showTime();
		setInterval("serviceTime.showTime()", 1000);
	};
})(jQuery);
