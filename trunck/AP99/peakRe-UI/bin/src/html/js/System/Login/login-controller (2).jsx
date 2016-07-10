/**
 * Created by brad.wu on 9/15/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        initializeErrorModel: function () {
            this.errorModel = $pt.createModel($page.model.error);
        },
        initialize: function () {
            this.autoLogin();
        },
        /**
         * check cookie, see auto login enabled or not
         * @override
         */
        autoLogin: function () {
            this.model = $pt.createModel({});
            var name = Cookies.get($page.constants.USER_COOKIE_NAME);
            this.model.set('name', name);
            var pwd = Cookies.get($page.constants.PWD_COOKIE_NAME);
            this.model.set('pwd', pwd);
            var autoLogin = Cookies.get($page.constants.AUTOLOGIN_COOKIE_NAME);
            this.model.set('autoLogin', autoLogin === 'true');
            if (autoLogin && Cookies.get($page.constants.LOGOUT_COOKIE_NAME) !== 'true') {
                $page.service.login(this.model.getCurrentModel(),
                    this.onLoginSuccess.bind(this),
                    this.renderUI.bind(this));
            } else {
                this.renderUI();
            }
            Cookies.remove($page.constants.LOGOUT_COOKIE_NAME);
        },
        onLoginSuccess: function (data) {
            console.log(data);
            Cookies.remove($page.constants.LOGOUT_COOKIE_NAME);
            $.sessionStorage.set($page.constants.MENU_COOKIE_NAME, data.menus);
            Cookies.set($page.constants.USER_COOKIE_NAME, this.model.get('name'));
            if (this.model.get('autoLogin')) {
                // auto login enabled
                Cookies.set($page.constants.PWD_COOKIE_NAME, this.model.get('pwd'));
                Cookies.set($page.constants.AUTOLOGIN_COOKIE_NAME, true);
            } else {
                Cookies.remove($page.constants.PWD_COOKIE_NAME, this.model.get('pwd'));
                Cookies.set($page.constants.AUTOLOGIN_COOKIE_NAME, false);
            }
            this.gotoIndex();
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout} className='login-form'/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        login: function () {
            var user = this.model.get('name');
            if (!user || user.isBlank()) {
                this.errorModel.set('text', $pt.getMessage('login.error.l001'));
                this.errorModel.set('loginFail', true);
                //this.form.refs.text.forceUpdate();
                return;
            }
            var password = this.model.get('pwd');
            if (!password || password.isBlank()) {
                this.errorModel.set('text', $pt.getMessage('login.error.l002'));
                this.errorModel.set('loginFail', true);
                //this.form.refs.text.forceUpdate();
                return;
            }
            $page.service.login(this.model.getCurrentModel(),
                this.onLoginSuccess.bind(this),
                {'506': this.onLoginFail.bind(this)});
        },
        onLoginFail: function (jqXHR) {
            var error = $pt.getMessage('login.error.' + jqXHR.responseJSON.code);
            this.errorModel.set('text', error);
            this.errorModel.set('loginFail', true);
            //this.form.refs.text.forceUpdate();
        }
    };
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));

    $page.controller = new Controller();
    // for layout purpose
    $page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));