(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.layout = $pt.getService($page, 'layout');
    $page.layout.client = $pt.getService($page.layout, 'client');

    var Layout = jsface.Class({
        createExchangeRateInfo: function () {
            return {
                label: "Exchange Rate Management",
                style: 'primary-darken',
                layout: {
                    searchPanel: {
                        label: "Search Criteria",
                        pos: {
                            width: 12,
                            row: 1
                        },
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                condition_BaseCurrency: {
                                    label: "From Currency",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Currency,
                                        //allowClear : true,
                                        placeholder: "please select..."
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                condition_ExchangeCurrency: {
                                    label: "To Currency",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Currency,
                                        //allowClear : true,
                                        placeholder: "please select..."
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                condition_RateType: {
                                    label: "Rate Type",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        minimumResultsForSearch: -1,
                                        data: $page.codes.RateType,
                                        //allowClear : true,
                                        placeholder: "please select..."
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                condition_ValidDateFrom: {
                                    label: "Valid Date From",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        valueFormat: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 2,
                                        width: 4
                                    }
                                },
                                condition_ValidDateTo: {
                                    label: "Valid Date To",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        valueFormat: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 2,
                                        width: 4
                                    }
                                },
                                condition_ExpireDateFrom: {
                                    label: "Expiry Date From",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        valueFormat: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 2,
                                        width: 4
                                    }
                                },
                                condition_ExpireDateTo: {
                                    label: "Expiry Date To",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        valueFormat: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 3,
                                        width: 4
                                    }
                                },
                                searchButtons: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            left:[{
                                                text: "Create",
                                                style: "primary",
                                                click: function () {
                                                    var sectionForm = NModalForm.createFormModal("Add Exchange Rate", '');
                                                    var sectionModel = $pt.createModel();
                                                    var sectionLayout = $pt.createFormLayout({
                                                        BaseCurrency: {
                                                            label: "From Currency",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Select,
                                                                data: $page.codes.Currency,
                                                                //allowClear : true,
                                                                placeholder: "please select..."
                                                            },
                                                            pos: {
                                                                row: 1,
                                                                width: 6
                                                            }
                                                        },
                                                        ExchangeCurrency: {
                                                            label: "To Currency",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Select,
                                                                data: $page.codes.Currency,
                                                                //allowClear : true,
                                                                //placeholder: "please select..."
                                                            	enabled: false
                                                            },
                                                            pos: {
                                                                row: 1,
                                                                width: 6
                                                            }
                                                        },
                                                        ExchangeRate: {
                                                            label: "Exchange Rate",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Text,
                                                                format: $helper.formatNumber(6)
                                                            },
                                                            pos: {
                                                                row: 2,
                                                                width: 6
                                                            }
                                                        },
                                                        RateType: {
                                                            label: "Rate Type",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Select,
                                                                minimumResultsForSearch: -1,
                                                                data: $page.codes.RateType,
                                                                //allowClear : true,
                                                                placeholder: "please select..."
                                                            },
                                                            pos: {
                                                                row: 2,
                                                                width: 6
                                                            }
                                                        },
                                                        ValidDate: {
                                                            label: "Valid Date",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Date,
                                                                format: "DD/MM/YYYY",
                                                                valueFormat: "DD/MM/YYYY"
                                                            },
                                                            pos: {
                                                                row: 3,
                                                                width: 6
                                                            }
                                                        },
                                                        ExpireDate: {
                                                            label: "Expiry Date",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Date,
                                                                format: "DD/MM/YYYY",
                                                                valueFormat: "DD/MM/YYYY"
                                                            },
                                                            pos: {
                                                                row: 3,
                                                                width: 6
                                                            }
                                                        },
                                                        Status:{
                                                            label:"Status",
                                                            comp:{
                                                                type:$pt.ComponentConstants.Select,
                                                                data:$page.codes.ExchangeRateStatus,
                                                                visible: false
                                                            },
                                                            pos: {
                                                                row: 4,
                                                                width: 6
                                                            }
                                                        }
                                                    });
                                                    //Default value
                                                    sectionModel.set("ExchangeCurrency", "USD");
                                                    sectionForm.show({
                                                        model: sectionModel,
                                                        layout: sectionLayout,
                                                        buttons: {
                                                            reset: false,
                                                            validate: false,
                                                            cancel: false,
                                                            right: [{
                                                                icon: 'eject',
                                                                text: 'Cancel',
                                                                style: 'warning',
                                                                click: function () {
                                                                    sectionForm.hide();
                                                                }
                                                            }, {
                                                                icon: 'save',
                                                                text: 'Save',
                                                                style: 'primary',
                                                                click: function (dialogModel) {
                                                                    if (!$page.controller.checkInputValue(dialogModel.getCurrentModel())){
                                                                        return false;
                                                                    }
                                                                    if ($page.controller.saveExchangeRate(dialogModel.getCurrentModel())){
                                                                        sectionForm.hide();
                                                                    }
                                                                }
                                                            }]
                                                        },
                                                        draggable: false
                                                    });
                                                    sectionModel.addPostChangeListener("ValidDate", function(evt){
                                                        var validDate = evt.model.get("ValidDate");
                                                        if (null != validDate && "" != validDate){
                                                            var validDateArray = validDate.split("/");
                                                            if (validDate.length == 10 && null != validDateArray && validDateArray.length == 3
                                                                && validDateArray[0].length == 2 && validDateArray[1].length == 2 && validDateArray[2].length == 4 ){
                                                                var newDate = validDateArray[2] + "/" + validDateArray[1] + "/01";
                                                                var lastMonth = new Date(newDate);
                                                                lastMonth.setMonth(lastMonth.getMonth() + 1);
                                                                var lastDay = new Date(lastMonth.getTime() - 1000*60*60*24);
                                                                evt.model.set("ExpireDate", moment(lastDay).format("DD/MM/YYYY"));
                                                            }
                                                            else{
                                                                evt.model.set("ValidDate", "");
                                                            }
                                                        }
                                                        else{
                                                            evt.model.set("ExpireDate", "");
                                                        }
                                                    });
                                                }
                                            }],
                                            right: [{
                                                text: "Reset",
                                                style: "warning",
                                                click: function () {
                                                    $page.controller.reset();
                                                }
                                            }, {
                                                text: "Search",
                                                style: "primary",
                                                click: function (model) {
                                                    var postData = {
                                                        BaseCurrency: model.get("condition_BaseCurrency"),
                                                        ExchangeCurrency: model.get("condition_ExchangeCurrency"),
                                                        ValidDateFrom:model.get("condition_ValidDateFrom"),
                                                        ValidDateTo:model.get("condition_ValidDateTo"),
                                                        ExpireDateFrom: model.get("condition_ExpireDateFrom"),
                                                        ExpireDateTo: model.get("condition_ExpireDateTo"),
                                                        RateType:model.get("condition_RateType")
                                                    };

                                                    $page.controller.searchExchangeRate(postData);
                                                }
                                            }]
                                        }
                                    },
                                    pos: {
                                        width: 12,
                                        row: 3
                                    }
                                }
                            }
                        }
                    },
                    searchTable: {
                        label: "Search Result",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            pageable: true,
                            criteria: 'cachedCriteria',
                            addable: false,
                            columns: [
                                {title: "From Currency", data: "BaseCurrencyCode"},
                                {title: "To Currency", data: "ExCurrencyCode"},
                                {title: "Exchange Rate", data: "Rate",
                                    render: function (row) {
                                        return $helper.formatNumberForLabel(row.Rate, 6);
                                    }
                                },
                                {title: "Rate Type", data: "RateType", codes: $page.codes.RateType},
                                {title: "Valid Date", data: "ValidDate",
                                    render: function (row) {
                                        return moment(row.ValidDate,"YYYY-MM-DD").format("DD/MM/YYYY");
                                    }
                                },
                                {title: "Expiry Date", data: "ExpiryDate",
                                    render: function (row) {
                                        return moment(row.ExpiryDate,"YYYY-MM-DD").format("DD/MM/YYYY");
                                    }
                                },
                                {title: "Status", data: "Status", codes: $page.codes.ExchangeRateStatus,
                                    visible: false
                                }
                            ],
                            rowOperations: [{
                                tooltip: "Edit",
                                click: function (rowModel) {
                                    var sectionForm2 = NModalForm.createFormModal("Edit Exchange Rate", '');
                                    var sectionModel2 = $pt.createModel();
                                    var sectionLayout2 = $pt.createFormLayout({
                                        BaseCurrency: {
                                            label: "From Currency",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Currency
                                            },
                                            pos: {
                                                row: 1,
                                                width: 6
                                            }
                                        },
                                        ExchangeCurrency: {
                                            label: "To Currency",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Currency,
                                                enabled: false
                                            },
                                            pos: {
                                                row: 1,
                                                width: 6
                                            }
                                        },
                                        ExchangeRate: {
                                            label: "Exchange Rate",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                format: $helper.formatNumber(6)
                                            },
                                            pos: {
                                                row: 2,
                                                width: 6
                                            }
                                        },
                                        RateType: {
                                            label: "Rate Type",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                minimumResultsForSearch: -1,
                                                data: $page.codes.RateType,
                                                //allowClear : true,
                                                placeholder: "please select..."
                                            },
                                            pos: {
                                                row: 2,
                                                width: 6
                                            }
                                        },
                                        ValidDate: {
                                            label: "Valid Date",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                format: "DD/MM/YYYY",
                                                valueFormat: "DD/MM/YYYY"
                                            },
                                            pos: {
                                                row: 3,
                                                width: 6
                                            }
                                        },
                                        ExpireDate: {
                                            label: "Expiry Date",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                format: "DD/MM/YYYY",
                                                valueFormat: "DD/MM/YYYY"
                                            },
                                            pos: {
                                                row: 3,
                                                width: 6
                                            }
                                        },
                                        RateId:{
                                            comp:{
                                                type: $pt.ComponentConstants.Text,
                                                visible: false
                                            }
                                        },
                                        IsImport:{
                                            comp:{
                                                type: $pt.ComponentConstants.Text,
                                                visible: false
                                            }
                                        },
                                        Status:{
                                            label:"Status",
                                            comp:{
                                                type:$pt.ComponentConstants.Select,
                                                data:$page.codes.ExchangeRateStatus,
                                                visible: false
                                            },
                                            pos: {
                                                row: 4,
                                                width: 6
                                            }
                                        }
                                    });
                                    sectionModel2.set("BaseCurrency", rowModel.BaseCurrencyCode);
                                    sectionModel2.set("ExchangeCurrency", rowModel.ExCurrencyCode);
                                    sectionModel2.set("ValidDate", moment(rowModel.ValidDate,"YYYY-MM-DD").format("DD/MM/YYYY"));
                                    sectionModel2.set("ExpireDate", moment(rowModel.ExpiryDate,"YYYY-MM-DD").format("DD/MM/YYYY"));
                                    //var editRate = $helper.formatNumberForLabel(rowModel.Rate, 6);
                                    sectionModel2.set("ExchangeRate", rowModel.Rate);
                                    sectionModel2.set("RateType", rowModel.RateType);
                                    sectionModel2.set("RateId", rowModel.RateId);
                                    sectionModel2.set("IsImport", rowModel.IsImport);
                                    sectionModel2.set("Status", rowModel.Status);
                                    sectionForm2.show({
                                        model: sectionModel2,
                                        layout: sectionLayout2,
                                        buttons: {
                                            reset: false,
                                            validate: false,
                                            cancel: false,
                                            right: [{
                                                icon: 'eject',
                                                text: 'Cancel',
                                                style: 'warning',
                                                click: function () {
                                                    sectionForm2.hide();
                                                }
                                            }, {
                                                icon: 'save',
                                                text: 'Save',
                                                style: 'primary',
                                                click: function (updateModel) {
                                                    if (!$page.controller.checkInputValue(updateModel.getCurrentModel())){
                                                        return false;
                                                    }
                                                    if ($page.controller.updateExchangeRate(updateModel.getCurrentModel())){
                                                        sectionForm2.hide();
                                                    }
                                                }
                                            }]
                                        },
                                        draggable: false
                                    });
                                    sectionModel2.addPostChangeListener("ValidDate", function(evt){
                                        var validDate = evt.model.get("ValidDate");
                                        if (null != validDate && "" != validDate){
                                            var validDateArray = validDate.split("/");
                                            if (validDate.length == 10 && null != validDateArray && validDateArray.length == 3
                                                && validDateArray[0].length == 2 && validDateArray[1].length == 2 && validDateArray[2].length == 4 ){
                                                var newDate = validDateArray[2] + "/" + validDateArray[1] + "/01";
                                                var lastMonth = new Date(newDate);
                                                lastMonth.setMonth(lastMonth.getMonth() + 1);
                                                var lastDay = new Date(lastMonth.getTime() - 1000*60*60*24);
                                                evt.model.set("ExpireDate", moment(lastDay).format("DD/MM/YYYY"));
                                            }
                                            else{
                                                evt.model.set("ValidDate", "");
                                            }
                                        }
                                        else{
                                            evt.model.set("ExpireDate", "");
                                        }
                                    });
                                }
                            },{
                                tooltip: "Delete",
                                click: function (rowModel) {
                                    $page.controller.deleteExchangeRate(rowModel);
                                }
                            }]
                        },
                        evt: {
                            pageChange: function (evt) {
                                $page.controller.doPageJump(evt.criteria, evt.target);
                            }
                        },
                        pos: {
                            width: 12,
                            row: 3

                        }
                    },
                    bottomButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Exit",
                                    style: "warning",
                                    click: function () {
                                        window.location.href = $pt.getURL('index');
                                    }
                                }, {
                                    text: "Import",
                                    style: "primary",
                                    click: function () {
                                        window.open("../Document/documentUpload.html?businessType=4&businessId=0");
        }
                                }]
                            }
                        },
                        pos: {
                            width: 12,
                            row: 4
                        }
                    }
                }
            }
        },
        createFormLayout: function () {
            return {
                _sections: {
                    exchangeRateInfoSection: this.createExchangeRateInfo()
                }
            }
        }
    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));