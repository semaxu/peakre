(function (context) {
    var $page = $pt.getService(context, '$page');


    var Layout = jsface.Class({
        claimQuery:function(){
            return{
                label: "Claim Query" ,
                style: 'primary-darken',
                collapsible: false,
                layout: {
                    searchCondition: {
                        label: "Search Criteria",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                condition_ClaimNo: {
                                    label: "Claim No.",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                condition_EventId: {
                                    label: "Event Code",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data:$page.model.eventCodes,
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                condition_ClaimBranch: {
                                    label: "Claim Branch",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.UwCompany

                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                condition_ContractID: {
                                    label: "Contract ID",
                                    comp: {
                                        type: $pt.ComponentConstants.NContractSearchText,
                                        searchTriggerDigits: 6,
                                        contractStatus: [4],
                                        //isSingle : true,
                                        url: $ri.getRestfulURL("action.contract.contractHome") + "/page"
                                    },
                                    pos: {
                                        row: 2,
                                        width: 4
                                    }
                                },
                                condition_UnderwritingYear: {
                                    label: "Underwriting Year",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.UWYear

                                    },
                                    pos: {
                                        row: 2,
                                        width: 4
                                    }
                                },
                                condition_CedantName: {
                                    label: "Cedent Name",
                                    base: $helper.basePartnerSearch(),
                                    pos: {
                                        row: 2,
                                        width: 4
                                    }
                                },
                                condition_CedantCountry: {
                                    label: "Cedent Country",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data:$page.codes.CedentCountry
                                    },
                                    pos: {
                                        row: 3,
                                        width: 4
                                    }
                                },
                                condition_CedantReference: {
                                    label: "Cedent Reference",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 3,
                                        width: 4
                                    }
                                },
                                condition_BrokerReference: {
                                    label: "Broker Reference",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 3,
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
                                        row: 4,
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
                                        row: 4,
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
                                        row: 4,
                                        width: 4
                                    }
                                },
                                condition_Status: {
                                    label: "Loss Status",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.ClaimStatus

                                    },
                                    pos: {
                                        row: 5,
                                        width: 4
                                    }
                                },
                                condition_TaskOwner: {
                                    label: "Task Owner",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                       // searchTriggerDigits: 6
                                        data: $page.model.userCodes
                                    },
                                    pos: {
                                        row: 5,
                                        width: 4
                                    }
                                },
                                condition_LossDescription: {
                                    label: "Loss Description",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 6,
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
                                                        $page.controller.resetClaimQueryPram();
                                                    }
                                                }, {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function () {
                                                        $page.controller.doSearchClaimQuery();
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
                            row: 1,
                            width: 12
                        }
                    },
                    searchResult: {
                        label: "Search Result",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            pageable: true,
                            criteria: 'cachedCriteria',
                            view:true,
                            columns: [
                                {
                                    title: "Claim No.",
                                    data: "ClaimNo",
                                    width: "10%"
                                }, {
                                    title: "Event Code",
                                    data: "EventId",
                                    codes:$page.model.eventCodes,
                                    width: "12%"
                                }, {
                                    title: "Loss Start Date",
                                    data: "DateOfLossFrom",
                                    width: "15%",
                                    render: function (row) {
                                        return $helper.formatDateForTableView(row.DateOfLossFrom, "DD/MM/YYYY HH:mm");
                                    }

                                }, {
                                    title: "Cause of Loss",
                                    data: "CauseOfLoss",
                                    codes: $page.codes.Cause,
                                    width: "15%"
                                }, {
                                    title: "Loss Status",
                                    data: "Status",
                                    codes: $page.codes.ClaimStatus,
                                    width: "10%"
                                }, {
                                    title: "Loss Description",
                                    data: "LossDescription",
                                    inline: "textarea",
                                    width: '25%'

                                },{
                                    data: "ClaimId",
                                    visible: false
                                }
                            ],
                            rowOperations:[
                                {
                                    tooltip: "edit",
                                    enabled:{
                                        depends:"status",
                                        when:function(rowModel){
                                            return rowModel.getCurrentModel().Status == '0';
                                        }
                                    },
                                    click: function (rowModel) {
                                        $page.controller.loadClaim(rowModel, 0);
                                    }

                                },
                                {
                                    tooltip: "Reopen",
                                    enabled: {
                                        depends: "status",
                                        when: function (rowModel) {
                                            return rowModel.getCurrentModel().Status == '1';
                                        }
                                    },
                                    click: function (rowModel) {
                                        $page.controller.loadClaim(rowModel, 1);
                                    }
                                },
                                {
                                    tooltip: "View",

                                    click: function (rowModel) {
                                        $page.controller.loadClaim(rowModel,2);
                                    }
                                },
                                //{
                                //    tooltip: "xml",
                                //
                                //    click: function (rowModel) {
                                //        $page.controller.loadClaim(rowModel,5);
                                //    }
                                //}
                            ]
                            //editClick: function (rowModel) {
                            //    console.log(rowModel);
                            //  //   var claimId = rowModel.get('ClaimId');
                            //  ////  var claimId = '1637018';
                            //  //  alert(claimId);
                            //  //  $page.controller.loadClaim(claimId);
                            //}
                        },
                        //evt: {
                        //    pageChange: function (evt) {
                        //        $page.controller.doPageJumpClaimQuery(evt.criteria, evt.target);
                        //    }
                        //},
                        pos: {
                            row: 2,
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
                                            console.log(url);

                                            window.location.href = url;
                                        }
                                    }, {
                                        text: "Create",
                                        style: "primary",
                                        click: function () {
                                            var url = $pt.getURL('ui.claim.createClaim');
                                            console.log(url);

                                            window.location.href = url;
                                        }
                                    },
                                    {
                                        text: "UploadClaim",
                                        style: "primary",
                                        click: function () {
                                          //  $pt.dataImport(22, 0);
                                            window.open("../Document/documentUpload.html?businessType=22&businessId=0");
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 3,
                            width: 12
                        }
                    }
                }
            };
        },
        createFormLayout: function () {
            return {
                _sections: {
                    claimQuerySection: this.claimQuery()
                }
            }
        }
    });
    $page.layout = new Layout();

    //$page.layout.client.search = $.extend(true, {}, criteria);

}(typeof window !== "undefined" ? window : this));
