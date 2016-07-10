(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            defaultSection: {
                label: "SUPI Revision",
                style: "primary-darken",
                layout: {
                    contractDetail: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Contract Detail",
                                        style: "link",
                                        click: function () {
                                            window.open("/peakRe-UI/src/html/Treaty/contractHome.html");
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 1,
                            width: 12
                        }
                    },
                    contractInfo:{
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                                contractId:{
                                    label:"Contract ID",
                                    comp:{
                                        type:$pt.ComponentConstants.Text
                                    },
                                    pos:{
                                        width:6
                                    }
                                },
                                underWritingYear:{
                                    label:"Underwrting Year",
                                    comp:{
                                        type:$pt.ComponentConstants.Text
                                    },
                                    pos:{
                                        width:6
                                    }
                                }
                            }
                        },
                        pos:{
                            row:2,
                            width:12
                        }
                    },
                    sectionsPanel:{
                        comp:{
                            type:$pt.ComponentConstants.SupiRevisionSection
                        },
                        pos:{
                            row:3,
                            width:12
                        }
                    }
                }
            }
        }
    }
}(typeof window !== "undefined" ? window : this));