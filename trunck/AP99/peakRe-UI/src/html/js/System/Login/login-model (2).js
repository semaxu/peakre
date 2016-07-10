/**
 * Created by brad.wu on 9/15/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    // form model
    var form = {
        name: null,
        pwd: null,
        autoLogin: false
    };

    // error model
    var error = {
        text: null,
        loginFail: false
    };

    $page.model = {
        form: form,
        error: error
    };
}(typeof window !== "undefined" ? window : this));