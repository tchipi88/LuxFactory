(function() {
    'use strict';
    angular
        .module('app')
        .factory('Produit', Produit);

    Produit.$inject = ['$resource','DateUtils'];

    function Produit ($resource,DateUtils) {
        var resourceUrl =  'api/produits/:id';

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
