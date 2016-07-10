(function (context, $, $pt) {
    var PayeeTable = React.createClass($pt.defineCellComponent({
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
            return {
                label: "",
                comp: {
                    type: $pt.ComponentConstants.Table,
                    searchable: false,
                    sortable: false,
                    addable: $page.viewEnable == "" && $page.collectInfoEnable,
                    removable: $page.viewEnable == "" && $page.collectInfoEnable,
                    columns: [
                        {
                            title: "Payee",
                            data: "Payee",
                            width: "40%",
                            inline: "PayeePayer"
                        }, {
                            title: "Payment Amount",
                            data: "PaymentAmount",
                            width: "40%",
                            inline: "PaymentAmountText"
                        }, {
                            title: "Payment Currency",
                            data: "CurrencyCode",
                            width: "20%",
                            inline: "inlineReadOnlyText"
                        }
                    ],
                    canRemove : function(model,item){
                        var _this = this;
                        var paymentAmount = item.PaymentAmount;
                        var totalAmount = _this.getModel().get("TotalAmount");
                        if (undefined == totalAmount || "" == totalAmount){
                            totalAmount = 0;
                        }
                        if (undefined == paymentAmount || null == paymentAmount ||
                            "" == paymentAmount || isNaN(parseFloat(paymentAmount))){
                            paymentAmount = 0;
                        }
                        totalAmount = parseFloat(totalAmount) - parseFloat(paymentAmount);
                        _this.getModel().set("TotalAmount", parseFloat(totalAmount).toFixed(2));
                        return true;
                    },
                    addClick: function (model) {
                        var _this = this;
                        var currency = _this.getModel().get("PaymentCurrency");
                        if (!$page.controller.checkPaymentDetail()){
                            return false;
                        }
                        $page.paymentInfoEnable = false;
                        //model.set("CurrencyCode", "USD");
                        model.add("Payee", {CurrencyCode:currency});
                        $page.controller.form.forceUpdate();
                    },
                    rowListener: [{
                        id: 'PaymentAmount',
                        listener: function (evt) {
                            var old = evt.old;
                            var newVal = evt.new;
                            var model = $page.controller.model;
                            var totalAmount = model.get("TotalAmount");
                            if (undefined == totalAmount || isNaN(parseFloat(totalAmount)) || null == totalAmount || "" == totalAmount){
                                totalAmount = 0;
                            }
                            if (undefined == old || null == old || "" == old){
                                old = 0;
                            } else if (old.indexOf("-") > -1){
                                //
                                return;
                            }else if (isNaN(parseFloat(old))){
                                old = 0;
                            }
                            if (newVal.indexOf("-") > -1) {
                                var tempAmount = parseFloat(totalAmount) - parseFloat(old);
                                model.set("TotalAmount", parseFloat(tempAmount).toFixed(2));
                                evt.model.set("PaymentAmount", "");
                                return;
                            }
                            if (parseFloat(newVal) < 0){
                                evt.model.set("PaymentAmount", "");
                            }
                            if (isNaN(parseFloat(newVal)) || "" == newVal){
                                evt.model.set("PaymentAmount", "");
                                newVal = 0;
                            }
                            newVal = parseFloat(parseFloat(newVal).toFixed(2));
                            old = parseFloat(parseFloat(old).toFixed(2));
                            totalAmount = parseFloat(parseFloat(totalAmount).toFixed(2)) + parseFloat(newVal) - parseFloat(old);
                            model.set("TotalAmount", parseFloat(totalAmount).toFixed(2));
                        }
                    },{
                        id:'Payee',
                        listener: function (evt) {
                            console.info(evt.model);
                        }
                    }]
                },
                pos: {
                    row: 3,
                    width: 12
                },
                css: {
                    comp: 'inline-editor'
                }
            };
        }
    }));
    context.PayeeTable = PayeeTable;
    NFormCell.registerComponentRenderer('PayeeTable', function(model, layout, direction) {
        return <PayeeTable model={model} layout={layout} direction={direction} />;
    });
    $pt.ComponentConstants.PayeeTable = {type: 'PayeeTable', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
