/**
 * Created by gang.wang on 10/20/2015.
 */
(function(context){
    var $page= $pt.getService(context,'$page');
    var me = {
        initializeErrorModel : function () {
            //this.errorModel = $pt.createModel($page.model.error);
            return true;
        },
        initializeData : function(){
            console.debug("initializeData");
            //this.codes = $pt.createModel($page.codes);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={$pt.createModel($page.model)} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        convert : function(dataInServer) {
            var codes = $page.codes.ClaimLimitCate.list();
            return codes.map(function(code) {
                var codeId = code.id;
                var item = dataInServer.find(function(item) {
                    return item.typeId == codeId;
                });
                if (item) {
                    item.selected = true;
                    item.label = code.text;
                    return item;
                } else {
                    return {
                        id: null,
                        typeId: codeId,
                        percentage: null,
                        amount: null,
                        selected: false,
                        label: code.text
                    };
                }
            })
        }
    };

    var Controller = jsface.Class($.extend({},$page.ControllerInterface,me));
    $page.control = new Controller();
    //for layout purpose
    //$page.control.initializeErrorModel();
    //$page.control.initialize();
}(typeof window !== "undefined" ? window : this));
