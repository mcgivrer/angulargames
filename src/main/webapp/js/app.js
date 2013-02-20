/** Application */

var app = angular.module("app",["i18n"])
	.config(function($routeProvider) {
				$routeProvider.
				      when('/games', {templateUrl: 'partials/game-list.html',   controller: 'GameListCtrl'}).
				      when('/game/:gameId', {templateUrl: 'partials/game-detail.html', controller: 'GameDetailCtrl'}).
				      otherwise({redirectTo: '/games'});
				}
			);
