/**
 * Created by Elvira.Du on 11/27/2015.
 */
(function(context){
    var $page= $pt.getService(context,'$page');
    $page.bankAccountCodeTable = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+800017});
    $page.viewEnable = "";
    $page.collectInfoEnable = true;
    $page.paymentInfoEnable = true;
    var payment = {
        //initializeErrorModel : function () {
        //    return true;
        //},
        initializeData : function(){
            this.model = $pt.createModel($page.model.all,$page.validator);
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
                console.info(transId + "|" + $page.viewEnable);
                var _this = this;
                $page.service.viewPayment(transId, false, true,
                    function(data) {
                        _this.updatePaymentResult(data, true);
                    },
                    function(jqXHR, textStatus, errorThrown) {
                    }
                );
            }
            if ($page.viewEnable == "") {
                this.model.set("PaymentDate", moment(new Date()).format("YYYY-MM-DD"));
                this.model.set("ValueDate", moment(new Date()).format("YYYY-MM-DD"));
                this.model.set("PaymentMethod", "1");
                this.model.addPostChangeListener("PaymentDate", function(evt){
                    var collectionDate = evt.model.get("PaymentDate");
                    if (undefined != collectionDate && "" != collectionDate) {
                        collectionDate = moment(collectionDate).format("YYYY-MM-DD");
                        var nowDate = moment(new Date()).format("YYYY-MM-DD");
                        if (collectionDate > nowDate){
                            NConfirm.getConfirmModal().show({
                                title:"",
                                disableClose: true,
                                messages: ['The Value Date must be earlier than or equal to system date.']
                            });
                            evt.model.set("PaymentDate", nowDate);
                        }
                    }
                });
                this.model.addPostChangeListener("BankCharge", function(evt){
                    var bankCharge = evt.model.get("BankCharge");
                    if (isNaN(parseFloat(bankCharge)) || parseFloat(bankCharge) < 0){
                       /* NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['Bank Charge must be the number and greater than zero.']
                        });*/
                        evt.model.set("BankCharge", "");
                    }else{
                        evt.model.set("BankCharge", parseFloat(bankCharge));
                    }
                });
                this.model.addPostChangeListener("ChequeDate", function (evt) {
                    var chequeDate = evt.model.get("ChequeDate");
                    if (undefined != chequeDate && "" != chequeDate) {
                        chequeDate = moment(chequeDate).format("YYYY-MM-DD");
                        var nowDate = moment(new Date()).format("YYYY-MM-DD");
                        if (chequeDate > nowDate) {
                            NConfirm.getConfirmModal().show({
                                title: "",
                                disableClose: true,
                                messages: ['The Cheque Date must be earlier than or equal to system date.']
                            });
                            evt.model.set("ChequeDate", "");
                        }
                    }
                });
            }
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var viewVal = Boolean($page.viewEnable);
            var form = <NForm model={this.model} layout={layout} view={viewVal}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        reset: function(){
            this.model.getCurrentModel().Condition = {};
            this.form.forceUpdate();
        },
        queryPayment:function(model) {
            var _this = this;
            if (!this.checkSearchMandatory()){
                return false;
            }
            $page.collectInfoEnable = false;
            $page.service.queryPayment(model, false, true,
                function(data) {
                    console.info(data);
                    _this.updateSearchResult(data, true);
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
        checkPaymentDetail:function(){
            var checkDataModel = this.model;
            var paymentCurrency = checkDataModel.get("PaymentCurrency");
            if (undefined == paymentCurrency || null == paymentCurrency || "" == paymentCurrency){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Payment Currency is required']
                });
                return false;
            }
            var paymentDate = checkDataModel.get("PaymentDate");
            if (undefined == paymentDate || null == paymentDate || "" == paymentDate){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Registration Date is required']
                });
                return false;
            }
            var paidAccount = checkDataModel.get("PaidAccount");
            if (undefined == paidAccount || null == paidAccount || "" == paidAccount){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Paid from Bank Account is required']
                });
                return false;
            }
            var valueDate = checkDataModel.get("ValueDate");
            if (undefined == valueDate || null == valueDate || "" == valueDate){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Value Date is required']
                });
                return false;
            }
            var paymentMethod = checkDataModel.get("PaymentMethod");
            if (undefined == paymentMethod || null == paymentMethod || "" == paymentMethod){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Payment Method is required']
                });
                return false;
            }
            return true;
        },
        checkPayees:function(){
            var checkDataModel = this.model;
            var payees = checkDataModel.get("Payee");
            if (undefined != payees && null != payees && payees.length > 0){
                var len = payees.length;
                var payee, paymentAmount;
                for (var index=0;index<len;index++){
                    payee = payees[index].Payee;
                    if (undefined == payee || null == payee || "" == payee){
                        NConfirm.getConfirmModal().show({
                            title:"",
                            disableClose: true,
                            messages: ['Payee is required']
                        });
                        return false;
                    }
                    paymentAmount = payees[index].PaymentAmount;
                    if (undefined == paymentAmount || null == paymentAmount || "" == paymentAmount){
                        NConfirm.getConfirmModal().show({
                            title:"",
                            disableClose: true,
                            messages: ['Payment Amount is required']
                        });
                        return false;
                    }
                }
            }
            else {
                NConfirm.getConfirmModal().show({
                    title: "",
                    disableClose: true,
                    messages: ["Please add a Payee first"]
                });
                return false;
            }
            return true;
        },
        checkSearchMandatory:function(){
            if (!this.checkPaymentDetail()){
                return false;
            }
            if (!this.checkPayees()){
                return false;
            }
            return true;
        },
        savePayment:function(){
            if (!this.checkSearchMandatory()){
                return false;
            }
            if (this.checkPaymentMandatory() == false){
                return;
            }
            if (this.checkPaymentInfo() == false){
                return;
            }
            $page.service.savePayment(this.model.getCurrentModel(), false, true,
                function(data) {
                    var paymentNo = data.SettlementNum;
                    console.info("--Save Payment success!The Payment number is --"+paymentNo);

                    NConfirm.getConfirmModal().show({
                        title:"Payment is processed successfully",
                        close: true,
                        messages: ['The Payment Number is '+paymentNo],
                        afterClose:function(){
                            window.location.href = $pt.getURL('ui.payment.payment');
                        }
                    });
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
        checkPaymentMandatory:function(){
            var checkDataModel = this.model;
            console.info(checkDataModel);
            // check mark-off amount
            var creditsAndDebits = checkDataModel.get("CreditsAndDebit");
            if (undefined != creditsAndDebits && null != creditsAndDebits && creditsAndDebits.length > 0){
                var entryItems, currency2, msg, result,amountInPayment,convertedAmount;
                for (var i1=0; i1<creditsAndDebits.length; i1++){
                    entryItems = creditsAndDebits[i1].EntryItems;
                    currency2 = creditsAndDebits[i1].CurrencyCode;
                    amountInPayment = creditsAndDebits[i1].Amount;
                    convertedAmount = creditsAndDebits[i1].ConvertedAmount;
                    if (undefined != entryItems && null != entryItems && entryItems.length > 0) {
                        if (this.checkEntryItemSelect(entryItems)) {
                            if (undefined == amountInPayment || null == amountInPayment || "" == amountInPayment) {
                                msg = "Amount in Payment Currency for the currency group of " + currency2 + " is required";
                                NConfirm.getConfirmModal().show({
                                    title: "",
                                    disableClose: true,
                                    messages: [msg]
                                });
                                return false;
                            }
                            if (undefined == convertedAmount || null == convertedAmount || "" == convertedAmount) {
                                msg = "Amount in Original Currency for the currency group of " + currency2 + " is required";
                                NConfirm.getConfirmModal().show({
                                    title: "",
                                    disableClose: true,
                                    messages: [msg]
                                });
                                return false;
                            }
                        }
                        for (var j1 = 0; j1 < entryItems.length; j1++) {
                            if (entryItems[j1].ContractSelect) {
                                result = this.checkMarkOffAmount(entryItems, entryItems[j1].ParentFeeIdStr);
                                if (!result) {
                                    msg = "Mark-off Amount for the currency group of " + currency2 + " is required";
                                    NConfirm.getConfirmModal().show({
                                        title: "",
                                        disableClose: true,
                                        messages: [msg]
                                    });
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            var balances = checkDataModel.get("Balances");
            if (undefined != balances && null != balances/* && balances.length > 0*/){
                var len = balances.length;
                var markOffAmount, settleAmount;
                for(var index=0; index<len; index++){
                    markOffAmount = balances[index].MarkOffAmount;
                    settleAmount = balances[index].SettleAmount;
                    if ((undefined == settleAmount || null == settleAmount || "" == settleAmount) &&
                        (undefined != markOffAmount && null != markOffAmount && "" != markOffAmount)){
                        NConfirm.getConfirmModal().show({
                            title:"",
                            disableClose: true,
                            messages: ['Refund Amount for Balance is required']
                        });
                        return false;
                    }
                    if ((undefined == markOffAmount || null == markOffAmount || "" == markOffAmount) &&
                        (undefined != settleAmount && null != settleAmount && "" != settleAmount)){
                        NConfirm.getConfirmModal().show({
                            title:"",
                            disableClose: true,
                            messages: ['Refund in OC for Balance is required']
                        });
                        return false;
                    }
                }
            }
            return true;
        },
        checkEntryItemSelect:function(entryItems){
            var checkResult = false;
            for (var j3=0; j3<entryItems.length;j3++){
                if (entryItems[j3].ContractSelect){
                    checkResult = true;
                    break;
                }
            }
            return checkResult;
        },
        checkMarkOffAmount: function(entryItems, parentStr){
            var bResult = false;
            for (var i=0; i<entryItems.length; i++){
                if (entryItems[i].ParentFeeIdStr != parentStr){
                    continue;
                }
                if (entryItems[i].ContractSelect == false){
                    if (entryItems[i].MarkOffAmount != undefined && entryItems[i].MarkOffAmount != "" &&
                        !isNaN(parseFloat(entryItems[i].MarkOffAmount)) && parseFloat(entryItems[i].MarkOffAmount) != 0){
                        bResult = true;
                        break;
                    }
                }
            }
            return bResult;
        },
        checkPaymentInfo:function(){
            var dataModel = this.model;
            var totalAmount = dataModel.get("TotalAmount");
            if (undefined == totalAmount || null == totalAmount || "" == totalAmount) {
                totalAmount = 0;
            }
            var creditsAndDebits = dataModel.get("CreditsAndDebit");
            var entryItems = null;
            var convertedAmount = 0;
            var settlementAmountTotal = 0;
            var currency = "";
            var info = "";
            if (undefined != creditsAndDebits && null != creditsAndDebits && creditsAndDebits.length > 0) {
                for (var i1 = 0; i1 < creditsAndDebits.length; i1++) {
                    var markOffAmountTotal = 0;
                    currency = creditsAndDebits[i1].CurrencyCode;
                    convertedAmount = creditsAndDebits[i1].ConvertedAmount;
                    entryItems = creditsAndDebits[i1].EntryItems;
                    if (undefined != entryItems && null != entryItems && entryItems.length > 0) {
                        for (var j1 = 0; j1 < entryItems.length; j1++) {
                            if (entryItems[j1].ContractSelect) {
                                markOffAmountTotal += this.calMarkOffAmountTotal(entryItems, entryItems[j1].ParentFeeIdStr);
                            }
                        }
                    }
                    if (isNaN(parseFloat(convertedAmount))){
                        convertedAmount = 0;
                    }
                    if(!isNaN(parseFloat(creditsAndDebits[i1].Amount))){
                    	settlementAmountTotal += parseFloat(creditsAndDebits[i1].Amount);
                    }
                    console.info(convertedAmount + "|" + markOffAmountTotal);
                    if (undefined != convertedAmount && "" != convertedAmount && parseFloat(convertedAmount) != markOffAmountTotal) {
                        info += "The Amount in Original Currency does not equal to the total Mark-off Amount for the currency group of " + currency + ";";
                    }
                }
                if (info != "") {
                    NConfirm.getConfirmModal().show({
                        title: "",
                        disableClose: true,
                        messages: [info]
                    });
                    return false;
                }
            }
            
            var balances = dataModel.get("Balances");
            var totalSettleAmount = 0;
            var settleAmount = 0;
            if (undefined != balances && null != balances && balances.length > 0) {
                for (var i2 = 0; i2 < balances.length; i2++) {
                    settleAmount = balances[i2].SettleAmount;
                    if (undefined != settleAmount && null != settleAmount && "" != settleAmount) {
                        totalSettleAmount += parseFloat(parseFloat(settleAmount).toFixed(2));
                    }
                }
            }
            console.info(settlementAmountTotal+"|"+totalSettleAmount);
            var checkAmount = parseFloat(parseFloat(settlementAmountTotal).toFixed(2)) +
                parseFloat(parseFloat(totalSettleAmount).toFixed(2));
            console.info(totalAmount+"|"+checkAmount);
            if (parseFloat(parseFloat(totalAmount).toFixed(2)) > parseFloat(parseFloat(checkAmount).toFixed(2))){
                var exceedAmount = parseFloat(parseFloat(totalAmount).toFixed(2)) - parseFloat(parseFloat(checkAmount).toFixed(2));
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['The Total Payment Amount exceeds by ' + $helper.formatNumberForLabel(exceedAmount, 2)]
                });
                return false;
            }
            else if (parseFloat(parseFloat(totalAmount).toFixed(2)) < parseFloat(parseFloat(checkAmount).toFixed(2))){
                var enoughAmount = parseFloat(parseFloat(totalAmount).toFixed(2)) - parseFloat(parseFloat(checkAmount).toFixed(2));
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['The Total Payment Amount is '+$helper.formatNumberForLabel(enoughAmount, 2)+' less']
                });
                return false;
            }
            return true;
        },
        calSettleAmountTotal: function(entryItems, parentStr){
            var settleAmountTotal = 0;
            for (var i=0; i<entryItems.length; i++){
                if (entryItems[i].ParentFeeIdStr != parentStr){
                    continue;
                }
                if (entryItems[i].ContractSelect == false){
                    if (entryItems[i].SettleAmount != undefined && entryItems[i].SettleAmount != "") {
                        settleAmountTotal += parseFloat(parseFloat(entryItems[i].SettleAmount).toFixed(2));
                    }
                }
            }
            return settleAmountTotal;
        },
        calMarkOffAmountTotal:function(entryItems, parentStr){
            var markOffAmountTotal = 0;
            for (var i=0; i<entryItems.length; i++){
                if (entryItems[i].ParentFeeIdStr != parentStr){
                    continue;
                }
                if (entryItems[i].ContractSelect == false){
                    if (entryItems[i].MarkOffAmount != undefined && entryItems[i].MarkOffAmount != ""){
                        markOffAmountTotal += parseFloat(parseFloat(entryItems[i].MarkOffAmount).toFixed(2));
                    }
                }
            }
            return markOffAmountTotal;
        },
        updateSearchResult: function (data, updateUI) {
            var convertorData = data;
            console.log("############Payment UpdateSearchResult###################");
            console.log(convertorData);
            if(undefined == convertorData.Balance || null == convertorData.Balance || "" == convertorData.Balance){
            	convertorData.Balance = [];
            }
            var _this = this;
            this.model.mergeCurrentModel({
                CreditsAndDebit: convertorData.CreditsAndDebit,
                Balances:convertorData.Balance
            });
            if (updateUI) {
                _this.form.forceUpdate();
            }
        },
        updatePaymentResult: function (data, updateUI) {
            console.info("===========this is return data========= start");
            console.log(data);

            var _this = this;
            var bankCharge = data.BankCharge;
            if (undefined != bankCharge && null != bankCharge
                && "" != bankCharge && parseFloat(bankCharge) > 0){
                //bankCharge = $helper.formatNumberForLabel(bankCharge, 2);
            }else{
                bankCharge = "0.00";
            }
            var totalAmount = data.TotalAmount;
            if (undefined != totalAmount && null != totalAmount
                && "" != totalAmount && parseFloat(totalAmount) > 0){
                //totalAmount = $helper.formatNumberForLabel(totalAmount, 2);
            }else{
                totalAmount = "0.00";
            }
            this.model.mergeCurrentModel({
                CreditsAndDebit: data.CreditsAndDebit,
                Balances:data.Balances,
                Payee:data.Payee,
                PaymentCurrency:data.PaymentCurrency,
                PaidAccount:data.PaidAccount,
                PaidAccountView:data.PaidAccount,
                BankCharge:bankCharge,
                PaymentDate:data.PaymentDate,
                TotalAmount:totalAmount,
                TransNumber:data.TransNumber,
                TransStatus:data.TransStatus,
                OperationDate:data.OperationDate,
                OperatorId:data.OperatorId,
                Remark:data.Remark,
                //ExchangeRateReference:data.ExchangeRateReference
                ValueDate:data.ValueDate,
                PaymentMethod:data.PaymentMethod,
                ChequeNumber:data.ChequeNumber,
                ChequeDate:data.ChequeDate,
                ChequeHolderName:data.ChequeHolderName
            });
            if (updateUI) {
                _this.form.forceUpdate();
            }
        },
        calGainLossInUSD:function(model) {
            console.info(model);
            var gainLoss = 0;
            $page.service.calGainLossInUSD(model, false, false,
                function(data) {
                    gainLoss = data.GainOrLoss;
                    console.info("Gain Or Loss is"+gainLoss);
                },
                function(jqXHR, textStatus, errorThrown) {

                }
            );
            return gainLoss;
        }
    };

    var Controller = jsface.Class($.extend({},$page.ControllerInterface,payment));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));