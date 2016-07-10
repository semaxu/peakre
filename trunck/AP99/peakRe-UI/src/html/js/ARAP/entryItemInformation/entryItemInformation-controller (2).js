(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.GeneralLedgerId = null;
    var transQuery = {
        //initialize: function () {
        //},
        initializeData: function() {
            this.model = $pt.createModel($page.model);
            this.codes = $pt.createModel($page.codes);
            var _this = this;
            var urlData = $pt.getUrlData();
            var generalLedgerId = urlData.generalLedgerId;
            $page.GeneralLedgerId = generalLedgerId;
            var url = $ri.getRestfulURL("action.arap.GLQuery.queryEntryItemInformation");
            url = url + "/" + generalLedgerId;
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
            $page.service.queryEntryItemInformation(url, criteria, async, done);
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

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, transQuery));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));