/*
 * Create By elvira.du 10/20/2015
 */
(function (context) {
    var $page = $pt.getService(context, "$page");
    var codes = $pt.getService(context, "codes");
    var limitType = $pt.createCodeTable([{id: '1', text: "QS"}, {id: '2', text: "Surplus"}]);
    var baseType = $pt.createCodeTable([{id: '1', text: "PML"}, {id: '2', text: "Sum Insured"}]);
    var eventType = $pt.createCodeTable([{id: '1', text: "Winter Storm"}]);
    $page.layout = {
        _sections: {
            limit: {
                label: "Limit",
                style: 'primary-darken',
                pos: {
                    width: 12
                },
                collapsible: true,
                expanded: true,
                layout: {
                    limitType: {
                        label: "Limit Type",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: limitType
                        }
                    },
                    limitBaseOn: {
                        label: "Limit Base On",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: baseType,
                            visible: {
                                when: function (model) {
                                    return model.get('limitType') == '1';
                                }, depends: 'limitType'
                            }
                        }
                    },
                    qsPanel: {
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            visible: {
                                when: function (model) {
                                    return model.get('limitType') == '1';
                                }, depends: 'limitType'
                            },
                            editLayout: {
                                qs1: {
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        addable: true,
                                        removable: true,
                                        searchable: false,
                                        addClick: function (model, item, layout) {
                                            model.add("qs1", item.getCurrentModel());
                                        },
                                        columns: [
                                            {
                                                title: "Currency",
                                                data: "currency",
                                                inline: "select",
                                                codes: codes.Currency,
                                                width : "19%"
                                            },
                                            {
                                                title: "Retention",
                                                data: "retention",
                                                inline: "number",
                                                width : "19%"
                                            },
                                            {
                                                title: "Liability 100%",
                                                data: "liability",
                                                inline: "number",
                                                width : "19%"
                                            },
                                            {
                                                title: "Liability Our Share",
                                                data: "liabilityOurShare",
                                                inline: "number",
                                                width : "19%"
                                            },
                                            {
                                                title: "Remark",
                                                data: "remark",
                                                inline: "text",
                                                width : "19%"
                                            }
                                        ]
                                    },
                                    pos: {
                                        width: 12
                                    }
                                },
                                qs2: {
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        addable: true,
                                        removable: true,
                                        searchable: false,
                                        addClick: function (model, item, layout) {
                                            model.add("qs2", item.getCurrentModel());
                                        },
                                        columns: [
                                            {
                                                title: "Currency",
                                                data: "currency",
                                                inline: "select",
                                                codes: codes.Currency,
                                                width : "19%"
                                            },
                                            {
                                                title: "Event",
                                                data: "event",
                                                inline: "select",
                                                codes: eventType,
                                                width : "19%"
                                            },
                                            {
                                                title: "Liability 100%",
                                                data: "liability",
                                                inline: "number",
                                                width : "19%"
                                            },
                                            {
                                                title: "Liability Our Share",
                                                data: "liabilityOurShare",
                                                inline: "number",
                                                width : "19%"
                                            },
                                            {
                                                title: "Remark",
                                                data: "remark",
                                                inline: "text",
                                                width : "19%"
                                            }
                                        ]
                                    },
                                    pos: {
                                        width: 12
                                    }
                                },
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    spPanel: {
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            visible: {
                                when: function (model) {
                                    return model.get('limitType') == '2';
                                }, depends: 'limitType'
                            },
                            editLayout: {
                                retention: {
                                    label: "Retention",
                                    comp: {
                                        type: $pt.ComponentConstants.Text,
                                        format: $helper.formatNumber(2)
                                    }
                                },
                                lineNumber: {
                                    label: "No. of Lines",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    }
                                },
                                capacity: {
                                    label: "Capacity",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    }
                                },
                                eventLimit: {
                                    label: "Event Limit",
                                    comp: {
                                        type: $pt.ComponentConstants.Panel,
                                        editLayout: {
                                            eq: {
                                                label: "EQ",
                                                comp: {
                                                    type: $pt.ComponentConstants.Text,
                                                    format: $helper.formatNumber(2)
                                                }
                                            },
                                            ws: {
                                                label: "WS",
                                                comp: {
                                                    type: $pt.ComponentConstants.Text,
                                                    format: $helper.formatNumber(2)
                                                }
                                            },
                                            fl: {
                                                label: "FL",
                                                comp: {
                                                    type: $pt.ComponentConstants.Text,
                                                    format: $helper.formatNumber(2)
                                                }
                                            },
                                            ot: {
                                                label: "OT",
                                                comp: {
                                                    type: $pt.ComponentConstants.Text,
                                                    format: $helper.formatNumber(2)
                                                }
                                            }
                                        }
                                    },
                                    pos: {
                                        width: 12
                                    }
                                },
                                terrorism: {
                                    label: "Terrorism",
                                    comp: {
                                        type: $pt.ComponentConstants.Text,
                                        format: $helper.formatNumber(2)
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    rightButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Save",
                                    style: "primary",
                                    click: function () {

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
    };
}(typeof window !== "undefined" ? window : this));