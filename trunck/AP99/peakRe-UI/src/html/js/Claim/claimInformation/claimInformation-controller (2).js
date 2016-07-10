/**
 * Created by Gordon.Cao on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    //$page.contractSelection = $page.codes.createMutableCodeTable();
    //$page.rcontractSelection = $page.codes.createMutableCodeTable();
    //$page.EventCodes = $page.codes.createMutableCodeTable();
    var flag = [];
    $page.cliamId = null;

    $page.pageType = 0;
    var initClaim = {
        initializeErrorModel: function () {
            //this.errorModel = $pt.createModel($page.model.error);
            return true;
        },
        initializeData: function () {
            //console.debug("initializeData");
            //this.codes = $pt.createModel($page.codes);
            console.log($pt.date);
            this.model = $pt.createModel($page.model.createModel(), $page.validator);
            var urlData = $pt.getUrlData();
            var claimId = urlData.claimId;
            $page.claimId = claimId;
            var pageType = urlData.pageType;
            $page.pageType = pageType;
            console.log('claimId = ' + claimId);
            // var _this = this;
            var loadFContract = function (data, textStatus, jqXHR) {
                console.log(data);
                $helper.lowerKeysOfJSON(data);
                //$page.contractSelection.reset(data);
                //console.log($page.contractSelection);
                $page.model.contractSelection = $page.codes.createMutableCodeTable();
                $page.model.contractSelection.reset(data);
            }.bind(this);
            $page.service.loadFContractSection(claimId, false, false, loadFContract);

            var loadRContract = function (data, textStatus, jqXHR) {
                console.log(data);
                $helper.lowerKeysOfJSON(data);
                //$page.rcontractSelection.reset(data);
                //console.log($page.rcontractSelection);
                $page.model.rcontractSelection = $page.codes.createMutableCodeTable();
                $page.model.rcontractSelection.reset(data);
            }.bind(this);
            $page.service.loadRContractSection(claimId, false, false, loadRContract);

            this.loadClaimModel(claimId);
            //console.log("----------"+this.model.get("ReserveList").length);

            var _this = this;
            this.model.addPostChangeListener("DateOfReport", function (evt) {
                var DateOfReport = evt.model.get("DateOfReport");
                var DateOfLossFrom = evt.model.get("DateOfLossFrom");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");
                if ("" != DateOfLossFrom && null != DateOfLossFrom && undefined != DateOfLossFrom) {
                    DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                }
                if ("" != DateOfReport && null != DateOfReport && undefined != DateOfReport) {
                    DateOfReport = moment(DateOfReport).format("YYYY-MM-DD HH:mm");
                    if (DateOfReport > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: [' Date of Report must be earlier or equal to today.']
                        });
                        _this.model.set("DateOfReport", "");
                    }

                    if (DateOfReport < DateOfLossFrom) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: [' Date of Report must  be later or equal to Loss Start Date .']
                        });
                        _this.model.set("DateOfReport", "");
                    }
                }

            });
            this.model.addPostChangeListener("DateOfLossFrom", function (evt) {
                var DateOfLossFrom = evt.model.get("DateOfLossFrom");
                var DateOfReport = evt.model.get("DateOfReport");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");
                if (null != DateOfReport && "" != DateOfReport && undefined != DateOfReport) {
                    DateOfReport = moment(DateOfReport).format("YYYY-MM-DD HH:mm");
                }
                if (null != DateOfLossFrom && "" != DateOfLossFrom && undefined != DateOfLossFrom) {
                    DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                    if (DateOfLossFrom > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: [' Loss Start Date must be earlier or equal to Today ']
                        });
                        _this.model.set("DateOfLossFrom", "");
                    }

                    if (DateOfLossFrom > DateOfReport) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: [' Loss Start Date must be earlier or equal to  Date of Report.']
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
                if (null != DateOfLossFrom && "" != DateOfLossFrom && undefined != DateOfLossFrom) {
                    DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                }
                if (null != DateOfLossTo && "" != DateOfLossTo && undefined != DateOfLossTo) {
                    DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");
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
            this.model.addPostChangeListener("DateOfReceipt", function (evt) {
                var DateOfReceipt = evt.model.get("DateOfReceipt");
                var nowDate = moment(new Date()).format("YYYY-MM-DD");
                if ("" != DateOfReceipt && null != DateOfReceipt && undefined != DateOfReceipt) {
                    DateOfReceipt = moment(DateOfReceipt).format("YYYY-MM-DD");
                    if (DateOfReceipt > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: [' Date of Receipt must be earlier or equal to today.']
                        });
                        _this.model.set("DateOfReceipt", nowDate);
                    }
                }

            });
            if (this.model.get("ReserveList") != null && this.model.get("ReserveList") != undefined) {
                $page.ReserveItems = this.getReserveList(this.model.get("ReserveList"));

            }
            if (this.model.get("ReserveListRetro") != null && this.model.get("ReserveListRetro") != undefined) {
                $page.ReserveRetroItems = this.getReserveList(this.model.get("ReserveListRetro"));
            }

            var afterEventCode = function (data, textStatus, jqXHR) {
                //console.log(data);
                //$helper.lowerKeysOfJSON(data);
                //$page.EventCodes.reset(data);
                //console.log($page.EventCodes);
                $helper.lowerKeysOfJSON(data);
                $page.model.eventCodes = $page.codes.createMutableCodeTable();
                $page.model.eventCodes.reset(data);

            }.bind(this);
            $page.service.loadEventCodes(null, false, false, afterEventCode);

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
            $page.service.loadClaimMessage(claimId, false, false, afterLoadClaimMessage);


        },

        loadClaimModel: function (claimId) {
            var loadClaimFu = function (data, textStatus, jqXHR) {
                this.myload(data);
            }.bind(this);
            $page.service.loadClaim(claimId, false, false, loadClaimFu);
        },
        renderContent: function () {
            console.debug("rendering ........" + this.model.get("Status"));
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            if ($page.pageType != 0) {
                layout = $pt.createFormLayout($page.layout.createFormLayout());
                form = <NForm model={this.model} layout={layout} view={true}/>;
            }
            this.form = ReactDOM.render(form, document.getElementById('main'));

        },
        saveClaimInfo: function (model) {
            this.model.validate();
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return;
            }
            if (this.checkDate(model) == false) {
                return;
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
            if (this.checkSettlementItem() == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['There is no SettlementItem,please add one first.']
                });
                return false;
            }
            //this.checkRequired(model);
            //this.getReserve(model);
           // this.checkInput();

            if (!this.checkInput()){
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['This claim is beyond the date range of attached event, please check the dates.'],
                    onConfirm: function (model) {
                        return false;
                    }
                });
            }else if (!this.checkSettlement(model)){
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['There is no related reserve, please add reserve first.']
                });
            }else{
                var saveClaimDone = function (data, textStatus, jqXHR) {
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: ['Successful.']
                    });
                    this.myload(data, true);
                }.bind(this);
                $page.service.saveClaimInfo(model, false, false, saveClaimDone);
            }



            //if ($page.checkInputflag == true) {
            //    if (this.checkSettlement(model) == false) {
            //        NConfirm.getConfirmModal().show({
            //            title: 'System Message',
            //            disableClose: true,
            //            messages: ['There is no related reserve, please add reserve first.']
            //        });
            //    } else {
            //        var saveClaimDone = function (data, textStatus, jqXHR) {
            //            NConfirm.getConfirmModal().show({
            //                title: 'System Message',
            //                disableClose: true,
            //                messages: ['Successful.']
            //            });
            //            this.myload(data, true);
            //        }.bind(this);
            //        $page.service.saveClaimInfo(model, false, false, saveClaimDone);
            //    }
            //} else {
            //    NConfirm.getConfirmModal().show({
            //        title: 'System Message',
            //        disableClose: true,
            //        messages: ['This claim is beyond the date range of attached event, please check the dates.'],
            //        onConfirm: function (model) {
            //            return false;
            //        }
            //    });
            //}
        },
        checkInput: function () {
            var _this = this;
            var DateOfLossFrom = _this.model.get("DateOfLossFrom");
            var DateOfReport = _this.model.get("DateOfReport");
            var DateOfLossTo = _this.model.get("DateOfLossTo");
            DateOfReport = moment(DateOfReport).format("YYYY-MM-DD HH:mm");
            DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
            DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");
            console.log("this==="+this.model.get("EventId"));
            var dateFlag = true;
            var params = {
                EventId: this.model.get("EventId")
            };
            if (this.model.get("EventId") != "" && this.model.get("EventId") != null) {
                var eventDateReturn = function (data, textStatus, jqXHR) {
                    var eventDateFrom = data.DateOfLossFrom;
                    var eventDateTo = data.DateOfLossTo;
                    var checkInputNum =0;
                    eventDateFrom = moment(eventDateFrom).format("YYYY-MM-DD HH:mm");
                    eventDateTo = moment(eventDateTo).format("YYYY-MM-DD HH:mm");
                    if (DateOfLossFrom < eventDateFrom || DateOfLossFrom > eventDateTo) {
                        checkInputNum = checkInputNum + 1;
                    }
                    if (DateOfLossTo < eventDateFrom || DateOfLossTo > eventDateTo) {
                        checkInputNum = checkInputNum + 1;
                    }
                    if (checkInputNum > 0) {
                        dateFlag = false;
                    }
                }.bind(this);
                $page.service.checkEventDate(params, false, false, eventDateReturn)
            }
            return dateFlag;
        },
        onRemoveSettlementClicked: function (settlement,item) {
            var removeRow = function (item) {
                //if (true) {
                var _this = this;
                item.parent().remove(settlement, item.getCurrentModel());
                //}
                $pt.Components.NConfirm.getConfirmModal().hide();
            };
            $pt.Components.NConfirm.getConfirmModal().show(NTable.REMOVE_CONFIRM_TITLE, NTable.REMOVE_CONFIRM_MESSAGE, removeRow.bind(this, item));
        },
        checkSettlement: function (model) {
            var flag = [];
            if (this.model.getCurrentModel().SettlementList != null && this.model.getCurrentModel().SettlementList != undefined) {
                flag.push(this.checkIfExitReserve(this.model.get("ReserveList"), this.model.getCurrentModel().SettlementList));
            }
            if (this.model.getCurrentModel().SettlementListRetro != null && this.model.getCurrentModel().SettlementListRetro != undefined) {
                flag.push(this.checkIfExitRetroReserve(this.model.get("ReserveListRetro"), this.model.getCurrentModel().SettlementListRetro));
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
        checkTableMandatory: function () {
            console.log("table");
            console.log(this.model.getCurrentModel().ReserveList);
            var reserveList = this.model.getCurrentModel().ReserveList;
            console.log(reserveList);
            var reserveListRetro = this.model.getCurrentModel().ReserveListRetro;
            var settlementList = this.model.getCurrentModel().SettlementList;
            var settlementListRetro = this.model.getCurrentModel().SettlementListRetro;
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
                    if(0 != reserveList[a].AmountOc){
                        if (null == reserveList[a].AmountOc || undefined == reserveList[a].AmountOc || "" == reserveList[a].AmountOc) {
                            rNum = rNum + 1;
                        }
                    }
                }
            }
            if (null != reserveListRetro && undefined != reserveListRetro) {
                for (var b = 0; b < reserveListRetro.length; b++) {
                    if (null == reserveListRetro[b].SectionId || undefined == reserveListRetro[b].SectionId) {
                        rNum = rNum + 1;
                    }
                    if (null == reserveListRetro[b].RetroRefSectionId || undefined == reserveListRetro[b].RetroRefSectionId) {
                        rNum = rNum + 1;
                    }
                    if (null == reserveListRetro[b].ReserveType || undefined == reserveListRetro[b].ReserveType) {
                        rNum = rNum + 1;
                    }
                    if (null == reserveListRetro[b].OriginalCurrency || undefined == reserveListRetro[b].OriginalCurrency) {
                        rNum = rNum + 1;
                    }
                    if( 0 != reserveListRetro[b].AmountOc){
                        if (null == reserveListRetro[b].AmountOc || undefined == reserveListRetro[b].AmountOc || "" == reserveListRetro[b].AmountOc) {
                            rNum = rNum + 1;
                        }
                    }
                }
            }
            if (null != settlementList && undefined != settlementList) {
                for (var c = 0; c < settlementList.length; c++) {
                    if (null != settlementList[c].SettlementItemList && undefined != settlementList[c].SettlementItemList) {
                        var settlementItemList = settlementList[c].SettlementItemList;
                        console.log(settlementItemList);
                        for (var d = 0; d < settlementItemList.length; d++) {
                            if (null == settlementItemList[d].SectionId || undefined == settlementItemList[d].SectionId) {
                                sNum = sNum + 1;
                            }
                            if (null == settlementItemList[d].SettlementType || undefined == settlementItemList[d].SettlementType) {
                                sNum = sNum + 1;
                            }
                            if (null == settlementItemList[d].OriginalCurrency || undefined == settlementItemList[d].OriginalCurrency) {
                                sNum = sNum + 1;
                            }
                            if (null == settlementItemList[d].AmountOc || undefined == settlementItemList[d].AmountOc || "" == settlementItemList[d].AmountOc) {
                                sNum = sNum + 1;
                            }

                        }
                    }
                }
            }
            if (null != settlementListRetro && undefined != settlementListRetro) {
                for (var e = 0; e < settlementListRetro.length; e++) {
                    if (null != settlementListRetro[e].SettlementItemList && undefined != settlementListRetro[e].SettlementItemList) {
                        var settlementItemListRetro = settlementListRetro[e].SettlementItemList;
                        for (var f = 0; f < settlementItemListRetro.length; f++) {
                            if (null == settlementItemListRetro[f].SectionId || undefined == settlementItemListRetro[f].SectionId) {
                                sNum = sNum + 1;
                            }
                            if (null == settlementItemListRetro[f].RetroRefSectionId || undefined == settlementItemListRetro[f].RetroRefSectionId) {
                                sNum = sNum + 1;
                            }
                            if (null == settlementItemListRetro[f].SettlementType || undefined == settlementItemListRetro[f].SettlementType) {
                                sNum = sNum + 1;
                            }
                            if (null == settlementItemListRetro[f].OriginalCurrency || undefined == settlementItemListRetro[f].OriginalCurrency) {
                                sNum = sNum + 1;
                            }
                            if (null == settlementItemListRetro[f].AmountOc || undefined == settlementItemListRetro[f].AmountOc || "" == settlementItemListRetro[f].AmountOc) {
                                sNum = sNum + 1;
                            }

                        }
                    }
                }
            }
            var tableMandatoryFlag = true;
            console.log(rNum+sNum);
            if (rNum > 0 || sNum > 0) {
                tableMandatoryFlag = false;
            }
            return tableMandatoryFlag;
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
                    console.log(SettlementItem[n]);
                    console.log(ReserveItem[k]);
                    if (SettlementItem[n] === ReserveItem[k]) {
                        Flag[n] = true;
                        break;
                    }
                }
            }
            console.log(Flag);
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
        checkIfExitRetroReserve: function (ReserveList, SettlementList) {
            var ReserveItem = [];
            if (ReserveList != undefined && ReserveList != null) {
                for (var i = 0; i < ReserveList.length; i++) {
                    ReserveItem.push(ReserveList[i].RetroRefSectionId + ReserveList[i].SectionId + ReserveList[i].ReserveType + ReserveList[i].OriginalCurrency);
                }
            }
            var SettlementItem = [];
            if (SettlementList != undefined && SettlementList != null) {
                var m;
                for (m = 0; m < SettlementList.length; m++) {
                    if (SettlementList[m].SettlementItemList != null && SettlementList[m].SettlementItemList != undefined) {
                        var settleItemList = SettlementList[m].SettlementItemList;
                        for (var j = 0; j < settleItemList.length; j++) {
                            SettlementItem.push(settleItemList[j].RetroRefSectionId + settleItemList[j].SectionId + settleItemList[j].SettlementType + settleItemList[j].OriginalCurrency);
                        }
                    }
                }
            }
            var Flag = [];
            var num = 0;
            for (var n = 0; n < SettlementItem.length; n++) {
                Flag[n] = false;
                for (var k = 0; k < ReserveItem.length; k++) {
                    console.log(SettlementItem[n]);
                    console.log(ReserveItem[k]);
                    if (SettlementItem[n] === ReserveItem[k]) {
                        Flag[n] = true;
                        break;
                    }
                }
            }
            console.log(Flag);
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
        getReserveList: function (list) {
            var ReserveItems = [];
            if (list != undefined && list != null) {
                for (var i = 0; i < list.length; i++) {
                    ReserveItems.push(list[i].SectionId + list[i].ReserveType + list[i].OriginalCurrency);
                }
                return ReserveItems;
            }
        },
        getReserve: function (model) {
            var params = {
                claimId: $page.claimId
            };
            var getReserveDone = function (data, textStatus, jqXHR) {
                console.log(data);
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Successful.']
                });
                this.myload(data, true);
            }.bind(this);
            $page.service.getReserve(params, false, false, getReserveDone);
        },

        getPosting: function (sectionId) {
            var getPostingDone = function (data, textStatus, jqXHR) {
                $page.postingFlag = data;
            }.bind(this);
            $page.service.getPosting(sectionId, false, false, getPostingDone);
        },


        myload: function (data, updateForm) {
            this.model.mergeCurrentModel(data);
            this.model.set("DeleteReserveList", null);
            this.model.set("DeleteSettlementItemList", null);
            if (updateForm) {
                this.form.forceUpdate();
            }
        },
        submitClaimInfo: function (model) {
            this.model.validate();
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }
            if (this.checkDate(model) == false) {
                return false;
            }
            if (this.checkTableMandatory() == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information..']
                });
                return false;
            }
            if ( this.checkSettlementItem() == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['There is no SettlementItem,please add one first.']
                });
                return false;
            }
            //this.checkRequired(model);

            if(!this.checkInput()){
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['This claim is beyond the date range of attached event, please check the dates.'],
                });
            }else if(!this.checkSettlement(model)){
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['There is no related reserve, please add reserve first.']
                });
            } else if(!this.AMLCheck()) {

            } else {
                var afterSubmit = function (data, textStatus, jqXHR) {
                    var claimId = this.model.get("ClaimId");
                    $page.broker = true;
                    var url = $pt.getURL('ui.claim.claimInfo');
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: ['Successful.'],
                        afterClose: function (data) {
                            window.location.href = url + "?claimId=" + claimId + "&pageType=0";

                        }
                    });

                }.bind(this);
                $page.service.submitClaimInfo(model, false, false, afterSubmit);
            }

            //this.checkInput();
            //
            //if ($page.checkInputflag == true) {
            //    if (!this.checkSettlement(model)) {
            //        NConfirm.getConfirmModal().show({
            //            title: 'System Message',
            //            disableClose: true,
            //            messages: ['There is no related reserve, please add reserve first.']
            //        });
            //    } else if (!this.AMLCheck()) {
            //        NConfirm.getConfirmModal().show({
            //            title: 'System Message',
            //            disableClose: true,
            //            messages: ['The business partner is pending for AML check; the process is denied until the business partner passes the AML check!']
            //        });
            //    } else {
            //        var afterSubmit = function (data, textStatus, jqXHR) {
            //            var claimId = this.model.get("ClaimId");
            //            $page.broker = true;
            //            var url = $pt.getURL('ui.claim.claimInfo');
            //            NConfirm.getConfirmModal().show({
            //                title: 'System Message',
            //                disableClose: true,
            //                messages: ['Successful.'],
            //                afterClose: function (data) {
            //                    //var afterLoadClaimMessage = function (data, textStatus, jqXHR) {
            //                    //    var messageList = data ? data : [];
            //                    //    if (messageList.length != 0) {
            //                    //        var message = "";
            //                    //        data.forEach(function (messages) {
            //                    //            message = message + messages.MessageDescription + '\r\n';
            //                    //        });
            //                    //        NConfirm.getConfirmModal().show({
            //                    //            title: 'System Message',
            //                    //            disableClose: true,
            //                    //            messages: message,
            //                    //            afterClose: function (data) {
            //                    //                window.location.href = url + "?claimId=" + claimId + "&pageType=0";
            //                    //            }
            //                    //        });
            //                    //    } else {
            //                    //        window.location.href = url + "?claimId=" + claimId + "&pageType=0";
            //                    //    }
            //                    //}.bind(this);
            //                    //
            //                    //$page.service.loadClaimAmountMessage(claimId, false, false, afterLoadClaimMessage);
            //                    window.location.href = url + "?claimId=" + claimId + "&pageType=0";
            //
            //                }
            //            });
            //
            //        }.bind(this);
            //        $page.service.submitClaimInfo(model, false, false, afterSubmit);
            //    }
            //} else {
            //    NConfirm.getConfirmModal().show({
            //        title: 'System Message',
            //        disableClose: true,
            //        messages: ['This claim is beyond the date range of attached event, please check the dates.'],
            //        //onConfirm: function (model) {
            //        //    return false;
            //        //}
            //    });
            //}
        },

        AMLCheck: function (model) {
            var RefId = this.model.get("ClaimId");
            var flag = true;
//messageList
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

        checkDate: function (model) {
            var _this = this;
            var nowDate = moment(new Date()).format("YYYY-MM-DD");
            var receiptflag = true;
            if (null != _this.model.get("SettlementList") && undefined != _this.model.get("SettlementList")) {
                var settlementList = _this.model.get("SettlementList");
                for (var index = 0; index < settlementList.length; index++) {
                    var dateOfReceipt = settlementList[index].DateOfReceipt;
                    console.log(dateOfReceipt);
                    if (null != dateOfReceipt && undefined != dateOfReceipt && "" != dateOfReceipt) {
                        dateOfReceipt = moment(dateOfReceipt).format("YYYY-MM-DD");
                        console.log(dateOfReceipt, nowDate);
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
            if (null != _this.model.get("SettlementListRetro") && undefined != _this.model.get("SettlementListRetro")) {
                var settlementListRetro = _this.model.get("SettlementListRetro");
                for (var indexRetro = 0; indexRetro < settlementListRetro.length; indexRetro++) {
                    var dateOfReceiptRetro = settlementListRetro[indexRetro].DateOfReceipt;
                    console.log(dateOfReceiptRetro);
                    if (null != dateOfReceiptRetro && undefined != dateOfReceiptRetro) {
                        dateOfReceiptRetro = moment(dateOfReceiptRetro).format("YYYY-MM-DD");
                        console.log(dateOfReceiptRetro, nowDate
                        );
                        if (dateOfReceiptRetro > nowDate) {
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
        checkSettlementItem: function () {
            var settleFlag = [];
            console.log(this.model.getCurrentModel().SettlementList);
            if (this.model.getCurrentModel().SettlementList != null && this.model.getCurrentModel().SettlementList != undefined) {
                var SettlementList = this.model.getCurrentModel().SettlementList;
                if (SettlementList != undefined && SettlementList != null) {
                    var settlementItemList;
                    for (var n = 0; n < SettlementList.length; n++) {
                        settlementItemList = SettlementList[n].SettlementItemList;
                        if (settlementItemList != null && settlementItemList != undefined && settlementItemList.length > 0) {
                            settleFlag.push(true);
                        } else {
                            settleFlag.push(false);
                        }
                    }
                }
            }

            if (this.model.getCurrentModel().SettlementListRetro != null && this.model.getCurrentModel().SettlementListRetro != undefined) {
                var SettlementListRetro = this.model.getCurrentModel().SettlementListRetro;
                if (SettlementListRetro != undefined && SettlementListRetro != null) {
                    for (var m = 0; m < SettlementListRetro.length; m++) {
                        var settlementItemListRetro = SettlementListRetro[m].SettlementItemList;
                        if (settlementItemListRetro != null && settlementItemListRetro != undefined && settlementItemListRetro.length > 0) {
                            settleFlag.push(true);
                        } else {
                            settleFlag.push(false);
                        }
                    }
                }
            }
            console.log(settleFlag);
            var num = 0;
            var checkSettleFlag = true;
            for (var a = 0; a < settleFlag.length; a++) {
                if (settleFlag[a] == false) {
                    num = num + 1;
                }
            }
            if (num > 0) {
                checkSettleFlag = false;
            }
            return checkSettleFlag ;
        },
        //checkReserveItem:function(AllReserveList,ReserveItems){
        //    var AllReserveItem = [];
        //    if(null != AllReserveList && undefined != AllReserveList){
        //        for (var x = 0; x < AllReserveList.length; x++) {
        //            AllReserveItem.push(AllReserveList[x].SectionId + AllReserveList[x].ReserveType + AllReserveList[x].OriginalCurrency);
        //        }
        //    }
        //
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
        }
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
                console.log(treeCodesData);
                $page.controller.checkedSection(dialogModel, type);
            }
            var _this = this;
            sectionModel.addPostChangeListener("ContractRecords", function (evt) {
                sectionModel.set("ContractID", evt.model.getCurrentModel().ContractRecords.ContractCode);
                sectionModel.set("UnderWritingYear", evt.model.getCurrentModel().ContractRecords.UwYear);

                //this.model.set("ContractID",_this.model.getCurrentModel().ContractRecords.ContractCode);
                //this.model.set("UnderwritingYear",_this.model.getCurrentModel().ContractRecords.UwYear);

            });
            sectionModel.addPostChangeListener("ContractID", function(evt){
                console.log(evt.model.getCurrentModel().ContractID);
                console.log(evt.model.get("ContractID"));

                if (evt.model.get("ContractID") == "") {
                    sectionModel.set("UnderWritingYear","");
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
                RefId: this.model.get("ClaimId"),
                BusinessDirection: type
            };
            //console.log(params);

            var checkSection = function (data, textStatus, jqXHR) {
                //   console.log(data);
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
        findContract: function (dialogModel, type) {
            var _this = this;
            var params = {
                ContractID: dialogModel.get("ContractID"),
                UnderWritingYear: dialogModel.get("UnderWritingYear")
            };

            if (dialogModel.get("ContractID") == null || dialogModel.get("ContractID") == undefined ||dialogModel.get("ContractID") == ""
                || dialogModel.get("UnderWritingYear") == null || dialogModel.get("UnderWritingYear") == undefined || dialogModel.get("UnderWritingYear")=="") {
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

        saveContract: function (treeArray, type) {
            // alert(treeArray);
            var params = {
                treeArray: treeArray,
                refType: 1,
                refId: this.model.get("ClaimId"),
                businessDirection: type
            };
            var saveContract = function (data, textStatus, jqXHR) {
                $helper.lowerKeysOfJSON(data);
                if (type == 1) {
                    var contractOptions = $page.model.contractSelection;
                    contractOptions.reset(data);
                } else {
                    var rcontractOptions = $page.model.rcontractSelection;
                    rcontractOptions.reset(data);
                }
            }.bind(this);
            $page.service.saveContractSer(params, false, false, saveContract);

        },

        checkOutCompare: function (node, model, type) {
            var reserveTable = "";
            if (type == 1) {
                reserveTable = $page.controller.model.get("ReserveList");
            } else {
                reserveTable = $page.controller.model.get("ReserveListRetro");
            }
            //console.log(model);
            var SectionTree = model.get("SectionTree");
            var flag = true;
            if (reserveTable != undefined && reserveTable != null && reserveTable.length > 0) {
                reserveTable.forEach(function (reserve) {
                    if (node.id == reserve.SectionId) {
                        //NConfirm.getConfirmModal().show({
                        //    title: 'System Message',
                        //    close: true,
                        //    messages: ['Contract section with reserve cannot be removed.']
                        //});
                        //SectionTree.push(node.id.valueOf());
                        flag = false;
                    }

                });
            }
            return flag;
        }


    };

    var forMessage = {
        showMessage: function () {
            var _this = this;

            var RefId = this.model.get("ClaimId");

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
                //   $page.controller.checkedMessage(dialogModel, type);
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
            // alert(treeArray);
            var params = {
                treeArray: treeArray,
                refId: this.model.get("ClaimId")
            };
            var afterSubmitMessage = function (data, textStatus, jqXHR) {
                //$helper.lowerKeysOfJSON(data);
                //if (type == 1) {
                //    var contractOptions = $page.contractSelection;
                //    contractOptions.reset(data);
                //    console.log(contractOptions);
                //
                //} else {
                //    var rcontractOptions = $page.rcontractSelection;
                //    rcontractOptions.reset(data);
                //}
            }.bind(this);
            $page.service.submitClaimMessageService(params, false, false, afterSubmitMessage);

        }
    };
    var settlement = {
        getSettlementName: function (settlementModel) {
            var afterGetSettlement = function (data, textStatus, jqXHR) {
                var settlementName = data.SettlementName;
                settlementModel.SettlementName = settlementName;

            }.bind(this);
            var params = {
                RefId: this.model.get("ClaimId")
            };
            $page.service.getSettlementSer(params, false, false, afterGetSettlement);

        }
    };

    var closeAndReopenClaim = {
        closeClaimInfo: function (model) {

            //console.log($page.controller.model.get("ReserveList").set);
            //return false;

            var _this = this;
            var message = '';

            message = 'There is outstanding reserve, are you sure to close this claim?';
            var mo = $page.controller.model;
            this.checkReserve();
            if ($page.flag == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['You are closing this claim, are you sure?'],
                    onConfirm: function (model) {
                        _this.close(mo.getCurrentModel());
                    },
                    onCancel: function () {
                        return false;
                    }

                });
                return false;
            }
            if (this.checkReserve() == false) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    messages: [message],
                    onConfirm: function (model) {
                        _this.close(mo.getCurrentModel());
                    },
                    onCancel: function () {
                        return false;
                    }

                });
            } else {
                _this.close(mo.getCurrentModel());

            }

        },

        checkReserve: function (model) {
            var reserveTable = $page.controller.model.get("ReserveList");
            console.log("......." + reserveTable);
            if (reserveTable != undefined && reserveTable != null && reserveTable.length > 0) {
                var amountAll = 0;
                reserveTable.forEach(function (reserve) {
                    amountAll = amountAll + reserve.AmountOc;
                });
                console.log(amountAll);
                if (amountAll == 0) {
                    $page.flag = false;
                }
                if (amountAll > 0) {
                    return false;
                } else {
                    return true;
                }

            }
        },
        close: function (model) {
            var resource = {
                ResourceId: this.model.get("ClaimId"),
                ResourceType: 2,
                ResourceNo: this.model.get("ClaimNo"),
                OwnerId: this.model.get("TaskOwner")
            };
            var url = $pt.getURL('ui.claim.claimQuery');
            var afterCloseClaim = function (data, textStatus, jqXHR) {
                //$helper.lowerKeysOfJSON(data);
                //_this.addContractSection(data, dialogModel,type)
                var done = function (flag) {
                    window.location.href = url;
                }.bind(this);
                this.unlock(resource, done);
            }.bind(this);
            $page.service.closeClaimService(model, false, false, afterCloseClaim);
        },

        reopenClaim: function (model) {
            var claimNo = this.model.get("ClaimNo");
            var claimId = this.model.get("ClaimId");

            NConfirm.getConfirmModal().show({
                title: 'System Message',
                messages: ['Claim No. ' + claimNo + ' will be reopened, are you sure to continue? '],
                onConfirm: function () {
                    var url = $pt.getURL('ui.claim.claimInfo');
                    var afterReopenClaim = function (data, textStatus, jqXHR) {
                        window.location.href = url + "?claimId=" + claimId + "&pageType=0";
                    }.bind(this);
                    $page.service.reopenClaimService(model, false, false, afterReopenClaim);
                },
                onCancel: function () {
                    return false;
                }
            });

        }


    };

    var forExitClaim = {
        exit: function () {
            var resource = {
                ResourceId: this.model.get("ClaimId"),
                ResourceType: 2,
                ResourceNo: this.model.get("ClaimNo"),
                OwnerId: this.model.get("TaskOwner")
            };

            var url = $pt.getURL('ui.claim.claimQuery');
            var afterExit = function(){
                if($page.pageType != 2){
                    var done = function () {
                        window.location.href = url;
                    }.bind(this);
                    this.unlock(resource, done);
                }else{
                    window.location.href = url;
                }
            }.bind(this);
            this.exitConfirm($page.pageType == 2,afterExit);
        }
    };
    var forExcel = {
        exportExcel: function () {
            var excelParams = {
                RefId: this.model.get("ClaimId")
            };
            $pt.generateFile(9, excelParams);
           // var reserveList = this.model.get("ReserveList");
           // if(null != reserveList && undefined != reserveList && reserveList.length>0){
           //
           // }else{
           //    NConfirm.getConfirmModal().show({
           //        title: 'System Message',
           //        disableClose: true,
           //        messages: ['No detail found. ']
           //    });
           //}

            //if (count > 0) {
            //
            //} else {
            //
            //}


        }

    };
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, initClaim, forContract, settlement, closeAndReopenClaim, forMessage, forExcel, forExitClaim));
    $page.controller = new Controller();
    //for layout purpose
    //$page.control.initializeErrorModel();
    //$page.control.initialize();
}(typeof window !== "undefined" ? window : this));
