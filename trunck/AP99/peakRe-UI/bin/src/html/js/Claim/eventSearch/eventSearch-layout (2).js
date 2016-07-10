(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');

    var Layout = jsface.Class({
        eventQuery: function () {
            return {
                label: "Event Query",
                style: "primary-darken",
                collapsible: false,
                layout: {
                    searchCondition: {
                        label: "Search Criteria",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                condition_EventCode: {
                                    label: "Event Code",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                condition_CauseOfLoss: {
                                    label: "Cause of Loss",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Cause
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                condition_TaskOwner: {
                                    label: "Task Owner",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: codes.SystemUser
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                condition_ClaimNo: {
                                    label: "Claim No.",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 2,
                                        width: 4
                                    }
                                },
                                condition_CedentName: {
                                    label: "Cedent Name",
                                    base: $helper.basePartnerSearch(),
                                    pos: {
                                        row: 2,
                                        width: 4
                                    }
                                },
                                condition_ContractCode: {
                                    label: "Contract ID",
                                    comp: {
                                        //type: $pt.ComponentConstants.Text
                                        type: $pt.ComponentConstants.NContractSearchText,
                                        searchTriggerDigits: 6,
                                        contractStatus: [4],
                                        isSingle : true,
                                        url: $ri.getRestfulURL("action.contract.contractHome") + "/page"
                                    },
                                    pos: {
                                        row: 2,
                                        width: 4
                                    }
                                },
                                condition_DateOfLossFrom: {
                                    label: "Loss Start Date",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY HH:mm"
                                    },
                                    pos: {
                                        row: 3,
                                        width: 4
                                    }
                                },
                                condition_DateOfLossTo: {
                                    label: "Loss End Date",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY HH:mm"
                                    },
                                    pos: {
                                        row: 3,
                                        width: 4
                                    }
                                },
                                condition_LossDesc: {
                                    label: "Loss Description",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 4,
                                        width: 8
                                    }
                                },
                                buttonPanel1: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [
                                                {
                                                    text: "Reset",
                                                    style: "warning",
                                                    click: function () {
                                                        $page.controller.resetEventQueryPram();

                                                    }
                                                }, {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function () {
                                                        $page.controller.doSearchEventQuery();
                                                    }
                                                }
                                            ]
                                        }
                                    },
                                    pos: {
                                        row: 7,
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    searchResult: {
                        label: "Search Result",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            view:true,
                            //editable: true,
                            pageable: true,
                            criteria: 'cachedCriteria',
                            columns: [
                                {
                                    title: "Event Code",
                                    data: "EventCode",
                                    width: "15%"
                                }, {
                                    title: "Loss Start Date",
                                    data: "DateOfLossFrom",
                                    width: "15%",
                                    render: function (row) {
                                        return $helper.formatDateForTableView(row.DateOfLossFrom, "DD/MM/YYYY HH:mm");
                                    }
                                }, {
                                    title: "Loss End Date",
                                    data: "DateOfLossTo",
                                    width: "15%",
                                    render: function (row) {
                                        return $helper.formatDateForTableView(row.DateOfLossTo, "DD/MM/YYYY HH:mm");
                                    }
                                }, {
                                    title: "Cause of Loss",
                                    data: "CauseOfLoss",
                                    codes: $page.codes.Cause,
                                    width: "15%"
                                }, {
                                    title: "Loss Description",
                                    data: "LossDesc",
                                    inline: "textarea",
                                    width: "25%"
                                }
                            ],
                            rowOperations:[
                                {
                                    tooltip: "edit",
                                    click: function (rowModel) {
                                        $page.controller.loadEvent(rowModel.EventId,0);
                                    }

                                },
                                {
                                    tooltip: "View",
                                    click: function (rowModel) {
                                        $page.controller.loadEvent(rowModel.EventId,2);
                                    }
                                },
                                //{
                                //    tooltip: "xml",
                                //
                                //    click: function (rowModel) {
                                //        $page.controller.loadEventXml(rowModel,5);
                                //    }
                                //}
                            ]
                        },
                        //evt: {
                        //    pageChange: function (evt) {
                        //        $page.controller.doPageJumpEventQuery(evt.criteria, evt.target);
                        //    }
                        //},
                        pos: {
                            width: 12
                        }
                    },
                    buttonPanel2: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Exit",
                                        style: "warning",
                                        click: function () {
                                            var url = $pt.getURL('index');
                                            window.location.href = url;
                                        }
                                    }, {
                                        text: "Create",
                                        style: "primary",
                                        click: function () {
                                            var url = $pt.getURL('ui.claim.createEvent');
                                            window.location.href = url;
                                        }
                                    },
                                    {
                                        text: "Upload Event",
                                        style: "primary",
                                        click: function () {
                                            //  $pt.dataImport(22, 0);
                                            window.open("../Document/documentUpload.html?businessType=23&businessId=0");
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
        createFormLayout: function () {
            return {
                _sections: {
                    eventQuerySection: this.eventQuery()
                }
            }
        }
    });
    $page.layout = new Layout();

}(typeof window !== "undefined" ? window : this));
