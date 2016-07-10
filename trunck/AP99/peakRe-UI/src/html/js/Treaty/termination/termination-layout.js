(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');

    var layout = jsface.Class({
        createLayout: function () {
            return {
                _sections: {
                    defaultSection: {
                        label: "Termination",
                        style: 'primary-darken',
                        layout: {

                            viewContractButton: {
                                label: "Contract Detail",
                                comp: {
                                    type: $pt.ComponentConstants.Button,
                                    style: "link",
                                    click: function () {
                                        var url = "contractHome.html?ContCompId=" + this.getModel().get("ContCompId") + "&OperateType=0";
                                        window.open(url);
                                    }
                                },
                                pos: {
                                    row: 1,
                                    width: 12
                                },
                                css: {
                                    comp: 'pull-right'
                                }
                            },
                            TerminatedDate: {
                                label: "Terminated as of",
                                comp: {
                                    type: $pt.ComponentConstants.Date,
                                    format: "DD/MM/YYYY"
                                },
                                pos: {
                                    row: 2
                                }
                            },
                            AgreedDate: {
                                label: "Agreed On",
                                comp: {
                                    type: $pt.ComponentConstants.Date,
                                    format: "DD/MM/YYYY"
                                },
                                pos: {
                                    row: 2
                                }
                            },
                            Condition: {
                                label: "Condition",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.TerminationCondition
                                },
                                pos: {
                                    row: 2
                                }
                            },
                            CommuttedDate: {
                                label: "Commuted as of",
                                comp: {
                                    type: $pt.ComponentConstants.Date,
                                    format: "DD/MM/YYYY"
                                },
                                pos: {
                                    row: 3,
                                    width: 4
                                }
                            },
                            Reason: {
                                label: "Reason",
                                comp: {
                                    type: $pt.ComponentConstants.Text
                                },
                                pos: {
                                    row: 3,
                                    width: 8
                                }
                            },
                            remarkPanel: {
                                label: "Remarks",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        Remarks: {
                                            comp: {
                                                type: {type: $pt.ComponentConstants.TextArea, label: false},
                                                lines: 3,
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
                            rightButtons: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [{
                                            text: "Exit",
                                            style: "warning",
                                            click: function () {
                                                window.location.href = "../Treaty/contractQuery.html";
                                            }
                                        }, {
                                            text: "Submit",
                                            style: "primary",
                                            click: function () {
                                                NConfirm.getConfirmModal().show({
                                                    title: 'Confirm',
                                                    messages: ['You are about to terminate the contract.'],
                                                    onConfirm: function () {
                                                        if ($page.controller.save()) {
                                                            window.location.href = "../Treaty/contractQuery.html";
                                                        }
                                                    }
                                                });
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
                }
            }
        }
    });

    $page.layout = new layout();
}(typeof window !== "undefined" ? window : this));
