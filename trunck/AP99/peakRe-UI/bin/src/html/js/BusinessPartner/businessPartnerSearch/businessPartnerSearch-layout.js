(function (context) {
    var $page = $pt.getService(context, '$page');

    var Layout = jsface.Class({
        createClaimInfo: function () {
            return {
                label: "Document Search",
                style: 'primary-darken',
                collapsible: false,
                layout: {
                    searchCondition: {
                        label: "Search Criteria",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                condition_BusinessPartnerCategory: {
                                    label: "BP Category",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.BusinessPartnerCategory
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                condition_RoleSelect: {
                                    label: "BP Role",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.partnerRole
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                condition_BusinessPartnerId: {
                                    label: "BP ID",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                condition_TradingName: {
                                    label: "Name",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                condition_Status: {
                                    label: "Status",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.Status
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                condition_Pending: {
                                    label: "Pending AML Check",
                                    comp: {
                                        type: $pt.ComponentConstants.Check
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                searchPanel: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [
                                                {
                                                    text: "Reset",
                                                    style: "warning",
                                                    click:function(model){
                                                        $page.controller.resetQuery();
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
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    partnerTablePanel: {
                        label: "Search Result",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                partnerTable: {
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        searchable: false,
                                        sortable: false,
                                        header: false,
                                        pageable: true,
                                        criteria: 'cachedCriteria',
                                        columns: [
                                            {
                                                title: "Business Partner ID",
                                                data: "BusinessPartnerId",
                                                width: "12%"
                                            }, {
                                                title: "Name",
                                                data: "Name",
                                                width: "15%"
                                            }, {
                                                title: "BP Category",
                                                data: "BusinessPartnerCategory",
                                                codes: $page.BusinessPartnerCategory,
                                                width: "15%"
                                            }, {
                                                title: "BP Role",
                                                data: "RoleSelectIds",
                                                render : function(row){
                                                    console.log($page.partnerRole);
                                                    console.log($page.partnerRole.getText("1"));
                                                    var value = row.RoleSelectIds;
                                                    if(value == null || value == ""){
                                                        return;
                                                    }
                                                    var arrValue = '';
                                                    for(var i =0; i<value.length;i++){
                                                        arrValue = arrValue +" "+ $page.partnerRole.getText(value[i]);
                                                    }
                                                    return arrValue;
                                                },
                                                width: "15%"
                                            }, {
                                                title: "Status",
                                                data: "Status",
                                                codes: $page.Status,
                                                width: "12%"
                                            }, {
                                                title: "Creator",
                                                data: "Creator",
                                                codes: $page.codes.SystemUser,
                                                width: "12%"
                                            }, {
                                                title: "Update Date",
                                                data: "UpdateTime",
                                                width: "12%",
                                                render: function (row) {
                                                    return $helper.formatDateForTableView(row.UpdateTime, "DD/MM/YYYY");
                                                }
                                            }
                                        ],
                                        rowOperations: [
                                            {
                                                tooltip: "Edit",
                                                click: function (rowModel) {
                                                    $page.controller.doPartnerEdit(rowModel,0);
                                                }
                                            }, {
                                                tooltip: "View",
                                                click: function (rowModel) {
                                                    $page.controller.doPartnerEdit(rowModel,1);
                                                }
                                            }
                                        ]
                                    },
                                    evt: {
                                        pageChange: function (evt) {
                                            $page.controller.doPageJump(evt.criteria, evt.target);
                                        }
                                    },
                                    pos: {
                                        row: 1,
                                        width: 12
                                    }
                                },
                                actionPanel: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [
                                                {
                                                    text: "Exit",
                                                    style: "warning",
                                                    click:function(){
                                                        var url = $pt.getURL('index');
                                                        console.log(url);
                                                        window.location.href = url;
                                                    }
                                                }, {
                                                    text: "Create Individual BP",
                                                    style: "primary",
                                                    click: function () {
                                                        $page.controller.doPartnerCreateIndividual();
                                                    }
                                                }, {
                                                    text: "Create Organization BP",
                                                    style: "primary",
                                                    click: function () {
                                                        $page.controller.doPartnerCreateCompany();
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
                    claimInfoSection: this.createClaimInfo()
                }
            }
        }
    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));