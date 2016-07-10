(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');
    var limitType = $pt.createCodeTable([
        {id: '1', text: 'QS'},
        {id: '2', text: 'Surplus'},
        {id: '3', text: 'XOL'},
        {id: '4', text: 'Stop Loss'},
        {id: '7', text: 'Combined QS&SPL'}
    ]);

    NTable.registerInlineEditor('TextAreaReadOnly', {
        comp: {
            type: {type: $pt.ComponentConstants.TextArea, label: false },
            lines: 3,
            view :true
        }
    });

    var Layout = jsface.Class({
        createAddContractSectionDialogLayout: function (treeCodes) {
            return {
                ContractCode: {
                    label: "Contract ID",
                    comp: {
                       type: $pt.ComponentConstants.NContractSearchText,
                        searchTriggerDigits: 6,
                        contractStatus: [4],
                        contractCategory: 2,
                        url: $ri.getRestfulURL("action.contract.contractHome") + "/page"
                    },
                    pos: {
                        row: 1,
                        width: 4
                    }
                },
                UnderWritingYear: {
                    label: "UW Year",
                    comp: {
                        type: $pt.ComponentConstants.Text,
                        // format:"YYYY",
                        // valueFormat:"YYYY"
                    },
                    pos: {
                        row: 1,
                        width: 4
                    }
                },
                SectionTree: {
                    dataId: "SectionTree",
                    comp: {
                        type: $pt.ComponentConstants.Tree,
                        root: false,
                        check: function (node) {
                            // console.log(node);
                            if (node.level == 1) {
                                return false;
                            } else {
                                return true;
                            }

                        },
                        valueAsArray: true,
                        data: treeCodes
                    },
                    pos: {
                        row: 2,
                        width: 12
                    }
                },
                searchBtn: {
                    label: "Search",
                    comp: {
                        type: $pt.ComponentConstants.Button,
                        click: function (dialogModel) {
                            // if (!$page.controller.validate(dialogModel)) {
                            //     return;
                            // }
                            $page.controller.onAddContractSectionSearch(dialogModel);
                        }
                    },
                    pos: {
                        row: 1,
                        width: 4
                    }
                }
            };
        },
        createFormLayout: function () {
            return {
                _sections: {
                    defaultSection: {
                        label: "Retrocession Arrangement",
                        style: 'primary-darken',
                        collapsible: true,
                        expanded: true,
                        layout: {
                            RetrocessionList: {
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    addable: true,
                                    removable: true,
                                    sortable: false,
                                    columns: [
                                        {
                                            title: "Sequence",
                                            data: "Sequence",
                                            inline: "integer",
                                            width: "10%"
                                        }, {
                                            title: "Contract ID",
                                            data: "RetroContractCode",
                                            width: "10%"
                                        }, {
                                            title: "Retro Section/Sub-Section",
                                            data: "RetroSectionName",
                                            width: "20%"
                                        }, {
                                            title: "Nature of Contract",
                                            data: "ContractNature",
                                            width: "15%",
                                            codes: $page.codes.ContractNature
                                        }, {
                                            title: "Limit Type",
                                            data: "LimitType",
                                            width: "10%",
                                            codes: limitType
                                        }, {
                                            title: "Brief Text",
                                            data: "BriefText",
                                            inline: "TextAreaReadOnly",
                                            width: "35%"
                                        }
                                    ],
                                    addClick: function (model, rowModel, layout) {
                                        var treeCodesData = null;
                                        var dialogModel = null;
                                        $page.controller.addContractSection(treeCodesData, dialogModel);
                                    },
                                    canRemove: function (model, item) {
                                        if (item.RetroId && item.RetroId != 0) {
                                            model.add("DeleteRetrocessionList", item);
                                        }
                                        this.getModel().remove(this.getDataId(), item);
                                    }
                                },
                                css:{
                                    comp: "inline-editor",
                                    cell: "title-align"
                                },
                                pos: {
                                    row: 3,
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
                                                    var url;
                                                    var urlData = $pt.getUrlData();
                                                    var exitBool = urlData.Exit;
                                                    if(exitBool == 1){
                                                        window.close();
                                                    }else{
                                                    if (this.getModel().get("SubmitType") == '1') {
                                                        url = "section.html?ContCompId=" + this.getModel().get("ContCompId")
                                                            + "&ContractNature=" + this.getModel().get("ContractNature")
                                                            + "&OperateType=" + this.getModel().get("OperateType");
                                                    } else if (this.getModel().get("SubmitType") == '2') {
                                                        url = "subsection.html?ContCompId=" + this.getModel().get("ContCompId")
                                                            + "&OperateType=" + this.getModel().get("OperateType");
                                                    }
                                                    if(this.getModel().get("OperateId")){
                                                        url += "&OperateId=" + this.getModel().get("OperateId");
                                                    }
                                                    window.location.href = url;}
                                                }
                                            }, {
                                                text: "Save & Return",
                                                style: "primary",
                                                visible:$page.controller.isVisible(),
                                                click: function () {
                                                    if ($page.controller.validate()) {
                                                        if ($page.controller.onAddRetroSectionSubmit()) {
                                                            var url;
                                                            if (this.getModel().get("SubmitType") == '1') {
                                                                url = "section.html?ContCompId=" + this.getModel().get("ContCompId")
                                                                    + "&ContractNature=" + this.getModel().get("ContractNature")
                                                                    + "&OperateType=" + this.getModel().get("OperateType");
                                                            } else if (this.getModel().get("SubmitType") == '2') {
                                                                url = "subsection.html?ContCompId=" + this.getModel().get("ContCompId")
                                                                    + "&OperateType=" + this.getModel().get("OperateType");
                                                            }
                                                            window.location.href = url;
                                                        }
                                                    }
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 14,
                                    width: 12
                                }
                            }
                        }
                    }
                }
            }
        }
    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));
