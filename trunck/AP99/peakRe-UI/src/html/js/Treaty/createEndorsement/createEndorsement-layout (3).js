(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            defaultSection: {
                label: "Creating New Endorsement",
                style: "primary-darken",
                layout: {
                    placeHolder1: {
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        pos: {
                            row: 1,
                            width: 3
                        }
                    },
                    ClientEndoNo: {
                        label: "Client Endorsement Number",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            enabled: true
                        },
                        pos: {
                            row: 1,
                            width: 6
                        }
                    },
                    placeHolder2: {
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        pos: {
                            row: 2,
                            width: 3
                        }
                    },
                    EndoType: {
                        label: "Type of Endorsement",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.EndorsementType
                        },
                        pos: {
                            row: 2,
                            width: 6
                        }
                    },
                    placeHolder3: {
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        pos: {
                            row: 3,
                            width: 3
                        }
                    },
                    ChangeCondition: {
                        label: "Conditions to Change",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            enabled: true
                        },
                        pos: {
                            row: 3,
                            width: 6
                        }
                    },
                    placeHolder4: {
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        pos: {
                            row: 4,
                            width: 3
                        }
                    },
                    ApplicableTo: {
                        label: "Applicable to",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            enabled: true
                        },
                        pos: {
                            row: 4,
                            width: 6
                        }
                    },
                    placeHolder5: {
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        pos: {
                            row: 5,
                            width: 3
                        }
                    },
                    EffDate: {
                        label: "Effective Date",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY"
                        },
                        pos: {
                            row: 5,
                            width: 6
                        }
                    },
                    placeHolder6: {
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        pos: {
                            row: 6,
                            width: 3
                        }
                    },
                    AgreedDate: {
                        label: "Agreed Date",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY"
                        },
                        pos: {
                            row: 6,
                            width: 6
                        }
                    },
                    placeHolder7: {
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        pos: {
                            row: 7,
                            width: 3
                        }
                    },
                    descriptionPanel: {
                        label: "Description",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                Description: {
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
                            row: 7,
                            width: 6
                        }
                    },
                    placeHolder8: {
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel: false
                        },
                        pos: {
                            row: 8,
                            width: 3
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
                                            var url = "endorsementLog.html?ContCompId=" + this.getModel().get("ContCompId")
                                                + "&status=" + $page.status;
                                            window.location.href = url;
                                        }
                                    }, {
                                        text: "Save",
                                        style: "primary",
                                        click: function () {
                                            if ($page.controller.isEffective()) {
                                                $page.controller.save(true);
                                            }
                                        }
                                    }, {
                                        text: "Save & Continue",
                                        style: "primary",
                                        click: function () {
                                            if($page.controller.isEffective()){
                                                if ($page.controller.save()) {
                                                    var url = "contractHome.html?OperateType=3&ContCompId=" + this.getModel().get("ContCompId");
                                                    window.location.href = url;
                                                }
                                            }
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 8,
                            width: 6
                        }

                    }
                }
            }
        }
    };

}(typeof window !== "undefined" ? window : this));