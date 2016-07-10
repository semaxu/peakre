(function (context, $, $pt) {
    var ReserveAndRebate = React.createClass($pt.defineCellComponent({
        statics: {},
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
                            Reserve: {
                                label: "Reserve & NCB",
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                layout: {
                                    ReserveConditions: {
                                        label: "Reserve Conditions",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_PremiumCalcMethod: {
                                                    label: "Premium Calc Method",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.PremiumCalcMethod
                                                        // data: $page.codes.EarningPattern
                                                        // labelWidth:3
                                                    },
                                                    pos: {
                                                        // width:10
                                                    }
                                                },
                                                BusinessConditionVO_PremiumReserve: {
                                                    label: "Premium Reserve",
                                                    base : $helper.basePercentage(2),
                                                },
                                                BusinessConditionVO_InterestRate: {
                                                    label: "Interest Rate",
                                                    base : $helper.basePercentage(2),
                                                },
                                                BusinessConditionVO_AdjustedQuarter: {
                                                    label: "Adjusted Quarter",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data:$page.codes.AdjustedQuarter
                                                    },
                                                },


                                                BusinessConditionVO_LossCalcMethod: {
                                                    label: "Loss Calc Method",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                         data:$page.codes.LossCalcMethod,
                                                        // labelWidth:3
                                                    },
                                                    pos: {
                                                        // width:10
                                                    }
                                                },
                                                BusinessConditionVO_LossReserves: {
                                                    label: "Loss Reserves",
                                                    base : $helper.basePercentage(2),
                                                },

                                            }
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                    RebateConditions: {
                                        label: "Rebate Conditions",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_NoClaimBonus: {
                                                    label: "No Claim Bonus",
                                                    base : $helper.basePercentage(2),
                                                    pos: {
                                                        row: 1
                                                        // width:10
                                                    }
                                                },
                                                BusinessConditionVO_RebatePercent: {
                                                    label: "Rebate Percent",
                                                    base : $helper.basePercentage(2),
                                                    pos: {
                                                        row: 2
                                                        // width:10
                                                    }
                                                },
                                                BusinessConditionVO_LossRatioFrom: {
                                                    label: "Loss Ratio From",
                                                    base : $helper.basePercentage(2),
                                                    pos: {
                                                        row: 2
                                                        // width:10
                                                    }
                                                },
                                                BusinessConditionVO_LossRatioTo: {
                                                    label: "Loss Ratio To",
                                                    base : $helper.basePercentage(2),
                                                    pos: {
                                                        row: 2
                                                        // width:10
                                                    }
                                                },
                                                BusinessConditionVO_CalcDate: {
                                                    label: "Calculation Date",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Date,
                                                        format:"DD/MM/YYYY"
                                                    },
                                                    pos: {
                                                        row: 3
                                                    }
                                                },
                                                BusinessConditionVO_ExpirationYear: {
                                                    label: "Year after expiration",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text,
                                                    },
                                                    base : $helper.baseNumber(),
                                                    // css: {cell: 'currency-align-right-text'},
                                                    pos: {
                                                        row: 4
                                                        // width:10
                                                    }
                                                },
                                                BusinessConditionVO_ExpirationMonth: {
                                                    label: "Month after expiration",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text
                                                    },
                                                    base : $helper.baseNumber(),
                                                    // css: {cell: 'currency-align-right-text'},
                                                    pos: {
                                                        row: 4
                                                    }
                                                },
                                                BusinessConditionVO_ExpirationDays: {
                                                    label: "Days after expiration",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text
                                                    },
                                                    base : $helper.baseNumber(),
                                                    // css: {cell: 'currency-align-right-text'},
                                                    pos: {
                                                        row: 4
                                                    }
                                                },
                                                BusinessConditionVO_LrCalcYears: {
                                                    label: "Years before LR calc",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text
                                                    },
                                                    base : $helper.baseNumber(),
                                                    // css: {cell: 'currency-align-right-text'},
                                                    pos: {
                                                        row: 5
                                                    }
                                                }
                                            }
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                   RebateRemark: {
                                        label: "Remarks",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_RebateRemark: {
                                                    comp: {
                                                        type: {type: $pt.ComponentConstants.TextArea, label: false},
                                                        lines: 3
                                                    },
                                                    pos: {
                                                        width: 12
                                                    }
                                                }
                                            }
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                    rightButtons: {
                                        comp: {
                                            type: $pt.ComponentConstants.ButtonFooter,
                                            buttonLayout: {
                                                right: [{
                                                    text: "Save",
                                                    style: "primary",
                                                    visible: {
                                                        when: function (model) {
                                                            return model.get('BusinessConditionVO_OperateType') != '0' && model.get('BusinessConditionVO_OperateType') != '5';
                                                        }
                                                    },
                                                    click: function () {
                                                        $page.controller.save(true);
                                                    }
                                                }]
                                            }
                                        },
                                        pos: {
                                            width: 12
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

        render: function () {
            if(this.getModel().get('BusinessConditionVO_OperateType') == 0 || this.getModel().get('BusinessConditionVO_OperateType') == 5){
                return <NForm model={this.getModel()} layout={this.state.editLayout} view={true}/>;
            }
            return <NForm model={this.getModel()} layout={this.state.editLayout}/>;
        }
    }));
    context.ReserveAndRebate = ReserveAndRebate;
    NFormCell.registerComponentRenderer('ReserveAndRebate', function (model, layout, direction) {
        return <ReserveAndRebate model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.ReserveAndRebate = {type: 'ReserveAndRebate', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
