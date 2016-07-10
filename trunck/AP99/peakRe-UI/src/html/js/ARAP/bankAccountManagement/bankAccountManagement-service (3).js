/**
 * Created by brad.wu on 9/6/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');

    service.queryBankAccount = function (url, model, async, done) {
        $pt.doPost(url, model, {
            quiet: false,
            async: async,
            done: done
        });
    };

    service.saveBankAccount = function (model, quiet, async, done, fail) {
        $pt.doPost($ri.getRestfulURL("action.arap.bankAccount.saveBankAccount"), model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.updateBankAccount = function (model, quiet, async, done, fail) {
        $pt.doPost($ri.getRestfulURL("action.arap.bankAccount.updateBankAccount"), model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

    service.deleteBankAccount = function (model, quiet, async, done, fail) {
        $pt.doPost($ri.getRestfulURL("action.arap.bankAccount.deleteBankAccount"), model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };

}(this));