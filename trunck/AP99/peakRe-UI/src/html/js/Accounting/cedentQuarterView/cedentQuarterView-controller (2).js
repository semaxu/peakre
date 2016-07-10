(function (context) {
    var $page = $pt.getService(context, '$page');
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializeData: function () {
            var urlData = $pt.getUrlData();
            var level = urlData.Level;
            var contCompId = urlData.ContCompId;
            var contractCode = urlData.ContractCode;
            var uwYear = urlData.UwYear;
            this.model = $pt.createModel($page.model.createBaseModel());
            //this.editControlModel = $pt.createModel({adjusting: "", currentFNQuarter: "2016Q1"});
            var _this = this;
            var successFunc = function (data) {
                $helper.lowerKeysOfJSON(data);
                _this.initialInfo(data, false);
            };
            $page.service.load(level, contCompId, successFunc);

            $page.controller.model.mergeCurrentModel({
                level: level,
                contCompId: contCompId,
                contractCode: contractCode,
                uwYear: uwYear
            });

            this.model.addPostChangeListener('currency', function (evt) {
                var model = evt.model;
                var radioValue = evt.new;
                _this.doSearch(_this.model.getCurrentModel().date);
            });

            this.model.addPostChangeListener('date', function (evt) {
                var date = evt.model.get("date");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");


                if (null != date && "" != date && undefined != date) {
                    date = moment(date).format("YYYY-MM-DD HH:mm");

                    if (date > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'Message',
                            disableClose: true,
                            messages: ['The Date must be earlier or equal to today.']
                        });
                        _this.model.set("date", nowDate);
                    }
                }
            });
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        doSearch: function (date) {
            var cache = $.extend({}, this.model.getCurrentModel());
            var level = cache.level;
            var contCompId = cache.contCompId;
            var currency = cache.currency;
            var _this = this;
            var successFunc = function (data) {
                $helper.lowerKeysOfJSON(data);
                _this.initialInfo(data, true);
            };
            if (date) {
                $page.service.search(level, contCompId, currency, moment(date).format("YYYY-MM-DD HH:mm:ss"), successFunc);
            } else {
                $page.service.load(level, contCompId, successFunc);
            }
        },
        exit: function () {
            var cache = $.extend({}, this.model.getCurrentModel());
            var contractCode = cache.contractCode;
            var uwYear = cache.uwYear;
            var urlData = $pt.getUrlData();
            var exitBool = urlData.Exit;
            if (exitBool == 1) {
                window.close();
            } else {
                var url = $pt.getURL("ui.accounting.accountSummary") + "?ContractCode=" + contractCode + "&UwYear=" + uwYear;
                window.location.href = url;
            }
        },
        recalculateForecast: function () {
            var _this = this;
            var successFunc = function (data) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Success'],
                    onConfirm: function () {
                        //_this.doSearch(null);
                        window.location.reload();
                    }
                });
            };
            $page.service.recalculateForecast(this.model.getCurrentModel().level, this.model.getCurrentModel().contCompId, successFunc);
        },
        initialInfo: function (data, updateUI) {
            var cache = $.extend({}, this.model.getCurrentModel());
            var currencies = cache.currencies;
            currencies.push({
                id: "DEFAULT",
                text: "Main Currency：" + data.mainCurrency
            }, {
                id: "USD",
                text: "USD"
            });
            console.log(data);
            $page.controller.model.mergeCurrentModel({
                date: data.date,
                cleanCut: data.cleanCut,
                currentFNQuarter: data.currentFNQuarter,
                forecastTable: data.forecastTable ? data.forecastTable : [],
                estimationTable: data.estimationTable ? data.estimationTable : [],
                reversalTable: data.reversalTable ? data.reversalTable : [],
                actualTable: data.actualTable ? data.actualTable : [],
                tableColumns: data.tableColumns
            });
            if (updateUI) {
                $page.controller.form.forceUpdate();
            }
        },
        showTransactionDetail: function (entryCode, cedentQuarter, status) {
            var url = $pt.getURL('ui.accounting.uwTransactionDetail') + "?contCompId=" + this.model.getCurrentModel().contCompId + "&entryCode=" + entryCode + "&cedentQuarter=" + cedentQuarter + "&status=" + status;
            window.open(url);
        },
        doShowSectionDialog: function (contCompId) {
            var _this = this;
            var successFunc = function (data) {
                var sectionForm = NModalForm.createFormModal("Post Estimation");
                var sectionModel = $pt.createModel();
                var sectionLayout = $pt.createFormLayout(
                    $page.layout.createQuarterLayout(data)
                );

                sectionForm.show({
                    model: sectionModel,
                    layout: sectionLayout,
                    buttons: {
                        reset: false,
                        validate: false,
                        cancel: false,
                        right: [
                            {
                                text: 'Close',
                                style: 'warning',
                                click: function (model) {
                                    sectionForm.hide();
                                }
                            }, {
                                text: 'Confirm',
                                style: 'primary',
                                click: function (model) {
                                    var quarters = model.get("quarterTree");
                                    if (quarters) {
                                        if (quarters.length == 0) {
                                            alert('Please choose quarter');
                                            return;
                                        }
                                        var estimateIds = quarters.toString();
                                        _this.postEstimation(estimateIds);
                                    } else {
                                        alert('Please choose quarter');
                                    }
                                }
                            }
                        ]
                    },
                    draggable: false
                });
            };
            $page.service.loadQuarterNeedPostE(contCompId, successFunc);
        },
        postEstimation: function (estimateIds) {
            var successFunc = function (data) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Success'],
                    onConfirm: function () {
                        window.location.reload();
                    }
                });
            };
            $page.service.postEstimation(this.model.getCurrentModel().contCompId, estimateIds, successFunc);
        },
        saveAdjustmentE: function () {
            $page.controller.model.set("adjusting", "");
            var cache = $.extend({}, this.model.getCurrentModel());
            var estimationTable = cache.estimationTable;
            var adjustStr = "";
            var quarterSeq = [];
            var _this = this;
            var tableColumns = this.model.getCurrentModel().tableColumns;
            tableColumns.forEach(function (tableColumns) {
                if (tableColumns.status == 2) {
                    quarterSeq.push(tableColumns.value);
                }
            });
            console.log(quarterSeq.toString());

            quarterSeq.forEach(function (quarterSeq) {
                var array = [];
                estimationTable.forEach(function (estimationTable) {
                    if (estimationTable.item != '4112' && estimationTable.item != '4116') {
                        array.push(estimationTable.item + ":" + estimationTable[quarterSeq]);
                    }
                });
                console.log(array.toString());
                var quarterStr;
                tableColumns.forEach(function (tableColumns) {
                    if (tableColumns.value == quarterSeq) {
                        quarterStr = tableColumns.text;
                    }
                });
                adjustStr = adjustStr + quarterStr + "@" + array.toString() + "&";
            });
            adjustStr = adjustStr.substring(0, adjustStr.length - 1);
            console.log(adjustStr);

            var successFunc = function (data) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Success'],
                    onConfirm: function () {
                        _this.doSearch(null);
                    }
                });
            };
            $page.service.adjustEstimation(this.model.getCurrentModel().contCompId, adjustStr, successFunc);
        },
        cancelAdjustmentE: function () {
            $page.controller.model.set("adjusting", "");
            this.doSearch(null);
        },
        saveAdjustmentA: function () {
            $page.controller.model.set("adjusting", "");
            var cache = $.extend({}, this.model.getCurrentModel());
            var estimationTable = cache.estimationTable;
            var adjustStr = "";
            var quarterSeq = [];
            var _this = this;
            var tableColumns = this.model.getCurrentModel().tableColumns;
            tableColumns.forEach(function (tableColumns) {
                if (tableColumns.status == 2) {
                    quarterSeq.push(tableColumns.value);
                }
            });
            console.log(quarterSeq.toString());

            quarterSeq.forEach(function (quarterSeq) {
                var array = [];
                estimationTable.forEach(function (estimationTable) {
                    if (estimationTable.item == '4112' || estimationTable.item == '4116') {
                        array.push(estimationTable.item + ":" + estimationTable[quarterSeq]);
                    }
                });
                console.log(array.toString());
                var quarterStr;
                tableColumns.forEach(function (tableColumns) {
                    if (tableColumns.value == quarterSeq) {
                        quarterStr = tableColumns.text;
                    }
                });
                adjustStr = adjustStr + quarterStr + "@" + array.toString() + "&";
            });
            adjustStr = adjustStr.substring(0, adjustStr.length - 1);
            console.log(adjustStr);

            var successFunc = function (data) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Success'],
                    onConfirm: function () {
                        _this.doSearch(null);
                    }
                });
            };
            $page.service.adjustUprDac(this.model.getCurrentModel().contCompId, adjustStr, successFunc);
        },
        cancelAdjustmentA: function () {
            $page.controller.model.set("adjusting", "");
            this.doSearch(null);
        },
        exportExcel: function () {
            var excelParams = {
                sectionId: $page.controller.model.get("contCompId")
            };
            $pt.generateFile(12, excelParams);
        }
    }));

    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));