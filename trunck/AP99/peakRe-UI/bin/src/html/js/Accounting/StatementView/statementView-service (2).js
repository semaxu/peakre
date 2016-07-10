/**
 * Created by Sema.Xu on 12/23/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    /**
     * client search service
     * @param model {{user: string, password: string}}
     * @param quiet {boolean}
     * @param async {boolean}
     * @param done {function}
     * @param fail {function|{}}
     */


    service.load = function (model, quiet, async, done, fail) {
        var SoaIdRead = model.SoaIdRead;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/loadSoaView/'+SoaIdRead;
        $pt.doGet(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.saveSoaView = function (model, quiet, async, done, fail) {
        var SoaIdRead =model.get("SoaId");
        var ReviewedFlag =model.get("ReviewedFlag");
        var SoaText =model.get("SoaText");
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/SaveSoaView/'+SoaIdRead+'/'+ReviewedFlag+'/'+SoaText;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };


    service.cancel = function (model, quiet, async, done, fail) {
        var SoaIdRead =model.get("SoaId");
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/cancelSoa/'+SoaIdRead;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.withdraw = function (model, quiet, async, done, fail) {
        var SoaIdRead =model.get("SoaId");
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/withdrawSoa/'+SoaIdRead;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.ignoringWithdraw = function (model, quiet, async, done, fail) {
        var SoaIdRead =model.get("SoaId");
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/withdrawIgnoringCutOffDateSoa/'+SoaIdRead;
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };
    service.doBeforeReversal = function (model, quiet, async, done, fail) {
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/validQuarterClosing';
        $pt.doGet(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.WithdrawOrderCheck = function (model, quiet, async, done, fail) {
        var SoaID =model.SoaId;
        var url =  $ri.getRestfulURL("action.accounting.statement")+'/IsSoaWithdrawOrder/'+SoaID;
        $pt.doPost(url, null, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };










}(this));