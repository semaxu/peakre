(function (context, $, $pt) {
    var parentFeeIdStr = "";
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
                    type: {type: $pt.ComponentConstants.Text, popover: false, label: false},
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
                },
                pos: {width: 12},
                css: {comp: 'currency-align-right-text'}
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
                },
                pos: {width: 12},
                css: {comp: 'currency-align-right-text'}
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
                    model: viewModel ,
                    useFormModel: true,
                    visible : {
                        when: function(row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr') ;
                        }
                    },
                    enabled: {
                        when: function (row) {
                            return this.getInnerModel().get("FullySettleStr-" + row.get('ParentFeeIdStr')) && $page.viewEnable == "";
                        },
                        depends: {id: /ParentFeeIdStr-.*/, on: 'inner'}
                    }
                }
            });
            NTable.registerInlineEditor('readonlyText2', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    model: viewModel ,
                    useFormModel: true,
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr');
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
            var _this = this;
            return {
                comp: {
                    type: $pt.ComponentConstants.Table,
                    rowListener: [{
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
                                        messages: ['Amount in Collection Currency is required']
                                    });
                                    return;
                                }
                                amount = parseFloat(parseFloat(amount).toFixed(2));
                                var convertedAmount = _this.getModel().get("ConvertedAmount");
                                if (convertedAmount == undefined || convertedAmount == "" || convertedAmount == 0 || convertedAmount == "0") {
                                    _this.getModel().set("ExchangeRate", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Amount in Original Currency must be required']
                                    });
                                    return;
                                }
                                convertedAmount = parseFloat(parseFloat(convertedAmount).toFixed(2));
                                if (markOffAmount == undefined || markOffAmount == "" || isNaN(markOffAmount)) {
                                    evt.model.set("AssignedAmount", "");
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("GainLoss", "");
                                    var oldMarkOffAmount = evt.old;
                                    if (!isNaN(parseFloat(oldMarkOffAmount))){
                                        var newTotalAmountAssigned = parseFloat(parseFloat(_this.getModel().get("AmountAssigned")).toFixed(2)) -
                                            parseFloat(parseFloat(oldMarkOffAmount).toFixed(2));console.info(oldMarkOffAmount + newTotalAmountAssigned);
                                        _this.getModel().set("AmountAssigned", newTotalAmountAssigned);
                                        _this.getModel().set("CollectedAssignedDiff", parseFloat(amount - newTotalAmountAssigned).toFixed(2));
                                    }
                                    return;
                                }
                                var osBalance = evt.model.get('OsBalance');
                                markOffAmount = parseFloat(parseFloat(markOffAmount).toFixed(2));
                                osBalance = parseFloat(parseFloat(osBalance).toFixed(2));
                                if (parseFloat(markOffAmount) == 0){
                                    evt.model.set("AssignedAmount", "");
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("GainLoss", "");
                                    var oldMarkOffAmount1 = evt.old;
                                    if (!isNaN(parseFloat(oldMarkOffAmount1))){
                                        var newTotalAmountAssigned1 = parseFloat(parseFloat(_this.getModel().get("AmountAssigned")).toFixed(2)) -
                                            parseFloat(parseFloat(oldMarkOffAmount1).toFixed(2));console.info(oldMarkOffAmount1 + newTotalAmountAssigned1);
                                        _this.getModel().set("AmountAssigned", newTotalAmountAssigned1);
                                        _this.getModel().set("CollectedAssignedDiff", parseFloat(amount - newTotalAmountAssigned1).toFixed(2));
                                    }
                                    return;
                                }
                                if (osBalance > 0 && (markOffAmount < 0 || markOffAmount > osBalance)) {
                                    evt.model.set("MarkOffAmount", "0.");
                                    evt.model.set("AssignedAmount", "");
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("GainLoss", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['The Mark-off Amount should be limited by the range [0, '+$helper.formatNumberForLabel(osBalance, 2)+'].']
                                    });
                                    return;
                                }
                                if (osBalance < 0 && (markOffAmount > 0 || markOffAmount < osBalance)) {
                                    evt.model.set("MarkOffAmount", "");
                                    evt.model.set("AssignedAmount", "");
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("GainLoss", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['The Mark-off Amount should be limited by the range ['+$helper.formatNumberForLabel(osBalance, 2)+', 0].']
                                    });
                                    return;
                                }
                                var totalAmountAssigned = _this.getModel().get("AmountAssigned");
                                var exchangeRate = parseFloat(parseFloat(_this.getModel().get("ExchangeRate")).toFixed(6));
                                if (markOffAmount != "" && exchangeRate != "") {
                                    var settleAmount = markOffAmount / (convertedAmount/amount);
                                    evt.model.set("SettleAmount", settleAmount.toFixed(2));
                                    var oldAssignedAmount = evt.model.get("AssignedAmount");
                                    if (undefined == oldAssignedAmount || null == oldAssignedAmount || "" == oldAssignedAmount){
                                        oldAssignedAmount = parseFloat(0);
                                    }
                                    if (undefined == totalAmountAssigned || null == totalAmountAssigned || "" == totalAmountAssigned){
                                        totalAmountAssigned = parseFloat(0);
                                    }
                                    var newAssignedAmount = parseFloat(parseFloat(markOffAmount * ((convertedAmount/amount))).toFixed(2));
                                    totalAmountAssigned = parseFloat(parseFloat(totalAmountAssigned).toFixed(2)) -
                                        parseFloat(parseFloat(oldAssignedAmount).toFixed(2)) + parseFloat(parseFloat(newAssignedAmount).toFixed(2));
                                    evt.model.set("AssignedAmount", newAssignedAmount);
                                    _this.getModel().set("AmountAssigned", totalAmountAssigned);
                                    var diffAmount = parseFloat(parseFloat(amount).toFixed(2))-totalAmountAssigned;
                                    _this.getModel().set("CollectedAssignedDiff", diffAmount.toFixed(2));
                                    evt.model.set("ExchangeRate", exchangeRate);
                                    var rowParentFeeIdStr = evt.model.get('ParentFeeIdStr');
                                    // FullySettle
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
                                        MarkOffAmount: markOffAmount,
                                        OriginalCurrency:evt.model.get("Currency"),
                                        SettleAmount: settleAmount,
                                        SettleCurrency: evt.model.get("SettleCurrency"),
                                        SettleDate:collectionDate
                                    };
                                    var returnVal = $page.controller.calGainLossInUSD(postData);
                                    if (undefined != returnVal && null != returnVal && "" != returnVal && !isNaN(returnVal)){
                                        evt.model.set("GainLoss", parseFloat(returnVal));
                                    }
                                }
                                delete _this.state.calculator[rowId];
                            }, 300);
                        }
                    },{
                        id:'FullySettle',
                        listener : function(evt) {
                            /*var contractSelect = evt.model.get('ContractSelect');
                            if (!contractSelect){
                                return;
                            }*/
                            viewModel.set('ParentFeeIdStr-' + evt.model.get('ParentFeeIdStr'), !evt.new);
                            var dataModel = _this.getModel();
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
                                                var settleDiff = parseFloat(parseFloat(osBalance).toFixed(2)) - parseFloat(parseFloat(markOffAmount).toFixed(2));
                                                if (parseFloat(settleDiff) == 0){
                                                    settleDiff = "0.00";
                                                }
                                                else {
                                                    settleDiff = parseFloat(settleDiff).toFixed(2);
                                                }
                                                entryItems[j1].SettleDiff = settleDiff;
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
                    },{
                        id:'ContractSelect',
                        listener : function(evt) {
                            viewModel.set('FullySettleStr-' + evt.model.get('ParentFeeIdStr'), evt.new);
                            var contractSelect = evt.model.get('ContractSelect');
                            var currency = _this.getModel().get("CurrencyCode");
                            var parentFeeIdStrTemp = evt.model.get('ParentFeeIdStr');
                            var amount = _this.getModel().get("Amount");
                            if (amount == undefined || amount == "" || amount == 0 || amount == "0") {
                                NConfirm.getConfirmModal().show({
                                    title:"",
                                    disableClose: true,
                                    messages: ['Amount in Collection Currency is required']
                                });
                                return;
                            }
                            amount = parseFloat(parseFloat(amount).toFixed(2));
                            if (!contractSelect){
                                var dataModel1 = $page.controller.model.getCurrentModel();
                                var creditsAndDebit1 = dataModel1.CreditsAndDebit;
                                var entryItems1,settleAmount1,markOffAmount1,assignedAmount1,osBalance1;
                                var totalAmountAssigned1 = _this.getModel().get("AmountAssigned");
                                evt.model.set('FullySettle', false);
                                viewModel.set('ParentFeeIdStr-' + evt.model.get('ParentFeeIdStr'), false);
                                for (var m1=0; m1<creditsAndDebit1.length;m1++){
                                    if (currency == creditsAndDebit1[m1].CurrencyCode){
                                        entryItems1 = creditsAndDebit1[m1].EntryItems;
                                        for (var m2=0;m2<entryItems1.length;m2++){
                                            if (parentFeeIdStrTemp == entryItems1[m2].ParentFeeIdStr && parentFeeIdStrTemp != entryItems1[m2].FeeIdStr){
                                                assignedAmount1 = entryItems1[m2].AssignedAmount;
                                                if (undefined != totalAmountAssigned1 && null != totalAmountAssigned1 && "" != totalAmountAssigned1){
                                                    totalAmountAssigned1 = parseFloat(parseFloat(totalAmountAssigned1).toFixed(2)) -
                                                        parseFloat(parseFloat(assignedAmount1).toFixed(2));
                                                }else{
                                                    totalAmountAssigned1 = 0;
                                                }
                                                entryItems1[m2].SettleAmount = "";
                                                entryItems1[m2].AssignedAmount = "";
                                                entryItems1[m2].ExchangeRate = "";
                                                entryItems1[m2].SettleDiff = "";
                                                entryItems1[m2].GainLoss = "";
                                            }
                                        }
                                    }
                                }
                                _this.getModel().set("AmountAssigned", totalAmountAssigned1.toFixed(2));
                                var diff1 = parseFloat(parseFloat(amount).toFixed(2)) - parseFloat(parseFloat(totalAmountAssigned1).toFixed(2));
                                _this.getModel().set("CollectedAssignedDiff", diff1.toFixed(2));
                                $page.controller.form.forceUpdate();
                                return;
                            }
                            evt.model.set('FullySettle', true);
                            var convertedAmount = _this.getModel().get("ConvertedAmount");
                            if (convertedAmount == undefined || convertedAmount == "" || convertedAmount == 0 || convertedAmount == "0") {
                                NConfirm.getConfirmModal().show({
                                    title:"",
                                    disableClose: true,
                                    messages: ['Amount in Original Currency must be required']
                                });
                                return;
                            }
                            convertedAmount = parseFloat(parseFloat(convertedAmount).toFixed(2));
                            var exchangeRate = parseFloat(parseFloat(_this.getModel().get("ExchangeRate")).toFixed(6));
                            var collectionDate = $page.controller.model.get("CollectionDate");
                            var dataModel = $page.controller.model.getCurrentModel();
                            var creditsAndDebit = dataModel.CreditsAndDebit;
                            var entryItems,settleAmount,markOffAmount,assignedAmount,osBalance,totalAmountAssigned=0;
                            for (var index1=0; index1<creditsAndDebit.length;index1++){
                                if (currency == creditsAndDebit[index1].CurrencyCode){
                                    entryItems = creditsAndDebit[index1].EntryItems;
                                    for (var index2=0;index2<entryItems.length;index2++){
                                        if (entryItems[index2].ContractSelect){
                                            parentFeeIdStrTemp = entryItems[index2].ParentFeeIdStr;
                                        }
                                        if (parentFeeIdStrTemp == entryItems[index2].ParentFeeIdStr && parentFeeIdStrTemp != entryItems[index2].FeeIdStr){
                                            markOffAmount = parseFloat(parseFloat(entryItems[index2].MarkOffAmount).toFixed(2));
                                            osBalance = parseFloat(parseFloat(entryItems[index2].OsBalance).toFixed(2));
                                            if (undefined != markOffAmount && !isNaN(markOffAmount) &&
                                                null != markOffAmount && ""!=markOffAmount){
                                                settleAmount = (markOffAmount/(convertedAmount/amount)).toFixed(2);
                                                entryItems[index2].SettleAmount = settleAmount;
                                                assignedAmount = (markOffAmount*(convertedAmount/amount)).toFixed(2);
                                                entryItems[index2].AssignedAmount = assignedAmount;
                                                entryItems[index2].ExchangeRate = exchangeRate;
                                                if (undefined != osBalance && !isNaN(parseFloat(osBalance))){
                                                    var settleDiff = parseFloat(osBalance - markOffAmount).toFixed(2);
                                                    if (parseFloat(settleDiff) == 0){
                                                        settleDiff = "0.00";
                                                    }
                                                    entryItems[index2].SettleDiff = settleDiff;
                                                }
                                                totalAmountAssigned = parseFloat(parseFloat(totalAmountAssigned).toFixed(2)) + parseFloat(assignedAmount);
                                                var postData = {
                                                    FeeId: entryItems[index2].FeeIdStr,
                                                    MarkOffAmount: markOffAmount,
                                                    OriginalCurrency: entryItems[index2].Currency,
                                                    SettleAmount: settleAmount,
                                                    SettleCurrency: entryItems[index2].SettleCurrency,
                                                    SettleDate:collectionDate
                                                };
                                                var returnVal = $page.controller.calGainLossInUSD(postData);
                                                if (undefined != returnVal && null != returnVal && "" != returnVal && !isNaN(returnVal)){
                                                    entryItems[index2].GainLoss = parseFloat(returnVal).toFixed(2);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            _this.getModel().set("AmountAssigned", totalAmountAssigned);
                            var diff = parseFloat(parseFloat(amount).toFixed(2)) - parseFloat(totalAmountAssigned);
                            _this.getModel().set("CollectedAssignedDiff", diff.toFixed(2));
                            $page.controller.form.forceUpdate();
                        }
                    }],
                    searchable: false,
                    sortable: false,
                    scrollX: true,
                    columns: [
                        {title:"",data:"ContractSelect",inline:"checkForType", width: 50},
                        {title: "Contract ID", data: "ContractCode", width: 100,
                            inline: {
                                label: {
                                    comp: {
                                        type: {
                                            label: false,
                                            popover: false,
                                            render: function (row) {
                                                return row.get('ContractCode');
                                            }
                                        },
                                        visible: {
                                            when: function (row) {
                                                return row.get('ParentFeeIdStr') == row.get('FeeIdStr');
                                            }
                                        }
                                    },
                                    pos: {width: 12}
                                }
                            }
                        },
                        {title: "Business Type", data: "BizTransType",codes:$page.codes.BusinessTransType, width: 100},
                        {title: "Business Number", data: "BizTransNo", width: 140,
                            inline: {
                                label: {
                                    comp: {
                                        type: {
                                            label: false,
                                            popover: false,
                                            render: function (row) {
                                                return row.get('BizTransNo');
                                            }
                                        },
                                        visible: {
                                            when: function (row) {
                                                return row.get('ParentFeeIdStr') == row.get('FeeIdStr');
                                            }
                                        }
                                    },
                                    pos: {width: 12}
                                }
                            }
                        },
                        {title: "Entry Item", data: "Description", width: 120},
                        {title: "Due Date", data: "DueDate", width: 100,
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
                        {title: "Amount in OC", data: "AmountOC", width: 140,
                            inline: {
                                label: {
                                    comp: {
                                        type: {
                                            label: false,
                                            popover: false,
                                            render: function (row) {
                                                if (undefined != row.get("AmountOC") && null != row.get("AmountOC") &&
                                                    "" != row.get("AmountOC")){
                                                    return $helper.formatNumberForLabel(row.get("AmountOC"),2);
                                                }
                                                return "0.00";
                                            }
                                        }
                                    },
                                    pos: {width: 12},
                                    css: {cell: 'currency-align-right'}
                                }
                            }
                        },
                        {title: "Outstanding Balance", data: "OsBalance", width: 140,
                            inline: {
                                label: {
                                    comp: {
                                        type: {
                                            label: false,
                                            popover: false,
                                            render: function (row) {
                                                if (undefined != row.get("OsBalance") && null != row.get("OsBalance")
                                                    && "" != row.get("OsBalance")){
                                                    return $helper.formatNumberForLabel(row.get("OsBalance"),2);
                                                }
                                                return "0.00";
                                            }
                                        }
                                    },
                                    pos: {width: 12},
                                    css: {cell: 'currency-align-right'}
                                }
                            }
                        },
                        {title: "Mark-off Amount", data: "MarkOffAmount", width: 140,inline:"numberForPayment"},
                        {title: "OC", data: "Currency",codes : $page.codes.Currency, width: 60},
                        {title: "Assigned Amount", data: "AssignedAmount", width: 140,inline:"inlineAmount"},
                        {title: "Collection Amount", data: "SettleAmount",inline:"inlineAmount", width: 140},
                        {title: "Collection Currency", data: "SettleCurrency",inline:"readonlyText", width: 80},
                        {title: "Exchange Rate", data: "ExchangeRate",inline:"inlineExchangeRate", width: 140},
                        {title: "Fully Settle", data: "FullySettle",inline:"fullySettle", width: 60},
                        {title:"Settlement Difference(OC)",data:"SettleDiff",inline:"inlineAmount", width: 140},
                        {title:"E/X Gain or Loss(USD)",data:"GainLoss",inline:"inlineAmount", width: 140}
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