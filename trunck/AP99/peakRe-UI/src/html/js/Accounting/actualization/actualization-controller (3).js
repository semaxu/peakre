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
            this.model.mergeCurrentModel({
                resultTable: data.Rows ? data.Rows : [],
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount
                }
            });
            if (updateUI) {
                this.form.forceUpdate();
            }
        },
        cancelSelectio: function () {
            var resultTable = this.model.getCurrentModel().resultTable;
            resultTable.forEach(function (resultTable) {
                resultTable.selected = false;
            });
            this.model.mergeCurrentModel({
                resultTable: resultTable
            });
            this.form.forceUpdate();
        },
        selectAll: function () {
            var resultTable = this.model.getCurrentModel().resultTable;
            resultTable.forEach(function (resultTable) {
                if (resultTable.Actualized == 0) {
                    resultTable.selected = true;
                }
            });
            this.model.mergeCurrentModel({
                resultTable: resultTable
            });
            this.form.forceUpdate();
        },
        doActualization: function () {
            //var criteria = this.model.getCurrentModel().condition;
            var array = [];
            var _this = this;
            var resultTable = this.model.getCurrentModel().resultTable;
            resultTable.forEach(function (resultTable) {
                if (resultTable.selected) {
                    array.push(resultTable.ContCompId);
                }
            });
            var successFunc = function (data) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Success'],
                    onConfirm: function () {
                        _this.doSearch();
                    }
                });
            };
            var failFun = function () {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Save Failure'],
                    onConfirm: function () {
                        _this.doSearch();
                    }
                });
            };
            if (array.length == 0) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Please Select Contract']
                });
            } else {
                //if(!criteria.Exceeding){
                //    NConfirm.getConfirmModal().show({
                //        title: 'There are not N+8 quarters after the contract is in-force, are you sure to continue?',
                //        messages: ['Save Failure'],
                //        onConfirm: function () {
                //            $page.service.actualize(array.toString(), successFunc, failFun);
                //        }
                //    });
                //}
                $page.service.actualize(array.toString(), successFunc, failFun);
            }
        }
    }));

    $page.controller = new Controller();
    $page.controller.initializePageType();
}(typeof window !== "undefined" ? window : this));