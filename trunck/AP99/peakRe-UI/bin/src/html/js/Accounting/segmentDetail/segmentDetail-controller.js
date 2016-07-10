
(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.StatusFlag = true;
    var me = {
        initializeErrorModel: function () {
            return true;
        },
        initializeData: function () {
            var urlData = $pt.getUrlData();
            var segmentId = urlData.SegmentId;
            this.model = $pt.createModel($page.model);
            this.model.set("SegmentId", segmentId);
            var _this = this;
            $page.service.load(segmentId, false, false, function (data, textStatus, jqXHR) {
                _this.updateModel(data);
            }, function () {
            });

            $page.service.loadTable(segmentId, false, false, function (data, textStatus, jqXHR) {
                _this.updateTable(data, true);
            }, function () {
            });
            this.model.addPostChangeListener("Status", function (evt) {
                var Status = evt.model.get("Status");
                console.log("Status==" + Status);
                if (Status == "0") {
                    $page.StatusFlag = false;
                } else {
                    $page.StatusFlag = true;
                }


            });
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        updateModel: function (data) {
            console.log(data);
            this.model.mergeCurrentModel({
                SegmentName: data.SegmentName,
                SegmentCode: data.SegmentCode,
                Status: data.Status,
                Cob: data.Cob,
                CedentCountry: data.CedentCountry,
                ContractNature: data.ContractNature
            });
        },
        updateTable: function (data, updateUI) {
            if (data != null) {
                this.model.mergeCurrentModel({
                    segmentTable: data ? data : []
                });
            }
        },
        save: function () {
            var _this = this;
            if (!$page.StatusFlag) {
                var isContinue = false;
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    //disableClose: true,
                    messages: ['The Segment Status is Invalid, Are you sure to submit?'],
                    onConfirm: function (model) {
                        isContinue = true;
                    },
                    onCancel: function () {
                        return false;
                    },
                    afterClose: function () {
                        if (isContinue) {
                            _this.submit();
                        }
                    }
                });
            } else {
                _this.submit();
            }
        },
        submit: function () {
            var SegmentId = this.model.get("SegmentId");
            var Status = this.model.get("Status");
            var successFun = function (data, textStatus, jqXHR) {
                //console.log(data);
                if (data.result == 'success') {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Save Successful'],
                        afterClose: function () {
                            window.location.href = "segmentQuery.html";
                        }
                    });

                } else {
                    NConfirm.getConfirmModal().show({
                        title: 'Warning',
                        disableClose: true,
                        messages: ['Name or Three Conditions of Segment is the same with valid existing Segment']
                    });
                }
            };
            var failFun = function (jqXHR, textStatus, errorThrown) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Save Failure'],
                    onConfirm: function () {
                        window.location.reload();
                    }
                });
            };
            $page.service.update(
                {
                    SegmentId: SegmentId,
                    Status: Status
                }, false, false, successFun, failFun);
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));