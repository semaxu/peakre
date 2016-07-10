(function (context) {
    var $page = $pt.getService(context, '$page');

    var Layout = jsface.Class({
        createClaimInfo: function () {
            return {
                label: "Claim Creation",
                style: 'primary-darken',
                collapsible: false,
                layout: {
                    EventId: {
                        label: "Event Code",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.model.eventCodes

                        },
                        pos: {
                            row: 1,
                            width: 4
                        }
                    },
                    ClaimBranch:{
                        label:"Claim Branch",
                        comp:{
                            type:$pt.ComponentConstants.Select,
                            data: $page.codes.UwCompany
                        },
                        pos: {
                            row: 1,
                            width: 4
                        }
                    },
                    CauseOfLoss: {
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
                    DateOfReport: {
                        label: "Date of Report",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY HH:mm"
                        },
                        pos: {
                            row: 2,
                            width: 4
                        }
                    },
                    DateOfLossFrom: {
                        label: "Loss Start Date",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY HH:mm"

                        },
                        pos: {
                            row: 2,
                            width: 4
                        }
                    },
                    DateOfLossTo: {
                        label: "Loss End Date",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY HH:mm"
                        },
                        pos: {
                            row: 2,
                            width: 4
                        }
                    },
                    DateOfCreation: {
                        label: "Created on",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY HH:mm",
                            enabled: false
                        },
                        pos: {
                            row: 3,
                            width: 4
                        }
                    },
                    descriptionPanel: {
                        label: "Loss Description",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                LossDescription: {
                                    comp: {
                                        type: {type: $pt.ComponentConstants.TextArea, label: false},
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
                    remarkPanel: {
                        label: "Remarks",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                Remark: {
                                    comp: {
                                        type: {type: $pt.ComponentConstants.TextArea, label: false},
                                        lines: 3
                                    },
                                    pos: {
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {
                            row: 5,
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
                                            var url = $pt.getURL('ui.claim.claimQuery');
                                            window.location.href = url;
                                        }
                                    }, {
                                        text: "Submit",
                                        style: "primary",
                                        click: function () {
                                            $page.controller.submitData();
                                            // window.location.href = "claimInformation.html";
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 6,
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