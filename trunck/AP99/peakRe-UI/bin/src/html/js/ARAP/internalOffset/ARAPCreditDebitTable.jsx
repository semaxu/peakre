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
                    // do your filter logic, here filter all
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
                        //depends : "ContractSelect"
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
                    enabled: {
                        when: function () {
                            return false;
                        }
                    }
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
                    enabled: {
                        when: function () {
                            return false;
                        }
                    }
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
                        //depends : "ContractSelect"
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
                    visible: {
                        when: function (row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr');
                        }
                    },
                    enabled: false
                }
            });
            var _this = this;
            return {
                comp: {
                    type: $pt.ComponentConstants.Table,
                    rowListener: [{
                        id: 'ContractSelect',
                        listener: function(evt)  {
                            viewModel.set('FullySettleStr-' + evt.model.get('ParentFeeIdStr'), evt.new);
                            var contractSelect = evt.model.get('ContractSelect');
                            var parentFeeIdStrTemp = evt.model.get('ParentFeeIdStr');
                            if (!contractSelect){
                                var dataModel1 = $page.controller.model.getCurrentModel();
                                var creditsAndDebit1 = dataModel1.CreditsAndDebit;
                                evt.model.set('FullySettle', false);
                                viewModel.set('ParentFeeIdStr-' + evt.model.get('ParentFeeIdStr'), false);
                                var entryItems1;
                                for (var m1=0; m1<creditsAndDebit1.length;m1++){
                                    entryItems1 = creditsAndDebit1[m1].EntryItems;
                                    for (var m2=0;m2<entryItems1.length;m2++){
                                        if (parentFeeIdStrTemp == entryItems1[m2].ParentFeeIdStr && parentFeeIdStrTemp != entryItems1[m2].FeeIdStr){
                                            entryItems1[m2].SettleDiff = "";
                                        }
                                    }
                                }
                                $page.controller.form.forceUpdate();
                                return;
                            }
                            evt.model.set('FullySettle', true);
                            var dataModel = $page.controller.model.getCurrentModel();
                            var creditsAndDebit = dataModel.CreditsAndDebit;
                            var entryItems, osBalance, markOffAmount;
                            for (var index1=0; index1<creditsAndDebit.length;index1++){
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
                                            if (undefined != osBalance && !isNaN(parseFloat(osBalance))){
                                                var settleDiff = osBalance - markOffAmount;
                                                if (parseFloat(settleDiff) == 0){
                                                    settleDiff = "0.00";
                                                }
                                                entryItems[index2].SettleDiff = settleDiff;
                                            }
                                        }
                                    }
                                }
                            }
                            $page.controller.form.forceUpdate();
                        }
                    },{
                        id:'SettleAmount',
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
                                var markOffAmount = evt.model.get('SettleAmount');
                                if (markOffAmount == undefined || markOffAmount == "" || isNaN(parseFloat(markOffAmount))) {
                                    evt.model.set("SettleAmount", "0.00");
                                    return;
                                }
                                var osBalance = evt.model.get('OsBalanceInSettleCurrency');
                                markOffAmount = parseFloat(parseFloat(markOffAmount).toFixed(2));
                                osBalance = parseFloat(parseFloat(osBalance).toFixed(2));
                                if (osBalance > 0 && (markOffAmount < 0 || markOffAmount > osBalance)) {
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("SettleAmount", "0.00");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['The Mark-off Amount should be limited by the range [0, '+$helper.formatNumberForLabel(osBalance,2)+'].']
                                    });
                                    return;
                                }
                                if (osBalance < 0 && (markOffAmount > 0 || markOffAmount < osBalance)) {
                                    evt.model.set("SettleDiff", "");
                                    evt.model.set("SettleAmount", "0.00");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['The Mark-off Amount should be limited by the range ['+$helper.formatNumberForLabel(osBalance,2)+', 0].']
                                    });
                                    return;
                                }
                                var rowParentFeeIdStr = evt.model.get('ParentFeeIdStr');
                                if ("" != parentFeeIdStr && parentFeeIdStr == rowParentFeeIdStr) {
                                   var settleDiff = parseFloat(parseFloat(osBalance).toFixed(2)) - parseFloat(parseFloat(markOffAmount).toFixed(2));
                                   evt.model.set("SettleDiff", parseFloat(settleDiff).toFixed(2));
                                }
                                else {
                                    evt.model.set("SettleDiff", "");
                                }
                                evt.model.set("SettleAmount", parseFloat(markOffAmount).toFixed(2));
                                delete _this.state.calculator[rowId];
                            }, 800);
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
                                            osBalance = entryItems[j1].OsBalanceInSettleCurrency;
                                            markOffAmount = entryItems[j1].SettleAmount;
                                            if (undefined != markOffAmount && !isNaN(markOffAmount) && null != markOffAmount && "" != markOffAmount){
                                                var settleDiff = parseFloat(parseFloat(osBalance).toFixed(2)) - parseFloat(parseFloat(markOffAmount).toFixed(2));
                                                entryItems[j1].SettleDiff = parseFloat(settleDiff).toFixed(2);
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
                    //scrollX: true,
                    columns: [
                        {title:"",data:"ContractSelect",inline:"checkForType",width: "2%"},
						{title: "Contract ID", data: "ContractCode",width: "5%",
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
                        {title: "Business Type", data: "BizTransType",codes:$page.codes.BusinessTransType,width: "5%"},
                        {title: "Business Number", data: "BizTransNo",width: "8%",
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
                        {title: "Entry Item", data: "Description",width: "8%"},
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
                        {title: "Amount in OC", data: "AmountOC",width: "12%",
                            inline: {
                                label: {
                                    comp: {
                                        type: {
                                            label: false,
                                            popover: false,
                                            render: function (row) {
                                                if (undefined != row.get("AmountOC") && null != row.get("AmountOC")
                                                    && "" != row.get("AmountOC")){
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
                        {title: "OC", data: "Currency",codes : $page.codes.Currency,width: "5%"},
                        {title: "Outstanding Balance", data: "OsBalance",width: "12%",
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
                        {title: "Outstanding in USD", data: "OsBalanceInSettleCurrency",width: "12%",
                            inline: {
                                label: {
                                    comp: {
                                        type: {
                                            label: false,
                                            popover: false,
                                            render: function (row) {
                                                if (undefined != row.get("OsBalanceInSettleCurrency") && null != row.get("OsBalanceInSettleCurrency")
                                                    && "" != row.get("OsBalanceInSettleCurrency")){
                                                    if (row.get('ParentFeeIdStr') != row.get('FeeIdStr')) {
                                                        return $helper.formatNumberForLabel(row.get("OsBalanceInSettleCurrency"), 2);
                                                    }
                                                    else {
                                                        return "";
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    pos: {width: 12},
                                    css: {cell: 'currency-align-right'}
                                }
                            }
                        },
                        {title: "Mark-off Amount in USD", data: "SettleAmount",width: "12%",inline : "numberForPayment"},
                        {title: "Fully Settle", data: "FullySettle",inline:"fullySettle",width: "2%"},
                        {title:"Settlement Difference(USD)",data:"SettleDiff",inline:"inlineAmount",width: "12%"}
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