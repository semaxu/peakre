(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.StatusFlag = true;
    var me = {
        initializeData: function () {
            this.model = $pt.createModel($page.model);
            this.codes = $pt.createModel($page.codes);
            var _this = this;
            this.load(function (data, textStatus, jqXHR) {
                _this.updateModel(data);
            });

            this.model.addPostChangeListener("Status", function (evt) {
                var Status = evt.model.get("Status");
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
        load: function (done) {
            $page.service.load(done);
        },
        updateModel: function (data) {
            this.model.mergeCurrentModel({
                SegmentCode: data.segmentCode
            });
        },
        save: function (criteria, quiet, async, done, fail) {
            $page.service.save(criteria, quiet, async, done, fail);
        },
        submitData: function () {
            var _this = this;
            if (!$page.StatusFlag) {
                var isContinue = false;
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    //disableClose: true,
                    messages: ['The Segment Status is Invalid, Are you sure to submit?'],
                    onConfirm: function () {
                        isContinue = true;
                    },
                    onCancel: function () {
                        return false;
                    },
                    afterClose: function () {
                        if (isContinue) {
                            _this.submitSegment();
                        }
                    }
                });


            } else {
                _this.submitSegment();
            }

        },
        submitSegment: function () {
            var successFun = function (data, textStatus, jqXHR) {
                if (data.result == 'success') {

                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Save Successful'],
                        onConfirm: function () {
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
            this.save(this.model.getCurrentModel(), false, false, successFun, failFun);
        }

    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));