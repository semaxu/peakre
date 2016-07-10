(function (context) {
    var $page = $pt.getService(context, '$page');
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializeData: function () {
            this.model = $pt.createModel($page.model.createBaseModel(), $page.validator.supiAdjustmentValidate());
            var urlData = $pt.getUrlData();
            this.model.mergeCurrentModel(urlData);
            var contCompId = urlData.ContCompId;
            this.loadSUPIAdjustment({
                ContCompId: contCompId
            });
            var afterLoadAllUser = function (data, textStatus, jqXHR) {
                $helper.lowerKeysOfJSON(data);
                $page.model.userCodes = $page.codes.createMutableCodeTable();
                $page.model.userCodes.reset(data);
            }.bind(this);
            $page.service.loadAllUserCodes(null, false, false, afterLoadAllUser);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        loadSUPIAdjustment: function (criteria) {
            var _self = this;
            var done = function (data) {
                _self.model.mergeCurrentModel(data);
                _self.initialData();
            }.bind(this);
            $page.service.load(criteria, false, false, done, null);
        },
        //adjustDate: function () {
        //    var _self = this;
        //    var isSaved = true;
        //    var initialModel = _self.model.getCurrentModel();
        //    //var adjustedSUPIList=initialModel.SupiSecList.AdjustedSUPIList;
        //    initialModel.SupiSecList.forEach(function (item) {
        //        var adjustedSUPIList = item.AdjustedSUPIList;
        //        console.log(adjustedSUPIList);
        //        var i = 0;
        //
        //        for (i = 0; i < adjustedSUPIList.length; i++) {
        //            var ajusti = adjustedSUPIList[i].AdjustmentDate;
        //            if (ajusti == null || ajusti == '') {
        //                isSaved = false;
        //                //NConfirm.getConfirmModal().show({
        //                //    title: 'Alert',
        //                //    disableClose: true,
        //                //    messages: ['Invalid Date of Adjustment. Same Date is required for SUPI Adjustment in each Currency type.']
        //                //});
        //                break;
        //            }
        //            var j = i + 1;
        //            var currentAdjustmentDate = moment(ajusti).format("YYYY-MM-DD");
        //            for (j = i + 1; j < adjustedSUPIList.length; j++) {
        //                var ajustj = moment(adjustedSUPIList[j].AdjustmentDate).format("YYYY-MM-DD");
        //                if (currentAdjustmentDate != ajustj) {
        //                    isSaved = false;
        //                    //console.log("+++++++"+isSaved);
        //                    //NConfirm.getConfirmModal().show({
        //                    //    title: 'Alert',
        //                    //    disableClose: true,
        //                    //    messages: ['Invalid Date of Adjustment. Same Date is required for SUPI Adjustment in each Currency type.']
        //                    //});
        //                    break;
        //                }
        //            }
        //            if (isSaved == false) {
        //                break;
        //            }
        //        }
        //    });
        //    return isSaved;
        //
        //},
        saveSUPIAdjustment: function () {
            this.model.validate();
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Amount could not be null.']
                });
                return false;
            }

            var _self = this;
            var isSaved = false;
            var done = function (data) {
                _self.model.mergeCurrentModel(data);
                isSaved = true;
            }.bind(this);
            var fail = function () {
                NConfirm.getConfirmModal().show({
                    title: 'Alert',
                    disableClose: true,
                    messages: $pt.getMessage("SaveFail")
                });
                isSaved = false;
            }.bind(this);
            $page.service.save(this.model.getCurrentModel(), false, false, done, fail);
            return isSaved;
        },
        initialData: function () {
            var initialModel = this.model.getCurrentModel();
            var _self = this;
            var criteria = {ContCompId: _self.model.get("ContCompId")};
            var done = function (data) {
                if (initialModel.SupiSecList) {
                    initialModel.SupiSecList.forEach(function (item) {
                        if (item.AdjustedSUPIList) {
                            item.AdjustedSUPIList.forEach(function (inner) {
                                inner.AdjustmentDate = moment().format('YYYY-MM-DDTHH:mm:ss');
                                inner.DateAt = moment(data.ReinsEnding).format('YYYY-MM-DDTHH:mm:ss');
                            })
                        }
                    })
                }
            }
            $page.service.loadContractInfo(criteria, false, false, done, function () {
            });
        }
    }));

    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));
