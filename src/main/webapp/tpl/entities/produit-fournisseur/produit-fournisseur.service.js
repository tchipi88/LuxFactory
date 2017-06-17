(function() {
    'use strict';
    angular
        .module('app')
        .factory('ProduitFournisseur', ProduitFournisseur);

    ProduitFournisseur.$inject = ['$resource','DateUtils'];

    function ProduitFournisseur ($resource,DateUtils) {
        var resourceUrl =  'api/produit-fournisseurs/:id';

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
