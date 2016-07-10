(function (context) {
    var $page = $pt.getService(context, '$page');

    var transQuery = {
        //initialize: function () {
        //},
        initializeData: function() {
            this.model = $pt.createModel($page.model);
            this.codes = $pt.createModel($page.codes);
            this.model.addPostChangeListener("SettleDateFrom", function (evt) {
                var settleDateFrom = evt.model.get("SettleDateFrom");
                if (undefined != settleDateFrom && "" != settleDateFrom) {
                    settleDateFrom = moment(settleDateFrom).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (settleDateFrom > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['Collection/Payment Date from cannot later than system date.']
                        });
                        evt.model.set("SettleDateFrom", nowDate);
                    }
                }
            });
            this.model.addPostChangeListener("SettleDateTo", function (evt) {
                var settleDateTo = evt.model.get("SettleDateTo");
                if (undefined != settleDateTo && "" != settleDateTo) {
                    settleDateTo = moment(settleDateTo).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (settleDateTo > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['Collection/Payment Date to cannot later than system date.']
                        });
                        evt.model.set("SettleDateTo", nowDate);
                    }
                }
            });
            this.model.addPostChangeListener("TransDateFrom", function (evt) {
                var transDateFrom = evt.model.get("TransDateFrom");
                if (undefined != transDateFrom && "" != transDateFrom) {
                    transDateFrom = moment(transDateFrom).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (transDateFrom > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['Transaction Date from cannot later than system date.']
                        });
                        evt.model.set("TransDateFrom", nowDate);
                    }
                }
            });
            this.model.addPostChangeListener("TransDateTo", function (evt) {
                var transDateTo = evt.model.get("TransDateTo");
                if (undefined != transDateTo && "" != transDateTo) {
                    transDateTo = moment(transDateTo).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (transDateTo > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['Transaction Date to cannot later than system date.']
                        });
                        evt.model.set("TransDateTo", nowDate);
                    }
                }
            });
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        resetTransactionQuery : function(){
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
        searchTransactionQuery:function(model){
            var searchCondition = this.model.getCurrentModel().Condition;
            
            var criteria = $.extend({}, this.model.getCurrentModel());
            var _this = this;
            var successFunc = function (data) {
            	_this.updateSearchResult(data, true);
            };
            this.search(criteria.cachedCriteria.url, searchCondition, true, successFunc);
        },
        search: function (url, criteria, async, done) {
            $page.service.searchTransactionQuery(url, criteria, async, done);
        },
        updateSearchResult: function (data, updateUI) {
            console.log("############updateSearchResult###################");
            console.log(data);
            //console.log(this.model);
            this.model.mergeCurrentModel({
            	SearchResult: data.Rows?data.Rows:[],
                        cachedCriteria: {
                            pageIndex: data.PageIndex,
                            pageCount: data.PageCount,
                            countPerPage: data.CountPerPage
                        }
                    });
            var _this = this;
            this.model.getCurrentModel().cachedCriteria = $.extend(true,{},this.model.getCurrentModel().cachedCriteria,this.model.getCurrentModel().Condition);
            
            if (updateUI) {
                _this.form.forceUpdate();
            }
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, transQuery));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));