/**
 * Created by Gordon.Cao on 10/20/2015.
 */
(function(context){
    var $page= $pt.getService(context,'$page');
    var me = {
        initializeErrorModel : function () {
            return true;
        },
        initializeData : function(){
            this.model = $pt.createModel({
                    condition: {
                        CountPerPage: 10,
                        PageIndex: 1
                    },
                    results:[],
                    lastCriteria:{}
                }
            );
            this.searchExchangeRate(this.model.getCurrentModel().condition , false, false,
                function (data) {
                    this.updateSearchResult(data, true);
                }, function (jqXHR, textStatus, errorThrown) {

                }
            );
        },
        reset: function(){
            this.model.getCurrentModel().condition = {};
            this.form.forceUpdate();
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            this.form = ReactDOM.render(<NForm model={this.model} layout={layout}/>,document.getElementById('main'));
        },
        checkInputValue:function(dialogModel){
            var baseCurrency = dialogModel.BaseCurrency;
            if (baseCurrency == undefined || baseCurrency == "" || null == baseCurrency) {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['From Currency is required']
                });
                return false;
            }
            var exchangeCurrency = dialogModel.ExchangeCurrency;
            if (exchangeCurrency == undefined || exchangeCurrency == "" || null == exchangeCurrency) {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Exchange Currency is required']
                });
                return false;
            }
            var validDate = dialogModel.ValidDate;
            if (validDate == undefined || validDate == "" || null == validDate) {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Valid Date is required']
                });
                return false;
            }
            var expireDate = dialogModel.ExpireDate;
            if (expireDate == undefined || expireDate == "" || null == expireDate) {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Expiry Date is required']
                });
                return false;
            }
            var array1 = validDate.split("/");
            validDate = new Date(array1[2] + "/" + array1[1] + "/" + array1[0]);
            var array2 = expireDate.split("/");
            expireDate = new Date(array2[2] + "/" + array2[1] + "/" + array2[0]);
            if (validDate > expireDate) {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ["Valid Date must not be later than Expiry Date"]
                });
                return false;
            }
            var exchangeRate = dialogModel.ExchangeRate;
            if (exchangeRate == undefined || exchangeRate == "" || null == exchangeRate || isNaN(exchangeRate)) {
            	dialogModel.ExchangeRate = "";
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Exchange Rate is required']
                });
                return false;
            }
            if (exchangeRate <= 0) {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['The exchange rate should be positive']
                });
                return false;
            }
            exchangeRate = parseFloat(exchangeRate).toFixed(6);
            if (null != exchangeRate && "" != exchangeRate){
                var array =  exchangeRate.split(".");
                if (null != array && array.length == 2){
                    if (array[0].length > 6){
                        //dialogModel.ExchangeRate = "";
                         NConfirm.getConfirmModal().show({
                             title:"",
                             disableClose: true,
                             messages: ['The Exchange Rate exceeds']
                         });
                        return false;
                    }
                }
            }
            var rateType = dialogModel.RateType;
            if (rateType == undefined || rateType == "" || null == rateType) {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Rate Type is required']
                });
                return false;
            }
            return true;
        },
        searchExchangeRate:function(model){
            var criteria = $.extend({}, this.model.getCurrentModel());
            var _this = this;
            delete criteria.searchTable;
            delete criteria.cachedCriteria;
            $page.service.searchExchangeRate(model, false, true,
                function(data, textStatus, jqXHR) {
                    _this.updateSearchResult(data, true);
                },
                function(jqXHR, textStatus, errorThrown) {

                }
            );
        },
        doPageJump: function(criteria, table) {
            var _this = this;
            var newCondition = $.extend({}, this.model.getCurrentModel().condition,criteria);
            this.searchExchangeRate(newCondition, false, true, function(data) {
                _this.updateSearchResult(data, true);
                table.forceUpdate();
            });
        },
        saveExchangeRate: function(model){
            var _this = this;
            var bResult = false;
            $page.service.saveExchangeRate(model, false, false,
                function(data) {
                    var info = "";
                    if (data.Status == 1){
                        _this.searchExchangeRate(_this.model.getCurrentModel().condition);
                        bResult = true;
                        info = "Create Exchange Rate Successfully";
                    }
                    else if (data.Status == 2){
                        info = "The same exchange rate record exists in the system";
                    }
                    else if (data.Status == 3){
                        info = "The exchange rate period overlaps other existing data";
                    }
                    else if (data.Status == 4){
                        info = "Value of field is required";
                    }
                    NConfirm.getConfirmModal().show({
                        title: "",
                        disableClose: true,
                        messages: [info]
                    });
                },
                function(jqXHR, textStatus, errorThrown) {
                    console.error(errorThrown);
                }
            );
            return bResult;
        },
        updateExchangeRate:function(model){
            var _this = this;
            var bResult = false;
            $page.service.updateExchangeRate(model, false, false,
                function(data) {
                    var info = "";
                    if (data.Status == 1){
                        _this.searchExchangeRate(_this.model.getCurrentModel().condition);
                        bResult = true;
                        info = "Update Exchange Rate Successfully";
                    }
                    else if (data.Status == 2){
                        info = "The same exchange rate record exists in the system";
                    }
                    else if (data.Status == 3){
                        info = "The exchange rate period overlaps other existing data";
                    }
                    else if (data.Status == 4){
                        info = "Value of field is required";
                    }
                    NConfirm.getConfirmModal().show({
                        title: "",
                        disableClose: true,
                        messages: [info]
                    });
                },
                function(jqXHR, textStatus, errorThrown) {
                    console.error(errorThrown);
                }
            );
            return bResult;
        },
        deleteExchangeRate:function(model){
            var bResult = false;
            var _this = this;
            NConfirm.getConfirmModal().show({
                title:"",
                close: false,
                messages: ['Are you sure to delete this exchange rate?'],
                onConfirm:function(){
                    bResult = true;
                },
                afterClose: function(){
                    if (!bResult) return;
                    var postData = {
                        RateId: model.RateId
                    };
                    $page.service.deleteExchangeRate(postData, false, false,
                        function(data) {
                            if (data.Status == 1){
                                NConfirm.getConfirmModal().show({
                                    title:"",
                                    disableClose: true,
                                    close:true,
                                    messages: ['Delete Exchange Rate Successfully'],
                                    afterClose:function(){
                                        _this.searchExchangeRate(_this.model.getCurrentModel().condition);
                                    }
                                });
                            }
                            else {
                                NConfirm.getConfirmModal().show({
                                    title: "",
                                    disableClose: true,
                                    messages: ['The exchange rate cannot be deleted']
                                });
                            }
                        },
                        function(jqXHR, textStatus, errorThrown) {

                        }
                    );
                }
            });
        },
        /*invalidExchangeRate: function(model){
            if (model.Status == "2"){
                alert("This status is Invalid, Don't change status!");
                return;
            }
            var postData = {
                RateId: model.RateId
            };
           $page.service.invalidExchangeRate(postData, false, false,
                function(data, textStatus, jqXHR) {

                },
                function(jqXHR, textStatus, errorThrown) {

                }
            );

            this.searchExchangeRate(this.model.getCurrentModel().condition);
        },*/
        updateSearchResult: function (data, updateUI) {
            var _this = this;
            this.model.mergeCurrentModel({
                searchTable: data.Rows?data.Rows:[],
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount,
                    countPerPage: data.CountPerPage
                }
            });
            if (updateUI) {
                _this.form.forceUpdate();
            }
        },
        submitData: function() {
            var uploadExchangeRateUrl = $ri.getRestfulURL("action.arap.exchangeRate.uploadExchangeRate");
            $pt.doPost(uploadExchangeRateUrl, this.model.getCurrentModel(), {
                done: function() {
                    NConfirm.getConfirmModal().show({
                        title: '',
                        disableClose: true,
                        messages: ['Upload Exchange Rate Successfully']
                    });

                },
                fail: function() {
                    NConfirm.getConfirmModal().show({
                        title: '',
                        disableClose: true,
                        messages: ['Upload Exchange Rate Failed']
                    });
                }
            });
        }
    };

    var Controller = jsface.Class($.extend({},$page.ControllerInterface,me));
    $page.controller = new Controller();
    //for layout purpose
    //$page.control.initializeErrorModel();
    //$page.control.initialize();
}(typeof window !== "undefined" ? window : this));