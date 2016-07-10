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
                                //expanded: false,
                                collapsible: true,
                                editLayout: function(model){
                                    return {
                                        EntryItems: {
                                            comp: {
                                                type: $pt.ComponentConstants.ARAPCreditDebitTable,
                                                //collapsible: true,
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