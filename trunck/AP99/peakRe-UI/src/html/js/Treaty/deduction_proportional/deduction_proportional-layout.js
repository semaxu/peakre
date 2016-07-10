(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            claimSec: {
                label: "Deductions",
                style:'primary-darken',
                pos:{
                    width:12
                },
                layout: {
                    RICommission: {
                        label: "RI Commission",
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        css: {
                            cell: 'date-range-link'
                        },
                        pos: {
                            row: 1,
                            width: 2
                        }
                    },
                    fixed1:{
                        label:"Fixed",
                        comp:{
                            type:{
                                labelDirection: "horizontal",
                                enabled:true
                            }
                        },
                        pos:{
                            row:1,
                            width:4
                        }
                    },
                    profitCommission: {
                        label: "Profit Commission",
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        css: {
                            cell: 'date-range-link'
                        },
                        pos: {
                            row: 1,
                            width: 2
                        }
                    },
                    fixed2:{
                        label:"Fixed",
                        comp:{
                            type:{
                                labelDirection: "horizontal",
                                enabled:true
                            }
                        },
                        pos:{
                            row:1,
                            width:4
                        }
                    },
                    blank1: {
                        label: "",
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        css: {
                            cell: 'date-range-link'
                        },
                        pos: {
                            row: 2,
                            width: 2
                        }
                    },
                    provisional1:{
                        label:"SS Provisional",
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
                    blank2: {
                        label: "",
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        css: {
                            cell: 'date-range-link'
                        },
                        pos: {
                            row: 2,
                            width: 2
                        }
                    },
                    provisional2:{
                        label:"SS Provisional",
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
                    blank3: {
                        label: "",
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        css: {
                            cell: 'date-range-link'
                        },
                        pos: {
                            row: 3,
                            width: 2
                        }
                    },
                    viewContractButton1:{
                        label:"SS Commission Detail",
                        comp:{
                            type:$pt.ComponentConstants.Button,
                        style:"link",
                        click:function(){

                        }
                    },
                    pos:{
                        row:3,
                        width: 4
                    },
                    css:{
                        comp: 'pull-right'
                    }
                },
                    blank4: {
                        label: "",
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        css: {
                            cell: 'date-range-link'
                        },
                        pos: {
                            row: 3,
                            width: 2
                        }
                    },
                    viewProgramButton2:{
                        label:"Profit Commission Detail",
                        comp:{
                            type:$pt.ComponentConstants.Button,
                            style:"link",
                            click:function(){

                            }
                        },
                        pos:{
                            row:3,
                            width: 4
                        },
                        css:{
                            comp: 'pull-right'
                        }
                    },
                    nonlinear : {
                        label:"Other Deductions",
                        pos:{
                            width:12,
                            row:4
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
                                        editable: true,
                                        removable: true,
                                        addable: true,
                                        //criteria : "paginationCriteria",
                                        columns: [
                                            {title: "Deduction Item", data: "item" },
                                            {title: "In Percentage", data: "percentage",inline:"text"},
                                            {title: "In Amount", data: "amount",inline:"text"},
                                            {title: "Remarks", data: "remarks",inline:"text"}
                                        ],
                                        addClick:function(model, item, layout){
                                            // alert("addClick");
                                            // model.add("specificReinstatementData",
                                            //   {reinstatementNum:"1",rate:0.1,time:"N",amount:"Y"}
                                            // );
                                            // console.log(item);
                                            model.add("table", item.getCurrentModel());
                                            // console.log(model.get("specificReinstatementData"));
                                            // this.forceUpdate();
                                        }

                                    },
                                    pos: {
                                        width: 8,
                                        row: 1

                                    }
                                }

                            }
                        }
                    },
                    remarkPanel:{
                        label:"Remarks",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                                remark:{
                                    comp:{
                                        type:{
                                            type:$pt.ComponentConstants.TextArea,
                                                label:false},
                                             lines:3
                                    },
                                              pos:{
                                                  width:12
                                                   }
                                            }
                                                 }
                                         },
                           pos:{
                               width:12
                              }}
                      ,
                    bottomButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Exit",
                                        style: "warning"
                                    }, {
                                        text: "Save",
                                        style: "primary"
                                    }
                                ]
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

}(typeof window !== "undefined" ? window : this));