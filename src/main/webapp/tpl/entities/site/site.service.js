(function() {
    'use strict';
    angular
        .module('app')
        .factory('Site', Site);

    Site.$inject = ['$resource','DateUtils'];

    function Site ($resource,DateUtils) {
        var resourceUrl =  'api/sites/:id';

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
