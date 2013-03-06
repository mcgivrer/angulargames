'user strict';

/**
 * Games list controller.
 */ 
function GameListCtrl($scope ,$http,i18n){
	$http({method:'GET',url:'../rest/game/all'}).success(function(data) {
    	$scope.games = data;
  	}).error(function(data,status){
  		alert(i18n.getString("err.no.games")+status);
  	});
}

/**
 * Game details controller.
 */ 
function GameDetailCtrl($scope, $routeParams, $http, i18n) {
	$scope.gameId = $routeParams.gameId;
	$http({method:"GET",url:'../rest/game/'+$scope.gameId}).success(function(data) {
		$scope.game = data;
	}).error(function(data,status){
		alert(i18n.getString("err.no.game")+status);
	});
}