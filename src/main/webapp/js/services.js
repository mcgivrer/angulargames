'user strict';

/**
 * Service Internationalization (i18n)
 */
app.factory('i18n', function($locale, $http) {

	var res = {};

	$http({
		method : 'GET',
		url : "i18n/messages.json"
	}).success(function(data) {
		res = data;
	}).error(function(data, status) {

		alert('Unable to contact i18n service for translation.');
	});

	return {
		getString : function(key) {
			return res[$locale.id][key];
		}
	}
});