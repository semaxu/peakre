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
                                        click: function (model) {
                                            _this.setState({showChecked: false});
                                        }
                                    }, {
                                        text: "Show all records",
                                        style: "primary",
                                        click: function (model) {
                                            _this.setState({showChecked: true});
                                        }
                                    }]
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
                                        return (typeof(item.get('CurrencyCode'))=="undefined")?"":item.get('CurrencyCode');
                                    }
                                },
                                //expanded: false,
                                collapsible: true,
                                editLayout: {
                                    Amount: {
                                        label: 'Amount in Collection Currency',
                                        comp: {
                                            type: $pt.ComponentConstants.Text,
                                            tooltip: "Amount in Collection Currency",
                                            format: $helper.formatNumber(2),
                                            enabled: true
                                        }
                                    },
                                    ConvertedAmount: {
                                        label: "Converted Amount",
                                        comp: {
                                            type: $pt.ComponentConstants.Text,
                                            format: $helper.formatNumber(2)
                                        }
                                    },
                                    ExchangeRate: {
                                        label: "Exchange Rate",
                                        comp: {
                                            type: $pt.ComponentConstants.Text,
                                            format: $helper.formatNumber(6),
                                            enabled: false
                                        }
                                    },
                                    EntryItems: {
                                        comp: {
                                            type: $pt.ComponentConstants.ARAPCreditDebitTable,
                                            showChecked: this.state.showChecked
                                        },
                                        pos: {width: 12}
                                    }
                                },

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
            console.log("elvira");
            console.log(this.getModel());
            this.getModel().addPostChangeListener( "Amount", function(evt){
                console.log(evt.model);
                console.info(evt.model.get("Amount") + "|" + evt.model.get("ConvertedAmount"));
            });
            this.getModel().addPostChangeListener( "ConvertedAmount", function(evt){
                console.info(evt.model.get("Amount") + "|" + evt.model.get("ConvertedAmount"));
                /*if(evt.model.get("linkName") != evt.model.get("linkedNameSelect")){
                    evt.model.set("linkName",evt.model.get("linkedNameSelect"));
                }*/
            });
            return <NPanel model={this.getModel()} layout={layout} direction={this.props.direction}/>
        }
    }));
    context.ARAPCreditDebit = ARAPCreditDebit;
    NFormCell.registerComponentRenderer('ARAPCreditDebit', function (model, layout, direction) {
        return <ARAPCreditDebit model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.ARAPCreditDebit = {type: 'ARAPCreditDebit', label: false, popover: false};

}(typeof window !== "undefined" ? window : this, jQuery, $pt));