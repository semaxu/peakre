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
            NTable.registerInlineEditor('ExchangeRateInlineReadOnlyText', {
                comp: {
                    type: {type: $pt.ComponentConstants.Text, label: false},
                    format: $helper.formatNumber(6),
                    enabled: false
                }
            });
            NTable.registerInlineEditor('BalancePartner', {
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
                    rowListener: [{
                        id: 'MarkOffAmount',
                        listener: function(evt)  {
                            var amountInOC = evt.model.get('MarkOffAmount');
                            if (undefined == amountInOC || null == amountInOC || "" == amountInOC) {
                                evt.model.set("ExchangeRate", "");
                                return;
                            }
                            else {
                                if (parseFloat(amountInOC) < 0 || isNaN(parseFloat(amountInOC))){
                                    evt.model.set("MarkOffAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Refund in OC must not be negative or invalid number']
                                    });
                                    return;
                                }
                            }
                            var balanceAmount = evt.model.get("BalanceAmount");
                            if (parseFloat(parseFloat(amountInOC).toFixed(2)) > parseFloat(parseFloat(balanceAmount).toFixed(2))){
                                evt.model.set("MarkOffAmount", "");
                                evt.model.set("ExchangeRate", "");
                                NConfirm.getConfirmModal().show({
                                    title:"",
                                    disableClose: true,
                                    messages: ['Refund in OC must not be more than Balance Amount']
                                });
                                return;
                            }
                            var paymentAmount = evt.model.get('SettleAmount');
                            if (undefined == paymentAmount || null == paymentAmount || "" == paymentAmount) {
                                evt.model.set("ExchangeRate", "");
                                return;
                            }
                            else {
                                if (parseFloat(paymentAmount) <= 0 || isNaN(parseFloat(paymentAmount))){
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Refund Amount  must not be negative or invalid number']
                                    });
                                    return;
                                }
                            }
                            var exchangeRate = parseFloat(parseFloat(amountInOC).toFixed(2)) / parseFloat(parseFloat(paymentAmount).toFixed(2));
                            evt.model.set("ExchangeRate", exchangeRate.toFixed(6));
                        }
                    },{
                        id: 'SettleAmount',
                        listener: function(evt)  {
                            var paymentAmount = evt.model.get('SettleAmount');console.info(paymentAmount);
                            if (undefined == paymentAmount || null == paymentAmount || "" == paymentAmount) {
                                evt.model.set("SettleAmount", "");
                                evt.model.set("ExchangeRate", "");
                                return;
                            }
                            else {
                                if (parseFloat(paymentAmount) <= 0 || isNaN(parseFloat(paymentAmount))){
                                    evt.model.set("SettleAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    NConfirm.getConfirmModal().show({
                                        title:"",
                                        disableClose: true,
                                        messages: ['Refund Amount  must not be negative or invalid number']
                                    });
                                    return;
                                }
                            }
                            var amountInOC = evt.model.get('MarkOffAmount');
                            if (undefined == amountInOC || null == amountInOC || "" == amountInOC) {
                                evt.model.set("MarkOffAmount", "");
                                evt.model.set("ExchangeRate", "");
                                return;
                            }
                            else {
                                if (parseFloat(amountInOC) < 0 || isNaN(parseFloat(amountInOC))){
                                    evt.model.set("MarkOffAmount", "");
                                    evt.model.set("ExchangeRate", "");
                                    return;
                                }
                            }
                            var exchangeRate = parseFloat(amountInOC).toFixed(2) / parseFloat(paymentAmount).toFixed(2);
                            evt.model.set("ExchangeRate", exchangeRate.toFixed(6));
                        }
                    }],
                    searchable: false,
                    sortable: false,
                    columns: [
                        {title: "Balance Type", data: "SuspenseType", width: "10%",codes: $page.codes.BalanceType},
                        {title: "Contract ID", data: "ContractCode", width: "10%"},
                        {
                            title: "Business Partner",
                            data: "PartnerCode",
                            width: "10%",inline:"BalancePartner"
                        },
                        {title: "Balance Currency", data: "BalanceCurrency", width: "10%"},
                        {
                            title: "Balance Amount",
                            data: "BalanceAmount",
                            width: "10%",
                            inline: {
                                label: {
                                    comp: {
                                        type: {
                                            label: false,
                                            popover: false,
                                            render: function (row) {
                                                if (undefined != row.get("BalanceAmount") && null != row.get("BalanceAmount")
                                                    && "" != row.get("BalanceAmount")){
                                                    return $helper.formatNumberForLabel(row.get("BalanceAmount"), 2);
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
                        {title: "Refund in OC", data: "MarkOffAmount", width: "10%", inline : "numberForPayment2"},
                        {title: "Payment Currency", data: "SettleCurrencyCode", width: "10%", codes: $page.codes.Currency},
                        {title: "Refund Amount", data: "SettleAmount", width: "10%",inline : "numberForPayment2"},
                        {title: "Exchange Rate", data: "ExchangeRate", width: "10%", inline:"ExchangeRateInlineReadOnlyText"},
                        {title: "Generation Date", data: "GenerationDate", width: "10%", inline:"inlineReadOnlyDate"}
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
