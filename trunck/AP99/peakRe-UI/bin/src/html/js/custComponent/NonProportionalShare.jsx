(function (context, $, $pt) {
    var NonProportionalShare = React.createClass($pt.defineCellComponent({
        statics: {
          OLD_WrittenShare1: null,
          OLD_SignedShares1 : null
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
                            Share: {
                                label: "Share",
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                layout: {
                                    BusinessConditionVO_Coinsurance: {
                                        label: "Co-insurance",
                                        comp: {
                                            type: $pt.ComponentConstants.Text,
                                            rightAddon: {
                                                text: '%'
                                            }
                                        },
                                        base : $helper.basePercentage(2),
                                        pos: {
                                            // width:10
                                        }
                                    },
                                    CoShareCatePanel1: {
                                        label: "From 100%",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_WrittenShare1: {
                                                    label: "Written Shares",
                                                    base : $helper.basePercentage(2),
                                                    evt : {
                                                      blur : function(){
                                                        var newValue = this.getModel().get("BusinessConditionVO_WrittenShare1");
                                                        var oldValue = NonProportionalShare.OLD_WrittenShare1;
                                                         if($page.businessCalculator.change100ShareAlterinfo(oldValue,newValue)){
                                                           NonProportionalShare.OLD_WrittenShare1 = newValue;
                                                         }
                                                      }
                                                    }
                                                },
                                                BusinessConditionVO_SignedShares1: {
                                                    label: "Signed Shares",
                                                    base : $helper.basePercentage(2),
                                                    evt : {
                                                      blur : function(){
                                                        var newValue = this.getModel().get("BusinessConditionVO_SignedShares1");
                                                        var oldValue = NonProportionalShare.OLD_SignedShares1;
                                                         if($page.businessCalculator.change100ShareAlterinfo(oldValue,newValue)){
                                                           NonProportionalShare.OLD_SignedShares1 = newValue;
                                                         }
                                                      }
                                                    }
                                                },
                                                BusinessConditionVO_Comments1: {
                                                    label: "Remarks",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text
                                                    }
                                                }
                                            }
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                    CoShareCatePanel2: {
                                        label: "From Ceded",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_WrittenShare2: {
                                                    label: "Written Shares",
                                                    base : $helper.basePercentage(2)
                                                },
                                                BusinessConditionVO_SignedShares2: {
                                                    label: "Signed Shares",
                                                    base : $helper.basePercentage(2)
                                                },
                                                BusinessConditionVO_Comments2: {
                                                    label: "Remarks",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text
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
                                                right: [
                                                    {
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
                                                    }
                                                ]
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
            if (this.getModel().get('BusinessConditionVO_OperateType') == 0 || this.getModel().get('BusinessConditionVO_OperateType') == 5 || (this.getModel().get('BusinessConditionVO_OperateType') == 3 && (this.getModel().get('EndoType') == 3) && this.getModel().get('HasInforce'))) {
                return <NForm model={this.getModel()} layout={this.state.editLayout} view={true}/>;
            }
            return <NForm model={this.getModel()} layout={this.state.editLayout}/>;
        }
    }));
    context.NonProportionalShare = NonProportionalShare;
    NFormCell.registerComponentRenderer('NonProportionalShare', function (model, layout, direction) {
      NonProportionalShare.OLD_WrittenShare1 = model.get("BusinessConditionVO_WrittenShare1");
      NonProportionalShare.OLD_SignedShares1 = model.get("BusinessConditionVO_SignedShares1");
        return <NonProportionalShare model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.NonProportionalShare = {type: 'NonProportionalShare', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
