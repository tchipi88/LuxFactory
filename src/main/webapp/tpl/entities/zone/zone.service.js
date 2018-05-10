(function() {
    'use strict';
    angular
        .module('app')
        .factory('Zone', Zone);

    Zone.$inject = ['$resource','DateUtils'];

    function Zone ($resource,DateUtils) {
        var resourceUrl =  'api/zones/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
