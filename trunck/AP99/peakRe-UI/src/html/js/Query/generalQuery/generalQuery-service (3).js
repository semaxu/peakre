/**
 * Created by ammon.zhou on 2/22/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');


    service.query = function (url, model, quiet, async, done, fail) {
        $pt.doPost(url, model, {
            quiet: quiet,
            async: async,
            done: done,
            fail: fail
        });
    };


}(this));