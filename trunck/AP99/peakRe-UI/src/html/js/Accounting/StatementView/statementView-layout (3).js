(function (context) {
        var $page = $pt.getService(context, "$page");
        var codes = $pt.getService(context, "$codes");
        var LayoutHelper = jsface.Class({
            createFormLayout: function (tableColumnCount) {
                var layout = $.extend({
                    _sections: this.createBaseLayout(),
                    resultTablePanel: this.createResultLayout(tableColumnCount)},
                    this.createButtonLayout());
                return $pt.createFormLayout(layout);
            },
            createButtonLayout: function () {
                return {
                    Remark: {
                        label: "Remarks",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                Remarks: {
                                    comp: {
                                        type: $pt.ComponentConstants.TextArea,
                                        labelDirection: "vertical",
                                        lines: 3,
                                        enabled: false
                                    },
                                    pos: {
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {width: 12}
                    },
                    ReviewedFlag: {
                            label: "Reviewed",
                        comp: {
                            type: $pt.ComponentConstants.Check,
                            enabled: $page.controller.model.getCurrentModel().SoaStatus != '3',
                            labelWidth: 1
                        },
                        pos: {
                            width: 12
                        }
                    },

                    ReviewedBy: {
                        label: "Reviewed by",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            visible: {
                                when: function (model) {
                                    if (model.get('ReviewedFlag') != undefined && model.get('ReviewedFlag') != null) {
                                        var reviewedFlag = model.get('ReviewedFlag');
                                        return reviewedFlag;
                                    }
                                    return false;
                                },
                                depends: 'ReviewedFlag'
                            },
                            enabled:false
                        },
                        pos: {width: 6}
                    },
                    buttons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Exit",
                                        style: "warning",
                                        icon: "eject",
                                        click: function () {
                                            //this.model = $pt.createModel($page.model.createModel());
                                            var urlData = $pt.getUrlData();
                                            //this.model.mergeCurrentModel(urlData);
                                            var exitBool = urlData.Exit;
                                            if(exitBool==1){
                                                window.close();
                                            }else{
                                                $page.controller.exit();
                                            }
                                        }
                                    },
                                    {
                                        text: "Cancel",
                                        style: "primary",
                                        icon: "shopping-cart",
                                        click: function (model) {
                                            $page.controller.doCancel(model);
                                        },
                                        enabled:{
                                            depends:"status",
                                            when:function(model){
                                                var urlData = $pt.getUrlData();
                                                var exitBool = urlData.Exit;
                                                if(exitBool==1){
                                                    return false;
                                                }else{
                                                    return model.getCurrentModel().SoaStatus != '2'&&model.getCurrentModel().SoaStatus != '3'&&!$page.isView;
                                                }
                                            }
                                        }
                                    },
                                     {
                                        text: "Withdraw",
                                        style: "primary",
                                        icon: "shopping-cart",
                                        click: function (model) {
                                            $page.controller.doWithdraw(model);
                                        },
                                         enabled:{
                                             depends:"status",
                                             when:function(model){
                                                 var urlData = $pt.getUrlData();
                                                 var exitBool = urlData.Exit;
                                                 if(exitBool==1){
                                                     return false;
                                                 }else{
                                                     return model.getCurrentModel().SoaStatus == '2'&&!$page.isView;
                                                 }

                                             }
                                         }
                                    }, {
                                        text: "Withdraw ingoring cut-off date",
                                        style: "primary",
                                        icon: "shopping-cart",
                                        click: function (model) {
                                            $page.controller.doIgnoringWithdraw(model);
                                        },
                                        enabled:{
                                            depends:"status",
                                            when:function(model){
                                                var urlData = $pt.getUrlData();
                                                var exitBool = urlData.Exit;
                                                if(exitBool==1){
                                                    return false;
                                                }else{
                                                    return model.getCurrentModel().SoaStatus == '2'&&$page.controller.model.getCurrentModel().IsCutOffPeriod=="true"&&!$page.isView;
                                                }
                                            }
                                        }
                                    },
                                    {
                                        text: "Save",
                                        style: "primary",
                                        icon: "save",
                                        enabled:{
                                            when:function(){
                                                var urlData = $pt.getUrlData();
                                                var exitBool = urlData.Exit;
                                                if(exitBool==1){
                                                    return false;
                                                }else{
                                                    return true;
                                                }
                                            }
                                        },
                                        click: function (model) {
                                            $page.controller.doSaveSoaView(model);
                                        }
                                    },

                                ]
                            }
                        },
                        pos: {width: 12}
                    }
                }
            },
            createBaseLayout: function () {
                return {
                    statementInput: {
                        label: "Statement View",
                        style: 'primary-darken',
                        pos: {
                            width: 12
                        },
                        collapsible: true,
                        expanded: true,
                        layout: {
                            ContractId: {
                                label: "Contract OverView",
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [
                                            {
                                                text: "Contract OverView",
                                                style: "link",
                                                click: function (model) {
                                                    var contractId = model.get("ContractId");
                                                    var url = "";
                                                    url = $pt.getURL("ui.queryView.view");
                                                    url = url + "?ContCompId="+contractId;
                                                    window.open(url);
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {row: 1,
                                width:12}
                            },

                            ContractCode: {
                                label: "Contract ID",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {row: 2}
                            },
                            FinancialYear: {
                                label: "Financial Year",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {row: 2}
                            },
                            FinancialQuarter: {
                                label: "Financial Quarter",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.FNQuarter,
                                    enabled: false
                                },
                                pos: {row: 2}
                            },
                            Cedent: {
                                label: "Cedent",
                                comp: {
                                    type: $pt.ComponentConstants.CodeSearch,
                                    searchTriggerDigits: 6,
                                    enabled: false
                                },
                                pos: {row: 3}
                            },
                            SoaId: {
                                label: "Statement ID",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {row: 3}
                            },
                            UwYear: {
                                label: "UW Year",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {row: 3}
                            },
                            Broke: {
                                label: "Broker",
                                comp: {
                                    type: $pt.ComponentConstants.CodeSearch,
                                    searchTriggerDigits: 6,
                                    enabled: false
                                },
                                pos: {row: 4}
                            },
                            ReceivedDate: {
                                label: "Date Received",
                                comp: {
                                    type: $pt.ComponentConstants.Date,
                                    format: "DD/MM/YYYY",
                                    enabled: false
                                },
                                pos: {row: 4}
                            },
                            DueDate: {
                                label: "Due Date",
                                comp: {
                                    type: $pt.ComponentConstants.Date,
                                    format: "DD/MM/YYYY",
                                    enabled: false
                                },
                                pos: {row: 4}
                            },
                            AccountLevel: {
                                label: "Account Level",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.AccountLevel,
                                    enabled: false
                                },
                                pos: {row: 5}
                            },
                            BizType: {
                                label: "Business Type",
                                comp: {
                                    type: $pt.ComponentConstants.Radio,
                                    data: $pt.createCodeTable([{id: "1", text: "Direct"}, {id: "2", text: "Indirect"}]),
                                    enabled: false
                                },
                                pos: {row: 5}
                            },
                            CedentYear: {
                                label: "Cedent Year",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {row: 5}
                            },
                            CedentQuarter: {
                                label: "Cedent Quarter",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.CedentQuarter,
                                    enabled: false
                                },
                                pos: {row: 6}
                            },
                            TaskreleaserName: {
                                label: "Task Releaser",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {row: 6}
                            },
                            TakswithdrawName: {
                                label: "Withdraw User",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {row: 6}
                            },
                            TaskcreatorName: {
                                label: "Task Creator",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {row: 7}
                            },
                            TaskCreator: {
                                label: "Task Creator",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false,
                                    visible: false
                                },
                                pos: {row: 7}
                            },
                            CedentPeriod: {
                                label: "Cedent Period",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.CedentPeriod,
                                    enabled: false
                                },
                                pos: {row: 7}
                            },
                            SoaText: {
                                label: "Statement Text",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                   // enabled: $page.controller.model.getCurrentModel().SoaStatus != '3',
                                    enabled: {
                                        when: function (model) {
                                            if ($page.controller.model.getCurrentModel().SoaStatus == '3') {
                                                return false;
                                            }
                                            return !model.get('ReviewedFlag');
                                        },
                                        depends: 'ReviewedFlag'
                                    },
                                    labelWidth: 2
                                },
                                pos: {width: 8, row: 8}

                            }
                        }
                    }
                }
            },
            createResultTableColumns: function (tableColumnCount) {
                var columns = [{
                    title: " ",
                    data: "LineTitle",
                    width: "10%"
                }];
                var index = 0;
                for (; index < tableColumnCount; index++) {
                    columns.push({
                        title: ('Statement Section'+0+(index +1)+''),
                        data: 'Column' + (index + 1),
                       // width: 92 / tableColumnCount + '%'
                        width: "8%"
                    });
                }
                columns.push({
                    title:  'Total',
                    data: 'Total',
                    width: "8%"
                });
                return columns;
            },
            createResultLayout: function (tableColumnCount) {
                return {
                    label: 'Currency Panel',
                    dataId: 'Currency',
                    comp: {
                        type: $pt.ComponentConstants.ArrayPanel,
                        itemTitle: {
                            depends: 'ContractCurrecy',
                            when: function (item) {
                                return $page.codes.Currency.getText(item.get("ContractCurrecy"))
                            }
                        },
                        ContractCurrecy: {
                            label: 'ContractCurrecy',
                            comp: {
                                type: $pt.ComponentConstants.Text,
                                enabled: false
                            },
                            pos: {row: 1}
                        },
                        editLayout: {
                            queryResult: {
                                //   label: "Query Result",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    columns: this.createResultTableColumns(tableColumnCount)
                                },
                                pos: {width: 12}
                            }
                        },
                        style: 'primary-darken',
                    },
                    pos: {
                        width: 12
                    }
                }
            }
        });
        $page.layoutHelper = new LayoutHelper();
    }(typeof window !== "undefined" ? window : this)
);
