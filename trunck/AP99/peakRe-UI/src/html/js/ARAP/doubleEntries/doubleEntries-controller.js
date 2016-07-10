(function (context) {
    var $page = $pt.getService(context, '$page');
   // $page.GLAccount = null;
    $page.Type = null;
    var doubleEntriesQuery = {
        //initialize: function () {
        //},
        initializeData: function() {
            this.model = $pt.createModel($page.model);
            this.codes = $pt.createModel($page.codes);
            var _this = this;
            var urlData = $pt.getUrlData();
            var mappingEntryId = urlData.mappingEntryId;
            //$page.GLAccount = urlData.GLAccount;
            $page.Type = urlData.Type;
            var url = $ri.getRestfulURL("action.arap.GLQuery.queryDoubleEntries");
            url = url + "/" + mappingEntryId;
            var successFunc = function (data) {
                _this.updateSearchResult(data, false);
            };
            _this.search(url, null, false, successFunc);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        search: function (url, criteria, async, done) {
            $page.service.queryDoubleEntries(url, criteria, async, done);
        },
        updateSearchResult: function (data, updateUI) {
            console.log("############updateSearchResult###################");
            console.log(data);
            var _this = this;
            this.model.mergeCurrentModel({
                SearchResult: data
            });
            if (updateUI) {
                _this.form.forceUpdate();
            }
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, doubleEntriesQuery));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));