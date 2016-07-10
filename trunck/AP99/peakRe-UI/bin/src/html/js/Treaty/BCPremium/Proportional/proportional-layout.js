/*
 * Create By elvira.du 10/20/2015
 */
(function (context) {
    var $page = $pt.getService(context, "$page");
    var codes = $pt.getService(context, "$codes");
    var epiTypeCode = $pt.createCodeTable([{id: '1', text: "QS"}, {id: '2', text: "Surplus"}]);
    var epiDefinedInCode = $pt.createCodeTable([{id: 'USD', text: "PML"}, {id: 'CNY', text: "Sum Insured"}]);
    var currencyType = $pt.createCodeTable([{id: 'USD', text: "US Dollar"}, {id: 'CNY', text: "China Yuan"}]);
    var eventType = $pt.createCodeTable([{id: '1', text: "Winter Storm"}]);
    $page.layout = {
        _sections: {
            premium : {
                label: "Premium",
                style: 'primary-darken',
                pos: {
                    width: 12
                },
                collapsible: true,
                expanded: true,
                layout : {
                    epiLayout : {
                        label : "EPI",
                        comp : {
                           type : $pt.ComponentConstants.Panel,
                            editLayout : {
                                epiType : {
                                    label : "EPI Type",
                                    comp : {
                                        type : $pt.ComponentConstants.Select,
                                        data : epiTypeCode,
                                    }
                                },
                                epiDefinedIn : {
                                    label : "EPI Defined in",
                                    comp : {
                                        type : $pt.ComponentConstants.Select,
                                        data : epiDefinedInCode
                                    }
                                },
                                epiList : {
                                    pos : {width : 12},
                                    comp : {
                                        type : $pt.ComponentConstants.Table,
                                        addable : true,
                                        removable : true,
                                        searchable : false,
                                        addClick:function(model, item, layout){
                                            model.add("epiList", item.getCurrentModel());
                                        },
                                        columns : [
                                            {
                                                title: "Currency",
                                                data: "currency",
                                                inline: "select",
                                                codes :  codes.Currency,
                                                width : '31%'
                                            },
                                            {
                                                title : "Amount 100%",
                                                data : "amountPercent",
                                                inline : "percentage",
                                                width : '31%'
                                            },
                                            {
                                                title : "Amount in Share",
                                                data : "amountInShare",
                                                inline : "number",
                                                width : '31%'
                                            }
                                        ]
                                    }
                                }
                            }
                        },
                        pos : {width : 12}
                    },
                    epiRevisionLayout : {
                        label : "EPI Revision",
                        comp : {
                            type : $pt.ComponentConstants.Panel,
                            editLayout : {
                                epiRevisionType : {
                                    label : "EPI Type",
                                    comp : {
                                        type : $pt.ComponentConstants.Select,
                                        data : epiTypeCode
                                    }
                                },
                                epiDefinedIn : {
                                    label : "EPI Defined in",
                                    comp : {
                                        type : $pt.ComponentConstants.Select,
                                        data : epiDefinedInCode,
                                        enabled:false
                                    }
                                },
                                epiRevision : {
                                    pos : {width : 12},
                                    comp : {
                                        type : $pt.ComponentConstants.Table,
                                        addable : true,
                                        removable : true,
                                        searchable : false,
                                        addClick:function(model, item, layout){
                                            model.add("epiRevision", item.getCurrentModel());
                                        },
                                        columns : [
                                            {
                                                title: "Date",
                                                data: "date",
                                                inline: "date",
                                                width : "19%"
                                            },
                                            {
                                                title : "By",
                                                data : "by",
                                                inline : "text",
                                                width : "19%"
                                            },
                                            {
                                                title: "Currency",
                                                data: "currency",
                                                inline: "select",
                                                codes :  codes.Currency,
                                                width : "19%"
                                            },
                                            {
                                                title : "Amount 100%",
                                                data : "amountPercent",
                                                inline : "percentage",
                                                width : "19%"
                                            },
                                            {
                                                title : "Amount in Share",
                                                data : "amountInShare",
                                                inline : "number",
                                                width : "19%"
                                            }
                                        ]
                                    }
                                }
                            }
                        },
                        pos : {width : 12}
                    },
                    terrorismPremium : {
                        label : "Terrorism Premium",
                        pos : {width : 12},
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            addable : true,
                            removable : true,
                            searchable : false,
                            addClick:function(model, item, layout){
                                model.add("terrorismPremium", item.getCurrentModel());
                            },
                            columns : [
                                {
                                    title: "Currency",
                                    data: "currency",
                                    inline: "select",
                                    codes :  codes.Currency,
                                    width : '31%'
                                },
                                {
                                    title : "Amount 100%",
                                    data : "amountPercent",
                                    inline : "percentage",
                                    width : '31%'
                                },
                                {
                                    title : "Amount in Share",
                                    data : "amountInShare",
                                    inline : "number",
                                    width : '31%'
                                }
                            ]
                        }
                    },
                    rightButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Save",
                                    style: "primary",
                                    click:function(){

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
    };
}(typeof window !== "undefined" ? window : this));