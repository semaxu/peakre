/**
 * Created by brad.wu on 9/1/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $pt.mock({
        url: $pt.getURL('ui.login'),
        response: function (settings) {
            console.log(settings);
            var data = $pt.parseJSON(settings.data);
            var user = data.name;

            if (data.pwd != user) {
                this.status = '506';
                this.responseText = {
                    code: '0001'
                };
                return;
            }
            switch (user) {
                case 'admin':
                    this.responseText = {menus: $page.menus};
                    break;
                default:
                    this.status = '506';
                    this.responseText = {
                        code: '0002'
                    };
            }
        }
    });
}(typeof window !== "undefined" ? window : this));