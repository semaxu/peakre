/**
 * Created by ammon.zhou on 1/15/2016.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    /**
     * user search service
     * @param model
     * @param quiet {boolean}
     * @param async {boolean}
     * @param done {function}
     * @param fail {function|{}}
     */
    service.searchTransactionQuery = function (url, model, async, done) {
        $pt.doPost(url, model, {
            quiet: false,
            async: async,
            done: done
        });
    };
}(this));

