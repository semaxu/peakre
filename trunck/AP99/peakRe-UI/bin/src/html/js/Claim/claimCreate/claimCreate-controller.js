(function (context) {
    var $page = $pt.getService(context, '$page');
    //$page.EventCodes = $page.codes.createMutableCodeTable();

    var me = {
        //initialize: function () {
        //},
        //getInitialState: function() {
        //    return {model1: this.model};
        //},
        initializeData: function () {
            this.model = $pt.createModel($page.model.createModel(), $page.validator);
            this.codes = $pt.createModel($page.codes);
            var _this = this;

            this.model.addPostChangeListener("DateOfReport", function (evt) {
                var DateOfReport = evt.model.get("DateOfReport");
                var DateOfLossFrom = evt.model.get("DateOfLossFrom");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");
                if (null != DateOfLossFrom && "" != DateOfLossFrom && undefined != DateOfLossFrom) {
                    DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                }
                if (null != DateOfReport && "" != DateOfReport && undefined != DateOfReport) {
                    DateOfReport = moment(DateOfReport).format("YYYY-MM-DD HH:mm");
                    if (DateOfReport < DateOfLossFrom) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: [' Date of Report cannot be earlier to Loss Start Date.']
                        });
                        _this.model.set("DateOfReport", "");
                    }
                    if (DateOfReport > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: [' Date of Report must be earlier or equal to today.']
                        });
                        _this.model.set("DateOfReport", "");
                    }
                }

            });
            this.model.addPostChangeListener("DateOfLossFrom", function (evt) {
                var DateOfLossFrom = evt.model.get("DateOfLossFrom");
                var DateOfLossTo = evt.model.get("DateOfLossTo");
                var DateOfReport = evt.model.get("DateOfReport");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");
                if (null != DateOfLossTo && "" != DateOfLossTo && undefined != DateOfLossTo) {
                    DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");
                }
                if (null != DateOfReport && "" != DateOfReport && undefined != DateOfReport) {
                    DateOfReport = moment(DateOfReport).format("YYYY-MM-DD HH:mm");
                }
                if (null != DateOfLossFrom && "" != DateOfLossFrom && undefined != DateOfLossFrom) {
                    DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                    if (DateOfReport < DateOfLossFrom) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss Start Date must be earlier or equal to Date of Report.']
                        });
                        _this.model.set("DateOfLossFrom", "");
                    }
                    //if(DateOfLossFrom > DateOfLossTo){
                    //    NConfirm.getConfirmModal().show({
                    //        title: 'System Message',
                    //        disableClose: true,
                    //        messages: ['Loss Start Date must be earlier or equal to Loss End Date.']
                    //    });
                    //}
                    if (DateOfLossFrom > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss Start Date must be earlier or equal to today.']
                        });
                        _this.model.set("DateOfLossFrom", "");
                    }
                }
                _this.model.set("DateOfLossTo", DateOfLossFrom);


            });
            this.model.addPostChangeListener("DateOfLossTo", function (evt) {
                var DateOfLossTo = evt.model.get("DateOfLossTo");
                var DateOfLossFrom = evt.model.get("DateOfLossFrom");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");
                if (null != DateOfLossFrom && "" != DateOfLossFrom && undefined != DateOfLossFrom) {
                    DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                }
                if (null != DateOfLossTo && "" != DateOfLossTo && undefined != DateOfLossTo) {
                    DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");
                    if (DateOfLossTo < DateOfLossFrom) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss End Date must be later or equal to Loss Start Date.']
                        });
                        _this.model.set("DateOfLossTo", "");
                    }
                    if (DateOfLossTo > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss End Date must be earlier or equal to Today.']
                        });
                        _this.model.set("DateOfLossTo", "");
                    }
                }


            });

            var afterEventCode = function (data, textStatus, jqXHR) {
                //console.log(data);
                //$helper.lowerKeysOfJSON(data);
                //$page.EventCodes.reset(data);
                //console.log($page.EventCodes);
                $helper.lowerKeysOfJSON(data);
                $page.model.eventCodes = $page.codes.createMutableCodeTable();
                $page.model.eventCodes.reset(data);
            }.bind(this);
            $page.service.loadEventCodes(null, false, false, afterEventCode);

        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        submitData: function () {
            this.model.validate();
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }
            console.log(this.model.validator);
            //return false;
            var _this = this;
            var isContinued = false;
            if (!this.checkInput()) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['This claim is beyond the date range of attached event, please check the dates.'],
                    onConfirm:function(model){
                        return false;
                    }
                });
            }else{
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    messages: ['Loss Start Date cannot be modified once submitted, are you sure to continue?'],
                    onConfirm: function () {
                        isContinued = true;
                    },
                    onCancel: function () {
                        isContinued = false;
                    },
                    afterClose: function () {
                        if (isContinued) {
                            $page.service.createclaim(_this.model.getCurrentModel(), false, false, saveClaimReturn);
                        }
                    }
                });
                var saveClaimReturn = function (data, textStatus, jqXHR) {
                    var claimNo = data.ClaimNo;
                    var claimModel = data;
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: ['The claim has been created successfully. Claim No. is  ' + claimNo],
                        afterClose: function (data) {
                            _this.loadClaimInformation(claimModel);
                        }
                    });
                }.bind(this);
                console.log(JSON.stringify(this.model.getCurrentModel()));

            }
        },


        loadClaimInformation :function(claimModel){

            console.log(claimModel);
            var resource = {
                ResourceId: claimModel.ClaimId,
                ResourceType: 2,
                ResourceNo: claimModel.ClaimNo,
                OwnerId: claimModel.TaskOwner
            };
            var url = $pt.getURL('ui.claim.claimInfo');

            var done =function(){
                window.location.href = url + "?claimId=" + claimModel.ClaimId + "&pageType=0";
            }.bind(this);
            this.lockInfo(resource, done);

        },
        //addListener
        checkInput: function () {
            var _this = this;
            var DateOfLossFrom = _this.model.get("DateOfLossFrom");
            var DateOfReport = _this.model.get("DateOfReport");
            var DateOfLossTo = _this.model.get("DateOfLossTo");

            DateOfReport = moment(DateOfReport).format("YYYY-MM-DD HH:mm");
            DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
            DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");

            console.log(this.model.get("EventId"));
            var params = {
                EventId: this.model.get("EventId")
            };
            var checkInFlag =true;
            var checkInNum = 0;
            if (this.model.get("EventId") != "" && this.model.get("EventId") != null) {
                var eventDateReturn = function (data, textStatus, jqXHR) {
                    var eventDateFrom = data.DateOfLossFrom;
                    var eventDateTo = data.DateOfLossTo;
                    eventDateFrom = moment(eventDateFrom).format("YYYY-MM-DD HH:mm");
                    eventDateTo = moment(eventDateTo).format("YYYY-MM-DD HH:mm");
                    if (DateOfLossFrom < eventDateFrom || DateOfLossFrom > eventDateTo) {
                      checkInNum = checkInNum + 1;
                    }
                    if (DateOfLossTo < eventDateFrom || DateOfLossTo > eventDateTo) {
                        checkInNum = checkInNum + 1;
                    }
                    if (checkInNum > 0) {
                        checkInFlag = false;
                    }
                }.bind(this);
                $page.service.checkEventDate(params, false, false, eventDateReturn)
            }
            return checkInFlag;
        }

    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));