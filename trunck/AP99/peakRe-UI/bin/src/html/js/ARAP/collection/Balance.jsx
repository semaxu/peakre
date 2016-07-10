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
            return {
                label: "Balance",
                comp: {
                    type: $pt.ComponentConstants.Table,
                    searchable: false,
                    sortable: false,
                    addable: $page.viewEnable == "",
                    removable: $page.viewEnable == "",
                    rowListener: [{
                        id: 'SettleAmount',
                        listener: function(evt)  {
                            var settleAmount = evt.model.get('SettleAmount');
                            if (isNaN(parseFloat(settleAmount))){
                                evt.model.set("SettleAmount", "");
                                return;
                            }
                            evt.model.set("SettleAmount", parseFloat(settleAmount));
                        }
                    },{
                        id: 'SuspenseType',
                        listener: function(evt)  {
                            var suspenseType = evt.model.get('SuspenseType');
                            if (!suspenseType) {
                                evt.model.set("ContractId", "");
                            }
                            if (suspenseType == 2){
                                evt.model.set("ContractId", "");
                            }
                        }
                    }],
                    columns: [
                        {
                            title: "Balance Type",
                            data: "SuspenseType",
                            width: "16.5%",
                            inline: "BalanceTypeSelect"
                        }, {
                            title: "Contract ID",
                            data: "ContractId",
                            width: "16.5%",
                            inline: {
                                ContractId:{
                                    comp:{
                                        type: {type: $pt.ComponentConstants.Select, label: false},
                                        data: $page.contractIDCodeTable,
                                        enabled: {
                                            depends: 'SuspenseType',
                                            when: function (rowModel) {
                                                if (!rowModel.get("SuspenseType")) {
                                                    return false;
                                                }
                                                if (rowModel.get("SuspenseType") == 2){
                                                    return false;
                                                }
                                                if ($page.viewEnable != ""){
                                                    return false;
                                                }
                                                return true;
                                            }
                                        }
                                    },
                                    pos: {
                                        width: 12
                                    }
                                }
                            }
                        }, {
                            title: "Business Partner",
                            data: "PartnerCode",
                            width: "16.5%",
                            inline: "BusinessPartnerCodeSearch"
                        }, {
                            title: "Collection Amount",
                            data: "SettleAmount",
                            width: "16.5%",
                            inline: "numberForPayment2"
                        }, {
                            title: "Collection Currency",
                            data: "SettleCurrencyCode",
                            width: "16.5%",
                            inline: "inlineReadOnlyText"
                        },{
                        	title: "Generation Date", 
                        	data: "GenerationDate",
                        	width: "17.5%",
                        	inline: "inlineReadOnlyDate"
                         }
                    ],
                    addClick: function (model) {
                        var _this = this;
                        if (!$page.controller.checkSearchMandatory()){
                            return false;
                        }
                        $page.collectInfoEnable = false;
                        var payer = _this.getModel().get("Payer");
                        if (payer == undefined || null == payer || "" == payer){
                            payer = "";
                        }
                        var genDate = moment().format("YYYY-MM-DD");
                        var currency = _this.getModel().get("CollectionCurrency");
                        model.add("Balances", {PartnerCode:payer, SettleCurrencyCode:currency, GenerationDate:genDate});
                        $page.controller.form.forceUpdate();
                    }
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
