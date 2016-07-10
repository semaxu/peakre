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
                                //expanded: false,
                                collapsible: true,
                                editLayout: function(model){
                                    model.addPostChangeListener("Amount", function(evt){
                                        var amount = evt.model.get("Amount");
                                        if (isNaN(amount) || parseFloat(amount) <= 0){
                                            evt.model.set("Amount", "");
                                            return;
                                        }
                                        var convertedAmount = evt.model.get("ConvertedAmount");
                                        if (isNaN(convertedAmount) || parseFloat(convertedAmount) <= 0){
                                            convertedAmount = 0;
                                            evt.model.set("ConvertedAmount", convertedAmount.toFixed(2));
                                        }
                                        var exchangeRate = "";
                                        if ("" != amount && "" != convertedAmount) {
                                            amount = parseFloat(amount);
                                            convertedAmount = parseFloat(convertedAmount);
                                            exchangeRate = convertedAmount / amount;
                                        }
                                        evt.model.set("ExchangeRate", exchangeRate);
                                    });
                                    model.addPostChangeListener("ConvertedAmount", function(evt){
                                        var amount = evt.model.get("Amount");
                                        var convertedAmount = evt.model.get("ConvertedAmount");
                                        if (isNaN(convertedAmount) || parseFloat(convertedAmount) <= 0){
                                            convertedAmount = 0;
                                            evt.model.set("ConvertedAmount", convertedAmount.toFixed(2));
                                            return;
                                        }
                                        if (isNaN(amount) || parseFloat(amount) <= 0){
                                            NConfirm.getConfirmModal().show({
                                                title: "",
                                                disableClose: true,
                                                messages: ['Amount in Collection Currency must be the number and greater than zero.']
                                            });
                                            evt.model.set("Amount", "");
                                            return;
                                        }
                                        var exchangeRate = "";
                                        if ("" != amount && "" != convertedAmount) {
                                            amount = parseFloat(amount);
                                            convertedAmount = parseFloat(convertedAmount);
                                            exchangeRate = convertedAmount / amount;
                                        }
                                        evt.model.set("ExchangeRate", exchangeRate);
                                    });
                                    return {
                                        //Amount: {
                                        //    label: 'Amount in Collection Currency',
                                        //    comp: {
                                        //        type: $pt.ComponentConstants.Text,
                                        //        tooltip: "Amount in Collection Currency",
                                        //        format: $helper.formatNumber(2),
                                        //        enabled: {
                                        //            when: function () {
                                        //                return $page.viewEnable == "";
                                        //            }
                                        //        }
                                        //    }
                                        //},
                                        //ConvertedAmount: {
                                        //    label: "Converted Amount",
                                        //    comp: {
                                        //        type: $pt.ComponentConstants.Text,
                                        //        format: $helper.formatNumber(2),
                                        //        enabled: {
                                        //            when: function () {
                                        //                return $page.viewEnable == "";
                                        //            }
                                        //        }
                                        //    }
                                        //},
                                        //ExchangeRate: {
                                        //    label: "Exchange Rate",
                                        //    comp: {
                                        //        type: $pt.ComponentConstants.Text,
                                        //        format: $helper.formatNumber(6),
                                        //        enabled: false
                                        //    }
                                        //},
                                        EntryItems: {
                                            comp: {
                                                type: $pt.ComponentConstants.ARAPCreditDebitTable,
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