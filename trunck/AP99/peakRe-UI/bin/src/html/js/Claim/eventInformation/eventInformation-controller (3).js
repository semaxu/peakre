/**
 * Created by Gordon.Cao on 10/20/2015.
 */

(function (context) {
    var $page = $pt.getService(context, '$page');
    //$page.rcontractSelection = $page.codes.createMutableCodeTable();
    $page.pageType = 0;
    //$page.relatedClaim = $page.codes.createMutableCodeTable();

    var me = {
        initializeErrorModel: function () {
            //this.errorModel = $pt.createModel($page.model.error);
            //return true;
        },
        initializeData: function () {
            this.model = $pt.createModel($page.model.createModel(), $page.validator);
            var _this = this;
            this.model.addPostChangeListener("DateOfLossFrom", function (evt) {
                var DateOfLossFrom = evt.model.get("DateOfLossFrom");
                var DateOfLossTo = evt.model.get("DateOfLossTo");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");
                DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                if (null != DateOfLossTo && "" != DateOfLossTo && undefined != DateOfLossTo) {
                    DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");
                }
                if (null != DateOfLossFrom && "" != DateOfLossFrom && undefined != DateOfLossFrom) {
                    if (DateOfLossFrom > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: [' Loss Start Date must be earlier or equal to today.']
                        });
                        _this.model.set("DateOfLossFrom", "");
                    }
                    if (DateOfLossFrom > DateOfLossTo) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: [' Loss Start Date must be earlier or equal to Loss End Date.']
                        });
                        _this.model.set("DateOfLossFrom", "");
                    }
                }
                _this.model.set("DateOfLossTo", DateOfLossFrom);
            });
            this.model.addPostChangeListener("DateOfLossTo", function (evt) {
                var DateOfLossTo = evt.model.get("DateOfLossTo");
                var DateOfLossFrom = evt.model.get("DateOfLossFrom");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");
                DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");
                if (null != DateOfLossFrom && "" != DateOfLossFrom && undefined != DateOfLossFrom) {
                    DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                }
                if (null != DateOfLossTo && "" != DateOfLossTo && undefined != DateOfLossTo) {
                    if (DateOfLossTo < DateOfLossFrom) {
                        console.log('Loss End Date  must be later or equal to Loss Start Date.');

                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss End Date  must be later or equal to Loss Start Date.']
                        });
                        _this.model.set("DateOfLossTo", "");
                    }

                    if (DateOfLossTo > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss End Date must be earlier or equal to today.']
                        });
                        _this.model.set("DateOfLossTo", "");

                    }
                }
            });
            //this.model.addPostChangeListener("DateOfReceipt", function (evt) {
            //    var DateOfReceipt = evt.model.get("DateOfReceipt");
            //    var nowDate = moment(new Date()).format("YYYY-MM-DD");
            //    if ( "" !=DateOfReceipt && null !=  DateOfReceipt && undefined !=DateOfReceipt){
            //        DateOfReceipt = moment(DateOfReceipt).format("YYYY-MM-DD");
            //        console.log(DateOfReceipt,nowDate);
            //        if (DateOfReceipt > nowDate) {
            //            NConfirm.getConfirmModal().show({
            //                title: 'System Message',
            //                disableClose: true,
            //                messages: [' Date of Receipt must be earlier or equal to today.']
            //            });
            //            _this.model.set("SettlementListRetro.DateOfReceipt", nowDate);
            //        }
            //    }
            //
            //});
            var urlData = $pt.getUrlData();
            var eventId = urlData.eventId;
            console.log('eventId = ' + eventId);
            // var _this = this;
            var pageType = urlData.pageType;
            $page.pageType = pageType;

            var loadRContract = function (data, textStatus, jqXHR) {
                console.log(data);
                $helper.lowerKeysOfJSON(data);
                //$page.rcontractSelection.reset(data);
                $page.model.rcontractSelection = $page.codes.createMutableCodeTable();
                $page.model.rcontractSelection.reset(data);
            }.bind(this);
            $page.service.loadRContractSection(eventId, false, false, loadRContract);

            var loadEventFu = function (data, textStatus, jqXHR) {
                console.log(data);
                this.myload(data);
            }.bind(this);
            var params = {
                EventId: eventId
            };
            $page.service.loadEvent(params, false, false, loadEventFu);

            var afterLoadRelatedClaim = function (data, textStatus, jqXHR) {
                console.log(data);
                $helper.lowerKeysOfJSON(data);
                $page.model.relatedClaims = $page.codes.createMutableCodeTable();
                $page.model.relatedClaims.reset(data);
            }.bind(this);
            $page.service.loadRelatedClaim(params, false, false, afterLoadRelatedClaim);

            if (this.model.get("ReserveListRetro") != null && this.model.get("ReserveListRetro") != undefined) {
                $page.ReserveRetroItems = this.getReserveList(this.model.get("ReserveListRetro"));
            }
            var afterLoadClaimMessage = function (data, textStatus, jqXHR) {
                var messageList = data ? data : [];
                if (messageList.length != 0) {
                    var message = [];
                    data.forEach(function (messages) {
                        message.push(messages.MessageDescription);
                    });
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: message
                    });
                    $page.messageNum = messageList.length;
                } else {
                    $page.messageNum = 0;
                }
            }.bind(this);
            $page.service.loadClaimMessage(eventId, false, false, afterLoadClaimMessage);

            this.model.addPostChangeListener("ContractRecords", function (evt) {
                _this.model.set("ContractCode", _this.model.getCurrentModel().ContractRecords.ContractCode);
                _this.model.set("UwYear", _this.model.getCurrentModel().ContractRecords.UwYear);
                delete _this.model.getCurrentModel().ContractRecords;

            });
        },
        myload: function (data, updateForm) {
            console.log(data);
            this.model.mergeCurrentModel(data);
            if (updateForm) {
                this.form.forceUpdate();
            }


        },
        getReserveList: function (list) {
            var ReserveItems = [];
            if (list != undefined && list != null) {
                for (var i = 0; i < list.length; i++) {
                    ReserveItems.push(list[i].SectionId + list[i].ReserveType + list[i].OriginalCurrency);
                }
                return ReserveItems;
            }
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            if ($page.pageType != 0) {
                layout = $pt.createFormLayout($page.layout.createFormLayout());
                form = <NForm model={this.model} layout={layout} view={true}/>;
            }
            this.form = ReactDOM.render(form, document.getElementById('main'));

        },
        getPosting: function (sectionId) {
            var getPostingDone = function (data, textStatus, jqXHR) {
                $page.postingFlag = data;
            }.bind(this);
            $page.service.getPosting(sectionId, false, false, getPostingDone);
        },
        checkSettlement: function (model) {
            var flag = [];
            if (this.model.getCurrentModel().SettlementListRetro != null && this.model.getCurrentModel().SettlementListRetro != undefined) {
                flag.push(this.checkIfExitReserve(this.model.get("ReserveListRetro"), this.model.getCurrentModel().SettlementListRetro));
            }
            flag.push(true);
            var num = 0;
            for (var u = 0; u < flag.length; u++) {
                if (flag[u] == false) {
                    num = num + 1;
                }
            }
            if (num != 0) {
                return false;
            } else {
                return true;
            }
        },
        checkIfExitReserve: function (ReserveList, SettlementList) {
            var ReserveItem = [];
            if (ReserveList != undefined && ReserveList != null) {
                for (var i = 0; i < ReserveList.length; i++) {
                    ReserveItem.push(ReserveList[i].SectionId + ReserveList[i].ReserveType + ReserveList[i].OriginalCurrency);
                }
            }
            var SettlementItem = [];
            if (SettlementList != undefined && SettlementList != null) {
                for (var m = 0; m < SettlementList.length; m++) {
                    if (SettlementList[m].SettlementItemList != null && SettlementList[m].SettlementItemList != undefined) {
                        var settleItemList = SettlementList[m].SettlementItemList;
                        for (var j = 0; j < settleItemList.length; j++) {
                            SettlementItem.push(settleItemList[j].SectionId + settleItemList[j].SettlementType + settleItemList[j].OriginalCurrency);
                        }
                    }
                }
            }
            var Flag = [];
            var num = 0;
            for (var n = 0; n < SettlementItem.length; n++) {
                Flag[n] = false;
                for (var k = 0; k < ReserveItem.length; k++) {
                    if (SettlementItem[n] === ReserveItem[k]) {
                        Flag[n] = true;
                        break;
                    }
                }
            }
            for (var q = 0; q < Flag.length; q++) {
                if (Flag[q] == true) {
                    num = num + 1;
                }
            }
            if (num == SettlementItem.length) {
                return true;
            } else {
                return false;
            }
        },
        submitEventInfo: function (model) {
            this.model.validate();
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }

            if ( this.checkDate(model) == false) {
                return false;
            }
            if (this.checkTableMandatory() == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all reserveList or settlementItemList.']
                });
                return false;
            }
            if (this.checkSettlementItem() == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['There is no SettlementItem,please add one first.']
                });
                return false;
            }
            if (this.checkCode(model) == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['EventCode Can not be the same.']
                });
            } else {
                if (this.checkSettlement(model) == false) {
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: ['There is no related reserve, please add reserve first.']
                    });
                } else if (!this.AMLCheck()) {
                    //NConfirm.getConfirmModal().show({
                    //    title: 'System Message',
                    //    disableClose: true,
                    //    messages: ['The business partner is pending for AML check; the process is denied until the business partner passes the AML check!']
                    //});
                } else {
                    var afterSubmit = function (data, textStatus, jqXHR) {
                        var url = $pt.getURL('ui.claim.eventInfo');
                        var EventId = this.model.get("EventId");

                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Successful.'],
                            afterClose: function (data) {
                                //var afterLoadClaimMessage = function (data, textStatus, jqXHR) {
                                //    var messageList = data ? data : [];
                                //    if (messageList.length != 0) {
                                //        var message = "";
                                //        data.forEach(function (messages) {
                                //            message = message + messages.MessageDescription +'\r\n';
                                //        });
                                //        NConfirm.getConfirmModal().show({
                                //            title: 'System Message',
                                //            disableClose: true,
                                //            messages:message,
                                //            afterClose: function (data) {
                                //                var url = $pt.getURL('ui.claim.eventInfo');
                                //                console.log(url);
                                //                window.location.href = url + "?eventId=" + EventId + "&pageType=0";                                                }
                                //        });
                                //    }else{
                                window.location.href = url + "?eventId=" + EventId + "&pageType=0";
                                //    }
                                //}.bind(this);
                                //$page.service.loadEventAmountMessage(claimId, false, false, afterLoadClaimMessage);
                            }
                        });
                    }.bind(this);
                    $page.service.submitEventInfo(model, false, false, afterSubmit);
                }
            }
        },
        AMLCheck: function (model) {
            var RefId = this.model.get("EventId");
            var flag = true;

            var AfterGetAmlCheckResult = function (data, textStatus, jqXHR) {
                console.log("data===" + data);
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
        saveEventInfo: function (model) {
            this.model.validate();
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }
            if (this.checkTableMandatory() == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                //this.form.forceUpdate();
                return false;
            }
            console.debug(model);
            if (this.checkDate(model) == false) {
                return false;
            }
            if (this.checkSettlementItem() == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['There is no SettlementItem,please add one first.']
                });
                return false;
            }
            if (this.checkCode(model) == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['There is an existing event with this code, you cannot create a same one.']
                });
                return false;
            }
            if (this.checkSettlement(model) == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['There is no related reserve, please add reserve first.']
                });
            } else {
                var saveEventDone = function (data, textStatus, jqXHR) {
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: ['Successful.']
                    });
                    this.myload(data, true);
                }.bind(this);
                $page.service.saveEventInfo(model, false, false, saveEventDone);
            }

        },
        checkSettlementItem: function () {
            var settleFlag = [];
            if (this.model.getCurrentModel().SettlementListRetro != null && this.model.getCurrentModel().SettlementListRetro != undefined) {
                var SettlementListRetro = this.model.getCurrentModel().SettlementListRetro;
                if (SettlementListRetro != undefined && SettlementListRetro != null) {
                    for (var m = 0; m < SettlementListRetro.length; m++) {
                        var settlementItemList = SettlementListRetro[m].SettlementItemList;
                        if (settlementItemList != null && settlementItemList != undefined && settlementItemList.length > 0) {
                            settleFlag.push(true);
                        } else {
                            settleFlag.push(false);
                        }
                    }
                }
            }
            var num = 0;
            var checkSettleFlag =true;
            for (var a = 0; a < settleFlag.length; a++) {
                if (settleFlag[a] == false) {
                    num = num + 1;
                }
            }
            if (num > 0) {
                checkSettleFlag = false;
            }


            return checkSettleFlag;

        },
        checkTableMandatory: function () {
            var reserveList = this.model.getCurrentModel().ReserveListRetro;
            var settlementList = this.model.getCurrentModel().SettlementListRetro;
            var rNum = 0;
            var sNum = 0;
            if (null != reserveList && undefined != reserveList) {
                for (var a = 0; a < reserveList.length; a++) {
                    if (null == reserveList[a].SectionId || undefined == reserveList[a].SectionId) {
                        rNum = rNum + 1;
                    }
                    if (null == reserveList[a].ReserveType || undefined == reserveList[a].ReserveType) {
                        rNum = rNum + 1;
                    }
                    if (null == reserveList[a].OriginalCurrency || undefined == reserveList[a].OriginalCurrency) {
                        rNum = rNum + 1;
                    }
                    if( 0 != null == reserveList[a].AmountOc){
                        if (null == reserveList[a].AmountOc || undefined == reserveList[a].AmountOc || "" == reserveList[a].AmountOc) {
                            rNum = rNum + 1;
                        }
                    }


                }
            }
            if (null != settlementList && undefined != settlementList) {
                for (var b = 0; b < settlementList.length; b++) {
                    if (null != settlementList[b].SettlementItemList && undefined != settlementList[b].SettlementItemList) {
                        var settlementItemList = settlementList[b].SettlementItemList;
                        for (var c = 0; c < settlementItemList.length; c++) {
                            if (null == settlementItemList[c].SectionId || undefined == settlementItemList[c].SectionId) {
                                sNum = sNum + 1;
                            }
                            if (null == settlementItemList[c].SettlementType || undefined == settlementItemList[c].SettlementType) {
                                sNum = sNum + 1;
                            }
                            if (null == settlementItemList[c].OriginalCurrency || undefined == settlementItemList[c].OriginalCurrency) {
                                sNum = sNum + 1;
                            }
                            if (null == settlementItemList[c].AmountOc || undefined == settlementItemList[c].AmountOc || "" == settlementItemList[c].AmountOc) {
                                sNum = sNum + 1;
                            }
                        }
                    }
                }
            }
            var flag =true;
            if (rNum > 0 || sNum > 0) {
                flag = false;
            }
            return flag;
        },
        checkDate: function (model) {
            var _this = this;
            var receiptflag = true;
            if (null != _this.model.get("SettlementListRetro") && undefined != _this.model.get("SettlementListRetro")) {
                var settlementList = _this.model.get("SettlementListRetro");
                var nowDate = moment(new Date()).format("YYYY-MM-DD");
                for (var index = 0; index < settlementList.length; index++) {
                    var dateOfReceipt = settlementList[index].DateOfReceipt;
                    if (null != dateOfReceipt && undefined != dateOfReceipt && "" != dateOfReceipt) {
                        dateOfReceipt = moment(dateOfReceipt).format("YYYY-MM-DD");
                        if (dateOfReceipt > nowDate) {
                            receiptflag = false;
                            NConfirm.getConfirmModal().show({
                                title: 'System Message',
                                disableClose: true,
                                messages: [' Date of Receipt must be earlier or equal to today.']
                            });
                        }
                    } else {
                        receiptflag = false;
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Please fill in all mandatory information.']
                        });
                    }
                }
            }
            return receiptflag;
        },
        checkCode: function (model) {
            console.info(model);
            var EventCode = this.model.get("EventCode");
            var urlData = $pt.getUrlData();
            var eventId = urlData.eventId;
            //var type = $page.pageType;
            console.log(EventCode);
            console.log(eventId);
            var flag = true;
            var checkCodeDone = function (data, textStatus, jqXHR) {
                for (var x = 0; x < data.length; x++) {
                    if (EventCode == data[x].Text && eventId != data[x].Id) {
                        flag = false;
                    }
                }
            }.bind(this);
            $page.service.checkCode(model, false, false, checkCodeDone);
            return flag;
        },
        //checkReserveItem:function(AllReserveList,ReserveItems){
        //    var AllReserveItem = [];
        //    if(null != AllReserveList && undefined != AllReserveList){
        //        for (var x = 0; x < AllReserveList.length; x++) {
        //            AllReserveItem.push(AllReserveList[x].SectionId + AllReserveList[x].ReserveType + AllReserveList[x].OriginalCurrency);
        //        }
        //    }
        //    var Flag =[];
        //    for(var n =0;n<ReserveItems.length;n++){
        //        Flag.push(false);
        //    }
        //    for(var y =0; y<AllReserveItem.length;y++){
        //        for(var z =0;z<ReserveItems.length;z++){
        //            if(AllReserveItem[y] == ReserveItems[z]){
        //                Flag[z] = true;
        //                break;
        //            }
        //        }
        //    }
        //    for (var q = 0; q < Flag.length; q++) {
        //        if (Flag[q] == false) {
        //            ReserveItems.splice(q,1);
        //            Flag.splice(q,1);
        //        }
        //    }
        //},
        onRemoveSettlementClicked: function (item) {
            var removeRow = function (item) {
                //if (true) {
                var _this = this;
                item.parent().remove('SettlementListRetro', item.getCurrentModel());
                //}
                $pt.Components.NConfirm.getConfirmModal().hide();
            };
            $pt.Components.NConfirm.getConfirmModal().show(NTable.REMOVE_CONFIRM_TITLE, NTable.REMOVE_CONFIRM_MESSAGE, removeRow.bind(this, item));
        },
        checkReserveItem: function (AllReserveList, deleteReserveList, ReserveItems) {
            var AllReserveItem = [];
            var currentReserveItem = [];
            var deleteReserveItem = [];
            if (null != AllReserveList && undefined != AllReserveList) {
                for (var x = 0; x < AllReserveList.length; x++) {
                    AllReserveItem.push(AllReserveList[x].SectionId + AllReserveList[x].ReserveType + AllReserveList[x].OriginalCurrency);
                }
            }
            if (null != deleteReserveList && undefined != deleteReserveList) {
                for (var t = 0; t < deleteReserveList.length; t++) {
                    deleteReserveItem.push(deleteReserveList[t].SectionId + deleteReserveList[t].ReserveType + deleteReserveList[t].OriginalCurrency)
                }
            }
            for (var a = 0; a < AllReserveItem.length; a++) {
                for (var b = 0; b < deleteReserveItem.length; b++) {
                    if (AllReserveItem[a] === deleteReserveItem[b]) {
                        return;
                    } else {
                        currentReserveItem.push(AllReserveItem[a]);
                    }
                }
            }
            var Flag = [];
            for (var n = 0; n < ReserveItems.length; n++) {
                Flag.push(false);
            }
            for (var y = 0; y < currentReserveItem.length; y++) {
                for (var z = 0; z < ReserveItems.length; z++) {
                    if (currentReserveItem[y] == ReserveItems[z]) {
                        Flag[z] = true;
                        break;
                    }
                }
            }
            for (var q = 0; q < Flag.length; q++) {
                if (Flag[q] == false) {
                    ReserveItems.splice(q, 1);
                    Flag.splice(q, 1);
                }
            }
        },
        removeClaims: function (model) {
            var relatedClaim = this.model.get("RelatedClaimList");

            if ("" != this.model.get("RelatedClaimList") && undefined != this.model.get("RelatedClaimList") && null != this.model.get("RelatedClaimList")) {
                var EventId = this.model.get("EventId");
                var type = $page.pageType;
                var removeClaimsDone = function (data, textStatus, jqXHR) {
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: ['Successful.'],
                        afterClose: function () {
                            var url = $pt.getURL('ui.claim.eventInfo');
                            console.log(url);
                            window.location.href = url + "?eventId=" + EventId + "&pageType=" + type;
                        }
                    });
                }.bind(this);
                $page.service.removeClaims(relatedClaim, false, false, removeClaimsDone);
            } else {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please select the claim to be removed first.']
                });
            }


        },
        loadClaim: function (claimId, type, opcode) {
            var url = $pt.getURL('ui.claim.claimInfo');
            window.open(url + "?claimId=" + claimId + "&pageType=" + type);
        },

    };


    var forContract = {
        getContractSection: function () {
            if (!this.addContractSectionDialog) {
                this.addContractSectionDialog = NModalForm.createFormModal("Choose Sections");
            }
            return this.addContractSectionDialog;
        },
        /**
         *
         * @param treeCodesData {*} JSON object
         * @param dialogModel {ModelInterface} previous dialog model, optional
         */
        addContractSection: function (treeCodesData, dialogModel, type) {
            var sectionForm = this.getContractSection();
            var sectionModel = dialogModel ? dialogModel : $pt.createModel({});

            var treeCodes = $pt.createCodeTable(treeCodesData ? treeCodesData : []);
            var sectionLayout = $pt.createFormLayout($page.layout.createAddContractSectionDialogLayout(treeCodes, type));
            if (treeCodesData != null) {
                $page.controller.checkedSection(dialogModel, type);
            }
            sectionModel.addPostChangeListener("ContractRecords", function (evt) {
                console.log(evt.model.getCurrentModel());
                sectionModel.set("ContractID", evt.model.getCurrentModel().ContractRecords.ContractCode);
                sectionModel.set("UnderWritingYear", evt.model.getCurrentModel().ContractRecords.UwYear);

                //this.model.set("ContractID",_this.model.getCurrentModel().ContractRecords.ContractCode);
                //this.model.set("UnderwritingYear",_this.model.getCurrentModel().ContractRecords.UwYear);

            });
            sectionModel.addPostChangeListener("ContractID", function (evt) {
                console.log(evt.model.getCurrentModel().ContractID);
                console.log(evt.model.get("ContractID"));

                if (evt.model.get("ContractID") == "") {
                    sectionModel.set("UnderWritingYear", "");
                }


            });

            sectionForm.show(
                {
                    model: sectionModel,
                    layout: sectionLayout,
                    buttons: {
                        reset: false,
                        validate: false,
                        cancel: false,
                        right: [
                            {
                                text: "Submit",
                                style: "primary",
                                click: function (dialogModel) {
                                    $page.controller.onAddContractSectionSubmit(dialogModel, sectionForm, type);
                                }
                            }
                        ]
                    }
                }
            );
        },
        checkedSection: function (dialogModel, type) {
            var params = {
                RefId: this.model.get("EventId"),
                BusinessDirection: type
            };
            console.log(params);

            var checkSection = function (data, textStatus, jqXHR) {
                console.log(data);
                $page.num = data.length;
                dialogModel.set("SectionTree", data);
            }.bind(this);
            $page.service.checkSectionSer(params, false, false, checkSection);
        },
        onAddContractSectionSearch: function (dialogModel, type) {
            $page.controller.findContract(dialogModel, type);
        },
        onAddContractSectionSubmit: function (dialogModel, sectionForm, type) {
            var array = dialogModel.get("SectionTree");
            var num = 0;
            if (null != $page.findContractData && undefined != $page.findContractData && null != array && undefined != array) {
                for (var index = 0; index < array.length; index++) {
                    var findData = JSON.stringify($page.findContractData);
                    if (findData.indexOf(array[index]) != -1) {
                        num = num + 1;
                    }
                }
            }
            var UnderWritingYear = dialogModel.get("UnderWritingYear");
            if (num > 0) {
                var treeArray = [];
                for (var i = 0; i < array.length; i++) {
                    treeArray.push(array[i]);
                }
                if(this.checkContractDate()){
                    $page.controller.saveContract(treeArray, type);
                    sectionForm.hide();
                }
            } else {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    close: false,
                    messages: ['Please select at least one section/sub-section.']
                });
            }

        },
        findContract: function (dialogModel, type) {
            var _this = this;
            var params = {
                ContractID: dialogModel.get("ContractID"),
                UnderWritingYear: dialogModel.get("UnderWritingYear")
            };
            if (dialogModel.get("ContractID") == null || dialogModel.get("ContractID") == undefined || dialogModel.get("ContractID") == ""
                || dialogModel.get("UnderWritingYear") == null || dialogModel.get("UnderWritingYear") == undefined || dialogModel.get("UnderWritingYear") == "") {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    close: true,
                    messages: ['Please first locate the contract and the underwriting year in the Contract ID Query, and then search the related sections ! ']

                });
            } else {
                var findContract = function (data, textStatus, jqXHR) {
                    $helper.lowerKeysOfJSON(data);
                    $page.findContractData = data;
                    _this.addContractSection(data, dialogModel, type);
                }.bind(this);
                var failFindContract = function (data, textStatus, jqXHR) {
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        close: true,
                        messages: ['Please first locate the contract and the underwriting year in the Contract ID Query, and then search the related sections ! ']

                    });
                }.bind(this);
                $page.service.findContractSer(params, false, false, findContract, failFindContract);
            }

        },
        checkContractDate:function(){
            var data = $page.findContractData;
            var contCompId = null;
            var checkFlag = true;
            for(var i =0;i<data.length;i++){
                contCompId = data[i].id;
            }
            var findContractDate = function (data, textStatus, jqXHR) {
                console.log("getContractDate="+new Date(data));
                var lossStartDate = this.model.get("DateOfLossFrom");
                lossStartDate =  moment(lossStartDate).format("YYYY-MM-DD");
                if(lossStartDate < data){
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose:true,
                        messages: ['Claim Loss Start Date must be later than or equal to the contract start day.']
                    });
                    checkFlag = false;
                }
            }.bind(this);
            $page.service.getContractDate(contCompId, false, false, findContractDate)
            return checkFlag;
        },
        saveContract: function (treeArray, type) {
            // alert(treeArray);
            var params = {
                treeArray: treeArray,
                refType: 1,
                refId: this.model.get("EventId"),
                businessDirection: type
            };
            var saveContract = function (data, textStatus, jqXHR) {
                $helper.lowerKeysOfJSON(data);
                var options = $page.model.rcontractSelection;
                options.reset(data);
            }.bind(this);
            $page.service.saveContractSer(params, false, false, saveContract);

        },

        checkOutCompare: function (node, model) {
            var reserveTable = $page.controller.model.get("ReserveListRetro");
            console.log(model);
            var SectionTree = model.get("SectionTree");
            var flag = true;

            if (reserveTable != undefined && reserveTable != null && reserveTable.length > 0) {

                reserveTable.forEach(function (reserve) {
                    if (node.id == reserve.SectionId) {
                        //NConfirm.getConfirmModal().show("System Message", {
                        //    close: true,
                        //    messages: ['Contract section with reserve cannot be removed.']
                        //
                        //});
                        //SectionTree.push(node.id.valueOf());

                        flag = false;
                    }

                });
            }
        }


    };
    var settlement = {
        getSettlementName: function (settlementModel) {
            var afterGetSettlement = function (data, textStatus, jqXHR) {
                var settlementName = data.SettlementName;
                settlementModel.SettlementName = settlementName;

            }.bind(this);
            var params = {
                RefId: this.model.get("EventId")
            };
            $page.service.getSettlementSer(params, false, false, afterGetSettlement);

        }
    };
    var forMessage = {
        showMessage: function () {
            var _this = this;

            var RefId = this.model.get("EventId");

            var afterLoadMessage = function (data, textStatus, jqXHR) {
                var messageTree = data.MessageTree;
                var checkedMessage = data.Messages;

                $helper.lowerKeysOfJSON(messageTree);
                _this.addMessage(messageTree, checkedMessage);
            }.bind(this);
            $page.service.getAllClaimMessageService(RefId, false, false, afterLoadMessage);
        },
        addMessage: function (messageCodesData, checkedMessage) {
            var messageForm = this.getMessage();
            var messageModel = $pt.createModel({});

            var messageCodes = $pt.createCodeTable(messageCodesData ? messageCodesData : []);
            var messageLayout = $pt.createFormLayout($page.layout.createMessageLayout(messageCodes));
            if (messageCodesData != null) {
                messageModel.set("MessageTree", checkedMessage);
            }

            messageForm.show(
                {
                    model: messageModel,
                    layout: messageLayout,
                    buttons: {
                        reset: false,
                        validate: false,
                        cancel: false,
                        right: [
                            {
                                text: "Ok",
                                style: "primary",
                                click: function (dialogModel) {
                                    $page.controller.onSubmitCheckedMessage(dialogModel, messageForm);
                                }
                            },
                            {
                                text: "Do not remind me again",
                                style: "primary",
                                click: function (dialogModel) {
                                    $page.controller.onSubmitCheckedMessage(dialogModel, messageForm);
                                }
                            }
                        ]
                    }
                }
            );
        },
        getMessage: function () {
            if (!this.addMessageDialog) {
                this.addMessageDialog = NModalForm.createFormModal("Messages");
            }
            return this.addMessageDialog;
        },
        onSubmitCheckedMessage: function (dialogModel, messageForm) {
            var array = dialogModel.get("MessageTree");

            if (array == null || array.length == 0) {
                $page.controller.saveMessage(null);

            } else {
                var treeArray = [];
                for (var i = 0; i < array.length; i++) {
                    treeArray.push(array[i]);
                }
                $page.controller.saveMessage(treeArray);

            }
            messageForm.hide();
        },
        saveMessage: function (treeArray) {
            var params = {
                treeArray: treeArray,
                refId: this.model.get("EventId")
            };
            var afterSubmitMessage = function (data, textStatus, jqXHR) {
            }.bind(this);
            $page.service.submitClaimMessageService(params, false, false, afterSubmitMessage);

        }
    };

    var claims = {
        //getClaimRecordsService
        searchClaim: function () {

            var claimRecords = function (data, textStatus, jqXHR) {
                this.megreeClaimRecord(data);
            }.bind(this);
            $page.service.getClaimRecordsService(this.model.getCurrentModel(), false, false, claimRecords);
        },
        megreeClaimRecord: function (data) {

            console.log(data);
            this.model.set("ClaimRecordsList", data.ClaimRecordsList);
            this.model.set("ClaimRecordsSummary", data.ClaimRecordsSummary);
           
            var claims = $page.model.relatedClaims;
            $helper.lowerKeysOfJSON(data.ClaimNos);
            this.form.forceUpdate();

            claims.reset(data.ClaimNos);
          
        }
    };
    var forExcel = {
        exportExcel: function () {
            var RefId = this.model.get("EventId");
            console.log(this.model.get("EventId"));
            var excelParams = {
                RefId: this.model.get("EventId")
            };
            console.log(this.model.__model);
            //var summaryList = this.model.__model.ClaimRecordsSummary;
            //var count = 0;
            //for (var index = 0; index < summaryList.length; index++) {
            //    if (summaryList[index].GrossIncurredLoss != 0 || summaryList[index].GrossRetrocessionRecovery != 0) {
            //        count = count + 1;
            //    }
            //}
            //if (count > 0) {
                $pt.generateFile(10, excelParams);
            //} else {
            //    NConfirm.getConfirmModal().show({
            //        title: 'System Message',
            //        disableClose: true,
            //        messages: ['No detail found. ']
            //    });
            //}
        },
        exportDetailExcel: function () {
            var RefId = this.model.get("EventId");
            console.log(this.model.get("EventId"));
            var excelParams = {
                RefId: this.model.get("EventId")
            };
            //var summaryList = this.model.__model.EventSummary;
            //var count = 0;
            ////for (var index = 0; index < summaryList.length; index++) {
            //if (summaryList[2].ReserveSummary != 0 || summaryList[2].SettlementSummary != 0) {
            //    count = count + 1;
            //}
            //}
            //if (count > 0) {

            ////}
            //if (count > 0) {

                $pt.generateFile(18, excelParams);
            //} else {
            //    NConfirm.getConfirmModal().show({
            //        title: 'System Message',
            //        disableClose: true,
            //        messages: ['No detail found. ']
            //    });
            //}

        }
    };
    var forExitEvent = {
        exit: function () {
            var resource = {
                ResourceId: this.model.get("EventId"),
                ResourceType: 2,
                ResourceNo: this.model.get("EventCode"),
                OwnerId: this.model.get("TaskOwner")
            };

            var url = $pt.getURL('ui.claim.eventSearch');
            var afterExit = function(){
                if ($page.pageType != 2) {
                    var done = function () {
                        window.location.href = url;
                    }.bind(this);
                    this.unlock(resource, done);
                } else {
                    window.location.href = url;
                }
            }.bind(this);
            this.exitConfirm($page.pageType == 2,afterExit);


        }
    };
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me, forContract, settlement, claims, forMessage, forExcel, forExitEvent));
    $page.controller = new Controller();
    //for layout purpose
    //$page.control.initializeErrorModel();
    //$page.control.initialize();
}(typeof window !== "undefined" ? window : this));
