/**
 * Created by Weiping.Wang on 01/16/2016.
 */
(function(context){
    var $page= $pt.getService(context,'$page');
    var me = {
        initializeErrorModel : function () {
            //this.errorModel = $pt.createModel($page.model.error);
            return true;
        },
        initializeData : function(){
            var urlData = $pt.getUrlData();
            this.model = $pt.createModel($page.model);
            this.model.mergeCurrentModel(urlData);
            var _self = this;
            if(this.model.get("OperateId")){
                this.loadCoveredRetrocessionForLog({
                    ContId: this.model.get("ContCompId"),
                    OperateId: this.model.get("OperateId")
                }, false, false, function (data) {
                    _self.model.mergeCurrentModel({
                        CoveredSectionList: data
                    });
                }, null);
            }else{
                this.loadCoveredRetrocession({
                    ContId: this.model.get("ContCompId")
                }, false, false, function (data) {
                    _self.model.mergeCurrentModel({
                        CoveredSectionList: data
                    });
                }, null);
            }
        },
        loadCoveredRetrocession: function (criteria, quiet, async, done, fail) {
            $page.service.loadCoveredRetrocession(criteria, quiet, async, done, fail);
        },
        loadCoveredRetrocessionForLog: function (criteria, quiet, async, done, fail) {
            $page.service.loadCoveredRetrocessionForLog(criteria, quiet, async, done, fail);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));

        }
    };

    var Controller = jsface.Class($.extend({},$page.ControllerInterface,me));
    $page.controller = new Controller();
    //for layout purpose
    //$page.control.initializeErrorModel();
    //$page.control.initialize();
}(typeof window !== "undefined" ? window : this));