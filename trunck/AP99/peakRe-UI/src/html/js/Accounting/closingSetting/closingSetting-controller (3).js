(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        initializeData: function () {
            this.model = $pt.createModel({
                    condition: {
                        CountPerPage: 10,
                        PageIndex: 1
                    },
                    results: [],
                    lastCriteria: {}
                }
            );
            this.codes = $pt.createModel($page.codes);

            var _this = this;
            $page.service.load(function (data, textStatus, jqXHR) {
                $page.closingDate = data.ClosingDate;
                _this.updateModel(data);
            });
            //this.model.addPostChangeListener("ClosingDate", function (evt) {
            //    var closingDate = evt.model.get("ClosingDate");
            //    var array1 = closingDate.split("/");
            //    closingDate = new Date(array1[2] + "/" + array1[1] + "/" + array1[0]);
            //    closingDate = moment(closingDate).format("YYYY/MM/DD");
            //    var nowDate = moment(new Date()).format("YYYY/MM/DD");
            //    if(closingDate < nowDate ){
            //        NConfirm.getConfirmModal().show({
            //            title: 'System Message',
            //            disableClose: true,
            //            messages: ['Cut-off Date must be later or equal to Today ']
            //        });
            //        evt.model.set("ClosingDate", $page.closingDate);
            //    }
            //});
            this.loadTable(this.model.getCurrentModel().condition, false, false, function (data, textStatus, jqXHR) {
                console.log(data);
                _this.updateTable(data, true);
            }, null);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        loadTable: function (criteria, quiet, async, done, fail) {
            $page.service.loadTable(criteria, quiet, async, done, fail);
        },
        updateModel: function (data) {
            this.model.mergeCurrentModel({
                CurrentQuarter: data.Quarter,
                ClosingDate: data.ClosingDate,
                ClosingStatus: data.ClosingStatus
            });
        },
        updateTable: function (data, updateUI) {
            //console.log(data);
            this.model.mergeCurrentModel({
                closingHistory: data.Rows ? data.Rows : [],
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount,
                    countPerPage: data.CountPerPage
                }
            });
        },
        checkDate:function(){
            var closingDate = this.model.get("ClosingDate");
            var array1 = closingDate.split("/");
            closingDate = new Date(array1[2] + "/" + array1[1] + "/" + array1[0]);
            closingDate = moment(closingDate).format("YYYY/MM/DD");
            var nowDate = moment(new Date()).format("YYYY/MM/DD");
            if(closingDate < nowDate ){
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Cut-off Date must be later or equal to Today ']
                });
                return false;
            }else{
                return true;
            }
        },
        doPageJump: function (criteria, table) {
            var _this = this;
            this.loadTable(criteria, false, true, function (data) {
                _this.updateTable(data, false);
                table.forceUpdate();
            });
        },
        setCutOffDate: function () {
            var fnQuarter = this.model.get("CurrentQuarter");
            var closingDate = this.model.get("ClosingDate");
            $page.service.setCutOffDate(
                {
                    FnQuarter: fnQuarter,
                    CutoffDate: closingDate
                },
                function (data, textStatus, jqXHR) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Set Successful']
                    });
                },
                function (jqXHR, textStatus, errorThrown) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Set Failure']
                    });
                });
        },
        setStartDate: function () {
            var fnQuarter = this.model.get("CurrentQuarter");
            $page.service.startClosing(
                fnQuarter,
                function (data, textStatus, jqXHR) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Set Successful'],
                        onConfirm: function () {
                            window.location.reload();
                        }
                    });
                },
                function (jqXHR, textStatus, errorThrown) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Set Failure']
                    });
                });
        },
        setClosedDate: function () {
            var fnQuarter = this.model.get("CurrentQuarter");
            $page.service.endClosing(
                fnQuarter,
                function (data, textStatus, jqXHR) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Set Successful'],
                        onConfirm: function () {
                            window.location.reload();
                        }
                    });
                },
                function (jqXHR, textStatus, errorThrown) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Set Failure']
                    });
                });
        },
        runBatch:function(){
            var successFunc = function(){
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Batch Executed'],
                    onConfirm: function () {
                        window.location.reload();
                    }
                });
            };
            var failFunc = function(){

            };
            $page.service.runBatch(successFunc, failFunc);
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));