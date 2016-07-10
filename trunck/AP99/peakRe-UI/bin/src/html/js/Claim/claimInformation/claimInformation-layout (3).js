(function (context) {
    var $page = $pt.getService(context, '$page');
    // var contractSelection = $page.codes.createMutableCodeTable();
    var registerInlineSelect = function (name, codes, type) {
        NTable.registerInlineEditor(name, {
            comp: {
                type: {type: type, label: false, popover: true},
                data: codes,
                enabled: {
                    depends: "Status",
                    when: function (row) {
                        return row.get('Status') == 0;
                    }
                }
            }
        });
        return name;
    };
    var Layout = jsface.Class({
        // /**
        //  * get contract section options
        //  * @returns {CodeTable}
        //  */
        // getContractSectionOptions: function() {
        //     return contractSelection;
        // },
        createClaimInfo: function () {
            return {
                label: "Claim Information",
                style: 'primary-darken',
                collapsible: true,
                expanded: true,
                layout: {
                    headButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Message(" + $page.messageNum + ")",
                                    style: "link",
                                    //enabled: {
                                    //    depends: "status",
                                    //    when: function () {
                                    //        return $page.messageNum != 0;
                                    //    }
                                    //},
                                    click: function (model) {

                                        $page.controller.showMessage();
                                    }
                                }]
                            }
                        },
                        pos: {
                            width: 12,
                            row: 1
                        }
                    },
                    divider1: {
                        base: {
                            comp: {
                                type: $pt.ComponentConstants.Divider
                            },
                            pos: {
                                width: 12, row: 15
                            },
                            css: {
                                cell: 'form-cell-divider'
                            }
                        }
                    },
                    ClaimId: {
                        label: "ClaimId",
                        comp: {
                            //type: $pt.ComponentConstants.Text,

                            visible: false
                        }
                    },
                    ClaimNo: {
                        label: "Claim No",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            enabled: false
                        },
                        pos: {row: 20}
                    },
                    EventId: {
                        label: "Event Code",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.model.eventCodes
                        },
                        pos: {row: 20}
                    },
                    ClaimBranch: {
                        label: "Claim Branch",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.UwCompany

                        },
                        pos: {row: 20}
                    },
                    CauseOfLoss: {
                        label: "Cause of Loss",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.Cause
                        },
                        pos: {row: 25}
                    },
                    DateOfLossFrom: {
                        label: "Loss Start Date",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY HH:mm",
                            enabled: false
                        },
                        pos: {row: 25}
                    },
                    DateOfLossTo: {
                        label: "Loss End Date",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY HH:mm"
                        },
                        pos: {row: 25}
                    },
                    DateOfReport: {
                        label: "Date of Report",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY HH:mm"
                        },
                        pos: {row: 25}
                    },
                    DateOfCreation: {
                        label: "Created on",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY HH:mm",
                            enabled: false
                        },
                        pos: {row: 25}
                    },
                    lossDescriptionPanel: {
                        label: "Loss Description",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                LossDescription: {
                                    comp: {
                                        type: {
                                            type: $pt.ComponentConstants.TextArea,
                                            label: false
                                        },
                                        lines: 3
                                    },
                                    pos: {
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 6, row: 30
                        }
                    },
                    remarkPanel: {
                        label: "Remarks",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                Remark: {
                                    comp: {
                                        type: {
                                            type: $pt.ComponentConstants.TextArea,
                                            label: false
                                        },
                                        lines: 3
                                    },
                                    pos: {
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 6, row: 30
                        }
                    },
                    ClaimSummary: {
                        // label: "Total Incurred Loss",
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
                            scrollX: false,
                            header: false,
                            //criteria : "paginationCriteria",
                            columns: [
                                {title: "", data: "CurrencyType", width: "15%"},//, codes: $page.codes.CurrencySpecial
                                {
                                    title: "Submitted Reserve(USD)", data: "ReserveSummary",
                                    //render: function (row) {
                                    //    return $helper.formatNumberForLabel(row.ReserveSummary, 2);
                                    //},
                                    width: "30%",
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("ReserveSummary"), 2);
                                                    }
                                                }
                                            },
                                            pos: {width: 12},
                                            css: {cell: 'currency-align-right'}
                                        }
                                    }
                                },
                                {
                                    title: "Approved Settlement(USD)", data: "SettlementSummary",
                                    //render: function (row) {
                                    //    return $helper.formatNumberForLabel(row.SettlementSummary, 2);
                                    //},
                                    width: "30%",
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("SettlementSummary"), 2);
                                                    }
                                                }
                                            },
                                            pos: {width: 12},
                                            css: {cell: 'currency-align-right'}
                                        }
                                    }
                                },
                                {
                                    title: "Total(USD)", data: "TotalByCurrency",
                                    //render: function (row) {
                                    //    return $helper.formatNumberForLabel(row.TotalByCurrency, 2);
                                    //},
                                    width: "20%",
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("TotalByCurrency"), 2);
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
                        pos: {
                            width: 6
                        }
                    },
                    buttonLayout: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                left: [{
                                    text: "Detail",
                                    style: "primary",
                                    click: function (model) {
                                        $page.controller.exportExcel();
                                    }
                                }]
                            }
                        },
                        pos: {
                            width: 6
                        }
                    }
                }
            };
        },
        createDetailFinanicalTab: function () {
            return {
                label: "Financial",
                icon: "bookmark",
                editLayout: {
                    lossReverse: {
                        label: "Reserve",
                        pos: {
                            width: 12,
                            row: 2
                        },
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            style: "primary-darken",
                            collapsible: true,
                            expanded: true,
                            editLayout: {
                                headButtons: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            left: [{
                                                text: "Add Contract Section",
                                                style: "primary",
                                                icon: "plus",
                                                enabled: {
                                                    depends: "status",
                                                    when: function () {
                                                        return $page.pageType == 0;
                                                    }
                                                },
                                                click: function () {
                                                    var treeCodesData = null;
                                                    var dialogModel = null;
                                                    $page.controller.addContractSection(treeCodesData, dialogModel, 1);
                                                }
                                            }],
                                            right: [{
                                                text: "Reserve History",
                                                style: "link",
                                                click: function (model) {
                                                    var claimID = model.get("ClaimId");
                                                    var url = $pt.getURL('ui.claim.reserveHistory');
                                                    window.open(url + "?refId=" + claimID + "&businessDirection=1");
                                                }
                                            }]
                                        }
                                    },
                                    pos: {
                                        width: 12,
                                        row: 1
                                    }
                                },
                                ReserveList: {
                                    label: "",
                                    comp: {
                                        centralId: 'reserveTable',
                                        type: $pt.ComponentConstants.Table,
                                        rowListener: [{
                                            id: 'SectionId',
                                            listener: function (evt) {
                                                var _self = evt.model.__parent.__model;
                                                var SectionId = evt.model.get("SectionId");
                                                var ReserveType = evt.model.get("ReserveType");
                                                var OriginalCurrency = evt.model.get("OriginalCurrency");
                                                if (null != SectionId && undefined != SectionId) {
                                                    $page.controller.getPosting(SectionId);
                                                    if ($page.postingFlag == false) {
                                                        evt.model.set("PostingFlag", '0');
                                                    } else {
                                                        evt.model.set("PostingFlag", '1');
                                                    }
                                                }
                                                if (undefined != ReserveType && null != ReserveType) {
                                                    if (ReserveType === "3") {
                                                        evt.model.set("PostingFlag", '1');
                                                    }
                                                }
                                                if (undefined != SectionId && null != SectionId && undefined != ReserveType && null != ReserveType && undefined != OriginalCurrency && null != OriginalCurrency) {
                                                    var nowReserveItem = SectionId + ReserveType + OriginalCurrency;
                                                    var ReserveItems = [];
                                                    if ($page.ReserveItems != undefined && $page.ReserveItems != null && "" != $page.ReserveItems) {
                                                        ReserveItems = $page.ReserveItems;
                                                        var num = ReserveItems.length;
                                                        console.log(num);
                                                        for (var i = 0; i < num; i++) {
                                                            var reserveItems = ReserveItems[i];
                                                            if (nowReserveItem === reserveItems) {
                                                                NConfirm.getConfirmModal().show({
                                                                    title: 'System Message',
                                                                    disableClose: true,
                                                                    messages: ['There is an existing reserve under selected conditions, you cannot add a same one.']
                                                                });
                                                                evt.model.set("SectionId", null);
                                                            }
                                                        }
                                                        ReserveItems[num] = nowReserveItem;
                                                        num = num + 1;
                                                        var AllReserveList = evt.model.__parent.__model.ReserveList;
                                                        var deleteReserveList = $page.controller.model.get("DeleteReserveList");
                                                        $page.controller.checkReserveItem(AllReserveList, deleteReserveList, ReserveItems);
                                                    } else {
                                                        ReserveItems.push(nowReserveItem);
                                                        $page.ReserveItems = ReserveItems;
                                                    }
                                                }
                                            }
                                        }, {
                                            id: 'ReserveType',
                                            listener: function (evt) {
                                                var SectionId = evt.model.get("SectionId");
                                                var ReserveType = evt.model.get("ReserveType");
                                                var OriginalCurrency = evt.model.get("OriginalCurrency");
                                                if (undefined != ReserveType && null != ReserveType) {
                                                    if (ReserveType === "3") {
                                                        evt.model.set("PostingFlag", '1');
                                                    }else{
                                                        if (ReserveType === "5" || ReserveType === "6"){
                                                            evt.model.set("PostingFlag", '0');
                                                        }
                                                        if (null != SectionId && undefined != SectionId) {
                                                            $page.controller.getPosting(SectionId);
                                                            if ($page.postingFlag == false) {
                                                                evt.model.set("PostingFlag", '0');
                                                            } else {
                                                                evt.model.set("PostingFlag", '1');
                                                            }
                                                        }
                                                    }
                                                }

                                                if (undefined != SectionId && null != SectionId && undefined != ReserveType && null != ReserveType && undefined != OriginalCurrency && null != OriginalCurrency) {
                                                    var nowReserveItem = SectionId + ReserveType + OriginalCurrency;
                                                    var ReserveItems = [];
                                                    if ($page.ReserveItems != undefined && $page.ReserveItems != null && "" != $page.ReserveItems) {
                                                        ReserveItems = $page.ReserveItems;
                                                        var num = ReserveItems.length;
                                                        for (var i = 0; i < num; i++) {
                                                            var reserveItems = ReserveItems[i];
                                                            if (nowReserveItem === reserveItems) {
                                                                NConfirm.getConfirmModal().show({
                                                                    title: 'System Message',
                                                                    disableClose: true,
                                                                    messages: ['There is an existing reserve under selected conditions, you cannot add a same one.']
                                                                });
                                                                evt.model.set("ReserveType", null);
                                                            }
                                                        }
                                                        ReserveItems[num] = nowReserveItem;
                                                        num = num + 1;
                                                        var AllReserveList = evt.model.__parent.__model.ReserveList;
                                                        var deleteReserveList = $page.controller.model.get("DeleteReserveList");
                                                        $page.controller.checkReserveItem(AllReserveList, deleteReserveList, ReserveItems);
                                                    } else {
                                                        ReserveItems.push(nowReserveItem);
                                                        $page.ReserveItems = ReserveItems;
                                                    }
                                                }
                                            }
                                        }, {
                                            id: 'OriginalCurrency',
                                            listener: function (evt) {
                                                var SectionId = evt.model.get("SectionId");
                                                var ReserveType = evt.model.get("ReserveType");
                                                var OriginalCurrency = evt.model.get("OriginalCurrency");
                                                if (undefined != SectionId && null != SectionId && undefined != ReserveType && null != ReserveType && undefined != OriginalCurrency && null != OriginalCurrency) {
                                                    var nowReserveItem = SectionId + ReserveType + OriginalCurrency;
                                                    var ReserveItems = [];
                                                    if ($page.ReserveItems != undefined && $page.ReserveItems != null && "" != $page.ReserveItems) {
                                                        ReserveItems = $page.ReserveItems;
                                                        var num = ReserveItems.length;
                                                        for (var i = 0; i < num; i++) {
                                                            var reserveItems = ReserveItems[i];
                                                            if (nowReserveItem === reserveItems) {
                                                                NConfirm.getConfirmModal().show({
                                                                    title: 'System Message',
                                                                    disableClose: true,
                                                                    messages: ['There is an existing reserve under selected conditions, you cannot add a same one.']
                                                                });
                                                                evt.model.set("OriginalCurrency", null);
                                                            }
                                                        }
                                                        ReserveItems[num] = nowReserveItem;
                                                        num = num + 1;
                                                        var AllReserveList = evt.model.__parent.__model.ReserveList;
                                                        var deleteReserveList = $page.controller.model.get("DeleteReserveList");
                                                        $page.controller.checkReserveItem(AllReserveList, deleteReserveList, ReserveItems);
                                                    } else {
                                                        ReserveItems.push(nowReserveItem);
                                                        $page.ReserveItems = ReserveItems;
                                                    }
                                                }
                                            }
                                        }, {
                                            id: 'AmountOc',
                                            listener: function (evt) {
                                                var amountOc = evt.model.get("AmountOc");
                                                if (null != amountOc && "" != amountOc && undefined != amountOc) {
                                                    //var flag = $page.controller.checkNum(amountOc);
                                                    if (parseFloat(amountOc) != amountOc) {
                                                        NConfirm.getConfirmModal().show({
                                                            title: 'System Message',
                                                            disableClose: true,
                                                            messages: ['Amount(OC) only allows numbers to be filled in.']
                                                        });
                                                        evt.model.set("AmountOc", "");
                                                    }
                                                }
                                            }
                                        }, {
                                            id: 'PostingFlag', listener: function (evt) {
                                                var ReserveType = evt.model.get("ReserveType");
                                                var PostingFlag = evt.model.get("PostingFlag");
                                                if (undefined != ReserveType && null != ReserveType) {
                                                    if (ReserveType === "3") {
                                                        evt.model.set("PostingFlag", '1');
                                                    }
                                                }
                                            }
                                        }
                                        ],
                                        sortable: false,
                                        searchable: false,
                                        //editable: true,
                                        //removable: true,
                                        addable: true,
                                        "scrollX": true,
                                        columns: [
                                            {
                                                title: "Contract Section",
                                                data: "SectionId",
                                                codes: $page.model.contractSelection,
                                                inline: registerInlineSelect.call(window, "SectionId", $page.model.contractSelection, $pt.ComponentConstants.Select),
                                                width: 300
                                            },
                                            {
                                                title: "Reserve Type",
                                                data: "ReserveType",
                                                inline: registerInlineSelect("ReserveType", $page.codes.ReserveType, $pt.ComponentConstants.Select),
                                                codes: $page.codes.ReserveType,
                                                width: 150
                                            },
                                            {
                                                title: "Original Currency",
                                                data: "OriginalCurrency",
                                                inline: registerInlineSelect("OriginalCurrency", $page.codes.Currency, $pt.ComponentConstants.Select),
                                                codes: $page.codes.Currency,
                                                width: 120
                                            },
                                            {
                                                title: "Submitted Amount(OC)",
                                                data: "OrgAmountOc",
                                                width: 160,
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("OrgAmountOc"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Latest Entered Amount(OC)",
                                                data: "AmountOc",
                                                inline: "number",
                                                render: function (row) {
                                                    return $helper.formatNumberForLabel(row.AmountOc, 2);
                                                },
                                                width: 180
                                                //inline: {
                                                //    label: {
                                                //        comp: {
                                                //            type: {
                                                //                label: true,
                                                //                popover: false,
                                                //                render: function (model) {
                                                //                    return $helper.formatNumberForLabel(model.get("AmountOc"), 2);
                                                //                }
                                                //            }
                                                //        },
                                                //        pos: {width: 12},
                                                //        css: {cell: 'currency-align-right'}
                                                //    }
                                                //}
                                            },
                                            {
                                                title: "Submitted Amount(USD)",
                                                data: "OrgAmountUsd",
                                                // inline: "number",
                                                // render: function (row) {
                                                //     return $helper.formatNumberForLabel(row.OrgAmountUsd, 2);
                                                // },
                                                width: 170,
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("OrgAmountUsd"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Latest Entered Amount(USD)",
                                                data: "AmountUsd",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.AmountUsd, 2);
                                                //},
                                                // inline: "number",
                                                width: 190,
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
                                            {
                                                title: "Cedent Ref.",
                                                data: "CedentRefer",
                                                inline: "text",
                                                //inline: registerInlineSelect("CedentRefer", null, $pt.ComponentConstants.Text),
                                                width: 150
                                            },
                                            {
                                                title: "Broker Ref.",
                                                data: "BrokerRefer",
                                                inline: "text",
                                                //inline: registerInlineSelect("BrokerRefer", null, $pt.ComponentConstants.Text),
                                                width: 150
                                            },
                                            {
                                                title: "Transfer to Accounts",
                                                data: "PostingFlag",
                                                //inline: "radio",
                                                //codes: $page.codes.Boolean,
                                                width: 100,
                                                inline: {
                                                    PostingFlag:{
                                                        comp:{
                                                            type: {type: $pt.ComponentConstants.Radio, label: false},
                                                            data: $page.codes.Boolean,
                                                            enabled: {
                                                                depends: 'ReserveType',
                                                                when: function (rowModel) {
                                                                    var reserveType = rowModel.get("ReserveType");
                                                                    //console.info(reserveType);
                                                                    if (reserveType == "5" || reserveType == "6"){
                                                                        return false;
                                                                    }
                                                                    return true;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        ],
                                        addClick: function (model) {
                                            // model.add("ReserveList", item.getCurrentModel());
                                            //console.log($page.model.reserveArrayTemplate());
                                            model.add("ReserveList", $.extend(true, {}, $page.model.reserveArrayTemplate()));
                                        },
                                        rowOperations: [
                                            {
                                                icon: "trash",
                                                tooltip: "delete",
                                                enabled: {
                                                    depends: "status",
                                                    when: function (rowModel) {
                                                        return rowModel.getCurrentModel().Status == '0';
                                                    }
                                                },
                                                click: function (rowModel) {
                                                    if (rowModel.ReserveId == undefined || rowModel.ReserveId == "undefined") {
                                                    } else {
                                                        $page.controller.model.add("DeleteReserveList", rowModel);
                                                        console.log($page.controller.model.get("DeleteReserveList"));
                                                    }
                                                    this.getModel().remove(this.getDataId(), rowModel);
                                                }
                                            }
                                        ]

                                    },
                                    css: {
                                        comp: 'inline-editor',
                                        cell: 'title-align'
                                    },
                                    pos: {
                                        width: 12,
                                        row: 1
                                    }
                                },
                                changeReasonPanel: {
                                    comp: {
                                        type: $pt.ComponentConstants.Panel,
                                        editLayout: {
                                            remarkPanel: {
                                                label: "Reserve Remarks",
                                                comp: {
                                                    type: $pt.ComponentConstants.Panel,
                                                    editLayout: {
                                                        ReserveUpdateRemark: {
                                                            comp: {
                                                                type: {
                                                                    type: $pt.ComponentConstants.TextArea,
                                                                    label: false
                                                                },
                                                                lines: 4
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
                                            }
                                        }
                                    },
                                    pos: {
                                        width: 5,
                                        row: 2
                                    }
                                },
                                ReserveSummary: {
                                    label: "Summary",
                                    style: "primary",
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        sortable: false,
                                        searchable: false,
                                        // header:false,
                                        columns: [
                                            {
                                                title: "",
                                                data: "CurrencyType",
                                                codes: $page.codes.CurrencySpecial,
                                                width: "15%"
                                            },
                                            {
                                                title: "Loss Indemnity & Expense",
                                                data: "LossTotal",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.LossTotal, 2);
                                                //},
                                                width: "25%",
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
                                            },
                                            {
                                                title: "ACR",
                                                data: "ACRTotal",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.ACRTotal, 2);
                                                //},
                                                // inline: "number",
                                                width: "15%",
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("ACRTotal"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }

                                            },
                                            {
                                                title: "RIP",
                                                data: "RIPTotal",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.RIPTotal, 2);
                                                //},
                                                width: "15%",
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("RIPTotal"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Tax",
                                                data: "TaxTotal",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.TaxTotal, 2);
                                                //},
                                                width: "15%",
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("TaxTotal"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Others",
                                                data: "OthersTotal",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.OthersTotal, 2);
                                                //},
                                                width: "15%",
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("OthersTotal"), 2);
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
                                        comp: 'inline-editor',
                                        cell: 'title-align'
                                    },
                                    pos: {width: 7, row: 2}
                                }
                            }
                        }
                    },
                    settlement: {
                        label: "Settlement",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            style: "primary-darken",
                            collapsible: true,
                            expanded: true,
                            editLayout: {
                                headButtons: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            left: [{
                                                text: "Add Settlement",
                                                style: "primary",
                                                icon: "plus",
                                                enabled: {
                                                    depends: "status",
                                                    when: function () {
                                                        return $page.pageType == 0;
                                                    }
                                                },
                                                click: function (model) {
                                                    //    console.log(model.get("settlementArray"));
                                                    //console.log($page.model.settlementArrayTemplate());
                                                    var settlementModel = $.extend(true, {}, $page.model.settlementArrayTemplate());
                                                    $page.controller.getSettlementName(settlementModel);
                                                    model.add("SettlementList", $.extend(true, {}, settlementModel));
                                                }
                                            }],
                                            right: [{
                                                text: "Settlement History",
                                                style: "link",
                                                click: function (model) {
                                                    var claimID = model.get("ClaimId");
                                                    var url = $pt.getURL('ui.claim.settlementHistory');
                                                    window.open(url + "?refId=" + claimID + "&businessDirection=1");
                                                }
                                            }, {
                                                text: "Change Posting",
                                                style: "link",
                                                enabled: {
                                                    depends: "status",
                                                    when: function () {
                                                        return $page.pageType == 0;
                                                    }
                                                },
                                                click: function (model) {
                                                    var claimID = model.get("ClaimId");
                                                    var url = $pt.getURL('ui.claim.changePosting');
                                                    window.open(url + "?refId=" + claimID + "&businessDirection=1");
                                                }
                                            }]
                                        }
                                    },
                                    pos: {
                                        width: 12,
                                        row: 5
                                    }
                                },
                                settlementPanel: {
                                    label: "Settlement",
                                    style: "primary",
                                    pos: {
                                        width: 12,
                                        row: 6
                                    },
                                    dataId: "SettlementList",
                                    comp: {
                                        type: $pt.ComponentConstants.ArrayPanel,
                                        itemTitle: {
                                            dataId: "SettlementName",
                                            depends: 'SettlementName',
                                            when: function (item) {
                                                return item.get("SettlementName");

                                            }
                                        },
                                        headerButtons : {
                                          text : 'delete',
                                          visible: {
                                            depends: 'settlementId',
                                            when: function (item) {
                                                return null == item.getCurrentModel().SettlementId || undefined == item.getCurrentModel();
                                            }
                                          },
                                          click: function (item) {
                                              $page.controller.onRemoveSettlementClicked("SettlementList",item);
                                          }
                                        },
                                        editLayout: {
                                            DateOfReceipt: {
                                                label: "Date of Receipt",
                                                comp: {
                                                    type: $pt.ComponentConstants.Date,
                                                    format: "DD/MM/YYYY"
                                                    //valueFormat:$pt.ComponentConstants.Default_Date_Format,
                                                    //labelDirection: "horizontal"
                                                },
                                                pos: {
                                                    row: 1
                                                }
                                            },
                                            Status: {
                                                label: "Status",
                                                comp: {
                                                    type: $pt.ComponentConstants.Select,
                                                    data: $page.codes.SettleStatus,
                                                    enabled: false
                                                },
                                                pos: {
                                                    row: 1
                                                }
                                            },
                                            SettlementItemList: {
                                                label: "",
                                                comp: {
                                                    type: $pt.ComponentConstants.Table,
                                                    sortable: false,
                                                    searchable: false,
                                                    rowListener: [{
                                                        id: 'SectionId',
                                                        listener: function (evt) {
                                                            var SectionId = evt.model.get("SectionId");
                                                            var SettlementType = evt.model.get("SettlementType");
                                                            if (null != SectionId && "" != SectionId) {
                                                                // meiliang.zou modify 2016.5.24 start
                                                                /*$page.controller.getPosting(SectionId);
                                                                 if ($page.postingFlag == false) {
                                                                 evt.model.set("PostingFlag", '0');
                                                                 } else {
                                                                 evt.model.set("PostingFlag", '1');
                                                                 }*/
                                                                evt.model.set("PostingFlag", '1');
                                                                // meiliang.zou modify 2016.5.24 end
                                                            }
                                                            //if(undefined != SettlementType && null != SettlementType){
                                                            //    if(SettlementType === "4" ){
                                                            //        evt.model.set("PostingFlag", '1');
                                                            //    }
                                                            //}
                                                        }
                                                    }, {
                                                        id: 'AmountOc',
                                                        listener: function (evt) {
                                                            var amountOc = evt.model.get("AmountOc");
                                                            if (null != amountOc && "" != amountOc && undefined != amountOc) {
                                                                //var flag = $page.controller.checkNum(amountOc);
                                                                if (parseFloat(amountOc) != amountOc) {
                                                                    NConfirm.getConfirmModal().show({
                                                                        title: 'System Message',
                                                                        disableClose: true,
                                                                        messages: ['Amount(OC) only allows numbers to be filled in.']
                                                                    });
                                                                    evt.model.set("AmountOc", "");
                                                                }
                                                            }
                                                        }
                                                    }],
                                                    // {id: 'SettlementType',
                                                    //    listener: function (evt) {
                                                    //        var SettlementType = evt.model.get("SettlementType");
                                                    //        if(undefined != SettlementType && null != SettlementType){
                                                    //            if(SettlementType === "4" ){
                                                    //                evt.model.set("PostingFlag", '1');
                                                    //            }
                                                    //        }
                                                    //    }
                                                    //},{id:'PostingFlag',listener :function(evt){
                                                    //    var SettlementType = evt.model.get("SettlementType");
                                                    //    if(undefined != SettlementType && null != SettlementType){
                                                    //        if(SettlementType === "4" ){
                                                    //            evt.model.set("PostingFlag", '1');
                                                    //        }
                                                    //    }
                                                    //}

                                                    //editable: true,
                                                    //removable: true,
                                                    addable: true,
                                                    scrollX: true,
                                                    columns: [
                                                        {
                                                            title: "Contract Section",
                                                            data: "SectionId",
                                                            inline: "select",
                                                            codes: $page.model.contractSelection,
                                                            required: true,
                                                            width: 300
                                                        },
                                                        {
                                                            title: "Settlement Type",
                                                            data: "SettlementType",
                                                            inline: "select",
                                                            codes: $page.codes.SettlementType,
                                                            width: 200
                                                        },
                                                        {
                                                            title: "Original Currency",
                                                            data: "OriginalCurrency",
                                                            inline: "select",
                                                            codes: $page.codes.Currency,
                                                            width: 200
                                                        },
                                                        {
                                                            title: "Amount(OC)",
                                                            data: "AmountOc",
                                                            inline: "number",
                                                            width: 120
                                                        },
                                                        {
                                                            title: "Amount(USD)",
                                                            data: "AmountUsd",
                                                            required: true,
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.AmountUsd, 2);
                                                            //},
                                                            width: 120,
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
                                                        {
                                                            title: "Cedent Ref.",
                                                            data: "CedentRefer",
                                                            inline: "text",
                                                            width: 150
                                                        },
                                                        {
                                                            title: "Broker Ref.",
                                                            data: "BrokerRefer",
                                                            inline: "text",
                                                            width: 150
                                                        },
                                                        {
                                                            title: "Transfer to Accounts",
                                                            data: "PostingFlag",
                                                            inline: "radio",
                                                            codes: $page.codes.Boolean,
                                                            required: true,
                                                            width: 100
                                                        }
                                                    ],
                                                    addClick: function (model, item, layout) {
                                                        model.add("SettlementItemList", $.extend(true, {}, $page.model.settlementDetailTemplate()));
                                                        // model.add("SettlementItemList", item.getCurrentModel());
                                                    },
                                                    rowOperations: [
                                                        {
                                                            icon: "trash",
                                                            tooltip: "delete",
                                                            enabled: {
                                                                depends: "status",
                                                                when: function (rowModel) {
                                                                    //  console.log(rowModel.parent().getCurrentModel().Status);
                                                                    return rowModel.parent().getCurrentModel().Status == '1';
                                                                }
                                                            },
                                                            click: function (rowModel) {

                                                                if (rowModel.SettleDetailId == undefined || rowModel.SettleDetailId == "undefined" || rowModel.SettleDetailId == null) {
                                                                } else {
                                                                    $page.controller.model.add("DeleteSettlementItemList", rowModel);
                                                                    console.log($page.controller.model.get("DeleteSettlementItemList"));
                                                                }
                                                                this.getModel().remove(this.getDataId(), rowModel);
                                                            }


                                                        }
                                                    ]
                                                },
                                                css: {
                                                    comp: 'inline-editor',
                                                    cell: 'title-align'
                                                },
                                                pos: {
                                                    width: 12,
                                                    row: 1
                                                }
                                            },
                                            changeReasonPanel: {
                                                comp: {
                                                    type: $pt.ComponentConstants.Panel,
                                                    editLayout: {
                                                        remarkPanel: {
                                                            label: "Settlement Remarks",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Panel,
                                                                editLayout: {
                                                                    Remark: {
                                                                        comp: {
                                                                            type: {
                                                                                type: $pt.ComponentConstants.TextArea,
                                                                                label: false
                                                                            },
                                                                            lines: 4
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
                                                        }
                                                    }
                                                },
                                                pos: {
                                                    width: 6,
                                                    row: 2
                                                }
                                            },
                                            SettlementSummary: {
                                                label: "Summary",
                                                style: "primary",
                                                comp: {
                                                    type: $pt.ComponentConstants.Table,
                                                    sortable: false,
                                                    searchable: false,
                                                    columns: [
                                                        {
                                                            title: "",
                                                            data: "CurrencyType",
                                                            codes: $page.codes.CurrencySpecial,
                                                            width: "15%"
                                                        },
                                                        {
                                                            title: "Gross Amount",
                                                            data: "GrossTotal",
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.GrossTotal, 2);
                                                            //},
                                                            width: "20%",
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
                                                        },
                                                        {
                                                            title: "RIP",
                                                            data: "RIPTotal",
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.RIPTotal, 2);
                                                            //},
                                                            width: "15%",
                                                            inline: {
                                                                label: {
                                                                    comp: {
                                                                        type: {
                                                                            label: false,
                                                                            popover: false,
                                                                            render: function (model) {
                                                                                return $helper.formatNumberForLabel(model.get("RIPTotal"), 2);
                                                                            }
                                                                        }
                                                                    },
                                                                    pos: {width: 12},
                                                                    css: {cell: 'currency-align-right'}
                                                                }
                                                            }
                                                        },
                                                        {
                                                            title: "Tax",
                                                            data: "TaxTotal",
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.TaxTotal, 2);
                                                            //},
                                                            width: "15%",
                                                            inline: {
                                                                label: {
                                                                    comp: {
                                                                        type: {
                                                                            label: false,
                                                                            popover: false,
                                                                            render: function (model) {
                                                                                return $helper.formatNumberForLabel(model.get("TaxTotal"), 2);
                                                                            }
                                                                        }
                                                                    },
                                                                    pos: {width: 12},
                                                                    css: {cell: 'currency-align-right'}
                                                                }
                                                            }
                                                        },
                                                        {
                                                            title: "Others",
                                                            data: "OthersTotal",
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.OthersTotal, 2);
                                                            //},
                                                            width: "15%",
                                                            inline: {
                                                                label: {
                                                                    comp: {
                                                                        type: {
                                                                            label: false,
                                                                            popover: false,
                                                                            render: function (model) {
                                                                                return $helper.formatNumberForLabel(model.get("OthersTotal"), 2);
                                                                            }
                                                                        }
                                                                    },
                                                                    pos: {width: 12},
                                                                    css: {cell: 'currency-align-right'}
                                                                }
                                                            }
                                                        },
                                                        {
                                                            title: "Net Amount",
                                                            data: "NetTotal",
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.NetTotal, 2);
                                                            //},
                                                            width: "20%",
                                                            inline: {
                                                                label: {
                                                                    comp: {
                                                                        type: {
                                                                            label: false,
                                                                            popover: false,
                                                                            render: function (model) {
                                                                                return $helper.formatNumberForLabel(model.get("NetTotal"), 2);
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
                                                    comp: 'inline-editor',
                                                    cell: 'title-align'
                                                },
                                                pos: {width: 6, row: 2}
                                            }
                                            // SettlementUsdEquivalent : {
                                            //     label: "USD Equivalent",
                                            //     comp :{
                                            //       type : $pt.ComponentConstants.Text,
                                            //       enabled: false
                                            //     },
                                            //     pos: {width: 4, row: 2}
                                            // }
                                        }
                                    }
                                },
                                settleDocUpload: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [{
                                                text: "Attachment",
                                                style: "primary",
                                                click: function (model) {

                                                  var readOnly = 0 ;
                                                  if($page.pageType==2){
                                                    readOnly=1;
                                                  }
                                                  $pt.viewAttachment(3, model.get("ClaimId"), readOnly);
                                                    // var claimID = model.get("ClaimId");
                                                    // var uploadUrl = "../Document/documentUpload.html?businessId=" + claimID + "&businessType=3";
                                                    // window.open(uploadUrl);
                                                }
                                            }]
                                        }
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
                    }
                }
            };
        },
        createDetailRetrocessionTab: function () {
            return {
                label: "Retrocession",
                icon: "bookmark",
                editLayout: {
                    lossReverse: {
                        label: "Reserve",
                        pos: {
                            width: 12,
                            row: 2
                        },
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            style: "primary-darken",
                            collapsible: true,
                            expanded: true,
                            editLayout: {
                                headButtons: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            left: [{
                                                text: "Add Retrocession Contract Section",
                                                style: "primary",
                                                icon: "plus",
                                                enabled: {
                                                    depends: "status",
                                                    when: function () {
                                                        return $page.pageType == 0;
                                                    }
                                                },
                                                click: function () {
                                                    var treeCodesData = null;
                                                    var dialogModel = null;
                                                    $page.controller.addContractSection(treeCodesData, dialogModel, 2);
                                                }
                                            }],
                                            right: [{
                                                text: "Reserve History",
                                                style: "link",
                                                click: function (model) {
                                                    console.log(model.get("ClaimId"));
                                                    var claimID = model.get("ClaimId");
                                                    var url = $pt.getURL('ui.claim.reserveHistory');
                                                    window.open(url + "?refId=" + claimID + "&businessDirection=2");
                                                    }
                                            }]
                                        }
                                    },
                                    pos: {
                                        width: 12,
                                        row: 1
                                    }
                                },
                                ReserveListRetro: {
                                    label: "",
                                    comp: {
                                        centralId: 'reserveTable',
                                        type: $pt.ComponentConstants.Table,
                                        sortable: false,
                                        searchable: false,
                                        rowListener: [{
                                            id: 'RetroRefSectionId',
                                            listener: function (evt) {
                                                var RetroRefSectionId = evt.model.get("RetroRefSectionId");
                                                var ReserveList = evt.model.__parent.__model.ReserveList;
                                                if (ReserveList != undefined && ReserveList != null) {
                                                    var SectionId = [];
                                                    for (var i = 0; i < ReserveList.length; i++) {
                                                        //SectionId[i] = ReserveList[i].SectionId;
                                                        SectionId.push(ReserveList[i].SectionId);
                                                    }
                                                    var flag = false;
                                                    for (var j = 0; j < SectionId.length; j++) {
                                                        if (RetroRefSectionId === SectionId[j]) {
                                                            flag = true;
                                                        }
                                                    }
                                                    if (flag == false) {
                                                        NConfirm.getConfirmModal().show({
                                                            title: 'System Message',
                                                            disableClose: true,
                                                            messages: ['There is no financial reserve under this claim section.']
                                                        });
                                                        evt.model.set("RetroRefSectionId", null);
                                                    }
                                                } else {
                                                    NConfirm.getConfirmModal().show({
                                                        title: 'System Message',
                                                        disableClose: true,
                                                        messages: ['There is no financial reserve under this claim section.']
                                                    });
                                                    evt.model.set("RetroRefSectionId", null);
                                                }

                                            }
                                        }, {
                                            id: 'SectionId',
                                            listener: function (evt) {
                                                //var SectionId = evt.model.get("SectionId");
                                                var ReserveType = evt.model.get("ReserveType");
                                                var OriginalCurrency = evt.model.get("OriginalCurrency");
                                                var SectionId = evt.model.get("SectionId");
                                                if (null != SectionId && "" != SectionId) {
                                                    $page.controller.getPosting(SectionId);
                                                    if ($page.postingFlag == false) {
                                                        evt.model.set("PostingFlag", '0');
                                                    } else {
                                                        evt.model.set("PostingFlag", '1');
                                                    }
                                                }
                                                if (undefined != ReserveType && null != ReserveType) {
                                                    if (ReserveType === "3") {
                                                        evt.model.set("PostingFlag", '1');
                                                    }
                                                }
                                                console.log(evt.model);
                                                if (undefined != SectionId && undefined != ReserveType && undefined != OriginalCurrency) {
                                                    var nowReserveItem = SectionId + ReserveType + OriginalCurrency;
                                                    var ReserveItems = [];
                                                    if ($page.ReserveRetroItems != undefined && $page.ReserveRetroItems != null && "" != $page.ReserveRetroItems) {
                                                        ReserveItems = $page.ReserveRetroItems;
                                                        var num = ReserveItems.length;
                                                        for (var i = 0; i < num; i++) {
                                                            var reserveItems = ReserveItems[i];
                                                            if (nowReserveItem === reserveItems) {
                                                                NConfirm.getConfirmModal().show({
                                                                    title: 'System Message',
                                                                    disableClose: true,
                                                                    messages: ['There is an existing reserve under selected conditions, you cannot add a same one.']
                                                                });
                                                                evt.model.set("SectionId", null);
                                                            }
                                                        }
                                                        ReserveItems[num] = nowReserveItem;
                                                        num = num + 1;
                                                        var AllReserveList = evt.model.__parent.__model.ReserveListRetro;
                                                        var deleteReserveList = $page.controller.model.get("DeleteReserveList");
                                                        $page.controller.checkReserveItem(AllReserveList, deleteReserveList, ReserveItems);
                                                    } else {
                                                        ReserveItems.push(nowReserveItem);
                                                        $page.ReserveRetroItems = ReserveItems;
                                                    }
                                                }
                                            }
                                        }, {
                                            id: 'ReserveType',
                                            listener: function (evt) {
                                                var SectionId = evt.model.get("SectionId");
                                                var ReserveType = evt.model.get("ReserveType");
                                                var OriginalCurrency = evt.model.get("OriginalCurrency");
                                                if (undefined != ReserveType && null != ReserveType) {
                                                    if (ReserveType === "3") {
                                                        evt.model.set("PostingFlag", '1');
                                                    }else{
                                                        if (ReserveType === "5" || ReserveType === "6"){
                                                            evt.model.set("PostingFlag", '0');
                                                        }
                                                        if (null != SectionId && undefined != SectionId) {
                                                            $page.controller.getPosting(SectionId);
                                                            if ($page.postingFlag == false) {
                                                                evt.model.set("PostingFlag", '0');
                                                            } else {
                                                                evt.model.set("PostingFlag", '1');
                                                            }
                                                        }
                                                    }
                                                }
                                                if (undefined != SectionId && undefined != ReserveType && undefined != OriginalCurrency) {
                                                    var nowReserveItem = SectionId + ReserveType + OriginalCurrency;
                                                    var ReserveItems = [];
                                                    if ($page.ReserveRetroItems != undefined && $page.ReserveRetroItems != null && "" != $page.ReserveRetroItems) {
                                                        ReserveItems = $page.ReserveRetroItems;
                                                        var num = ReserveItems.length;
                                                        for (var i = 0; i < num; i++) {
                                                            var reserveItems = ReserveItems[i];
                                                            if (nowReserveItem === reserveItems) {
                                                                NConfirm.getConfirmModal().show({
                                                                    title: 'System Message',
                                                                    disableClose: true,
                                                                    messages: ['There is an existing reserve under selected conditions, you cannot add a same one.']
                                                                });
                                                                evt.model.set("ReserveType", null);
                                                            }
                                                        }
                                                        ReserveItems[num] = nowReserveItem;
                                                        num = num + 1;
                                                        var AllReserveList = evt.model.__parent.__model.ReserveListRetro;
                                                        var deleteReserveList = $page.controller.model.get("DeleteReserveList");
                                                        $page.controller.checkReserveItem(AllReserveList, deleteReserveList, ReserveItems);
                                                    } else {
                                                        ReserveItems.push(nowReserveItem);
                                                        $page.ReserveRetroItems = ReserveItems;
                                                    }
                                                }
                                            }
                                        }, {
                                            id: 'OriginalCurrency',
                                            listener: function (evt) {
                                                var SectionId = evt.model.get("SectionId");
                                                var ReserveType = evt.model.get("ReserveType");
                                                var OriginalCurrency = evt.model.get("OriginalCurrency");
                                                if (undefined != SectionId && undefined != ReserveType && undefined != OriginalCurrency) {
                                                    var nowReserveItem = SectionId + ReserveType + OriginalCurrency;
                                                    var ReserveItems = [];
                                                    if ($page.ReserveRetroItems != undefined && $page.ReserveRetroItems != null && "" != $page.ReserveRetroItems) {
                                                        ReserveItems = $page.ReserveRetroItems;
                                                        var num = ReserveItems.length;
                                                        for (var i = 0; i < num; i++) {
                                                            var reserveItems = ReserveItems[i];
                                                            if (nowReserveItem === reserveItems) {
                                                                NConfirm.getConfirmModal().show({
                                                                    title: 'System Message',
                                                                    disableClose: true,
                                                                    messages: ['There is an existing reserve under selected conditions, you cannot add a same one.']
                                                                });
                                                                evt.model.set("OriginalCurrency", null);
                                                            }
                                                        }
                                                        ReserveItems[num] = nowReserveItem;
                                                        num = num + 1;
                                                        var AllReserveList = evt.model.__parent.__model.ReserveListRetro;
                                                        var deleteReserveList = $page.controller.model.get("DeleteReserveList");
                                                        $page.controller.checkReserveItem(AllReserveList, deleteReserveList, ReserveItems);
                                                    } else {
                                                        ReserveItems.push(nowReserveItem);
                                                        $page.ReserveRetroItems = ReserveItems;
                                                    }
                                                }
                                            }
                                        }, {
                                            id: 'AmountOc',
                                            listener: function (evt) {
                                                var amountOc = evt.model.get("AmountOc");
                                                if (null != amountOc && "" != amountOc && undefined != amountOc) {
                                                    //var flag = $page.controller.checkNum(amountOc);
                                                    if (parseFloat(amountOc) != amountOc) {
                                                        NConfirm.getConfirmModal().show({
                                                            title: 'System Message',
                                                            disableClose: true,
                                                            messages: [' Amount(OC) only allows numbers to be filled in.']
                                                        });
                                                        evt.model.set("AmountOc", "");
                                                    }
                                                }
                                            }
                                        }, {
                                            id: 'PostingFlag', listener: function (evt) {
                                                var ReserveType = evt.model.get("ReserveType");
                                                var PostingFlag = evt.model.get("PostingFlag");
                                                if (undefined != ReserveType && null != ReserveType) {
                                                    if (ReserveType === "3") {
                                                        evt.model.set("PostingFlag", '1');
                                                    }
                                                }
                                            }
                                        }],
                                        //editable: true,
                                        //removable: true,
                                        addable: true,
                                        "scrollX": true,
                                        columns: [
                                            {
                                                title: "Retro Section",
                                                data: "SectionId",
                                                codes: $page.model.rcontractSelection,
                                                inline: registerInlineSelect.call(window, "RetroSection", $page.model.rcontractSelection, $pt.ComponentConstants.Select),
                                                width: 300
                                            },
                                            {
                                                title: "Claim Section",
                                                data: "RetroRefSectionId",
                                                inline: registerInlineSelect.call(window, "ClaimSection", $page.model.contractSelection, $pt.ComponentConstants.Select),
                                                codes: $page.model.contractSelection,
                                                width: 300
                                            },
                                            {
                                                title: "Reserve Type",
                                                data: "ReserveType",
                                                inline: registerInlineSelect.call(window, "ReserveType", $page.codes.ReserveType, $pt.ComponentConstants.Select),
                                                codes: $page.codes.ReserveType,
                                                width: 150
                                            },
                                            {
                                                title: "Original Currency",
                                                data: "OriginalCurrency",
                                                inline: registerInlineSelect.call(window, "OriginalCurrency", $page.codes.Currency, $pt.ComponentConstants.Select),
                                                codes: $page.codes.Currency,
                                                width: 120
                                            },
                                            {
                                                title: "Submitted Amount(OC)",
                                                data: "OrgAmountOc",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.OrgAmountOc, 2);
                                                //},
                                                width: 160,
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("OrgAmountOc"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Latest Entered Amount(OC)",
                                                data: "AmountOc",
                                                inline: "number",
                                                width: 180
                                            },
                                            {
                                                title: "Submitted Amount(USD)",
                                                data: "OrgAmountUsd",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.OrgAmountUsd, 2);
                                                //},
                                                width: 170,
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("OrgAmountUsd"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Latest Entered Amount(USD)",
                                                data: "AmountUsd",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.AmountUsd, 2);
                                                //},
                                                width: 190,
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
                                            {
                                                title: "Cedent Ref.",
                                                data: "CedentRefer",
                                                inline: "text",
                                                //inline: registerInlineSelect.call(window, "CedentRefer", null, $pt.ComponentConstants.Text),
                                                width: 150
                                            },
                                            {
                                                title: "Broker Ref.",
                                                data: "BrokerRefer",
                                                inline: "text",
                                                //inline: registerInlineSelect.call(window, "BrokerRefer", null, $pt.ComponentConstants.Text),
                                                width: 150
                                            },
                                            {
                                                title: "Transfer to Accounts",
                                                data: "PostingFlag",
                                                //inline: "radio",
                                                //codes: $page.codes.Boolean,
                                                width: 100,
                                                inline: {
                                                    PostingFlag:{
                                                        comp:{
                                                            type: {type: $pt.ComponentConstants.Radio, label: false},
                                                            data: $page.codes.Boolean,
                                                            enabled: {
                                                                depends: 'ReserveType',
                                                                when: function (rowModel) {
                                                                    var reserveType = rowModel.get("ReserveType");
                                                                    //console.info(reserveType);
                                                                    if (reserveType == "5" || reserveType == "6"){
                                                                        return false;
                                                                    }
                                                                    return true;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        ],
                                        addClick: function (model, item, layout) {
                                            // model.add("ReserveListRetro", item.getCurrentModel());
                                            model.add("ReserveListRetro", $.extend(true, {}, $page.model.reserveArrayTemplate()));
                                        },
                                        rowOperations: [
                                            {
                                                icon: "trash",
                                                tooltip: "delete",
                                                enabled: {
                                                    depends: "status",
                                                    when: function (rowModel) {
                                                        return rowModel.getCurrentModel().Status == '0';
                                                    }
                                                },
                                                click: function (rowModel) {
                                                    if (rowModel.ReserveId == undefined || rowModel.ReserveId == "undefined") {
                                                    } else {
                                                        $page.controller.model.add("DeleteReserveList", rowModel);
                                                        console.log($page.controller.model.get("DeleteReserveList"));
                                                    }
                                                    this.getModel().remove(this.getDataId(), rowModel);
                                                }


                                            }
                                        ]

                                    },
                                    css: {
                                        comp: 'inline-editor',
                                        cell: 'title-align'
                                    },
                                    pos: {
                                        width: 12,
                                        row: 1
                                    }
                                },
                                changeReasonPanel: {
                                    comp: {
                                        type: $pt.ComponentConstants.Panel,
                                        editLayout: {
                                            remarkPanel: {
                                                label: "Reserve Remarks",
                                                comp: {
                                                    type: $pt.ComponentConstants.Panel,
                                                    editLayout: {
                                                        ReserveUpdateRemarkRetro: {
                                                            comp: {
                                                                type: {
                                                                    type: $pt.ComponentConstants.TextArea,
                                                                    label: false
                                                                },
                                                                lines: 4
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
                                            }
                                        }
                                    },
                                    pos: {
                                        width: 5,
                                        row: 2
                                    }
                                },
                                ReserveSummaryRetro: {
                                    label: "Summary",
                                    style: "primary",
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        sortable: false,
                                        searchable: false,
                                        columns: [
                                            {
                                                title: "",
                                                data: "CurrencyType",
                                                codes: $page.codes.CurrencySpecial,
                                                width: "15%"
                                            },
                                            {
                                                title: "Loss Indemnity & Expense",
                                                data: "LossTotal",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.LossTotal, 2);
                                                //},
                                                width: "25%",
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
                                            },
                                            {
                                                title: "ACR",
                                                data: "ACRTotal",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.ACRTotal, 2);
                                                //},
                                                width: "15%",
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("ACRTotal"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "RIP",
                                                data: "RIPTotal",
                                                render: function (row) {
                                                    return $helper.formatNumberForLabel(row.RIPTotal, 2);
                                                },
                                                width: "15%",
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("RIPTotal"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Tax",
                                                data: "TaxTotal",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.TaxTotal, 2);
                                                //},
                                                width: "15%",
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("TaxTotal"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Others",
                                                data: "OthersTotal",
                                                //render: function (row) {
                                                //    return $helper.formatNumberForLabel(row.OthersTotal, 2);
                                                //},
                                                width: "15%",
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("OthersTotal"), 2);
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
                                        comp: 'inline-editor',
                                        cell: 'title-align'
                                    },
                                    pos: {width: 7, row: 2}
                                }
                                // ReserveUsdEquivalentRetro : {
                                //     label: "USD Equivalent",
                                //     comp :{
                                //       type : $pt.ComponentConstants.Text,
                                //       enabled: false
                                //     },
                                //     pos: {width: 4, row: 2}
                                // }
                            }
                        }
                    },
                    settlement: {
                        label: "Settlement",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            style: "primary-darken",
                            collapsible: true,
                            expanded: true,
                            editLayout: {
                                headButtons: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            left: [{
                                                text: "Add Settlement",
                                                style: "primary",
                                                icon: "plus",
                                                enabled: {
                                                    depends: "status",
                                                    when: function () {
                                                        return $page.pageType == 0;
                                                    }
                                                },
                                                click: function (model) {
                                                    //    console.log(model.get("settlementArray"));
                                                    var settlementModel = $.extend(true, {}, $page.model.settlementArrayTemplate());
                                                    $page.controller.getSettlementName(settlementModel);
                                                    model.add("SettlementListRetro", settlementModel);

                                                }
                                            }],
                                            right: [{
                                                text: "Settlement History",
                                                style: "link",
                                                click: function (model) {
                                                    var claimID = model.get("ClaimId");
                                                    var url = $pt.getURL('ui.claim.settlementHistory');
                                                    window.open(url + "?refId=" + claimID + "&businessDirection=2");
                                                }
                                            }, {
                                                text: "Change Posting",
                                                style: "link",
                                                enabled: {
                                                    depends: "status",
                                                    when: function () {
                                                        return $page.pageType == 0;
                                                    }
                                                },
                                                click: function (model) {
                                                    var claimID = model.get("ClaimId");
                                                    var url = $pt.getURL('ui.claim.changePosting');
                                                    window.open(url + "?refId=" + claimID + "&businessDirection=2");
                                                }
                                            }]
                                        }
                                    },
                                    pos: {
                                        width: 12,
                                        row: 5
                                    }
                                },
                                settlementPanel: {
                                    label: "Settlement",
                                    style: "primary",
                                    pos: {
                                        width: 12,
                                        row: 6
                                    },
                                    dataId: "SettlementListRetro",
                                    comp: {
                                        type: $pt.ComponentConstants.ArrayPanel,
                                        itemTitle: {
                                            dataId: "SettlementName",
                                            depends: 'SettlementName',
                                            when: function (item) {
                                                return item.get("SettlementName");
                                            }
                                        },
                                        headerButtons : {
                                          text : 'delete',
                                          visible: {
                                            depends: 'settlementId',
                                            when: function (item) {
                                                return null == item.getCurrentModel().SettlementId || undefined == item.getCurrentModel();
                                            }
                                          },
                                          click: function (item) {
                                              $page.controller.onRemoveSettlementClicked("SettlementListRetro",item);
                                          }
                                        },
                                        editLayout: {
                                            DateOfReceipt: {
                                                label: "Date of Receipt",
                                                comp: {
                                                    type: $pt.ComponentConstants.Date,
                                                    format: "DD/MM/YYYY",
                                                    required:true
                                                },
                                                pos: {
                                                    row: 1
                                                }
                                            },
                                            Status: {
                                                label: "Status",
                                                comp: {
                                                    type: $pt.ComponentConstants.Select,
                                                    data: $page.codes.SettleStatus,
                                                    enabled: false
                                                },
                                                pos: {
                                                    row: 1
                                                }
                                            },
                                            SettlementItemList: {
                                                label: "",
                                                comp: {
                                                    type: $pt.ComponentConstants.Table,
                                                    sortable: false,
                                                    searchable: false,
                                                    rowListener: [{
                                                        id: 'SectionId',
                                                        listener: function (evt) {
                                                            var SectionId = evt.model.get("SectionId");
                                                            var SettlementType = evt.model.get("SettlementType");
                                                            if (null != SectionId && "" != SectionId) {
                                                                // meiliang.zou modify 2016.5.24 start
                                                                /*$page.controller.getPosting(SectionId);
                                                                 if ($page.postingFlag == false) {
                                                                 evt.model.set("PostingFlag", '0');
                                                                 } else {
                                                                 evt.model.set("PostingFlag", '1');
                                                                 }*/
                                                                evt.model.set("PostingFlag", '1');
                                                                // meiliang.zou modify 2016.5.24 end
                                                            }
                                                            //if(undefined != SettlementType && null != SettlementType){
                                                            //    if(SettlementType === "4" ){
                                                            //        evt.model.set("PostingFlag", '1');
                                                            //    }
                                                            //}
                                                        }
                                                    }, {
                                                        id: 'AmountOc',
                                                        listener: function (evt) {
                                                            var amountOc = evt.model.get("AmountOc");
                                                            if (null != amountOc && "" != amountOc && undefined != amountOc) {
                                                                //var flag = $page.controller.checkNum(amountOc);
                                                                if (parseFloat(amountOc) != amountOc) {
                                                                    NConfirm.getConfirmModal().show({
                                                                        title: 'System Message',
                                                                        disableClose: true,
                                                                        messages: ['Amount(OC) only allows numbers to be filled in.']
                                                                    });
                                                                    evt.model.set("AmountOc", "");
                                                                }
                                                            }
                                                        }
                                                    }],
                                                    //    },{id: 'SettlementType',
                                                    //    listener: function (evt) {
                                                    //        var SettlementType = evt.model.get("SettlementType");
                                                    //        if(undefined != SettlementType && null != SettlementType){
                                                    //            if(SettlementType === "4" ){
                                                    //                evt.model.set("PostingFlag", '1');
                                                    //            }
                                                    //        }
                                                    //    }
                                                    //},{id:'PostingFlag',listener :function(evt){
                                                    //    var SettlementType = evt.model.get("SettlementType");
                                                    //    if(undefined != SettlementType && null != SettlementType){
                                                    //        if(SettlementType === "4" ){
                                                    //            evt.model.set("PostingFlag", '1');
                                                    //        }
                                                    //    }

                                                    //editable: true,
                                                    //removable: true,
                                                    addable: true,
                                                    scrollX: true,
                                                    columns: [
                                                        {
                                                            title: "Retro Section",
                                                            data: "SectionId",
                                                            inline: "select",
                                                            codes: $page.model.rcontractSelection,
                                                            width: 300
                                                        },
                                                        {
                                                            title: "Claim Section",
                                                            data: "RetroRefSectionId",
                                                            inline: "select",
                                                            codes: $page.model.contractSelection,
                                                            width: 300
                                                        },
                                                        {
                                                            title: "Settlement Type",
                                                            data: "SettlementType",
                                                            inline: "select",
                                                            codes: $page.codes.SettlementType,
                                                            width: 150
                                                        },
                                                        {
                                                            title: "Original Currency",
                                                            data: "OriginalCurrency",
                                                            inline: "select",
                                                            codes: $page.codes.Currency,
                                                            width: 140
                                                        },
                                                        {
                                                            title: "Amount(OC)",
                                                            data: "AmountOc",
                                                            inline: "number",
                                                            width: 120
                                                        },
                                                        {
                                                            title: "Amount(USD)",
                                                            data: "AmountUsd",
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.AmountUsd, 2);
                                                            //},
                                                            width: 120,
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
                                                        {
                                                            title: "Cedent Ref.",
                                                            data: "CedentRefer",
                                                            inline: "text",
                                                            required: true,
                                                            width: 150
                                                        },
                                                        {
                                                            title: "Broker Ref.",
                                                            data: "BrokerRefer",
                                                            inline: "text",
                                                            width: 150
                                                        },
                                                        {
                                                            title: "Transfer to Accounts",
                                                            data: "PostingFlag",
                                                            inline: "radio",
                                                            codes: $page.codes.Boolean,
                                                            width: 100
                                                        }
                                                    ],
                                                    addClick: function (model, item, layout) {
                                                        model.add("SettlementItemList", $.extend(true, {}, $page.model.settlementDetailTemplate()));
                                                        // model.add("SettlementItemList", item.getCurrentModel());
                                                    },
                                                    //addClick: function (model, item, layout) {
                                                    //    model.add("SettlementItemList", item.getCurrentModel());
                                                    //},
                                                    rowOperations: [
                                                        {
                                                            icon: "trash",
                                                            tooltip: "delete",
                                                            enabled: {
                                                                depends: "status",
                                                                when: function (rowModel) {
                                                                    //console.log(rowModel.parent().getCurrentModel().Status);
                                                                    return rowModel.parent().getCurrentModel().Status == '1';
                                                                }
                                                            },
                                                            click: function (rowModel) {
                                                                if (rowModel.SettleDetailId == undefined || rowModel.SettleDetailId == "undefined" || rowModel.SettleDetailId == null) {
                                                                } else {
                                                                    $page.controller.model.add("DeleteSettlementItemList", rowModel);
                                                                    console.log($page.controller.model.get("DeleteSettlementItemList"));
                                                                }


                                                                this.getModel().remove(this.getDataId(), rowModel);
                                                            }


                                                        }
                                                    ]
                                                },
                                                css: {
                                                    comp: 'inline-editor',
                                                    cell: 'title-align'
                                                },
                                                pos: {
                                                    width: 12,
                                                    row: 1

                                                }
                                            },
                                            changeReasonPanel: {
                                                comp: {
                                                    type: $pt.ComponentConstants.Panel,
                                                    editLayout: {
                                                        remarkPanel: {
                                                            label: "Settlement Remarks",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Panel,
                                                                editLayout: {
                                                                    Remark: {
                                                                        comp: {
                                                                            type: {
                                                                                type: $pt.ComponentConstants.TextArea,
                                                                                label: false
                                                                            },
                                                                            lines: 4
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
                                                        }

                                                    }
                                                },
                                                pos: {
                                                    width: 6,
                                                    row: 2
                                                }
                                            },
                                            SettlementSummaryRetro: {
                                                label: "Summary",
                                                style: "primary",
                                                comp: {
                                                    type: $pt.ComponentConstants.Table,
                                                    sortable: false,
                                                    searchable: false,
                                                    columns: [
                                                        {
                                                            title: "",
                                                            data: "CurrencyType",
                                                            codes: $page.codes.CurrencySpecial,
                                                            width: "15%"
                                                        },
                                                        {
                                                            title: "Gross Amount",
                                                            data: "GrossTotal",
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.GrossTotal, 2);
                                                            //},
                                                            width: "20%",
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
                                                        },
                                                        {
                                                            title: "RIP",
                                                            data: "RIPTotal",
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.RIPTotal, 2);
                                                            //},
                                                            width: "15%",
                                                            inline: {
                                                                label: {
                                                                    comp: {
                                                                        type: {
                                                                            label: false,
                                                                            popover: false,
                                                                            render: function (model) {
                                                                                return $helper.formatNumberForLabel(model.get("RIPTotal"), 2);
                                                                            }
                                                                        }
                                                                    },
                                                                    pos: {width: 12},
                                                                    css: {cell: 'currency-align-right'}
                                                                }
                                                            }
                                                        },
                                                        {
                                                            title: "Tax",
                                                            data: "TaxTotal",
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.TaxTotal, 2);
                                                            //},
                                                            width: "15%",
                                                            inline: {
                                                                label: {
                                                                    comp: {
                                                                        type: {
                                                                            label: false,
                                                                            popover: false,
                                                                            render: function (model) {
                                                                                return $helper.formatNumberForLabel(model.get("TaxTotal"), 2);
                                                                            }
                                                                        }
                                                                    },
                                                                    pos: {width: 12},
                                                                    css: {cell: 'currency-align-right'}
                                                                }
                                                            }
                                                        },
                                                        {
                                                            title: "Others",
                                                            data: "OthersTotal",
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.OthersTotal, 2);
                                                            //},
                                                            width: "15%",
                                                            inline: {
                                                                label: {
                                                                    comp: {
                                                                        type: {
                                                                            label: false,
                                                                            popover: false,
                                                                            render: function (model) {
                                                                                return $helper.formatNumberForLabel(model.get("OthersTotal"), 2);
                                                                            }
                                                                        }
                                                                    },
                                                                    pos: {width: 12},
                                                                    css: {cell: 'currency-align-right'}
                                                                }
                                                            }
                                                        },
                                                        {
                                                            title: "Net Amount",
                                                            data: "NetTotal",
                                                            //render: function (row) {
                                                            //    return $helper.formatNumberForLabel(row.NetTotal, 2);
                                                            //},
                                                            width: "20%",
                                                            inline: {
                                                                label: {
                                                                    comp: {
                                                                        type: {
                                                                            label: false,
                                                                            popover: false,
                                                                            render: function (model) {
                                                                                return $helper.formatNumberForLabel(model.get("NetTotal"), 2);
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
                                                    comp: 'inline-editor',
                                                    cell: 'title-align'
                                                },
                                                pos: {width: 6, row: 2}
                                            }
                                            // SettlementUsdEquivalentRetro : {
                                            //     label: "USD Equivalent",
                                            //     comp :{
                                            //       type : $pt.ComponentConstants.Text,
                                            //       enabled: false
                                            //     },
                                            //     pos: {width: 4, row: 2}
                                            // }

                                        }
                                    }
                                },
                                settleDocUpload: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [{
                                                text: "Attachment",
                                                style: "primary",
                                                click: function (model) {
                                                  var readOnly = 0 ;
                                                  if($page.pageType==2){
                                                    readOnly=1;
                                                  }
                                                  $pt.viewAttachment(3, model.get("ClaimId"), readOnly);
                                                    // var claimID = model.get("ClaimId");
                                                    // var uploadUrl = "../Document/documentUpload.html?businessId=" + claimID + "&businessType=3";
                                                    // window.open(uploadUrl);
                                                }
                                            }]
                                        }
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
                    }
                }
            };
        },
        createDetailInfo: function () {
            //console.log(this.createDetailFinanicalTab());
            return {
                comp: {
                    type: $pt.ComponentConstants.Panel,
                    editLayout: {
                        formTab: {
                            comp: {
                                type: $pt.ComponentConstants.Tab,
                                justified: false,
                                style: 'primary',
                                //tabType:"pill",
                                "titleDirection": "horizontal",
                                //canRemove:true,
                                tabs: [this.createDetailFinanicalTab(), this.createDetailRetrocessionTab()]
                            },
                            pos: {
                                width: 12
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
                                            //this.model = $pt.createModel($page.model.createModel());
                                            var urlData = $pt.getUrlData();
                                            //this.model.mergeCurrentModel(urlData);
                                            var exitBool = urlData.Exit;
                                            if(exitBool==1){
                                                window.close();
                                            }else{
                                              $page.controller.exit();
                                            }
                                        }
                                    }, {
                                        text: "Reopen Claim",
                                        style: "primary",
                                        enabled: {
                                            depends: "status",
                                            when: function (model) {
                                                return $page.pageType == 1;
                                            }
                                        },
                                        click: function (model) {
                                            //window.location.href = "claimQuery.html";
                                            var controller = $page.controller;
                                            var reopenModel = $.extend({}, model.getCurrentModel());
                                            // console.debug(model);
                                            controller.reopenClaim(reopenModel)
                                        }

                                    }, {
                                        text: "Close Claim",
                                        style: "warning",
                                        enabled: {
                                            depends: "status",
                                            when: function (model) {
                                                return $page.pageType == 0;
                                            }
                                        },
                                        click: function (model) {
                                            //window.location.href = "claimQuery.html";
                                            var controller = $page.controller;
                                            var closeModel = $.extend({}, model.getCurrentModel());
                                            // console.debug(model);
                                            controller.closeClaimInfo(closeModel)
                                        }
                                    }, {
                                        text: "Submit",
                                        style: "primary",
                                        enabled: {
                                            depends: "status",
                                            when: function (model) {
                                                return $page.pageType == 0;
                                            }
                                        },
                                        click: function (model) {
                                            //window.location.href = "claimQuery.html";
                                            var controller = $page.controller;
                                            var submitModel = $.extend({}, model.getCurrentModel());
                                            // console.debug(model);
                                            controller.submitClaimInfo(submitModel)
                                        }
                                    }, {
                                        text: "Save",
                                        style: "primary",
                                        enabled: {
                                            depends: "status",
                                            when: function (model) {
                                                return $page.pageType == 0;
                                            }
                                        },
                                        click: function (model) {
                                            console.debug("save button on click...");
                                            var controller = $page.controller;
                                            var saveModel = $.extend({}, model.getCurrentModel());
                                            // console.debug(model);
                                            controller.saveClaimInfo(saveModel);
                                        }
                                    }]
                                }
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
            };
        },
        createFormLayout: function () {

            return {
                _sections: {
                    claimInfoSection: this.createClaimInfo()
                },
                detailSection: this.createDetailInfo()
            }
        },
        createAddContractSectionDialogLayout: function (treeCodes, type) {
            return {
                ContractID: {
                    label: "Contract ID",
                    comp: {
                        type: $pt.ComponentConstants.NContractSearchText,
                        searchTriggerDigits: 6,
                        contractStatus: [4],
                        contractCategory:type,
                        url: $ri.getRestfulURL("action.contract.contractHome") + "/page"
                    },
                    pos: {
                        row: 1,
                        width: 5
                    }
                },
                UnderWritingYear: {
                    label: "Underwriting Year",
                    comp: {
                        type: $pt.ComponentConstants.Text,
                        //data: $page.codes.UWYear
                        enabled: false
                    },
                    pos: {
                        row: 1,
                        width: 4
                    }
                },
                SectionTree: {
                    dataId: "SectionTree",
                    comp: {
                        type: $pt.ComponentConstants.Tree,
                        root: false,
                        check: function (node) {
                           // console.log(node);
                            if (node.level == 1) {
                                return false;
                            } else {
                                return true;
                            }

                        },
                        valueAsArray: true,
                        data: treeCodes,

                        valueCanCheck:function(node){
                            //console.log(node);
                             return $page.controller.checkOutCompare(node, this.getModel(), type);
                        }
                    },
                    pos: {
                        row: 2,
                        width: 12
                    }
                },
                searchBtn: {
                    label: "Search",
                    comp: {
                        type: $pt.ComponentConstants.Button,
                        click: function (dialogModel) {
                            $page.controller.onAddContractSectionSearch(dialogModel, type);
                        }
                    },
                    pos: {
                        row: 1,
                        width: 3
                    }
                }
            };
        },
        createMessageLayout: function (messageCodes) {
            return {
                MessageTree: {
                    dataId: "MessageTree",
                    comp: {
                        type: $pt.ComponentConstants.Tree,
                        root: false,
                        check: "selected",
                        //hierarchyCheck: true,
                        valueAsArray: true,
                        data: messageCodes
                    },
                    pos: {
                        row: 2,
                        width: 12
                    }
                }
            }
        }


    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));
