(function (context) {
    var $page = $pt.getService(context, '$page');
    var ShareType = $pt.createCodeTable([
        {
            id: '1',
            text: 'QS'
        }, {
            id: '2',
            text: 'Surplus'
        },{
            id: '7',
            text: "Combined QS&SPL"
        }
    ]);
    if ($pt.getUrlData().ContractNature != '1') {
        ShareType = $pt.createCodeTable([
            {
                id: '3',
                text: 'XOL'
            }, {
                id: '4',
                text: 'Stop Loss'
            }
        ]);
    }

    var layout = jsface.Class({
        createBaseLayout: function () {
            return {
                _sections: {
                    basicSectionInfo: {
                        label: "Basic Section Info",
                        style: 'primary-darken',
                        collapsible: true,
                        expanded: true,
                        layout: {
                            headPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        ShareType: {
                                            label: "Cession Type",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: ShareType
                                            },
                                            pos: {
                                                width: 4
                                            }
                                        },
                                        SectionName: {
                                            label: "Section Name",
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
                                                parentFilter: function(data){
                                                  var parentValue = this.getParentPropertyValue();
                                                  return $pt.createCodeTable(this.getCodeTable().filter(
                                                    function(data){
                                                      return parentValue.indexOf(data.parentId) != -1
                                                    }
                                                  ));
                                                },
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
                                                hideChildWhenParentChecked: true,
                                                labelWidth: 3,
                                                model : $page.controller.viewModelForArea,
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
                                                //type: $pt.ComponentConstants.Tree,
                                                //root: false,
                                                data: $page.codes.Peril,
                                                //hideChildWhenParentChecked: false,
                                                //model : $page.controller.viewModelForPerilArea,
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
                            SubsectionList: {
                                label: "Subsections",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    addable: true,
                                    view: {
                                      depends: "OperateType",
                                      when : function(model){return this.model.get("OperateType") == 0 || this.model.get("OperateType") == 5}
                                    },
                                    columns: [
                                        {
                                            title: "No",
                                            data: "ContCompId",
                                            width: "8%"
                                        }, {
                                            title: "Name",
                                            data: "SubsectionName",
                                            width: "18%"
                                        }, {
                                            title: "Main Currency",
                                            data: "MainCurrency",
                                            width: "15%"
                                        }, {
                                            title: "Our share",
                                            data: "OurShare",
                                            render : function(row){
                                              var value = row.OurShare;
                                              if(value == 0){
                                                return "0.00%";
                                              }else{
                                                  if(!value){
                                                    value = "";
                                                  }
                                              }
                                              value = isNaN(value) || (value + '').isBlank() ? value : (value.toFixed(4) + '').movePointRight(2)+ "%";
                                              return value
                                            },
                                            width: "15%"
                                        }, {
                                            title: "Premium",
                                            data: "Premium",
                                            width: "15%",
                                            render: function (row) {
                                                return $helper.formatNumberForLabel(row.Premium, 2);
                                            }
                                        }, {
                                            title: "Deductions",
                                            data: "Deductions",
                                            render : function(row){
                                              var value = row.Deductions;
                                              if(value == 0){
                                                return "0.00%";
                                              }else{
                                                  if(!value){
                                                    value = "";
                                                  }
                                              }
                                              value = isNaN(value) || (value + '').isBlank() ? value : (value.toFixed(4) + '').movePointRight(2)+ "%";
                                              return value
                                            },
                                            width: "20%"
                                        }
                                    ],
                                    addClick: function (model, rowModel, layout) {
                                        if (!$page.controller.validate(model)) {
                                            return;
                                        }
                                        if ($page.controller.save()) {
                                            var url = "subsection.html?ParentId=" + this.getModel().get("ContCompId")
                                                + "&OperateType=" + this.getModel().get("OperateType");
                                            window.location.href = url;
                                        }
                                    },
                                    rowOperations: [
                                        {
                                            icon: "edit",
                                            click: function (row) {
                                                if (this.getModel().get("OperateType") != 0) {
                                                    if (!$page.controller.save()) {
                                                        return;
                                                    }
                                                }
                                                var url = "subsection.html?ContCompId=" + row.ContCompId
                                                    + "&OperateType=" + this.getModel().get("OperateType");
                                                if (this.getModel().get("OperateId")) {
                                                    url += "&OperateId=" + this.getModel().get("OperateId");
                                                }
                                                window.location.href = url;
                                            }
                                        }, {
                                            icon: "trash",
                                            text : "delete",
                                            visible : {when : function(row){
                                                if ($page.controller.model.get("OperateType") == 0 || $page.controller.model.get("OperateType") == 5) {
                                                    return false;
                                                }else{
                                                    return !row.get("HasInforce");
                                                }
                                            }},
                                            click: function (row) {
                                              var removeRow = function (row) {
                                                if (row.ContCompId && row.ContCompId != 0) {
                                                    this.getModel().add("DeleteSubSections", row.ContCompId);
                                                }
                                                this.getModel().remove(this.getDataId(), row);
                                        				$pt.Components.NConfirm.getConfirmModal().hide();
                                        			};
                                        			$pt.Components.NConfirm.getConfirmModal().show(NTable.REMOVE_CONFIRM_TITLE, NTable.REMOVE_CONFIRM_MESSAGE, removeRow.bind(this, row));
                                            }
                                        }
                                    ]
                                },
                                css: {
                                    comp: "inline-editor",
                                    cell: "title-align"
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
                                return model.get('BusinessConditionVO_ContractNature') == '1';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
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
                                return model.get('BusinessConditionVO_ContractNature') == '1';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
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
                                return model.get('BusinessConditionVO_ContractNature') == '1';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
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
                                return model.get('BusinessConditionVO_ContractNature') == '1';
                            },
                            depends: 'BusinessConditionVO_ContractNature'
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
                        row: 6,
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
                        row: 7,
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
                        row: 8,
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
                                        $page.controller.retrocessionClick(model);
                                    }
                                }, {
                                    text: "Pricing Estimate",
                                    style: "primary",
                                    click: function (model) {
                                        $page.controller.pricingClick(model);
                                    }
                                }
                            ],
                            right: [
                                {
                                    text: "Exit",
                                    style: "warning",
                                    click: function () {
                                        var url = "contractHome.html?ContCompId=" + this.getModel().get("ParentId")
                                            + "&OperateType=" + this.getModel().get("OperateType")
                                            + "&backType=true";
                                        if(this.getModel().get("OperateId")){
                                            url += "&OperateId=" + this.getModel().get("OperateId");
                                        }
                                        var urlData = $pt.getUrlData();
                                        var ExitBool = urlData.Exit;
                                        if(ExitBool==1){
                                            window.close();
                                        }else{
                                            window.location.href = url;
                                        }
                                    }
                                }, {
                                    text: "Save & Return",
                                    style: "primary",
                                    visible: $page.controller.isVisible(),
                                    click: function (model) {
                                        var url = "contractHome.html?ContCompId=" + this.getModel().get("ParentId")
                                            + "&OperateType=" + this.getModel().get("OperateType")
                                            + "&backType=true";
                                        if ($page.controller.save()) {
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
            //return $.extend(true, {}, this.createBaseLayout(), this.createButtonLayout());
        }
    });
    $page.layout = new layout();

}(typeof window !== "undefined" ? window : this));
