(function (context, $, $pt) {
    var $page = $pt.getService(context, "$page");

    var ARAPCreditDebit = React.createClass($pt.defineCellComponent({
        statics: {},
        propTypes: {
            // model
            model: React.PropTypes.object,
            // CellLayout
            layout: React.PropTypes.object
        },
        componentWillUpdate: function() {
            this.unregisterFromComponentCentral();
        },
        componentDidUpdate: function() {
            this.registerToComponentCentral();
        },
        componentDidMount: function() {
            this.registerToComponentCentral();
        },
        componentWillUnmount: function() {
            this.unregisterFromComponentCentral();
        },
        getPanelLayout: function () {
            var _this = this;
            return {
                comp: {
                    type: $pt.ComponentConstants.Panel,
                    editLayout: {
                        buttons: {
                            comp: {
                                type: $pt.ComponentConstants.ButtonFooter,
                                buttonLayout: {
                                    right: [{
                                        text: "Show selected records",
                                        style: "primary",
                                        click: function () {
                                            _this.setState({showChecked: false});
                                        }
                                    }, {
                                        text: "Show all records",
                                        style: "primary",
                                        click: function () {
                                            _this.setState({showChecked: true});
                                        }
                                    }]
                                },
                                visible: {
                                    when: function () {
                                        return $page.viewEnable == "";
                                    }
                                }
                            },
                            pos: {
                                width: 12
                            }
                        },
                        CreditsAndDebit: {
                            label: 'Credit Panel',
                            comp: {
                                type: $pt.ComponentConstants.ArrayPanel,
                                itemTitle: {
                                    depends: 'Currency',
                                    when: function (item) {
                                        return (typeof(item.get('CurrencyCode'))=="undefined")?"No result":item.get('CurrencyCode');
                                    }
                                },
                                expanded: true,
                                collapsible: true,
                                editLayout: function(model){
                                    model.addPostChangeListener("Amount", function(evt){
                                        var currency = evt.model.get("CurrencyCode");
                                        var amount = evt.model.get("Amount");
                                        if (undefined == amount || null == amount || "" == amount){
                                            if (undefined != evt.model.get("ExchangeRate")){
                                                evt.model.set("ExchangeRate", "");
                                            }
                                            return;
                                        }
                                        amount = parseFloat(parseFloat(amount).toFixed(2));
                                        if (amount == 0){
                                            NConfirm.getConfirmModal().show({
                                                title:"",
                                                disableClose: true,
                                                messages: ["Amount in Payment Currency must not be 0"]
                                            });
                                            evt.model.set("Amount", "");
                                            return;
                                        }
                                        evt.model.set("Amount", amount);
                                        var convertedAmount = evt.model.get("ConvertedAmount");
                                        if (undefined == convertedAmount || null == convertedAmount || "" == convertedAmount){
                                            return;
                                        }
                                        convertedAmount = parseFloat(parseFloat(convertedAmount).toFixed(2));
                                        var exchangeRate;
                                        try{
                                            exchangeRate = parseFloat(parseFloat(convertedAmount / amount).toFixed(6));
                                        }
                                        catch(e){
                                            console.info(e);
                                            evt.model.set("Amount", "");
                                            return;
                                        }
                                        if (exchangeRate < 0) {
                                            evt.model.set("Amount", "");
                                            evt.model.set("ConvertedAmount", "");
                                            NConfirm.getConfirmModal().show({
                                                title:"",
                                                disableClose: true,
                                                messages: ["The exchange rate cannot be negative"]
                                            });
                                            return;
                                        }
                                        evt.model.set("ExchangeRate", exchangeRate.toFixed(6));
                                        var collectionDate = $page.controller.model.get("PaymentDate");
                                        var dataModel = $page.controller.model.getCurrentModel();
                                        var creditsAndDebit = dataModel.CreditsAndDebit;
                                        var entryItems,parentFeeIdStr,settleAmount,markOffAmount,assignedAmount,totalAmountAssigned=0,isFlush1=false;
                                        for (var index1=0; index1<creditsAndDebit.length;index1++){
                                            if (currency == creditsAndDebit[index1].CurrencyCode){
                                                entryItems = creditsAndDebit[index1].EntryItems;
                                                for (var index2=0;index2<entryItems.length;index2++){
                                                    if (entryItems[index2].ContractSelect){
                                                        isFlush1 = true;
                                                        parentFeeIdStr = entryItems[index2].ParentFeeIdStr;
                                                    }
                                                    if (parentFeeIdStr == entryItems[index2].ParentFeeIdStr && parentFeeIdStr != entryItems[index2].FeeIdStr){
                                                        entryItems[index2].ExchangeRate = exchangeRate.toFixed(6);
                                                        markOffAmount = parseFloat(parseFloat(entryItems[index2].MarkOffAmount).toFixed(2));
                                                        if (undefined != markOffAmount && !isNaN(markOffAmount) &&
                                                            null != markOffAmount && ""!=markOffAmount){
                                                            settleAmount = parseFloat(parseFloat(markOffAmount/(convertedAmount/amount)).toFixed(2));
                                                            entryItems[index2].SettleAmount = settleAmount;
                                                            assignedAmount = parseFloat(parseFloat(markOffAmount*(convertedAmount/amount)).toFixed(2));
                                                            entryItems[index2].AssignedAmount = assignedAmount;
                                                            totalAmountAssigned = parseFloat(totalAmountAssigned) + parseFloat(assignedAmount);
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
                                        if (isFlush1){
                                            evt.model.set("AmountAssigned", totalAmountAssigned.toFixed(2));
                                            var diff = parseFloat(amount) - parseFloat(totalAmountAssigned);
                                            evt.model.set("CollectedAssignedDiff", diff.toFixed(2));
                                            $page.controller.form.forceUpdate();
                                        }
                                    });
                                    model.addPostChangeListener("ConvertedAmount", function(evt){
                                        var convertedAmount = evt.model.get("ConvertedAmount");
                                        if (undefined == convertedAmount || null == convertedAmount || "" == convertedAmount){
                                            return;
                                        }
                                        convertedAmount = parseFloat(parseFloat(convertedAmount).toFixed(2));
                                        if (convertedAmount == 0){
                                            NConfirm.getConfirmModal().show({
                                                title:"",
                                                disableClose: true,
                                                messages: ["Amount in Original Currency must not be 0"]
                                            });
                                            evt.model.set("ConvertedAmount", "");
                                            return;
                                        }
                                        evt.model.set("ConvertedAmount", convertedAmount);
                                        var currency = evt.model.get("CurrencyCode");
                                        var amount = evt.model.get("Amount");
                                        if (undefined == amount || null == amount || "" == amount){
                                            if (undefined != evt.model.get("ExchangeRate")){
                                                evt.model.set("ExchangeRate", "");
                                            }
                                            return;
                                        }
                                        amount = parseFloat(parseFloat(amount).toFixed(2));
                                        var exchangeRate;
                                        try{
                                            exchangeRate = parseFloat(parseFloat(convertedAmount / amount).toFixed(6));
                                        }
                                        catch(e){
                                            console.info(e);
                                            evt.model.set("Amount", "");
                                            return;
                                        }
                                        if (exchangeRate < 0) {
                                            evt.model.set("Amount", "");
                                            evt.model.set("ConvertedAmount", "");
                                            NConfirm.getConfirmModal().show({
                                                title:"",
                                                disableClose: true,
                                                messages: ["The exchange rate cannot be negative"]
                                            });
                                            return;
                                        }
                                        evt.model.set("ExchangeRate", exchangeRate.toFixed(6));
                                        var collectionDate = $page.controller.model.get("PaymentDate");
                                        var dataModel = $page.controller.model.getCurrentModel();
                                        var creditsAndDebit = dataModel.CreditsAndDebit;
                                        var entryItems,parentFeeIdStr,settleAmount,markOffAmount,assignedAmount,totalAmountAssigned=0,isFlush1=false;
                                        for (var index1=0; index1<creditsAndDebit.length;index1++){
                                            if (currency == creditsAndDebit[index1].CurrencyCode){
                                                entryItems = creditsAndDebit[index1].EntryItems;
                                                for (var index2=0;index2<entryItems.length;index2++){
                                                    if (entryItems[index2].ContractSelect){
                                                        isFlush1 = true;
                                                        parentFeeIdStr = entryItems[index2].ParentFeeIdStr;
                                                    }
                                                    if (parentFeeIdStr == entryItems[index2].ParentFeeIdStr && parentFeeIdStr != entryItems[index2].FeeIdStr){
                                                        entryItems[index2].ExchangeRate = exchangeRate.toFixed(6);
                                                        markOffAmount = parseFloat(parseFloat(entryItems[index2].MarkOffAmount).toFixed(2));
                                                        if (undefined != markOffAmount && !isNaN(markOffAmount) &&
                                                            null != markOffAmount && ""!=markOffAmount){
                                                            settleAmount = parseFloat(parseFloat(markOffAmount/(convertedAmount/amount)).toFixed(2));
                                                            entryItems[index2].SettleAmount = settleAmount;
                                                            assignedAmount = parseFloat(parseFloat(markOffAmount*(convertedAmount/amount)).toFixed(2));
                                                            entryItems[index2].AssignedAmount = assignedAmount;
                                                            totalAmountAssigned = parseFloat(totalAmountAssigned) + parseFloat(assignedAmount);
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
                                        if (isFlush1){
                                            evt.model.set("AmountAssigned", totalAmountAssigned.toFixed(2));
                                            var diff = parseFloat(amount) - parseFloat(totalAmountAssigned);
                                            evt.model.set("CollectedAssignedDiff", diff.toFixed(2));
                                            $page.controller.form.forceUpdate();
                                        }
                                    });
                                    return {
                                        Amount: {
                                            label: 'Amount in Payment Currency',
                                            base : $helper.baseAmount(2),
                                            comp: {
                                                enabled: {
                                                    when: function () {
                                                        return $page.viewEnable == "";
                                                    }
                                                }
                                            }
                                        },
                                        ConvertedAmount: {
                                            label: "Amount in Original Currency",
                                            base : $helper.baseAmount(2),
                                            comp: {
                                                enabled: {
                                                    when: function () {
                                                        return $page.viewEnable == "";
                                                    }
                                                }
                                            }
                                        },
                                        ExchangeRate: {
                                            label: "Exchange Rate",
                                            base : $helper.baseAmount(6),
                                            comp: {
                                                enabled: false
                                            }
                                        },
                                        AmountAssigned: {
                                            label: "Converted Amount in Payment Currency",
                                            base : $helper.baseAmount(2),
                                            comp: {
                                                enabled: false
                                            }
                                        },
                                        CollectedAssignedDiff: {
                                            label: "Difference between Amount Paid and Assigned",
                                            base : $helper.baseAmount(2),
                                            comp: {
                                                enabled: false
                                            }
                                        },
                                        EntryItems: {
                                            comp: {
                                                type: $pt.ComponentConstants.ARAPCreditDebitTable,
                                                collapsible: true,
                                                showChecked: _this.state.showChecked
                                            },
                                            pos: {width: 12}
                                        }
                                    }
                                }
                            },
                            pos: {
                                width: 12
                            }
                        }
                    }
                }
            };
        },
        getInitialState: function () {
            return {
                showChecked: true
            };
        },
        render: function () {
            var layout = $pt.createCellLayout(this.getDataId(), this.getPanelLayout());
            return <NPanel model={this.getModel()} layout={layout} direction={this.props.direction}/>
        }
    }));
    context.ARAPCreditDebit = ARAPCreditDebit;
    NFormCell.registerComponentRenderer('ARAPCreditDebit', function (model, layout, direction) {
        return <ARAPCreditDebit model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.ARAPCreditDebit = {type: 'ARAPCreditDebit', label: false, popover: false};

}(typeof window !== "undefined" ? window : this, jQuery, $pt));