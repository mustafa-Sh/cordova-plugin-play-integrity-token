var exec = require('cordova/exec');

exports.integToken = function (arg0, arg1, success, error) {
    exec(success, error, 'PlayIntegrityToken', 'integToken', [arg0, arg1]);
};