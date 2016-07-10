(function(context) {
    var $page = $pt.getService(context,'$page');
    var $code = $pt.getService($page,'codes');
    var currency = $pt.createCodeTable([
        {id:'1', text : "USD US Dollar"},
        {id:'2', text : "CNY CN Yuan"},
        {id:'3', text : "HKD HK Dollar"}
    ]);

    var sectionData = [
        {
            title : "Section No",
            data : "sectionNo",
            width : "8%"
        }, {
            title : "Section Type",
            data : "sectionType",
            width : "13%"
        }, {
            title : "Section Name",
            data : "sectionName",
            width : "15%"
        }, {
            title : "Main Currency",
            data : "mainCurrency",
            width : "15%",
            codes : currency
        }, {
            title : "Our share",
            data : "ourShare",
            width : "12%"
        }, {
            title : "Premium",
            data : "premium",
            width : "15%",
            render : function(row){return $helper.formatNumberForLabel(row.premium,2);}
        }, {
            title : "Deductions",
            data : "deductions",
            width : "15%",
            render : function(row){return $helper.formatNumberForLabel(row.premium,2);}
        }
    ];

    var logData = [
        {
            title : "Program Status",
            data : "programStatus",
            width : "25%"
        }, {
            title : "Update Time",
            data : "updateTime",
            width : "25%"
        }, {
            title : "Update By",
            data : "updateBy",
            width : "25%"
        }, {
            title : "Remarks",
            data : "remarks",
            width : "25%"
        }
    ];

    $page.layout = {
        _sections : {
            moreContractInfo : {
                label : "More Program Info",
                style : 'primary-darken',
                layout : {
                    coverageDefinition : {
                        label : "Coverage Definition",
                        comp : {
                            type : $pt.ComponentConstants.Panel,
                            editLayout : {
                                class : {
                                    label:"Class",
                                    comp : {
                                        type : $pt.ComponentConstants.Button,
                                        style:"link",
                                        click:function(model){
                                            $page.controller.showDefaultDialog($page.classModel, model, 'classSelected',$page.classDialogLayout,"classes");
                                        }
                                    },
                                    pos : {
                                        row:1
                                    }
                                },
                                classSelected:{
                                    comp:{
                                        type:$pt.ComponentConstants.Text,
                                        enabled:false
                                    },
                                    pos:{
                                        row:1
                                    }
                                },
                                subClass : {
                                    label:"Sub-class",
                                    comp : {
                                        type : $pt.ComponentConstants.Button,
                                        style:"link",
                                        click:function(model){
                                            $page.controller.showDefaultDialog($page.subclassModel, model, 'subClassSelected',$page.subclassDialogLayout,"subclasses");
                                        }
                                    },
                                    pos : {
                                        row:2
                                    }
                                },
                                subClassSelected:{
                                    comp:{
                                        tyep:$pt.ComponentConstants.Text,
                                        enabled:false
                                    },
                                    pos:{
                                        row:2
                                    }
                                },
                                currency : {
                                    label:"Currency",
                                    comp : {
                                        type : $pt.ComponentConstants.Button,
                                        style:"link",
                                        click:function(model){
                                            $page.controller.showDefaultDialog($page.currencyModel, model, 'currencySelected',$page.currencyDialogLayout,"currency");
                                        }
                                    },
                                    pos : {
                                        row:3
                                    }
                                },
                                currencySelected:{
                                    comp:{
                                        tyep:$pt.ComponentConstants.Text,
                                        enabled:false
                                    },
                                    pos:{
                                        row:3
                                    }
                                },
                                uwArea : {
                                    label:"UW Area",
                                    comp : {
                                        type : $pt.ComponentConstants.Button,
                                        style:"link",
                                        click:function(model){

                                        }
                                    },
                                    pos : {
                                        row:4
                                    }
                                },
                                uwAreaSelected:{
                                    comp:{
                                        tyep:$pt.ComponentConstants.Text,
                                        enabled:false
                                    },
                                    pos:{
                                        row:4
                                    }
                                },
                                coveredArea : {
                                    label:"Covered Area",
                                    comp : {
                                        type : $pt.ComponentConstants.Button,
                                        style:"link",
                                        click:function(model){

                                        }
                                    },
                                    pos : {
                                        row:5
                                    }
                                },
                                coveredAreaSelected:{
                                    comp:{
                                        tyep:$pt.ComponentConstants.Text,
                                        enabled:false
                                    },
                                    pos:{
                                        row:5
                                    }
                                },
                                protectionBasis : {
                                    label : "Protection Basis",
                                    comp : {
                                        type : $pt.ComponentConstants.Select,
                                        labelDirection : "horizontal"
                                    },
                                    pos : {
                                        row : 6
                                    }
                                }
                            }
                        },
                        pos : {
                            width : 12
                        }
                    },
                    sectionTable : {
                        label : "Sections",
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            searchable:false,
                            addable : true,
                            removable: true,
                            columns : sectionData,
                            addClick : function (model, rowModel, layout) {
                                window.location.href="section.html?programType="+model.get("programType");
                            },
                            rowOperations : [
                                {
                                    icon : "edit",
                                    click : function (model,row) {
                                        window.location.href="section.html?programType="+model.get("programType");
                                    }
                                }
                            ]
                        },
                        pos : {
                            width : 12
                        }
                    },
                    logTable : {
                        label : "Status Log",
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            searchable : false,
                            sortable : false,
                            columns : logData
                        },
                        pos : {
                            width : 12
                        }
                    },
                    buttonPanel : {
                        comp : {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right : [
                                    {
                                        text : "Exit",
                                        style : "warning"
                                    }, {
                                        text : "Save",
                                        style : "primary"
                                    }
                                ]
                            }
                        },
                        pos : {
                            width : 12
                        }
                    }
                }
            }
        }
    };

    $page.classDialogLayout = {
        classes : {
            comp : {
                type : $pt.ComponentConstants.Tree,
                check : "selected",
                hierarchyCheck : true,
                inactiveSlibing : false,
                root : false
            }
        }
    };

    $page.subclassDialogLayout = {
        subclasses : {
            comp : {
                type : $pt.ComponentConstants.Tree,
                check : "selected",
                hierarchyCheck : true,
                inactiveSlibing : false,
                root : false
            }
        }
    };

    $page.currencyDialogLayout = {
        currency : {
            comp : {
                type : $pt.ComponentConstants.Tree,
                check : "selected",
                hierarchyCheck : true,
                inactiveSlibing : false,
                root : false
            }
        }
    };

    $page.countryDialogLayout = {
        countries : {
            comp : {
                type : $pt.ComponentConstants.Tree,
                check : "selected",
                hierarchyCheck : true,
                inactiveSlibing : false,
                root : false
            }
        }
    };

    $page.coveredAreaDialogLayout = {
        coveredArea : {
            comp : {
                type : $pt.ComponentConstants.Tree,
                check : "selected",
                hierarchyCheck : true,
                inactiveSlibing : false,
                root : false
            }
        }
    };

}(typeof window !== "undefined" ? window : this));