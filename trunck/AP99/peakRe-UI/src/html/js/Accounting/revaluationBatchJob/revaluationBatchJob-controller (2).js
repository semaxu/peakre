(function (context) {
    var $page = $pt.getService(context, '$page');

    var initial = {
        //initialize: function () {
        //},
        initializeData: function () {
            this.model = $pt.createModel($page.model);
            this.codes = $pt.createModel($page.codes);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        }
    };
    var restService = {

        //doValid: function () {
        //    var _this = this;
        //   this.valid();
        //},
        doExecute: function () {
            var executeFlag = false;
            var afterExecute = function (data, textStatus, jqXHR) {
                NConfirm.getConfirmModal().show({
                    title:"Confirm Dialog",
                    disableClose: true,
                    messages: 'Successful'
                });
            };
            this.execute(null, false, false, afterExecute);

        },
        execute: function (criteria, quiet, async, done, fail) {
            $page.service.execute(criteria, quiet, async, done, fail);
        },
        doValid: function (updateData, quiet, async, done, fail) {
            var executeFlag;
            var _this = this;
            var toValidClosingAndBatch = function (data, textStatus, jqXHR) {

                var nowDateYear = moment(new Date()).format("YYYY");
                var nowDateMonth = moment(new Date()).format("MM");
                var FNYearAndQuarter = "";
                if (moment(201601).format("MM") <= nowDateMonth <= moment(201603).format("MM")) {
                    FNYearAndQuarter = nowDateYear + "Q1";
                } else if (moment(201604).format("MM") <= nowDateMonth <= moment(201606).format("MM")) {
                    FNYearAndQuarter = nowDateYear + "Q2";
                } else if (moment(201607).format("MM") <= nowDateMonth <= moment(201609).format("MM")) {
                    FNYearAndQuarter = nowDateYear + "Q3";
                } else if (moment(201610).format("MM") <= nowDateMonth <= moment(201612).format("MM")) {
                    FNYearAndQuarter = nowDateYear + "Q4";
                }
                if (data.InClosingPeriod == 'true') {
                    executeFlag = data.ExecutedFlag;
                    var currencyCodes = data.CurrencyCodes;
                    if (data.CurrencyCodes != 'true') {
                        NConfirm.getConfirmModal().show({
                            title:"Confirm Dialog",
                            disableClose: true,
                            messages: currencyCodes+ ' month end exchange rate does not exist,please configuration'
                        });
                    }else if (data.ExecutedFlag == 'true') {
                       var confirmed1 = false;
                       NConfirm.getConfirmModal().show({title : 'Message',messages: 'The revaluation batch job has executed, are you sure to continue?',
                               onConfirm: function() {
                                   _this.doExecute();
                                   confirmed1 = true;
                               },
                           afterClose: function (result) {
                               if (confirmed1)
                                   NConfirm.getConfirmModal().show({
                                       title:"Confirm Dialog",
                                       disableClose: true,
                                       messages: 'Successful'
                                   });
                               }
                           });
                    } else {
                        var confirmed2 = false;

                        NConfirm.getConfirmModal().show({title : 'Message',messages: 'System is going to do the revaluation batch job for  '+FNYearAndQuarter,
                            onConfirm: function() {
                                _this.doExecute();
                                confirmed2 = true;
                            },
                            afterClose: function (result) {
                                if (confirmed2)
                                    NConfirm.getConfirmModal().show({
                                        title:"Confirm Dialog",
                                        disableClose: true,
                                        messages: 'Successful'
                                    });
                            }
                        });
                    }
                } else {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Not in closing period']
                    });
                }

            }.bind(this);
            this.validBefore(updateData, false, false, toValidClosingAndBatch);

        },
        validBefore: function (updateData, quiet, async, done, fail) {
            $page.service.validBefore(updateData, quiet, async, done, fail);
        },
        doView: function () {
            //var afterView = function (data, textStatus, jqXHR) {
            //
            window.location.href = "revaluationView.html";
            //}.bind(this);
            //this.view(null, false, false, afterView);
        },
        view: function (criteria, quiet, async, done, fail) {
            $page.service.view(criteria, quiet, async, done, fail);
        },
        doSearch: function () {
            var _this = this;
            var searchCondition = this.model.getCurrentModel();
            var afterSearch = function (data, textStatus, jqXHR) {
                _this.model.mergeCurrentModel({
                    queryResult: data.Rows !== undefined ? data.Rows : [],
                    cachedCriteria: {
                        pageIndex: data.PageIndex,
                        pageCount: data.PageCount,
                        countPerPage: data.CountPerPage
                    }
                });

                if (_this.form) {
                    this.form.forceUpdate();
                }
            }.bind(this);
            console.log("dsf");
            console.log(searchCondition);
            console.log(searchCondition.financialYear);
            if (searchCondition == undefined || searchCondition.financialYear == undefined || searchCondition.financialYear == null) {
                $page.controller.model.set("financialYear",null);
            }
            if (searchCondition == undefined || searchCondition.financialQuarter == undefined || searchCondition.financialQuarter == null) {
                $page.controller.model.set("financialQuarter", 0);
            }
            if (searchCondition == undefined || searchCondition.status == undefined || searchCondition.status == null) {
                $page.controller.model.set("status", 0);
            }
            $page.service.search(searchCondition, false, false, afterSearch);
        }
    };
    var forExcel = {
        //exportExcel: function (rowModel) {
        //    var excelParams = {
        //        refId: rowModel.RevaluationId
        //    };
        //    var afterExport = function (data, textStatus, jqXHR) {
        //        var exportUrl = "../fileUpload/"+data.filePath;
        //        window.open(exportUrl);
        //    }.bind(this);
        //    $page.service.exportService(excelParams, false, false, afterExport);
        //},

        exportExcel: function (rowModel) {
            var excelParams = {
                refId: rowModel.RevaluationId
            };
            $pt.generateFile(11,excelParams);

        }
    };


    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, initial, restService, forExcel));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));