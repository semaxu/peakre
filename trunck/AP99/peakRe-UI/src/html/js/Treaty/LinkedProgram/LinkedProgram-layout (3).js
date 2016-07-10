(function (context) {
    var $page = $pt.getService(context, '$page');
    var $code = $pt.getService($page, 'codes');

    $page.layout = {
        _sections : {
            defaultSection : {
                layout : {
                    formTab1 : {
                        comp : {
                            type : $pt.ComponentConstants.Tab,
                            justified: false,
                            tabs : [
                                {
                                    label : "UY 2015",
                                    editLayout: {
                                        subPanel01: {
                                            comp: {
                                                type: $pt.ComponentConstants.Panel,
                                                editLayout: {
                                                    buttonPanel : {
                                                        comp : {
                                                            type: $pt.ComponentConstants.ButtonFooter,
                                                            buttonLayout: {
                                                                right : [
                                                                    {
                                                                        text :"Expland All"
                                                                    }, {
                                                                        text : "Collapse All"
                                                                    }
                                                                ]
                                                            }
                                                        },
                                                        pos : {
                                                            width : 12
                                                        }
                                                    },
                                                    contractTree : {
                                                        comp : {
                                                            type : $pt.ComponentConstants.Tree
                                                        },
                                                        pos : {
                                                            width : 12
                                                        }
                                                    }
                                                }
                                            },
                                            pos : {
                                                width: 12,
                                                row: 1
                                            }
                                        }
                                    }
                                }, {
                                    label : "UY 2014",
                                    editLayout: {
                                        subPanel02: {
                                            comp: {
                                                type: $pt.ComponentConstants.Panel,
                                                editLayout: {
                                                    buttonPanel : {
                                                        comp : {
                                                            type: $pt.ComponentConstants.ButtonFooter,
                                                            buttonLayout: {
                                                                right : [
                                                                    {
                                                                        text :"Expland All"
                                                                    }, {
                                                                        text : "Collapse All"
                                                                    }
                                                                ]
                                                            }
                                                        },
                                                        pos : {
                                                            width : 12
                                                        }
                                                    },
                                                    contractTree : {
                                                        comp : {
                                                            type : $pt.ComponentConstants.Tree
                                                        },
                                                        pos : {
                                                            width : 12
                                                        }
                                                    }
                                                }
                                            },
                                            pos : {
                                                width: 12,
                                                row: 1
                                            }
                                        }
                                    }
                                }, {
                                    label : "UY 2013",
                                    editLayout: {
                                        subPanel03: {
                                            comp: {
                                                type: $pt.ComponentConstants.Panel,
                                                editLayout: {
                                                    buttonPanel : {
                                                        comp : {
                                                            type: $pt.ComponentConstants.ButtonFooter,
                                                            buttonLayout: {
                                                                right : [
                                                                    {
                                                                        text :"Expland All"
                                                                    }, {
                                                                        text : "Collapse All"
                                                                    }
                                                                ]
                                                            }
                                                        },
                                                        pos : {
                                                            width : 12
                                                        }
                                                    },
                                                    contractTree : {
                                                        comp : {
                                                            type : $pt.ComponentConstants.Tree
                                                        },
                                                        pos : {
                                                            width : 12
                                                        }
                                                    }
                                                }
                                            },
                                            pos : {
                                                width: 12,
                                                row: 1
                                            }
                                        }
                                    }
                                }
                            ]
                        },
                        pos : {
                            width : 6
                        }
                    }
                }
            }
        }
    };

}(typeof window !== "undefined" ? window : this));