(function (context) {
    var $page = $pt.getService(context, "$page");
    var layout = jsface.Class({
        createNonDiffTemplate: function () {
            return {
                currency: {
                    comp: {
                        type: $pt.ComponentConstants.Currency
                    },
                    pos: {
                        row: 1,
                        width: 12
                    }
                },
                reserveAndRebate: {
                    comp: {
                        type: $pt.ComponentConstants.ReserveAndRebate
                    },
                    pos: {
                        row: 7,
                        width: 12
                    }
                },
                lossCorridorArea: {
                    comp: {
                        type: $pt.ComponentConstants.BCLossCorridor
                    },
                    pos: {
                        row: 8,
                        width: 12
                    }
                },
                clause: {
                    comp: {
                        type: $pt.ComponentConstants.Clause
                    },
                    pos: {
                        row: 9,
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
                                        $page.controller.exit();
                                    }
                                }, {
                                    text: "Submit",
                                    style: "primary",
                                    visible: $page.controller.isVisible(),
                                    click: function () {
                                        $page.controller.submit(false);
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
        },
        createProportionalTemplate: function () {
            return {
                proportionalShare: {
                    comp: {
                        type: $pt.ComponentConstants.ProportionalShare,
                        visible: {
                            when: function (model) {
                                return model.get('BusinessConditionVO_ContractNature') == '1';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
                        }
                    },
                    pos: {
                        row: 2,
                        width: 12
                    }
                },
                premiumProArea: {
                    comp: {
                        type: $pt.ComponentConstants.BCPremiumProportion,
                        visible: {
                            when: function (model) {
                                return model.get('BusinessConditionVO_ContractNature') == '1';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
                        }
                    },
                    pos: {
                        row: 3,
                        width: 12
                    }
                },
                limitProArea: {
                    comp: {
                        type: $pt.ComponentConstants.BCLimitProportion,
                        visible: {
                            when: function (model) {
                                return model.get('BusinessConditionVO_ContractNature') == '1';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
                        }
                    },
                    pos: {
                        row: 4,
                        width: 12
                    }
                },
                deductionProArea: {
                    comp: {
                        type: $pt.ComponentConstants.DeductionProportional,
                        visible: {
                            when: function (model) {
                                return model.get('BusinessConditionVO_ContractNature') == '1';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
                        }
                    },
                    pos: {
                        row: 6,
                        width: 12
                    }
                }
            }
        },
        createNonProportionalTemplate: function () {
            return {
                nonProportionalShare: {
                    comp: {
                        type: $pt.ComponentConstants.NonProportionalShare,
                        visible: {
                            when: function (model) {
                                return model.get('BusinessConditionVO_ContractNature') == '2';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
                        }
                    },
                    pos: {
                        row: 2,
                        width: 12
                    }
                },
                premiumNonProArea: {
                    comp: {
                        type: $pt.ComponentConstants.BCPremiumNonProportion,
                        visible: {
                            when: function (model) {
                                return model.get('BusinessConditionVO_ContractNature') == '2';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
                        }
                    },
                    pos: {
                        row: 3,
                        width: 12
                    }
                },
                limitNonProArea: {
                    comp: {
                        type: $pt.ComponentConstants.BCLimitNonProportion,
                        visible: {
                            when: function (model) {
                                return model.get('BusinessConditionVO_ContractNature') == '2';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
                        }
                    },
                    pos: {
                        row: 4,
                        width: 12
                    }
                },
                reinstatement: {
                    comp: {
                        type: $pt.ComponentConstants.Reinstatement,
                        visible: {
                            when: function (model) {
                                return model.get('BusinessConditionVO_ContractNature') == '2';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
                        }
                    },
                    pos: {
                        row: 5,
                        width: 12
                    }
                },
                deductionNonProArea: {
                    comp: {
                        type: $pt.ComponentConstants.DeductionNonproportional,
                        visible: {
                            when: function (model) {
                                return model.get('BusinessConditionVO_ContractNature') == '2';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
                        }
                    },
                    pos: {
                        row: 6,
                        width: 12
                    }
                }
            }
        },
        createLayoutTemplate: function () {
            var noDiffBC = this.createNonDiffTemplate();
            var proportionBC = this.createProportionalTemplate();
            var nonProportionBC = this.createNonProportionalTemplate();
            return $.extend(true, {}, noDiffBC, proportionBC, nonProportionBC)
        },
        createBCLayout: function () {
            return {
                _sections: {
                    businessSession: {
                        layout: this.createLayoutTemplate()
                    }
                }
            }
        },
        createLayout: function () {
            return $.extend(true, {}, this.createBCLayout());
            //return $.extend(true, {}, this.createBCLayout(), this.createButtonLayout());
        }
    });
    $page.layout = new layout();
}(typeof window !== "undefined" ? window : this));