/**
 * Created by Gordon.Cao on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializePageType: function () {

        },
        initializeErrorModel: function () {
            return true;
        },
        initializeData: function () {
            this.model = $pt.createModel($page.model.createModel());
            var urlData = $pt.getUrlData();
            var documentId = urlData.documentId;

            var _this = this;
            this.doSearch();
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        doSearch: function () {
            var criteria = $.extend({}, this.model.getCurrentModel());
            delete criteria.searchResult;
            delete criteria.cachedCriteria.pageIndex;
            delete criteria.cachedCriteria.PageCount;
            console.log(criteria.cachedCriteria.url);
            var _this = this;
            var qureyReturn = function(data, textStatus, jqXHR){
                _this.updateSearchResult(data, true);
            }.bind(this);
            $page.service.query(criteria.cachedCriteria.url,_this.model.getCurrentModel().condition , false, true,qureyReturn);
        },

        updateSearchResult: function (data, updateUI) {
            console.log(data);
            this.model.mergeCurrentModel({
                documentTable: data.Rows ? data.Rows: [],
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount,
                    countPerPage: data.CountPerPage
                }
            });
            this.model.getCurrentModel().cachedCriteria = $.extend(true,{},this.model.getCurrentModel().cachedCriteria,this.model.getCurrentModel().condition);
            if (updateUI) {
                this.form.forceUpdate();
            }
        },

        viewFile:function(model, rowModel, layout){
            window.open("test.txt");
        }


    }));

    $page.controller = new Controller();
    $page.controller.initializePageType();

}(typeof window !== "undefined" ? window : this));
