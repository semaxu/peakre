/**
 * Created by brad.wu on 9/15/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        label: {
            label: $pt.getMessage('login.label.please'),
            comp: {
                type: $pt.ComponentConstants.Label,
                textFromModel: false
            },
            css: {
                cell: 'col-lg-push-5'
            },
            pos: {row: 10, col: 1, width: 2}
        },
        name: {
            label: $pt.getMessage('login.user'),
            comp: {
                type: {type: $pt.ComponentConstants.Text, label: false},
                placeholder: $pt.getMessage('login.user')
            },
            evt: {
                keyUp: function (evt) {
                    if (evt.keyCode == '13') {
                        evt.preventDefault();
                        $(':password').focus();
                    }
                }
            },
            css: {
                cell: 'col-lg-push-5'
            },
            pos: {row: 100, col: 1, width: 2}
        },
        pwd: {
            label: $pt.getMessage('login.password'),
            comp: {
                type: {type: $pt.ComponentConstants.Text, label: false},
                pwd: true,
                placeholder: $pt.getMessage('login.password'),
                rightAddon: {
                    icon: 'arrow-right',
                    click: function () {
                        $page.controller.login();
                    }
                }
            },
            css: {
                cell: 'col-lg-push-5'
            },
            evt: {
                keyUp: function (evt) {
                    if (evt.keyCode == '13') {
                        evt.preventDefault();
                        $page.controller.login();
                    }
                }
            },
            pos: {row: 200, col: 1, width: 2}
        },
        text: {
            comp: {
                type: $pt.ComponentConstants.Label,
                visible: {
                    depends: 'loginFail',
                    when: function (model) {
                        return model.get('loginFail') === true;
                    }
                },
                model: $page.controller.errorModel
            },
            css: {
                cell: 'col-lg-push-5 has-error'
            },
            pos: {row: 300, col: 1, width: 2}
        },
        autoLogin: {
            label: $pt.getMessage('login.autoLogin'),
            comp: {
                type: {type: $pt.ComponentConstants.Check, label: false},
                labelAttached: 'right'
            },
            css: {
                cell: 'col-lg-push-5'
            },
            pos: {row: 999, col: 1, width: 2}
        }
    };
}(typeof window !== "undefined" ? window : this));