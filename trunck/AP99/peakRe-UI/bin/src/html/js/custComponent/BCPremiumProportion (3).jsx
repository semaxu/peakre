(function (context, $, $pt) {
    var codes = $pt.getService(context, "codes");
    var $page= $pt.getService(context,'$page');
    var BCPremiumProportion = React.createClass($pt.defineCellComponent({
        statics: {},
        propTypes: {
            // model
            model: React.PropTypes.object,
            // CellLayout
            layout: React.PropTypes.object
        },

        getDefaultProps: function () {
          var _this = this;
          var autoCalculation = [
            {id:'Amount',listener:function(evt){return $page.businessCalculator.calculationPremiumAmount("OurWrittenShare","OurSignedShare",evt)}},
            {id:'OurWrittenShare',listener:function(evt){return $page.businessCalculator.calculationWrittenPremium("Amount","OurSignedShare",evt)}},
            {id:'OurSignedShare',listener:function(evt){return $page.businessCalculator.calculationSignedPremium("Amount","OurWrittenShare",evt)}}
          ];
            return {
                defaultOptions: {
                    editLimitLayout: {
                        _sections: {
                            premium : {
                                label: "Premium",
                                style: 'primary-darken',
                                collapsible : true,
                                expanded : false,
                                pos: {
                                    width: 12
                                },
                                layout : {
                                    msg : {
                                        label : "NOTE: Premium revision will cause changes in Pricing & Estimates.",
                                        comp : {
                                            type : $pt.ComponentConstants.Label,
                                            textFromModel: false,
                                            style: "danger",
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO_OperateType') == '3' && (model.get('EndoType') == 1 || model.get('EndoType') == 2)
                                                    && model.get('HasInforce');
                                                },
                                                depends: 'BusinessConditionVO_OperateType'
                                            }
                                        },
                                        pos : {width : 12},
                                        data: null
                                    },
                                    epiLayout : {
                                        label : "EPI",
                                        comp : {
                                            type : $pt.ComponentConstants.Panel,
                                            editLayout : {
                                                BusinessConditionVO_EpiType : {
                                                    label : "EPI Type",
                                                    comp : {
                                                        type : $pt.ComponentConstants.Select,
                                                        data : $page.codes.EPIType
                                                    }
                                                },
                                                BusinessConditionVO_DefinedIn:{
                                                    label : "EPI Defined in",
                                                    comp : {
                                                        type : $pt.ComponentConstants.Select,
                                                        data : $page.codes.PremiumDefinedIn

                                                    }
                                                },
                                                BusinessConditionVO_EpiList : {
                                                    pos : {width : 12},
                                                    comp : {
                                                        type : $pt.ComponentConstants.Table,
                                                        rowListener: autoCalculation,
                                                        addable : true,
                                                        removable : true,
                                                        sortable : false,
                                                        searchable : false,
                                                        addClick:function(model){
                                                            model.add("BusinessConditionVO_EpiList",  $.extend(true, {}, $page.model.getEpiListTemplate()));
                                                        },
                                                        canRemove: function (model, item) {
                                                            if (item.ItemId && item.ItemId != 0) {
                                                                model.add("BusinessConditionVO_DeletePremiumList", item);
                                                            }
                                                            this.getModel().remove(this.getDataId(), item);
                                                        },
                                                        columns : [
                                                            {
                                                                title: "Currency",
                                                                data: "Currency",
                                                                inline: "select",
                                                                codes :  $page.codes.Currency,
                                                                width : '25%'
                                                            },
                                                            {
                                                                title : "Amount 100%",
                                                                data : "Amount",
                                                                inline : $helper.registerInlineAmount("amount",2),
                                                                width : '25%'
                                                            },
                                                            {
                                                                title : "Amount Our Written Share",
                                                                data : "OurWrittenShare",
                                                                inline : $helper.registerInlineAmount("amount",2),
                                                                width : '25%'
                                                            },
                                                            {
                                                                title : "Amount Our Signed Share",
                                                                data : "OurSignedShare",
                                                                inline : $helper.registerInlineAmount("amount",2),
                                                               width : '25%'                                                            }

                                                        ]
                                                    },
                                                    css:{
                                                        comp: "inline-editor",
                                                        cell: "title-align"
                                                    }
                                                }
                                            }
                                        },
                                        pos : {width : 12}
                                    },
                                    terrorismPremiumPanel:{
                                        label : "Terrorism Premium",
                                        comp:{
                                            type:$pt.ComponentConstants.Panel,
                                            editLayout:{
                                                BusinessConditionVO_TerrorismList : {
                                                    pos : {width : 12},
                                                    comp : {
                                                        type : $pt.ComponentConstants.Table,
                                                        rowListener:autoCalculation,
                                                        addable : true,
                                                        removable : true,
                                                        searchable : false,
                                                        sortable : false,
                                                        addClick:function(model){
                                                            model.add("BusinessConditionVO_TerrorismList", $.extend(true, {}, $page.model.getTerrorismListTemplate()));
                                                        },
                                                        canRemove: function (model, item) {
                                                            if (item.ItemId && item.ItemId != 0) {
                                                                model.add("BusinessConditionVO_DeletePremiumList", item);
                                                            }
                                                            this.getModel().remove(this.getDataId(), item);
                                                        },
                                                        columns : [
                                                            {
                                                                title: "Currency",
                                                                data: "Currency",
                                                                inline: "select",
                                                                codes :  $page.codes.Currency,
                                                                width : '25%'
                                                            },
                                                            {
                                                                title : "Amount 100%",
                                                                data : "Amount",
                                                                inline : $helper.registerInlineAmount("Amount",2),
                                                                width : '25%'
                                                            },
                                                            {
                                                                title : "Amount Our Written Share",
                                                                data : "OurWrittenShare",
                                                                inline : $helper.registerInlineAmount("OurWrittenShare",2),
                                                                width : '25%'
                                                            },
                                                            {
                                                                title : "Amount Our Signed Share",
                                                                data : "OurSignedShare",
                                                                inline : $helper.registerInlineAmount("OurSignedShare",2),
                                                               width : '25%'
                                                            }
                                                        ]
                                                    },
                                                    css:{
                                                        comp: "inline-editor",
                                                        cell: "title-align"
                                                    }
                                                }
                                            }
                                        },
                                        pos:{
                                            width:12
                                        }
                                    },
                                    Remark: {
                                        label: "Remarks",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_PremiumRemark: {
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
                                                    click:function(){
                                                        $page.controller.save(true);
                                                    }
                                                }]
                                            }
                                        },
                                        pos:{
                                            width:12,
                                            section : "limit"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
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
            if (this.getModel().get('BusinessConditionVO_OperateType') == 0 || this.getModel().get('BusinessConditionVO_OperateType') == 5 || (this.getModel().get('BusinessConditionVO_OperateType') == 3 && this.getModel().get('EndoType') == 3 && this.getModel().get('HasInforce'))) {
                return <NForm model={this.getModel()} layout={this.state.editLimitLayout} view={true}/>;
            }
            return <NForm model={this.getModel()} layout={this.state.editLimitLayout}/>;
        }
    }));
    context.BCPremiumProportion = BCPremiumProportion;
    NFormCell.registerComponentRenderer('BCPremiumProportion', function (model, layout, direction) {
        return <BCPremiumProportion model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.BCPremiumProportion = {type: 'BCPremiumProportion', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
