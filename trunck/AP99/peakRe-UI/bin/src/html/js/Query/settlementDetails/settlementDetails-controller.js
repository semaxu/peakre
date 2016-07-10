/**
 * Created by ammon.zhou on 4/1/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    var transQuery = {
        //initialize: function () {
        //},
        initializeData: function() {
            this.model = $pt.createModel($page.model);
            this.codes = $pt.createModel($page.codes);
            var urldata = $pt.getUrlData();
            this.model.mergeCurrentModel(urldata);
            this.load();
            this.model.addPostChangeListener("SettleDateFrom", function (evt) {
                var settleDateFrom = evt.model.get("SettleDateFrom");
                if (undefined != settleDateFrom && "" != settleDateFrom) {
                    settleDateFrom = moment(settleDateFrom).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (settleDateFrom > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['Collection/Payment Date from cannot later than system date.']
                        });
                        evt.model.set("SettleDateFrom", nowDate);
                    }
                }
            });
            this.model.addPostChangeListener("SettleDateTo", function (evt) {
                var settleDateTo = evt.model.get("SettleDateTo");
                if (undefined != settleDateTo && "" != settleDateTo) {
                    settleDateTo = moment(settleDateTo).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (settleDateTo > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['Collection/Payment Date to cannot later than system date.']
                        });
                        evt.model.set("SettleDateTo", nowDate);
                    }
                }
            });
            this.model.addPostChangeListener("TransDateFrom", function (evt) {
                var transDateFrom = evt.model.get("TransDateFrom");
                if (undefined != transDateFrom && "" != transDateFrom) {
                    transDateFrom = moment(transDateFrom).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (transDateFrom > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['Transaction Date from cannot later than system date.']
                        });
                        evt.model.set("TransDateFrom", nowDate);
                    }
                }
            });
            this.model.addPostChangeListener("TransDateTo", function (evt) {
                var transDateTo = evt.model.get("TransDateTo");
                if (undefined != transDateTo && "" != transDateTo) {
                    transDateTo = moment(transDateTo).format("YYYY-MM-DD");
                    var nowDate = moment(new Date()).format("YYYY-MM-DD");
                    if (transDateTo > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: "",
                            disableClose: true,
                            messages: ['Transaction Date to cannot later than system date.']
                        });
                        evt.model.set("TransDateTo", nowDate);
                    }
                }
            });
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        load: function (criteria, quiet, async, done, fail){
            var _this = this;
            $page.service.load(_this.model.get("FeeIdArray"), false, false, function(data){
                _this.model.mergeCurrentModel({SearchResult: data});
                if(_this.form){
                    _this.form.forceUpdate();
                }
            },function(){
            });
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, transQuery));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));