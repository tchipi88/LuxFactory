(function() {
    'use strict';
    angular
        .module('app')
        .factory('[(${entity})]', [(${entity})]);

    [(${entity})].$inject = ['$resource','DateUtils'];

    function [(${entity})] ($resource,DateUtils) {
        var resourceUrl =  'api/[(${entity_url})]s/:id';

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
