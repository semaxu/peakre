(function (context) {
    var $page = $pt.getService(context, '$page');

    var layout = jsface.Class({
        createBaseLayout: function () {
            return {
                _sections: {
                    defaultSection: {
                        label: "Account Summary",
                        style: 'primary-darken',
                        collapsible: false,
                        layout: {
                            contractLevel: {
                                label: "Contract Level",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        programLevel: {
                                            comp: {
                                                type: $pt.ComponentConstants.Tree,
                                                root: false,
                                                data: $pt.createCodeTable($page.controller.model.get("contractCodes")),
                                                nodeClick: function (node) {
                                                    $page.controller.toForecastAndEstimation(1, node.id);
                                                }
                                            },
                                            pos: {
                                                width: 12
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    width: 12
                                }
                            },
                            sectionLevel: {
                                label: "Contract Section Level",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        sectionLevel: {
                                            comp: {
                                                type: $pt.ComponentConstants.Tree,
                                                root: false,
                                                data: $pt.createCodeTable($page.controller.model.get("sectionCodes")),
                                                nodeClick: function (node) {
                                                    console.log(node.id);
                                                    $page.controller.toForecastAndEstimation(2, node.id);
                                                }
                                            },
                                            pos: {
                                                width: 12
                                            }
                                        }
                                    }
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
                                                click: function () {
                                                    window.location.href = $pt.getURL('ui.accounting.accountSummaryQuery');
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
        createLayout: function () {
            return $.extend(true, {}, this.createBaseLayout());
        }
    });

    $page.layout = new layout();

}(typeof window !== "undefined" ? window : this));