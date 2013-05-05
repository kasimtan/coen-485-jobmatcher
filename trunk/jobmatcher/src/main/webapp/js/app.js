//add service module
var app = angular.module('app', ['app.services','ui.bootstrap']).
    config(['$routeProvider', function($routeProvider) {

    $routeProvider.

        when('/:param1', {templateUrl: '/index.html', controller: 'MainCtrl'})

}]);
app.run(function($rootScope) {
    /*
     Receive emitted message and broadcast it.
     Event names must be distinct or browser will blow up!
     */
    $rootScope.$on('handleEmit', function(event, args) {
        $rootScope.$broadcast('handleBroadcast', args);
    });
});



