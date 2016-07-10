(function (context) {
    var $page = $pt.getService(context, '$page');
    var ClosingStatus = $pt.createCodeTable([
        {
            id: "1",
            text: "Open"
        }, {
            id: "2",
            text: "Closing"
        }, {
            id: "3",
            text: "Closed"
        }
    ]);
    // var startClosingFlag = false;
    var layout = jsface.Class({
        createBaseLayout: function () {
            return {
                _sections: {
                    defaultSection: {
                        label: "Closing Setting",
                        style: "primary-darken",
                        layout: {
                            CurrentQuarter: {
                                label: "Current Quarter",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {
                                    row: 1
                                }
                            },
                            ClosingDate: {
                                label: "Cut-off Date",
                                comp: {
                                    type: $pt.ComponentConstants.Date,
                                    format: "DD/MM/YYYY",
                                    valueFormat: "DD/MM/YYYY"
                                },
                                pos: {
                                    row: 2
                                }
                            },
                            setButton: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        left: [
                                            {
                                                text: "Set",
                                                style: "primary",
                                                click: function () {
                                                    if($page.controller.checkDate()){
                                                        $page.controller.setCutOffDate();
                                                    }
                                                },
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('ClosingStatus') == '1';
                                                    },
                                                    depends: 'ClosingStatus'
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 2,
                                    width: 8
                                }
                            },
                            ClosingStatus: {
                                label: "Accounting Status",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: ClosingStatus,
                                    enabled: false
                                },
                                pos: {
                                    row: 3
                                }
                            },
                            actionButton: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        left: [
                                            {
                                                text: "Start Closing",
                                                style: "primary",
                                                enabled: {
                                                    depends: {on: 'inner', id: 'ClosingDate'},
                                                    when: function () {
                                                        var closingDate = this.getInnerModel().get("ClosingDate");
                                                        var array1 = closingDate.split("/");
                                                        closingDate = new Date(array1[2] + "/" + array1[1] + "/" + array1[0]);
                                                        closingDate = moment(closingDate).format("YYYY/MM/DD");
                                                        var nowDate = moment(new Date()).format("YYYY/MM/DD");
                                                        if(closingDate < nowDate &&  $page.controller.model.get('ClosingStatus') == '1'){
                                                          // startClosingFlag = true;
                                                            return true;
                                                        }
                                                    }
                                                },
                                                model: $page.controller.model,
                                                click: function () {
                                                    //console.log("Click the StartingButton");
                                                    $page.controller.setStartDate();
                                                }

                                            }, {
                                                text: "End Closing",
                                                style: "primary",
                                                click: function () {
                                                    $page.controller.setClosedDate();
                                                },
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('ClosingStatus') == '2';
                                                    },
                                                    depends: 'ClosingStatus'
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 3,
                                    width: 8
                                }
                            },
                            closingHistory: {
                                label: "Closing History",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    pageable: true,
                                    criteria: 'cachedCriteria',
                                    columns: [
                                        {
                                            title: "Quarter",
                                            data: "Quarter",
                                            width: "25%"
                                        }, {
                                            title: "Cut-off Date",
                                            data: "ClosingDate",
                                            //inline: "date",
                                            width: "25%"
                                        }, {
                                            title: "Actual Closing Start Date",
                                            data: "StartDate",
                                            width: "25%"
                                        }, {
                                            title: "Closed Date",
                                            data: "ClosedDate",
                                            width: "25%"
                                        }
                                    ]

                                },

                                evt: {
                                    pageChange: function (evt) {
                                        $page.controller.doPageJump(evt.criteria, evt.target);
                                    }
                                },
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
                                                text: "Cut Off Batch",
                                                style: "primary",
                                                visible: false,
                                                click: function () {
                                                    $page.controller.runBatch();
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 5,
                                    width: 12
                                }
                            }
                        }
                    }
                }
            }
        },
        createLayout: function () {
            return $.extend(true, {}, this.createBaseLayout());
        }
    });

    $page.layout = new layout();

}(window));
