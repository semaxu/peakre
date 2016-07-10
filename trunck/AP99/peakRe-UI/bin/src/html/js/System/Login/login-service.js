/**
 * Created by brad.wu on 9/1/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var service = $pt.getService($page, 'service');
    var url = $pt.getURL('ui.login');
    /**
     * login service
     * @param model {{user: string, password: string}}
     * @param done {function}
     * @param fail {function|{}}
     */
    service.login = function (model, done, fail) {
        $pt.doPost(url, model, {
            quiet: true,
            async: false,
            done: done,
            fail: fail
        });
    };
}(typeof window !== "undefined" ? window : this));