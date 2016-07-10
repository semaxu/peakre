(function (context) {
    var $page = $pt.getService(context, '$page');

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializePageType: function () {
            var type = $pt.getUrlData();
            this.isManageClient = type && type.type == 'manage';
        },
        initializeErrorModel: function () {
            return true;
        },
        initializeData: function () {
            this.model = $pt.createModel($page.model.createBaseModel());
            //var _this = this;
            //var url = _this.model.getCurrentModel().cachedCriteria.url;
            //this.search(url, this.model.getCurrentModel().condition, false, function (data) {
            //    _this.updateSearchResult(data, false);
            //});
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            this.form = ReactDOM.render(<NForm model={this.model} layout={layout}/>,
                document.getElementById('main'));
        },
        reset: function () {
            delete this.model.getCurrentModel().condition;
            this.form.forceUpdate();
            $page.controller.model.mergeCurrentModel({
                condition: {
                    CountPerPage: 10,
                    PageIndex: 1
                }
            });
        },
        doSearch: function () {
            var criteria = $.extend({}, this.model.getCurrentModel());
            delete criteria.queryResult;
            delete criteria.cachedCriteria.pageIndex;
            delete criteria.cachedCriteria.PageCount;

            var _this = this;
            var successFunc = function (data) {
                _this.updateSearchResult(data, true);
            };
            this.search(criteria.cachedCriteria.url, this.model.getCurrentModel().condition, true, successFunc);
        },
        search: function (criteria, quiet, async, done, fail) {
            $page.service.query(criteria, quiet, async, done, fail);
        },
        updateSearchResult: function (data, updateUI) {
            console.log(data);
            this.model.mergeCurrentModel({
                queryResult: data.Rows ? data.Rows : [],
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount
                }
            });
            this.model.getCurrentModel().cachedCriteria = $.extend(true,{},this.model.getCurrentModel().cachedCriteria,this.model.getCurrentModel().condition);
            if (updateUI) {
                this.form.forceUpdate();
            }
        }
    }));

    $page.controller = new Controller();
    $page.controller.initializePageType();
}(typeof window !== "undefined" ? window : this));