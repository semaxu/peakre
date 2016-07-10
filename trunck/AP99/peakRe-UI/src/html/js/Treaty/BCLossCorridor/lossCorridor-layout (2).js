/*
 * Create By elvira.du 10/20/2015
 */
(function (context) {
    var $page = $pt.getService(context, "$page");
    var codes = $pt.getService(context, "codes");
    var baseType = $pt.createCodeTable([{id: '1', text: "Loss Ratio"}]);
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
                    participationBase: {
                        label: "Participation Base",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: baseType
                        }
                    },
                    minimumRatio: {
                        label: "Minimum Ratio",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            convertor: NText.PERCENTAGE,
                            rightAddon : {
                                text : "%"
                            }
                        }
                    },
                    maxinumRatio: {
                        label: "Maximum Ratio",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            convertor: NText.PERCENTAGE,
                            rightAddon : {
                                text : "%"
                            }
                        }
                    },
                    cedentParticipation: {
                        label: "Cedent's Participation",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            convertor: NText.PERCENTAGE,
                            rightAddon : {
                                text : "%"
                            }
                        }
                    },
                    firstCalc: {
                        label: "First Calc. After",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            rightAddon : {
                                text : "Years"
                            }
                        }
                    },
                    subseqCalc: {
                        label: "Subseq. Calc. Every",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            rightAddon : {
                                text : "Years"
                            }
                        }
                    },
                    ssTable: {
                        label : "Upload Loss Participation SS Table",
                        comp: {
                            type: $pt.ComponentConstants.File,
                            showPreview: false
                        },
                        pos: {
                            width: 9
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