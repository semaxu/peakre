(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        //initialize: function () {
        //},
        initializeData: function() {
            this.model = $pt.createModel($page.model.all);
            return true;
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));