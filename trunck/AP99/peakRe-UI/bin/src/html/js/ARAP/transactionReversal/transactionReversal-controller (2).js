(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        //initialize: function () {
        //},
        initializeData: function () {
            this.model = $pt.createModel($page.model,$page.validator);
            var afterLoadCurrentUser = function (data, textStatus, jqXHR) {
                console.log(data);
                this.model.set("RequestedBy", data.UserName);
            }.bind(this);
            $page.service.loadCurrentUserCodes(null, false, false, afterLoadCurrentUser);
            this.model.addPostChangeListener("Condition_SettleDateFrom", function (evt) {
                var settleDateFrom = evt.model.get("Condition_SettleDateFrom");
                if (undefined != settleDateFrom && "" != settleDateFrom) {
                    settleDateFrom = moment(settleDateFrom).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (settleDateFrom > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['The Value Date from cannot later than system date.']
                        });
                        evt.model.set("Condition_SettleDateFrom", "");
                    }
                }
            });
            this.model.addPostChangeListener("Condition_SettleDateTo", function (evt) {
                var settleDateTo = evt.model.get("Condition_SettleDateTo");
                if (undefined != settleDateTo && "" != settleDateTo) {
                    settleDateTo = moment(settleDateTo).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (settleDateTo > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['The Value Date To cannot later than system date.']
                        });
                        evt.model.set("Condition_SettleDateTo", "");
                    }
                }
            });
            this.model.addPostChangeListener("Condition_TransDateFrom", function (evt) {
                var transDateFrom = evt.model.get("Condition_TransDateFrom");
                if (undefined != transDateFrom && "" != transDateFrom) {
                    transDateFrom = moment(transDateFrom).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (transDateFrom > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['The Operation Date From cannot later than system date.']
                        });
                        evt.model.set("Condition_TransDateFrom", "");
                    }
                }
            });
            this.model.addPostChangeListener("Condition_TransDateTo", function (evt) {
                var transDateTo = evt.model.get("Condition_TransDateTo");
                if (undefined != transDateTo && "" != transDateTo) {
                    transDateTo = moment(transDateTo).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (transDateTo > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['The Operation Date To cannot later than system date.']
                        });
                        evt.model.set("Condition_TransDateTo", "");
                    }
                }
            });
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        resetTransactionReversal: function(){
        	var restfulURL = this.model.getCurrentModel().cachedCriteria.url;
        	delete this.model.getCurrentModel().Condition;
        	delete this.model.getCurrentModel().cachedCriteria;
            this.form.forceUpdate();
            $page.controller.model.mergeCurrentModel({
                Condition: {
                    CountPerPage: 10,
                    PageIndex: 1
                },
                cachedCriteria:{
                	countPerPage: 10,
                    url:restfulURL
                }
            });

        },
        searchTransactionReversal:function(model){
        	console.log("############searchTransactionReversal###################");
            var searchCondition = this.model.getCurrentModel().Condition;
            var criteria = $.extend({}, this.model.getCurrentModel());
            var _this = this;
            var successFunc = function (data) {
            	_this.updateSearchResult(data, true);
            };
            this.search(criteria.cachedCriteria.url, searchCondition, true, successFunc);
        },
        search: function (url, criteria, async, done) {
            $page.service.searchTransactionReversal(url, criteria, async, done);
        },
        updateSearchResult: function (data, updateUI) {
            console.log("############updateSearchResult###################");
            console.log(data);
            this.model.mergeCurrentModel({
            	TransactionList: data.Rows?data.Rows:[],
                        cachedCriteria: {
                            pageIndex: data.PageIndex,
                            pageCount: data.PageCount,
                            countPerPage: data.CountPerPage
                        }
            });
            var _this = this;
            this.model.getCurrentModel().cachedCriteria = $.extend({},_this.model.getCurrentModel().cachedCriteria,_this.model.getCurrentModel().Condition);
            var cc = this.model.getCurrentModel().cachedCriteria;
            if (updateUI) {
                _this.form.forceUpdate();
            }
        },
        submitTransactionReversal:function(){
            if (this.checkTransactionReversal() == false){
                return false;
            }
            delete this.model.getCurrentModel().cachedCriteria;
            $page.service.submitTransactionReversal(this.model.getCurrentModel(), false, true,
                function(data, textStatus, jqXHR) {
            		console.info("--reversal status: "+data.status + " message:"+data.status);
            		var status = data.Status;
            		var message = data.Message;
            		if(status == 1){
            			NConfirm.getConfirmModal().show({
            				title:"",
            				disableClose: true,
            				messages: 'Reversal is processed successfully',
            				afterClose:function(){
            					window.location.href = $pt.getURL('ui.payment.transactionReversal');
            				}
            			});
            		}else{
            			NConfirm.getConfirmModal().show({
            				title:"Reverse Fail",
            				disableClose: true,
            				messages: message
            			});
            		}

                },
                function(jqXHR, textStatus, errorThrown) {
                	console.info("--Submit reversal fail!");
                	NConfirm.getConfirmModal().show({
                        title:"Submit reversal fail",
                        disableClose: true,
                        messages: [errorThrown]/*,
                        afterClose:function(){
                            window.location.href = $pt.getURL('ui.payment.transactionReversal');
                        }*/
                    });
                }
            );
        },
        checkTransactionReversal:function(){
            var dataModel = this.model;
            var transList = dataModel.get("TransactionList");
            if (undefined == transList || null == transList || transList.length == 0){
                NConfirm.getConfirmModal().show({
                    title: "",
                    disableClose: true,
                    messages: ["Please Search Transaction Reversal first"]
                });
                return false;
            }
            var checkResult = false;
            for (var i=0;i<transList.length;i++){
                if (transList[i].Selected == true){
                    checkResult = true;
                    break;
                }
            }
            if (!checkResult){
                NConfirm.getConfirmModal().show({
                    title: "",
                    disableClose: true,
                    messages: ["Please select one record at least"]
                });
                return false;
            }
            var reversalReason = dataModel.get("ReversalReason");
            if (undefined == reversalReason || null == reversalReason || "" == reversalReason){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Reversal Reason is required']
                });
                return false;
            }
            var requestedBy = dataModel.get("RequestedBy");
            if (undefined == requestedBy || null == requestedBy || "" == requestedBy){
                NConfirm.getConfirmModal().show({
                    title:"",
                    disableClose: true,
                    messages: ['Requested by is required']
                });
                return false;
            }
            return true;
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));