angular.module('app.services', ['ngResource']).

   factory('EventBroadcast', function($rootScope) {
        // eventBroadcaster is the object created by the factory method.
        var eventBroadcaster = {};

        // The message is a string or object to carry data with the event.
        eventBroadcaster.obj = {};

        // The event name is a string used to define event types.
        eventBroadcaster.eventName = '';

        // This method is called from within a controller to define an event and attach data to the eventBroadcaster object.
        eventBroadcaster.broadcast = function(evName, obj) {
            this.obj = obj;
            this.eventName = evName;
            this.broadcastItem();
        };

        // This method broadcasts an event with the specified name.
        eventBroadcaster.broadcastItem = function() {
            $rootScope.$broadcast(this.eventName,this.obj);
        };

        return eventBroadcaster;
    }). factory('JobSeeker', function($resource) {

        return $resource('/jobmatcher/jobseekers/:id', {id : '@id'},
            {

            }

        );

    }).factory('HiringManager', function($resource) {

        return $resource('/jobmatcher/hiringmanagers/:id', {id : '@id'},
            {

            }

        );
    }).factory('Job', function($resource) {



        return $resource('/jobmatcher/jobs/:id', {id : '@id'},
            {

            }

        );
    }).factory('JobSearch', function($resource) {



        return $resource('/jobmatcher/jobs/search?keyword=:keyword', {keyword : '@keyword'},
            {

            }

        );
    })


    config(function($httpProvider) {
        $httpProvider.defaults.headers.post  = {'content-type': 'application/json'};
    })