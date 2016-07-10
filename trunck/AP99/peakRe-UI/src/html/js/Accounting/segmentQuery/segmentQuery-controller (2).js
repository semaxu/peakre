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
            this.model = $pt.createModel($page.model);
            var _this = this;
            var url = _this.model.getCurrentModel().cachedCriteria.url;
            var searchModel = _this.model.getCurrentModel().condition;

            var successFunc = function (data) {
                _this.updateSearchResult(data, false);
            };
            this.search(url, searchModel, false, successFunc);
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
            delete criteria.segmentTable;
            delete criteria.cachedCriteria.pageIndex;
            delete criteria.cachedCriteria.PageCount;

            var _this = this;
            var successFunc = function (data) {
                _this.updateSearchResult(data, true);
            };
            this.search(criteria.cachedCriteria.url, this.model.getCurrentModel().condition, true, successFunc);
        },
        search: function (url, criteria, async, done) {
            $page.service.query(url, criteria, async, done);
        },
        updateSearchResult: function (data, updateUI) {
            $page.controller.model.mergeCurrentModel({
                segmentTable: data.Rows ? data.Rows : [],
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount
                }
            });
            if (updateUI) {
                $page.controller.form.forceUpdate();
            }
        }
    }));

    $page.controller = new Controller();
    $page.controller.initializePageType();

}(typeof window !== "undefined" ? window : this));