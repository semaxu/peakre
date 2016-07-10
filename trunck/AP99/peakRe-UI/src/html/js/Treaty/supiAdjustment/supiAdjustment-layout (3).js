(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.layout = $pt.getService($page, 'layout');
    var layout = jsface.Class({
        createBaseLayout: function () {
            return {
                _sections: {
                    defaultSection: {
                        label: "SUPI Adjustment",
                        style: "primary-darken",
                        layout: {
                            linkedPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [
                                            {
                                                text: "Contract Detail",
                                                style: "link",
                                                click: function () {
                                                    var url = "contractHome.html?ContCompId=" + this.getModel().get("ContCompId") + "&OperateType=0";
                                                    window.open(url);
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 1,
                                    width: 12
                                }
                            },
                            basicSectionInfo: {
                                label: "Contract Briefing",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    view:true,
                                    editLayout: {
                                        ContractCode: {
                                            label: "Contract ID",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                enabled: false
                                            },
                                            pos: {
                                                width: 4
                                            }
                                        },
                                        UwYear: {
                                            label: "UW Year",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                enabled: false
                                            },
                                            pos: {
                                                width: 4
                                            }
                                        },
                                        ContractCategory: {
                                            label: "Contract Category",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.ContractCategory,
                                                enabled: false
                                            }
                                        },
                                        Broker: {
                                            label: "Broker",
                                            base: $helper.basePartnerSearch()
                                            //comp: {
                                            //    type: $pt.ComponentConstants.BPSearch,
                                            //    searchTriggerDigits: 6,
                                            //    enabled: false
                                            //}
                                        },
                                        CoBroker: {
                                            label: "Co-Broker",
                                            base: $helper.basePartnerSearch()
                                            //comp: {
                                            //    type: $pt.ComponentConstants.BPSearch,
                                            //    searchTriggerDigits: 6,
                                            //    enabled: false
                                            //}

                                        },
                                        Mga: {
                                            label: "MGA",
                                            base: $helper.basePartnerSearch()
                                            //comp: {
                                            //    type: $pt.ComponentConstants.BPSearch,
                                            //    searchTriggerDigits: 6,
                                            //    enabled: false
                                            //}

                                        }
                                    }
                                },
                                pos: {
                                    row: 2,
                                    width: 12
                                }
                            },
                            SupiSecList: {
                                label: 'Adjustment Panel',
                                comp: {
                                    type: $pt.ComponentConstants.ArrayPanel,
                                    style: 'primary-darken',
                                    itemTitle: {
                                        depends: 'PanelTitle',
                                        when: function (item) {
                                            return (typeof(item.get('PanelTitle')) == "undefined") ? "No result" : item.get('PanelTitle');
                                        }
                                    },
                                    collapsible: true,
                                    editLayout: function (model) {
                                        return {
                                            AdjustedSUPIDefined: {
                                                label: "Adjusted SUPI Defined in",
                                                comp: {
                                                    type: $pt.ComponentConstants.Select,
                                                    enabled: false,
                                                    data: $page.codes.PremiumDefinedIn
                                                }
                                            },
                                            note: {
                                                comp: {
                                                    type: $pt.ComponentConstants.Label,
                                                    style: "info"
                                                },
                                                pos: {
                                                    width: 8
                                                }
                                            },
                                            AdjustedSUPIList: {
                                                label: "SUPI Adjustment",
                                                comp: {
                                                    type: $pt.ComponentConstants.Table,
                                                    addable: false,
                                                    removable: false,
                                                    searchable: false,
                                                    sortable: false,
                                                    // addClick: function (model, item, layout) {
                                                    //     model.add("AdjustedSUPIList", $.extend(true, {}, $page.model.getAdjustedSUPITemplate));
                                                    // },
                                                    // canRemove: function (model, item) {
                                                    //     if (item.ItemId && item.ItemId != 0) {
                                                    //         model.add("DeleteAdjustedSUPIList", item);
                                                    //     }
                                                    //     this.getModel().remove(this.getDataId(), item);
                                                    // },
                                                    //rowListener: [
                                                    //    {
                                                    //        id: 'AdjustmentDate', listener: function (evt) {
                                                    //        var momentTime = moment().format('YYYY-MM-DDTHH:mm:ss');
                                                    //        var adjustmentDate = evt.model.get('AdjustmentDate');
                                                    //        console.log(evt);
                                                    //        if (adjustmentDate) {
                                                    //            if (adjustmentDate > momentTime) {
                                                    //                NConfirm.getConfirmModal().show({
                                                    //                    title: 'System Message',
                                                    //                    disableClose: true,
                                                    //                    messages: ['Date of Adjustment could not be later than today.'],
                                                    //                    onConfirm: function (model) {
                                                    //                        return false;
                                                    //                    }
                                                    //                });
                                                    //                evt.model.set('AdjustmentDate', "");
                                                    //            }
                                                    //        }
                                                    //
                                                    //    }
                                                    //    }
                                                    //],
                                                    columns: [
                                                        {
                                                            title: "Date of Adjustment",
                                                            data: "AdjustmentDate",
                                                            inline: "DateFormateForDisable",
                                                            width: "20%"
                                                        }, {
                                                            title: "By User",
                                                            data: "UserBy",
                                                            //inline: "select",
                                                            inline: {
                                                                UserBy: {
                                                                    comp: {
                                                                        label: false,
                                                                        type: $pt.ComponentConstants.Select,
                                                                        data: $page.model.userCodes,
                                                                        enabled: false
                                                                    },
                                                                    pos: {width: 12},
                                                                }
                                                            },
                                                            width: "20%"
                                                        }, {
                                                            title: "Currency",
                                                            data: "Currency",
                                                            //inline: "select",
                                                            inline: {
                                                                Currency: {
                                                                    comp: {
                                                                        type: $pt.ComponentConstants.Select,
                                                                        data: $page.codes.Currency,
                                                                        label: false,
                                                                        enabled: false
                                                                    },
                                                                    pos: {width: 12},
                                                                }
                                                            },
                                                            //codes: $page.codes.Currency,
                                                            width: "20%"
                                                        }, {
                                                            title: "Amount",
                                                            data: "Amount",
                                                            inline: "number",
                                                            width: "20%"
                                                        }, {
                                                            title: "As at Date",
                                                            data: "DateAt",
                                                            inline: "DateFormate",
                                                            width: "20%"
                                                        }
                                                    ]
                                                },
                                                pos: {
                                                    width: 12
                                                },
                                                css: {
                                                    comp: "inline-editor"
                                                }
                                            },
                                            HisList: {
                                                label: "History",
                                                comp: {
                                                    type: $pt.ComponentConstants.Table,
                                                    searchable: false,
                                                    sortable: false,
                                                    view: true,
                                                    columns: [
                                                        {
                                                            title: "Date of Adjustment",
                                                            data: "AdjustmentDate",
                                                            inline: "DateFormateForLabel",
                                                            width: "20%"
                                                        }, {
                                                            title: "By User",
                                                            data: "UserBy",
                                                            inline: "select",
                                                            codes: $page.model.userCodes,
                                                            width: "20%"
                                                        }, {
                                                            title: "Currency",
                                                            data: "Currency",
                                                            // inline: "select",
                                                            codes: $page.codes.Currency,
                                                            width: "20%"
                                                        }, {
                                                            title: "Amount",
                                                            data: "Amount",
                                                            // inline: "number",
                                                            width: "20%",
                                                            render: function (row) {
                                                                return $helper.formatNumberForLabel(row.Amount, 2);
                                                            }
                                                        }, {
                                                            title: "As at Date",
                                                            data: "DateAt",
                                                            inline: "DateFormateForLabel",
                                                            width: "20%"
                                                        }
                                                    ]
                                                },
                                                pos: {
                                                    width: 12
                                                }
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 3,
                                    width: 12
                                }
                            },
                            remarkPanel: {
                                label: "Remarks",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        Remark: {
                                            comp: {
                                                type: $pt.ComponentConstants.TextArea,
                                                labelDirection: "vertical",
                                                lines: 3
                                            },
                                            pos: {
                                                width: 12
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 4,
                                    width: 12
                                }
                            },
                            buttonPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [
                                            {
                                                text: "Exit",
                                                style: "warning",
                                                click: function () {
                                                    //window.location.href = "/peak-basic-ui/src/main/webapp/html/Treaty/contractQuery.html?epiSupi=true";
                                                    var url = "contractQuery.html?OperateType=4";
                                                    window.location.href = url;
                                                }
                                            }, {
                                                text: "Submit",
                                                style: "primary",
                                                click: function () {
                                                    //if (!$page.controller.adjustDate()) {
                                                    //    NConfirm.getConfirmModal().show({
                                                    //        title: 'Alert',
                                                    //        disableClose: true,
                                                    //        messages: ['Invalid Date of Adjustment. Same Date is required for SUPI Adjustment in each Currency type.']
                                                    //    });
                                                    //    return;
                                                    //}
                                                    if (!$page.controller.saveSUPIAdjustment()) {
                                                        return;
                                                    }
                                                    var url = "contractQuery.html?OperateType=4";
                                                    window.location.href = url;
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 5,
                                    width: 12
                                }
                            }
                        }
                    }
                }
            }
        },
        createLayout: function () {
            return $.extend(true, {}, this.createBaseLayout());
        }
    });

    $page.layout = new layout();

}(typeof window !== "undefined" ? window : this));
