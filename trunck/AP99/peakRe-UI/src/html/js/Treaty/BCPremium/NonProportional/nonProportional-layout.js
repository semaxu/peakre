/*
 * Create By elvira.du 10/20/2015
 */
(function (context) {
    var $page = $pt.getService(context, "$page");
    var codes = $pt.getService($page, 'codes');
    var supiDefineType = $pt.createCodeTable([]);
    var supiRevisedDefineType = $pt.createCodeTable([]);
    var supiAdjustDefineType = $pt.createCodeTable([]);
    var premiumType = $pt.createCodeTable([{id:'1',text:'Flat premium'},{id:'2',text:'Fixed rate'},{id:'3',text:'Swing rate'}]);
    var preminumDefined = $pt.createCodeTable([{id:"1",text : "Amount"}]);
    var baseLayout = {
        _sections : {
            limit : {
                label : "Premium",
                style: 'primary-darken',
                pos : {
                    width : 12
                },
                collapsible : true,
                expanded : true
            }
        }
    };

    var estimatedSUPIV = {
        estimatedSUPIPanel : {
            label : "Estimated SUPI",
            comp : {
                type : $pt.ComponentConstants.Panel,
                editLayout : {
                    supiDefined : {
                        label : "SUPI defined in",
                        comp : {
                            type : $pt.ComponentConstants.Select,
                            data : supiDefineType
                        }
                    },
                    estimatedSUPI : {
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            addable : true,
                            removable : true,
                            searchable : false,
                            addClick:function(model, item, layout){
                                model.add("estimatedSUPI", item.getCurrentModel());
                            },
                            columns : [
                                {
                                    title: "Currency",
                                    data: "currency",
                                    inline: "select",
                                    codes :  codes.Currency,
                                    width : "47%"
                                },
                                {
                                    title : "Amount",
                                    data : "amount",
                                    inline : "number",
                                    width : "46%"
                                }
                            ]
                        },
                        pos : {
                            width : 12
                        }
                    }
                },
            },
            pos : {
                width : 12,
                section : "limit"
            }
        }
    };
    var revisedSUPIV = {
        revisedSUPIPanel : {
            label : "Revised SUPI",
            comp : {
                type : $pt.ComponentConstants.Panel,
                editLayout : {
                    supiRevisionDefine : {
                        label : "SUPI Revision Defined in",
                        comp : {
                            type : $pt.ComponentConstants.Select,
                            codes : supiRevisedDefineType
                        }
                    },
                    note : {
                        comp : {
                            type : $pt.ComponentConstants.Label
                        },
                        pos : {
                            width : 8
                        }
                    },
                    revisedSUPI : {
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            addable : true,
                            removable : true,
                            searchable : false,
                            addClick:function(model, item, layout){
                                model.add("revisedSUPI", item.getCurrentModel());
                            },
                            columns : [
                                {
                                    title : "Date of Revision",
                                    data : "dateOfRevision",
                                    inline : "date",
                                    width : "31%"
                                },
                                {
                                    title: "Currency",
                                    data: "currency",
                                    inline: "select",
                                    codes :  codes.Currency,
                                    width : "31%"
                                },
                                {
                                    title : "Amount",
                                    data : "amount",
                                    inline : "number",
                                    width : "31%"
                                }
                            ]
                        },
                        pos : {
                            width : 12
                        }
                    }
                }
            },
            pos : {
                width : 12,
                section : "limit"
            }
        }
    };
    var adjustedSUPIV = {
        adjustedSUPIPanel : {
            label : "Adjusted SUPI",
            comp : {
                type : $pt.ComponentConstants.Panel,
                editLayout : {
                    supiRevisionDefine : {
                        label : "Adjusted SUPI Defined in",
                        comp : {
                            type : $pt.ComponentConstants.Select,
                            data : supiAdjustDefineType
                        }
                    },
                    adjustedSUPI : {
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            addable : true,
                            removable : true,
                            searchable : false,
                            addClick:function(model, item, layout){
                                model.add("adjustedSUPI", item.getCurrentModel());
                            },
                            columns : [
                                {
                                    title : "Date of Adjustment",
                                    data : "dateOfRevision",
                                    inline : "date",
                                    width : "31%"
                                },
                                {
                                    title: "Currency",
                                    data: "currency",
                                    inline: "select",
                                    codes :  codes.Currency,
                                    width : "31%"
                                },
                                {
                                    title : "Amount",
                                    data : "amount",
                                    inline : "number",
                                    width : "31%"
                                }
                            ]
                        },
                        pos : {
                            width : 12
                        }
                    }
                }
            },
            pos : {
                width : 12,
                section : "limit"
            }
        }
    };
    var premiumTypeV = {
        premiumTypePanel : {
            label : "Premium Type",
            comp : {
                type : $pt.ComponentConstants.Panel,
                editLayout : {
                    premiumType : {
                        comp : {
                            type : $pt.ComponentConstants.Radio,
                            data : premiumType,
                            labelWidth : 1
                        },
                        pos : {
                            width : 6
                        }
                    },
                    floatPremium : {
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            addable : true,
                            removable : true,
                            searchable : false,
                            addClick:function(model, item, layout){
                                model.add("floatPremium", item.getCurrentModel());
                            },
                            visible : {when: function(model) {return model.get('premiumType') == '1';}, depends: 'premiumType'},
                            columns : [
                                {
                                    title: "Currency",
                                    data: "currency",
                                    inline: "select",
                                    codes :  codes.Currency,
                                    width : "31%"
                                },
                                {
                                    title : "100%",
                                    data : "percent",
                                    inline : "percentage",
                                    width : "31%"
                                },
                                {
                                    title : "Our Share",
                                    data : "ourShare",
                                    inline : "number",
                                    width : "31%"
                                }
                            ]
                        },
                        pos : {
                            width : 12
                        }
                    },
                    fixedRate : {
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            addable : true,
                            removable : true,
                            searchable : false,
                            addClick:function(model, item, layout){
                                model.add("fixedRate", item.getCurrentModel());
                            },
                            visible : {when: function(model) {return model.get('premiumType') == '2';}, depends: 'premiumType'},
                            columns : [
                                {
                                    title: "Currency",
                                    data: "currency",
                                    inline: "select",
                                    codes :  codes.Currency,
                                    width : "23%"
                                },
                                {
                                    title : "Premium Rate",
                                    data : "premiumRate",
                                    inline : "percentage",
                                    width : "23%"
                                },
                                {
                                    title : "100%",
                                    data : "percent",
                                    inline : "percentage",
                                    width : "23%"
                                },
                                {
                                    title : "Our Share",
                                    data : "ourShare",
                                    inline : "number",
                                    width : "23%"
                                }
                            ]
                        },
                        pos : {
                            width : 12
                        }
                    },
                    swingRate : {
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            addable : true,
                            removable : true,
                            searchable : false,
                            addClick:function(model, item, layout){
                                model.add("swingRate", item.getCurrentModel());
                            },
                            visible : {when: function(model) {return model.get('premiumType') == '3';}, depends: 'premiumType'},
                            columns : [
                                {
                                    title: "Currency",
                                    data: "currency",
                                    inline: "select",
                                    codes :  codes.Currency,
                                    width : "18%"
                                },
                                {
                                    title : "LR From",
                                    data : "lrFrom",
                                    inline : "percentage",
                                    width : "18%"
                                },
                                {
                                    title : "LR To",
                                    data : "lrTo",
                                    inline : "percentage",
                                    width : "18%"
                                },
                                {
                                    title : "PR From",
                                    data : "prTo",
                                    inline : "percentage",
                                    width : "18%"
                                },
                                {
                                    title : "PR To",
                                    data : "prTo",
                                    inline : "percentage",
                                    width : "18%"
                                },
                                {
                                    title : "Prov. R",
                                    data : "provR",
                                    inline : "percentage",
                                    width : "18%"
                                },
                                {
                                    title : "Prov AMT",
                                    data : "provAMT",
                                    inline : "number",
                                    width : "18%"
                                }
                            ]
                        },
                        pos : {
                            width : 12
                        }
                    },
                }
            },
            pos : {
                width : 12,
                section : "limit"
            }
        }
    };
    var minimumPremiumV = {
        minimumPremium : {
            label : "Minimum Premium",
            comp : {
                type : $pt.ComponentConstants.Table,
                addable : true,
                removable : true,
                searchable : false,
                addClick:function(model, item, layout){
                    model.add("minimumPremium", item.getCurrentModel());
                },
                columns : [
                    {
                        title: "Currency",
                        data: "currency",
                        inline: "select",
                        codes :  codes.Currency,
                        width : "23%"
                    },
                    {
                        title : "Defined In",
                        data : "definedIn",
                        inline : "select",
                        codes : preminumDefined,
                        width : "23%"
                    },
                    {
                        title : "100%",
                        data : "percent",
                        inline : "percentage",
                        width : "23%"
                    },
                    {
                        title: "Our Share",
                        data: "ourShare",
                        inline: "number",
                        width : "23%"
                    }
                ]
            },
            pos : {
                width : 12,
                section : "limit"
            }
        }
    };
    var depositPremiumV = {
        depositPremium : {
            label : "Deposit Premium",
            comp : {
                type : $pt.ComponentConstants.Table,
                addable : true,
                removable : true,
                searchable : false,
                addClick:function(model, item, layout){
                    model.add("depositPremium", item.getCurrentModel());
                },
                columns : [
                    {
                        title: "Currency",
                        data: "currency",
                        inline: "select",
                        codes :  codes.Currency,
                        width : "23%"
                    },
                    {
                        title : "Defined In",
                        data : "definedIn",
                        inline : "select",
                        codes : preminumDefined,
                        width : "23%"
                    },
                    {
                        title : "100%",
                        data : "percent",
                        inline : "percentage",
                        width : "23%"
                    },
                    {
                        title: "Our Share",
                        data: "ourShare",
                        inline: "number",
                        width : "23%"
                    }
                ]
            },
            pos : {
                width : 12,
                section : "limit"
            }
        },
    };
    var depositScheduleV = {
        depositSchedulePanel : {
            label : "Deposit Schedule",
            comp : {
                type : $pt.ComponentConstants.Panel,
                editLayout : {
                    installments : {
                        label : "# of Installments",
                        comp : {
                            type : $pt.ComponentConstants.Text
                        }
                    },
                    depositSchedule : {
                        pos : {width : 12},
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            addable : true,
                            removable : true,
                            searchable : false,
                            addClick:function(model, item, layout){
                                model.add("depositSchedule", item.getCurrentModel());
                            },
                            columns : [
                                {
                                    title: "Installment No.",
                                    data: "installmentNo",
                                    inline: "text",
                                    width : "23%"
                                },
                                {
                                    title : "Due Date",
                                    data : "dueDate",
                                    inline : "date",
                                    width : "23%"
                                },
                                {
                                    title: "Currency",
                                    data: "currency",
                                    inline: "select",
                                    codes :  codes.Currency,
                                    width : "23%"
                                },
                                {
                                    title: "Amount",
                                    data: "amount",
                                    inline: "number",
                                    width : "23%"
                                }
                            ]
                        },
                    }
                }
            },
            pos : {
                width : 12,
                section : "limit"
            }
        }
    };
    var buttonsV = {
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
    };

    $page.layout = $.extend(true,{},baseLayout,estimatedSUPIV,revisedSUPIV,adjustedSUPIV,premiumTypeV,minimumPremiumV,depositPremiumV,depositScheduleV,buttonsV);

}(typeof window !== "undefined" ? window : this));