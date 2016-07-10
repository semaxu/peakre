/**
 * Created by brad.wu on 9/1/2015.
 */
(function (context) {
    var $helper = $pt.getService(context, '$helper');
    (function () {
        $helper.formatNumberForLabel = function (value, point) {
            if (value == null || (value + '').isBlank()) {
                return "";
            }
            value = parseFloat(value).toFixed(point);
            var parts = (value + '').split('.');
            var integral = parts[0];
            var fraction;
            if (point > 0) {
                if (parts.length > 1) {
                    var zeroNum = parts[1].length > point ? -1 : (point - parts[1].length + 1);
                    fraction = '.' + parts[1] + Array(zeroNum).join('0');
                } else {
                    fraction = '.' + Array(point + 1).join('0');
                }
            } else {
                fraction = parts.length > 1 ? '.' + parts[1] : '';
            }

            var rgx = /(\d+)(\d{3})/;
            while (rgx.test(integral)) {
                integral = integral.replace(rgx, '$1' + ',' + '$2');
            }
            return (integral + fraction).toString();
        };
    })();

}(typeof window !== "undefined" ? window : this));