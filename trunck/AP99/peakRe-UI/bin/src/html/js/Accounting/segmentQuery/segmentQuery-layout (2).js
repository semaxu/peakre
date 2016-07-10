(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.layout = $pt.getService($page, 'layout');
    $page.layout.client = $pt.getService($page.layout, 'client');
    $page.IBNRCodes = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810033});
    var layout = jsface.Class({
        createBaseLayout : function(){
            return{
                _sections: {
                    claimSec: {
                        label: "Segment Query",
                        style: 'primary-darken',
                        layout: {
                            condition_SegmentCode: {
                                label: "Segment Code",
                                comp: {
                                    type: {
                                        enabled: true
                                    }
                                },
                                pos: {
                                    row: 1
                                }
                            },
                            condition_Cob: {
                                label: "Main Sub CoB",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data:  $page.IBNRCodes
                                },
                                pos: {
                                    row: 1
                                }
                            },
                            condition_CedentCountry: {
                                label: "Cedent Country",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.CedentCountry
                                },
                                //comp: {
                                //    type: $pt.ComponentConstants.SelectTree,
                                //    data: $page.codes.Country,
                                //    // root: true,
                                //    // check: true,
                                //    hideChildWhenParentChecked: true,
                                //    labelWidth: 3,
                                //    treeLayout: {
                                //        comp: {
                                //            hierarchyCheck: true,
                                //            expandLevel: "all",
                                //            inactiveSlibing: false,
                                //            valueAsArray: true
                                //        }
                                //    }
                                //},
                                pos: {
                                    row: 1
                                }
                            },
                            condition_SegmentName: {
                                label: "Segment Name",
                                comp: {
                                    type: $pt.ComponentConstants.Text
                                },
                                pos: {
                                    row: 2
                                }
                            },
                            condition_ContractNature: {
                                label: "Contract Nature",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.ContractNature
                                },
                                pos: {
                                    row: 2
                                }
                            },
                            condition_Status: {
                                label: "Segment Status",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.AccountStatus
                                },
                                pos: {
                                    row: 2
                                }
                            },
                            middleButtons: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [
                                            {
                                                text: "Reset",
                                                style: "warning",
                                                click: function (model) {
                                                    $page.controller.reset();
                                                }
                                            }, {
                                                text: "Create",
                                                style: "primary",
                                                click: function () {
                                                    window.location.href = "segmentCreate.html";
                                                }
                                            }, {
                                                text: "Search",
                                                style: "primary",
                                                click: function (model) {
                                                    $page.controller.doSearch();
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 3,
                                    width: 12
                                }
                            },
                            segmentTable: {
                                //label: "Query Result",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    //indexable: true,
                                    searchable: false,
                                    sortable: false,
                                    pageable: true,
                                    header: false,
                                    criteria: 'cachedCriteria',
                                    columns: [
                                        {
                                            title: "Segment Code",
                                            data: "SegmentCode",
                                            width: "15%"
                                        }, {
                                            title: "Segment Name",
                                            data: "SegmentName",
                                            width: "15%"
                                        }, {
                                            title: "Main Sub CoB",
                                            data: "Cob",
                                            codes: $page.codes.SubClass,
                                            width: "15%"
                                        }, {
                                            title: "Cedent Country",
                                            data: "CedentCountry",
                                            codes: $page.codes.CedentCountry,
                                            width: "15%"
                                        }, {
                                            title: "Contract Nature",
                                            data: "ContractNature",
                                            codes: $page.codes.ContractNature,
                                            width: "15%"
                                        }, {
                                            title: "Segment Status",
                                            data: "Status",
                                            codes: $page.codes.AccountStatus,
                                            width: "15%"
                                        }, {
                                            data: "SegmentId",
                                            visible: false
                                        }
                                    ],
                                    rowOperations: [
                                        {
                                            tooltip: "view",
                                            click: function (rowModel) {
                                                var segmentId = rowModel.SegmentId;
                                                var url = $pt.getURL('ui.accounting.segmentDetail');
                                                window.open(url + "?SegmentId=" + segmentId);
                                            }
                                        }
                                    ]
                                },
                                //evt: {
                                //    pageChange: function (evt) {
                                //        $page.controller.doPageJump(evt.criteria, evt.target);
                                //    }
                                //},
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
                                                    window.location.href = "../index.html";
                                                }
                                            }, {
                                                text: "IBNR Upload",
                                                style: "primary",
                                                click: function () {
                                                 //   url: "ui.accounting.ibnrUpload"
                                                    //window.open("../Document/documentUpload.html?businessType=6&businessId=0");
                                                    $pt.dataImport(6, 0);
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
                }
            }
        },
        createLayout : function(){
            return $.extend(true,{},this.createBaseLayout());
        }
    });

    $page.layout = new layout();

}(typeof window !== "undefined" ? window : this));
