var BlogIndex = function () {
	var _DataAPI = new Object();
	//初始化编辑页面
	_DataAPI.addExemptionLetterById = "s/exemptionLetter/initAddOrEditExemptionLetter.do";
	//保存豁免函
	_DataAPI.saveExemptionLetter = "s/exemptionLetter/saveExemptionLetter.json";

	//初始化敏感人员申告编辑页面
	_DataAPI.addSeStaffReportById = "s/seStaffReport/initAddOrEditSeStaffReport.do";
	//保存敏感人员申告
	_DataAPI.saveSeStaffReport = "s/seStaffReport/saveSeStaffReport.json";

	//初始化投委评分申告编辑页面
	_DataAPI.addReportScoreById = "s/reportScore/initAddOrEditReportScore.do";
	//保存投委评分
	_DataAPI.saveReportScore = "s/reportScore/saveReportScore.json";

	//导出豁免函
	_DataAPI.exportReport = "export/exportBlogFile.do";

	//导出敏感人申告
	_DataAPI.exportSeStaffReport = "s/seStaffReport/exportSeStaffReport.do";
	//导出评分
	_DataAPI.exportReportScore = "s/reportScore/exportReportScore.do";

	var _exportInfoReport= function () {
		var URL = _DataAPI.exportReport;
		window.open(URL);
	};

	var _saveMeetingVoteDataBefore = function () {
		// Form 验证字段内容
		jQuery("#exemptionLetterForm").validate({
			focusInvalid: false,
			errorClass: 'dd-validate-error', // 使用自定义样式
			validClass: 'dd-validate-valid',// 使用自定义样式
			errorElement: "span",
			rules: {
				"twoDate": {
					required: true
				}/*,
                "context": {
                    required: true,
                    maxlength: 1000
                }*/
			},
			onkeyup: false,
			messages: {
//                "meetingVoteName" : {
//                    required : i18n.t('INDEXSET.VALID.INDEXSETNAME_NULL')
//                }
			}
		});
		var addForm = jQuery("#exemptionLetterForm");
		if (!addForm.valid()){
			bootbox.alert({
				size: 'small',
				message: '数据有误，请重新输入！'
			});
			return false;
		}
		bootbox.confirm({
			size: 'small',
			message: i18n.t('GENERAL.CONFIRM.GEN'),
			buttons: {
				confirm: {
					label: i18n.t('GENERAL.OK')
				},
				cancel: {
					label: i18n.t('GENERAL.CANCEL')
				}
			},
			callback: function (result) {
				if (result) {
					_saveExemptionLetter();
				}
			}
		})
	};

	//复选框选中与取消
	var _onCheckbox = function (checkbox) {
		if (checkbox.checked == true) {
			checkbox.value = "1";
		} else {
			checkbox.value = "2";
		}
		$(".error").remove();
	};

	/**
	 * 初始化新增或编辑豁免函页面
	 * @param stageId
	 * @private
	 */
	var _addExemptionLetterById = function(id,meetingId,onlyView){
		_openNewWindow(_DataAPI.addExemptionLetterById+'?id='+id+'&meetingId='+meetingId+'&remark=0&onlyView='+onlyView,'1000','600');
		/*jQuery.ajax({
            type: 'GET',
            url: _DataAPI.addExemptionLetterById+ "?id="+id+"&meetingId="+meetingId+"&remark=0&onlyView="+onlyView,
            success: function (data) {
                bootbox.dialog({
                    size: 'large',
                    message: data,
                    title: '豁免函'
                });
            }
        });*/
	};

	/**
	 * 保存豁免函
	 */
	var _saveExemptionLetter = function () {
		var json = jQuery("#exemptionLetterForm").serialize();
		jQuery('#exemptionLetterForm').showLoading();
		jQuery.ajax({
			type: 'POST',
			url: _DataAPI.saveExemptionLetter,
			data: json,
			success: function (data) {
				var result = data;
				var dataHtml = "";
				if (result.resultCode == 1) {
					dataHtml = i18n.t('GENERAL.SUCCESS');
				} else {
					dataHtml = result.message;
				}
				jQuery('#exemptionLetterForm').hideLoading();
				bootbox.alert({
					size: 'small',
					message: dataHtml,
					callback: function (result) {
						window.close();
						//bootbox.hideAll(); // 隐藏所有的bootbox
					}
				});
			}
		});
	};

	/**
	 * 保存敏感人申告前校验
	 * @returns {boolean}
	 * @private
	 */
	var __saveSeStaffReportDataBefore = function () {
		if ($(":checkbox:checked").size() == 0) {
			$(":checkbox").after("<font class='error' color='red'> 请选择任意一项或多项</font>");
			bootbox.alert({
				size: 'small',
				message: '数据有误，请选择申告事由！'
			});
			return;
		}
		// Form 验证字段内容
		jQuery("#SESTAFFREPORT_FORM").validate({
			focusInvalid: false,
			errorClass: 'dd-validate-error', // 使用自定义样式
			validClass: 'dd-validate-valid',// 使用自定义样式
			errorElement: "span",
			rules: {
				/*,
                 "context": {
                     required: true,
                     maxlength: 1000
                 }*/
			},
			onkeyup: false,
			messages: {
//                "meetingVoteName" : {
//                    required : i18n.t('INDEXSET.VALID.INDEXSETNAME_NULL')
//                }
			}
		});
		var addForm = jQuery("#SESTAFFREPORT_FORM");
		if (!addForm.valid()){
			bootbox.alert({
				size: 'small',
				message: '数据有误，请重新输入！'
			});
			return false;
		}
		bootbox.confirm({
			size: 'small',
			message: i18n.t('GENERAL.CONFIRM.GEN'),
			buttons: {
				confirm: {
					label: i18n.t('GENERAL.OK')
				},
				cancel: {
					label: i18n.t('GENERAL.CANCEL')
				}
			},
			callback: function (result) {
				if (result) {
					_saveSeStaffReport();
				}
			}
		})
	};

	/**
	 * 初始化新增或编辑敏感人员申告页面
	 * @param stageId
	 * @private
	 */
	var _addSeStaffReportById = function(id,meetingId,onlyView){
		_openNewWindow(_DataAPI.addSeStaffReportById+'?id='+id+'&meetingId='+meetingId+'&remark=0&onlyView='+onlyView,'1000','700');
		/*jQuery.ajax({
            type: 'GET',
            url: _DataAPI.addSeStaffReportById+ "?id="+id+"&meetingId="+meetingId+"&remark=0&onlyView="+onlyView,
            success: function (data) {
                bootbox.dialog({
                    size: 'large',
                    message: data,
                    title: '敏感人申告'
                });
            }
        });*/
	};

	/**
	 * 保存敏感人员申告
	 */
	var _saveSeStaffReport = function () {
		var json = jQuery("#SESTAFFREPORT_FORM").serialize();
		jQuery('#SESTAFFREPORT_FORM').showLoading();
		jQuery.ajax({
			type: 'POST',
			url: _DataAPI.saveSeStaffReport,
			data: json,
			success: function (data) {
				var result = data;
				var dataHtml = "";
				if (result.resultCode == 1) {
					dataHtml = i18n.t('GENERAL.SUCCESS');
				} else {
					dataHtml = result.message;
				}
				jQuery('#SESTAFFREPORT_FORM').hideLoading();
				bootbox.alert({
					size: 'small',
					message: dataHtml,
					callback: function (result) {
						jQuery(".dd-nav-tabs li.active a").click();
						//DecisionMeetingVote.init();
						window.close();
						//bootbox.hideAll(); // 隐藏所有的bootbox
					}
				});
			}
		});
	};

	/**
	 * 保存投委评分前校验
	 * @returns {boolean}
	 * @private
	 */
	var __saveReportScoreDataBefore = function () {
		// Form 验证字段内容
		jQuery("#REPORTSCORE_FORM").validate({
			focusInvalid: false,
			errorClass: 'dd-validate-error', // 使用自定义样式
			validClass: 'dd-validate-valid',// 使用自定义样式
			errorElement: "span",
			rules: {
				"oneScore": {
					required:true,
					range:[0,20]
				},
				"twoScore": {
					required:true,
					range:[0,10]
				},
				"threeScore": {
					required:true,
					range:[0,10]
				},
				"fourScore": {
					required:true,
					range:[0,20]
				},
				"fiveScore": {
					required:true,
					range:[0,20]
				},
				"sixScore": {
					required:true,
					range:[0,20]
				},
				"oneRemarks": {
					maxlength: 1000
				},
				"twoRemarks": {
					maxlength: 1000
				},
				"threeRemarks": {
					maxlength: 1000
				},
				"fourRemarks": {
					maxlength: 1000
				},
				"fiveRemarks": {
					maxlength: 1000
				},
				"sixRemarks": {
					maxlength: 1000
				}

			},
			onkeyup: false,
			messages: {
//                "meetingVoteName" : {
//                    required : i18n.t('INDEXSET.VALID.INDEXSETNAME_NULL')
//                }
			}
		});
		var addForm = jQuery("#REPORTSCORE_FORM");
		if (!addForm.valid()){
			bootbox.alert({
				size: 'small',
				message: '数据有误，请重新输入！'
			});
			return false;
		}
		bootbox.confirm({
			size: 'small',
			message: i18n.t('GENERAL.CONFIRM.GEN'),
			buttons: {
				confirm: {
					label: i18n.t('GENERAL.OK')
				},
				cancel: {
					label: i18n.t('GENERAL.CANCEL')
				}
			},
			callback: function (result) {
				if (result) {
					_saveReportScore();
				}
			}
		})
	};

	/**
	 * 初始化新增或编辑投委评分页面
	 * @param stageId
	 * @private
	 */
	var _addReportScoreById = function(id,meetingId,onlyView){
		/*jQuery.ajax({
            type: 'GET',
            url: _DataAPI.addReportScoreById+ "?id="+id+"&meetingId="+meetingId+"&remark=0&onlyView="+onlyView,
            success: function (data) {
                bootbox.dialog({
                    size: 'large',
                    message: data,
                    title: '投委评分',
                    className: 'boxwidth'
                });
            }
        });*/
		_openNewWindow(_DataAPI.addReportScoreById+'?id='+id+'&meetingId='+meetingId+'&remark=0&onlyView='+onlyView,'1000','700');
	};

	/**
	 * 保存投委评分
	 */
	var _saveReportScore = function () {
		var json = jQuery("#REPORTSCORE_FORM").serialize();
		jQuery('#REPORTSCORE_FORM').showLoading();
		jQuery.ajax({
			type: 'POST',
			url: _DataAPI.saveReportScore,
			data: json,
			success: function (data) {
				var result = data;
				var dataHtml = "";
				if (result.resultCode == 1) {
					dataHtml = i18n.t('GENERAL.SUCCESS');
				} else {
					dataHtml = result.message;
				}
				jQuery('#REPORTSCORE_FORM').hideLoading();
				bootbox.alert({
					size: 'small',
					message: dataHtml,
					callback: function (result) {
						//bootbox.hideAll(); // 隐藏所有的bootbox
						window.close();
					}
				});
			}
		});
	};

	/**
	 * 打开一个新的窗口
	 * @private
	 */
	var _openNewWindow = function(url, width, height){
		var windowId = window.location.href;
		var left = (window.screen.availWidth - width) / 2;
		window.open(url, "", "width=" + width + "px,height=" + height + "px,left=" + left
			+ "px, top: 0px, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no,titlebar=no, menubar=no, status=no");
	};


	return {
		onCheckbox: function (checkbox) {
			_onCheckbox(checkbox);
		},
		addExemptionLetterById:function(id,meetingId,onlyView){
			_addExemptionLetterById(id,meetingId,onlyView);
		},
		saveExemptionLetter:function(){
			_saveMeetingVoteDataBefore();
		},
		addSeStaffReportById:function(id,meetingId,onlyView){
			_addSeStaffReportById(id,meetingId,onlyView);
		},
		saveSeStaffReport:function(){
			__saveSeStaffReportDataBefore();
		},
		addReportScoreById:function(id,meetingId,onlyView){
			_addReportScoreById(id,meetingId,onlyView);
		},
		saveReportScore:function(){
			__saveReportScoreDataBefore();
		},
		exportInfoReport:function(){
			_exportInfoReport();
		}
	};
}();