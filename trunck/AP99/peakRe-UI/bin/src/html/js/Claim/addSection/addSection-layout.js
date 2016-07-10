(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            addSection: {
                label: "Add Section",
                style: 'primary-darken',
                collapsible: false,
                layout: {
                    contractID: {
                        label: "Contract ID",
                        comp: {
                            type: $pt.ComponentConstants.Search,
                            searchTriggerDigits: 6
                        },
                        pos: {
                            row: 1,
                            width: 4
                        }
                    },
                    underWritingYear: {
                        label: "Underwriting Year",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.UWYear
                        },
                        pos: {
                            row: 1,
                            width: 4
                        }
                    },
                    searchBtn: {
                        label: "Search",
                        comp: {
                            type: $pt.ComponentConstants.Button,
                            click: function () {
                                alert("eeeeeeeeeee");

                            }
                        },
                        pos: {
                            row: 1,
                            width: 4
                        }
                    }

                }
            },
            contractSection: {
                label: "Contract Section",
                style: 'primary-darken',
                collapsible: false,
                layout: {
                    sectionTree: {
                        comp: {
                            type: $pt.ComponentConstants.Tree,
                            root: false,
                            check: "selected",
                            hierarchyCheck: true,
                            data: $page.TreeCodes
                        },
                        pos: {
                            row: 2,
                            width: 12
                        }
                    },
                    buttonPanel: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Submit",
                                        style: "primary",
                                        click: function () {
                                            window.location.href = "claimInformation.html";
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