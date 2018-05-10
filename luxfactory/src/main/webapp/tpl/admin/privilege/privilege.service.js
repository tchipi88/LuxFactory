(function() {
    'use strict';
    angular
        .module('app')
        .factory('Privilege', Privilege);

    Privilege.$inject = ['$resource','DateUtils'];

    function Privilege ($resource,DateUtils) {
        var resourceUrl =  'api/privileges/:id';

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
