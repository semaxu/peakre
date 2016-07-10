(function (context, $, $pt) {
    var $page = $pt.getService(context, "$page");

    var SupiRevisionSection = React.createClass($pt.defineCellComponent({
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
                comp:{
                    type:$pt.ComponentConstants.Panel,
                    editLayout:{
                        supiSection:{
                            label: 'section Panel',
                            comp:{
                                type:$pt.ComponentConstants.ArrayPanel,
                                itemTitle:{
                                    when:function(item){
                                        return item.get("sectionName");
                                    }
                                },
                                editLayout:{
                                    supiDefined:{
                                        label:"SUPI Defined In",
                                        comp:{
                                            type:$pt.ComponentConstants.Select
                                        },
                                        pos:{
                                            row:1,
                                            width:6
                                        }
                                    },
                                    supiRevision:{
                                        label:"SUPI Revision",
                                        comp:{
                                            type:$pt.ComponentConstants.Table,
                                            searchable:false,
                                            sortable:false,
                                            addable:true,
                                            columns:[
                                                {
                                                    title:"Date of Revision",
                                                    data:"date",
                                                    inline:"date",
                                                    width:"20%"
                                                },{
                                                    title:"By",
                                                    data:"by",
                                                    inline:"text",
                                                    width:"20%"
                                                },{
                                                    title:"Currency",
                                                    data:"currency",
                                                    codes:$page.codes.Currency,
                                                    inline:"select",
                                                    width:"20%"
                                                },{
                                                    title:"Amount",
                                                    data:"amount",
                                                    inline:"text",
                                                    width:"20%"
                                                }
                                            ],
                                            addClick:function(model,item,layout){
                                                model.add("supiRevision",item.getCurrentModel());
                                            }
                                        },
                                        pos:{
                                            row:2,
                                            width:12
                                        }
                                    },
                                    actionPanel:{
                                        comp:{
                                            type:$pt.ComponentConstants.ButtonFooter,
                                            buttonLayout:{
                                                right:[
                                                    {
                                                        text:"Exit",
                                                        style:"warning"
                                                    },{
                                                        text:"Submit",
                                                        style:"primary"
                                                    }
                                                ]
                                            }
                                        },
                                        pos:{
                                            row:3,
                                            width:12
                                        }
                                    },
                                    history:{
                                        label:"History",
                                        comp:{
                                            type:$pt.ComponentConstants.Table,
                                            searchable:false,
                                            sortable:false,
                                            columns:[
                                                {
                                                    title:"Date",
                                                    data:"date",
                                                    width:"20%"
                                                },{
                                                    title:"By",
                                                    data:"by",
                                                    width:"20%"
                                                },{
                                                    title:"Currency",
                                                    data:"currency",
                                                    width:"20%"
                                                },{
                                                    title:"Amount",
                                                    data:"amount",
                                                    width:"20%"
                                                }
                                            ]
                                        },
                                        pos:{
                                            row:4,
                                            width:12
                                        }
                                    }
                                }
                            },
                            pos:{
                                width:12
                            }
                        }
                    }
                },
                pos:{
                    width:12
                }
            }
        },
        getInitialState: function () {
            return null;
            //return {
            //    showChecked: true
            //};
        },

        render: function () {

            var layout = $pt.createCellLayout(this.getDataId(), this.getPanelLayout());
            //console.log("elvira");
            //console.log(this.getModel());
            //this.getModel().addPostChangeListener( "Amount", function(evt){
            //    console.log(evt.model);
            //    console.info(evt.model.get("Amount") + "|" + evt.model.get("ConvertedAmount"));
            //});
            //this.getModel().addPostChangeListener( "ConvertedAmount", function(evt){
            //    console.info(evt.model.get("Amount") + "|" + evt.model.get("ConvertedAmount"));
            //    /*if(evt.model.get("linkName") != evt.model.get("linkedNameSelect")){
            //     evt.model.set("linkName",evt.model.get("linkedNameSelect"));
            //     }*/
            //});
            return <NPanel model={this.getModel()} layout={layout} direction={this.props.direction}/>
        }
    }));
    context.SupiRevisionSection = SupiRevisionSection;
    NFormCell.registerComponentRenderer('SupiRevisionSection', function (model, layout, direction) {
        return <SupiRevisionSection model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.SupiRevisionSection = {type: 'SupiRevisionSection', label: false, popover: false};

}(typeof window !== "undefined" ? window : this, jQuery, $pt));