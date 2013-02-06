'user strict';

/**
 * Games list controller.
 */ 
function GameListCtrl($scope ,$http){
	$http({method:'GET',url:'../rest/game/all.json'}).success(function(data) {
    	$scope.games = data;
  	}).error(function(data,status){
  		alert("Unable to read games database, status="+status);
  	});
}

/**
 * Game details controller.
 */ 
function GameDetailCtrl($scope, $routeParams, $http) {
	$scope.gameId = $routeParams.gameId;
	$http({method:"GET",url:'../rest/game/1.json'}).success(function(data) {
		$scope.game = data;
	});
}