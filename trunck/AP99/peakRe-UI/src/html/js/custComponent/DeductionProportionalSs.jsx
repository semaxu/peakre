(function (context, $, $pt) {
    var DeductionproportionalSs = React.createClass($pt.defineCellComponent({
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
                                label: "SS Commission Detail",
                                style:'primary-darken',
                                layout: {
                                    lossRatioDefinition : {
                                        label:"Loss Ratio Definition",
                                        pos:{
                                            width:12,
                                            row:1
                                        },
                                        comp:{
                                            type : $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                lossIncludeIBNR:{
                                                    label:"Loss include IBNR",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    pos:{
                                                        width:6,
                                                        row:1
                                                    }
                                                }
                                            }
                                        }
                                    },

                                    carriedForwardPanel : {
                                        label:"Carried Forward",
                                        pos:{
                                            width:12
                                        },
                                        comp:{
                                            type : $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                carriedForwards: {
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
                                                        addable: false,
                                                        //criteria : "paginationCriteria",
                                                        columns: [
                                                            {title: "", data: "name"},
                                                            {title: "Y/N", data: "yn"   ,  inline: "text"},
                                                            {title: "Number Of Years", data: "years",inline: "text"},
                                                            {title: "To Extinction", data: "extinction",inline: "text"},

                                                        ]

                                                    },
                                                    pos: {
                                                        width: 8


                                                    }
                                                }
                                            }
                                        }
                                    },

                                    calculationFrequency : {
                                        label:"Calculation Frequency",
                                        pos:{
                                            width:12
                                        },
                                        comp:{
                                            type : $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                firstCal:{
                                                    label:"First calc After",
                                                    comp:{
                                                        type: {
                                                            labelDirection: "horizontal",
                                                            enabled:true
                                                        }
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:1
                                                    }
                                                },
                                                subsequentCalc: {
                                                    label: "Subsequent calc every",
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
                                                minimumRI:{
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
                                                maximumRI: {
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
                                                minimumLoss:{
                                                    label:"Minimium Loss Rate",
                                                    comp:{
                                                        type:{
                                                            labelDirection: "horizontal",
                                                            enabled:true
                                                        }
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:2
                                                    }
                                                },
                                                maximumLoss: {
                                                    label: "Maximum Loss Rate",
                                                    comp: {
                                                        type: {
                                                            labelDirection: "horizontal",
                                                            enabled:true
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
                                                check: {
                                                    label: "Inclusive",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Check
                                                        //labelDirection: "horizontal"
                                                    },
                                                    pos: {
                                                        row: 1,
                                                        col: 2
                                                        //width:6

                                                    }
                                                },
                                                table: {
                                                    label: "",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        //indexable : true,
                                                        //sortable:true,
                                                        //pageable:true,
                                                        //countPerPage : 5,
                                                        searchable:false,
                                                        editable: true,
                                                        removable: true,
                                                        addable: true,
                                                        //criteria : "paginationCriteria",
                                                        columns: [
                                                            {title: "LR % (Inclusive)", data: "idType", codes: $page.codes.IdType},
                                                            {title: "Commission %", data: "idNumber"}

                                                        ]

                                                    },
                                                    pos: {
                                                        width: 8,
                                                        row: 1

                                                    }
                                                },
                                                buttoms: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.ButtonFooter,
                                                        buttonLayout: {
                                                            right: [{
                                                                text: "Upload Nonlinear Table",
                                                                style: "primary",
                                                                click:function(){

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
    context.DeductionproportionalSs = DeductionproportionalSs;
    NFormCell.registerComponentRenderer('DeductionproportionalSs', function(model, layout, direction) {
        return <DeductionproportionalSs model={model} layout={layout} direction={direction} />;
    });
    $pt.ComponentConstants.DeductionproportionalSs = {type: 'DeductionproportionalSs', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
