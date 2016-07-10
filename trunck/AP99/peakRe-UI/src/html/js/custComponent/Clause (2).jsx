(function (context, $, $pt) {
    var Clause = React.createClass($pt.defineCellComponent({
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
                            Clauses: {
                                label: "Clauses",
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                layout: {
                                    BusinessConditionVO_ClausesRequiredList: {
                                        label: "Required",
                                        comp: {
                                            type: $pt.ComponentConstants.SelectTree,
                                            data: $page.codes.required,
                                            // root: true,
                                            // check: true,
                                            hideChildWhenParentChecked: true,
                                            labelWidth: 3,
                                            treeLayout: {
                                                comp: {
                                                    hierarchyCheck: true,
                                                    expandLevel: "all",
                                                    inactiveSlibing: false,
                                                    valueAsArray: true
                                                }
                                            }
                                        },
                                        pos: {
                                            row: 1,
                                            width: 6
                                        }
                                    },
                                    BusinessConditionVO_ClausesRecommendList: {
                                        label: "Condition",
                                        comp: {
                                            type: $pt.ComponentConstants.SelectTree,
                                            data: $page.codes.recommend,
                                            // root: true,
                                            // check: true,
                                            hideChildWhenParentChecked: true,
                                            labelWidth: 3,
                                            treeLayout: {
                                                comp: {
                                                    hierarchyCheck: true,
                                                    expandLevel: "all",
                                                    inactiveSlibing: false,
                                                    valueAsArray: true
                                                }
                                            }
                                        },
                                        pos: {
                                            row: 2,
                                            width: 6
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
            if (this.getModel().get('BusinessConditionVO_OperateType') == 0 || this.getModel().get('BusinessConditionVO_OperateType') == 5) {
                return <NForm model={this.getModel()} layout={this.state.editLayout} view={true}/>;
            }
            return <NForm model={this.getModel()} layout={this.state.editLayout}/>;
        }
    }));
    context.Clause = Clause;
    NFormCell.registerComponentRenderer('Clause', function (model, layout, direction) {
        return <Clause model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.Clause = {type: 'Clause', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
