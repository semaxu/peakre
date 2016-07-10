(function (context, $, $pt) {
    var Cancellation = React.createClass($pt.defineCellComponent({
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
                            cancellation: {
                                label: "Cancellation",
                                style:'primary-darken',
                                collapsible : true,
                                expanded : false,
                                layout: {
                                    PNOC : {
                                        label:"PNOC Terms",
                                        pos:{
                                            width:12
                                        },
                                        comp:{
                                            visible: {
                                                when: function (model) {
                                                    if (model.get('ContractNature') == '2') {
                                                        return false;
                                                    }
                                                    return true;
                                                },
                                                depends: 'ContractNature'
                                            },
                                            type : $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                forCedent:{
                                                    label:"For Cedent",
                                                    comp:{
                                                        type: $pt.ComponentConstants.Label,
                                                        textFromModel: false
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:1
                                                    }
                                                },
                                                PnocCedentMonth:{
                                                    label:"Months",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    base : $helper.baseNumber(),
                                                    pos:{
                                                        width:4,
                                                        row:1
                                                    }
                                                },
                                                PnocCedentDay:{
                                                    label:"Days",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    base : $helper.baseNumber(),
                                                    pos:{
                                                        width:4,
                                                        row:1
                                                    }
                                                },
                                                forReinsurer:{
                                                    label:"For Reinsurer",
                                                    comp:{
                                                        type: $pt.ComponentConstants.Label,
                                                        textFromModel: false
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:2
                                                    }
                                                },
                                                PnocReinsurerMonth:{
                                                    label:"Months",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    base : $helper.baseNumber(),
                                                    pos:{
                                                        width:4,
                                                        row:2
                                                    }
                                                },
                                                PnocReinsurerDay:{
                                                    label:"Days",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    base : $helper.baseNumber(),
                                                    pos:{
                                                        width:4,
                                                        row:2
                                                    }
                                                },
                                                automatic:{
                                                    label:"Automatic",
                                                    comp:{
                                                        type: $pt.ComponentConstants.Label,
                                                        textFromModel: false
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:3
                                                    }
                                                },
                                                PnocAutomatic:{
                                                    label:"Y/N",
                                                    comp:{
                                                        type: $pt.ComponentConstants.Select,
                                                        data:$page.codes.Boolean
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:3
                                                    }
                                                }
                                            }
                                        },
                                        css: {comp: 'currency-align-right-text'}
                                    },
                                    DNOC : {
                                        label:"DNOC Terms",
                                        pos:{
                                            width:12
                                        },
                                        comp:{
                                            type : $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                war: {
                                                    label: "War",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Label,
                                                        textFromModel: false
                                                    },
                                                    css: {
                                                        cell: 'date-range-link'
                                                    },
                                                    pos: {
                                                        row: 1,
                                                        col: 2,
                                                        width: 4
                                                    }
                                                },
                                                DnocWarMonth:{
                                                    label:"Months",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    base : $helper.baseNumber(),
                                                    pos:{
                                                        width:4,
                                                        row:1
                                                    }
                                                },
                                                 DnocWarDay:{
                                                    label:"Days",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    base : $helper.baseNumber(),
                                                    pos:{
                                                        width:4,
                                                        row:1
                                                    }
                                                },
                                                politicalRisks:{
                                                    label:"Political Risks",
                                                    comp:{
                                                        type: $pt.ComponentConstants.Label,
                                                        textFromModel: false
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:2
                                                    }
                                                },
                                                DnocPoliticalMonth:{
                                                    label:"Months",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    base : $helper.baseNumber(),
                                                    pos:{
                                                        width:4,
                                                        row:2
                                                    }
                                                },
                                                DnocPoliticalDay:{
                                                    label:"Days",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    base : $helper.baseNumber(),
                                                    pos:{
                                                        width:4,
                                                        row:2
                                                    }
                                                },
                                                sanction:{
                                                    label:"Sanctions",
                                                    comp:{
                                                        type: $pt.ComponentConstants.Label,
                                                        textFromModel: false
                                                    },
                                                    pos:{
                                                        width:4,
                                                        row:3
                                                    }
                                                },
                                                DnocSanctionMonth:{
                                                    label:"Months",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    base : $helper.baseNumber(),
                                                    pos:{
                                                        width:4,
                                                        row:3
                                                    }
                                                },
                                                DnocSanctionDay:{
                                                    label:"Days",
                                                    comp:{
                                                        labelDirection: "horizontal",
                                                        enabled:true
                                                    },
                                                    base : $helper.baseNumber(),
                                                    pos:{
                                                        width:4,
                                                        row:3
                                                    }
                                                }
                                            }
                                        },
                                        css: {comp: 'currency-align-right-text'}
                                    },
                                    bottomButtons: {
                                        comp: {
                                            type: $pt.ComponentConstants.ButtonFooter,
                                            buttonLayout: {
                                                right: [{
                                                    text: "Save",
                                                    style: "primary",
                                                    visible: {
                                                        when: function (model) {
                                                            return model.get('OperateType') != '0' && model.get('OperateType') != '5';
                                                        }
                                                    },
                                                    click: function () {
                                                        $page.controller.save(true);
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
            if(this.getModel().get('OperateType') == 0 || this.getModel().get('OperateType') == 5){
                return <NForm model={this.getModel()} layout={this.state.editLayout} view={true}/>;
            }
            return <NForm model={this.getModel()} layout={this.state.editLayout}/>;
        }
    }));
    context.Cancellation = Cancellation;
    NFormCell.registerComponentRenderer('Cancellation', function(model, layout, direction) {
        return <Cancellation model={model} layout={layout} direction={direction} />;
    });
    $pt.ComponentConstants.Cancellation = {type: 'Cancellation', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
