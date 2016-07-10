(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.layout = {
        _sections: {
            defaultSection: {
                label: "Manually Batch Job Trigger",
                style: "primary-darken",
                layout: {
                    manuallyTriggerCutoff: {
                        label: "Manually Trigger Cut-off",
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel:false
                        },
                        pos: {
                            row: 1,
                            width: 2
                        }
                    },
                    executePanel1: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                left: [
                                    {
                                        text: "Execute",
                                        style: "primary"
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 1,
                            width: 6
                        }
                    },
                    revaluation: {
                        label: "Revaluation",
                        comp: {
                            type: $pt.ComponentConstants.Label,
                            textFromModel:false
                        },
                        pos: {
                            row: 2,
                            width: 2
                        }
                    },
                    executePanel2: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                left: [
                                    {
                                        text: "Execute",
                                        style: "primary",
                                        click: function (model) {
                                            $page.controller.doExecute();
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 2,
                            width: 4
                        }
                    },
                    view: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                left: [
                                    {
                                        text: "View",
                                        style: "primary",
                                        click: function (model) {
                                            $page.controller.doView();
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 2,
                            width: 4
                        }
                    },
                    buttonPanel1: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Exit",
                                        style: "warning",
                                        click:function(){
                                            window.location.href = "/peak-basic-ui/src/main/webapp/html/index.html";
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
            }
        }
    };
}(typeof window !== "undefined" ? window : this));