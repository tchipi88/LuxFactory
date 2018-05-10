(function() {
    'use strict';
    angular
        .module('app')
        .factory('Produits', Produits);

    Produits.$inject = ['$resource','DateUtils'];

    function Produits ($resource,DateUtils) {
        var resourceUrl =  'api/produitss/:id';

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
            'update': { method:'PUT' },
            'save': { method:'POST' }
        });
    }
})();
