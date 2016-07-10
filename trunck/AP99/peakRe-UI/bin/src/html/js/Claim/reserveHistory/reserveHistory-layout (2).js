/**
 * Created by gang.wang on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var reserveType =$pt.createCodeTable({id:"1",text:"Loss"},{id:"2",text:"Expense"});


    var Layout = jsface.Class({
        reserveHistoryQuery:function() {
            return {

                label: "Reserve History",
                style: 'primary-darken',
                layout: {
                    searchPanel: {
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                ReserveType: {
                                    label: "Reserve Type",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.ReserveType
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                ContractSection: {
                                    label: "Contract Section",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.contractSelection
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                //condition_ContractCode: {
                                //    label: "Contract ID",
                                //    comp: {
                                //        type: $pt.ComponentConstants.Select,
                                //        //data: $pt.createCodeTable([{}])
                                //    }
                                //},
                                UnderwritingYear: {
                                    label: "Underwriting Year",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.uwYearSelection
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },

                                buttonPanel1: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [
                                                {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function () {
                                                        $page.controller.searchReserve();
                                                    }
                                                }
                                            ]
                                        }
                                    },
                                    pos: {
                                        row: 2,
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },

                    ReserveHistoryInfo: {
                        label: "Reserve History",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            //indexable : true,
                            sortable: true,
                            searchable: false,
                            //pageable:true,
                            //countPerPage : 5,
                            //criteria : "paginationCriteria",
                            columns: [
                                {title: "Update Date", data: "UpdateTime",
                                    render: function (row) {
                                        return $helper.formatDateForTableView(row.UpdateTime, "DD/MM/YYYY HH:mm");
                                    },width:"10%"},
                                //{title: "Contract ID", data: "ContractCode",width:"10%"},
                                //{title: "UY", data: "UnderwritingYear",width:"7%"},
                                {title: "Contract Section", data: "ContractSectionName",width:"25%"},
                                {title: "Reserve Type", data: "ReserveType", codes:$page.codes.ReserveType,width:"15%"},
                                {title: "Original Currency", data: "OriginalCurrency", codes:$page.codes.Currency,width:"13%"},
                                {title: "Original Amount", data: "AmountOc",width:"13%",
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
                                {title: "USD Amount", data: "AmountUsd",width:"13%",
                                   // render : function(row){return $helper.formatNumberForLabel(row.AmountUsd,2);}
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
                                {title:"Transfer to Accounts",data:"PostingFlag",codes: $page.codes.Boolean, width:"6%"},
                                {data: "Remark", visible: false},
                                {data: "InsertBy", visible: false},
                                {data: "InsertTime", visible: false}
                            ],
                            rowOperations: [{
                                icon: "link",
                                tooltip: "details",
                                click: function (row) {
                                    var reserveForm = NModalForm.createFormModal('Details','abc');
                                    var reserveModel = $pt.createModel({
                                        createBy: row.InsertBy,
                                        createDate: row.InsertTime,
                                        remark: row.Remark
                                    });
                                    var layout = $pt.createFormLayout({
                                        createBy: {
                                            label: "Created By",
                                            comp: {type: $pt.ComponentConstants.Select,
                                                data:$page.model.userCodes,
                                                enabled: false},
                                            pos: {width: 6}
                                        },
                                        createDate: {
                                            label: "Created on",
                                            comp: {type: $pt.ComponentConstants.Date,
                                                format: "DD/MM/YYYY HH:mm",
                                                enabled: false},
                                            pos: {width: 6}
                                        },
                                        remarkPanel: {
                                            label: "Remarks",
                                            comp: {
                                                type: $pt.ComponentConstants.Panel,
                                                editLayout: {
                                                    remark: {
                                                        comp: {
                                                            type: {
                                                                type: $pt.ComponentConstants.TextArea,
                                                                label: false
                                                            },
                                                            lines: 3,
                                                            enabled: false
                                                        },
                                                        pos: {width: 12}
                                                    }
                                                }
                                            },
                                            pos: {width: 12}
                                        }
                                    });

                                    reserveForm.show({
                                        model: reserveModel,
                                        layout: layout,
                                        buttons: {
                                            cancel: false,
                                            validate: false,
                                            reset: false,
                                            right: [{
                                                text: 'Exit',
                                                style: 'warning',
                                                click: function () {
                                                    reserveForm.hide();
                                                }
                                            }]
                                        }
                                    }, 'horizontal');
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
                    ReserveInfo: {
                        label: "Latest Reserve",
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
                                {title: "Contract Section", data: "ContractSectionName",width:"7%"},
                                {title: "Reserve Type", data: "ReserveType", codes:$page.codes.ReserveType,width:"11%"},
                                {title: "Original Currency", data: "OriginalCurrency", codes:$page.codes.Currency,width:"12%"},
                                {title: "Original Amount", data: "AmountOc",width:"11%",
                                    //render : function(row){return  $helper.formatNumberForLabel(row.AmountOc,2);}
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
                                {title: "USD Amount", data: "AmountUsd",width:"11%",
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
                    blank: {
                        comp: {type: $pt.ComponentConstants.Nothing},
                        pos: {width: 6}
                    },
                    ReserveSummary: {
                        label: "Summary",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            sortable: false,
                            searchable: false,
                            columns: [
                                {title: "Original Currency", data: "CurrencyType"},
                                {title: "Original Amount", data: "LossTotal",
                                    //render : function(row){return $helper.formatNumberForLabel(row.LossTotal,2);}
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("LossTotal"), 2);
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
                            width: 6
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
                                        //window.location.href = "claimInformation.html";
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
            };
        },
        createFormLayout: function () {
            return {
                _sections: {
                    reserveHistory: this.reserveHistoryQuery()
                }
            }
        }
    });
    $page.layout = new Layout();

}(typeof window !== "undefined" ? window : this));
