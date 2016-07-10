(function (context, $, $pt) {
    var codes = $pt.getService(context, "codes");

    var BCLossCorridor = React.createClass($pt.defineCellComponent({
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
                    editLimitLayout: {
                        _sections: {
                            Limit: {
                                label: "Loss Corridor/Loss Participation",
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                pos: {
                                    width: 12
                                },
                                layout: {
                                    BusinessConditionVO_ParticipBase: {
                                        label: "Participation Base",
                                        comp: {
                                            type: $pt.ComponentConstants.Select,
                                            data: $page.codes.ParticipationBase
                                        }
                                    },
                                    BusinessConditionVO_MinRatio: {
                                        label: "Minimum Ratio",
										base : $helper.basePercentage(2),
                                    },
                                    BusinessConditionVO_MaxRatio: {
                                        label: "Maximum Ratio",
                                        base : $helper.basePercentage(2),
                                    },
                                    BusinessConditionVO_CedentParticip: {
                                        label: "Cedent's Participation",
                                         base : $helper.basePercentage(2),
                                    },
                                    BusinessConditionVO_FirstCalcAfter: {
                                        label: "First Calc. After",
                                        // comp: {
                                        //    type: $pt.ComponentConstants.Text,
                                        //    rightAddon: {
                                        //        text: "Years"
                                        //    }
                                        // },
                                        base : $helper.baseYear()
                                    },
                                    BusinessConditionVO_SubCalcEvery: {
                                        label: "Subseq. Calc. Every",
                                        // comp: {
                                        //    type: $pt.ComponentConstants.Text,
                                        //    rightAddon: {
                                        //        text: "Years"
                                        //    }
                                        // },
                                        base : $helper.baseYear()
                                    },
                                    //ssTable: {
                                    //    label : "Upload Loss Participation SS Table",
                                    //    comp: {
                                    //        type: $pt.ComponentConstants.File,
                                    //        showPreview: false
                                    //    },
                                    //    pos: {
                                    //        width: 9
                                    //    }
                                    //},
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
                editLimitLayout: $pt.createFormLayout(this.getComponentOption('editLimitLayout'))
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
            if (this.getModel().get('BusinessConditionVO_OperateType') == 0 || this.getModel().get('BusinessConditionVO_OperateType') == 5) {
                return <NForm model={this.getModel()} layout={this.state.editLimitLayout} view={true}/>;
            }
            return <NForm model={this.getModel()} layout={this.state.editLimitLayout}/>;
        }
    }));
    context.BCLossCorridor = BCLossCorridor;
    NFormCell.registerComponentRenderer('BCLossCorridor', function (model, layout, direction) {
        return <BCLossCorridor model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.BCLossCorridor = {type: 'BCLossCorridor', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
