(function (context) {
    var $page = $pt.getService(context, '$page');

    var registerInlineSelect = function (name, codes, type) {
        NTable.registerInlineEditor(name, {
            comp: {
                type: {type: type, label: false},
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
        createEventInfo: function () {
            return {

                label: "Event Information",
                style: 'primary-darken',
                pos: {
                    width: 12
                },
                layout: {//

                    headButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Message(" + $page.messageNum + ")",
                                    style: "link",
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
                    EventCode: {
                        label: "Event Code",
                        comp: {
                            type: {
                                labelDirection: "horizontal",
                                enabled: true
                            }
                        },
                        pos: {
                            row: 4,
                            width: 4
                        }
                    },
                    CauseOfLoss: {
                        label: "Cause of Loss",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            minimumResultsForSearch: -1,
                            data: $page.codes.Cause,
                            //allowClear : true,
                            placeholder: "please select..."
                        },
                        pos: {
                            row: 4,
                            width: 4
                        }
                    },
                    DateOfCreation: {
                        label: "Created on",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY HH:mm",
                            enabled: false
                            //valueFormat:$pt.ComponentConstants.Default_Date_Format,
                            //labelDirection: "horizontal"
                        },
                        pos: {
                            row: 4,
                            width: 4
                        }
                    },
                    DateOfLossFrom: {
                        label: "Loss Start Date",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY HH:mm"
                            //valueFormat:$pt.ComponentConstants.Default_Date_Format,
                            //labelDirection: "horizontal"
                        }
                    },
                    DateOfLossTo: {
                        label: "Loss End Date",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY HH:mm"
                            //valueFormat:$pt.ComponentConstants.Default_Date_Format,
                            //labelDirection: "horizontal"
                        }
                    },
                    lossDescriptionPanel: {
                        label: "Loss Description",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                LossDesc: {
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
                            width: 12
                        }
                    },
                    EventSummary: {
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
                    //ViewButton: {
                    //    label: "View Details Of Claim Record",
                    //    comp: {
                    //        type: $pt.ComponentConstants.Button,
                    //        style: "primary",
                    //        click: function () {
                    //            $page.controller.exportExcel();
                    //        }
                    //    },
                    //    pos: {
                    //        width:4
                    //    },
                    //
                    //},
                    //Button:{
                    //    label: "View Details Of Retrocession",
                    //    comp: {
                    //        type: $pt.ComponentConstants.Button,
                    //        style: "primary",
                    //        click: function () {
                    //            $page.controller.exportDetailExcel();
                    //        }
                    //    },
                    //    pos: {
                    //        width: 4
                    //    }
                    //},
                    buttonPanel2: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                left: [
                                    {
                                        text: "View Details Of Claim Record",
                                        style: "primary",
                                        click: function () {
                                            $page.controller.exportExcel();
                                        }
                                    }, {
                                        text: "View Details Of Retrocession",
                                        style: "primary",
                                        click: function () {
                                            $page.controller.exportDetailExcel();
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            width: 12
                        }
                    }
                }
            }
        },

        createClaimDetailTab: function () {
            return {

                label: "Claim Records",
                icon: "bookmark",
                editLayout: {
                    ContractCode: {
                        label: "Contract ID",
                        comp: {
                            //type: $pt.ComponentConstants.Search,
                            //searchTriggerDigits:6
                            //// minimumResultsForSearch: -1,
                            ////// data: $page.codes.Event,
                            //// //allowClear : true,
                            //// placeholder: "please select..."
                            type: $pt.ComponentConstants.NContractSearchText,
                            searchTriggerDigits: 6,
                            url: $ri.getRestfulURL("action.contract.contractHome") + "/page"
                        },
                        pos: {
                            row: 1,
                            width: 4
                        }
                    },
                    UwYear: {
                        label: "Underwriting Year",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            //minimumResultsForSearch: -1,
                            // data: $page.codes.UwYear
                            //allowClear : true,
                            // placeholder: "please select..."

                        },
                        pos: {
                            row: 1,
                            width: 4
                        }
                    },
                    ContractType: {
                        label: "Contract Type",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            //minimumResultsForSearch: -1,
                            data: $page.codes.ContractType
                        },
                        pos: {
                            row: 1,
                            width: 4
                        }
                    },
                    CedentName: {
                        label: "Cedent Name",
                        base: $helper.basePartnerSearch(),
                        pos: {
                            row: 2,
                            width: 4
                        }
                    },
                    BrokerName: {
                        label: "Broker Name",
                        base: $helper.basePartnerSearch(),
                        pos: {
                            row: 2,
                            width: 4
                        }
                    },
                    searchButton: {
                        label: "Search",
                        comp: {
                            type: $pt.ComponentConstants.Button,
                            style: "primary",
                            click: function () {
                                $page.controller.searchClaim();
                            }
                        },
                        pos: {
                            row: 2,
                            width: 4
                        }
                    },
                    claimRecords: {
                        label: "Claim Records",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                ClaimRecordsList: {
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
                                        scrollX: true,
                                        view:true,
                                        //criteria : "paginationCriteria",
                                        columns: [
                                            {title: "Claim No", data: "ClaimNo", width: 150},
                                            {title: "Broker", data: "Broker",inline:$helper.registerInlineBPSearch(),width: 250},
                                            {title: "Cedent", data: "Cedent", inline:$helper.registerInlineBPSearch(),width: 250},
                                            {title: "Contract ID", data: "ContractId", width: 100},
                                            {title: "Section / Sub Section", data: "SectionId", width: 150},
                                            //{title: "Sub Section", data: "subsection", width: 200},
                                            {
                                                title: "Loss Reverse (USD)",
                                                data: "LossReverse",
                                                //render : function(row){return $helper.formatNumberForLabel(row.LossReverse,2);},
                                                width: 150,
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("LossReverse"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Loss Paid (USD)",
                                                data: "LossPaid",
                                                //render : function(row){return $helper.formatNumberForLabel(row.LossPaid,2);},
                                                width: 150,
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("LossPaid"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Retro Loss Reserve (USD)",
                                                data: "RetroOS",
                                                //render : function(row){return $helper.formatNumberForLabel(row.RetroOS,2);},
                                                width: 150,
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("RetroOS"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Retro Loss Paid (USD)",
                                                data: "RetroPaid",
                                                //render : function(row){return $helper.formatNumberForLabel(row.RetroPaid,2);},
                                                width: 150,
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("RetroPaid"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            //{title: "RIP Reserve(In USD)", data: "RipReserve",render : function(row){return $helper.formatNumberForLabel(row.RipReserve,2);}, width: 200},
                                            //{title: "RIP Paid(In USD)", data: "RipPaid",render : function(row){return $helper.formatNumberForLabel(row.RipPaid,2);}, width: 200},
                                            {
                                                title: "Status",
                                                data: "Status",
                                                codes: $page.codes.ClaimStatus,
                                                width: 50
                                            },
                                            {
                                                title: "Date of Last Update",
                                                data: "DateOfLostUpdate",
                                                render: function (row) {
                                                    return $helper.formatDateForTableView(row.DateOfLostUpdate, "DD/MM/YYYY");
                                                },
                                                width: 100
                                            },
                                            {
                                                data: "ClaimId",
                                                visible: false
                                            }
                                        ],
                                        rowOperations: [
                                            {
                                                tooltip: "View",

                                                visible: {
                                                    depends: "status",
                                                    when: function (rowModel) {
                                                        return rowModel.getCurrentModel().ClaimId != undefined;
                                                    }
                                                },
                                                click: function (rowModel) {
                                                    $page.controller.loadClaim(rowModel.ClaimId, 2);
                                                }
                                            }
                                        ],
                                        addClick: function (model, item, layout) {
                                            // alert("addClick");
                                            // model.add("specificReinstatementData",
                                            //   {reinstatementNum:"1",rate:0.1,time:"N",amount:"Y"}
                                            // );
                                            // console.log(item);
                                            //  model.add("reverseTable", item.getCurrentModel());
                                            // console.log(model.get("specificReinstatementData"));
                                            // this.forceUpdate();
                                        }

                                    },
                                    css: {
                                        comp: "inline-editor",
                                        cell: 'title-align'
                                    },
                                    pos: {
                                        row: 1,
                                        width: 12
                                    }
                                },
                                //TotalIncurredLoss: {
                                //    label: "Total Incurred Loss",
                                //    comp: {
                                //        type: $pt.ComponentConstants.Text,
                                //        //data: $page.relatedClaim,
                                //        format :$helper.formatNumber(2)
                                //    },
                                //    pos: {
                                //        row: 2,
                                //        width: 6
                                //    }
                                //},
                                ClaimRecordsSummary: {
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
                                        columns: [
                                            {title: "", data: "Currency", width: "15%"},//, codes: $page.codes.CurrencySpecial
                                            {
                                                title: "Gross Incurred Loss", data: "GrossIncurredLoss",
                                                //render : function(row){return $helper.formatNumberForLabel(row.GrossIncurredLoss,2);},
                                                width: "30%",
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("GrossIncurredLoss"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Gross Retrocession Recovery", data: "GrossRetrocessionRecovery",
                                                //render : function(row){return $helper.formatNumberForLabel(row.GrossRetrocessionRecovery,2);},
                                                width: "30%",
                                                inline: {
                                                    label: {
                                                        comp: {
                                                            type: {
                                                                label: false,
                                                                popover: false,
                                                                render: function (model) {
                                                                    return $helper.formatNumberForLabel(model.get("GrossRetrocessionRecovery"), 2);
                                                                }
                                                            }
                                                        },
                                                        pos: {width: 12},
                                                        css: {cell: 'currency-align-right'}
                                                    }
                                                }
                                            },
                                            {
                                                title: "Net", data: "NetTotal",
                                                //render : function(row){return $helper.formatNumberForLabel(row.NetTotal,2);},
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
                                        comp: "inline-editor",
                                        cell: 'title-align'
                                    },

                                    pos: {
                                        row: 2,
                                        width: 6
                                    }
                                },
                                //buttonLayout: {
                                //    comp: {
                                //        type: $pt.ComponentConstants.ButtonFooter,
                                //        buttonLayout: {
                                //            left: [{
                                //                text: "Detail",
                                //                style: "primary",
                                //                click: function (model) {
                                //                    $page.controller.exportExcel();
                                //                }
                                //            }]
                                //        }
                                //    },
                                //    pos: {
                                //        row: 2,
                                //        width: 6
                                //    }
                                //},
                                RelatedClaimList: {
                                    label: "Related Claim",
                                    comp: {
                                      type: $pt.ComponentConstants.SelectTree,
                                      data: $page.model.relatedClaims,
                                      hideChildWhenParentChecked: true,
                                      treeLayout: {
                                          comp: {
                                              hierarchyCheck: true,
                                              expandLevel: "all",
                                              inactiveSlibing: false,
                                              valueAsArray: true
                                          }
                                      },
                                        placeholder: "please select..."
                                    },
                                    pos: {
                                        row: 3,
                                        width: 6
                                    }
                                },
                                removeButton: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            left: [{
                                                text: "Remove Related Claims",
                                                style: "primary",
                                                click: function () {
                                                    $page.controller.removeClaims();
                                                }
                                            }]
                                        }
                                    },
                                    pos: {
                                        row: 3,
                                        width: 6
                                    }
                                }
                            }
                        },
                        pos: {
                            row: 3,
                            width: 12
                        }
                    }
                }
            }
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
                                                    console.log(model.get("EventId"));
                                                    var EventId = model.get("EventId");
                                                    var url = $pt.getURL('ui.claim.reserveHistory');
                                                    window.open(url + "?refId=" + EventId + "&businessDirection=2");
                                                    // window.location.href = url + "?refId=" +EventId+"&businessDirection=1";

                                                    // window.location.href = "reserveHistory.html";
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
                                        rowListener: [{
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
                                                    } else {
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
                                                    //console.log(parseFloat(amountOc) == amountOc);
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
                                                title: "Retro Section",
                                                data: "SectionId",
                                                codes: $page.model.rcontractSelection,
                                                inline: registerInlineSelect.call(window, "RetroSection", $page.model.rcontractSelection, $pt.ComponentConstants.Select),
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
                                                //render : function(row){return $helper.formatNumberForLabel(row.OrgAmountOc,2);},
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
                                                    return $helper.formatNumberForLabel(row.OrgAmountOc, 2);
                                                },
                                                width: 180
                                            },
                                            {
                                                title: "Submitted Amount(USD)",
                                                data: "OrgAmountUsd",
                                                //render : function(row){return $helper.formatNumberForLabel(row.OrgAmountUsd,2);},
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
                                                //render : function(row){return $helper.formatNumberForLabel(row.AmountUsd,2);},
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
                                                title: "Cedent Refer",
                                                data: "CedentRefer",
                                                inline: registerInlineSelect.call(window, "CedentRefer", null, $pt.ComponentConstants.Text),
                                                width: 100
                                            },
                                            {
                                                title: "Broker Refer",
                                                data: "BrokerRefer",
                                                inline: registerInlineSelect.call(window, "BrokerRefer", null, $pt.ComponentConstants.Text),
                                                width: 100
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
                                                //render : function(row){return $helper.formatNumberForLabel(row.LossTotal,2);},
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
                                                //render : function(row){return $helper.formatNumberForLabel(row.ACRTotal,2);},
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
                                                //render : function(row){return $helper.formatNumberForLabel(row.RIPTotal,2);},
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
                                                //render : function(row){return $helper.formatNumberForLabel(row.TaxTotal,2);},
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
                                                //render : function(row){return $helper.formatNumberForLabel(row.OthersTotal,2);},
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
                                                    var EventId = model.get("EventId");
                                                    var url = $pt.getURL('ui.claim.settlementHistory');
                                                    window.open(url + "?refId=" + EventId + "&businessDirection=2");
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
                                                    var EventId = model.get("EventId");
                                                    var url = $pt.getURL('ui.claim.changePosting');
                                                    window.open(url + "?refId=" + EventId + "&businessDirection=2");
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
                                              $page.controller.onRemoveSettlementClicked(item);
                                          }
                                        },
                                        editLayout: {
                                            DateOfReceipt: {
                                                label: "Date of Receipt",
                                                comp: {
                                                    type: $pt.ComponentConstants.Date,
                                                    format: "DD/MM/YYYY",
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
                                                                $page.controller.getPosting(SectionId);
                                                                if ($page.postingFlag == false) {
                                                                    evt.model.set("PostingFlag", '0');
                                                                } else {
                                                                    evt.model.set("PostingFlag", '1');
                                                                }
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
                                                                        messages: [' Amount(OC) only allows numbers to be filled in.']
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
                                                    //},{id:'PostingFlag',listener:function(evt){
                                                    //    var SettlementType = evt.model.get("SettlementType");
                                                    //    if(undefined != SettlementType && null != SettlementType){
                                                    //        if(SettlementType === "4" ){
                                                    //            evt.model.set("PostingFlag", '1');
                                                    //        }
                                                    //    }
                                                    //}}

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
                                                            width: "30%"
                                                        },
                                                        {
                                                            title: "Settlement Type",
                                                            data: "SettlementType",
                                                            inline: "select",
                                                            codes: $page.codes.SettlementType,
                                                            width: "15%"
                                                        },
                                                        {
                                                            title: "Original Currency",
                                                            data: "OriginalCurrency",
                                                            inline: "select",
                                                            codes: $page.codes.Currency,
                                                            width: "15%"
                                                        },
                                                        {
                                                            title: "Amount(OC)",
                                                            data: "AmountOc",
                                                            inline: "number",
                                                            render: function (row) {
                                                                return $helper.formatNumberForLabel(row.AmountUsd, 2);
                                                            },
                                                            width: "10%"
                                                        },
                                                        {
                                                            title: "Amount(USD)",
                                                            data: "AmountUsd",
                                                            //render : function(row){return $helper.formatNumberForLabel(row.AmountUsd,2);},
                                                            width: "10%",
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
                                                            title: "Cedent Refer",
                                                            data: "CedentRefer",
                                                            inline: "text",
                                                            width: "10%"
                                                        },
                                                        {
                                                            title: "Broker Refer",
                                                            data: "BrokerRefer",
                                                            inline: "text",
                                                            width: "10%"
                                                        },
                                                        {
                                                            title: "Transfer to Accounts",
                                                            data: "PostingFlag",
                                                            inline: "radio",
                                                            codes: $page.codes.Boolean,
                                                            width: "10%"
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
                                                                    console.log(rowModel.parent().getCurrentModel().Status);
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
                                                    width: 5,
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
                                                            //render : function(row){return $helper.formatNumberForLabel(row.GrossTotal,2);},
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
                                                            //render : function(row){return $helper.formatNumberForLabel(row.RIPTotal,2);},
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
                                                            //render : function(row){return $helper.formatNumberForLabel(row.TaxTotal,2);},
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
                                                            //render : function(row){return $helper.formatNumberForLabel(row.OthersTotal,2);},
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
                                                            //render : function(row){return $helper.formatNumberForLabel(row.NetTotal,2);},
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
                                                pos: {width: 7, row: 2}
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
                                                    // var EventId = model.get("EventId");
                                                    // var uploadUrl = "../Document/documentUpload.html?businessId=" + EventId + "&businessType=3";
                                                    // window.open(uploadUrl);
                                                    var readOnly = 0 ;
                                                    if($page.pageType==2){
                                                      readOnly=1;
                                                    }
                                                    $pt.viewAttachment(3, model.get("EventId"), readOnly);
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
            return {
                comp: {
                    type: $pt.ComponentConstants.Panel,
                    editLayout: {
                        formTab: {
                            comp: {
                                type: $pt.ComponentConstants.Tab,
                                justified: false,
                                //canRemove:true,
                                tabs: [this.createClaimDetailTab(), this.createDetailRetrocessionTab()]
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
                                            $page.controller.exit()
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
                                            controller.submitEventInfo(submitModel)
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
                                            controller.saveEventInfo(saveModel);
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
                    eventSec: this.createEventInfo()
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
                        width: 4
                    }
                },
                UnderWritingYear: {
                    label: "Underwriting Year",
                    comp: {
                        type: $pt.ComponentConstants.Text,
                        enabled: false

                    },
                    pos: {
                        row: 1,
                        width: 5
                    }
                },
                SectionTree: {
                    dataId: "SectionTree",
                    comp: {
                        type: $pt.ComponentConstants.Tree,
                        root: false,
                        check: function (node) {
                            if (node.level == 1) {
                                return false;
                            } else {
                                return true;
                            }

                        },
                        valueAsArray: true,
                        data: treeCodes,

                        valueCanCheck: function (node) {
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
