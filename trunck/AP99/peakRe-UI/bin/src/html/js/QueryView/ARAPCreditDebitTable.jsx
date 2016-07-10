(function (context, $, $pt) {
    //var tableViewModel = $pt.createModel({'contractId-Contract1': true});
    var ARAPCreditDebitTable = React.createClass($pt.defineCellComponent({
        propTypes: {
            model: React.PropTypes.object,
            layout: React.PropTypes.object
        },
        getInitialState: function() {
            return {
                viewModel: $pt.createModel({})
            }
        },
        render: function() {
            var layout = $pt.createCellLayout(this.getDataId(), this.getTableLayout());
            return <NTable model={this.getDisplayModel()} layout={layout} />
        },
        getDisplayModel: function() {
            var showAll = this.getComponentOption('showChecked');
            if (showAll) {
                return this.getModel();
            } else {
                var data = this.getModel().get(this.getDataId());

                var model = {};
                var shownParents = {};
                model[this.getDataId()] = data == null ? [] : data.filter(function(item) {
                    if (item.ContractSelect) {
                        shownParents[item.ParentFeeIdStr + ''] = true;
                        return true;
                    }else if (item.ParentFeeIdStr != '') {
                        return shownParents[item.ParentFeeIdStr + ''];
                    }
                    return false;
                });

                var validator = this.getModel().getValidator();
                if (validator) {
                    var myValidator = {};
                    myValidator[this.getDataId()] = validator.getConfig()[this.getDataId()];
                    model = $pt.createModel(model, $pt.createModelValidator(myValidator));
                } else {
                    model = $pt.createModel(model);
                }

                model.useBaseAsCurrent();
                return model;
            }
        },
        getTableLayout: function() {
            var viewModel = this.state.viewModel;
            NTable.registerInlineEditor('numberForPayment', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    format: $helper.formatNumber(2),
                    model: viewModel ,
                    useFormModel: true,
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');//row.get('FeeIdStr')
                        }, depends: 'ParentFeeIdStr'
                    },
                    enabled: {
                        when: function (row) {
                            return this.getInnerModel().get("ParentFeeIdStr-" + row.get('ParentFeeIdStr')) && $page.viewEnable == "";
                        },
                        depends: {id: /ParentFeeIdStr-.*/, on: 'inner'}
                    }
                }
            });

            NTable.registerInlineEditor('inlineExchangeRate', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    model: viewModel ,
                    useFormModel: true,
                    format: $helper.formatNumber(6),
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');
                        }
                    },
                    enabled: false
                }
            });

            NTable.registerInlineEditor('inlineAmount', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    model: viewModel ,
                    useFormModel: true,
                    format: $helper.formatNumber(2),
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');
                        }
                    },
                    enabled: false
                }
            });

            NTable.registerInlineEditor('readonlyText', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    model: viewModel ,
                    useFormModel: true,
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');
                        }, depends: 'ParentFeeIdStr'
                    },
                    enabled: {
                        when: function () {
                            return false;
                        },
                        depends: {id: /ParentFeeIdStr-.*/, on: 'inner'}
                    }
                }
            });

            NTable.registerInlineEditor('checkForPayment', {
                comp: {
                    type: {type: $pt.ComponentConstants.Check, label: false},
                    visible : {
                        when: function(row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr');
                        },
                        depends: 'ParentFeeIdStr'
                    }
                }
            });
            NTable.registerInlineEditor('checkForType', {
                comp: {
                    type: {
                        type: $pt.ComponentConstants.Check, label: false
                    },
                    visible : {
                        when: function(row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr') ;
                        }
                    },
                    enabled: {
                        when: function () {
                            return $page.viewEnable == "";
                        }
                    }
                }
            });
            NTable.registerInlineEditor('fullySettle', {
                comp: {
                    type: {
                        type: $pt.ComponentConstants.Check, label: false
                    },
                    visible : {
                        when: function(row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr') ;
                        }
                    },
                    enabled: {
                        when: function () {
                            return $page.viewEnable == "";
                        }
                    }
                }
            });

            var _this = this;
            var parentFeeIdStr = "";
            return {
                comp: {
                    type: $pt.ComponentConstants.Table,
                    rowListener: [{
                        id: 'ContractSelect',
                        listener: function(evt)  {
                            viewModel.set('ParentFeeIdStr-' + evt.model.get('ParentFeeIdStr'), evt.new);
                        }
                    },{
                        id:'MarkOffAmount',
                        listener : function(evt) {
                            if (!_this.state.calculator) {
                                _this.state.calculator = {};
                            }
                            var rowId = evt.model.get('FeeIdStr');// get row id
                            if (_this.state.calculator[rowId]) {
                                clearTimeout(_this.state.calculator[rowId]);
                            }
                            _this.state.calculator[rowId] = setTimeout(function() {
                                // do calc
                                var markOffAmount = evt.model.get('MarkOffAmount');
                                // add check
                                var amount = _this.getModel().get("Amount");
                                if (amount == undefined || amount == "" || amount == 0 || amount == "0") {
                                    _this.getModel().set("ExchangeRate", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Amount in Collection Currency must not be empty']
                                    });
                                    return;
                                }
                                var convertedAmount = _this.getModel().get("ConvertedAmount");
                                if (convertedAmount == undefined || convertedAmount == "" || convertedAmount == 0 || convertedAmount == "0") {
                                    _this.getModel().set("ExchangeRate", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Converted Amount must not be empty']
                                    });
                                    return;
                                }
                                if (markOffAmount == undefined || markOffAmount == "") {
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("GainLoss", "");
                                    return;
                                }
                                var osBalance = evt.model.get('OsBalance');
                                markOffAmount = parseFloat(markOffAmount);
                                osBalance = parseFloat(osBalance);
                                if ((osBalance > 0 && (markOffAmount <= 0 || markOffAmount > osBalance)) ||
                                    (osBalance < 0 && (markOffAmount >= 0 || markOffAmount < osBalance))) {
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("GainLoss", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Mark-off Amount must not be greater than or less than Outstanding Balance']
                                    });
                                    return;
                                }
                                var exchangeRate = _this.getModel().get("ExchangeRate");
                                if (markOffAmount != "" && exchangeRate != "") {
                                    var settleAmount = parseFloat(markOffAmount) / parseFloat(exchangeRate);
                                    evt.model.set("SettleAmount", settleAmount.toFixed(2));
                                    evt.model.set("ExchangeRate", $helper.formatNumberForLabel(exchangeRate, 6));

                                    var rowParentFeeIdStr = evt.model.get('ParentFeeIdStr');
                                    if ("" != parentFeeIdStr && parentFeeIdStr == rowParentFeeIdStr) {
                                        var settleDiff = parseFloat(osBalance) - parseFloat(markOffAmount);
                                        evt.model.set("SettleDiff", settleDiff.toFixed(2));
                                    }
                                    else {
                                        evt.model.set("SettleDiff", "");
                                    }
                                    var collectionDate = $page.controller.model.get("CollectionDate");
                                    var postData = {
                                        FeeId: evt.model.get("FeeIdStr"),
                                        MarkOffAmount: parseFloat(markOffAmount),
                                        OriginalCurrency:evt.model.get("Currency"),
                                        SettleAmount: settleAmount,
                                        SettleCurrency: evt.model.get("SettleCurrency"),
                                        SettleDate:collectionDate
                                    };
                                    var returnVal = $page.controller.calGainLossInUSD(postData);
                                    if (undefined != returnVal && null != returnVal && "" != returnVal && !isNaN(returnVal)){
                                        evt.model.set("GainLoss", returnVal.toFixed(2));
                                    }
                                }

                                delete _this.state.calculator[rowId];
                            }, 300);
                        }
                    },{
                        id:'FullySettle',
                        listener : function(evt) {
                            var contractSelect = evt.model.get('ContractSelect');
                            if (!contractSelect){
                                return;
                            }
                            var dataModel = _this.getModel();console.info(dataModel);
                            var fullySettle = evt.model.get('FullySettle');
                            var entryItems, osBalance, markOffAmount, parentFeeIdStr2;
                            parentFeeIdStr2 = evt.model.get('ParentFeeIdStr');
                            if (fullySettle) {
                                parentFeeIdStr = parentFeeIdStr2;
                                entryItems = dataModel.get("EntryItems");
                                if (undefined != entryItems && null != entryItems && entryItems.length > 0) {
                                    for (var j1=0; j1<entryItems.length;j1++){
                                        if (entryItems[j1].ParentFeeIdStr == parentFeeIdStr2 && entryItems[j1].FeeId != parentFeeIdStr2){
                                            osBalance = entryItems[j1].OsBalance;
                                            markOffAmount = entryItems[j1].MarkOffAmount;
                                            if (undefined != markOffAmount && !isNaN(markOffAmount) && null != markOffAmount && "" != markOffAmount){
                                                var settleDiff = parseFloat(osBalance) - parseFloat(markOffAmount);
                                                entryItems[j1].SettleDiff = settleDiff.toFixed(2);
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                entryItems = dataModel.get("EntryItems");
                                if (undefined != entryItems && null != entryItems && entryItems.length > 0) {
                                    for (var m1 = 0; m1 < entryItems.length; m1++) {
                                        if (entryItems[m1].ParentFeeIdStr == parentFeeIdStr2 && entryItems[m1].FeeId != parentFeeIdStr2) {
                                            entryItems[m1].SettleDiff = "";
                                        }
                                    }
                                }
                                parentFeeIdStr = "";
                            }
                            $page.controller.form.forceUpdate();
                        }
                    }],
                    searchable: false,
                    sortable: false,
                    columns: [
                        {title:"",data:"ContractSelect",inline:"checkForType",width: "2%"},
                        {title: "Contract ID", data: "ContractCode",width: "10%"},
                        {title: "Business Type", data: "BizTransType",codes:$page.codes.BusinessTransType,width: "5%"},
                        {title: "Business Number", data: "BizTransNo",width: "5%"},
                        {title: "Entry Item", data: "Description",width: "5%"},
                        {title: "Amount in OC", data: "AmountOC",width: "5%",
                            render: function (row) {
                                if (undefined != row.AmountOC && null != row.AmountOC && "" != row.AmountOC){
                                    return row.AmountOC.toFixed(2);
                                }
                                return "";
                            }
                        },
                        {title: "OC", data: "Currency",codes : $page.codes.Currency,width: "5%"},
                        {title: "Due Date", data: "DueDate",width: "5%",
                            render: function (row) {
                                var dueDate = row.DueDate;
                                if ("" != dueDate && undefined != dueDate) {
                                    dueDate = moment(row.DueDate,"YYYY-MM-DD").format("DD/MM/YYYY");
                                }
                                else {
                                    dueDate = "";
                                }
                                return dueDate;
                            }
                        },
                        {title: "Outstanding Balance", data: "OsBalance",width: "5%",
                            render: function (row) {
                                if (undefined != row.OsBalance && null != row.OsBalance && "" != row.OsBalance){
                                    return row.OsBalance.toFixed(2);
                                }
                                return "";
                            }
                        },
                        {title: "Mark-off Amount", data: "MarkOffAmount",inline : "numberForPayment",width: "8%"},
                        {title: "Collection Currency", data: "SettleCurrency",inline:"readonlyText",width: "5%"},
                        {title: "Assigned Amount in Collection Currency", data: "AssignedAmount",width: "13%",inline:"inlineAmount"},
                        {title: "Collection Amount", data: "SettleAmount",inline:"inlineAmount",width: "5%"},
                        {title: "Exchange Rate", data: "ExchangeRate",inline:"inlineExchangeRate",width: "5%"},
                        {title: "Fully Settle", data: "FullySettle",inline:"fullySettle",width: "2%"},
                        {title:"Settlement Difference(OC)",data:"SettleDiff",inline:"inlineAmount",width: "10%"},
                        {title:"E/X Gain or Loss(USD)",data:"GainLoss",inline:"inlineAmount",width: "5%"}
                    ]
                },
                css: {
                    comp: 'inline-editor'
                }
            };
        }
    }));
    context.ARAPCreditDebitTable = ARAPCreditDebitTable;
    NFormCell.registerComponentRenderer('ARAPCreditDebitTable', function(model, layout, direction) {
        return <ARAPCreditDebitTable model={model} layout={layout} direction={direction} />;
    });
    $pt.ComponentConstants.ARAPCreditDebitTable = {type: 'ARAPCreditDebitTable', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));(function (context, $, $pt) {
    //var tableViewModel = $pt.createModel({'contractId-Contract1': true});
    var ARAPCreditDebitTable = React.createClass($pt.defineCellComponent({
        propTypes: {
            model: React.PropTypes.object,
            layout: React.PropTypes.object
        },
        getInitialState: function() {
            return {
                viewModel: $pt.createModel({})
            }
        },
        render: function() {
            var layout = $pt.createCellLayout(this.getDataId(), this.getTableLayout());
            return <NTable model={this.getDisplayModel()} layout={layout} />
        },
        getDisplayModel: function() {
            var showAll = this.getComponentOption('showChecked');
            if (showAll) {
                return this.getModel();
            } else {
                var data = this.getModel().get(this.getDataId());

                var model = {};
                var shownParents = {};
                model[this.getDataId()] = data == null ? [] : data.filter(function(item) {
                    if (item.ContractSelect) {
                        shownParents[item.ParentFeeIdStr + ''] = true;
                        return true;
                    }else if (item.ParentFeeIdStr != '') {
                        return shownParents[item.ParentFeeIdStr + ''];
                    }
                    return false;
                });

                var validator = this.getModel().getValidator();
                if (validator) {
                    var myValidator = {};
                    myValidator[this.getDataId()] = validator.getConfig()[this.getDataId()];
                    model = $pt.createModel(model, $pt.createModelValidator(myValidator));
                } else {
                    model = $pt.createModel(model);
                }

                model.useBaseAsCurrent();
                return model;
            }
        },
        getTableLayout: function() {
            var viewModel = this.state.viewModel;
            NTable.registerInlineEditor('numberForPayment', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    format: $helper.formatNumber(2),
                    model: viewModel ,
                    useFormModel: true,
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');//row.get('FeeIdStr')
                        }, depends: 'ParentFeeIdStr'
                    },
                    enabled: {
                        when: function (row) {
                            return this.getInnerModel().get("ParentFeeIdStr-" + row.get('ParentFeeIdStr')) && $page.viewEnable == "";
                        },
                        depends: {id: /ParentFeeIdStr-.*/, on: 'inner'}
                    }
                }
            });

            NTable.registerInlineEditor('inlineExchangeRate', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    model: viewModel ,
                    useFormModel: true,
                    format: $helper.formatNumber(6),
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');
                        }
                    },
                    enabled: false
                }
            });

            NTable.registerInlineEditor('inlineAmount', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    model: viewModel ,
                    useFormModel: true,
                    format: $helper.formatNumber(2),
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');
                        }
                    },
                    enabled: false
                }
            });

            NTable.registerInlineEditor('readonlyText', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    model: viewModel ,
                    useFormModel: true,
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');
                        }, depends: 'ParentFeeIdStr'
                    },
                    enabled: {
                        when: function () {
                            return false;
                        },
                        depends: {id: /ParentFeeIdStr-.*/, on: 'inner'}
                    }
                }
            });

            NTable.registerInlineEditor('checkForPayment', {
                comp: {
                    type: {type: $pt.ComponentConstants.Check, label: false},
                    visible : {
                        when: function(row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr');
                        },
                        depends: 'ParentFeeIdStr'
                    }
                }
            });
            NTable.registerInlineEditor('checkForType', {
                comp: {
                    type: {
                        type: $pt.ComponentConstants.Check, label: false
                    },
                    visible : {
                        when: function(row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr') ;
                        }
                    },
                    enabled: {
                        when: function () {
                            return $page.viewEnable == "";
                        }
                    }
                }
            });
            NTable.registerInlineEditor('fullySettle', {
                comp: {
                    type: {
                        type: $pt.ComponentConstants.Check, label: false
                    },
                    visible : {
                        when: function(row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr') ;
                        }
                    },
                    enabled: {
                        when: function () {
                            return $page.viewEnable == "";
                        }
                    }
                }
            });

            var _this = this;
            var parentFeeIdStr = "";
            return {
                comp: {
                    type: $pt.ComponentConstants.Table,
                    rowListener: [{
                        id: 'ContractSelect',
                        listener: function(evt)  {
                            viewModel.set('ParentFeeIdStr-' + evt.model.get('ParentFeeIdStr'), evt.new);
                        }
                    },{
                        id:'MarkOffAmount',
                        listener : function(evt) {
                            if (!_this.state.calculator) {
                                _this.state.calculator = {};
                            }
                            var rowId = evt.model.get('FeeIdStr');// get row id
                            if (_this.state.calculator[rowId]) {
                                clearTimeout(_this.state.calculator[rowId]);
                            }
                            _this.state.calculator[rowId] = setTimeout(function() {
                                // do calc
                                var markOffAmount = evt.model.get('MarkOffAmount');
                                // add check
                                var amount = _this.getModel().get("Amount");
                                if (amount == undefined || amount == "" || amount == 0 || amount == "0") {
                                    _this.getModel().set("ExchangeRate", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Amount in Collection Currency must not be empty']
                                    });
                                    return;
                                }
                                var convertedAmount = _this.getModel().get("ConvertedAmount");
                                if (convertedAmount == undefined || convertedAmount == "" || convertedAmount == 0 || convertedAmount == "0") {
                                    _this.getModel().set("ExchangeRate", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Converted Amount must not be empty']
                                    });
                                    return;
                                }
                                if (markOffAmount == undefined || markOffAmount == "") {
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("GainLoss", "");
                                    return;
                                }
                                var osBalance = evt.model.get('OsBalance');
                                markOffAmount = parseFloat(markOffAmount);
                                osBalance = parseFloat(osBalance);
                                if ((osBalance > 0 && (markOffAmount <= 0 || markOffAmount > osBalance)) ||
                                    (osBalance < 0 && (markOffAmount >= 0 || markOffAmount < osBalance))) {
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("GainLoss", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Mark-off Amount must not be greater than or less than Outstanding Balance']
                                    });
                                    return;
                                }
                                var exchangeRate = _this.getModel().get("ExchangeRate");
                                if (markOffAmount != "" && exchangeRate != "") {
                                    var settleAmount = parseFloat(markOffAmount) / parseFloat(exchangeRate);
                                    evt.model.set("SettleAmount", settleAmount.toFixed(2));
                                    evt.model.set("ExchangeRate", $helper.formatNumberForLabel(exchangeRate, 6));

                                    var rowParentFeeIdStr = evt.model.get('ParentFeeIdStr');
                                    if ("" != parentFeeIdStr && parentFeeIdStr == rowParentFeeIdStr) {
                                        var settleDiff = parseFloat(osBalance) - parseFloat(markOffAmount);
                                        evt.model.set("SettleDiff", settleDiff.toFixed(2));
                                    }
                                    else {
                                        evt.model.set("SettleDiff", "");
                                    }
                                    var collectionDate = $page.controller.model.get("CollectionDate");
                                    var postData = {
                                        FeeId: evt.model.get("FeeIdStr"),
                                        MarkOffAmount: parseFloat(markOffAmount),
                                        OriginalCurrency:evt.model.get("Currency"),
                                        SettleAmount: settleAmount,
                                        SettleCurrency: evt.model.get("SettleCurrency"),
                                        SettleDate:collectionDate
                                    };
                                    var returnVal = $page.controller.calGainLossInUSD(postData);
                                    if (undefined != returnVal && null != returnVal && "" != returnVal && !isNaN(returnVal)){
                                        evt.model.set("GainLoss", returnVal.toFixed(2));
                                    }
                                }

                                delete _this.state.calculator[rowId];
                            }, 300);
                        }
                    },{
                        id:'FullySettle',
                        listener : function(evt) {
                            var contractSelect = evt.model.get('ContractSelect');
                            if (!contractSelect){
                                return;
                            }
                            var dataModel = _this.getModel();console.info(dataModel);
                            var fullySettle = evt.model.get('FullySettle');
                            var entryItems, osBalance, markOffAmount, parentFeeIdStr2;
                            parentFeeIdStr2 = evt.model.get('ParentFeeIdStr');
                            if (fullySettle) {
                                parentFeeIdStr = parentFeeIdStr2;
                                entryItems = dataModel.get("EntryItems");
                                if (undefined != entryItems && null != entryItems && entryItems.length > 0) {
                                    for (var j1=0; j1<entryItems.length;j1++){
                                        if (entryItems[j1].ParentFeeIdStr == parentFeeIdStr2 && entryItems[j1].FeeId != parentFeeIdStr2){
                                            osBalance = entryItems[j1].OsBalance;
                                            markOffAmount = entryItems[j1].MarkOffAmount;
                                            if (undefined != markOffAmount && !isNaN(markOffAmount) && null != markOffAmount && "" != markOffAmount){
                                                var settleDiff = parseFloat(osBalance) - parseFloat(markOffAmount);
                                                entryItems[j1].SettleDiff = settleDiff.toFixed(2);
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                entryItems = dataModel.get("EntryItems");
                                if (undefined != entryItems && null != entryItems && entryItems.length > 0) {
                                    for (var m1 = 0; m1 < entryItems.length; m1++) {
                                        if (entryItems[m1].ParentFeeIdStr == parentFeeIdStr2 && entryItems[m1].FeeId != parentFeeIdStr2) {
                                            entryItems[m1].SettleDiff = "";
                                        }
                                    }
                                }
                                parentFeeIdStr = "";
                            }
                            $page.controller.form.forceUpdate();
                        }
                    }],
                    searchable: false,
                    sortable: false,
                    columns: [
                        {title:"",data:"ContractSelect",inline:"checkForType",width: "2%"},
                        {title: "Contract ID", data: "ContractCode",width: "10%"},
                        {title: "Business Type", data: "BizTransType",codes:$page.codes.BusinessTransType,width: "5%"},
                        {title: "Business Number", data: "BizTransNo",width: "5%"},
                        {title: "Entry Item", data: "Description",width: "5%"},
                        {title: "Amount in OC", data: "AmountOC",width: "5%",
                            render: function (row) {
                                if (undefined != row.AmountOC && null != row.AmountOC && "" != row.AmountOC){
                                    return row.AmountOC.toFixed(2);
                                }
                                return "";
                            }
                        },
                        {title: "OC", data: "Currency",codes : $page.codes.Currency,width: "5%"},
                        {title: "Due Date", data: "DueDate",width: "5%",
                            render: function (row) {
                                var dueDate = row.DueDate;
                                if ("" != dueDate && undefined != dueDate) {
                                    dueDate = moment(row.DueDate,"YYYY-MM-DD").format("DD/MM/YYYY");
                                }
                                else {
                                    dueDate = "";
                                }
                                return dueDate;
                            }
                        },
                        {title: "Outstanding Balance", data: "OsBalance",width: "5%",
                            render: function (row) {
                                if (undefined != row.OsBalance && null != row.OsBalance && "" != row.OsBalance){
                                    return row.OsBalance.toFixed(2);
                                }
                                return "";
                            }
                        },
                        {title: "Mark-off Amount", data: "MarkOffAmount",inline : "numberForPayment",width: "8%"},
                        {title: "Collection Currency", data: "SettleCurrency",inline:"readonlyText",width: "5%"},
                        {title: "Assigned Amount in Collection Currency", data: "AssignedAmount",width: "13%",inline:"inlineAmount"},
                        {title: "Collection Amount", data: "SettleAmount",inline:"inlineAmount",width: "5%"},
                        {title: "Exchange Rate", data: "ExchangeRate",inline:"inlineExchangeRate",width: "5%"},
                        {title: "Fully Settle", data: "FullySettle",inline:"fullySettle",width: "2%"},
                        {title:"Settlement Difference(OC)",data:"SettleDiff",inline:"inlineAmount",width: "10%"},
                        {title:"E/X Gain or Loss(USD)",data:"GainLoss",inline:"inlineAmount",width: "5%"}
                    ]
                },
                css: {
                    comp: 'inline-editor'
                }
            };
        }
    }));
    context.ARAPCreditDebitTable = ARAPCreditDebitTable;
    NFormCell.registerComponentRenderer('ARAPCreditDebitTable', function(model, layout, direction) {
        return <ARAPCreditDebitTable model={model} layout={layout} direction={direction} />;
    });
    $pt.ComponentConstants.ARAPCreditDebitTable = {type: 'ARAPCreditDebitTable', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));(function (context, $, $pt) {
    //var tableViewModel = $pt.createModel({'contractId-Contract1': true});
    var ARAPCreditDebitTable = React.createClass($pt.defineCellComponent({
        propTypes: {
            model: React.PropTypes.object,
            layout: React.PropTypes.object
        },
        getInitialState: function() {
            return {
                viewModel: $pt.createModel({})
            }
        },
        render: function() {
            var layout = $pt.createCellLayout(this.getDataId(), this.getTableLayout());
            return <NTable model={this.getDisplayModel()} layout={layout} />
        },
        getDisplayModel: function() {
            var showAll = this.getComponentOption('showChecked');
            if (showAll) {
                return this.getModel();
            } else {
                var data = this.getModel().get(this.getDataId());

                var model = {};
                var shownParents = {};
                model[this.getDataId()] = data == null ? [] : data.filter(function(item) {
                    if (item.ContractSelect) {
                        shownParents[item.ParentFeeIdStr + ''] = true;
                        return true;
                    }else if (item.ParentFeeIdStr != '') {
                        return shownParents[item.ParentFeeIdStr + ''];
                    }
                    return false;
                });

                var validator = this.getModel().getValidator();
                if (validator) {
                    var myValidator = {};
                    myValidator[this.getDataId()] = validator.getConfig()[this.getDataId()];
                    model = $pt.createModel(model, $pt.createModelValidator(myValidator));
                } else {
                    model = $pt.createModel(model);
                }

                model.useBaseAsCurrent();
                return model;
            }
        },
        getTableLayout: function() {
            var viewModel = this.state.viewModel;
            NTable.registerInlineEditor('numberForPayment', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    format: $helper.formatNumber(2),
                    model: viewModel ,
                    useFormModel: true,
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');//row.get('FeeIdStr')
                        }, depends: 'ParentFeeIdStr'
                    },
                    enabled: {
                        when: function (row) {
                            return this.getInnerModel().get("ParentFeeIdStr-" + row.get('ParentFeeIdStr')) && $page.viewEnable == "";
                        },
                        depends: {id: /ParentFeeIdStr-.*/, on: 'inner'}
                    }
                }
            });

            NTable.registerInlineEditor('inlineExchangeRate', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    model: viewModel ,
                    useFormModel: true,
                    format: $helper.formatNumber(6),
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');
                        }
                    },
                    enabled: false
                }
            });

            NTable.registerInlineEditor('inlineAmount', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    model: viewModel ,
                    useFormModel: true,
                    format: $helper.formatNumber(2),
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');
                        }
                    },
                    enabled: false
                }
            });

            NTable.registerInlineEditor('readonlyText', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    model: viewModel ,
                    useFormModel: true,
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') != row.get('FeeIdStr');
                        }, depends: 'ParentFeeIdStr'
                    },
                    enabled: {
                        when: function () {
                            return false;
                        },
                        depends: {id: /ParentFeeIdStr-.*/, on: 'inner'}
                    }
                }
            });

            NTable.registerInlineEditor('checkForPayment', {
                comp: {
                    type: {type: $pt.ComponentConstants.Check, label: false},
                    visible : {
                        when: function(row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr');
                        },
                        depends: 'ParentFeeIdStr'
                    }
                }
            });
            NTable.registerInlineEditor('checkForType', {
                comp: {
                    type: {
                        type: $pt.ComponentConstants.Check, label: false
                    },
                    visible : {
                        when: function(row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr') ;
                        }
                    },
                    enabled: {
                        when: function () {
                            return $page.viewEnable == "";
                        }
                    }
                }
            });
            NTable.registerInlineEditor('fullySettle', {
                comp: {
                    type: {
                        type: $pt.ComponentConstants.Check, label: false
                    },
                    visible : {
                        when: function(row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr') ;
                        }
                    },
                    enabled: {
                        when: function () {
                            return $page.viewEnable == "";
                        }
                    }
                }
            });

            var _this = this;
            var parentFeeIdStr = "";
            return {
                comp: {
                    type: $pt.ComponentConstants.Table,
                    rowListener: [{
                        id: 'ContractSelect',
                        listener: function(evt)  {
                            viewModel.set('ParentFeeIdStr-' + evt.model.get('ParentFeeIdStr'), evt.new);
                        }
                    },{
                        id:'MarkOffAmount',
                        listener : function(evt) {
                            if (!_this.state.calculator) {
                                _this.state.calculator = {};
                            }
                            var rowId = evt.model.get('FeeIdStr');// get row id
                            if (_this.state.calculator[rowId]) {
                                clearTimeout(_this.state.calculator[rowId]);
                            }
                            _this.state.calculator[rowId] = setTimeout(function() {
                                // do calc
                                var markOffAmount = evt.model.get('MarkOffAmount');
                                // add check
                                var amount = _this.getModel().get("Amount");
                                if (amount == undefined || amount == "" || amount == 0 || amount == "0") {
                                    _this.getModel().set("ExchangeRate", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Amount in Collection Currency must not be empty']
                                    });
                                    return;
                                }
                                var convertedAmount = _this.getModel().get("ConvertedAmount");
                                if (convertedAmount == undefined || convertedAmount == "" || convertedAmount == 0 || convertedAmount == "0") {
                                    _this.getModel().set("ExchangeRate", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Converted Amount must not be empty']
                                    });
                                    return;
                                }
                                if (markOffAmount == undefined || markOffAmount == "") {
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("GainLoss", "");
                                    return;
                                }
                                var osBalance = evt.model.get('OsBalance');
                                markOffAmount = parseFloat(markOffAmount);
                                osBalance = parseFloat(osBalance);
                                if ((osBalance > 0 && (markOffAmount <= 0 || markOffAmount > osBalance)) ||
                                    (osBalance < 0 && (markOffAmount >= 0 || markOffAmount < osBalance))) {
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("GainLoss", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Mark-off Amount must not be greater than or less than Outstanding Balance']
                                    });
                                    return;
                                }
                                var exchangeRate = _this.getModel().get("ExchangeRate");
                                if (markOffAmount != "" && exchangeRate != "") {
                                    var settleAmount = parseFloat(markOffAmount) / parseFloat(exchangeRate);
                                    evt.model.set("SettleAmount", settleAmount.toFixed(2));
                                    evt.model.set("ExchangeRate", $helper.formatNumberForLabel(exchangeRate, 6));

                                    var rowParentFeeIdStr = evt.model.get('ParentFeeIdStr');
                                    if ("" != parentFeeIdStr && parentFeeIdStr == rowParentFeeIdStr) {
                                        var settleDiff = parseFloat(osBalance) - parseFloat(markOffAmount);
                                        evt.model.set("SettleDiff", settleDiff.toFixed(2));
                                    }
                                    else {
                                        evt.model.set("SettleDiff", "");
                                    }
                                    var collectionDate = $page.controller.model.get("CollectionDate");
                                    var postData = {
                                        FeeId: evt.model.get("FeeIdStr"),
                                        MarkOffAmount: parseFloat(markOffAmount),
                                        OriginalCurrency:evt.model.get("Currency"),
                                        SettleAmount: settleAmount,
                                        SettleCurrency: evt.model.get("SettleCurrency"),
                                        SettleDate:collectionDate
                                    };
                                    var returnVal = $page.controller.calGainLossInUSD(postData);
                                    if (undefined != returnVal && null != returnVal && "" != returnVal && !isNaN(returnVal)){
                                        evt.model.set("GainLoss", returnVal.toFixed(2));
                                    }
                                }

                                delete _this.state.calculator[rowId];
                            }, 300);
                        }
                    },{
                        id:'FullySettle',
                        listener : function(evt) {
                            var contractSelect = evt.model.get('ContractSelect');
                            if (!contractSelect){
                                return;
                            }
                            var dataModel = _this.getModel();console.info(dataModel);
                            var fullySettle = evt.model.get('FullySettle');
                            var entryItems, osBalance, markOffAmount, parentFeeIdStr2;
                            parentFeeIdStr2 = evt.model.get('ParentFeeIdStr');
                            if (fullySettle) {
                                parentFeeIdStr = parentFeeIdStr2;
                                entryItems = dataModel.get("EntryItems");
                                if (undefined != entryItems && null != entryItems && entryItems.length > 0) {
                                    for (var j1=0; j1<entryItems.length;j1++){
                                        if (entryItems[j1].ParentFeeIdStr == parentFeeIdStr2 && entryItems[j1].FeeId != parentFeeIdStr2){
                                            osBalance = entryItems[j1].OsBalance;
                                            markOffAmount = entryItems[j1].MarkOffAmount;
                                            if (undefined != markOffAmount && !isNaN(markOffAmount) && null != markOffAmount && "" != markOffAmount){
                                                var settleDiff = parseFloat(osBalance) - parseFloat(markOffAmount);
                                                entryItems[j1].SettleDiff = settleDiff.toFixed(2);
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                entryItems = dataModel.get("EntryItems");
                                if (undefined != entryItems && null != entryItems && entryItems.length > 0) {
                                    for (var m1 = 0; m1 < entryItems.length; m1++) {
                                        if (entryItems[m1].ParentFeeIdStr == parentFeeIdStr2 && entryItems[m1].FeeId != parentFeeIdStr2) {
                                            entryItems[m1].SettleDiff = "";
                                        }
                                    }
                                }
                                parentFeeIdStr = "";
                            }
                            $page.controller.form.forceUpdate();
                        }
                    }],
                    searchable: false,
                    sortable: false,
                    columns: [
                        //{title:"",data:"ContractSelect",inline:"checkForType",width: "2%"},
                        {title: "Business Type", data: "BizTransType",codes:$page.codes.BusinessTransType,width:80},
                        {title: "Business Number", data: "BizTransNo",width: 80},
                        {title: "Contract ID", data: "ContractCode",width: 50},
                        {title: "Entry Item", data: "Description",width: 50},
                        {title: "Amount in OC", data: "AmountOC",width: 80,
                            render: function (row) {
                                if (undefined != row.AmountOC && null != row.AmountOC && "" != row.AmountOC){
                                    return row.AmountOC.toFixed(2);
                                }
                                return "";
                            }
                        },
                        {title: "OC", data: "Currency",codes : $page.codes.Currency,width: 30},
                        {title: "Due Date", data: "DueDate",width: 50,
                            render: function (row) {
                                var dueDate = row.DueDate;
                                if ("" != dueDate && undefined != dueDate) {
                                    dueDate = moment(row.DueDate,"YYYY-MM-DD").format("DD/MM/YYYY");
                                }
                                else {
                                    dueDate = "";
                                }
                                return dueDate;
                            }
                        },
                        {title: "Outstanding Balance", data: "OsBalance",width: 100,
                            render: function (row) {
                                if (undefined != row.OsBalance && null != row.OsBalance && "" != row.OsBalance){
                                    return row.OsBalance.toFixed(2);
                                }
                                return "";
                            }
                        },
                        {title: "Fully Settle", data: "FullySettle",inline:"fullySettle",width: 50},
                        {title: "Settlement details", data:"",width: 80}

                        //{title: "Settlement Type", data: "MarkOffAmount",inline : "numberForPayment",width: "8%"},
                        //{title: "Collection Currency", data: "SettleCurrency",inline:"readonlyText",width: "5%"},
                        //{title: "Assigned Amount in Collection Currency", data: "AssignedAmount",width: "13%",inline:"inlineAmount"},
                        //{title: "Collection Amount", data: "SettleAmount",inline:"inlineAmount",width: "5%"},
                        //{title: "Exchange Rate", data: "ExchangeRate",inline:"inlineExchangeRate",width: "5%"},
                        //{title:"Settlement Difference(OC)",data:"SettleDiff",inline:"inlineAmount",width: "10%"},
                        //{title:"E/X Gain or Loss(USD)",data:"GainLoss",inline:"inlineAmount",width: "5%"}
                    ],
                    rowOperations: [
                        {
                            tooltip: "View",

                            visible: {
                                depends: "BizTransType",
                                when: function (rowModel) {
                                    return rowModel.getCurrentModel().BizTransType != undefined;
                                }
                            },
                            click:function(model){
                                var FeeIdArray = model.FeeIdArray;
                                var url = "";
                                url = $pt.getURL('ui.queryView.settlementDetails');
                                url = url+"?view=true&FeeIdArray="+FeeIdArray;
                                console.info("++++++"+url);
                                window.open(url);
                            }
                        }
                    ]
                },
                css: {
                    comp: 'inline-editor'
                }
            };
        }
    }));
    context.ARAPCreditDebitTable = ARAPCreditDebitTable;
    NFormCell.registerComponentRenderer('ARAPCreditDebitTable', function(model, layout, direction) {
        return <ARAPCreditDebitTable model={model} layout={layout} direction={direction} />;
    });
    $pt.ComponentConstants.ARAPCreditDebitTable = {type: 'ARAPCreditDebitTable', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));