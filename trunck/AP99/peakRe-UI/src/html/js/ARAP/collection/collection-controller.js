(function (context) {
    var $page = $pt.getService(context, '$page');
    //$page.bankAccountCodeTable = $page.codes.createMutableCodeTable();
    $page.bankAccountColleciton = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+800018});
    $page.contractIDCodeTable = [];
    $page.viewEnable = "";
    $page.collectInfoEnable = true;
    var collection = {
        initializeData: function () {
            this.model = $pt.createModel($page.model.all,$page.validate);
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
                console.info(transId + "|" + $page.viewEnable);
                var _this = this;
                $page.service.viewCollection(transId, false, true,
                    function(data) {
                        _this.updateCollectionResult(data, true);
                    },
                    function(jqXHR, textStatus, errorThrown) {
                    }
                );
            }
            if ($page.viewEnable == "") {
                this.model.addPostChangeListener("CollectionDate", function (evt) {
                    var collectionDate = evt.model.get("CollectionDate");
                    if (undefined != collectionDate && "" != collectionDate) {
                        collectionDate = moment(collectionDate).format("YYYY-MM-DD");
                        var nowDate = moment(new Date()).format("YYYY-MM-DD");
                        if (collectionDate > nowDate) {
                            NConfirm.getConfirmModal().show({
                                title: "",
                                disableClose: true,
                                messages: ['The Registration Date must be earlier than or equal to system date.']
                            });
                            evt.model.set("CollectionDate", nowDate);
                        }
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
                this.model.addPostChangeListener("NetAmount", function (evt) {
                    var bankCharge = evt.model.get("BankCharge");
                    var newAmount = evt.model.get("NetAmount");
                    var collectionAmount = 0;
                    if ("" != newAmount && newAmount != undefined){
                        if (isNaN(parseFloat(newAmount))){
                            evt.model.set("NetAmount","");
                            return false;
                        }
                    }
                    if ("" == newAmount || newAmount == undefined) {
                        newAmount = 0;
                    }
                    else if (parseFloat(newAmount) <=0){
                        newAmount = 0;
                        evt.model.set("NetAmount", "");
                    }
                    if ("" == bankCharge || bankCharge == undefined) {
                        bankCharge = 0;
                    }
                    else if (parseFloat(bankCharge) <=0){
                        bankCharge = 0;
                        evt.model.set("BankCharge", "");
                    }
                    bankCharge = parseFloat(parseFloat(bankCharge).toFixed(2));
                    newAmount = parseFloat(parseFloat(newAmount).toFixed(2));
                    collectionAmount = parseFloat(parseFloat(newAmount).toFixed(2)) + parseFloat(parseFloat(bankCharge).toFixed(2));
                    collectionAmount = parseFloat(collectionAmount).toFixed(2);
                    if (parseFloat(collectionAmount) == 0){
                        collectionAmount = "0.00";
                    }
                    evt.model.set("CollectionAmount", collectionAmount);
                });
                this.model.addPostChangeListener("BankCharge", function(evt){
                    var bankCharge = evt.model.get("BankCharge");
                    var newAmount = evt.model.get("NetAmount");
                    var collectionAmount = 0;
                    if ("" != bankCharge && bankCharge != undefined){
                        if (isNaN(parseFloat(bankCharge))){
                            evt.model.set("BankCharge","");
                            return false;
                        }
                    }
                    if ("" == newAmount || newAmount == undefined) {
                        newAmount = 0;
                    }
                    else if (parseFloat(newAmount) <=0){
                        newAmount = 0;
                        evt.model.set("NetAmount", "");
                    }
                    if ("" == bankCharge || bankCharge == undefined) {
                        bankCharge = 0;
                    }
                    else if (parseFloat(bankCharge) <=0){
                        bankCharge = 0;
                        evt.model.set("BankCharge", "");
                    }
                    bankCharge = parseFloat(parseFloat(bankCharge).toFixed(2));
                    newAmount = parseFloat(parseFloat(newAmount).toFixed(2));
                    collectionAmount = parseFloat(parseFloat(newAmount).toFixed(2)) + parseFloat(parseFloat(bankCharge).toFixed(2));
                    collectionAmount = parseFloat(collectionAmount).toFixed(2);
                    if (parseFloat(collectionAmount) == 0){
                        collectionAmount = "0.00";
                    }
                    evt.model.set("CollectionAmount", collectionAmount);
                });
                this.model.set("CollectionDate", moment(new Date()).format("YYYY-MM-DD"));
                this.model.set("ValueDate", moment(new Date()).format("YYYY-MM-DD"));
                this.model.set("Bank", "001");
                this.model.set("PaymentMethod", "1");
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
        selectCollection:function(model) {
            var _this = this;
            if (!this.checkSearchMandatory()){
                return false;
            }
            $page.collectInfoEnable = false;
            $page.service.selectCollection(model, false, true,
                function(data) {
                    // add contract id
                    var contractIds = _this.updateContractIDs(data);
                    $page.contractIDCodeTable = $page.codes.createMutableCodeTable();
                    $page.contractIDCodeTable.reset(contractIds);
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
        updateContractIDs:function(data){
            var contractIds = [];//[{id:123456,text:123456},{id:654321,text:654321}];
            if (undefined != data && null != data){
                var idArray = [];
                var codeArray = [];
                var entryItems = null;
                var contractId = "";
                var contractCode = "";
                if (undefined != data && null != data && data.length > 0){
                    for(var index1=0; index1<data.length;index1++){
                        entryItems = data[index1].EntryItems;
                        if (undefined != entryItems && null != entryItems && entryItems.length > 0){
                            for(var index2=0;index2<entryItems.length;index2++){
                                contractId = entryItems[index2].ContractID;
                                contractCode = entryItems[index2].ContractCode;
                                if ($.trim(contractCode) != "" && idArray.indexOf(contractId) == -1) {
                                    idArray.push(contractId);
                                    codeArray.push(contractCode);
                                }
                            }
                        }
                    }
                }
                var obj = null;
                for(var index3=0; index3<idArray.length;index3++){
                    obj = {id:idArray[index3],text:codeArray[index3]};
                    contractIds.push(obj);
                }
            }
            return contractIds;
        },
        saveCollection:function(){
            if (this.checkCollectionMandatory() == false){
                return;
            }
            if (this.checkCollectionInfo() == false){
                return;
            }
            $page.service.saveCollection(this.model.getCurrentModel(), false, true,
                function(data) {
                    var collectionNo = data.SettlementNum;
                    NConfirm.getConfirmModal().show({
                        title:"Collection is processed successfully",
                        close: true,
                        messages: ['The Collection Number is '+collectionNo],
                        afterClose:function(){
                            window.location.href = $pt.getURL('ui.payment.collection');
                        }
                    });
                },
                function(jqXHR, textStatus, errorThrown) {
                    console.info("Save collection fail!textStatus=" + textStatus + "|errorThrown" + errorThrown);
                    NConfirm.getConfirmModal().show({
                        title:"Fail",
                        close: true,
                        messages: [jqXHR.responseText]
                    });
                }
            );

        },
        checkSearchMandatory:function(){
            var checkDataModel = this.model;
            var payer = checkDataModel.get("Payer");
            if (undefined == payer || null == payer || "" == payer){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Payer is required']
                });
                return false;
            }
            var registrationDate = checkDataModel.get("CollectionDate");
            if (undefined == registrationDate || null == registrationDate || "" == registrationDate){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Registration Date is required']
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
            var bank = checkDataModel.get("Bank");
            if (undefined == bank || null == bank || "" == bank){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Bank is required']
                });
                return false;
            }
            var currency = checkDataModel.get("CollectionCurrency");
            if (undefined == currency || null == currency || "" == currency){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Collection Currency is required']
                });
                return false;
            }
            var netAmount = checkDataModel.get("NetAmount");
            if (undefined == netAmount || null == netAmount || "" == netAmount){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Net Amount is required']
                });
                return false;
            }
            var collectToBankAccount = checkDataModel.get("CollectToBankAccount");
            if (undefined == collectToBankAccount || null == collectToBankAccount || "" == collectToBankAccount){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Collect To Bank Account is required']
                });
                return false;
            }
            return true;
        },
        checkCollectionMandatory:function(){
            if (!this.checkSearchMandatory()){
                return false;
            }
            var checkDataModel = this.model;
            // check mark-off amount
            var creditsAndDebits = checkDataModel.get("CreditsAndDebit");
            var checkResult = false;
            if (undefined != creditsAndDebits && null != creditsAndDebits && creditsAndDebits.length > 0) {
                var entryItems, markOffAmount, currency2, msg, result,amountInCollection,convertedAmount;
                for (var i1 = 0; i1 < creditsAndDebits.length; i1++) {
                    entryItems = creditsAndDebits[i1].EntryItems;
                    currency2 = creditsAndDebits[i1].CurrencyCode;
                    amountInCollection = creditsAndDebits[i1].Amount;
                    convertedAmount = creditsAndDebits[i1].ConvertedAmount;
                    if (undefined != entryItems && null != entryItems && entryItems.length > 0) {
                        if (this.checkEntryItemSelect(entryItems)) {
                            if (undefined == amountInCollection || null == amountInCollection || "" == amountInCollection) {
                                msg = "Amount in Collection Currency for the currency group of " + currency2 + " is required";
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
                                checkResult = true;
                            }
                        }
                    }
                }
            }
            var balances = checkDataModel.get("Balances");
            console.info('balance = ' + balances);
            if (undefined != balances && null != balances/* && balances.length > 0*/){
                checkResult = true;
                var len = balances.length;
                var balanceType, contractId,partnerCode, settleAmount;
                for(var index=0; index<len; index++){
                    balanceType = balances[index].SuspenseType;
                    if (undefined == balanceType || null == balanceType || "" == balanceType){
                        NConfirm.getConfirmModal().show({
                            title:"",
                            disableClose: true,
                            messages: ['Balance Type is required']
                        });
                        return false;
                    }
                    contractId = balances[index].ContractId;
                    if (balanceType == 1){// balanceType equeal one is the value of Contract Balance.
                        if (undefined == contractId || null == contractId || "" == contractId){
                            NConfirm.getConfirmModal().show({
                                title:"",
                                disableClose: true,
                                messages: ['Contract ID is required']
                            });
                            return false;
                        }
                    }
                    else if (balanceType == 2) {//Business Partner Balance
                        if (undefined != contractId && null != contractId && "" != contractId){
                            NConfirm.getConfirmModal().show({
                                title:"",
                                disableClose: true,
                                messages: ['Balance Type is Business Partner Balance, The Contract ID must be empty']
                            });
                            return false;
                        }
                    }
                    partnerCode = balances[index].PartnerCode;
                    if (undefined == partnerCode || null == partnerCode || "" == partnerCode){
                        NConfirm.getConfirmModal().show({
                            title:"",
                            disableClose: true,
                            messages: ['Business Partner for Balance is required']
                        });
                        return false;
                    }
                    settleAmount = balances[index].SettleAmount;
                    if (undefined == settleAmount || null == settleAmount || "" == settleAmount){
                        NConfirm.getConfirmModal().show({
                            title:"",
                            disableClose: true,
                            messages: ['Collection Amount for Balance is required']
                        });
                        return false;
                    }
                    if (parseFloat(settleAmount) <= 0 || isNaN(parseFloat(settleAmount))){
                        NConfirm.getConfirmModal().show({
                            title:"",
                            disableClose: true,
                            messages: ['Collection Amount should be more than 0']
                        });
                        return false;
                    }
                }
            }
            if (!checkResult){
                NConfirm.getConfirmModal().show({
                    title: "",
                    disableClose: true,
                    messages: ["Please offset credit/debit note records or add at least one balance record"]
                });
                return false;
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
        checkCollectionInfo: function(){
            var dataModel = this.model;
            var collectionAmount = dataModel.get("CollectionAmount");
            var bankCharge = dataModel.get("BankCharge");
            if (undefined == bankCharge || null == bankCharge || "" == bankCharge){
                bankCharge = 0;
            }
            var creditsAndDebits = dataModel.get("CreditsAndDebit");
            var entryItems = null;
            var convertedAmount = 0;
            var settlementAmountTotal = 0;
            var currency = "";
            var info = "";
            if (undefined != creditsAndDebits && null != creditsAndDebits && creditsAndDebits.length > 0) {
                for (var i1=0; i1<creditsAndDebits.length; i1++){
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
                    if (undefined != convertedAmount && "" != convertedAmount &&
                        parseFloat(parseFloat(convertedAmount).toFixed(2)) != parseFloat(parseFloat(markOffAmountTotal).toFixed(2))) {
                        info += "The Amount in Original Currency does not equal to the total Mark-off Amount for the currency group of " + currency;
                    }
                }
                if (info != ""){
                    NConfirm.getConfirmModal().show({
                        title:"",
                        disableClose: true,
                        messages: [info]
                    });
                    return false;
                }
            }
            
            var balances = dataModel.get("Balances");
            var totalSettleAmount = 0;
            var settleAmount = 0;
            if(undefined != balances && null != balances){
            	for (var i2=0; i2<balances.length;i2++){
            		settleAmount = balances[i2].SettleAmount;
            		if (undefined != settleAmount && null != settleAmount && "" != settleAmount && !isNaN(parseFloat(settleAmount))) {
            			totalSettleAmount += parseFloat(parseFloat(settleAmount).toFixed(2));
            		}
            	}
            }
            console.info(settlementAmountTotal+"|"+bankCharge+"|"+totalSettleAmount);
            var checkAmount = parseFloat(parseFloat(settlementAmountTotal).toFixed(2)) +
                parseFloat(parseFloat(bankCharge).toFixed(2)) + parseFloat(parseFloat(totalSettleAmount).toFixed(2));
            collectionAmount = parseFloat(parseFloat(collectionAmount).toFixed(2));
            console.info(collectionAmount+"|"+checkAmount);
            checkAmount = parseFloat(parseFloat(checkAmount).toFixed(2));
            if (collectionAmount > checkAmount){
                var exceedAmount = collectionAmount - checkAmount;
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['The Total Collection Amount exceeds by ' + $helper.formatNumberForLabel(exceedAmount, 2)]
                });
                return false;
            }
            else if (collectionAmount < checkAmount){
                var enoughAmount = checkAmount - collectionAmount;
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['The Total Collection Amount is '+$helper.formatNumberForLabel(enoughAmount, 2)+' less']
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
                    if (entryItems[i].SettleAmount != undefined && entryItems[i].SettleAmount != "" &&
                        !isNaN(parseFloat(entryItems[i].SettleAmount))) {
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
                    if (entryItems[i].MarkOffAmount != undefined && entryItems[i].MarkOffAmount != "" &&
                        !isNaN(parseFloat(entryItems[i].MarkOffAmount))){
                        markOffAmountTotal += parseFloat(parseFloat(entryItems[i].MarkOffAmount).toFixed(2));
                    }
                }
            }
            return markOffAmountTotal;
        },
        updateSearchResult: function (data, updateUI) {
            console.log("############Collection UpdateSearchResult###################");
            console.info("===========this is return data========= start");
            console.log(data);
            console.info("===========this is return data========= end");

            var _this = this;
            this.model.mergeCurrentModel({
                CreditsAndDebit: data
            });
            if (updateUI) {
                _this.form.forceUpdate();
            }
        },
        updateCollectionResult: function (data, updateUI) {
            console.info("===========this is return data========= start");
            console.log(data);

            var _this = this;
            var bankCharge = data.BankCharge;
            if (undefined != bankCharge && null != bankCharge && "" != bankCharge && parseFloat(bankCharge) > 0){
               // bankCharge = $helper.formatNumberForLabel(bankCharge, 2);
            }else{
                bankCharge = "0.00";
            }
            var collectionAmount = data.CollectionAmount;
            if (undefined != collectionAmount && null != collectionAmount && "" != collectionAmount && parseFloat(collectionAmount) > 0){
                //collectionAmount = $helper.formatNumberForLabel(collectionAmount, 2);
            }else{
                collectionAmount = "0.00";
            }
            var netAmount = parseFloat(collectionAmount) - parseFloat(bankCharge);
            if (undefined != netAmount && null != netAmount && "" != netAmount && parseFloat(netAmount) > 0){
                //netAmount = $helper.formatNumberForLabel(netAmount, 2);
            }else{
                netAmount = "0.00";
            }
            var balances = data.Balances;
            if (undefined != balances && null != balances && balances.length > 0){
                var balanceLen = balances.length;
                var balance,objBalance;
                var contractIDs = [];
                for (var i99=0; i99<balanceLen; i99++){
                    balance = balances[i99];
                    objBalance = {id:balance.ContractCode,text:balance.ContractCode};
                    contractIDs.push(objBalance);
                }
                $page.contractIDCodeTable = $page.codes.createMutableCodeTable();
                $page.contractIDCodeTable.reset(contractIDs);
            }
            var payer = data.Payer + "";
            var bank = data.Bank + "";
            console.info(payer + "|" + bank);
            this.model.mergeCurrentModel({
                CreditsAndDebit: data.CreditsAndDebit,
                Balances: balances,
                CollectionDate:data.CollectionDate,
                ValueDate:data.ValueDate,
                PaymentMethod:data.PaymentMethod,
                BankCharge:bankCharge,
                ChequeNumber:data.ChequeNumber,
                ChequeDate:data.ChequeDate,
                ChequeHolderName:data.ChequeHolderName,
                CollectionCurrency:data.CollectionCurrency,
                CollectionAmount:collectionAmount,
                NetAmount:netAmount,
                CollectToBankAccountView:data.CollectToBankAccount,
                TransNumber:data.TransNumber,
                TransStatus:data.TransStatus,
                OperationDate:data.OperationDate,
                OperatorId:data.OperatorId,
                Remark:data.Remark,
                PayerName:data.PayerName,
                Bank:data.Bank,
                BankName:data.BankName
            });
            if (updateUI) {
                _this.form.forceUpdate();
            }
        },
        calGainLossInUSD:function(model) {
            var gainLoss = 0;
            $page.service.calGainLossInUSD(model, false, false,
                function(data) {
                    gainLoss = data.GainOrLoss;
                },
                function(jqXHR, textStatus, errorThrown) {

                }
            );
            return gainLoss;
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, collection));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));