(function() {
    'use strict';
    angular
        .module('app')
        .factory('CompteAnalytique', CompteAnalytique);

    CompteAnalytique.$inject = ['$resource','DateUtils'];

    function CompteAnalytique ($resource,DateUtils) {
        var resourceUrl =  'api/compte-analytiques/:id';

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
