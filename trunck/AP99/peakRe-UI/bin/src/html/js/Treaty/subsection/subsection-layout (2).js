(function (context) {
    var $page = $pt.getService(context, '$page');

    var layout = jsface.Class({
        createBaseLayout: function () {
            return {
                _sections: {
                    basicSectionInfo: {
                        label: "Basic Subsection Info",
                        style: 'primary-darken',
                        collapsible: true,
                        expanded: true,
                        layout: {
                            headPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        SubsectionName: {
                                            label: "Subsection Name",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            },
                                            pos: {
                                                width: 4
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    width: 12
                                }
                            },
                            businessPanel: {
                                label: "Class Of Business",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        LoBList: {
                                            label: "CoB",
                                            comp: {
                                                type: $pt.ComponentConstants.SelectTree,
                                                data: $page.codes.Class,
                                                // root: true,
                                                // check: true,
                                                hideChildWhenParentChecked: true,
                                                labelWidth: 3,
                                                treeLayout: {
                                                    comp: {
                                                        hierarchyCheck: true,
                                                        expandLevel: "all",
                                                        inactiveSlibing: false,
                                                        valueAsArray: true
                                                    }
                                                }
                                            },
                                            pos: {
                                                row: 1,
                                                width: 6
                                            }
                                        },
                                        CoBList: {
                                            label: "Sub CoB",
                                            comp: {
                                                type: $pt.ComponentConstants.SelectTree,
                                                data: $page.codes.SubClass,
                                                parentPropId: "LoBList",
                                                parentFilter: function(){
                                                  var parentValue = this.getParentPropertyValue();
                                                  return $pt.createCodeTable(this.getCodeTable().filter(
                                                    function(index){
                                                      return parentValue.indexOf(index.parentId) != -1
                                                    }
                                                  ));
                                                },
                                                // root: true,
                                                // check: true,
                                                hideChildWhenParentChecked: true,
                                                labelWidth: 3,
                                                treeLayout: {
                                                    comp: {
                                                        hierarchyCheck: true,
                                                        expandLevel: "all",
                                                        inactiveSlibing: false,
                                                        valueAsArray: true
                                                    }
                                                }
                                            },
                                            pos: {
                                                row: 2,
                                                width: 6
                                            }
                                        },
                                        CoveredList: {
                                            label: "Area Covered",
                                            comp: {
                                                type: $pt.ComponentConstants.SelectTree,
                                                data: $page.codes.CoveredArea,
                                                model : $page.controller.viewModelForArea,
                                                hideChildWhenParentChecked: true,
                                                labelWidth: 3,
                                                treeLayout: {
                                                    comp: {
                                                        hierarchyCheck: true,
                                                        expandLevel: "all",
                                                        inactiveSlibing: false,
                                                        valueAsArray: true
                                                    }
                                                }
                                            },
                                            pos: {
                                                row: 3,
                                                width: 6
                                            }
                                        },
                                        PerilList: {
                                            label: "Peril Covered",
                                            comp: {
                                                type: $pt.ComponentConstants.SelectTree,
                                                data: $page.codes.Peril,
                                                hideChildWhenParentChecked: false,
                                                labelWidth: 3,
                                                treeLayout: {
                                                    comp: {
                                                        hierarchyCheck: false,
                                                        expandLevel: "all",
                                                        inactiveSlibing: false,
                                                        //check: function (node) {
                                                        //    // console.log(node);
                                                        //    if (node.level == 1) {
                                                        //        return false;
                                                        //    } else {
                                                        //        return true;
                                                        //    }
                                                        //
                                                        //},
                                                        valueAsArray: true
                                                    }
                                                }
                                            },
                                            pos: {
                                                row: 4,
                                                width: 6
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
                                            visible: $page.controller.isVisible(),
                                            click: function () {
                                                $page.controller.save(true);
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
            }
        },
        createNonDiffTemplate: function () {
            return {
                currency: {
                    comp: {
                        type: $pt.ComponentConstants.Currency
                    },
                    pos: {
                        row: 4,
                        width: 12
                    }
                },
                reserveAndRebate: {
                    comp: {
                        type: $pt.ComponentConstants.ReserveAndRebate
                    },
                    pos: {
                        row: 10,
                        width: 12
                    }
                },
                lossCorridorArea: {
                    comp: {
                        type: $pt.ComponentConstants.BCLossCorridor
                    },
                    pos: {
                        row: 11,
                        width: 12
                    }
                },
                clause: {
                    comp: {
                        type: $pt.ComponentConstants.Clause
                    },
                    pos: {
                        row: 12,
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
                                return model.get('ContractNature') == '1';
                            },
                            depends: 'ContractNature'
                        }
                    },
                    pos: {
                        row: 5,
                        width: 12
                    }
                },
                premiumProArea: {
                    comp: {
                        type: $pt.ComponentConstants.BCPremiumProportion,
                        visible: {
                            when: function (model) {
                                return model.get('ContractNature') == '1';
                            },
                            depends: 'ContractNature'
                        }
                    },
                    pos: {
                        row: 6,
                        width: 12
                    }
                },
                limitProArea: {
                    comp: {
                        type: $pt.ComponentConstants.BCLimitProportion,
                        visible: {
                            when: function (model) {
                                return model.get('ContractNature') == '1';
                            },
                            depends: 'ContractNature'
                        }
                    },
                    pos: {
                        row: 7,
                        width: 12
                    }
                },
                deductionProArea: {
                    comp: {
                        type: $pt.ComponentConstants.DeductionProportional,
                        visible: {
                            when: function (model) {
                                return model.get('ContractNature') == '1';
                            },
                            depends: 'ContractNature'
                        }
                    },
                    pos: {
                        row: 9,
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
                                return model.get('ContractNature') == '2';
                            },
                            depends: 'ContractNature'
                        }
                    },
                    pos: {
                        row: 5,
                        width: 12
                    }
                },
                premiumNonProArea: {
                    comp: {
                        type: $pt.ComponentConstants.BCPremiumNonProportion,
                        visible: {
                            when: function (model) {
                                return model.get('ContractNature') == '2';
                            },
                            depends: 'ContractNature'
                        }
                    },
                    pos: {
                        row: 6,
                        width: 12
                    }
                },
                limitNonProArea: {
                    comp: {
                        type: $pt.ComponentConstants.BCLimitNonProportion,
                        visible: {
                            when: function (model) {
                                return model.get('ContractNature') == '2';
                            },
                            depends: 'ContractNature'
                        }
                    },
                    pos: {
                        row: 7,
                        width: 12
                    }
                },
                reinstatement: {
                    comp: {
                        type: $pt.ComponentConstants.Reinstatement,
                        visible: {
                            when: function (model) {
                                return model.get('ContractNature') == '2';
                            },
                            depends: 'ContractNature'
                        }
                    },
                    pos: {
                        row: 8,
                        width: 12
                    }
                },
                deductionNonProArea: {
                    comp: {
                        type: $pt.ComponentConstants.DeductionNonproportional,
                        visible: {
                            when: function (model) {
                                return model.get('ContractNature') == '2';
                            },
                            depends: 'ContractNature'
                        }
                    },
                    pos: {
                        row: 9,
                        width: 12
                    }
                }
            }
        },
        createButtonLayout: function () {
            return {
                buttonPanel: {
                    comp: {
                        type: $pt.ComponentConstants.ButtonFooter,
                        buttonLayout: {
                            left: [
                                {
                                    text: "Retrocession",
                                    style: "primary",
                                    visible: {
                                        when: function (model) {
                                            return model.get('ContractCategory') == '1';
                                        },
                                        depends: 'ContractCategory'
                                    },
                                    click: function (model) {
                                        if (this.getModel().get("OperateType") != 0) {
                                            if (!$page.controller.validate(model)) {
                                                return;
                                            }
                                            if (!$page.controller.save(false)) {
                                                return;
                                            }
                                        }
                                        var url = "retrocession_edit.html?ContCompId=" + this.getModel().get("ContCompId")
                                             + "&OperateType=" + this.getModel().get("OperateType")
                                            + "&SubmitType=2";
                                        if(this.getModel().get("OperateId")){
                                            url += "&OperateId=" + this.getModel().get("OperateId");
                                        }
                                        window.location.href = url;
                                    }
                                }
                            ],
                            right: [
                                {
                                    text: "Exit",
                                    style: "warning",
                                    click: function () {
                                        var url = "section.html?ContCompId=" + this.getModel().get("ParentId")
                                            + "&ContractNature=" + this.getModel().get("ContractNature")
                                            + "&OperateType=" + this.getModel().get("OperateType");
                                        if(this.getModel().get("OperateId")){
                                            url += "&OperateId=" + this.getModel().get("OperateId");
                                        }
                                        window.location.href = url;
                                    }
                                }, {
                                    text: "Save & Return",
                                    style: "primary",
                                    visible: $page.controller.isVisible(),
                                    click: function (model) {
                                        if (!$page.controller.validate(model)) {
                                            return;
                                        }
                                        if ($page.controller.save(false)) {
                                            var url = "section.html?ContCompId=" + this.getModel().get("ParentId")
                                                + "&ContractNature=" + this.getModel().get("ContractNature")
                                                + "&OperateType=" + this.getModel().get("OperateType");
                                            window.location.href = url;
                                        }
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
        createBCLayoutTemplate: function () {
            var noDiffBC = this.createNonDiffTemplate();
            var proportionBC = this.createProportionalTemplate();
            var nonProportionBC = this.createNonProportionalTemplate();
            return $.extend(true, {}, noDiffBC, proportionBC, nonProportionBC)
        },
        createBCLayout: function () {
            return {
                _sections: {
                    businessSession: {
                        label: "Business Condition",
                        style: 'primary-darken',
                        collapsible: true,
                        expanded: false,
                        layout: this.createBCLayoutTemplate()
                    }
                }
            }
        },
        createLayout: function () {
            return $.extend(true, {}, this.createBaseLayout(), this.createBCLayout(), this.createButtonLayout());
        },
    });
    $page.layout = new layout();

}(typeof window !== "undefined" ? window : this));
