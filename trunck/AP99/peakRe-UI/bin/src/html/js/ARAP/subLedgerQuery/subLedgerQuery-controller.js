(function (context) {
    var $page = $pt.getService(context, '$page');

    var transQuery = {
        //initialize: function () {
        //},
        initializeData: function() {
            this.model = $pt.createModel($page.model);
            /*this.model = $pt.createModel({
                condition: {
                    CountPerPage: 10,
                    PageIndex: 1
                },
                cachedCriteria:{},
                SearchResult:[]
             }
            );*/
            this.codes = $pt.createModel($page.codes);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        resetSubLedgerQuery : function(){
        	var restfulURL = this.model.getCurrentModel().cachedCriteria.url;
        	console.info('restfulURL='+restfulURL);
        	delete this.model.getCurrentModel().condition;
        	delete this.model.getCurrentModel().cachedCriteria;
            this.form.forceUpdate();
            $page.controller.model.mergeCurrentModel({
                condition: {
                    CountPerPage: 10,
                    PageIndex: 1
                },
                cachedCriteria:{
                	countPerPage: 10,
                    url:restfulURL
                }
            });
        },
        searchSubLedgerQuery:function(){
            var searchCondition = this.model.getCurrentModel().condition;
            console.info(searchCondition);
            
            var criteria = $.extend({}, this.model.getCurrentModel());
            console.info(criteria);
            for(var p in criteria){
            	console.info('prop: '+p + "=" + criteria[p]);
            }
            var _this = this;
            var successFunc = function (data) {
            	_this.updateSearchResult(data, true);
            };
            this.search(criteria.cachedCriteria.url, searchCondition, true, successFunc);
        },
        search: function (url, criteria, async, done) {
            $page.service.searchSubLedgerQuery(url, criteria, async, done);
        },
        /*updateSearchResult: function (data, updateUI) {
            console.log("############updateSubLedgerSearchResult###################");
            console.log(data);
            var _this = this;
            this.model.mergeCurrentModel({
                SearchResult: data
            });
            if (updateUI) {
                _this.form.forceUpdate();
            }
        }*/
        updateSearchResult: function (data, updateUI) {
            console.info(data);
            this.model.mergeCurrentModel({
            	SearchResult: data.Rows?data.Rows:[],
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount,
                    countPerPage: data.CountPerPage
                }
            });
            var _this = this;
            this.model.getCurrentModel().cachedCriteria = $.extend(true,{},this.model.getCurrentModel().cachedCriteria,this.model.getCurrentModel().condition);
            if (updateUI) {
            	_this.form.forceUpdate();
            }
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, transQuery));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));