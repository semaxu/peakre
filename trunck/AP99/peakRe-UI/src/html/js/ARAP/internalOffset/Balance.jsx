(function (context, $, $pt) {
    var BalanceTable = React.createClass($pt.defineCellComponent({
        propTypes: {
            model: React.PropTypes.object,
            layout: React.PropTypes.object
        },
        getInitialState: function() {
            return {
                viewModel: $pt.createModel({})
            }
        },
        getDisplayModel: function() {
            return this.getModel();
        },
        render: function() {
            var layout = $pt.createCellLayout(this.getDataId(), this.getTableLayout());
            return <NTable model={this.getDisplayModel()} layout={layout} />
        },
        getTableLayout: function() {
            NTable.registerInlineEditor('BusinessPartnerCodeSearch', {
                comp: {
                    type: {type: $pt.ComponentConstants.BPSearch, label: false},
                    searchTriggerDigits: 6,
                    enabled: false
                }
            });
            return {
                label: "Balance",
                comp: {
                    type: $pt.ComponentConstants.Table,
                    searchable: false,
                    sortable: false,
                    header: false,
                    rowListener: [{
                        id: 'MarkOffAmountInSettleCurrency',
                        listener: function(evt)  {
                            var markOffAmount = evt.model.get('MarkOffAmountInSettleCurrency');
                            if (isNaN(parseFloat(markOffAmount))){
                                evt.model.set("MarkOffAmountInSettleCurrency", "");
                                return;
                            }
                            var balanceAmount = evt.model.get('BalanceAmountInSettleCurrency');
                            if (parseFloat(markOffAmount) < parseFloat(0)){
                                NConfirm.getConfirmModal().show({
                                    title:"",
                                    disableClose: true,
                                    messages: ['Mark-off Amount in USD must be greater than zero']
                                });
                                evt.model.set("MarkOffAmountInSettleCurrency", "");
                                return;
                            }
                            if (parseFloat(markOffAmount) > parseFloat(balanceAmount)){
                                NConfirm.getConfirmModal().show({
                                    title:"",
                                    disableClose: true,
                                    messages: ['Mark-off Amount in USD must not be greater than Balance Amount in USD']
                                });
                                evt.model.set("MarkOffAmountInSettleCurrency", "");
                                return;
                            }
                        }
                    }],
                    columns: [
                        {
                            title: "Balance Type",
                            data: "SuspenseType",
                            width: "12.5%",
                            codes: $page.codes.BalanceType
                        }, {
                            title: "Contract ID",
                            data: "ContractCode",
                            width: "12.5%"
                        }, {
                            title: "Business Partner",
                            data: "PartnerCode",
                            width: "12.5%",
                            inline: "BusinessPartnerCodeSearch"
                        }, {
                            title: "Balance Amount",
                            data: "BalanceAmount",
                            width: "12.5%",
                            inline: {
                                label: {
                                    comp: {
                                        type: {
                                            label: false,
                                            popover: false,
                                            render: function (row) {
                                                if (undefined != row.get("BalanceAmount") && null != row.get("BalanceAmount")
                                                    && "" != row.get("BalanceAmount")){
                                                    return $helper.formatNumberForLabel(row.get("BalanceAmount"),2);
                                                }
                                                return "0.00";
                                            }
                                        }
                                    },
                                    pos: {width: 12},
                                    css: {cell: 'currency-align-right'}
                                }
                            }
                        }, {
                            title: "Currency",
                            data: "BalanceCurrency",
                            width: "12.5%",
                            codes: $page.codes.Currency
                        }, {
                            title: "Balance Amount in USD",
                            data: "BalanceAmountInSettleCurrency",
                            width: "12.5%",
                            inline: {
                                label: {
                                    comp: {
                                        type: {
                                            label: false,
                                            popover: false,
                                            render: function (row) {
                                                if (undefined != row.get("BalanceAmountInSettleCurrency") && null != row.get("BalanceAmountInSettleCurrency")
                                                    && "" != row.get("BalanceAmountInSettleCurrency")){
                                                    return $helper.formatNumberForLabel(row.get("BalanceAmountInSettleCurrency"),2);
                                                }
                                                return "0.00";
                                            }
                                        }
                                    },
                                    pos: {width: 12},
                                    css: {cell: 'currency-align-right'}
                                }
                            }
                        }, {
                            title: "Mark-off Amount in USD",
                            data: "MarkOffAmountInSettleCurrency",
                            inline : "numberForPayment2",
                            width: "12.5%"
                        },
                        {title: "Generation Date", data: "GenerationDate", width: "12.5%", inline:"inlineReadOnlyDate"}
                    ]
                },
                pos: {
                    row: 4,
                    width: 12
                },
                css: {
                    comp: 'inline-editor'
                }
            };
        }
    }));
    context.BalanceTable = BalanceTable;
    NFormCell.registerComponentRenderer('BalanceTable', function(model, layout, direction) {
        return <BalanceTable model={model} layout={layout} direction={direction} />;
    });
    $pt.ComponentConstants.BalanceTable = {type: 'BalanceTable', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
