/**
 * Created by Gordon.Cao on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializeErrorModel : function () {
            return true;
        },
        initializeData: function () {
            this.model = $pt.createModel($page.model);
            var _this = this;
            var url = _this.model.getCurrentModel().cachedCriteria.url;
            var searchModel = _this.model.getCurrentModel().condition;

            var successFunc = function (data) {
                _this.updateSearchResult(data, false);
            };
            _this.search(url, searchModel, false, successFunc);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            this.form = ReactDOM.render(<NForm model={this.model} layout={layout}/>,document.getElementById('main'));
        },
        reset: function(){
            this.model.getCurrentModel().condition = {};
            this.form.forceUpdate();
            $page.controller.model.mergeCurrentModel({
                condition: {
                    CountPerPage: 10,
                    PageIndex: 1
                }
            });
        },
        searchBankAccount: function () {
            var criteria = $.extend({}, this.model.getCurrentModel());
            delete criteria.searchTable;
            delete criteria.cachedCriteria.pageIndex;
            delete criteria.cachedCriteria.PageCount;

            var _this = this;
            var successFunc = function (data) {
                _this.updateSearchResult(data, true);
            };
            this.search(criteria.cachedCriteria.url, this.model.getCurrentModel().condition, true, successFunc);
        },
        search: function (url, criteria, async, done) {
            $page.service.queryBankAccount(url, criteria, async, done);
        },
        checkInputValue:function(dialogModel){console.info(dialogModel);
            var branch = dialogModel.get("Branch");
            if (branch == undefined || branch == "") {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Branch is required']
                });
                return false;
            }
            var bankAccountNumber = dialogModel.get("BankAccountNumber");
            if (bankAccountNumber == undefined || bankAccountNumber == "") {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Bank Account Number is required']
                });
                return false;
            }
            if (bankAccountNumber.length > 50) {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Length of Bank Account Number can\'t be large than 50']
                });
                return false;
            }
            var bankAccountName = dialogModel.get("BankAccountName");
            if (bankAccountName == undefined || bankAccountName == "") {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Bank Account Name is required']
                });
                return false;
            }
            var currency = dialogModel.get("Currency");
            if (currency == undefined || currency == "") {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Currency is required']
                });
                return false;
            }
            var accountType = dialogModel.get("AccountType");
            if (accountType == undefined || accountType == "") {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Account Type is required']
                });
                return false;
            }
            var bank = dialogModel.get("Bank");
            if (bank == undefined || bank == "") {
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Bank is required']
                });
                return false;
            }

            return true;
        },
        saveBankAccount: function(model){
            var _this = this;console.info(model);
            var bResult = false;
            $page.service.saveBankAccount(model, false, false,
                function(data) {
                    if (data.Status == 1){
                        bResult = true;
                        NConfirm.getConfirmModal().show({
                            title:"",
                            disableClose: true,
                            close:true,
                            messages: ['Bank Account saved successfully'],
                            afterClose:function(){
                                _this.searchBankAccount(_this.model.getCurrentModel().condition);
                            }
                        });
                    }
                    else if (data.Status == 2){
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ["The same bank account exists in the system"]
                        });
                    }
                },
                function(jqXHR, textStatus, errorThrown) {

                }
            );
            return bResult;
        },
        deleteBankAccount: function(model){
            var bResult = false;
            var _this = this;
            NConfirm.getConfirmModal().show({
                title:"",
                close: false,
                messages: ['Are you sure to delete this bank account?'],
                onConfirm:function(){
                    bResult = true;
                },
                afterClose: function(){
                    if (!bResult) return;
                    var postData = {
                        AccountId: model.AccountId
                    };
                    $page.service.deleteBankAccount(postData, false, false,
                        function(data) {
                            if (data.Status == 1){
                                NConfirm.getConfirmModal().show({
                                    title:"",
                                    disableClose: true,
                                    close:true,
                                    messages: ['Delete Bank Account successfully'],
                                    afterClose:function(){
                                        _this.searchBankAccount(_this.model.getCurrentModel().condition);
                                    }
                                });
                            }
                            else if (data.Status == 5){
                                NConfirm.getConfirmModal().show({
                                    title: "",
                                    disableClose: true,
                                    messages: ['The bank account is in-use; the delete operation is denied']
                                });
                            }
                        },
                        function(jqXHR, textStatus, errorThrown) {

                        }
                    );
                }
            });
        },
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
            this.model.getCurrentModel().cachedCriteria = $.extend(true,{},this.model.getCurrentModel().cachedCriteria,this.model.getCurrentModel().condition);
            if (updateUI) {
                _this.form.forceUpdate();
            }
        }
    }));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));