(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        //initialize: function () {
        //},
        initializeData: function () {
            this.model = $pt.createModel($page.model.createModel(), $page.validator);
            this.codes = $pt.createModel($page.codes);
            var _this = this;
            this.model.addPostChangeListener("DateOfLossFrom", function (evt) {
                var DateOfLossFrom = evt.model.get("DateOfLossFrom");
                var DateOfLossTo = evt.model.get("DateOfLossTo");
                var nowDate =  moment(new Date()).format("YYYY-MM-DD HH:mm");
                if (null !=  DateOfLossTo && "" !=DateOfLossTo && undefined != DateOfLossTo) {
                    DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");
                }
                if (null !=  DateOfLossFrom && "" !=DateOfLossFrom && undefined != DateOfLossFrom) {
                    DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                    if (DateOfLossFrom > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: [' Loss Start Date must be earlier or equal to today.']
                        });
                        _this.model.set("DateOfLossFrom", "");
                    }
                    //if(DateOfLossFrom > DateOfLossTo){
                    //    NConfirm.getConfirmModal().show({
                    //        title: 'System Message',
                    //        disableClose: true,
                    //        messages: [' Loss Start Date must be earlier or equal to Loss End Date.']
                    //    });
                    //    _this.model.set("DateOfLossFrom", "");
                    //}
                    _this.model.set("DateOfLossTo",DateOfLossFrom);
                }

            });

            this.model.addPostChangeListener("DateOfLossTo", function (evt) {
                var DateOfLossTo = evt.model.get("DateOfLossTo");
                var DateOfLossFrom = evt.model.get("DateOfLossFrom");
                var nowDate =  moment(new Date()).format("YYYY-MM-DD HH:mm");
                if (null !=  DateOfLossFrom && "" !=DateOfLossFrom) {
                    DateOfLossFrom = DateOfLossFrom.substring(0, 10);
                }
                if (null !=  DateOfLossTo && "" !=DateOfLossTo && undefined != DateOfLossTo) {
                    DateOfLossTo = DateOfLossTo.substring(0, 10);
                    if (DateOfLossTo < DateOfLossFrom) {
                        console.log('Loss End Date  must be later or equal to Loss Start Date.');

                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss End Date  must be later or equal to Loss Start Date.']
                        });
                        _this.model.set("DateOfLossTo", "");
                    }

                    if (DateOfLossTo > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss End Date must be earlier or equal to today.']
                        });
                        _this.model.set("DateOfLossTo", "");

                    }
                }
            });
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        submitData: function () {
            this.model.validate();
            if(this.model.hasError() == true){
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }
            //this.;
            var _this = this;
            console.log(this.checkCode());
            if (this.checkCode()) {
                var afterCreateEvent = function (data, textStatus, jqXHR) {
                    var eventId = data.EventId;
                    var eventModel = data;
                    NConfirm.getConfirmModal().show("System message", {
                        close: true,
                        messages: ['Successful.'],
                        afterClose: function (data) {
                            _this.loadEventInformation(eventModel);
                        }
                    });
                }.bind(this);
                $page.service.createEvent(this.model.getCurrentModel(), false, false, afterCreateEvent);
            }
        },

        loadEventInformation :function(eventModel){

            console.log(eventModel);
            var resource = {
                ResourceId: eventModel.EventId,
                ResourceType: 2,
                ResourceNo: eventModel.EventCode,
                OwnerId: eventModel.TaskOwner
            };
            var url = $pt.getURL('ui.claim.eventInfo');

            var done =function(flag){
                window.location.href = url + "?eventId=" + eventModel.EventId + "&pageType=0";
            }.bind(this);
            this.lockInfo(resource, done);

        },
        checkCode: function () {
            var EventCode = this.model.get("EventCode");
            //var type = $page.pageType;
            var Flag = true;
            var checkCodeDone = function (data, textStatus, jqXHR) {
                console.log(data);
                for (var x = 0; x < data.length; x++) {
                    if (EventCode == data[x]) {
                        Flag = false;
                        break;
                    }
                }
                if (Flag == false) {
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: ['There is an existing event with this code, you cannot create a same one.']
                    });
                }
            }.bind(this);
            $page.service.checkCode(EventCode, false, false, checkCodeDone);
            return Flag;
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));