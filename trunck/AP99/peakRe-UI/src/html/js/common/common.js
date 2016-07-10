/**
 * Created by brad.wu on 9/1/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var $ri = $pt.getService(context, '$ri');
    var $helper = $pt.getService(context, '$helper');
    var constants = $pt.getService($page, 'constants');
    var envSetting = $pt.getService(context, 'envSetting');

    (function () {
        constants.authorize_url = "/oauth2.0/authorize?client_id=key&response_type=code&";
        constants.accessToken_url = "/oauth2.0/accessToken";
        constants.CAS_KEY = "Authorization";
        constants.MENU_COOKIE_NAME = '__menus';
        constants.USER_COOKIE_NAME = '__user';
        constants.PWD_COOKIE_NAME = '__pwd';
        constants.AUTOLOGIN_COOKIE_NAME = '__auto_login';
        constants.LOGOUT_COOKIE_NAME = '__logout';
        constants.CLIENT_MANAGE = '__client_manage';
        $pt.LayoutHelper.setDefaultCellWidth(4);
        NArrayTab.ADD_LABEL="",
        NForm.LABEL_DIRECTION = 'horizontal';
        NPageFooter.LEFT_TEXT = "";
        NPageFooter.TECH_BASE = "Parrot"
        NPageFooter.COMPANY = "eBaoTech"
        NPageFooter.COMPANY_URL = "https://www.eBaotech.com"
        // NForm.LABEL_DIRECTION = 'vertical';
        // NFormCell.LABEL_WIDTH = 6;
        $pt.ComponentConstants.Default_Date_Format = "YYYY-MM-DDTHH:mm:ss";
        NDateTime.VALUE_FORMAT = "YYYY-MM-DDTHH:mm:ss";
        $pt.BUILD_PROPERTY_VISITOR = false; // do not create the getter and setter for properties
        $pt.defineMessage("common.brand", "PeakRe");
        $pt.defineMessage("AMLCheckMassage","The business partner is pending for AML check; the process is denied until the business partner passes the AML check!");
        $pt.defineMessage("SaveFail","Save failed.");

    })();


    /**
     * Static HTML URL define,format as : ui.module.*
     */
    (function () {
        $pt.defineWebContext('/');
        $pt.defineURL('index', '/index.html');
        //$pt.defineURL('login', '/html/login.html');
        $pt.defineURL('ui.login', '/login.html');
        $pt.defineURL("brand.image", "/js/common/image/generis-6.png");

        //Contract URL Define begin
        $pt.defineURL("ui.contract.management", "/Treaty/contractQuery.html");
        $pt.defineURL("ui.contract.supi", "/Treaty/contractQuery.html?OperateType=4");
        $pt.defineURL("ui.contract.pricing", "/Treaty/contractQuery.html?OperateType=5");
        $pt.defineURL("ui.contract.contractQuery", "/Treaty/contractQuery.html");
        $pt.defineURL("ui.contract.contractView", "/Treaty/contractHome.html?OperateType=0");
        $pt.defineURL("ui.contract.contractHome", "/Treaty/section.html");
        $pt.defineURL("ui.contract.statusLogView", "/Treaty/contractHome.html");

        //Contract URL Define End
        //claim url begin
        $pt.defineURL("ui.claim.claimQuery", "/Claim/claimQuery.html");
        $pt.defineURL("ui.claim.eventSearch", "/Claim/eventSearch.html");
        $pt.defineURL('ui.claim.search', '/Claim/search.html');
        $pt.defineURL("ui.claim.createClaim", "/Claim/claimCreate.html");
        $pt.defineURL("ui.claim.claimInfo", "/Claim/claimInformation.html");
        $pt.defineURL("ui.claim.createEvent", "/Claim/createEvent.html");
        $pt.defineURL("ui.claim.eventInfo", "/Claim/eventInformation.html");
        $pt.defineURL("ui.claim.reserveHistory", "/Claim/reserveHistory.html");
        $pt.defineURL("ui.claim.settlementHistory", "/Claim/settlementHistory.html");
        $pt.defineURL("ui.claim.changePosting", "/Claim/changePosting.html");

        //accounting url begin
        $pt.defineURL("ui.accounting.statementQuery", "/Accounting/statementQuery.html");
        $pt.defineURL("ui.accounting.accountSummary", "/Accounting/accountSummary.html");
        $pt.defineURL("ui.accounting.cedentQuarterView", "/Accounting/cedentQuarterView.html");
        $pt.defineURL("ui.accounting.financialView", "/Accounting/financialView.html");
        $pt.defineURL("ui.accounting.accountSummaryQuery", "/Accounting/accountSummaryQuery.html");
        $pt.defineURL("ui.accounting.actualization", "/Accounting/actualization.html");
        $pt.defineURL("ui.accounting.segmentDetail", "/Accounting/segmentDetail.html");
        $pt.defineURL("ui.accounting.segmentQuery", "/Accounting/segmentQuery.html");
        $pt.defineURL("ui.accounting.ibnrUpload", "/Document/documentUpload.html?businessType=6");
        $pt.defineURL("ui.accounting.exceptionReport", "/Accounting/exceptionReport.html");
        $pt.defineURL("ui.accounting.exceptionReportView", "/Accounting/exceptionReportView.html");
        $pt.defineURL("ui.accounting.batchJobTrigger", "/Accounting/batchJobTrigger.html");
        $pt.defineURL("ui.accounting.revaluationBatchJob", "/Accounting/revaluationBatchJob.html");
        $pt.defineURL("ui.accounting.closingSetting", "/Accounting/closingSetting.html");
        $pt.defineURL('ui.accounting.uwTransactionDetail', '/Accounting/uwTransactionDetail.html');
        $pt.defineURL('ui.accounting.fnTransactionDetail', '/Accounting/fnTransactionDetail.html');
        $pt.defineURL('ui.accounting.statementView','/Accounting/statementView.html');
        //accounting url end

        $pt.defineURL("ui.payment.collection", "/ARAP/collection.html");
        $pt.defineURL("ui.payment.payment", "/ARAP/payment.html");
        $pt.defineURL("ui.payment.internalOffset", "/ARAP/internalOffset.html");
        $pt.defineURL("ui.payment.bankAccountManagement", "/ARAP/bankAccountManagement.html");
        $pt.defineURL("ui.payment.exchangeRateManagement", "/ARAP/exchangeRateManagement.html");
        $pt.defineURL("ui.payment.transactionReversal", "/ARAP/transactionReversal.html");
        $pt.defineURL("ui.payment.transactionQuery", "/ARAP/transactionQuery.html");
        $pt.defineURL("ui.gl.generalLedgerQuery", "/ARAP/glDataQuery.html");
        $pt.defineURL("ui.gl.entryItemInformation", "/ARAP/entryItemInformation.html");
        $pt.defineURL("ui.gl.subLedgerQuery", "/ARAP/subLedgerQuery.html");
        $pt.defineURL("ui.gl.doubleEntries", "/ARAP/doubleEntries.html");

        // partner
        $pt.defineURL("ui.partner.search", "/BusinessPartner/businessPartnerSearch.html");
        $pt.defineURL("ui.partner.individual", "/BusinessPartner/bpCreationForIndividual.html");
        $pt.defineURL("ui.partner.company", "/BusinessPartner/bpCreationForCompany.html");


        $pt.defineURL("ui.queryView.search", "/QueryView/generalQuery.html");
        $pt.defineURL("ui.queryView.view", "/QueryView/queryView.html");
        $pt.defineURL("ui.queryView.settlementDetails","/QueryView/settlementDetails.html");

        $pt.defineURL("ui.system.maintenance","/maintenancecenter/");
        $pt.defineURL("ui.batch.maintenance","/scheduler/");
    })();

    /**
     * Restful Service Context URL define
     * format:service.module.*
     */
    (function () {
      $pt.systemException = function(title, messages, needLogout){
        $pt.Components.NConfirm.getConfirmModal().show({
            title: title,
            disableClose: true,
            messages: messages,
            onConfirm : function(){
              if(needLogout){
                console.log("needLogout==" + needLogout);
                $page.ControllerInterface.logout();
              } else {
                envSetting = $.sessionStorage.get('envSetting');
                if(!envSetting){
                  $page.ControllerInterface.initialize();
                }
                window.location.href = envSetting.PORTAL_HOME_URL;
              }
            }
        });
      }
      $pt.authorityFail = function(jqXHR, textStatus, errorThrown){
        var failStatus = jqXHR.status;
        var failMessage = jqXHR.statusText;
        if(failStatus == "400"){
          $pt.systemException("400 : Bad request",'Bad request, pelase contact with system administrator.',false);
          //Time out
        }else if (failStatus == "401"){
          $pt.systemException("401 : System timeout",'System timeout, please re-login.',true);
          //No authority
        } else if(failStatus == "403"){
          $pt.systemException("403 : No Authority",'No operate permissions, please contact with system administrator.',false);
          //Not found
        } else if (failStatus == "404"){
          $pt.systemException("404:Not found",'The request can\'t be found, please contact with system administrator.',false);
          //Application Error
        } else if (failStatus == "506"){
          var responseText = JSON.parse(jqXHR.responseText);
          console.log(responseText);
          // $pt.systemException("505:System Exception",jqXHR.responseText,false);
          $pt.Components.NConfirm.getConfirmModal().show({
              title: '506 : Exception Raised',
              disableClose: true,
              messages: ['Operation fail, please contact with system administrator',responseText.Messages[0].Message]
          });
        } else {
					$pt.Components.NExceptionModal.getExceptionModal().show("" + jqXHR.status, jqXHR.responseText);
				}
      };
        var doPost = $pt.doPost;
        $pt.internalDoPost = $pt.doPost = function(url, data, settings) {
            var newURL = url + "?"+ $.sessionStorage.get(constants.CAS_KEY);
            settings = $.extend(true,{},settings,{fail : $pt.authorityFail});
            return doPost.call($pt, newURL, data, settings);
        }
        var doGet = $pt.doGet;
        $pt.doGet = function(url, data, settings) {
            var newURL = url + "?"+ $.sessionStorage.get(constants.CAS_KEY);
            settings = $.extend(true,{},settings,{fail : $pt.authorityFail});
            return doGet.call($pt, newURL, data, settings);
        }
        var doPut = $pt.doPut;
        $pt.doPut = function(url, data, settings) {
            var newURL = url + "?"+ $.sessionStorage.get(constants.CAS_KEY);
            settings = $.extend(true,{},settings,{fail : $pt.authorityFail});
            return doPut.call($pt, newURL, data, settings);
        }

        $ri.getRestfulURL = function (key) {
            var url = $pt.routes.urls[key];
            envSetting = $.sessionStorage.get('envSetting');
            if(!envSetting){
              $page.ControllerInterface.initialize();
            }
            return url == null ? null : envSetting.SYS_SERVICE_URL + url;
        };
        //Get Menus
        $pt.defineURL("action.authority.getPermission","/public/authorities/users/permissions");
        $pt.defineURL("action.public.getMenus","/public/menus/loadMenus");

        //Partner URL begin
        $pt.defineURL("action.public.partner", "/public/partner");
        //Documente URL begin
        $pt.defineURL("action.public.document", "/public/document");
        //File URL begin
        $pt.defineURL("action.public.file", "/public/file");
        //Excel URL begin
        $pt.defineURL("action.common.excel", "/public/export");
        //PDF URL begin
        $pt.defineURL("action.common.print", "/public/print");
        //Contract URL begin
        $pt.defineURL("action.contract.contractHome", "/contract/contractHome");
        $pt.defineURL("action.contract.contractBusiness", "/contract/businessCondition");
        $pt.defineURL("action.contract.contractStructure", "/contract/contractStructure");
        $pt.defineURL("action.contract.contractRetrocession", "/contract/contractRetrocession");
        $pt.defineURL("action.contract.contractEndorsement", "/contract/contractEndorsement");
        $pt.defineURL("action.contract.pricingEstimate", "/contract/pricingEstimate");
        $pt.defineURL("action.contract.adjustment", "/contract/adjustment");
        //Contract URL end

        //Accounting Action Begin
        $pt.defineURL("action.accounting.closing", "/accounting/closing");
        $pt.defineURL("action.accounting.estimate", "/accounting/estimate");
        $pt.defineURL("action.accounting.actualization", "/accounting/actualization");
        $pt.defineURL("action.accounting.ibnr", "/accounting/ibnr");
        $pt.defineURL("action.accounting.statement", "/accounting/statementOfAccounting");
        //Accounting Action End

        //common Action Begin
        $pt.defineURL("action.common.codeTable", "/common/codeTable");
        $pt.defineURL("action.common.codeTable.generateByCondition", "/common/codeTable/generateByCondition/");
        $pt.defineURL("action.common.codeTable.generate", "/common/codeTable/generate/");
        $pt.defineURL("action.common.codeTable.generateTree","/common/codeTable/generateTree/");
        $pt.defineURL("action.common.LockInfo", "/common/LockInfo");
        //common Action End

        //claim restful url begin
        $pt.defineURL("action.claim.contractSection", "/claim/claimSection");
        $pt.defineURL("action.claim.info", "/claim/claimInfo");
        $pt.defineURL("action.claim.query", "/claim/query");
        $pt.defineURL("action.claim.event", "/claim/event");
        $pt.defineURL("action.claim.settlement", "/claim/settlement");
        $pt.defineURL("action.claim.reserve", "/claim/reserve");
        $pt.defineURL("action.claim.message", "/claim/message");

        //claim restful url end

        // arap restful url begin
        // Bank Account URL Config
        $pt.defineURL("action.arap.bankAccount.queryBankAccount", "/arap/bankAccount/queryBankAccount");
        $pt.defineURL("action.arap.bankAccount.saveBankAccount", "/arap/bankAccount/saveBankAccount");
        $pt.defineURL("action.arap.bankAccount.deleteBankAccount", "/arap/bankAccount/deleteBankAccount");
        $pt.defineURL("action.arap.bankAccount.updateBankAccount", "/arap/bankAccount/updateBankAccount");
        $pt.defineURL("action.arap.bankAccount.queryBankAccountByCurrency", "/arap/bankAccount/queryBankAccountByCurrency");
        $pt.defineURL("action.arap.bankAccount.queryBank","/arap/bankAccount/queryBank");
        $pt.defineURL("action.arap.bankAccount.queryBankByCode","/arap/bankAccount/queryBankByCode");

        // Exchange Rate URL Config
        $pt.defineURL("action.arap.exchangeRate.queryExchangeRate", "/arap/exchangeRate/queryExchangeRate");
        $pt.defineURL("action.arap.exchangeRate.saveExchangeRate", "/arap/exchangeRate/saveExchangeRate");
        $pt.defineURL("action.arap.exchangeRate.updateExchangeRate", "/arap/exchangeRate/updateExchangeRate");
        $pt.defineURL("action.arap.exchangeRate.invalidExchangeRate", "/arap/exchangeRate/invalidExchangeRate");
        $pt.defineURL("action.arap.exchangeRate.deleteExchangeRate", "/arap/exchangeRate/deleteExchangeRate");
        $pt.defineURL("action.arap.exchangeRate.uploadExchangeRate", "/public/file/upload/filefolder");

        // Collection URL Config
        $pt.defineURL("action.arap.collection.queryCollection", "/arap/collection/queryCollection");
        $pt.defineURL("action.arap.collection.saveCollection", "/arap/collection/saveCollection");
        $pt.defineURL("action.arap.collection.calGainLossInUSD", "/arap/collection/calGainLossInUSD");
        $pt.defineURL("action.arap.collection.viewCollection", "/arap/collection/viewCollection");

        // Payment URL Config
        $pt.defineURL("action.arap.payment.queryPayment", "/arap/payment/queryPayment");
        $pt.defineURL("action.arap.payment.savePayment", "/arap/payment/savePayment");
        $pt.defineURL("action.arap.payment.calGainLossInUSD", "/arap/payment/calGainLossInUSD");
        $pt.defineURL("action.arap.payment.viewPayment", "/arap/payment/viewPayment");

        // Offset URL Config
        $pt.defineURL("action.arap.offset.querySuspense", "/arap/offset/querySuspense");
        $pt.defineURL("action.arap.offset.queryCreditDebit", "/arap/offset/queryCreditDebit");
        $pt.defineURL("action.arap.offset.submitOffset", "/arap/offset/submitOffset");
        $pt.defineURL("action.arap.offset.viewOffset", "/arap/offset/viewOffset");

        // Transaction Reversal URL Config
        $pt.defineURL("action.arap.transactionReversal.queryTransactionReversal", "/arap/transactionReversal/queryTransactionReversal");
        $pt.defineURL("action.arap.transactionReversal.submitTransactionReversal", "/arap/transactionReversal/submitTransactionReversal");

        //Transaction Query URL Config
        $pt.defineURL("action.arap.transactionQuery.queryTransactionQuery", "/arap/transactionQuery/queryTransactionQuery");

        //InternalOffset URL Config
        $pt.defineURL("action.arap.balanceSearch.queryBalanceSearch", "/arap/balanceSearch/queryBalanceSearch");
        // GL Query
        $pt.defineURL("action.arap.GLQuery.queryGeneralLedger", "/arap/GLQuery/queryGeneralLedger");
        $pt.defineURL("action.arap.GLQuery.querySubLedger", "/arap/GLQuery/querySubLedger");
        $pt.defineURL("action.arap.GLQuery.queryEntryItemInformation", "/arap/GLQuery/queryEntryItemInformation");
        $pt.defineURL("action.arap.GLQuery.queryDoubleEntries", "/arap/GLQuery/queryDoubleEntries");
        $pt.defineURL("action.arap.credit.queryCredit","/arap/credit/queryCredit");
        //$pt.defineURL("action.arap.collection.queryCreditNote", "/arap/collection/queryCreditNote");

        // arap restful url end

        //GeneralQuery View url Begin
        $pt.defineURL("action.accounting.statementOfAccounting.viewStatement","/accounting/statementOfAccounting/viewStatement");
        $pt.defineURL("action.query.view.viewClaim","/query/view/viewClaim");
        $pt.defineURL("action.arap.credit.queryTransactionHistory","/arap/credit/queryTransactionHistory");


        $pt.generateFile = function (bizType, model, quiet, async, done, fail) {
            var url = $ri.getRestfulURL("action.public.document");
            $pt.doPost(url+'/create/' + bizType, model, {
                quiet: quiet,
                async: async,
                done: (function (data, textStatus, jqXHR) {
                  var url = $ri.getRestfulURL("action.public.file") + '/download/'+ data.documentId;
                  if (data.fileFormat == "PDF") {
                    url = data.fileURL;
                  }
                  window.open(url +'?'+ $.sessionStorage.get($page.constants.CAS_KEY));
                }),
                fail: fail
            });
        };
        $pt.viewAttachment = function(bizType, bizId, readonly){
            var url = "/Document/documentSearch.html?businessId=" + bizId + "&businessType=" + bizType + "&readOnly=" + readonly;
            return window.open(url);
        };

        $pt.dataImport = function(bizType, bizId) {
            return window.open("/html/Document/documentUpload.html?businessType=" + bizType + "&businessId=" + bizId);
        };





        var date = new Date();
        var mon = date.getMonth() + 1;
        var day = date.getDate();
        var nowDay = date.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day);
        var codeTableUrl = "/js/common/codeTable.js.2016-02-17";
        $pt.defineURL("action.getScript", "/js/common/codeTable.js.2016-02-17");


        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        var strMinute = date.getMinutes();
        var StrHour = date.getHours();
        var strSecond =date.getSeconds();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        if (StrHour >= 0 && StrHour <= 9) {
            StrHour = "0" + StrHour;
        }
        if (strMinute >= 0 && strMinute <= 9) {
            strMinute = "0" + strMinute;
        }
        if (strSecond >= 0 && strSecond <= 9) {
            strSecond = "0" + strSecond;
        }
        $pt.newSystemDate = date.getFullYear() + seperator1 + month + seperator1 + strDate+ "T" + StrHour + seperator2 + strMinute + seperator2 + strSecond;

        $pt.newDate = date.getFullYear() + seperator1 + month + seperator1 + strDate+ "T"+"00"+seperator2+"00"+seperator2+"00";

    })();

    /**
     *
     */
    (function () {
      String.prototype.currencyFormat = function (fraction) {
					fraction = fraction ? fraction : 0;
					var value = this * 1;
					return value.toFixed(fraction)+"";
				};

        Date.prototype.dateFormat = function(){
            var mon = this.getMonth() + 1;
            var day = this.getDate();
            return this.getFullYear() + "-" + (mon < 10 ? "0" + mon : mon) + "-" + (day < 10 ? "0" + day : day);
        };
        Array.prototype.unique = function () {
          var n = [];
          for (var i = 0; i < this.length; i++)
          {
            if (n.indexOf(this[i]) == -1) n.push(this[i]);
          }
          return n;
        }

        NSearchText.SEARCH_PROXY = function (postData) {
            var realProxy = this.getComponentOption('searchProxy');
            if (realProxy) {
                            return realProxy.call(this, postData);
            } else {
                            return postData;
            }
          };
          NSearchText.SEARCH_PROXY_CALLBACK = function(postData){
            var realProxy = this.getComponentOption('searchProxyCallback');
            if (realProxy) {
                            return realProxy.call(this, postData);
            } else {
                            return postData;
            }
          };
        /**
         * * The figure is accurate to point decimal places.
         * @type {Function}
         */
        $helper.formatNumber = function (point) {
            return function (value) {
                return $helper.formatNumberForLabel(value, point);
            }
        };
        /**
         * format number for view
         * @type {Function}
         */
        $helper.formatNumberForLabel = function (value, point) {
          if (value == null ||  value == undefined  || (value + '').isBlank() || isNaN(parseFloat(value))){
              return "";
          }
                     value = parseFloat(value).toFixed(point);
                     var parts = (value + '').split('.');
                     var integral = parts[0];
                     var fraction;
                     if (point > 0) {
                         if (parts.length > 1) {
                             var zeroNum = parts[1].length > point ? -1 : (point - parts[1].length + 1);
                             fraction = '.' + parts[1] + Array(zeroNum).join('0');
                         } else {
                             fraction = '.' + Array(point + 1).join('0');
                         }
                     } else {
                         fraction = parts.length > 1 ? '.' + parts[1] : '';
                     }

                     var rgx = /(\d+)(\d{3})/;
                     while (rgx.test(integral)) {
                         integral = integral.replace(rgx, '$1' + ',' + '$2');
                     }
                     return (integral + fraction).toString();
        };
        /**
         * This function is to format date.style is format pattern, for example, "DD/MM/YYYY", "DD/MM/YYYY HH:mm:ss"
         * @param value
         * @param style
         * @returns {*}
         */
        $helper.formatDateForTableView = function (value, style) {
            if (value == null || value == "") {
                return;
            }
            return moment(value).format(style);
        };
        /**
         *
         * @param value
         * @param codes
         */
        $helper.formatSelectTreeForTableView = function (value, codes) {
            if (value == null || value == "") {
                return;
            }
            var arrValue;
            for (var i = 0; i < value.length; i++) {
                console.log(codes);
                console.log(codes.getText("1"));
                arrValue = arrValue + codes.getText(value[i]);
            }
            return arrValue;
        }
        /**
         * convert the first word to lower key
         * For example :The resetful service return JSON Key as the first word Upper Case but the codetable for UI need "id" and "text"
         * @type {Function}
         */
        $helper.lowerKeysOfJSON = function (obj) {
            for (var key in obj) {
                var val1 = obj[key];
                if (typeof obj[key] == "object") {
                    $helper.lowerKeysOfJSON(obj[key]);
                }
                delete obj[key];
                key = key.substring(0, 1).toLowerCase() + key.substring(1);
                obj[key] = val1;
            }
            return obj;
        };
        /**
        * format amount
        * right align
        * thousands
        */
        $helper.baseAmount = function(point){
          return {
            comp: {
                type: $pt.ComponentConstants.Text,
                transformer : {
                  model: function (value) {
                  return null == value || undefined == value || (value + '').isBlank() || isNaN(parseFloat(value))  ? "" : parseFloat(value);
                }},
                convertor: function(value) {
                  return $helper.formatNumberForLabel(value, point);
                },
                format: $helper.formatNumber(point)
            },
            css: {comp: 'currency-align-right-text'}
          }
        }
        /**
        * number is between 0 and 9999 or null or not
        *
        */
        $helper.baseNumber = function(){
          return {
            comp: {
              type: $pt.ComponentConstants.Text,
              transformer : {
                model: function (value) {
                  var reg = /^0$|^[1-9][0-9]$/;
                  var newValue = isNaN(value) || (value + '').isBlank() ? "" : value;
                  if(newValue && !reg.test(newValue)){
                    newValue = parseInt(newValue);
                  }
                  return newValue;
                }
              },
              format: function (value) {
                var reg = /^0$|^[1-9][0-9]$/;
                var newValue = isNaN(value) || (value + '').isBlank() ? "" : value;
                if(newValue && !reg.test(newValue)){
                  newValue = parseInt(newValue) + "";
                }
                return newValue;
              },
            },
            css: {comp: 'currency-align-right-text'}
          }

        }
        /**
        * format percentage
        * right align
        *
        */
        $helper.basePercentage = function(point){
          return {
            comp: {
                type: $pt.ComponentConstants.Text,
                transformer : {
                  model: function (value) {
          					return isNaN(value) || (value + '').isBlank() ? "" : (value + '').movePointLeft(2);
          				},
          				view: function (value) {
          					return isNaN(value) || (value + '').isBlank() ? value : (value + '').movePointRight(2);
          				}
                },
                convertor: function(value) {
                    if (value != null && !(value+'').isBlank()) {
                        var newValue = NText.PERCENTAGE.view(value);
                        return $helper.formatNumberForLabel(newValue, point) + ' %';
                    } else {
                        return "";
                    }
                },
                format: $helper.formatNumber(point),
                rightAddon: {
                    text: "%"
                }
              },
              css: {comp: 'currency-align-right-text'}
            }
          }

        $helper.baseYear = function(){
            return {
                comp: {
                    type: $pt.ComponentConstants.Number,
                    transformer : {
                        model: function (value) {
                          var reg = /^0$|^[1-9][0-9]$/;
                          var newValue = isNaN(value) || (value + '').isBlank() ? "" : value;
                          if(newValue && !reg.test(newValue)){
                            newValue = parseInt(newValue);
                          }
                          return newValue;
                        }
                      },
                    convertor: function(value) {
                        if (value != null && !(value+'').isBlank()) {
                            return value+ ' Years';
                        } else {
                            return "";
                        }
                    },
                    format: function (value) {
                        var reg = /^0$|^[1-9][0-9]$/;
                        var newValue = isNaN(value) || (value + '').isBlank() ? "" : value;
                        if(newValue && !reg.test(newValue)){
                          newValue = parseInt(newValue);
                        }
                        return newValue;
                      },
                    rightAddon: {
                        text: "Years"
                    }
                },
                css: {comp: 'currency-align-right-text'}
            }
        }

        $helper.baseDate = function(){
          return {
            comp: {
                type: $pt.ComponentConstants.Date,
                format: "DD/MM/YYYY"
            }
          }
        };

        $helper.baseDateTime = function(){
          return {
            comp : {
              type: $pt.ComponentConstants.Date,
              format: "DD/MM/YYYY HH:mm:ss"
            }
          }
        };

        $helper.validateAmount = function(required,maxAmount,miniAmount){
          return {
            required : required,
            rule: function(model, value, settings) {
              if (value == null) {
                return true;
              } else {
                if (isNaN(value)) {
                  return '%1 must be a numeric value.';
                } else {
                  if(maxAmount && parseFloat(value) > parseFloat(maxAmount)){
                      return '%1 can\'t be large than '+maxAmount+'.';
                  }
                  if(miniAmount && parseFloat(value) > parseFloat(miniAmount)){
                    return '%1 can\'t be small than '+maxAmount+'.';
                  }
                }
                return true;
              }
            }
          }
        };


        $helper.validateAmountLength = function(required, precision, len){
            return {
              required : required,
              rule: function(model, value, settings) {
                if (value == null) {
                  return true;
                } else {
                  if (isNaN(value)) {
                    return '%1 must be a numeric value.';
                  } else if(value){
                	  var parts = (value + '').split('.');
                      var integral = parts[0];
                      var fraction;
                      if(parts.length > 1){
                    	  fraction = parts[1];
                      }
                      var amountLen = (integral? integral.length : 0) + (fraction? fraction.length : 0);
                    if(amountLen > len){
                        return 'Length of amount can\'t be large than '+len+'.';
                    }
                    if(fraction && fraction.length > precision){
                    	return 'Precision of amount can\'t be large than '+precision+'.';
                    }
                  }
                  return true;
                }
              }
            }
          };

          $helper.registerInlinePercentage = function (name, point) {
             NTable.registerInlineEditor(name, {
               comp: {
                 type: {type: $pt.ComponentConstants.Text, label: false}
               },
               base : $helper.basePercentage(point)
             });
             return name;
         };

         $helper.registerInlineAmount = function (name, point) {
            NTable.registerInlineEditor(name, {
              comp: {
                type: {type: $pt.ComponentConstants.Text, label: false}
              },
              base : $helper.baseAmount(point)
            });
            return name;
        };
        $helper.basePartnerSearch = function(roleType){
            return {
                comp: {
                    type: $pt.ComponentConstants.BPSearch,
                    searchTriggerDigits: 6,
                    BusinessRole : roleType
                }
            }
        };
        $helper.registerInlineBPSearch = function(){
          NTable.registerInlineEditor("BPSearch", {
            comp : {
              type : {type: $pt.ComponentConstants.BPSearch, label : false},
              searchTriggerDigits: 6,
              enabled : false
            }
          });
          return "BPSearch";
        };
        $helper.registerInlineAmountUnable = function (point) {
           NTable.registerInlineEditor("Amount", {
             comp: {
               type: {type: $pt.ComponentConstants.Text, label: false},
               enabled : false
             },
             base : $helper.baseAmount(point)
           });
           return "Amount";
       };

    })();

    /**
     * Table Inline Editor and Paging define
     */
    (function () {
        /**
         * The figure is accurate to two decimal places.
         */
        NTable.registerInlineEditor('number', {
            comp: {
                type: {type: $pt.ComponentConstants.Text, label: false},
                format: $helper.formatNumber(2)
            },
            css: {comp: 'currency-align-right-text'}
        });
        NTable.registerInlineEditor('integer', {
              comp: {
                  type:{label:false}
              },
              base:$helper.baseNumber(),
              pos: {width: 12}
        });
        NTable.registerInlineEditor('textarea', {
            comp: {
                type: {type: $pt.ComponentConstants.TextArea,label: false}
            }
        });
		    NTable.registerInlineEditor('percentage', {
            comp: {
                type: {type: $pt.ComponentConstants.Text, label: false},
                convertor: NText.PERCENTAGE,
                rightAddon: {
                    text: '%'
                }
            }
        });
        /**
         * Inline Search define, search digits defined as 6
         */
        NTable.registerInlineEditor('searchEntryItem', {
            comp: {
                type: {type: $pt.ComponentConstants.searchEntryItem, label: false},
                searchTriggerDigits: 6,
                codeTableId: "1004"
            }
        });
        NTable.registerInlineEditor('DateFormate', {
            comp: {
                type:{ type: $pt.ComponentConstants.Date, label: false },
                format: "DD/MM/YYYY"
            }
        });


        NTable.registerInlineEditor('DateFormateForDisable', {
            comp: {
                type:{ type: $pt.ComponentConstants.Date, label: false },
                format: "DD/MM/YYYY",
                enabled: false
            }
        });

        NTable.registerInlineEditor('DateFormateForLabel', {
            comp: {
                type:{ type: $pt.ComponentConstants.Date, label: false },
                format: "DD/MM/YYYY",
                view: true
            }
        });

        /**
         * Restful Service need the first word to UpperCase,
         * this function used to convert the words mapping the interface
         * @param criteria
         * @returns {*}
         * @constructor
         */
        NTable.PAGE_JUMPING_PROXY = function (criteria) {
            criteria.PageIndex = criteria.pageIndex;
            criteria.CountPerPage = criteria.countPerPage;
            criteria.PageCount = criteria.pageCount;
            delete criteria.pageIndex;
            delete criteria.countPerPage;
            delete criteria.pageCount;
            return criteria;
        };
        NTable.PAGE_JUMPING_PROXY_CALLBACK = function (data, dataId) {
            console.log(data);
            var criteriaPropertyName = this.getQuerySettings();
            var convertedData = {};
            convertedData[criteriaPropertyName] = {
                pageIndex: data.PageIndex,
                pageCount: data.PageCount,
                countPerPage: data.CountPerPage
            };
            convertedData[dataId] = data.Rows ? data.Rows : [];
            return convertedData;
        };
        NText.PERCENTAGE = {
            model: function (value) {
              return isNaN(value) || (value + '').isBlank() ? value : (value + '').movePointLeft(2);
            },
            view: function (value) {
              return isNaN(value) || (value + '').isBlank() ? value : (value + '').movePointRight(2);
            }
          };
          $pt.ComponentConstants.CODETABLE_RECEIVER_PROXY = function(receiveData){
            return $helper.lowerKeysOfJSON(receiveData);
          }
          $pt.ComponentConstants.CODETABLE_PARENT_VALUE_KEY  = 'parentId';
    })();

    $page.ControllerInterface = {
        lockInfo: function (resource, done) {
            var _this = this;
            var flag = 1;
            var afterCkeckLock = function (data, textStatus, jqXHR) {
                var CheckModel = data;
                if (data.LockUserId != null && data.LockUserId != undefined) {
                    // lockUserId is not null
                    var lockUserName = data.LockUserName;
                    if (data.LockUserId == data.OpeartId) {
                        flag = 0;
                        done();
                    } else {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['The task is locked for editing by another user ' +lockUserName + '.']
                        });
                        flag = 1;
                    }
                } else {
                    //lockUserId is null
                    if (data.OpeartId != data.OwnerId) {
//This task belongs to user A, are you sure to continue
                        var isContinue = false;
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            messages: ['The task is owned by another user ' + data.OwnerName + '. Are you sure to continue processing this task?'],
                            onConfirm: function () {
                                isContinue = true;
                            },
                            onCancel: function () {
                                isContinue = false;
                               // done(flag);
                            },
                            afterClose: function () {
                                if (isContinue) {
                                    flag = _this.lock(CheckModel);
                                    if(flag==0){
                                        done();
                                    }
                                }
                            }
                        });
                    } else {
                        flag = _this.lock(CheckModel);
                        if(flag==0){
                            done();
                        }
                    }

                }
            }.bind(this);
            $pt.doPost($ri.getRestfulURL("action.common.LockInfo") + '/CheckLock', resource, {
                quiet: false,
                async: false,
                done: afterCkeckLock
            });

        },

        lock: function (resource) {
            var lockFlag = 1;
            var afterLock = function (data, textStatus, jqXHR) {
                console.log(data);
                lockFlag = data.MessageType;
                console.log(data.Message);
                if (lockFlag == 1) {
                    NConfirm.getConfirmModal().show({
                        title: 'System Message',
                        disableClose: true,
                        messages: [data.Message]
                    });
                }
            }.bind(this);
            $pt.doPost($ri.getRestfulURL("action.common.LockInfo") + '/Lock', resource, {
                quiet: false,
                async: false,
                done: afterLock
            });

            return lockFlag;
        },

        unlock: function (resource,done) {

            var afterUnLock = function (data, textStatus, jqXHR) {
                done();
            }.bind(this);
            $pt.doPost($ri.getRestfulURL("action.common.LockInfo") + '/Unlock', resource, {
                quiet: false,
                async: false,
                done: afterUnLock
            });

        },
        getMenus : function(){
            var menus = [];
            var _this = this;
            var url = $ri.getRestfulURL("action.public.getMenus");
            $pt.doGet(url, null, {
                quiet: false,
                async: false,
                done: function(data) {
                    data.forEach(function(item){
                        $helper.lowerKeysOfJSON(item);
                        menus.push(item);
                    });
                }
            });
            return menus;
        },

        exitConfirm: function (readOnly,done) {
            if (!readOnly) {
                var isContinued = false;
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    messages: ['Any unsaved changes will be lost if you exit now'],
                    onConfirm: function () {
                        isContinued = true;
                    },
                    onCancel: function () {
                       isContinued =false;
                    },
                    afterClose: function () {
                       if(isContinued){
                          done();
                       }
                    }
                });
            } else {
               done();
            }
        },

        initializeLogin : function(){
            var _this = this;
            var token = $.sessionStorage.get(constants.CAS_KEY);

            if(token && token != 'undefined'){
                $.when(_this.initializeData()).done(_this.initializeMenus()).done(_this.renderUI());
            }else{
                _this.login(envSetting.LOGIN_URL, window.location.href);
            }

        },

        login: function (casUrl, callbackUrl) {
            var _this = this;
            var currentUrl = window.location.href;
            var callbackUrlParam = "&redirect_uri=" + callbackUrl;
            var login_url = casUrl + constants.authorize_url + callbackUrlParam;

            var result = $pt.getUrlData();
            var code = result.code;
            var token = $.sessionStorage.get(constants.CAS_KEY);

            if ((!token || token == 'undefined') && (!code || code == 'undefined')) {
                window.location.href = login_url;
            } else if (code && code != 'undefined') {
                $.get(casUrl + constants.accessToken_url, {
                    client_id: "key",
                    client_secret: "secret",
                    grant_type: "authorization_code",
                    redirect_uri: callbackUrl,
                    code: code
                }).done(function (response) {
                    console.log(response);
                    $.sessionStorage.set($page.constants.CAS_KEY, response);
                    window.location.href = callbackUrl;
                }).fail(function (response) {
                  NConfirm.getConfirmModal().show({
                      title: failStatus + failMessage,
                      disableClose: true,
                      messages: ['System timeout, please re-login.']
                  });
                  window.close();
                });
            }
        },

        logout: function () {
            // $.sessionStorage.remove($page.constants.MENU_COOKIE_NAME);
            Cookies.set($page.constants.LOGOUT_COOKIE_NAME, true);
            $.sessionStorage.removeAll();
            console.log("$.sessionStorage.get(constants.CAS_KEY)=="+$.sessionStorage.get(constants.CAS_KEY));
            $page.ControllerInterface.initialize();
        },

      initialize: function () {
            var _this = this;
            $.getJSON('/html/configs/service.json', function (data) {
                $.sessionStorage.set('envSetting', data);
                envSetting = data;
                $pt.defineWebContext(envSetting.STATIC_HTML_CONTEXT);
                _this.initializeLogin();
            });
        },

        /**
         * initialize data
         * @returns {boolean}
         */
        initializeData: function () {
        },
        /**
         * initialize menus
         * @returns {boolean}
         */
        initializeMenus: function () {
            var menus = $.sessionStorage.get($page.constants.MENU_COOKIE_NAME);
            if (!menus || menus.length == 0) {
                menus = this.getMenus();
                $.sessionStorage.set($page.constants.MENU_COOKIE_NAME, menus);
            }
            var _this = this;
            var MenuVisitor = jsface.Class({
                replaceUrl: function (menu) {

                    if (menu.url) {
                        var url = menu.url;
                        menu.url = $pt.getURL(url);
                        if (!menu.url && menu.url != null) {
                            menu.func = Object.getPrototypeOf(_this)[url].bind(this);
                        }
                    } else if (menu.children) {
                        menu.children.forEach(this.replaceUrl.bind(this));
                    }
                }
            });
            var menuVisitor = new MenuVisitor();
            menus.forEach(menuVisitor.replaceUrl.bind(menuVisitor));
            this.menus = menus;
            return true;
        },
        /**
         * render UI
         */
        renderUI: function () {
            $.when(this.renderHeader()).done(
                $.when(this.renderContent()).done(
                    this.renderFooter()
                )
            )

        },
        renderContent: function () {
            // do nothing, interface only
        },
        /**
         * render header
         * @param menus
         */
        renderHeader: function (menus) {
            // render page header
            ReactDOM.render(<NPageHeader brand={<image src={$pt.getURL("brand.image")}/>}
                                         brandFunc={this.gotoIndex.bind(this)}
                                         menus={this.menus} side={true}/>,
                document.getElementById("page-header"));
        },
        /**
         * render footer
         */
        renderFooter: function () {
            // render page footer
            ReactDOM.render(<NPageFooter
                name={$pt.getMessage('common.brand')}/>, document.getElementById("page-footer"));
        },
        //logPagePerformance: function() {
        //    if (window.performance) {
        //        //var time = performance.timing;
        //        //console.debug('開始跳轉: ' + (time.redirectStart - time.navigationStart) + '毫秒');
        //        //console.debug('跳轉完畢: ' + (time.redirectEnd - time.redirectStart) + '毫秒');
        //        //console.debug('開始獲取資源: ' + (time.fetchStart - time.redirectEnd) + '毫秒');
        //        //console.debug('DNS查找: ' + (time.domainLookupEnd - time.domainLookupStart) + '毫秒');
        //        //console.debug('TCP連接: ' + (time.connectEnd - time.connectStart) + '毫秒');
        //        //console.debug('發送請求: ' + (time.requestStart - time.connectEnd) + '毫秒');
        //        //console.debug('接收數據: ' + (time.responseEnd - time.responseStart) + '毫秒');
        //        //console.debug('開始裝載DOM: ' + (time.domLoading - time.responseEnd) + '毫秒');
        //        //console.debug('啟動DOM: ' + (time.domInteractive - time.domLoading) + '毫秒');
        //        //console.debug('裝載DOM內容: ' + (time.domContentLoaded - time.domInteractive) + '毫秒');
        //        //console.debug('DOM裝載完畢: ' + (time.domComplete - time.domContentLoaded) + '毫秒');
        //        //console.debug('啟動Onload: ' + (time.loadEventStart - time.domComplete) + '毫秒');
        //        //console.debug('Onload完畢: ' + (time.loadEventEnd - time.loadEventStart) + '毫秒');
        //        //console.debug('總消耗時間: ' + (time.loadEventEnd - time.navigationStart) + '毫秒');
        //    }
        //},
        gotoIndex: function () {
            if (this.isIndexPage === true) {
                return;
            }
            $pt.relocatePage($pt.getURL('index'));
        },
        quitSystem: function () {
            // clear menus cookie
            $.sessionStorage.remove($page.constants.MENU_COOKIE_NAME);
            Cookies.set($page.constants.LOGOUT_COOKIE_NAME, true);
            $pt.relocatePage($pt.getURL('ui.login'));
        },

        clientSearch: function () {
            $pt.relocatePage($pt.getURL('client.search'), {
                type: 'search'
            });
        },
        clientManage: function () {
            $pt.relocatePage($pt.getURL('client.search'), {
                type: 'manage'
            });
        }
    };

}(typeof window !== "undefined" ? window : this));
