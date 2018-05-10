(function() {
    'use strict';
    angular
        .module('app')
        .factory('EcritureCompteAnalytique', EcritureCompteAnalytique);

    EcritureCompteAnalytique.$inject = ['$resource','DateUtils'];

    function EcritureCompteAnalytique ($resource,DateUtils) {
        var resourceUrl =  'api/ecriture-compte-analytiques/:id';

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
