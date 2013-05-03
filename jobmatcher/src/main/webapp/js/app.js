//add service module
var app = angular.module('app', ['app.services','ui.bootstrap']).
    config(['$routeProvider', function($routeProvider) {

    $routeProvider.
        otherwise({ redirectTo: '/cafe.html'}).
        when('/cafe.html', {templateUrl: 'partials/cafe.html'}).
        when('/myprofile.html', {templateUrl: 'partials/myprofile.html'}).
        when('/visual.html', {templateUrl: 'partials/visual.html'}).
        when('/cafedetail.html', {templateUrl: 'partials/cafedetail.html'}).
        when('/review.html', {templateUrl: 'partials/review.html'}).
        when('/cafedetail/:cafeid', {templateUrl:'partials/cafedetail.html'});

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



