(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.viewEnable = "";
    var internalOffset = {
        initializeData: function() {
            this.model = $pt.createModel($page.model.all,$page.validator);
            this.codes = $pt.createModel($page.codes);
            var urlData = $pt.getUrlData();
            if (urlData.view){
                var afterLoadAllUser = function (data, textStatus, jqXHR) {
                    //console.log(data);
                    $helper.lowerKeysOfJSON(data);
                    $page.model.userCodes = $page.codes.createMutableCodeTable();
                    $page.model.userCodes.reset(data);
                }.bind(this);
                $page.service.loadAllUserCodes(null, false, false, afterLoadAllUser);

                var transId = urlData.transId;
                $page.viewEnable = urlData.view;
                //console.info(transId + "|" + $page.viewEnable);
                if (undefined != transId && null != transId && "" != transId){
                    var _this = this;
                    $page.service.viewOffset(transId, false, true,
                        function(data) {
                            console.info(data);
                            _this.updateOffsetResult(data, true);
                        },
                        function(jqXHR, textStatus, errorThrown) {
                        }
                    );
                }
            }
            else{
                this.model.addPostChangeListener("RegistrationDate", function (evt) {
                    var registrationDate = evt.model.get("RegistrationDate");
                    if (undefined != registrationDate && "" != registrationDate) {
                        registrationDate = moment(registrationDate).format("YYYY-MM-DD");
                        var nowDate = moment(new Date()).format("YYYY-MM-DD");
                        if (registrationDate > nowDate) {
                            NConfirm.getConfirmModal().show({
                                title: "",
                                disableClose: true,
                                messages: ['The Registration Date must be earlier than or equal to system date.']
                            });
                            evt.model.set("RegistrationDate", nowDate);
                        }
                    }
                });
                this.model.set("RegistrationDate", moment(new Date()).format("YYYY-MM-DD"));
            }
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var viewVal = Boolean($page.viewEnable);
            var form = <NForm model={this.model} layout={layout} view={viewVal}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        resetBalanceSearch: function() {
            this.model.set("ContractId",null);
            this.model.set("PartnerCode",null);
        },
        resetDebitCredit:function() {
            this.model.getCurrentModel().Condition = {};
            this.form.forceUpdate();
        },
        searchCreditDebit:function(model){
            var _this = this;console.info(_this);
            //_this.form.setState({showChecked: true});
            $page.service.searchCreditDebit(model, false, true,
                function(data, textStatus, jqXHR) {
                    _this.updateDebitCreditSearchResult(data, true);
                    $pt.LayoutHelper.getComponent('ARAPCreditDebitId').setState({showChecked: true});
                },
                function(jqXHR, textStatus, errorThrown) {
                	NConfirm.getConfirmModal().show({
                        title:"Fail",
                        close: true,
                        messages: [jqXHR.responseText]
                    });
                }
            );
        },
        searchSuspense: function(model){
            console.info(model);
            var criteria = $.extend({}, this.model.getCurrentModel());
            var _this = this;

            $page.service.searchSuspense(model, false, true,
                function(data, textStatus, jqXHR) {
                    _this.updateSuspenseSearchResult(data, true);
                },
                function(jqXHR, textStatus, errorThrown) {
                	NConfirm.getConfirmModal().show({
                        title:"Fail",
                        close: true,
                        messages: [jqXHR.responseText]
                    });
                }
            );
        },
        updateSuspenseSearchResult: function (data, updateUI) {
            console.log("############updateSearchResult###################");
            console.log(data);
            var _this = this;
            this.model.mergeCurrentModel({
                Balances: data
            });
            if (updateUI) {
                _this.form.forceUpdate();
            }
        },
        submitOffset:function(){
            if (this.checkOffsetInfo() == false){
                return;
            }
            $page.service.submitOffset(this.model.getCurrentModel(), false, true,
                function(data, textStatus, jqXHR) {
                    var offsetNo = data.SettlementNum;
                    console.info("--Submit offset success!The offset number is --"+offsetNo);

                    NConfirm.getConfirmModal().show({
                        title:"Offset is processed successfully",
                        close: true,
                        messages: ['The offset number is '+offsetNo],
                        afterClose:function(data){
                            var url = $pt.getURL('ui.payment.internalOffset');
                            window.location.href = url;
                        }
                    });
                },
                function(jqXHR, textStatus, errorThrown) {
                    console.info("Submit offset fail!textStatus=" + textStatus + "|errorThrown" + errorThrown);
                    NConfirm.getConfirmModal().show({
                        title:"Fail",
                        close: true,
                        messages: [jqXHR.responseText]
                    });
                }
          );
        },
        checkOffsetInfo:function(){
            var dataModel = this.model;
            var registrationDate = dataModel.get("RegistrationDate");
            if (undefined == registrationDate || null == registrationDate || "" == registrationDate){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Registration Date is required']
                });
                return false;
            }
            // cal total of balance mark-off amount
            var balances = dataModel.get("Balances");
            var markOffAmount=0, totalMarkOffAmount=0;
            if (balances != undefined && null != balances && balances.length > 0){
                for (var m=0; m<balances.length;m++){
                    markOffAmount = balances[m].MarkOffAmountInSettleCurrency;
                    if (undefined != markOffAmount && null != markOffAmount && "" != markOffAmount){
                        totalMarkOffAmount += parseFloat(parseFloat(markOffAmount).toFixed(2));
                    }
                }
            }
            // cal total of ar and ap mark-off amount
            var creditsAndDebits = dataModel.get("CreditsAndDebit");
            if (creditsAndDebits == undefined || null == creditsAndDebits || creditsAndDebits.length == 0){
                NConfirm.getConfirmModal().show({
                    title: "",
                    disableClose: true,
                    messages: ["Please search CN/DN first"]
                });
                return false;
            }
            var entryItems, currency2,msg;
            var settlementAmountTotal = 0;
            var checkResult = false;
            for (var cdLen=0; cdLen<creditsAndDebits.length; cdLen++){
                currency2 = creditsAndDebits[cdLen].CurrencyCode;
                entryItems = creditsAndDebits[cdLen].EntryItems;
                if (undefined != entryItems && null != entryItems && entryItems.length > 0) {
                    for (var j2 = 0; j2 < entryItems.length; j2++) {
                        if (entryItems[j2].ContractSelect) {
                            if (!this.checkMarkOffAmount(entryItems, entryItems[j2].ParentFeeIdStr)) {
                                msg = "Mark-off Amount in USD for the currency group of " + currency2 + " is required";
                                NConfirm.getConfirmModal().show({
                                    title: "",
                                    disableClose: true,
                                    messages: [msg]
                                });
                                return false;
                            }
                            checkResult = true;
                            settlementAmountTotal += this.calSettleAmountTotal(entryItems, entryItems[j2].ParentFeeIdStr);
                        }
                    }
                }
            }
            if (!checkResult){
                NConfirm.getConfirmModal().show({
                    title: "",
                    disableClose: true,
                    messages: ["Please select CN/DN first"]
                });
                return false;
            }
            console.info(parseFloat(parseFloat(totalMarkOffAmount).toFixed(2)) + "|" + parseFloat(parseFloat(settlementAmountTotal).toFixed(2)));
            if (parseFloat(parseFloat(totalMarkOffAmount).toFixed(2)) != parseFloat(parseFloat(settlementAmountTotal).toFixed(2))){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['The offset amount is invalid. Please check']
                });
                return false;
            }
            return true;
        },
        checkMarkOffAmount: function(entryItems, parentStr){
            var bResult = false;
            for (var i=0; i<entryItems.length; i++){
                if (entryItems[i].ParentFeeIdStr != parentStr){
                    continue;
                }
                if (entryItems[i].ContractSelect == false){
                    if (entryItems[i].SettleAmount != undefined && entryItems[i].SettleAmount != "" &&
                        !isNaN(parseFloat(entryItems[i].SettleAmount)) && parseFloat(entryItems[i].SettleAmount) != 0){
                        bResult = true;
                        break;
                    }
                }
            }
            return bResult;
        },
        calSettleAmountTotal: function(entryItems, parentStr){
            var settleAmountTotal = 0;
            for (var i=0; i<entryItems.length; i++){
                if (entryItems[i].ParentFeeIdStr != parentStr){
                    continue;
                }
                if (entryItems[i].ContractSelect == false){
                    if (entryItems[i].SettleAmount != undefined && entryItems[i].SettleAmount != "" &&
                        !isNaN(parseFloat(entryItems[i].SettleAmount))) {
                        settleAmountTotal += parseFloat(parseFloat(entryItems[i].SettleAmount).toFixed(2));
                    }
                }
            }
            return settleAmountTotal;
        },
        updateDebitCreditSearchResult: function (data, updateUI) {
            console.log("############updateSearchResult###################");
            console.log(data);
            var _this = this;
            this.model.mergeCurrentModel({
                CreditsAndDebit: data
            });
            if (updateUI) {
                _this.form.forceUpdate();
            }
        },
        updateOffsetResult:function(data, updateUI){
            var _this = this;
            this.model.mergeCurrentModel({
                CreditsAndDebit: data.CreditsAndDebit,
                Balances:data.Balances,
                TransNumber:data.TransNumber,
                TransStatus:data.TransStatus,
                OperationDate:data.OperationDate,
                OperatorId:data.OperatorId,
                Remark:data.Remark,
                RegistrationDate:data.RegistrationDate
            });
            if (updateUI) {
                _this.form.forceUpdate();
            }
        }
    };
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, internalOffset));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));