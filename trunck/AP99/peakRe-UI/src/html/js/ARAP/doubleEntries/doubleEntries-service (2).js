/**
 * Created by ammon.zhou on 1/15/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    service.queryDoubleEntries = function (url, model, async, done) {
        $pt.doGet(url, model, {
            quiet: false,
            async: async,
            done: done
        });
    };
}(this));

