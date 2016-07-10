(function (context) {
    var $page = $pt.getService(context, '$page');
    NTable.registerInlineEditor("YearsForCarried", {
        comp: {
            type:{label:false},
            enabled: {
                when: function (model) {
                    if (model.get('Yn') == '1' && model.get('Extinction') == '1') {
                        model.set('Years', null);
                        return false;
                    }
                    else {
                        return true;
                    }
                },
                depends: ['Yn', 'Extinction']
            }
        },
        base:$helper.baseNumber()
    });

    $page.layout = {
        _sections: {
            ClaimSec: {
                label: "SS Commission Detail",
                style: 'primary-darken',
                layout: {
                    LossRatioDefinition: {
                        label: "Loss Ratio Definition",
                        pos: {
                            width: 12,
                            row: 1
                        },
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                LossIncludeIBNR: {
                                    label: "Loss include IBNR",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Boolean,
                                        labelWidth: 8
                                    },
                                    pos: {
                                        width: 3,
                                        row: 1
                                    }
                                }
                            }
                        }
                    },
                    CarriedForwardPanel: {
                        label: "Carried Forward",
                        pos: {
                            width: 12
                        },
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                DeductionsCarried: {
                                    label: "",
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        //indexable : true,
                                        sortable: false,
                                        //pageable:true,
                                        //countPerPage : 5,
                                        searchable: false,
                                        editable: false,
                                        removable: false,
                                        addable: false,
                                        //criteria : "paginationCriteria",
                                        columns: [
                                            {title: "", data: "Name"},
                                            {title: "Y/N", data: "Yn", inline: "select", codes: $page.codes.Boolean},
                                            // {title: "To Extinction", data: "Extinction", inline: "text"},
                                            {
                                                title: "To Extinction",
                                                data: "Extinction",
                                                inline: "select",
                                                codes: $page.codes.Boolean
                                            },
                                            {
                                                title: "Number Of Years",
                                                data: "Years",
                                                inline: "YearsForCarried"
                                            }
                                        ]
                                    },
                                    css:{
                                        comp: "currency-align-right-text",
                                        cell: "title-align"
                                    },
                                    pos: {
                                        width: 8
                                    }
                                }
                            }
                        }
                    },
                    CalculationFrequency: {
                        label: "Calculation Frequency",
                        pos: {
                            width: 12
                        },
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                FirstCal: {
                                    label: "First calculation after",
                                    base:$helper.baseYear(),
                                    pos: {
                                        width: 4,
                                        row: 1
                                    }
                                },
                                SubsequentCalc: {
                                    label: "Subseq. calc. freq.",
                                    base:$helper.baseYear(),
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                }
                            }
                        }
                    },
                    linear: {
                        label: "Sliding Scale",
                        pos: {
                            width: 12
                        },
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                MinimumRISs: {
                                    label: "Minimum RI Comm.",
                                    base : $helper.basePercentage(2),
                                    pos: {
                                        width: 4,
                                        row: 1
                                    }
                                },
                                MaximumRISs: {
                                    label: "Maximum RI Comm.",
                                    base : $helper.basePercentage(2),
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                MinimumLossSs: {
                                    label: "Minimum Loss Ratio",
                                    base : $helper.basePercentage(2),
                                    pos: {
                                        width: 4,
                                        row: 2
                                    }
                                },
                                MaximumLossSs: {
                                    label: "Maximum Loss Ratio",
                                    base : $helper.basePercentage(2),
                                    pos: {
                                        row: 2,
                                        width: 4
                                    }
                                }
                            }
                        }
                    },
                    nonlinear: {
                        label: "Attachment",
                        pos: {
                            width: 12
                        },
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                Blank: {
                                    label: "",
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
                                        width: 2
                                    }
                                },
                                DeductionsAttach: {
                                    label: "",
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        sortable:true,
                                        searchable: false,
                                        removable: true,
                                        addable: true,
                                        columns: [
                                            {title: "LR % From", data: "LrFrom",  inline:$helper.registerInlinePercentage("LrFrom",2),sort: "number"},
                                            {title: "LR % To", data: "LrTo",  inline:$helper.registerInlinePercentage("LrTo",2),sort: "number"},
                                            {title: "Commission %", data: "Commission",  inline:$helper.registerInlinePercentage("Commission",2),sort: "number"}
                                        ],
                                        addClick: function (model, item, layout) {
                                            model.add("DeductionsAttach", item.getCurrentModel());
                                        },
                                        canRemove: function (model, item) {
                                            if (item.AttachmentId && item.AttachmentId != 0) {
                                                model.add("DeleteDeductionsAttach", item);
                                            }
                                            this.getModel().remove(this.getDataId(), item);
                                        }
                                    },
                                    css:{
                                        comp: "inline-editor",
                                        cell: "title-align"
                                    },
                                    pos: {
                                        width: 10,
                                        row: 1
                                    }
                                },
                                buttoms: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [{
                                                text: "Upload Table/Attach Table",
                                                style: "primary",
                                                visible: {
                                                    when: function (model) {
                                                        return model.get('OperateType') != '0' && model.get('OperateType') != '5' && !(model.get('OperateType') == '3' && model.get('EndoType') == '3');
                                                    }
                                                },
                                                click: function (model) {
                                                    if ($page.controller.save(false)) {
                                                        var myWindow = $pt.dataImport(5,model.get("CommSlidingDetailId"));
                                                        var loop = setInterval(function(){
                                                   if(myWindow.closed){
                                                   clearInterval(loop);
                                                            window.location.href = window.location.href;
                                                       }
                                                        },1000);
                                                    }
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
                    },
                    bottomButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Exit",
                                    style: "warning",
                                    click: function () {
                                        window.close();
                                    }
                                }, {
                                    text: "Save",
                                    style: "primary",
                                    visible: {
                                        when: function (model) {
                                            return model.get('OperateType') != '0' && model.get('OperateType') != '5' && !(model.get('OperateType') == '3' && model.get('EndoType') == '3');
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
}(typeof window !== "undefined" ? window : this));
