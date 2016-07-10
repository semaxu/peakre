(function (context) {
        var $page = $pt.getService(context, "$page");
        var codes = $pt.getService(context, "$codes");
        NTable.registerInlineEditor('searchEntryItem', {
            comp: {
                type: {type: $pt.ComponentConstants.CodeSearch, label: false},
                labelPropId: 'namePropertyOfCode',
                searchTriggerDigits: 4,
                codeTableId: $pt.getUrlData().statementType

            }
        });

        NTable.registerInlineEditor("numberReadOnly", {
            comp: {
                type: {type: $pt.ComponentConstants.Text, label: false},
                enabled : false
            },
            base : $helper.baseAmount(2)
        });

        var saoLayout = jsface.Class({
            panelTemplate : function(){
                return {
                    label: 'Normal Panel',
                        dataId: 'Sections',
                    comp: {
                    type: $pt.ComponentConstants.ArrayPanel,
                        itemTitle: {
                        depends: 'ContractSection',
                            when: function (item) {
                            return 'Section';
                        }
                    },
                    style: 'primary-darken',
                        collapsible: true,
                        editLayout: {
                        ContractSection: {
                            label: 'Contract Section',
                                comp: {
                                type: $pt.ComponentConstants.Text,
                                    enabled: false,
                                    labelWidth:2
                            },
                            pos: {row: 1,width: 12}
                        },
                        ContracCompId: {
                            label: 'Contrac CompId',
                                comp: {
                                type: $pt.ComponentConstants.Text,
                                    enabled: false,
                                    visible:false
                            },
                            pos: {row: 1}
                        },
                        Cob: {
                            label: "Main Sub CoB",
                                comp: {
                                type: $pt.ComponentConstants.Select,
                                    data: $page.codes.SubClass
                            },
                            pos: {row: 2}
                        },
                        UwArea: {
                            label: "Cedent Country",
                                comp: {
                                type: $pt.ComponentConstants.Select,
                                    data: $page.codes.CedentCountry
                            },
                            pos: {row: 2}
                        },

                        ShareType: {
                            label: "Cession Type",
                                comp: {
                                type: $pt.ComponentConstants.Select,
                                    data: $page.codes.ShareType
                            },
                            pos: {row: 2}
                        },
                        Premium: {
                            label: "Premium",
                                comp: {
                                type: $pt.ComponentConstants.Text,
                                    format: $helper.formatNumber(2),
                                    enabled: false

                            },
                            pos: {row: 3}
                        },
                        IncurredLoss: {
                            label: "IncurredLoss",
                                comp: {
                                type: $pt.ComponentConstants.Text,
                                    format: $helper.formatNumber(2),
                                    enabled: false
                            },
                            pos: {row: 3}
                        },
                        Expense: {
                            label: "Expense",
                                comp: {
                                type: $pt.ComponentConstants.Text,
                                    format: $helper.formatNumber(2),
                                    enabled: false
                            },
                            pos: {row: 3}
                        },
                        EntryItems: {
                            comp: {
                                type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    addable: true,
                                    addClick: function (model) {
                                    model.add("EntryItems", $page.model.createEntryItemTemplate());
                                },
                                removable: true,
                                    columns: [
                                    {
                                        title: "Entry",
                                        data: "EntryItem",
                                        inline: "text",
                                        width: "8%",
                                        visible: false
                                    },
                                    {
                                        title: "Entry Item",
                                        data: "EntryCode",
                                        inline: "searchEntryItem",
                                        width: "20%"
                                    }, {
                                        title: "Amount",
                                        data: "Amount",
                                        inline: "number",
                                        width: "20%"
                                    }, {
                                        title: "Percentage",
                                        data: "Percentage",
                                        inline: $helper.registerInlinePercentage("percentage",2),
                                        width: "20%"
                                    }, {
                                        title: "Our Share Amount",
                                        data: "ShareAmount",
                                        //   inline: "number",
                                        width: "20%",
                                        //render: function (row) {
                                        //    return $helper.formatNumberForLabel(row.ShareAmount, 2);
                                        //}
                                        inline: "numberReadOnly"


                                    }
                                ],
                                    rowListener: [
                                    {
                                        id: 'Amount',
                                        listener: function (evt) {
                                            var amount = evt.model.get("Amount");
                                            if (isNaN(amount)){
                                                NConfirm.getConfirmModal().show({
                                                    title: 'Message',
                                                    disableClose: true,
                                                    messages: ['Please input number' ]
                                                });
                                                evt.model.set("Amount", parseFloat(0).toFixed(2));
                                                evt.model.set("ShareAmount", parseFloat(0).toFixed(2));

                                            }else{
                                                var shareamount;
                                                var percentage = evt.model.get("Percentage");
                                                if (amount == "" || amount == null) {
                                                    shareamount = 0;
                                                } else if (percentage == null || percentage == "") {
                                                    shareamount = amount;
                                                } else {
                                                    shareamount = parseFloat(amount).toFixed(2) * parseFloat(percentage).toFixed(2);
                                                }
                                                evt.model.set("ShareAmount", parseFloat(shareamount).toFixed(2));
                                            }

                                        }
                                    },
                                    {
                                        id: 'Percentage',
                                        listener: function (evt) {
                                            var shareAmount = parseFloat(evt.model.get("Amount")).toFixed(2) * parseFloat(evt.model.get("Percentage")).toFixed(2);
                                            evt.model.set("ShareAmount", parseFloat(shareAmount).toFixed(2));
                                        }
                                    } ,{
                                        id: 'EntryCode',
                                        listener: function (evt) {
                                            var entryCode = evt.new;
                                            if (entryCode.length == 4) {
                                                var returnVal = $page.controller.doQueryEntryItem(entryCode);
                                                if(returnVal == "wrong"){
                                                    NConfirm.getConfirmModal().show({
                                                        title: 'Message',
                                                        disableClose: true,
                                                        messages: ['Please input the right entry code' ]
                                                    });
                                                    evt.new = ""
                                                }else{
                                                    var contractNature = $page.controller.model.getCurrentModel().ContractNature;
                                                    if (contractNature==undefined||contractNature == "" || contractNature == null) {

                                                    } else if(contractNature=="1"&&(entryCode == "1010" ||entryCode == "1011"||entryCode == "1020")) {
                                                        NConfirm.getConfirmModal().show({
                                                            title: 'Message',
                                                            disableClose: true,
                                                            messages: ['Contract is Proportional, the entry item '+entryCode+  ' is invalid' ]
                                                        });
                                                        evt.new = "";
                                                    } else if(contractNature=="2"&&entryCode == "1000"){
                                                        NConfirm.getConfirmModal().show({
                                                            title: 'Message',
                                                            disableClose: true,
                                                            messages: ['Contract is Non-Proportional, the entry item '+entryCode+  ' is invalid' ]
                                                        });
                                                        evt.new = ""
                                                    }
                                                }

                                            }

                                        }
                                    }
                                    ,
                                    {
                                        id: 'ShareAmount',
                                        listener: function (evt) {
                                            var old = evt.old;
                                            var newVal = evt.new;
                                            var entryCode = evt.model.get("EntryCode");
                                            if (undefined == old || isNaN(old) || null == old || "" == old){
                                                old = 0;
                                            }
                                            if (isNaN(newVal) || "" == newVal){
                                                evt.model.set("ShareAmount", "");
                                                newVal = 0;
                                            }
                                            //var model = evt.model.parent();
                                            //
                                            //$page.controller.form.forceUpdate();

                                        }
                                    }
                                ]
                            },
                            css: {
                                comp: "centerTitle"
                            },
                            pos: {row: 3, width: 12}
                        },
                        SectionTotal: {
                            label: "Tech.Result",
                                comp: {
                                type: $pt.ComponentConstants.Text,
                                    format: $helper.formatNumber(2),
                                    enabled: false,
                                    visible: false

                            },
                            pos: {row: 4}
                        },
                        CashBalance: {
                            label: "Cash Balance",
                                comp: {
                                type: $pt.ComponentConstants.Text,
                                    format: $helper.formatNumber(2),
                                    enabled: false

                            },
                            pos: {row: 5}
                        },
                        IsFullyTransfer: {
                            label: "  Fully Transfer (generation of estimation &UPR/DAC will stop)",
                                comp: {
                                type: $pt.ComponentConstants.Check,
                                    labelWidth: 12,
                                    visible: {
                                    when: function (model) {
                                        if ($page.controller.model.get('StatementType')=='3') {
                                            return true;
                                        }
                                        return false;
                                    },
                                    depends: 'IsFullyTransfer'
                                }
                            },
                            pos: {

                                row: 6
                            },

                        },
                        rightButtons: {
                            comp: {
                                type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                    right: {
                                        text: "Delete Section",
                                            icon: "remove",
                                            style: "warning",
                                            click: function (item) {
                                            $page.controller.onRemoveSectionClicked(item);
                                        }
                                    }
                                }

                            },
                            pos: {
                                width: 12
                            }
                        }
                    }

                },
                    pos: {
                        width: 12
                    }

                }
            },
            arrayTabTemplate : function(){
                return {
                    arrayTab: {
                        label: 'Normal Panel',
                        dataId: 'Currencies',

                        comp: {
                            centralId : "CurrencyTabs",
                            type: $pt.ComponentConstants.ArrayTab,
                            style: "primary-darken",
                                itemTitle: {
                                depends: 'CurrencyType',
                                when: function (item) {
                                    if (item.get("CurrencyType") == "+") {
                                        return item.get("CurrencyType");
                                    }
                                    return $page.codes.Currency.getText(item.get("CurrencyType"))
                                }
                            },
                            addable : true,
                            onAdd : function(){
                                var newItem = {CurrencyType: null};
                                var currencyForm = NModalForm.createFormModal("Choose Currency......");
                                var currencyModel = $pt.createModel({Currency: null});
                                var currencyLayout = $pt.createFormLayout({
                                    Currency: {
                                        label: "Currency",
                                        comp: {
                                            type: $pt.ComponentConstants.Select,
                                            data: $page.codes.Currency
                                        }
                                    }
                                });

                                currencyForm.show({
                                    model: currencyModel,
                                    layout: currencyLayout,
                                    buttons: {
                                        reset: false,
                                        validate: false,
                                        cancel: false,
                                        right: [{
                                            icon: 'eject',
                                            text: 'Cancel',
                                            style: 'warning',
                                            click: function () {
                                                currencyForm.hide();
                                            }
                                        }, {
                                            icon: 'save',
                                            text: 'Confirm',
                                            style: 'primary',
                                            click: function () {
                                                var currency = this.getModel().get("Currency");
                                                var arrayForAdd = $page.controller.model.get('Currencies');
                                                for (var i = 0; i < arrayForAdd.length; i++) {
                                                    if (arrayForAdd[i].CurrencyType == currency) {
                                                        alert("The currency group has exist! Please choose another one!");
                                                        return;
                                                    }
                                                }
                                                newItem.CurrencyType = this.getModel().get("Currency");
                                                $page.controller.model.add('Currencies', newItem);
                                                currencyForm.hide();
                                            }
                                        }]
                                    },
                                    draggable: false
                                });
                                return false;
                            },
                            editLayout:
                                //function (item) {
                                //if (item.get("CurrencyType") == "+") {
                                //
                                //    return {
                                //        nothing: {
                                //            comp: {type: $pt.ComponentConstants.Nothing}
                                //        }
                                //    }
                                //} else {
                                //    return
                                    {
                                        panel: $page.layout.panelTemplate(),
                                        buttonPanel: {
                                            comp: {
                                                type: $pt.ComponentConstants.ButtonFooter,
                                                buttonLayout: {
                                                    right: [
                                                        {
                                                            text: "Delete Currency Group",
                                                            style: "warning",
                                                            icon: "remove",
                                                            click: function (itemModel) {
                                                                $page.controller.onRemoveCurrencyClicked(itemModel);
                                                            }
                                                        },
                                                        {
                                                            text: "Add Section",
                                                            style: "primary",
                                                            icon: "plus",
                                                            click: function (model) {
                                                                $page.controller.onAddContractSectionSearch(model);
                                                            }
                                                        }
                                                    ]
                                                }
                                            },
                                            pos: {width: 12}
                                        }
                                    //}

                                //}
                            },
                            icon: 'bookmark',
                            //canActive: true,
                                //function (nextValue, nextIndex, currentValue, currentIndex) {
                                //var array = $page.controller.model.get('Currencies');
                                //var _this = this;
                                ////var isCurrencyTypeAdd = 0;
                                ////if (array != undefined && array != null) {
                                ////    array.forEach(function (array) {
                                ////        if (array.CurrencyType == "+") {
                                ////            isCurrencyTypeAdd = 1;
                                ////        }
                                ////    });
                                ////}
                                //var newItem;
                                ////if (isCurrencyTypeAdd == '0') {
                                ////    newItem = {CurrencyType: "+"};
                                ////    $page.controller.model.add("Currencies", newItem);
                                ////}
                                //
                                //if (nextIndex == ($page.controller.model.get('Currencies').length - 1)) {
                                //    newItem = {CurrencyType: null};
                                //    var currencyForm = NModalForm.createFormModal("Choose Currency......");
                                //    var currencyModel = $pt.createModel({Currency: null});
                                //    var currencyLayout = $pt.createFormLayout({
                                //        Currency: {
                                //            label: "Currency",
                                //            comp: {
                                //                type: $pt.ComponentConstants.Select,
                                //                data: $page.codes.Currency
                                //            }
                                //        }
                                //    });
                                //
                                //    currencyForm.show({
                                //        model: currencyModel,
                                //        layout: currencyLayout,
                                //        buttons: {
                                //            reset: false,
                                //            validate: false,
                                //            cancel: false,
                                //            right: [{
                                //                icon: 'eject',
                                //                text: 'Cancel',
                                //                style: 'warning',
                                //                click: function () {
                                //                    currencyForm.hide();
                                //                }
                                //            }, {
                                //                icon: 'save',
                                //                text: 'Confirm',
                                //                style: 'primary',
                                //                click: function () {
                                //                    var currency = this.getModel().get("Currency");
                                //                    var arrayForAdd = $page.controller.model.get('Currencies');
                                //                    for (var i = 0; i < arrayForAdd.length; i++) {
                                //                        if (arrayForAdd[i].CurrencyType == currency) {
                                //                            alert("The currency group has exist! Please choose another one!");
                                //                            return;
                                //                        }
                                //                    }
                                //                    newItem.CurrencyType = this.getModel().get("Currency");
                                //                    $page.controller.model.add('Currencies', newItem, arrayForAdd.length - 1);
                                //                    currencyForm.hide();
                                //                }
                                //            }]
                                //        },
                                //        draggable: false
                                //    });
                                //    return false;
                                //} else {
                                //    return true;
                                //}
                            //}
                        },
                        pos: {width: 12, section: "statementInput"}
                    }
                }
            },
            baseLayout : function(){
                return {
                    _sections: {
                        statementInput: {
                            label: "Statement Input",
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
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.FNYear
                                    },
                                    pos: {row: 2}
                                },
                                FinancialQuarter: {
                                    label: "Financial Quarter",
                                    comp: {
                                        type: $pt.ComponentConstants.Text,
                                        data: $page.codes.FNQuarter,
                                        enabled: false
                                    },
                                    pos: {row: 2}
                                },
                                Cedent: {
                                    label: "Cedent",
                                    base: $helper.basePartnerSearch(),
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
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.UwYear
                                    },
                                    pos: {row: 3}
                                },
                                Broke: {
                                    label: "Broker",
                                    base: $helper.basePartnerSearch(),
                                    pos: {row: 4}
                                },
                                ReceivedDate: {
                                    label: "Date Received",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {row: 4}
                                },
                                DueDate: {
                                    label: "Due Date",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
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
                                        data: $pt.createCodeTable([{id: "1", text: "Direct"}, {id: "2", text: "Indirect"}])
                                    },
                                    pos: {row: 5}
                                },
                                CedentYear: {
                                    label: "Cedent Year",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.CedentYear
                                    },
                                    pos: {row: 5}
                                },
                                CedentQuarter: {
                                    label: "Cedent Quarter",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.CedentQuarter
                                    },
                                    pos: {row: 6}
                                },
                                CedentPeriod: {
                                    label: "Cedent Period",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.CedentPeriod
                                    },
                                    pos: {row: 6}
                                },
                                SoaText: {
                                    label: "Statement Text",
                                    comp: {
                                        type: $pt.ComponentConstants.Text,
                                        labelWidth: 2
                                    },
                                    pos: {width: 8, row: 7}
                                },
                                TaskCreator: {
                                    label: "Task Creator",
                                    comp: {
                                        type: $pt.ComponentConstants.Text,
                                        visible:false
                                    },
                                    pos: {row: 7},
                                },
                            }
                        }
                    }
                }
            },
            buttons : function(){
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
                                            lines: 3
                                    },
                                    pos: {
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {width: 12, section: "statementInput"}
                    },
                    buttons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                                buttonLayout: {
                                left: [
                                    {
                                        text: "Attachment",
                                        style: "primary",
                                        icon: "file",
                                        click: function (item) {
                                            $pt.viewAttachment(7, item.getCurrentModel().SoaId, false);
                                        }
                                    }, {
                                        text: "Preview of Summary",
                                        style: "primary",
                                        icon: "eye",
                                        click: function (item) {
                                            if ($page.controller.model.get('StatementType')=='3'||$page.controller.model.get('StatementType')=='2') {
                                                $page.controller.doPTFSummary(item);
                                            }else{
                                                $page.controller.doSummary(item);
                                            }

                                        }
                                    }
                                ],
                                    right: [
                                    {
                                        text: "Exit",
                                        style: "warning",
                                        icon: "eject",
                                        click: function (model) {
                                            $page.controller.exit();
                                            //  window.location.href = "statementQuery.html";
                                        }
                                    }, {
                                        text: "Submit Ignoring Cut-off date",
                                        style: "primary",
                                        icon: "shopping-cart",

                                        click: function (model) {
                                            if ($page.controller.model.get('StatementType')=='3'||$page.controller.model.get('StatementType')=='2') {
                                                $page.controller.doPTFBeforeRevered(model);
                                            }else{
                                                $page.controller.doIgnoringBeforeRevered(model);
                                            }

                                        },
                                        enabled: {
                                            depends: "status",
                                            when: function (model) {
                                                return $page.controller.model.getCurrentModel().SoaStatus == '1'&&$page.controller.model.getCurrentModel().IsCutOffPeriod=="true";
                                            }
                                        }
                                    },
                                    {
                                        text: "Submit",
                                        style: "primary",
                                        icon: "shopping-cart",

                                        click: function (model) {
                                            if ($page.controller.model.get('StatementType')=='3'||$page.controller.model.get('StatementType')=='2') {
                                                $page.controller.doPTFBeforeRevered(model);
                                            }else{
                                                $page.controller.doBeforeRevered(model);
                                            }

                                        },
                                        enabled: {
                                            depends: "status",
                                            when: function (model) {
                                                return $page.controller.model.getCurrentModel().SoaStatus == '1';
                                            }
                                        }
                                    }, {
                                        text: "Save",
                                        style: "primary",
                                        icon: "save",
                                        click: function (model) {
                                            if ($page.controller.model.get('StatementType')=='3'||$page.controller.model.get('StatementType')=='2') {
                                                $page.controller.doPTFSaveAndUpdate();
                                            }else{
                                                $page.controller.doSaveAndUpdate();
                                            }

                                        }
                                    }
                                ]
                            }
                        },
                        pos: {width: 12, section: "statementInput"}
                    }
                }
            },
            createSOALayout : function(){
                return $.extend(true, {}, this.baseLayout(), this.arrayTabTemplate(), this.buttons());
            }
        });

        $page.layout = new saoLayout();
    }(typeof window !== "undefined" ? window : this)
);
