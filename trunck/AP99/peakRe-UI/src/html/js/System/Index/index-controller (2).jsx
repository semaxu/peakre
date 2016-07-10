/**
 * Created by brad.wu on 9/1/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        renderContent: function () {
            ReactDOM.render(<LandingMenu menus={this.menus}/>, document.getElementById('main'));
        }
    };
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));

    $page.controller = new Controller();
    $page.controller.isIndexPage = true;
}(typeof window !== "undefined" ? window : this));