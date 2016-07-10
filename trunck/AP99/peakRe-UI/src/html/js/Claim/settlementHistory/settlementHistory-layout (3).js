/**
 * Created by gang.wang on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var paidType = $pt.createCodeTable([{id:"1",text:"offset"}]);


    var Layout = jsface.Class({
        settlementHistoryQuery : function() {
            return {

                label: "Settlement History",
                style: 'primary-darken',
                layout: {
                    searchPanel: {
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {

                                SettlementType: {
                                    label: "Settlement Type",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.SettlementType
                                    },
                                    pos:{
                                        row:1
                                    }
                                },
                                ContractSection: {
                                    label: "Contract Section",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.contractSelection
                                    },
                                    pos:{
                                        row:1
                                    }
                                },
                                underwritingYear: {
                                    label: "Underwriting Year",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.uwYearSelection
                                    },
                                    pos:{
                                        row:1
                                    }
                                },
                                //sectionId: {
                                //    label: "Section",
                                //    comp: {
                                //        type: $pt.ComponentConstants.Select,
                                //        //data: $pt.createCodeTable([{}])
                                //    },
                                //    pos:{
                                //        row:2
                                //    }
                                //},
                                //subSectionId: {
                                //    label: "Sub-section",
                                //    comp: {
                                //        type: $pt.ComponentConstants.Select,
                                //        //data: $pt.createCodeTable([{}])
                                //    },
                                //    pos:{
                                //        row:2
                                //    }
                                //},
                                rightButton: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [{
                                                text: "Search",
                                                style: "primary",
                                                click: function () {
                                                    $page.controller.searchSettlement();

                                                }
                                            }]
                                        }
                                    },
                                    pos:{
                                        row:2,
                                        width:12
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },

                    SettlementItemInfo: {
                        label: "Settlement History Data",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            //indexable : true,
                            sortable: true,
                            searchable: false,
                            //pageable:true,
                            //countPerPage : 5,
                            //criteria : "paginationCriteria",
                            columns: [
                                {title: "Approval Date", data: "UpdateTime", render: function (row) {
                                    return $helper.formatDateForTableView(row.UpdateTime, "DD/MM/YYYY");
                                },width:"10%"},
                                //{title: "Contract ID", data: "ContractCode",width:"10%"},
                                //{title: "UY", data: "UnderwritingYear",width:"7%"},
                                {title: "Settlement No", data: "SettlementName",width:"10%"},
                                {title: "Contract Section", data: "SectionId",codes: $page.contractSelection,width:"25%"},
                                {title: "Settlement Type", data: "SettlementType", codes:$page.codes.SettlementType,width:"10%"},
                                {title: "Original Currency", data: "OriginalCurrency", codes:$page.codes.Currency,width:"10%"},
                                {title: "Original Amount", data: "AmountOc",width:"10%",
                                    //render : function(row){return $helper.formatNumberForLabel(row.AmountOc,2);}
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("AmountOc"), 2);
                                                    }
                                                }
                                            },
                                            pos: {width: 12},
                                            css: {cell: 'currency-align-right'}
                                        }
                                    }
                                },
                                {title: "USD Amount", data: "AmountUsd",width:"10%",
                                    //render : function(row){return $helper.formatNumberForLabel(row.AmountUsd,2);}
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("AmountUsd"), 2);
                                                    }
                                                }
                                            },
                                            pos: {width: 12},
                                            css: {cell: 'currency-align-right'}
                                        }
                                    }
                                },
                                {title:"Transfer to Accounts",data:"PostingFlag",codes: $page.codes.Boolean, width:"5%"},
                                {data: "SettleDetailId", visible: false}
                            ],
                            rowOperations: [{
                                //"icon": "link",
                                tooltip: "details",
                                click: function  (rowModel) {
                                    console.log(rowModel.SettleDetailId);
                                    $page.controller.showDetail(rowModel.SettleDetailId);
                                    //var segmentId = rowModel.SegmentId;
                                    //var url = $pt.getURL('action.accounting.accountSummary');
                                    //window.open(url + "?SegmentId=" + segmentId);
                                }
                            }]
                        },
                        css: {
                            comp: "inline-editor",
                            cell: 'title-align'
                        },

                        pos: {
                            width: 12
                        }
                    },
                    SettlementItemSummary: {
                        label: "Summary",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            //indexable : true,
                            sortable: true,
                            searchable: false,
                            //pageable:true,
                            //countPerPage : 5,
                            //criteria : "paginationCriteria",
                            columns: [
                                //{title: "Contract ID", data: "ContractCode",width:"10%"},
                                //{title: "UY", data: "UnderwritingYear",width:"7%"},
                                {title: "Contract Section", data: "SectionId",codes: $page.contractSelection,width:"30%"},
                                {title: "Settlement Type", data: "SettlementType", codes:$page.codes.SettlementType,width:"15%"},
                                {title: "Original Currency", data: "OriginalCurrency", codes:$page.codes.Currency,width:"15%"},
                                {title: "Original Amount", data: "AmountOc",width:"15%",
                                    //render : function(row){return $helper.formatNumberForLabel(row.AmountOc,2);}
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("AmountOc"), 2);
                                                    }
                                                }
                                            },
                                            pos: {width: 12},
                                            css: {cell: 'currency-align-right'}
                                        }
                                    }
                                },
                                {title: "USD Amount", data: "AmountUsd",width:"15%",
                                    //render : function(row){return $helper.formatNumberForLabel(row.AmountUsd,2);}
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("AmountUsd"), 2);
                                                    }
                                                }
                                            },
                                            pos: {width: 12},
                                            css: {cell: 'currency-align-right'}
                                        }
                                    }
                                }
                            ]
                        },
                        css: {
                            comp: "inline-editor",
                            cell: 'title-align'
                        },

                        pos: {
                            width: 12
                        }
                    },
                    blank : {
                        comp:{type:$pt.ComponentConstants.Nothing},
                        pos:{width:6}
                    },
                    SettlementSummary : {
                        label : "Total",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            sortable: false,
                            searchable: false,
                            columns: [
                                {title: "Original Currency", data: "CurrencyType"},
                                {title: "Original Amount",
                                    data: "GrossTotal",
                                    //render : function(row){return $helper.formatNumberForLabel(row.GrossTotal,2);}
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("GrossTotal"), 2);
                                                    }
                                                }
                                            },
                                            pos: {width: 12},
                                            css: {cell: 'currency-align-right'}
                                        }
                                    }
                                }
                            ]
                        },
                        css: {
                            comp: "inline-editor",
                            cell: 'title-align'
                        },
                        pos:{
                            width:6
                        }
                    },

                    rightButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Exit",
                                    style: "warning",
                                    click: function () {
                                        window.close();
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
        createFormLayout: function () {
            return {
                _sections: {
                    settlementHistory: this.settlementHistoryQuery()
                }
            }
        },
        settlementDetailLayout:function(){
            return {
                InsertBy: {
                    label: "Created By",
                    comp: {
                        type: $pt.ComponentConstants.Select,
                        data:$page.model.userCodes,
                        enabled: false
                    },
                    pos: {
                        row: 1,
                        width: 4
                    }
                },
                InsertTime: {
                    label: "Created On",
                    comp: {
                       type: $pt.ComponentConstants.Date,
                        format: "DD/MM/YYYY",
                        enabled: false
                    },
                    pos: {
                        row: 1,
                        width: 4
                    }
                },
                DateOfReceipt: {
                    label: "Date of Receipt",
                    comp: {
                        type: $pt.ComponentConstants.Date,
                        format: "DD/MM/YYYY",
                        enabled: false
                    },
                    pos: {
                        row: 1,
                        width: 4
                    }
                },
                //DateOfPayment: {
                //    label: "Date of Payment",
                //    comp: {
                //        type: $pt.ComponentConstants.Date,
                //        enabled: false
                //    },
                //    pos: {
                //        row: 2,
                //        width: 4
                //    }
                //},
                //PayType: {
                //    label: "Paid Type",
                //    comp: {
                //        type: $pt.ComponentConstants.Select,
                //        code:$page.codes.PaymentMethod,
                //        enabled: false
                //    },
                //    pos: {
                //        row: 2,
                //        width: 4
                //    }
                //},
                Remark: {
                    label: "Remarks",
                    comp: {
                        type: $pt.ComponentConstants.Text,
                        enabled : false
                    },
                    pos: {
                        row: 3,
                        width:12
                    }
                },
                SettleHistory: {
                    comp: {
                        type: $pt.ComponentConstants.Table,
                        searchable:false,
                        sortable:false,
                        // scrollX: true,
                        columns : [
                            {title:"Amount",data:"SettleAmount",width:"20%",
                                inline: {
                                    label: {
                                        comp: {
                                            type: {
                                                label: false,
                                                popover: false,
                                                render: function (model) {
                                                    return $helper.formatNumberForLabel(model.get("SettleAmount"), 2);
                                                }
                                            }
                                        },
                                        pos: {width: 12},
                                        css: {cell: 'currency-align-right'}
                                    }
                                }
                            },
                            {title:"Pay Type",data:"SettleTransType",codes: $page.codes.TransactionType,width:"20%"},
                            {title:"Settle Date",data:"SettleDate",
                                render: function (row) {
                                    return $helper.formatDateForTableView(row.SettleDate, "DD/MM/YYYY");
                                }
                                ,width:"20%"}
                        ]
                    },

                    pos: {width: 12}
                },
                SettlementLog: {
                    comp: {
                        type: $pt.ComponentConstants.Table,
                        searchable:false,
                        sortable:false,
                       // scrollX: true,
                        columns : [
                            {title:"By",data:"OperateBy",
                                codes:$page.model.userCodes,width:"20%"},
                            {title:"Decision",data:"Desicion",width:"20%"},
                            {title:"Date",data:"OperateDate",
                                render: function (row) {
                                    return $helper.formatDateForTableView(row.OperateDate, "DD/MM/YYYY");
                                },
                                width:"20%"
                            },
                            {title:"Remarks",data:"Remark",width:"40%"}
                        ]
                    },

                    pos: {width: 12}
                }
            }
        }
    });

    $page.layout = new Layout();

    }(typeof window !== "undefined" ? window : this));
