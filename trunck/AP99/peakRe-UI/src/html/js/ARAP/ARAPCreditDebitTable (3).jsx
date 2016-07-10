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
                    //console.log(item.checked);
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
                            return this.getInnerModel().get("ParentFeeIdStr-" + row.get('ParentFeeIdStr'));
                        },
                        //depends : "ContractSelect"
                        depends: {id: /ParentFeeIdStr-.*/, on: 'inner'}
                    }
                }
            });

            NTable.registerInlineEditor('checkForPayment', {
                comp: {
                    type: {type: $pt.ComponentConstants.Check, label: false},
                    visible : {when: function(row) {return row.get('ParentFeeIdStr') == row.get('FeeIdStr');}, depends: 'ParentFeeIdStr'},
                    //enabled : function(row){}
                }
            });
            NTable.registerInlineEditor('checkForType', {
                comp: {
                    type: {
                        type: $pt.ComponentConstants.Check, label: false},
                        visible : {when: function(row) {
                            return row.get('ParentFeeIdStr') == row.get('FeeIdStr') ;
                        },
                        depends: 'Type'
                    }
                }
            });

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
                            console.info(evt.model.get('MarkOffAmount'));
                        }
                    }],
                    searchable: false,
                    sortable: false,
                    scrollX: true,
                    columns: [
                        {title:"",data:"ContractSelect",inline:"checkForType",width: 50},
                        {title:"Type",data:"Type",width: 80},
                        {title: "Contract ID", data: "ContractID",width:120,render:function(row){if(row.Type == "CN"||row.Type == "DN") return row.ContractID; else return ""}},
                        {title: "SoA No.", data: "SoaNumber",width:120,render:function(row){if(row.Type == "CN"||row.Type == "DN") return row.SoaNumber; else return ""}},
                        {title: "Claim No.", data: "ClaimNo",width:120,render:function(row){if(row.Type == "CN"||row.Type == "DN") return row.ClaimNo; else return ""}},
                        {title: "Description", data: "Description",width:150},
                        {title: "Amount(OC)", data: "AmountOC",width:120,
                            render: function (row) {
                                return $helper.formatNumberForLabel(row.AmountOC, 2);
                            }
                        },
                        {title: "OC", data: "Currency",codes : $page.codes.Currency,width:120},
                        {title: "Due Date", data: "DueDate",width:120},
                        {title: "O/S Balance", data: "OsBalance",width:150,
                            render: function (row) {
                                return $helper.formatNumberForLabel(row.OsBalance, 2);
                            }
                        },
                        {title: "Mark-off Amount", data: "MarkOffAmount",width:120,inline : "numberForPayment"},
                        {title: "Receipt Currency", data: "ReceiptCurrency",codes:$page.codes.Currency,width:120},
                        {title: "Receipt Amount", data: "ReceiptAmount",width:120},
                        {title: "Exchange Rate", data: "Rate",width:120},
                        {title: "Fully Settle", data: "FullySettle",inline:"checkForType",width:80},
                        {title:"Settlement Difference",data:"SettleDiff",width:150},
                        {title:"E/X Gain or Loss",data:"GainLoss",width:150}
                    ],
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