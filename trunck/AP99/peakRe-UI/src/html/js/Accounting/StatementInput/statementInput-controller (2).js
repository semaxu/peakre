/**
 * Create By Elvira.Du 10/15/2015
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    var initial = {
        initializeErrorModel: function () {
            return true;
        },
        initializeData: function () {
            var urlData = $pt.getUrlData();
            var contractId = urlData.contractId;
            var soaId = urlData.soaId;
            this.model = $pt.createModel($page.model.all,$page.validator);
            $page.controller.model.set("AccountLevel",1);
            this.model.AccountLevel = '1';// the type is "ourShare"
            this.model.set("ReceivedDate", moment().format('YYYY-MM-DDTHH:mm:ss'));
            var _this = this;
            var soaModelType = urlData.soaModelType;
            var newItem;
            if (soaModelType == 'continue') {
                var loadCondition = {SoaIdRead: soaId};
                var toLoad = function (data, textStatus, jqXHR){
                    _this.model.mergeCurrentModel(data);
                    //newItem = {CurrencyType: "+"}
                    //$page.controller.model.add("Currencies", newItem);
                    if (_this.form) {
                        _this.form.forceUpdate();
                    }
                }.bind(this);
                this.load(loadCondition, false, false, toLoad);
            } else {
                var createCondition = {SoaIdRead: soaId};
                var toCreate = function (data, textStatus, jqXHR){
                    _this.model.mergeCurrentModel(data);
                    //var array =_this.model.get('Currencies');
                    //var isCurrencyTypeAdd = 0;
                    //if (array != undefined && array != null) {
                    //    array.forEach(function (array) {
                    //        if (array.CurrencyType == "+") {
                    //            isCurrencyTypeAdd = 1;
                    //        }
                    //    });
                    //}
                    //if (isCurrencyTypeAdd == '0') {
                    //    newItem = {CurrencyType: "+"};
                    //    $page.controller.model.add("Currencies", newItem);
                    //}
                    if (_this.form) {
                        _this.form.forceUpdate();
                    }
                }.bind(this);
                this.load(createCondition, false, false, toCreate);
            }

            this.model.addPostChangeListener("ReceivedDate", function (evt) {
                var ReceivedDate = evt.model.get("ReceivedDate");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");
                var dueDate = new Date(ReceivedDate);
                var settlementDays = $page.controller.model.getCurrentModel().SettlementDays;
                dueDate.setDate(dueDate.getDate() +parseInt(settlementDays));
                evt.model.set("DueDate", moment(dueDate).format('YYYY-MM-DDTHH:mm:ss'));
                if ("" != ReceivedDate && null != ReceivedDate && undefined != ReceivedDate) {
                    ReceivedDate = moment(ReceivedDate).format("YYYY-MM-DD HH:mm");
                }

                    if (ReceivedDate > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: [' Date Received must be earlier or equal to today.']
                        });
                        _this.model.set("ReceivedDate", "");
                    }

            });


            this.model.addPostChangeListener("FinancialYear", function (evt) {
                var FinancialYear = evt.model.get("FinancialYear");
                evt.model.set("FinancialYear", moment(FinancialYear).format('YYYY'));
            });


        },
        renderContent: function () {
            this.layout = $pt.createFormLayout($page.layout.createSOALayout());
            var form = <NForm model={this.model} layout={this.layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        load: function (criteria, quiet, async, done, fail) {
            $page.service.load(criteria, quiet, async, done, fail);
        }
    };
    var restService = {
        doSaveAndUpdate: function () {
            var _this = this;
          //  console.log(this.form.getLayout().getComponent("Currencies"));
            var afterSaveAndUpdate = function (data, textStatus, jqXHR) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Successful']
                });
                $page.controller.model.mergeCurrentModel(data);
                //var array =  _this.model.get('Currencies');
                //var isCurrencyTypeAdd = 0;
                //if (array != undefined && array != null) {
                //    array.forEach(function (array) {
                //        if (array.CurrencyType == "+") {
                //            isCurrencyTypeAdd = 1;
                //        }
                //    });
                //}
                //if (isCurrencyTypeAdd == 0) {
                //    var newCurrencyItem = {CurrencyType: "+"}
                //    $page.controller.model.add("Currencies", newCurrencyItem);
                //}
               // $page.controller.form.forceUpdate();
                $pt.LayoutHelper.getComponent("CurrencyTabs").setState({tabs:null});
                if (_this.form) {
                    _this.form.forceUpdate();
                }

            }.bind(this);
            this.saveAndUpdateOnly(_this.model.getCurrentModel(), false, false, afterSaveAndUpdate);
        },
        doPTFSaveAndUpdate: function () {
            var a = $page.controller.model;
            var updateData = $.extend({}, this.model.getCurrentModel());
            var _this = this;
            var tosaveAndUpdate = function (data, textStatus, jqXHR) {
                //var loadCondition = {SoaIdRead: updateData.SoaId};
                var _this = this;
                _this.model.mergeCurrentModel(data);
                //var array =  _this.model.get('Currencies');
                //var isCurrencyTypeAdd = 0;
                //if (array != undefined && array != null) {
                //    array.forEach(function (array) {
                //        if (array.CurrencyType == "+") {
                //            isCurrencyTypeAdd = 1;
                //        }
                //    });
                //}
                //if (isCurrencyTypeAdd == 0) {
                //    var newCurrencyItem = {CurrencyType: "+"}
                //    $page.controller.model.add("Currencies", newCurrencyItem);
                //}
                $pt.LayoutHelper.getComponent("CurrencyTabs").setState({tabs:null});
                _this.form.forceUpdate();
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Successful']
                });
            }.bind(this);
            this.PTFSaveAndUpdateOnly(updateData, false, false, tosaveAndUpdate);
        },
        saveAndUpdateOnly: function (updateData, quiet, async, done, fail) {
            $page.service.saveAndUpdate(updateData, quiet, async, done, fail);
            //var toValidCendentYearAndQuarter = function (dataValid, textStatus, jqXHR){
            //    if(dataValid.InContractUWYear=='true'){
            //        if(dataValid.InCendentYearAndQuarter=='noEstimateInfo'){
            //            NConfirm.getConfirmModal().show({
            //                title: 'Message',
            //                disableClose: true,
            //                messages: ['There is no estimation generated under this cedant quarter. Please post estimation and then try again']
            //            });
            //        }else{
            //            if(dataValid.InCendentYearAndQuarter=='false'){
            //                var confirm2 = function () {
            //                    $page.service.saveAndUpdate(updateData, quiet, async, done, fail);
            //                };
            //                $pt.Components.NConfirm.getConfirmModal().show('Message', 'Previous cedent quarter(s) has not been inputted yet. Please confirm to continue.', confirm2.bind(this));
            //            }else{
            //                $page.service.saveAndUpdate(updateData, quiet, async, done, fail);
            //            }
            //        }
            //
            //    }else{
            //        NConfirm.getConfirmModal().show({
            //            title: 'Message',
            //            disableClose: true,
            //            messages: ['Contract status is invalided (Not in status of in-force or cancelled). System cannot create the statement.']
            //        });
            //    }
            //
            //}.bind(this);
            //this.validCendentYearAndQuarter(updateData, false, false, toValidCendentYearAndQuarter);
        },
        PTFSaveAndUpdateOnly: function (updateData, quiet, async, done, fail) {
            if(!this.ValidCurrentSoa()){
                 return
            }
            $page.service.saveAndUpdate(updateData, quiet, async, done, fail);
        },
        saveAndUpdate: function (updateData, quiet, async, done, fail) {
            var toValidCendentYearAndQuarter = function (dataValid, textStatus, jqXHR){
                 if(!this.AMLCheck()){

                }else if(dataValid.InContractUWYear=='true'&&dataValid.InCendentYearAndQuarter=='wrongQuarter'){
                     NConfirm.getConfirmModal().show({
                         title: 'Message',
                         disableClose: true,
                         messages: ['The  cedent quarter should not be earlier than the underwriting year of this contract.']
                     });
                 } else if(dataValid.InContractUWYear=='true'){
                    if(dataValid.InCendentYearAndQuarter=='noEstimateInfo'){
                        NConfirm.getConfirmModal().show({
                            title: 'Message',
                            disableClose: true,
                            messages: ['There is no estimation generated under this cedant quarter. Please post estimation and then try again.']
                        });
                    }else{
                        if(dataValid.InCendentYearAndQuarter=='false'){
                            var confirm2 = function () {
                                this.doValidRevered();
                            };
                            $pt.Components.NConfirm.getConfirmModal().show('Message', 'Previous cedent quarter(s) has not been inputted yet. Please confirm to continue.', confirm2.bind(this));
                        }
                        else{
                            this.doValidRevered();
                        }
                    }

                }else{
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Contract status is invalided (Not in status of in-force or cancelled). System cannot create the statement.']
                    });
                }

            }.bind(this);
            this.validCendentYearAndQuarter(updateData, false, false, toValidCendentYearAndQuarter);
        },
        PTFSaveAndUpdate: function (updateData, quiet, async, done, fail) {
            if(!this.AMLCheck()){
                return
            }
            if(!this.ValidCurrentSoa()){
                return
            }
            this.doPTFValidRevered();
        },
        saveAndUpdateSpecial: function (updateData, quiet, async, done, fail) {
            var toValidCendentYearAndQuarter = function (dataValid, textStatus, jqXHR){
                 if(!this.AMLCheck()){

                }else if(dataValid.InContractUWYear=='true'&&dataValid.InCendentYearAndQuarter=='wrongQuarter'){
                     NConfirm.getConfirmModal().show({
                         title: 'Message',
                         disableClose: true,
                         messages: ['The  cedent quarter should not be earlier than the underwriting year of this contract.']
                     });
                 } else if(dataValid.InContractUWYear=='true'){
                    if(dataValid.InCendentYearAndQuarter=='noEstimateInfo'){
                        NConfirm.getConfirmModal().show({
                            title: 'Message',
                            disableClose: true,
                            messages: ['There is no estimation generated under this cedant quarter. Please post estimation and then try again.']
                        });
                    }else{
                        if(dataValid.InCendentYearAndQuarter=='false'){
                            var confirm2 = function () {
                                this.doIgnoringValidRevered();
                            };
                            $pt.Components.NConfirm.getConfirmModal().show('Message', 'Previous cedent quarter(s) has not been inputted yet. Please confirm to continue.', confirm2.bind(this));
                        }
                        else{
                            this.doIgnoringValidRevered();
                        }
                    }

                }else{
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Contract status is invalided (Not in status of in-force or cancelled). System cannot create the statement.']
                    });
                }

            }.bind(this);
            this.validCendentYearAndQuarter(updateData, false, false, toValidCendentYearAndQuarter);
        },
        saveAndUpdateForSummary: function (updateData, quiet, async, done, fail) {
            var toValidCendentYearAndQuarter = function (dataValid, textStatus, jqXHR){
                if(dataValid.InContractUWYear=='true'){
                  if(dataValid.InCendentYearAndQuarter=='wrongQuarter'){
                        NConfirm.getConfirmModal().show({
                            title: 'Message',
                            disableClose: true,
                            messages: ['The  cedent quarter should not be earlier than the underwriting year of this contract.']
                        });
                    }else if(dataValid.InCendentYearAndQuarter=='noEstimateInfo'){
                      NConfirm.getConfirmModal().show({
                          title: 'Message',
                          disableClose: true,
                          messages: ['There is no estimation generated under this cedant quarter. Please post estimation and then try again.']
                      });
                    }else{
                        if(dataValid.InCendentYearAndQuarter=='false'){
                            var confirm2 = function () {

                                   $page.service.saveAndUpdate(updateData, quiet, async, done, fail);
                            };
                            $pt.Components.NConfirm.getConfirmModal().show('Message', 'Previous cedent quarter(s) has not been inputted yet. Please confirm to continue.', confirm2.bind(this));
                        }
                        else{

                              $page.service.saveAndUpdate(updateData, quiet, async, done, fail);
                        }
                    }

                }else{
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Contract status is invalided (Not in status of in-force or cancelled). System cannot create the statement.']
                    });
                }

            }.bind(this);
            this.validCendentYearAndQuarter(updateData, false, false, toValidCendentYearAndQuarter);
        },
        PTFSaveAndUpdateForSummary: function (updateData, quiet, async, done, fail) {
            $page.service.saveAndUpdate(updateData, quiet, async, done, fail);
        },
        validCendentYearAndQuarter: function (updateData, quiet, async, done, fail) {
            $page.service.validCendentYearAndQuarter(updateData, quiet, async, done, fail);
        },
        addContractSection: function (treeCodesData, model) {
            var sectionForm = NModalForm.createFormModal("Choose Sections");
            var sectionModel = $pt.createModel({nodes: null});
            //var codes = $pt.createCodeTable(treeCodesData);
            $page.sectionTree = [];
            var contractName = "ContractName";
            treeCodesData.forEach(function (treeCodesData) {
                contractName = treeCodesData.text;
                var sectionTree = treeCodesData.children;
                sectionTree.forEach(function (sectionTree) {
                    $page.sectionTree.push({id: sectionTree.id, text: sectionTree.text});
                });
            });

            var codes = $pt.createCodeTable($page.sectionTree);
            var sectionLayout = $pt.createFormLayout({
                nodes: {
                    comp: {
                        root: contractName,
                        type: $pt.ComponentConstants.Tree,
                        check: true,
                        hierarchyCheck: true,
                        valueAsArray: true,
                      //  root: false,
                        data: codes
                    },
                    pos: {width: 18}
                }
            });
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
                        text: 'Confirm',
                        style: 'primary',
                        click: function (dialogModel) {
                            var array = dialogModel.get("nodes");
                            if (array == null || array.length == 0) {
                                alert('Please choose a section at least.');
                            } else {
                                var treeArray = [];
                                for (var i = 0; i < array.length; i++) {
                                    treeArray.push(array[i]);
                                }
                                var params = {
                                    treeArray:treeArray,
                                    ContractCode:"123"
                                };
                                $page.service.saveContractser(params, false, false,
                                    function (data, textStatus, jqXHR) {
                                        $helper.lowerKeysOfJSON(data);
                                        data.forEach(function (data) {
                                            model.add('Sections', {
                                                ContractSection: data.text,
                                                ContracCompId:data.id,
                                                Cob:data.cob,
                                                UwArea:data.uwArea,
                                               // CoBList: data.coBList,
                                              //  UwAreaList : data.uwAreaList,
                                                ShareType: data.shareType,
                                                IsFullyTransfer: true&&$page.controller.model.get('StatementType')=='3'
                                            });
                                        });
                                    },
                                    function (jqXHR, textStatus, errorThrown) {
                                        alert("fail");
                                    });
                            }
                            sectionForm.hide();
                        }
                    }]
                },
                draggable: false,
                pos: {width: 18}
            });
        },

        onAddContractSectionSearch: function (model) {
            var _this = this;
            var params = {
                ContractCode: $page.controller.model.get("ContractCode"),
                UnderWritingYear: $page.controller.model.get("UwYear")
            };
            $page.service.findcontractser(params, false, false,
                function (data, textStatus, jqXHR) {
                    if(data==null||data==undefined||data==''){
                        NConfirm.getConfirmModal().show({
                            title: 'Message',
                            disableClose: true,
                            messages: ['Contract status is invalided (Not in status of in-force or cancelled).System cannot create the statement.']
                        });
                    }else{
                        $helper.lowerKeysOfJSON(data);
                        _this.addContractSection(data, model);
                    }
                },
                function (jqXHR, textStatus, errorThrown) {
                    alert("fail");
                });
        },

        doSummary: function (item) {
            var updateData = $.extend({}, this.model.getCurrentModel());
            var _this = this;
            var tosaveAndUpdate = function (data, textStatus, jqXHR) {
                var soaId =_this.model.getCurrentModel().SoaId;
                var statementType =_this.model.getCurrentModel().StatementType;
                window.location.href = "statementSummary.html?soaId="+soaId+"&&statementType="+ statementType;
                if (_this.form) {
                    _this.form.forceUpdate();
                }
            }.bind(this);
            this.saveAndUpdateForSummary(updateData, false, false, tosaveAndUpdate);
        },
        doPTFSummary: function (item) {
            var updateData = $.extend({}, this.model.getCurrentModel());
            var _this = this;
            var tosaveAndUpdate = function (data, textStatus, jqXHR) {
                var soaId =_this.model.getCurrentModel().SoaId;
                var statementType =_this.model.getCurrentModel().StatementType;

                window.location.href = "statementSummary.html?soaId="+soaId+"&&statementType="+ statementType;
                if (_this.form) {
                    _this.form.forceUpdate();
                }
            }.bind(this);
            this.PTFSaveAndUpdateForSummary(updateData, false, false, tosaveAndUpdate);
        },
        doSubmit: function () {
            var updateData = $.extend({}, this.model.getCurrentModel());
            var _this = this;

            var toSubmit = function (data, textStatus, jqXHR) {
                if (this.form) {
                    this.form.forceUpdate();
                }
                _this.initializeData();
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Successful.'],
                    afterClose: function (data) {
                        window.location.href = "statementQuery.html";
                    }
                });
            }.bind(this);
            this.submit(updateData, false, false, toSubmit);
        },
        doPTFSubmit: function () {
            var updateData = $.extend({}, this.model.getCurrentModel());
            var _this = this;

            var toSubmit = function (data, textStatus, jqXHR) {
                if (this.form) {
                    this.form.forceUpdate();
                }
                _this.initializeData();
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Successful.'],
                    afterClose: function (data) {
                        window.location.href = "statementQuery.html";
                    }
                });
            }.bind(this);
            this.PTFSubmit(updateData, false, false, toSubmit);
        },
        submit: function (updateData, quiet, async, done, fail) {
            $page.service.submit(updateData, quiet, async, done, fail);
        },
        PTFSubmit: function (updateData, quiet, async, done, fail) {
            $page.service.PTFSubmit(updateData, quiet, async, done, fail);
        },
        doIgnoringSubmit: function (updateData, quiet, async, done, fail) {
             updateData = $.extend({}, this.model.getCurrentModel());
            var _this = this;

            var toSubmit = function (data, textStatus, jqXHR) {
                if (this.form) {
                    this.form.forceUpdate();
                }
                _this.initializeData();
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Successful.'],
                    afterClose: function (data) {
                        window.location.href = "statementQuery.html";
                    }
                });
            }.bind(this);
            this.ignoringSubmit(updateData, false, false, toSubmit);
        },
        ignoringSubmit: function (updateData, quiet, async, done, fail) {
            $page.service.ignoringSubmit(updateData, quiet, async, done, fail);
        },
        doSaveAndReverse: function () {
            var updateData = $.extend({}, this.model.getCurrentModel());
            var _this = this;
            var toSaveAndReverse = function (data, textStatus, jqXHR) {
                if (this.form) {
                    this.form.forceUpdate();
                }
                _this.initializeData();
                window.location.href = "statementQuery.html";
            }.bind(this);
            this.saveAndReverse(updateData, false, false, toSaveAndReverse);
        },
        doIgnoringSaveAndReverse: function () {
            var updateData = $.extend({}, this.model.getCurrentModel());
            var _this = this;
            var toSaveAndReverse = function (data, textStatus, jqXHR) {
                if (this.form) {
                    this.form.forceUpdate();
                }
                _this.initializeData();
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Successful.'],
                    afterClose: function (data) {
                        window.location.href = "statementQuery.html";
                    }
                });
            }.bind(this);
            this.ignoringSaveAndReverse(updateData, false, false, toSaveAndReverse);
        },
        saveAndReverse: function (updateData, quiet, async, done, fail) {
            $page.service.saveAndReverse(updateData, quiet, async, done, fail);
        },
        ignoringSaveAndReverse: function (updateData, quiet, async, done, fail) {
            $page.service.ignoringSaveAndReverse(updateData, quiet, async, done, fail);
        },
        onRemoveSectionClicked: function (item) {
            var removeRow = function (item) {
                //if (true) {
                var _this = this;
                item.parent().remove('Sections', item.getCurrentModel());
                //}
                $pt.Components.NConfirm.getConfirmModal().hide();
            };
            $pt.Components.NConfirm.getConfirmModal().show(NTable.REMOVE_CONFIRM_TITLE, NTable.REMOVE_CONFIRM_MESSAGE, removeRow.bind(this, item));
        },

        onRemoveCurrencyClicked: function (itemModel) {
            var removeRow = function (itemModel) {
                //if (true) {
                itemModel.parent().remove('Currencies', itemModel.getCurrentModel());
                //}
                $pt.Components.NConfirm.getConfirmModal().hide();
            };
            $pt.Components.NConfirm.getConfirmModal().show(NTable.REMOVE_CONFIRM_TITLE, NTable.REMOVE_CONFIRM_MESSAGE, removeRow.bind(this, itemModel));
        },
        doBeforeRevered: function () {
            var updateData = $.extend({}, this.model.getCurrentModel());
            var _this = this;
            var tosaveAndUpdate = function (data, textStatus, jqXHR) {
                if (_this.form) {
                    _this.form.forceUpdate();
                    _this.doValidRevered();
                }

            }.bind(this);


            if (updateData==undefined||updateData.Cedent==undefined||updateData.Cedent == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent']
                });
                return;
            }
            if (updateData==undefined||updateData.UwYear==undefined||updateData.UwYear == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Uw Year ']
                });
                return;
            }
             if (updateData==undefined||updateData.DueDate==undefined||updateData.DueDate == null) {
                    NConfirm.getConfirmModal().show("Confirm Dialog", {
                        disableClose: true,
                        messages: ['Please input the Due Date ']
             });
                 return;
             }
               if (updateData==undefined||updateData.ReceivedDate==undefined||updateData.ReceivedDate == null) {
                   NConfirm.getConfirmModal().show("Confirm Dialog", {
                       disableClose: true,
                       messages: ['Please input the Date Received ']
                   });
                   return;
               }

            if (updateData==undefined||updateData.CedentYear==undefined||updateData.CedentYear == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Year ']
                });
                return;
            }
            if (updateData==undefined||updateData.CedentQuarter==undefined||updateData.CedentQuarter == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Quarter ']
                });
                return;
            }

            if (updateData==undefined||updateData.SoaText==undefined||updateData.SoaText == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Statement Text ']
                });
                return;
            }
            if (updateData==undefined||updateData.CedentPeriod==undefined||updateData.CedentPeriod == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Period ']
                });
                return;
            }


            this.saveAndUpdate(updateData, false, false, tosaveAndUpdate);
        },
        doPTFBeforeRevered: function () {
            var updateData = $.extend({}, this.model.getCurrentModel());
            var _this = this;
            var tosaveAndUpdate = function (data, textStatus, jqXHR) {
                if (_this.form) {
                    _this.form.forceUpdate();
                    _this.doPTFValidRevered();
                }

            }.bind(this);

            if (updateData==undefined||updateData.Cedent==undefined||updateData.Cedent == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent']
                });
                return;
            }
            if (updateData==undefined||updateData.UwYear==undefined||updateData.UwYear == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Uw Year ']
                });
                return;
            }
            if (updateData==undefined||updateData.DueDate==undefined||updateData.DueDate == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Due Date ']
                });
                return;
            }
            if (updateData==undefined||updateData.ReceivedDate==undefined||updateData.ReceivedDate == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Date Received ']
                });
                return;
            }
            if (updateData==undefined||updateData.CedentYear==undefined||updateData.CedentYear == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Year ']
                });
                return;
            }
            if (updateData==undefined||updateData.CedentQuarter==undefined||updateData.CedentQuarter == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Quarter ']
                });
                return;
            }
            if (updateData==undefined||updateData.SoaText==undefined||updateData.SoaText == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Statement Text ']
                });
                return;
            }
            if (updateData==undefined||updateData.CedentPeriod==undefined||updateData.CedentPeriod == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Period ']
                });
                return;
            }
            this.PTFSaveAndUpdate(updateData, false, false, tosaveAndUpdate);
        },

        doIgnoringBeforeRevered: function () {
            var updateData = $.extend({}, this.model.getCurrentModel());
            var _this = this;
            var tosaveAndUpdate = function (data, textStatus, jqXHR) {
                if (_this.form) {
                    _this.form.forceUpdate();
                    _this.doIgnoringValidRevered();
                }
                _this.initializeData();
            }.bind(this);

            if (updateData==undefined||updateData.Cedent==undefined||updateData.Cedent == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent']
                });
                return;
            }
            if (updateData==undefined||updateData.UwYear==undefined||updateData.UwYear == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Uw Year ']
                });
                return;
            }
            if (updateData==undefined||updateData.DueDate==undefined||updateData.DueDate == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Due Date ']
                });
                return;
            }

            if (updateData==undefined||updateData.ReceivedDate==undefined||updateData.ReceivedDate == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Date Received ']
                });
                return;
            }
            if (updateData==undefined||updateData.CedentYear==undefined||updateData.CedentYear == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Year ']
                });
                return;
            }
            if (updateData==undefined||updateData.CedentQuarter==undefined||updateData.CedentQuarter == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Cedent Quarter ']
                });
                return;
            }
            if (updateData==undefined||updateData.SoaText==undefined||updateData.SoaText == null) {
                NConfirm.getConfirmModal().show("Confirm Dialog", {
                    disableClose: true,
                    messages: ['Please input the Soa Text ']
                });
                return;
            }

            this.saveAndUpdateSpecial(updateData, false, false, tosaveAndUpdate);
        },
        doChooseSections: function (data) {
            var _this = this;
            var sectionForm = NModalForm.createFormModal("Reminding message");
            var sectionModel = $pt.createModel({message:null,nodes: true});
            if(data.Result=='true'){
                sectionModel.set("ReversalFlag",false);
            }else{
                sectionModel.set("ReversalFlag",true);
            }
            var sectionLayout = $pt.createFormLayout({
                message: {
                    label:  " Are you sure to submit the statement ? ",
                    comp:{
                        type: $pt.ComponentConstants.Label,
                        textFromModel: false
                    },
                    pos: {
                        width: 12
                    },
                    data:null
                },
                ReversalFlag: {
                    label: " Reverse estimation of this quarter ? ",
                    comp: {

                        type: $pt.ComponentConstants.Check,
                        check: false,
                        hierarchyCheck: true,
                        valueAsArray: true,
                        data: false,
                        enabled:{
                            depends:"ReversalFlag",
                            when:function(){
                                if(data.Result=='false'){
                                    return true;
                                }else{
                                    return false;
                                }
                            }
                        }
                    },
                    pos: {
                        width: 12
                    }
                }
            });
            sectionForm.show({
                model: sectionModel,
                layout: sectionLayout,
                buttons: {
                    reset: false,
                    validate: false,
                    cancel: false,
                    right: [{
                        icon: 'eject',
                        text: 'No',
                        style: 'warning',
                        click: function () {
                            sectionForm.hide();
                        }
                    }, {
                        icon: 'save',
                        text: 'Yes',
                        style: 'primary',
                        click: function (dialogModel) {
                            var array = dialogModel.get("ReversalFlag");
                            if (array == true) {
                                $page.controller.doSaveAndReverse();

                            }else{
                                $page.controller.doSubmit();
                            }
                            sectionForm.hide();
                        }
                    }]
                },
                draggable: false
            });
            $pt.Components.NConfirm.getConfirmModal().hide();
        },
        doSpecialChooseSections: function (data) {
            var _this = this;
            var sectionForm = NModalForm.createFormModal("Reminding message");
            var sectionModel = $pt.createModel({message:null,nodes: true});
            if(data.Result=='true'){
                sectionModel.set("ReversalFlag",false);
            }else{
                sectionModel.set("ReversalFlag",true);
            }
            var sectionLayout = $pt.createFormLayout({
                message: {
                    label:  " Are you sure to submit the statement ? ",
                    comp:{
                        type: $pt.ComponentConstants.Label,
                        textFromModel: false
                    },
                    pos: {
                        width: 12
                    },
                    data:null
                },
                ReversalFlag: {
                    label: " Reverse estimation of this quarter ? ",
                    comp: {

                        type: $pt.ComponentConstants.Check,
                        check: false,
                        hierarchyCheck: true,
                        valueAsArray: true,
                        data: false,
                        enabled:{
                            depends:"ReversalFlag",
                            when:function(){
                                if(data.Result=='false'){
                                    return true;
                                }else{
                                    return false;
                                }
                            }
                        }
                    },
                    pos: {
                        width: 12
                    }
                }
            });
            sectionForm.show({
                model: sectionModel,
                layout: sectionLayout,
                buttons: {
                    reset: false,
                    validate: false,
                    cancel: false,
                    right: [{
                        icon: 'eject',
                        text: 'No',
                        style: 'warning',
                        click: function () {
                            sectionForm.hide();
                        }
                    }, {
                        icon: 'save',
                        text: 'Yes',
                        style: 'primary',
                        click: function (dialogModel) {
                            var array = dialogModel.get("ReversalFlag");
                            if (array == true) {
                                $page.controller.doIgnoringSaveAndReverse();

                            }else{
                                $page.controller.doIgnoringSubmit();
                            }
                            sectionForm.hide();
                        }
                    }]
                },
                draggable: false
            });
            $pt.Components.NConfirm.getConfirmModal().hide();
        },
        doPTFChooseSections: function () {
            var _this = this;
            var sectionForm = NModalForm.createFormModal("Reminding message");
            var sectionModel = $pt.createModel({message:null,nodes: true});
            var sectionLayout = $pt.createFormLayout({
                message: {
                    label:  " Are you sure to submit the statement ? ",
                    comp:{
                        type: $pt.ComponentConstants.Label,
                        textFromModel: false
                    },
                    pos: {
                        width: 12
                    },
                    data:null
                }
            });
            sectionForm.show({
                model: sectionModel,
                layout: sectionLayout,
                buttons: {
                    reset: false,
                    validate: false,
                    cancel: false,
                    right: [{
                        icon: 'eject',
                        text: 'No',
                        style: 'warning',
                        click: function () {
                            sectionForm.hide();
                        }
                    }, {
                        icon: 'save',
                        text: 'Yes',
                        style: 'primary',
                        click: function (dialogModel) {
                            $page.controller.doPTFSubmit();
                            sectionForm.hide();
                        }
                    }]
                },
                draggable: false
            });
            $pt.Components.NConfirm.getConfirmModal().hide();
        },
        doValidRevered: function (model) {
            var updateData = $.extend({}, this.model.getCurrentModel());
            var toBeforeRevered = function (data, textStatus, jqXHR) {
                if(data.IsFullyTransfer=='true'){
                    var toContinue = function () {
                        var _this = this;
                        var sectionForm = NModalForm.createFormModal("Reminding message");
                        var sectionModel = $pt.createModel({message:null,nodes: true});
                        if(data.Result=='true'){
                            sectionModel.set("ReversalFlag",false);
                        }else{
                            sectionModel.set("ReversalFlag",true);
                        }
                        var sectionLayout = $pt.createFormLayout({
                            message: {
                                label:  " Are you sure to submit the statement ? ",
                                comp:{
                                    type: $pt.ComponentConstants.Label,
                                    textFromModel: false
                                },
                                pos: {
                                    width: 12
                                },
                                data:null
                            },
                            ReversalFlag: {
                                label: " Reverse estimation of this quarter ? ",
                                comp: {

                                    type: $pt.ComponentConstants.Check,
                                    check: false,
                                    hierarchyCheck: true,
                                    valueAsArray: true,
                                    data: false,
                                    enabled:{
                                        depends:"ReversalFlag",
                                        when:function(){
                                            if(data.Result=='false'){
                                                return true;
                                            }else{
                                                return false;
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    width: 12
                                }
                            }
                        });
                        sectionForm.show({
                            model: sectionModel,
                            layout: sectionLayout,
                            buttons: {
                                reset: false,
                                validate: false,
                                cancel: false,
                                right: [{
                                    icon: 'eject',
                                    text: 'No',
                                    style: 'warning',
                                    click: function () {
                                        sectionForm.hide();
                                    }
                                }, {
                                    icon: 'save',
                                    text: 'Yes',
                                    style: 'primary',
                                    click: function (dialogModel) {
                                        var array = dialogModel.get("ReversalFlag");
                                        if (array == true) {
                                            $page.controller.doSaveAndReverse();

                                        }else{
                                            $page.controller.doSubmit();
                                        }
                                        sectionForm.hide();
                                    }
                                }]
                            },
                            draggable: false
                        });
                        $pt.Components.NConfirm.getConfirmModal().hide();
                    };

                    if(data.IsOverCutOffDate=='overDate'||data.IsOverCutOffDate=='overClosedDate'){
                        var confirmed2 = false;
                        NConfirm.getConfirmModal().show({title : 'Message',messages: 'Please be noticed, below sections were Fully Ptf tansferred',
                            onConfirm: function() {
                                confirmed2 = true;
                            },
                            afterClose: function (result) {
                                if (confirmed2)
                                    $pt.Components.NConfirm.getConfirmModal().show('Current Quarter Info', 'The current financial quarter is closing, transaction will not be posted until opening of next financial quarter, are you sure to submit?', toContinue.bind(this, null));
                            }
                        });
                    }else {
                        $pt.Components.NConfirm.getConfirmModal().show('Message', 'Please be noticed, below sections were Fully Ptf tansferred', toContinue.bind(this, null));
                    }
                }else{

                    if(data.IsOverCutOffDate=='overDate'||data.IsOverCutOffDate=='overClosedDate'){
                        var confirmed3 = false;
                        NConfirm.getConfirmModal().show({title : 'Message',messages: 'The current financial quarter is closing, transaction will not be posted until opening of next financial quarter, are you sure to submit?',
                            onConfirm: function() {
                                confirmed3 = true;
                            },
                            afterClose: function (result) {
                                if (confirmed3){
                                    $page.controller.doChooseSections(data);
                                }
                            }
                        });
                    }else{
                        this.doChooseSections(data);
                    }

                }
            }.bind(this);
            this.toBeforeReversal(updateData, false, false, toBeforeRevered);
        },
        doPTFValidRevered: function (model) {
            this.doPTFChooseSections();
        },
        doIgnoringValidRevered: function (model) {
            var updateData = $.extend({}, this.model.getCurrentModel());
            var toBeforeRevered = function (data, textStatus, jqXHR) {
                var toSpecialContinue = function (){
                    var _this = this;
                    var sectionForm = NModalForm.createFormModal("Reminding message");
                    var sectionModel = $pt.createModel({message:null,nodes: true});
                    if(data.Result=='true'){
                        sectionModel.set("ReversalFlag",false);
                    }else{
                        sectionModel.set("ReversalFlag",true);
                    }
                    var sectionLayout = $pt.createFormLayout({
                        message: {
                            label:  " Are you sure to submit the statement ? ",
                            comp:{
                                type: $pt.ComponentConstants.Label,
                                textFromModel: false
                            },
                            pos: {
                                width: 12
                            },
                            data:null
                        },
                        ReversalFlag: {
                            label: " Reverse estimation of this quarter ? ",
                            comp: {

                                type: $pt.ComponentConstants.Check,
                                check: true,
                                hierarchyCheck: true,
                                valueAsArray: true,
                                data: null,
                                enabled:{
                                    depends:"ReversalFlag",
                                    when:function(){
                                        if(data.Result=='false'){
                                            return true;
                                        }else{
                                            return false;
                                        }

                                    }
                                }
                            },
                            pos: {
                                width: 12
                            }
                        }
                    });
                    sectionForm.show({
                        model: sectionModel,
                        layout: sectionLayout,
                        buttons: {
                            reset: false,
                            validate: false,
                            cancel: false,
                            right: [{
                                icon: 'eject',
                                text: 'No',
                                style: 'warning',
                                click: function () {
                                    sectionForm.hide();
                                }
                            }, {
                                icon: 'save',
                                text: 'Yes',
                                style: 'primary',
                                click: function (dialogModel) {
                                    var array = dialogModel.get("ReversalFlag");
                                    if (array == true) {
                                        $page.controller.doIgnoringSaveAndReverse();

                                    }else{
                                        $page.controller.doIgnoringSubmit();
                                    }
                                    sectionForm.hide();

                                }
                            }]
                        },
                        draggable: false
                    });
                }
                if(data.IsFullyTransfer=='true'){
                    $pt.Components.NConfirm.getConfirmModal().show('Current Quarter Info', 'Please be noticed, below sections were Fully Ptf tansferred', toSpecialContinue.bind(this, null));
                }else{
                    this.doSpecialChooseSections(data);
                }
            }.bind(this);
            this.toBeforeReversal(updateData, false, false, toBeforeRevered);

        },
        toBeforeReversal: function (updateData, quiet, async, done, fail) {
            $page.service.beforeReversal(updateData, quiet, async, done, fail);
        },
        doQueryEntryItem:function(entryCode) {
            var sign = 'ignore';
            $page.service.queryEntryItem(entryCode, false, false,
                function(data) {
                    $helper.lowerKeysOfJSON(data);
                    sign = data.result;
                },
                function(jqXHR, textStatus, errorThrown) {

                }
            );
            return sign;
        },
        exit: function () {
            var _this = this;
            var soaId =_this.model.getCurrentModel().SoaId;
            var resource = {
                ResourceId: _this.model.getCurrentModel().SoaId,
                ResourceType: 3,
                ResourceNo:  _this.model.getCurrentModel().SoaId,
                OwnerId:  _this.model.getCurrentModel().TaskCreator
            };
            var done = function () {
                window.location.href = "statementQuery.html";
            }.bind(this);
            this.unlock(resource, done);
        },
        AMLCheck: function (model) {
            var flag = true;
            var AfterGetAmlCheckResult = function (data, textStatus, jqXHR) {
                var amlMessageList = data ? data : [];
                if(amlMessageList.length > 0){
                    flag = false;
                    var messages = [];
                    amlMessageList.forEach(function (amlMessage) {
                        messages.push(amlMessage.Msg);
                    });

                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: messages
                    });
                }

            }.bind(this);
            $page.service.AMLCheckService(this.model.getCurrentModel(), false, false, AfterGetAmlCheckResult);

            return flag;
        },
        ValidCurrentSoa: function(model){
            var flag = true;
            var toValidateSoa = function (data, textStatus, jqXHR) {
                if(data.Result=='wrongContract'){
                    flag = false;
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Contract status is invalided (Not in status of in-force or cancelled).System cannot create the statement.']
                    });
                }
            }.bind(this);
            this.validatePTFSoa($page.controller.model, false, false, toValidateSoa);
            return flag;
        },
        validatePTFSoa: function (validateCondition, quiet, async, done, fail) {
            $page.service.validateCurrentPTFSoa(validateCondition, quiet, async, done, fail);
        }
    };
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, initial,restService));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));
