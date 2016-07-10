(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.validator = $pt.createModelValidator({
    });
}(typeof window !== "undefined" ? window : this));
