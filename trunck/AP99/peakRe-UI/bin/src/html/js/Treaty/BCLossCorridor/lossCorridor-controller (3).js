/**
 * Create By Elvira.Du 11/09/2015
 */
(function(context){
    var $page= $pt.getService(context,'$page');
    var me = {
        initializeErrorModel : function () {
            return true;
        },

        renderContent: function () {
            console.debug('get in render content');
            var layout1 = $pt.createFormLayout($.extend(true,{},$page.layout));
            var form = <NForm model={$pt.createModel($page.model.all)} layout={layout1}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
    };

    var Controller = jsface.Class($.extend({},$page.ControllerInterface,me));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));