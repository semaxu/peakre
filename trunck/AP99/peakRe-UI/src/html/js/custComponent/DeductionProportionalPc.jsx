(function (context, $, $pt) {
    var DeductionProportionalPc = React.createClass($pt.defineCellComponent({
        statics: {
        },
        propTypes: {
            // model
            model: React.PropTypes.object,
            // CellLayout
            layout: React.PropTypes.object
        },
        getDefaultProps: function () {
            return {
                defaultOptions: {
                    editLayout: {
                        _sections: {
                            claimSec: {
                                label: "PC Detail",
                                style:'primary-darken',
                                layout: {
                                    ProfitCommission : {
                                        label:"Profit Commission",
                                        pos:{
                                            width:12,
                                            row:1
                                        },
                                        comp:{
                                            type : $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                Fixed:{
                                                    label:"Fixed",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:1
                                                    }
                                                },
                                                Scale: {
                                                    label: "Scale",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.ReserveType,
                                                        labelDirection: "horizontal"
                                                    },
                                                    pos: {
                                                        width: 4,
                                                        row: 1
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    ManagementExpense:{
                                        label:"Management Expense",
                                        comp:{
                                            type:{
                                                labelDirection: "horizontal",
                                                enabled:true
                                            },
                                            rightAddon:{
                                                text: "%"
                                            }
                                        },
                                        pos:{
                                            width:6,
                                            row:2
                                        }
                                    },
                                    DeficitCarriedForward : {
                                        label:"Deficit Carried Forward",
                                        pos:{
                                            width:12
                                        },
                                        comp:{
                                            type : $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                Fixed:{
                                                    label:"To Extinction",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:1
                                                    }
                                                },
                                                Scale: {
                                                    label: "Scale",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.ReserveType,
                                                        labelDirection: "horizontal"
                                                    },
                                                    pos: {
                                                        width: 4,
                                                        row: 1
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    CalculationFrequency : {
                                        label:"Calculation Frequency",
                                        pos:{
                                            width:12
                                        },
                                        comp:{
                                            type : $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                FirstCal:{
                                                    label:"First calc After",
                                                    comp:{
                                                        type: {
                                                            labelDirection: "horizontal",
                                                            enabled:true
                                                        },
                                                        rightAddon: {
                                                            text: "Years"
                                                        }
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:1
                                                    }
                                                },
                                                SubsequentCalc: {
                                                    label: "Subsequent calc every",
                                                    comp: {
                                                        type: {
                                                            labelDirection: "horizontal",
                                                            enabled:true
                                                        },
                                                        rightAddon: {
                                                            text: "Years"
                                                        }
                                                    },
                                                    pos: {
                                                        row: 1,
                                                        width: 4
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    linear : {
                                        label:"Linear",
                                        pos:{
                                            width:12
                                        },
                                        comp:{
                                            type : $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                MinimumRI:{
                                                    label:"Minimium RI Comm",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:1
                                                    }
                                                },
                                                MaximumRI: {
                                                    label: "Maximum RI Comm",
                                                    comp: {
                                                        type: {
                                                            labelDirection: "horizontal",
                                                            enabled:true
                                                        }
                                                    },
                                                    pos: {
                                                        row: 1,
                                                        width: 4
                                                    }
                                                },
                                                MinimumLoss:{
                                                    label:"Minimium Loss Rate",
                                                    comp:{
                                                        type:{
                                                            labelDirection: "horizontal",
                                                            enabled:true
                                                        },
                                                        rightAddon:{
                                                            text: "%"
                                                        }
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:2
                                                    }
                                                },
                                                MaximumLoss: {
                                                    label: "Maximum Loss Rate",
                                                    comp: {
                                                        type: {
                                                            labelDirection: "horizontal",
                                                            enabled:true
                                                        },
                                                        rightAddon: {
                                                            text: "%"
                                                        }
                                                    },
                                                    pos: {
                                                        row: 2,
                                                        width: 4
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    nonlinear : {
                                        label:"Non-Linear",
                                        pos:{
                                            width:12
                                        },
                                        comp:{
                                            type : $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                table: {
                                                    label: "",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        //indexable : true,
                                                        sortable:false,
                                                        //pageable:true,
                                                        //countPerPage : 5,
                                                        searchable:false,
                                                        editable: false,
                                                        removable: false,
                                                        addable: true,
                                                        //criteria : "paginationCriteria",
                                                        columns: [
                                                            {title: "LR % (Inclusive)", data: "idType", codes: $page.codes.IdType},
                                                            {title: "Commission %", data: "idNumber"}

                                                        ]

                                                    },
                                                    pos: {
                                                        width: 8

                                                    }
                                                }

                                            }
                                        }
                                    },

                                    bottomButtons: {
                                        comp: {
                                            type: $pt.ComponentConstants.ButtonFooter,
                                            buttonLayout: {
                                                right: [{
                                                    text: "Exit",
                                                    style: "warning",
                                                    click:function(){

                                                    }
                                                }, {
                                                    text: "Save",
                                                    style: "primary",
                                                    click:function(){
                                                    $page.controller.save();
                                                    }
                                                }]
                                            }
                                        },
                                        pos:{
                                            width:12
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            };
        },
        getInitialState: function () {
            return {
                editLayout: $pt.createFormLayout(this.getComponentOption('editLayout'))
            };
        },
        /**
         * will update
         * @param nextProps
         */
        componentWillUpdate: function (nextProps) {
            // remove post change listener to handle model change
            // this.removePostChangeListener(this.onModelChanged);
            // this.removeEnableDependencyMonitor();
        },
        /**
         * did update
         * @param prevProps
         * @param prevState
         */
        componentDidUpdate: function (prevProps, prevState) {
            // add post change listener to handle model change
            // this.addPostChangeListener(this.onModelChanged);
            // this.addEnableDependencyMonitor();
        },
        /**
         * did mount
         */
        componentDidMount: function () {
            // add post change listener to handle model change
            // this.addPostChangeListener(this.onModelChanged);
            // this.addEnableDependencyMonitor();
        },
        /**
         * will unmount
         */
        componentWillUnmount: function () {
            // remove post change listener to handle model change
            // this.removePostChangeListener(this.onModelChanged);
            // this.removeEnableDependencyMonitor();
        },

        render: function() {

            // css[this.getComponentCSS('n-array-check')] = true;
            return 	<NForm model={this.getModel()} layout={this.state.editLayout} />;
        }
    }));
    context.DeductionProportionalPc = DeductionProportionalPc;
    NFormCell.registerComponentRenderer('DeductionProportionalPc', function(model, layout, direction) {
        return <DeductionProportionalPc model={model} layout={layout} direction={direction} />;
    });
    $pt.ComponentConstants.DeductionProportionalPc = {type: 'DeductionProportionalPc', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
