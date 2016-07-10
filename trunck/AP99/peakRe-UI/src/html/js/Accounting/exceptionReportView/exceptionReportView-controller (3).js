(function (context) {
    var $page = $pt.getService(context, '$page');

    var initial = {

        initializeData: function () {
            this.model = $pt.createModel($page.model.createModel());
            this.codes = $pt.createModel($page.codes);
            var urlData = $pt.getUrlData();
            var FNQuarter = urlData.FNQuarter;
            var FNYear = urlData.FNYear;
            var _this = this;
                var loadContract = {FNQuarter: FNQuarter,FNYear: FNYear};
                var toLoad = function (data, textStatus, jqXHR){
                    console.log("AA");
                    console.log(data);
                    this.model.mergeCurrentModel({
                        ExceptionContractList: data.Rows !== undefined ? data.Rows : []

                    });
                    if (this.form) {
                        this.form.forceUpdate();
                    }
                }.bind(this);
                this.load(loadContract, false, false, toLoad);

        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
    };
    var restService = {
        load: function (criteria, quiet, async, done, fail) {
            $page.service.load(criteria, quiet, async, done, fail);
        },
        doReview: function () {
            var _this = this;
            var afterReview = function (data, textStatus, jqXHR) {

            }.bind(this);
            console.log("BB");
            console.log(this.model.getCurrentModel());
            var updateData = $.extend({},  this.model.getCurrentModel());
            this.review(updateData, false, false, afterReview);
        },
        review: function (criteria, quiet, async, done, fail) {
            $page.service.review(criteria, quiet, async, done, fail);
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface,initial,restService));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));