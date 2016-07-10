(function (context) {
    var $page = $pt.getService(context, '$page');

    var initial  = {
        //initialize: function () {
        //},
        initializeData: function() {
            this.model = $pt.createModel($page.model);
            this.codes = $pt.createModel($page.codes);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        }
    };
    var restService = {
        doExecute: function () {
            var afterExecute = function (data, textStatus, jqXHR) {

            }.bind(this);
            this.execute(null, false, false, afterExecute);

        },
        execute: function (criteria, quiet, async, done, fail) {
            $page.service.execute(criteria, quiet, async, done, fail);
        },
        doView: function () {
            //var afterView = function (data, textStatus, jqXHR) {
            //
                window.location.href = "revaluationView.html";
            //}.bind(this);
            //this.view(null, false, false, afterView);
        },
        view: function (criteria, quiet, async, done, fail) {
            $page.service.view(criteria, quiet, async, done, fail);
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface,initial,restService));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));