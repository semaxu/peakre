(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');

    var layout = jsface.Class({
        createLayout: function () {
            return {
                _sections: {
                    defaultSection: {
                        label: "Endorsement Log",
                        style: "primary-darken",
                        layout: {
                            EndorsementList: {
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    scrollX: true,
                                    columns: [
                                        {
                                            title: "Number",
                                            data: "EndoId",
                                            width: "80"
                                        }
                                        , {
                                            title: "Client No.",
                                            data: "ClientEndoNo",
                                            width: "100"
                                        }, {
                                            title: "Type",
                                            data: "EndoType",
                                            codes:$page.codes.EndorsementType,
                                            width: "100"
                                        }, {
                                            title: "Condition",
                                            data: "ChangeCondition",
                                            width: "150"
                                        }, {
                                            title: "Description",
                                            data: "Description",
                                            width: "150"
                                        }, {
                                            title: "Applicable to",
                                            data: "ApplicableTo",
                                            width: "150"
                                        }, {
                                            title: "Effectvie Date",
                                            data: "EffDate",
                                            width: "100",
                                            render: function (row) {
                                                return $helper.formatDateForTableView(row.EffDate, "DD/MM/YYYY");
                                            }
                                        }, {
                                            title: "Agreed Date",
                                            data: "AgreedDate",
                                            width: "100",
                                            render: function (row) {
                                                return $helper.formatDateForTableView(row.AgreedDate, "DD/MM/YYYY");
                                            }
                                        }, {
                                            title: "User Name",
                                            data: "InsertBy",
                                            codes: codes.SystemUser,
                                            width: "120"
                                        }, {
                                            title: "Date",
                                            data: "InsertTime",
                                            width: "100",
                                            render: function (row) {
                                                return $helper.formatDateForTableView(row.InsertTime, "DD/MM/YYYY HH:mm:ss");
                                            }
                                        }, {
                                            title: "Approved By",
                                            data: "UpdateBy",
                                            codes: codes.SystemUser,
                                            width: "120"
                                        }, {
                                            title: "Approved Date",
                                            data: "UpdateTime",
                                            width: "120",
                                            render: function (row) {
                                                return $helper.formatDateForTableView(row.UpdateTime, "DD/MM/YYYY HH:mm:ss");
                                            }
                                        }
                                    ],
                                    rowOperations: [
                                        {
                                            tooltip: "edit",
                                            enabled: {
                                                when: function (model) {
                                                    return $page.controller.isEnabledForEditAndDel(model.get("EndoId"));
                                                }
                                            },
                                            click: function (row) {
                                                var url = "createEndorsement.html?ContCompId=" + this.getModel().get("ContCompId")
                                                    + "&EndoId=" + row.EndoId
                                                    + "&status=" + $page.status;
                                                window.location.href = url;
                                            }
                                        },
                                        {
                                            tooltip: "Delete & Revert",
                                            enabled: {
                                                when: function (model) {
                                                    return $page.controller.isEnabledForEditAndDel(model.get("EndoId"));
                                                }
                                            },
                                            click: function (row) {
                                                NConfirm.getConfirmModal().show({
                                                    title: 'Confirm',
                                                    messages: ['You are about to delete the selected Endorsement and revert the condition change.'],
                                                    onConfirm: function () {
                                                        if ($page.controller.delRevert(row)) {
                                                            var url = "endorsementLog.html?ContCompId=" + row.ContCompId + "&status=" + $page.status;
                                                            window.location.href = url;
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    ]
                                },
                                pos: {
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
                                                click: function (row) {
                                                    window.location.href = "../Treaty/contractQuery.html";
                                                }
                                            }, {
                                                text: "Add",
                                                style: "primary",
                                                enabled: {
                                                    when: function () {
                                                        return $page.status == '4';
                                                    },
                                                    depends: $page.status
                                                },
                                                click: function (row) {
                                                    var url = "createEndorsement.html?ContCompId=" + this.getModel().get("ContCompId")
                                                        + "&status=" + $page.status;
                                                    window.location.href = url;
                                                }
                                            }, {
                                                text: "Document",
                                                style: "primary",
                                                enabled: {
                                                    when: function () {
                                                        return $page.status == '4' || $page.status == '3';
                                                    },
                                                    depends: $page.status
                                                },
                                                click: function () {
                                                    $pt.viewAttachment(21, this.getModel().get("ContCompId"), 0);
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
        }
    });
    $page.layout = new layout();

}(typeof window !== "undefined" ? window : this));